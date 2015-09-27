package com.msusevusal.javabatchmobileclient.db;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by sid on 9/21/15.
 */
public class JobInstancesTable {

    public static final String TABLE_NAME = "INSTANCES";

    //adding a separate pk as the rest request might fail and might not provide an instanceId
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_INSTANCEID = "instanceId";
    public static final String COLUMN_BODY = "jsonData";
    public static final String COLUMN_REST_STATE = "restState";

    public static final String[] COLUMNS = {COLUMN_ID,COLUMN_INSTANCEID,COLUMN_REST_STATE,COLUMN_BODY};

    public static final String CREATE_TABLE ="create table " +
            TABLE_NAME +
            "( " +
            COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_INSTANCEID + " integer, " +
            COLUMN_BODY + " text, " +
            COLUMN_REST_STATE + " integer not null " +
            ");";

    public static void onCreate(SQLiteDatabase database){
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);

        database.execSQL(CREATE_TABLE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion){
        Log.w(JobInstancesTable.class.getName(), "Upgrading db from " + oldVersion + " to " + newVersion);
        onCreate(database);

    }
}
