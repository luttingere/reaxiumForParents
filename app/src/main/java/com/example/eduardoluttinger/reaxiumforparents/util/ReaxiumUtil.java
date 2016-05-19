package com.example.eduardoluttinger.reaxiumforparents.util;

import android.util.Log;

import com.example.eduardoluttinger.reaxiumforparents.global.GGGlobalValues;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Eduardo Luttinger on 17/05/2016.
 */
public class ReaxiumUtil {

    private static final String TAG = GGGlobalValues.TRACE_ID;

    public static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("hh:mm:ss a");


    public static String getFormattedTime(String date){
        String formattedTime = "";
        try {
            Date dateToFormat = DATE_FORMAT.parse(date);
            formattedTime = TIME_FORMAT.format(dateToFormat);
        }catch(Exception e){
            Log.i(TAG,"Error transformando la fecha "+date+ " al formato del sistema",e);
        }
        return formattedTime;
    }

}
