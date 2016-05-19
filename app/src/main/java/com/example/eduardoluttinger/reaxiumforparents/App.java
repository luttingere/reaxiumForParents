package com.example.eduardoluttinger.reaxiumforparents;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.example.eduardoluttinger.reaxiumforparents.activity.AdminActivity;
import com.example.eduardoluttinger.reaxiumforparents.database.ReaxiumDbHelper;
import com.example.eduardoluttinger.reaxiumforparents.fragment.AccessInformationFragment;
import com.example.eduardoluttinger.reaxiumforparents.fragment.AssociateUsersFragment;
import com.example.eduardoluttinger.reaxiumforparents.global.GGGlobalValues;
import com.crashlytics.android.Crashlytics;
import com.example.eduardoluttinger.reaxiumforparents.service.PushUtil;
import com.example.eduardoluttinger.reaxiumforparents.util.GGUtil;

import io.fabric.sdk.android.Fabric;


/**
 * Created by Eduardo Lutttinger on 11/04/2016.
 */
public class App extends MultiDexApplication implements Application.ActivityLifecycleCallbacks {

    private static final String TAG  = GGGlobalValues.TRACE_ID;
    private static Context mContext;
    private boolean isDevelopment = Boolean.TRUE;
    public static Boolean IS_THE_USER_ON_OUR_APP = Boolean.FALSE;
    public static GGMainFragment FRAGMENT_SELECTED;


    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        mContext = getContext();
        registerPushNotification();
        registerActivityLifecycleCallbacks(this);
        initializeDB();
    }

    public static Context getContext() {
        return mContext;
    }

    private void registerPushNotification(){
        Log.i(TAG, "registering for push notifications");
        if (PushUtil.checkGooglePlayServices(this)) {
            Log.i(TAG,"Google play is working fine");
            if(!PushUtil.isTheDeviceRegisteredForPushNotification(this)){
                PushUtil.registerInBackGround(this);
            }else{
                String registrationID = PushUtil.getRegistrationId(this);
                Log.i(TAG,"registration ID: "+registrationID);
            }
        }else{
            GGUtil.showAToast(this,"Google play its not working on the device");
            Log.e(TAG,"Google play its not working on the device");
        }
    }

    private void initializeDB(){
        ReaxiumDbHelper dbHelper = new ReaxiumDbHelper(this);
        dbHelper.getWritableDatabase();
        dbHelper.close();
    }


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        Log.d(TAG,"App is Active");
        IS_THE_USER_ON_OUR_APP = Boolean.TRUE;


    }

    @Override
    public void onActivityPaused(Activity activity) {
        Log.d(TAG,"App is Inactive");
        IS_THE_USER_ON_OUR_APP = Boolean.FALSE;

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
