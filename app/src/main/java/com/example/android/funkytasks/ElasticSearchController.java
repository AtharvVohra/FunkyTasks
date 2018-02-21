package com.example.android.funkytasks;

/**
 * Created by MonicaB on 2018-02-20.
 */

import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.util.ArrayList;
import java.util.List;

import io.searchbox.client.JestResult;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

public class ElasticSearchController {

    private static JestDroidClient client;
    private static String database = "http://cmput301.softwareprocess.es:8080/";
    private static String indexType = "cmput301w18t20";
    private static String userType = "user";
    private static String taskType = "task";

    public static class PostUser extends AsyncTask<User, Void, Void> { // add new user to database

        @Override
        protected Void doInBackground(User... newUser) {
            verifySettings();

            User user = newUser[0]; // get the new created user object to post to database

            Index index = new Index.Builder(database).index(indexType).type(userType).build();

            try {
                DocumentResult result = client.execute(index);
                if (result.isSucceeded()) {
                    user.setId(result.getId());
                } else {
                    Log.i("Error", "Some error with adding user");
                }
            } catch (Exception e) {
                Log.i("Error", "The application failed to build and add the users");
            }

            return null;
        }
    }

    public static class PostTask extends AsyncTask<Object, Void, Void> { // adds new task for the user

        // https://stackoverflow.com/questions/12069669/how-can-you-pass-multiple-primitive-parameters-to-asynctask
        @Override
        protected Void doInBackground(Object... params) {
            verifySettings();

            User user = (User) params[0];
            Task task = (Task) params[1];

            //https://www.elastic.co/guide/en/elasticsearch/reference/2.3/query-dsl-term-query.html
            String query = "{\n" +
                    "    \"query\" : {\n" +
                    "        \"term\" : { \"username\" : \"" + user.getUsername() + "\" }\n" +
                    "    }\n" +
                    "}";

            Search search = new Search.Builder(query).addIndex(database).addType(userType).build();

            try{
                JestResult result = client.execute(search); // Use JestResult for one result and searchresult for all results to add to a list
                if(result.isSucceeded()){
                    User userResult = result.getSourceAsObject(User.class);
                    userResult.addTask(task); // add the new task to the user's list
                }
                else{
                    Log.e("Nothing", "Theres no user in database");
                }
            }
            catch(Exception e){
                Log.e("Error", "Something went wrong with getting user!");
            }

            return null;
        }
    }


    public static class GetUser extends AsyncTask<String, Void, User> { // grabs user from database

        @Override
        protected User doInBackground(String... search_parameters) {
            verifySettings();

            //https://www.elastic.co/guide/en/elasticsearch/reference/2.3/query-dsl-term-query.html
            String query = "{\n" +
                    "    \"query\" : {\n" +
                    "        \"term\" : { \"username\" : \"" + search_parameters[0] + "\" }\n" +
                    "    }\n" +
                    "}";

            Search search = new Search.Builder(query).addIndex(database).addType(userType).build();

            try {
                JestResult result = client.execute(search); // Use JestResult for one result and searchresult for all results to add to a list
                if (result.isSucceeded()) {
                    User user = result.getSourceAsObject(User.class);
                    return user;
                } else {
                    Log.e("Nothing", "Theres no user in database");
                }
            } catch (Exception e) {
                Log.e("Error", "Something went wrong with getting user!");
            }

            return null;
        }
    }


    public static class GetAllTask extends AsyncTask<String, Void, ArrayList<Task>> {

        @Override
        protected ArrayList<Task> doInBackground (String... search_parameters) {
            verifySettings();

            // TODO build the correct query that returns all tasks associated with the USER

            String query = "{\n" +
                    "    \"query\" : {\n" +
                    "        \"term\" : { \"username\" : \"" + search_parameters[0] + "\" }\n" +
                    "    }\n" +
                    "}";

            ArrayList<Task> tasks = new ArrayList<Task>();

            Search search = new Search.Builder(query).addIndex(database).addType(userType).build();

            try{
                JestResult result = client.execute(search); // Use JestResult for one result and searchresult for all results to add to a list
                if(result.isSucceeded()){
                    User user = result.getSourceAsObject(User.class);
                    tasks.addAll(user.getTasks()); // grab the list of tasks for the user
                    return tasks;
                }
                else{
                    Log.e("Nothing", "Theres no user in database to grab tasks from");
                }
            }
            catch(Exception e){
                Log.e("Error", "Something went wrong with getting tasks from user!");
            }

            return null;
        }
    }

    public static class updateProfile extends AsyncTask<User,Void,Void>{ // update a user's contact info by passing in the user object

        @Override
        protected Void doInBackground (User... currentUser) {
            verifySettings();

            User user = currentUser[0];

            Index index = new Index.Builder(database).index(indexType).type(userType).id(user.getId()).build();

            try{
                DocumentResult result = client.execute(index); // Use JestResult for one result and searchresult for all results to add to a list
                if(result.isSucceeded()){
                    return null;
                }
                else{
                    Log.e("Nothing", "Theres no user in database to grab tasks from");
                }
            }
            catch(Exception e){
                Log.e("Error", "Something went wrong with getting tasks from user!");
            }


            return null;
        }
    }


    public static class updateTask extends AsyncTask<User,Void,Void>{ // update a user's task by passing in the user object
        // TODO IN THE front end (activity), make sure that the task is added to the user and then pass the user object in

        @Override
        protected Void doInBackground (User... currentUser) {
            verifySettings();

            User user = currentUser[0];

            Index index = new Index.Builder(database).index(indexType).type(userType).id(user.getId()).build();

            try{
                DocumentResult result = client.execute(index); // Use JestResult for one result and searchresult for all results to add to a list
                if(result.isSucceeded()){
                    return null;
                }
                else{
                    Log.e("Nothing", "Theres no user in database to update their task");
                }
            }
            catch(Exception e){
                Log.e("Error", "Something went wrong with getting tasks from user!");
            }


            return null;
        }
    }





    // Later

    //TODO update a task's status in database


    //TODO get all BIDDED tasks for task requester (US 05.04.01, I want to view list of MY tasks that have status bidded)

    //TODO get all BIDDED tasks for task provider (UC 05.02.01, I want to view a list of all tasks I HAVE bidded on)

    //TODO delete a specific task (status=done)


    public static void verifySettings() {
        if (client == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder(database);
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }


}
