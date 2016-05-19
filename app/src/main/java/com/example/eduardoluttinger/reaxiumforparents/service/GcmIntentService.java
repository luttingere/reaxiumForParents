package com.example.eduardoluttinger.reaxiumforparents.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.eduardoluttinger.reaxiumforparents.App;
import com.example.eduardoluttinger.reaxiumforparents.activity.AdminActivity;
import com.example.eduardoluttinger.reaxiumforparents.beans.AccessMessage;
import com.example.eduardoluttinger.reaxiumforparents.beans.User;
import com.example.eduardoluttinger.reaxiumforparents.database.AccessMessageDAO;
import com.example.eduardoluttinger.reaxiumforparents.fragment.AccessInformationFragment;
import com.example.eduardoluttinger.reaxiumforparents.global.GGGlobalValues;
import com.example.eduardoluttinger.reaxiumforparents.util.NotificationMessagePlayerSingleton;
import com.google.gson.Gson;

/**
 * Created by Eduardo Luttinger
 */
public class GcmIntentService extends IntentService {

    /**
     * Descripcion de la clase para propositos de log
     */
    private static final String TAG = GGGlobalValues.TRACE_ID;

    /**
     * BroadCastSender between this service and any activity
     */
    private LocalBroadcastManager mLocalBroadcastManager;

    /**
     * Constructor
     */
    public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        initBroadCast();
        handleTheNotification(intent.getExtras());
    }

    /**
     * Initialize the broadcast manager
     */
    private void initBroadCast() {
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(GcmIntentService.this);
    }

    /**
     * Handle all Push Notification Operations
     *
     * @param pushParameters (Map of parameters)
     */
    private void handleTheNotification(Bundle pushParameters) {
        try {
            //convert the message into an AccessMessage Object
            Gson gson = new Gson();
            AccessMessage accessMessage = gson.fromJson(pushParameters.getString("message"), AccessMessage.class);

            //transform the traffic info
            String accessInfoToBeTreated = accessMessage.getAccessInfo();
            String accessInfoToStore = accessInfoToBeTreated;
            accessInfoToStore = accessInfoToStore.replace("#","");
            accessMessage.setAccessInfo(accessInfoToStore);
            //store the accessMessage
            AccessMessageDAO accessMessageDAO = AccessMessageDAO.getInstance(this);

            accessMessageDAO.insertAnAccessMessage(accessMessage);

            if (accessInfoToBeTreated.indexOf("#") >= 0) {
                accessInfoToBeTreated = accessInfoToBeTreated.substring(0, accessInfoToBeTreated.indexOf("#")-1);
            }
            NotificationMessagePlayerSingleton.getInstance(this).initRingTone();

            if (App.IS_THE_USER_ON_OUR_APP) {
                if (App.FRAGMENT_SELECTED instanceof AccessInformationFragment) {

                    ((AccessInformationFragment) App.FRAGMENT_SELECTED).refreshList();

                } else {
                    //Load a top bar notification
                    Bundle parameters = new Bundle();
                    parameters.putString(GGGlobalValues.RUN_THIS_FRAGMENT, GGGlobalValues.ACCESS_INFORMATION_FRAGMENT);
                    User user = new User();
                    user.setUserId(accessMessage.getUserId());
                    parameters.putSerializable(GGGlobalValues.WITH_THESE_ARGUMENTS, user);
                    PushUtil.loadAnAppNotification(this, AdminActivity.class, parameters, accessMessage.getAccessMessageId().intValue(), accessInfoToBeTreated);
                }
            } else {
                //Load a top bar notification
                Bundle parameters = new Bundle();
                parameters.putString(GGGlobalValues.RUN_THIS_FRAGMENT, GGGlobalValues.ACCESS_INFORMATION_FRAGMENT);
                User user = new User();
                user.setUserId(accessMessage.getUserId());
                parameters.putSerializable(GGGlobalValues.WITH_THESE_ARGUMENTS, user);
                PushUtil.loadAnAppNotification(this, AdminActivity.class, parameters, accessMessage.getAccessMessageId().intValue(), accessInfoToBeTreated);
            }

        } catch (Exception e) {
            Log.e(TAG, "Error manejando la notificacion PUSH", e);
        }
    }


}