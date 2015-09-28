package com.msusevusal.javabatchmobileclient.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.msusevusal.javabatchmobileclient.R;

import java.util.ArrayList;

public class ViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        Intent intent = getIntent();
        ArrayList<String> strList = intent.getStringArrayListExtra(MainActivity.TEST_DS_STR_LIST);
        ArrayAdapter<String> mJobInstancesListAdapter = new ArrayAdapter<String>(this,R.layout.simple_list_view, new ArrayList<String>(strList));
        ListView mListView = (ListView) findViewById(R.id.listView_showResult);
            mListView.setAdapter(mJobInstancesListAdapter);

    }

}
