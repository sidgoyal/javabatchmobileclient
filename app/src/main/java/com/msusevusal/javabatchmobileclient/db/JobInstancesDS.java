package com.msusevusal.javabatchmobileclient.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.msusevusal.javabatchmobileclient.model.JsonResponseObject;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by sid on 9/21/15.
 */
public class JobInstancesDS {

    // Database fields
    private SQLiteDatabase database;
    private JBatchDBHelper dbHelper;

    public JobInstancesDS(Context context) {

        dbHelper = new JBatchDBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void createJobInstance(JsonResponseObject instance){
        ContentValues contentValues = new ContentValues();
        contentValues.put(JobInstancesTable.COLUMN_INSTANCEID, instance.getId());
        contentValues.put(JobInstancesTable.COLUMN_BODY,instance.getBody());
        contentValues.put(JobInstancesTable.COLUMN_REST_STATE, instance.getState().ordinal());

        database.insert(JobInstancesTable.TABLE_NAME, null, contentValues);

    }

    public void updateJobInstanceBody(JsonResponseObject instance){
        ContentValues contentValues = new ContentValues();
        contentValues.put(JobInstancesTable.COLUMN_BODY, instance.getBody());

        database.update(JobInstancesTable.TABLE_NAME, contentValues, JobInstancesTable.COLUMN_INSTANCEID + "=?",
                new String[]{"" + instance.getId()});

    }

    public JsonResponseObject getJobInstance(long jobInstanceId){
        Cursor cursor = database.query(JobInstancesTable.TABLE_NAME, JobInstancesTable.COLUMNS, JobInstancesTable.COLUMN_INSTANCEID + "=?" ,
                new String[]{""+jobInstanceId},null,null,null);

        checkOneJobInstance(cursor, jobInstanceId);

        cursor.moveToNext();
        JsonResponseObject response;
        response = getJobInstance(cursor);


        cursor.close();

        return response;
    }

    public ArrayList<JsonResponseObject> getJobInstances(){
        Cursor cursor = database.query(JobInstancesTable.TABLE_NAME, JobInstancesTable.COLUMNS, null,null,null,null,null);
        ArrayList<JsonResponseObject> jobInstances = new ArrayList<>();
        while(cursor.moveToNext()){
            jobInstances.add(getJobInstance(cursor));
        }
        cursor.close();
        return jobInstances;

    }

    public RestState getRestState(long jobInstanceId){
        Cursor cursor = database.query(JobInstancesTable.TABLE_NAME,new String[]{JobInstancesTable.COLUMN_REST_STATE},JobInstancesTable.CREATE_TABLE + "=?",
                new String[]{""+jobInstanceId},null,null,null);
        checkOneJobInstance(cursor, jobInstanceId);
        cursor.moveToNext();
        return  RestState.values()[cursor.getInt(cursor.getColumnIndex(JobInstancesTable.COLUMN_REST_STATE))];
    }

    public void updateRestState(long jobInstanceId, RestState restState){
        ContentValues cv = new ContentValues();
        cv.put(JobInstancesTable.COLUMN_REST_STATE,restState.ordinal());
        database.update(JobInstancesTable.TABLE_NAME,cv,JobInstancesTable.COLUMN_INSTANCEID + "=?", new String[]{"" + jobInstanceId});


    }

    private JsonResponseObject getJobInstance(Cursor cursor){

        JsonResponseObject response = new JsonResponseObject();

        try {
            response.setId(cursor.getLong(cursor.getColumnIndex(JobInstancesTable.COLUMN_INSTANCEID)));
            response.setBody(cursor.getString(cursor.getColumnIndex(JobInstancesTable.COLUMN_BODY)));
            response.setState(RestState.values()[cursor.getInt(cursor.getColumnIndex(JobInstancesTable.COLUMN_REST_STATE))]);
        }
        catch(NullPointerException e){
            Log.i("MobileClient" , "JobInstancesDS " + e.getStackTrace());
        }
        finally {
            cursor.close();
        }
        return response;
    }

    private void checkOneJobInstance(Cursor cursor, long jobInstanceId){
        if(cursor.getCount() > 1){
            Log.e("[DEBUG]","curosr count " + cursor.getCount());
            Log.e("[DEBUG]",cursor.toString());
            throw new IllegalStateException("Cannot get more than one jobInstance records for jobInstanceId " + jobInstanceId);
        }
    }
}
