package com.example.eduardoluttinger.reaxiumforparents.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.eduardoluttinger.reaxiumforparents.beans.AccessMessage;
import com.example.eduardoluttinger.reaxiumforparents.beans.Business;
import com.example.eduardoluttinger.reaxiumforparents.beans.ReaxiumDevice;
import com.example.eduardoluttinger.reaxiumforparents.beans.TrafficType;
import com.example.eduardoluttinger.reaxiumforparents.beans.User;
import com.example.eduardoluttinger.reaxiumforparents.global.GGGlobalValues;
import com.example.eduardoluttinger.reaxiumforparents.util.ReaxiumUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Eduardo Luttinger on 21/04/2016.
 * <p/>
 * class in charge of execute selects, inserts and updates operations in the AccessMessage table
 */
public class AccessMessageDAO {

    public static final String TAG = GGGlobalValues.TRACE_ID;
    private SQLiteDatabase database;
    private ReaxiumDbHelper dbHelper;
    private static AccessMessageDAO accessMessageDAO;
    private ContentValues insertValues;

    private AccessMessageDAO(Context context) {
        dbHelper = new ReaxiumDbHelper(context);
    }

    public static AccessMessageDAO getInstance(Context context) {
        if (accessMessageDAO == null) {
            accessMessageDAO = new AccessMessageDAO(context);
        }
        return accessMessageDAO;
    }

    /**
     * delete all the values from the table reaxium_access_message
     */
    public void deleteAllValuesFromReaxiumAccessMessageTable() {
        database = dbHelper.getWritableDatabase();
        database.delete(AccessMessageContract.ReaxiumAccessMessages.TABLE_NAME, null, null);
    }

    /**
     * fill the table AccessMessage
     *
     * @param accessMessageList
     */
    public Boolean fillAccessMessageTable(List<AccessMessage> accessMessageList) {
        Boolean succcess = Boolean.FALSE;
        try {
            database = dbHelper.getWritableDatabase();
            database.beginTransaction();
            for (AccessMessage accessMessage : accessMessageList) {
                insertValues = new ContentValues();
                insertValues.put(AccessMessageContract.ReaxiumAccessMessages.COLUMN_NAME_USER_ID, accessMessage.getUserId());
                insertValues.put(AccessMessageContract.ReaxiumAccessMessages.COLUMN_NAME_DEVICE_ID, accessMessage.getDevice().getDeviceId());
                insertValues.put(AccessMessageContract.ReaxiumAccessMessages.COLUMN_NAME_ACCESS_TYPE, accessMessage.getTrafficType().getTrafficTypeName());
                insertValues.put(AccessMessageContract.ReaxiumAccessMessages.COLUMN_NAME_ACCESS_DATE, accessMessage.getAccessDate());
                insertValues.put(AccessMessageContract.ReaxiumAccessMessages.COLUMN_NAME_ACCESS_INFO, accessMessage.getAccessInfo());
                database.insert(AccessMessageContract.ReaxiumAccessMessages.TABLE_NAME, null, insertValues);
            }
            succcess = Boolean.TRUE;
            Log.i(TAG, "Reaxium Access Message data successfully stored in db");
            database.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e(TAG, "Error saving the access messages", e);
            database.endTransaction();
        } finally {
            try {
                database.endTransaction();
            } catch (Exception e2) {
                Log.e(TAG, "Error cerrando la conexion", e2);
            }
        }
        return succcess;
    }


    /**
     * insert a record on the AccessMessage table
     *
     * @param accessMessage
     */
    public Boolean insertAnAccessMessage(AccessMessage accessMessage) {
        Boolean success = Boolean.FALSE;
        try {
            database = dbHelper.getWritableDatabase();
            insertValues = new ContentValues();
            insertValues.put(AccessMessageContract.ReaxiumAccessMessages.COLUMN_NAME_USER_ID, accessMessage.getUserId());
            insertValues.put(AccessMessageContract.ReaxiumAccessMessages.COLUMN_NAME_DEVICE_ID, accessMessage.getDevice().getDeviceId());
            insertValues.put(AccessMessageContract.ReaxiumAccessMessages.COLUMN_NAME_ACCESS_TYPE, accessMessage.getTrafficType().getTrafficTypeName());
            insertValues.put(AccessMessageContract.ReaxiumAccessMessages.COLUMN_NAME_ACCESS_DATE, accessMessage.getAccessDate());
            String accessInfo = accessMessage.getAccessInfo();
            accessInfo = accessInfo.replace("@Time@", ReaxiumUtil.getFormattedTime(accessMessage.getAccessDate()));
            insertValues.put(AccessMessageContract.ReaxiumAccessMessages.COLUMN_NAME_ACCESS_INFO, accessInfo);
            database.insert(AccessMessageContract.ReaxiumAccessMessages.TABLE_NAME, null, insertValues);
            success = Boolean.TRUE;
            Log.i(TAG, "Reaxium Access Message data successfully stored in db");
        } catch (Exception e) {
            Log.e(TAG, "Error saving the access messages", e);
        }
        return success;
    }


    /**
     * retrieve the access messages of a associated user
     *
     * @return user found
     */
    public List<AccessMessage> getAccessMessagesByUserId(Long userId) {
        List<AccessMessage> accessMessageList = null;
        AccessMessage accessMessage = null;
        Cursor resultSet = null;
        ReaxiumDevice device = null;
        TrafficType trafficType = null;
        try {
            database = dbHelper.getReadableDatabase();
            String[] projection = {
                    AccessMessageContract.ReaxiumAccessMessages.COLUMN_NAME_USER_ID,
                    AccessMessageContract.ReaxiumAccessMessages.COLUMN_NAME_DEVICE_ID,
                    AccessMessageContract.ReaxiumAccessMessages.COLUMN_NAME_ACCESS_DATE,
                    AccessMessageContract.ReaxiumAccessMessages.COLUMN_NAME_ACCESS_INFO,
                    AccessMessageContract.ReaxiumAccessMessages.COLUMN_NAME_ACCESS_TYPE
            };
            String selection = AccessMessageContract.ReaxiumAccessMessages.COLUMN_NAME_USER_ID+"=?";
            String[] arguments = {""+userId.longValue()};
            resultSet = database.query(AccessMessageContract.ReaxiumAccessMessages.TABLE_NAME, projection, selection, arguments, null, null, AccessMessageContract.ReaxiumAccessMessages.COLUMN_NAME_ACCESS_DATE + " DESC");

            if (resultSet.moveToFirst()) {
                accessMessageList = new ArrayList<>();
                while (resultSet.isAfterLast() == false) {
                    accessMessage = new AccessMessage();

                    accessMessage.setUserId(resultSet.getLong(resultSet.getColumnIndex(AccessMessageContract.ReaxiumAccessMessages.COLUMN_NAME_USER_ID)));

                    device = new ReaxiumDevice();
                    device.setDeviceId(resultSet.getLong(resultSet.getColumnIndex(AccessMessageContract.ReaxiumAccessMessages.COLUMN_NAME_DEVICE_ID)));
                    accessMessage.setDevice(device);

                    accessMessage.setAccessDate(resultSet.getString(resultSet.getColumnIndex(AccessMessageContract.ReaxiumAccessMessages.COLUMN_NAME_ACCESS_DATE)));

                    accessMessage.setAccessInfo(resultSet.getString(resultSet.getColumnIndex(AccessMessageContract.ReaxiumAccessMessages.COLUMN_NAME_ACCESS_INFO)));

                    trafficType = new TrafficType();
                    trafficType.setTrafficTypeName(resultSet.getString(resultSet.getColumnIndex(AccessMessageContract.ReaxiumAccessMessages.COLUMN_NAME_ACCESS_TYPE)));
                    accessMessage.setTrafficType(trafficType);

                    accessMessageList.add(accessMessage);

                    resultSet.moveToNext();
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error retrieving access messages information from the device db", e);
        } finally {
            if (resultSet != null) {
                if (!resultSet.isClosed()) {
                    resultSet.close();
                }
            }
        }
        return accessMessageList;
    }



    /**
     * retrieve all the access messages in the system
     *
     * @return user found
     */
    public List<AccessMessage> getAccessMessagesInSystem() {
        List<AccessMessage> accessMessageList = null;
        AccessMessage accessMessage = null;
        Cursor resultSet = null;
        ReaxiumDevice device = null;
        TrafficType trafficType = null;
        try {
            database = dbHelper.getReadableDatabase();
            String[] projection = {
                    AccessMessageContract.ReaxiumAccessMessages.COLUMN_NAME_USER_ID,
                    AccessMessageContract.ReaxiumAccessMessages.COLUMN_NAME_DEVICE_ID,
                    AccessMessageContract.ReaxiumAccessMessages.COLUMN_NAME_ACCESS_DATE,
                    AccessMessageContract.ReaxiumAccessMessages.COLUMN_NAME_ACCESS_INFO,
                    AccessMessageContract.ReaxiumAccessMessages.COLUMN_NAME_ACCESS_TYPE
            };
            resultSet = database.query(AccessMessageContract.ReaxiumAccessMessages.TABLE_NAME, projection, null, null, null, null, AccessMessageContract.ReaxiumAccessMessages.COLUMN_NAME_ACCESS_DATE + " DESC");

            if (resultSet.moveToFirst()) {
                accessMessageList = new ArrayList<>();
                while (resultSet.isAfterLast() == false) {
                    accessMessage = new AccessMessage();

                    accessMessage.setUserId(resultSet.getLong(resultSet.getColumnIndex(AccessMessageContract.ReaxiumAccessMessages.COLUMN_NAME_USER_ID)));

                    device = new ReaxiumDevice();
                    device.setDeviceId(resultSet.getLong(resultSet.getColumnIndex(AccessMessageContract.ReaxiumAccessMessages.COLUMN_NAME_DEVICE_ID)));
                    accessMessage.setDevice(device);

                    accessMessage.setAccessDate(resultSet.getString(resultSet.getColumnIndex(AccessMessageContract.ReaxiumAccessMessages.COLUMN_NAME_ACCESS_DATE)));
                    accessMessage.setAccessInfo(resultSet.getString(resultSet.getColumnIndex(AccessMessageContract.ReaxiumAccessMessages.COLUMN_NAME_ACCESS_INFO)));

                    trafficType = new TrafficType();
                    trafficType.setTrafficTypeName(resultSet.getString(resultSet.getColumnIndex(AccessMessageContract.ReaxiumAccessMessages.COLUMN_NAME_ACCESS_INFO)));
                    accessMessage.setTrafficType(trafficType);

                    accessMessageList.add(accessMessage);

                    resultSet.moveToNext();
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "Error retrieving access messages information from the device db", e);
        } finally {
            if (resultSet != null) {
                if (!resultSet.isClosed()) {
                    resultSet.close();
                }
            }
        }
        return accessMessageList;
    }


}
