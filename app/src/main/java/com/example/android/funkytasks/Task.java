package com.example.android.funkytasks;

/**
 * Created by MonicaB on 2018-02-20.
 */

public class Task {
    // title, description, status, lowest bid,

    private String title;
    private String description;
    private String poster;
    private String status;
    private String[] statuses={"requested","bidded","asigned","done"};

    Task(String title, String description,String poster){
        // constructor for task object
        this.title = title;
        this.description = description;
        this.poster = poster;
        this.status = statuses[0];

    }

    public String getTitle(){
        return this.title;
    }
    public void setTitle(String newTitle){
        this.title = newTitle;
    }
    public String getDescription(){
        return this.description;
    }
    public void setDescription(String newDescription){
        this.description = newDescription;
    }
    public void setBidded(){
        this.status = statuses[1];
    }
    public void setAsigned(){
        this.status = statuses[2];
    }
    public void setDone(){
        this.status = statuses[3];
    }
    public String getStatus(){
        return this.status;
    }
    public void setPoster(String newPoster){
        this.poster=newPoster;
    }
    public String getPoster(){
        return this.poster;
    }

}
