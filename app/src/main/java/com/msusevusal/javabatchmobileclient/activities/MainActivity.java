package com.msusevusal.javabatchmobileclient.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.msusevusal.javabatchmobileclient.R;
import com.msusevusal.javabatchmobileclient.db.JobInstancesDS;
import com.msusevusal.javabatchmobileclient.db.RestState;
import com.msusevusal.javabatchmobileclient.model.JsonResponseObject;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class MainActivity extends Activity {
    private JobInstancesDS dataStore;
    static Random random = new Random();
    static long instanceId = random.nextInt(1000);
    public static final String TEST_DS_STR_LIST = "com.msusevusal.test.strList";

    public static final String INTENT_SEND="com.msusevusal.javabatchmobileclient.SEND_DATA";
    public final String VIEW_DATA_ACTION = "android.intent.action.SHOWRESULT";

    private void createJobInstance (JsonResponseObject jobInstance){
        instanceId++;

        jobInstance.setId(instanceId);
        jobInstance.setState(RestState.GET_REQUESTED);
        jobInstance.setParentId(-1);
        jobInstance.setBody("{\"Sid\":\"Created\"}");

        dataStore.createJobInstance(jobInstance);
        Log.i("[DEBUG]", "created jobInstance " + instanceId);

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataStore = new JobInstancesDS(this);
        final Set<String> strList = new HashSet<>();

        final Intent intent = new Intent(INTENT_SEND);
//        intent.setAction(VIEW_DATA_ACTION);

        try {
            dataStore.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        final JsonResponseObject jobInstance = new JsonResponseObject();

        Button mPopulateJobInstances = (Button) findViewById(R.id.button_populate_jobInstances);
        mPopulateJobInstances.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createJobInstance(jobInstance);
            }
        });

//        final ListView mJobInstancesList = (ListView) findViewById(R.id.listView_jobInstances);

        Button mGetJobInstances = (Button) findViewById(R.id.button_jobInstances);
        mGetJobInstances.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("[DEBUG]","getting instances " );
//
//                JsonResponseObject instance = dataStore.getJobInstance(instanceId);
                ArrayList<JsonResponseObject> list = dataStore.getJobInstances();
                for(JsonResponseObject obj : list){
                    Log.d("[DEBUG]", "Got Id : " + obj.getId());
                    strList.add(obj.toString());
                }
//                strList.put(instanceId,instance.toString());
//                Log.i("[DEBUG]",instance.toString());
//                ArrayAdapter<String> mJobInstancesListAdapter = new ArrayAdapter<String>(v.getContext(),R.layout.simple_list_view, new ArrayList<String>(strList));
//                mJobInstancesList.setAdapter(mJobInstancesListAdapter);

                intent.putExtra(TEST_DS_STR_LIST, new ArrayList(strList));
                startActivity(intent);
                }

        });

        final Button mGetJobInstance = (Button) findViewById(R.id.button_jobInstance);
        mGetJobInstance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("[DEBUG]", "getting instance : " + instanceId);
                strList.add("" + dataStore.getJobInstance(instanceId));
                intent.putExtra(TEST_DS_STR_LIST, new ArrayList(strList));
                startActivity(intent);
            }
        });

        final Button mGetRestState = (Button) findViewById(R.id.button_get_restState);
        mGetRestState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("[DEBUG]", "getting restState for  : " + instanceId);
                strList.add("" + dataStore.getRestState(instanceId));
                intent.putExtra(TEST_DS_STR_LIST, new ArrayList(strList));
                startActivity(intent);
            }
        });

        final Button mUpdateJobInstanceBody = (Button) findViewById(R.id.button_update_jobInstance);
        mUpdateJobInstanceBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("[DEBUG]", "updating jobInstance Body for  : " + instanceId);
                jobInstance.setBody("{\"Sid\":\"Updated\"}");
                dataStore.updateJobInstanceBody(jobInstance);
            }
        });

        final Button mUpdateRestState = (Button) findViewById(R.id.button_update_restState);
        mUpdateRestState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("[DEBUG]", "updating restState for  : " + instanceId);
                dataStore.updateRestState(instanceId, RestState.PUT_REQUESTED);

            }
        });

        final Button mDeleteLast = (Button) findViewById(R.id.button_delete);
        mDeleteLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("[DEBUG]", "deleting   : " + instanceId);
                dataStore.deleteJobInstance(instanceId);

            }
        });

        final Button mDeleteRange = (Button) findViewById(R.id.button_delete_range);
        mDeleteRange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataStore.createJobInstance(jobInstance);
                dataStore.createJobInstance(jobInstance);
                dataStore.createJobInstance(jobInstance);
                dataStore.createJobInstance(jobInstance);



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
