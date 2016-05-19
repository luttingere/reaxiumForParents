package com.example.eduardoluttinger.reaxiumforparents.fragment;


import android.content.Intent;
import android.location.Location;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;

import com.example.eduardoluttinger.reaxiumforparents.GGMainFragment;
import com.example.eduardoluttinger.reaxiumforparents.R;
import com.example.eduardoluttinger.reaxiumforparents.activity.AdminActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;


/**
 * Created by Eduardo Luttinger on 09/05/2016.
 */
public class TrackUserFragment extends GGMainFragment {

    private float mRouteMapZoom = 16.0f;
    private LatLng reaxiumDeviceLocation;
    private GoogleMap mRouteMap;
    private SupportMapFragment mapFragment;
    private GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
        @Override
        public void onMyLocationChange (Location location) {
            Log.i("TEST", "Location changed");
            reaxiumDeviceLocation = new LatLng(location.getLatitude(), location.getLongitude());
            mRouteMap.animateCamera(CameraUpdateFactory.newLatLngZoom(reaxiumDeviceLocation, mRouteMapZoom));
        }
    };


    @Override
    public String getMyTag() {
        return TrackUserFragment.class.getName();
    }

    @Override
    public Integer getToolbarTitle() {
        return R.string.track_the_user;
    }

    @Override
    protected Integer getFragmentLayout() {
        return R.layout.track_user_activity;
    }

    @Override
    public Boolean onBackPressed() {
        Intent goToMainActivity = new Intent(getActivity(),AdminActivity.class);
        startActivity(goToMainActivity);
        getActivity().finish();
        return Boolean.TRUE;
    }

    @Override
    protected void setViews(View view) {
        FragmentManager fragmentManager = getChildFragmentManager();
        mapFragment = (SupportMapFragment) fragmentManager.findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mRouteMap = googleMap;
                mRouteMap.setMyLocationEnabled(Boolean.TRUE);
                mRouteMap.setOnMyLocationChangeListener(myLocationChangeListener);
            }
        });
        ((AdminActivity) getActivity()).showBackButton();
    }

    @Override
    protected void setViewsEvents() {

    }



    @Override
    public void clearAllViewComponents() {

    }
}
