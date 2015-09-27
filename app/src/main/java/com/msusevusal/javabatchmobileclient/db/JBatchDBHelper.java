package com.msusevusal.javabatchmobileclient.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sid on 9/21/15.
 */
public class JBatchDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "jbatch.db";
    private static final int VERSION = 1;

    public JBatchDBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        JobInstancesTable.onCreate(db);
//        JobExecutionTable.onCreate(db);
//        StepExecutionTable.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        JobInstancesTable.onUpgrade(db,oldVersion,newVersion);
//        JobExecutionTable.onUpgrade(db,oldVersion,newVersion);
//        StepExecutionTable.onUpgrade(db,oldVersion,newVersion);
    }
}
