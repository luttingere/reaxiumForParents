package com.example.eduardoluttinger.reaxiumforparents.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Eduardo Luttinger on 21/04/2016.
 */
public class ReaxiumDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ReaxiumForParents.db";

    public ReaxiumDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(AssociatedUsersContract.SQL_CREATE_ASSOCIATED_USER_TABLE);
        db.execSQL(AccessMessageContract.SQL_CREATE_ACCESS_MESSAGE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(AssociatedUsersContract.SQL_DELETE_ASSOCIATED_USER_TABLE);
        db.execSQL(AccessMessageContract.SQL_DELETE_ACCESS_MESSAGE_TABLE);

        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
