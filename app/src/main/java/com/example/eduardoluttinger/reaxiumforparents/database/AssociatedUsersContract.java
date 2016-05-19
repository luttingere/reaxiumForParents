package com.example.eduardoluttinger.reaxiumforparents.database;

import android.provider.BaseColumns;

/**
 *  Created by Eduardo Luttinger on 21/04/2016.
 *
 *  Contract reader por search reaxium users in the device
 *
 */
public final class AssociatedUsersContract {

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";

    /**
     * create table sentence
     */
    public static final String SQL_CREATE_ASSOCIATED_USER_TABLE =
            "CREATE TABLE " + ReaxiumAssociatedUsers.TABLE_NAME + " (" +
                    ReaxiumAssociatedUsers._ID + " INTEGER PRIMARY KEY," +
                    ReaxiumAssociatedUsers.COLUMN_NAME_USER_ID + INTEGER_TYPE + COMMA_SEP +
                    ReaxiumAssociatedUsers.COLUMN_NAME_USER_NAME + TEXT_TYPE + COMMA_SEP +
                    ReaxiumAssociatedUsers.COLUMN_NAME_USER_SECOND_NAME + TEXT_TYPE + COMMA_SEP +
                    ReaxiumAssociatedUsers.COLUMN_NAME_USER_LAST_NAME + TEXT_TYPE + COMMA_SEP +
                    ReaxiumAssociatedUsers.COLUMN_NAME_USER_DOCUMENT_ID + TEXT_TYPE + COMMA_SEP +
                    ReaxiumAssociatedUsers.COLUMN_NAME_USER_BIRTH_DATE + TEXT_TYPE + COMMA_SEP +
                    ReaxiumAssociatedUsers.COLUMN_NAME_USER_PHOTO + TEXT_TYPE + COMMA_SEP +
                    ReaxiumAssociatedUsers.COLUMN_NAME_USER_BUSINESS_NAME + TEXT_TYPE + COMMA_SEP +
                    ReaxiumAssociatedUsers.COLUMN_NAME_USER_EMAIL + TEXT_TYPE  + " )";

    /**
     * Delete table sentence
     */
    public static final String SQL_DELETE_ASSOCIATED_USER_TABLE =
            "DROP TABLE IF EXISTS " + ReaxiumAssociatedUsers.TABLE_NAME;



    public AssociatedUsersContract(){}



    /**
     * Reaxium Users Table
     */
    public static abstract class ReaxiumAssociatedUsers implements BaseColumns {
        public static final String TABLE_NAME = "reaxium_associated_users";
        public static final String COLUMN_NAME_USER_ID = "user_id";
        public static final String COLUMN_NAME_USER_NAME= "user_name";
        public static final String COLUMN_NAME_USER_SECOND_NAME= "user_second_name";
        public static final String COLUMN_NAME_USER_LAST_NAME= "user_last_name";
        public static final String COLUMN_NAME_USER_DOCUMENT_ID = "user_document_id";
        public static final String COLUMN_NAME_USER_BIRTH_DATE = "user_birth_date";
        public static final String COLUMN_NAME_USER_PHOTO = "user_photo";
        public static final String COLUMN_NAME_USER_EMAIL = "user_email";
        public static final String COLUMN_NAME_USER_BUSINESS_NAME = "user_business_name";
    }
}
