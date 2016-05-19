package com.example.eduardoluttinger.reaxiumforparents.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.eduardoluttinger.reaxiumforparents.R;

/**
 * Created by Eduardo Luttinger on 10/05/2016.
 */
public class AssociatedUserHolder extends  RecyclerView.ViewHolder {

    public ImageView userPhoto;
    public TextView userName;
    public TextView userDocumentId;
//    public TextView busNumber;
//    public TextView stopName;
    public TextView schoolName;
    public RelativeLayout accessInfoButton;
    public RelativeLayout trackUserButton;
    public ProgressBar userPhotoLoader;

    public AssociatedUserHolder(View itemView) {
        super(itemView);
        setViews(itemView);
    }

    private void setViews(View view){
        userPhoto = (ImageView) view.findViewById(R.id.user_photo);
        userName = (TextView) view.findViewById(R.id.student_name);
        userDocumentId = (TextView) view.findViewById(R.id.student_id);
        userPhotoLoader = (ProgressBar) view.findViewById(R.id.user_image_loader);
//        busNumber = (TextView) view.findViewById(R.id.bus_number);
//        stopName = (TextView) view.findViewById(R.id.stop_bus_number);
        schoolName = (TextView) view.findViewById(R.id.school_name);
        trackUserButton = (RelativeLayout) view.findViewById(R.id.tracking_button);
        accessInfoButton = (RelativeLayout) view.findViewById(R.id.access_info_button);
    }

}
