package com.example.android.funkytasks;

import android.content.ClipData;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.android.funkytasks.MainMenuActivity.tasksArrayList;


public class DashboardRequestedTask extends AppCompatActivity {

    private TextView titleValue;
    private TextView descriptionValue;
    private Button edit;
    private ListView bidListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_requested_task);


        Bundle extras = getIntent().getExtras();


        //set task details
        setTaskDetails();
        //set bids
        setBids();

        //to edit task details
        edit=(Button)findViewById(R.id.buttonEdit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendToEditDashboardRequestedTask(view);
            }
        });


    }

    // create an action bar button
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.editmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.editButton) {
            Button edit = (Button) findViewById(R.id.editButton);
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sendToEditDashboardRequestedTask(view);
                }
            });
        }
        return super.onOptionsItemSelected(item);
    }

    //set title and description
    public void setTaskDetails() {
        descriptionValue=(TextView)findViewById(R.id.textDescription);
        titleValue=(TextView) findViewById(R.id.taskName);
        titleValue.setText(tasksArrayList.get(0).getTitle());
        descriptionValue.setText(tasksArrayList.get(0).getDescription());

    }

    public void setBids(){
        bidListView=(ListView) findViewById(R.id.listView);

        ArrayAdapter<Bid> adapter= new ArrayAdapter<Bid>(DashboardRequestedTask.this,android.R.layout.simple_list_item_multiple_choice,tasksArrayList.get(0).getBids());
        bidListView.setAdapter(adapter);


    }


    public void sendToEditDashboardRequestedTask(View view){
        Intent intent = new Intent(this, EditDashboardRequestedTask.class);
        startActivityForResult(intent, 42);
    }
}
