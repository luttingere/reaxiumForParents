package com.example.eduardoluttinger.reaxiumforparents.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.eduardoluttinger.reaxiumforparents.App;
import com.example.eduardoluttinger.reaxiumforparents.GGMainActivity;
import com.example.eduardoluttinger.reaxiumforparents.GGMainFragment;
import com.example.eduardoluttinger.reaxiumforparents.R;
import com.example.eduardoluttinger.reaxiumforparents.beans.User;
import com.example.eduardoluttinger.reaxiumforparents.fragment.AssociateUsersFragment;
import com.example.eduardoluttinger.reaxiumforparents.global.GGGlobalValues;
import com.example.eduardoluttinger.reaxiumforparents.util.GGUtil;
import com.example.eduardoluttinger.reaxiumforparents.util.SharedPreferenceUtil;


/**
 * Created by Eduardo Luttinger on 15/04/2016.
 */
public class AdminActivity extends GGMainActivity {

    /**
     * Android utility layout to handle menus
     */
    private DrawerLayout mDrawerLayout;
    /**
     * Android menu drawer
     */
    private NavigationView mMenuDrawer;

    /**
     * fragment placed in the screen
     */
    private GGMainFragment mFragmentInScreen;

    /**
     * back button
     */
    private LinearLayout navigationBack;

    private SharedPreferenceUtil sharedPreferenceUtil;

    @Override
    protected Integer getMainLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    protected void setViews() {
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(this);
        navigationBack = (LinearLayout) findViewById(R.id.navigation_back);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.admin_menu_drawer);
        mMenuDrawer = (NavigationView) findViewById(R.id.nvView);
        View header = getLayoutInflater().inflate(R.layout.admin_menu_drawer_header, null);
        if(sharedPreferenceUtil.getString(GGGlobalValues.USER_FULL_NAME_IN_SESSION) != null){
            ((TextView)header.findViewById(R.id.user_full_name)).setText(sharedPreferenceUtil.getString(GGGlobalValues.USER_FULL_NAME_IN_SESSION));
        }else{
            ((TextView)header.findViewById(R.id.user_full_name)).setText("Parent Name");
        }
        mMenuDrawer.addHeaderView(header);
        mMenuDrawer.getMenu().findItem(R.id.action_home).setChecked(Boolean.TRUE);
    }

    @Override
    protected void setViewsEvents() {
        //adding the select item event to the drawer menu
        mMenuDrawer.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });

        getToolbar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
                    mDrawerLayout.closeDrawer(Gravity.RIGHT);
                } else {
                    mDrawerLayout.openDrawer(Gravity.RIGHT);
                }
            }
        });

        navigationBack.setVisibility(View.INVISIBLE);
        navigationBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getMainFragment() != null) {
                    getMainFragment().onBackPressed();
                }
            }
        });
    }

    public void hideBackButton(){
        navigationBack.setVisibility(View.INVISIBLE);
    }
    public void showBackButton(){
        navigationBack.setVisibility(View.VISIBLE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        int menuToUse = R.menu.menu_slider;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(menuToUse, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void runMyFragment(GGMainFragment fragment, Bundle params,int drawerId) {
        App.FRAGMENT_SELECTED = fragment;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if(fragment.getArguments() == null){
            fragment.setArguments(params);
        }
        setToolBarTitle(fragment.getToolbarTitle());
        mMenuDrawer.getMenu().findItem(drawerId).setChecked(Boolean.TRUE);
        transaction.replace(GGGlobalValues.FRAGMENT_CONTAINER, fragment).addToBackStack(null).commit();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.END);
                return true;
            case R.id.action_drawer:
                mDrawerLayout.openDrawer(GravityCompat.END);
                GGUtil.hideKeyboard(this);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public GGMainFragment getMainFragment() {
        mFragmentInScreen = (GGMainFragment)getSupportFragmentManager().findFragmentById(GGGlobalValues.FRAGMENT_CONTAINER);
        if(mFragmentInScreen == null){
            if(getIntent()!= null && getIntent().getExtras() != null){
                Bundle parameters = getIntent().getExtras();
                String fragmentName = parameters.getString(GGGlobalValues.RUN_THIS_FRAGMENT);
                try {
                    mFragmentInScreen = (GGMainFragment) Class.forName(GGGlobalValues.FRAGMENT_PACKAGE+fragmentName).newInstance();
                    Bundle arguments = new Bundle();
                    if(mFragmentInScreen.getArguments() == null){
                        mFragmentInScreen.setArguments(arguments);
                    }
                    mFragmentInScreen.getArguments().putSerializable(GGGlobalValues.USER_IN_A_BUNDLE,parameters.getSerializable(GGGlobalValues.WITH_THESE_ARGUMENTS));
                }catch (Exception e){
                    mFragmentInScreen =new AssociateUsersFragment();
                    Log.e(TAG,"Error lanzando el fragmento con reflection. Fragment Name:  "+fragmentName);
                }
            }else{
                mFragmentInScreen =new AssociateUsersFragment();
            }
        }
        return mFragmentInScreen;
    }

    @Override
    protected Integer getMainToolbarId() {
        return R.id.toolbar;
    }

    /**
     * actions for each click un the items placed on drawer menu
     *
     * @param menuItem
     */
    private void selectDrawerItem(MenuItem menuItem) {
        if (!menuItem.isChecked()) {
            switch (menuItem.getItemId()) {
                case R.id.action_home:
                    runMyFragment(new AssociateUsersFragment(),null,menuItem.getItemId());
                    break;
                case R.id.action_logout:
                    sharedPreferenceUtil.removeValue(GGGlobalValues.USER_ID_IN_SESSION);
                    sharedPreferenceUtil.removeValue(GGGlobalValues.USER_FULL_NAME_IN_SESSION);
                    mDrawerLayout.closeDrawer(GravityCompat.END);
                    Intent goToLoginPage = new Intent(this, LoginActivity.class);
                    startActivity(goToLoginPage);
                    finish();
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Fragment myFragemnt = getSupportFragmentManager().findFragmentById(GGGlobalValues.FRAGMENT_CONTAINER);
        myFragemnt.onActivityResult(requestCode, resultCode, data);
    }
}
