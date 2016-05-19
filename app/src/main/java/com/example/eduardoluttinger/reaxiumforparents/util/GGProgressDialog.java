package com.example.eduardoluttinger.reaxiumforparents.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.eduardoluttinger.reaxiumforparents.R;
import com.example.eduardoluttinger.reaxiumforparents.global.GGGlobalValues;

import at.grabner.circleprogress.AnimationState;
import at.grabner.circleprogress.AnimationStateChangedListener;
import at.grabner.circleprogress.CircleProgressView;
import at.grabner.circleprogress.TextMode;

/**
 * Created by Eduardo Luttinger on 16/05/2016.
 */
public class GGProgressDialog extends ProgressDialog {

    private static final String TAG = GGGlobalValues.TRACE_ID;
    private CircleProgressView mCircleView;
    private Boolean mShowUnit = true;
    private String message;


    public GGProgressDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gg_progress_dialog);
        mCircleView = (CircleProgressView) findViewById(R.id.circleView);
        mCircleView.setUnitVisible(Boolean.FALSE);
        mCircleView.setUnitColor(getContext().getResources().getColor(android.R.color.transparent));
        mCircleView.setTextMode(TextMode.TEXT);
        mCircleView.setShowTextWhileSpinning(true);
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public void setMessage(CharSequence message) {
        super.setMessage(message);
    }

    @Override
    public void show() {
        super.show();
        if(mCircleView != null){
            mCircleView.spin();
        }
    }

    @Override
    public void dismiss() {
        if(mCircleView != null){
            mCircleView.stopSpinning();
        }
        super.dismiss();
    }
}
