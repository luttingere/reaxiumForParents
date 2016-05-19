package com.example.eduardoluttinger.reaxiumforparents.util;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import com.example.eduardoluttinger.reaxiumforparents.R;


/**
 * Created by Eduardo Luttinger on 07/12/2015.
 */
public class NotificationMessagePlayerSingleton {

    private MediaPlayer mediaPlayer;

    private static NotificationMessagePlayerSingleton mediaPlayerSingleton;

    private Context mContext;

    private NotificationMessagePlayerSingleton(Context context){
        mContext = context;
        mediaPlayer = MediaPlayer.create(context, R.raw.short_notice);
        mediaPlayer.setLooping(Boolean.FALSE);
        mediaPlayer.setVolume(100,100);
    }
    public static NotificationMessagePlayerSingleton getInstance(Context context){
        if(mediaPlayerSingleton == null){
            mediaPlayerSingleton = new NotificationMessagePlayerSingleton(context);
        }
        return mediaPlayerSingleton;
    }

    public void initRingTone(){
        if(!mediaPlayer.isPlaying()){
            mediaPlayer.start();
        }
    }

    public void stopRingTone(){
        if(mediaPlayer != null && mediaPlayer.isPlaying()){
            mediaPlayer.stop();
            mediaPlayer.reset();
            try {
                mediaPlayer = MediaPlayer.create(mContext, R.raw.short_notice);
                mediaPlayer.setLooping(Boolean.TRUE);
                mediaPlayer.prepare();
                mediaPlayer.setVolume(100,100);
            }catch (Exception e){
                Log.e("MD_SINGLETON","",e);
            }
        }
    }


}
