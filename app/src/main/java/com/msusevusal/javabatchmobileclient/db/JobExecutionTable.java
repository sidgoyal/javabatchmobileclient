package com.msusevusal.javabatchmobileclient.db;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by sid on 9/21/15.
 */
public class JobExecutionTable {
    public static final String TABLE_NAME = "EXECUTIONS";

    //adding a separate pk as the rest request might fail and might not provide an instanceId
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_EXECUTIONID = "executionId";
    public static final String COLUMN_BODY = "jsonData";
    public static final String COLUMN_REST_STATE = "restState";
    public static final String COLUMN_INSTANCEID_FK = "instanceId_fk";

    public static final String CREATE_TABLE ="create table " +
            TABLE_NAME +
            "( " +
            COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_EXECUTIONID + " integer, " +
            COLUMN_INSTANCEID_FK + "integer, " +
            COLUMN_BODY + " text, " +
            COLUMN_REST_STATE + " integer not null, " +
            //FOREIGN KEY (instanceId_fk) REFERENCES INSTANCES(instanceId),
            "FOREIGN KEY(" + COLUMN_INSTANCEID_FK + ") REFERENCES INSTANCES(" + JobInstancesTable.COLUMN_INSTANCEID + "), " +
            ");";

    public static void onCreate(SQLiteDatabase database){
        database.execSQL(CREATE_TABLE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion){
        Log.w(JobExecutionTable.class.getName(), "Upgrading db from " + oldVersion + " to " + newVersion);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(database);

    }
}
