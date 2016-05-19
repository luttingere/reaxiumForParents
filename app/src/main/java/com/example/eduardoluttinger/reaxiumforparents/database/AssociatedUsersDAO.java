package com.example.eduardoluttinger.reaxiumforparents.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.eduardoluttinger.reaxiumforparents.beans.Business;
import com.example.eduardoluttinger.reaxiumforparents.beans.User;
import com.example.eduardoluttinger.reaxiumforparents.global.GGGlobalValues;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by Eduardo Luttinger on 21/04/2016.
 * <p/>
 * class in charge of execute selects, inserts and updates operations in the AssociatedUsers table
 */
public class AssociatedUsersDAO {

    public static final String TAG = GGGlobalValues.TRACE_ID;
    private SQLiteDatabase database;
    private ReaxiumDbHelper dbHelper;
    private static AssociatedUsersDAO reaxiumUsersDAO;
    private ContentValues insertValues;

    private AssociatedUsersDAO(Context context) {
        dbHelper = new ReaxiumDbHelper(context);
    }

    public static AssociatedUsersDAO getInstance(Context context) {
        if (reaxiumUsersDAO == null) {
            reaxiumUsersDAO = new AssociatedUsersDAO(context);
        }
        return reaxiumUsersDAO;
    }

    /**
     * delete all the values from the table reaxium_associated_users
     */
    public void deleteAllValuesFromReaxiumAssociatedUserTable() {
        database = dbHelper.getWritableDatabase();
        database.delete(AssociatedUsersContract.ReaxiumAssociatedUsers.TABLE_NAME, null, null);
    }

    /**
     * fill the table associated user
     *
     * @param associatedUserList
     */
    public Boolean fillAssociatedUsersTable(List<User> associatedUserList) {
        Boolean succcess = Boolean.FALSE;
        try {
            database = dbHelper.getWritableDatabase();
            database.beginTransaction();
            for (User associatedUser : associatedUserList) {
                insertValues = new ContentValues();
                insertValues.put(AssociatedUsersContract.ReaxiumAssociatedUsers.COLUMN_NAME_USER_ID, associatedUser.getUserId());
                insertValues.put(AssociatedUsersContract.ReaxiumAssociatedUsers.COLUMN_NAME_USER_NAME, associatedUser.getFirstName());
                insertValues.put(AssociatedUsersContract.ReaxiumAssociatedUsers.COLUMN_NAME_USER_SECOND_NAME, associatedUser.getSecondName());
                insertValues.put(AssociatedUsersContract.ReaxiumAssociatedUsers.COLUMN_NAME_USER_LAST_NAME, associatedUser.getFirstLastName());
                insertValues.put(AssociatedUsersContract.ReaxiumAssociatedUsers.COLUMN_NAME_USER_BIRTH_DATE, associatedUser.getBirthDate());
                insertValues.put(AssociatedUsersContract.ReaxiumAssociatedUsers.COLUMN_NAME_USER_BUSINESS_NAME, associatedUser.getBusiness().getBusinessName());
                insertValues.put(AssociatedUsersContract.ReaxiumAssociatedUsers.COLUMN_NAME_USER_PHOTO, associatedUser.getPhoto());
                insertValues.put(AssociatedUsersContract.ReaxiumAssociatedUsers.COLUMN_NAME_USER_DOCUMENT_ID, associatedUser.getDocumentId());
                insertValues.put(AssociatedUsersContract.ReaxiumAssociatedUsers.COLUMN_NAME_USER_EMAIL, associatedUser.getEmail());
                database.insert(AssociatedUsersContract.ReaxiumAssociatedUsers.TABLE_NAME, null, insertValues);
            }
            succcess = Boolean.TRUE;
            Log.i(TAG, "Reaxium Associated Users access data successfully stored in db");
            database.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e(TAG, "Error saving the users dataaccess", e);
            database.endTransaction();
        } finally {
            try {
                database.endTransaction();
            }catch (Exception e2){
                Log.e(TAG,"Error cerrando la conexion",e2);
            }
        }
        return succcess;
    }

    /**
     * lookup all associated users belong to the parent
     * @return a list of associated users found in the device
     */
    public List<User> getAssociatedUserInSystem() {
        List<User> associatedUserList = null;
        User user = null;
        Cursor resultSet = null;
        try {
            database = dbHelper.getReadableDatabase();
            String[] projection = {
                    AssociatedUsersContract.ReaxiumAssociatedUsers.COLUMN_NAME_USER_ID,
                    AssociatedUsersContract.ReaxiumAssociatedUsers.COLUMN_NAME_USER_NAME,
                    AssociatedUsersContract.ReaxiumAssociatedUsers.COLUMN_NAME_USER_SECOND_NAME,
                    AssociatedUsersContract.ReaxiumAssociatedUsers.COLUMN_NAME_USER_LAST_NAME,
                    AssociatedUsersContract.ReaxiumAssociatedUsers.COLUMN_NAME_USER_DOCUMENT_ID,
                    AssociatedUsersContract.ReaxiumAssociatedUsers.COLUMN_NAME_USER_PHOTO,
                    AssociatedUsersContract.ReaxiumAssociatedUsers.COLUMN_NAME_USER_EMAIL,
                    AssociatedUsersContract.ReaxiumAssociatedUsers.COLUMN_NAME_USER_BIRTH_DATE,
                    AssociatedUsersContract.ReaxiumAssociatedUsers.COLUMN_NAME_USER_BUSINESS_NAME
            };
            resultSet = database.query(AssociatedUsersContract.ReaxiumAssociatedUsers.TABLE_NAME, projection, null, null, null, null, null);

            if (resultSet.moveToFirst()) {
                associatedUserList = new ArrayList<>();
                while (resultSet.isAfterLast() == false) {
                    user = new User();
                    user.setUserId(resultSet.getLong(resultSet.getColumnIndex(AssociatedUsersContract.ReaxiumAssociatedUsers.COLUMN_NAME_USER_ID)));
                    user.setFirstName(resultSet.getString(resultSet.getColumnIndex(AssociatedUsersContract.ReaxiumAssociatedUsers.COLUMN_NAME_USER_NAME)));
                    user.setSecondName(resultSet.getString(resultSet.getColumnIndex(AssociatedUsersContract.ReaxiumAssociatedUsers.COLUMN_NAME_USER_SECOND_NAME)));
                    user.setFirstLastName(resultSet.getString(resultSet.getColumnIndex(AssociatedUsersContract.ReaxiumAssociatedUsers.COLUMN_NAME_USER_LAST_NAME)));
                    user.setPhoto(resultSet.getString(resultSet.getColumnIndex(AssociatedUsersContract.ReaxiumAssociatedUsers.COLUMN_NAME_USER_PHOTO)));
                    user.setDocumentId(resultSet.getString(resultSet.getColumnIndex(AssociatedUsersContract.ReaxiumAssociatedUsers.COLUMN_NAME_USER_DOCUMENT_ID)));
                    user.setEmail(resultSet.getString(resultSet.getColumnIndex(AssociatedUsersContract.ReaxiumAssociatedUsers.COLUMN_NAME_USER_EMAIL)));
                    user.setBirthDate(resultSet.getString(resultSet.getColumnIndex(AssociatedUsersContract.ReaxiumAssociatedUsers.COLUMN_NAME_USER_BIRTH_DATE)));
                    Business business = new Business();
                    business.setBusinessName(resultSet.getString(resultSet.getColumnIndex(AssociatedUsersContract.ReaxiumAssociatedUsers.COLUMN_NAME_USER_BUSINESS_NAME)));
                    user.setBusiness(business);
                    associatedUserList.add(user);
                    resultSet.moveToNext();
                }
            }
        }catch (Exception e){
            Log.e(TAG,"Error retrieving associated user information from the device db",e);
        }finally {
            if(resultSet != null){
                if(!resultSet.isClosed()){
                    resultSet.close();
                }
            }
        }
        return associatedUserList;
    }


}
