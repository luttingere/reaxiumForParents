package com.example.eduardoluttinger.reaxiumforparents.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.eduardoluttinger.reaxiumforparents.R;

/**
 * Created by Eduardo Luttinger on 10/05/2016.
 */
public class AccessInfoHolder extends  RecyclerView.ViewHolder {


    public TextView infoAccess;


    public AccessInfoHolder(View itemView) {
        super(itemView);
        setViews(itemView);
    }

    private void setViews(View view){
        infoAccess = (TextView) view.findViewById(R.id.access_info_text);
    }

}
