package com.msusevusal.javabatchmobileclient.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.msusevusal.javabatchmobileclient.R;
import com.msusevusal.javabatchmobileclient.db.JobInstancesDS;
import com.msusevusal.javabatchmobileclient.db.RestState;
import com.msusevusal.javabatchmobileclient.model.JsonResponseObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class MainActivity extends Activity {
    private JobInstancesDS dataStore;
    static Random random = new Random();
    static long instanceId = random.nextLong();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataStore = new JobInstancesDS(this);
        try {
            dataStore.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Button mPopulateJobInstances = (Button) findViewById(R.id.button_populate_jobInstances);

        mPopulateJobInstances.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                instanceId++;
                JsonResponseObject jobInstance = new JsonResponseObject(instanceId,-1, RestState.POST_REQUESTED,"{\"Sid\":\"Android\"}");

                dataStore.createJobInstance(jobInstance);
                Log.i("[DEBUG]", "created jobInstance " + instanceId);
            }
        });

        final ListView mJobInstancesList = (ListView) findViewById(R.id.listView_jobInstances);

        Button mGetJobInstances = (Button) findViewById(R.id.button_jobInstances);
        final HashMap<Long,String> strList = new HashMap<>();

        mGetJobInstances.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("[DEBUG]","getting instance : " + instanceId);
//
                JsonResponseObject instance = dataStore.getJobInstance(instanceId);
//                ArrayList<JsonResponseObject> list = dataStore.getJobInstances();
//                for(JsonResponseObject obj : list){
//                    Log.d("[DEBUG]","Got Id : " + obj.getId());
//                    strList.add(obj.toString());
//                }
                strList.put(instanceId,instance.toString());
                List tp ;
                Log.i("[DEBUG]",instance.toString());
                ArrayAdapter<String> mJobInstancesListAdapter = new ArrayAdapter<String>(v.getContext(),R.layout.simple_list_view, new ArrayList<String>(strList.values()));
                mJobInstancesList.setAdapter(mJobInstancesListAdapter);

                }

        });

    }

    @Override
    protected  void onPause() {
        dataStore.close();
        super.onPause();
    }

    @Override
    protected void onResume() {
        try {
            dataStore.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        super.onResume();
    }
}
