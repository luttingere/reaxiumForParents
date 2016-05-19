package com.example.eduardoluttinger.reaxiumforparents.database;

import android.provider.BaseColumns;

/**
 *  Created by Eduardo Luttinger on 21/04/2016.
 *
 *  Contract reader por search access control message in the device
 *
 */
public final class AccessMessageContract {

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";

    /**
     * create table sentence
     */
    public static final String SQL_CREATE_ACCESS_MESSAGE_TABLE =
            "CREATE TABLE " + ReaxiumAccessMessages.TABLE_NAME + " (" +
                    ReaxiumAccessMessages._ID + " INTEGER PRIMARY KEY," +
                    ReaxiumAccessMessages.COLUMN_NAME_USER_ID + INTEGER_TYPE + COMMA_SEP +
                    ReaxiumAccessMessages.COLUMN_NAME_DEVICE_ID + INTEGER_TYPE + COMMA_SEP +
                    ReaxiumAccessMessages.COLUMN_NAME_ACCESS_TYPE + TEXT_TYPE  + COMMA_SEP +
                    ReaxiumAccessMessages.COLUMN_NAME_ACCESS_INFO + TEXT_TYPE  + COMMA_SEP +
                    ReaxiumAccessMessages.COLUMN_NAME_ACCESS_DATE + TEXT_TYPE  + " )";

    /**
     * Delete table sentence
     */
    public static final String SQL_DELETE_ACCESS_MESSAGE_TABLE =
            "DROP TABLE IF EXISTS " + ReaxiumAccessMessages.TABLE_NAME;



    public AccessMessageContract(){}



    /**
     * Reaxium Access Messages Table
     */
    public static abstract class ReaxiumAccessMessages implements BaseColumns {
        public static final String TABLE_NAME = "reaxium_access_message";
        public static final String COLUMN_NAME_USER_ID = "user_id";
        public static final String COLUMN_NAME_ACCESS_TYPE= "access_type";
        public static final String COLUMN_NAME_ACCESS_DATE= "access_date";
        public static final String COLUMN_NAME_ACCESS_INFO = "access_info";
        public static final String COLUMN_NAME_DEVICE_ID= "device_id";
    }
}
