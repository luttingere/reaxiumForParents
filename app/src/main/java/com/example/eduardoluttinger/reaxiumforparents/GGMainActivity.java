package com.example.eduardoluttinger.reaxiumforparents;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.TextView;

import com.example.eduardoluttinger.reaxiumforparents.exceptions.NoMainLayoutException;
import com.example.eduardoluttinger.reaxiumforparents.global.GGGlobalValues;
import com.example.eduardoluttinger.reaxiumforparents.util.GGProgressDialog;
import com.example.eduardoluttinger.reaxiumforparents.util.SharedPreferenceUtil;

import java.util.List;


/**
 * Created by Eduardo Luttinger G&G on 11/04/2016.
 */
public abstract class GGMainActivity extends AppCompatActivity {


    /**
     * trace id for LOG proposal
     */
    protected static final String TAG = GGGlobalValues.TRACE_ID;


    /**
     * activity toolbar reference
     */
    private Toolbar mToolbar;

    /**
     * activity toolbar title text view
     */
    private TextView mToolBarTitle;

    /**
     * activity progress dialog
     */
    protected GGProgressDialog mProgressDialog;

    /**
     * Mandatory!!
     * <p/>
     * <p>override this method to set the child activity layout</p>
     *
     * @return Ej: R.layout.activity_layout
     */
    abstract protected Integer getMainLayout();

    /**
     * Mandatory
     * <p/>
     * <p>override this method and initialize all ur view components</p>
     */
    abstract protected void setViews();

    /**
     * Mandatory
     * override this method to set all fragment view components events
     */
    abstract protected void setViewsEvents();

    /**
     * No mandatory.
     * <p/>
     * <p>Override this method to set the child activity fragment</p>
     *
     * @return null or any fragment that inherits from ApplicationFragment
     */
    abstract protected GGMainFragment getMainFragment();


    /**
     * No mandatory
     * <p/>
     * <p>override this method to perform the child activity main toolbar </p>
     *
     * @return Ej: R.id.main_toolbar
     */
    abstract protected Integer getMainToolbarId();




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            if (getMainLayout() != null) {

                //load the activity layout
                setContentView(getMainLayout());

                //Init the child activity progress dialog
                mProgressDialog = new GGProgressDialog(this);
                mProgressDialog.setMessage(getString(R.string.progress_dialog_message));
                mProgressDialog.setCancelable(Boolean.FALSE);

                //Init all  child activity view components
                configureToolbar();
                setViews();
                setViewsEvents();


                //set child activity Fragment
                if (getMainFragment() != null) {
                    runMyFragment(getMainFragment(),null,R.id.action_home);
                }

            } else {
                throw new NoMainLayoutException(getString(R.string.no_main_layout_exception));
            }
        } catch (Exception e) {
            Log.e(TAG, getString(R.string.simple_exception_message), e);
            finish();
        }
    }

    /**
     * set the toolbar values and items
     */
    private void configureToolbar(){
        if (getMainToolbarId() != null) {
            mToolbar = (Toolbar) findViewById(getMainToolbarId());
            setSupportActionBar(mToolbar);
            mToolBarTitle = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        }
    }

    /**
     * method overridden to perform Chrona Fragment onBackPressed method.
     */
    @Override
    public void onBackPressed() {
        if (getMainFragment() != null) {
            GGMainFragment fragment = getLastFragment();
            if (fragment != null) {
                Boolean stopNativeBackPressed = fragment.onBackPressed();
                if (!stopNativeBackPressed) {
                    int count = getSupportFragmentManager().getBackStackEntryCount();
                    if (count == 1) {
                        finish();
                    } else {
                        super.onBackPressed();
                    }
                }
            } else {
                super.onBackPressed();
            }
        } else {
            super.onBackPressed();
        }
    }


    /**
     * Replace a Fragment into the activity fragment container
     *
     * @param fragment
     */
    public abstract void runMyFragment(GGMainFragment fragment, Bundle params, int drawerId);


    /**
     * @return the last fragment placed in the activity
     */
    public GGMainFragment getLastFragment() {
        FragmentManager fm = getSupportFragmentManager();
        GGMainFragment fragment = null;
        if (fm != null) {
            List<Fragment> fragments = fm.getFragments();
            for (int i = fragments.size() - 1; i >= 0; i--) {
                fragment = (GGMainFragment) fragments.get(i);
                if (fragment != null) {
                    break;
                }
            }
        }
        return fragment;
    }


    /**
     * show a progress dialog with a custom message
     *
     * @param message
     */
    public void showProgressDialog(String message) {
        if (mProgressDialog != null) {
            if(!mProgressDialog.isShowing()){
                mProgressDialog.setMessage(message);
                mProgressDialog.show();
            }
        }
    }

    /**
     * hide the progress dialog
     */
    public void dismissProgressDialog() {
        if (mProgressDialog != null) {
            if (mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        }
    }

    /**
     * change the message of the progress dialog
     * @param message
     */
    public void changeProgressDialogMessage(String message){
        if (mProgressDialog != null) {
            if (mProgressDialog.isShowing()) {
                mProgressDialog.setMessage(message);
            }
        }
    }


    /**
     * Android Cache Storage Manager object reference
     *
     * @return SharedPreferenceUtil Singleton Instance
     */
    public SharedPreferenceUtil getSharedPreferences() {
        return SharedPreferenceUtil.getInstance(this);
    }

    /**
     * Toolbar view reference
     * @return
     */
    public Toolbar getToolbar(){
        return mToolbar;
    }

    /**
     * change the toolbar title for each fragment placed
     * @param title
     */
    public void setToolBarTitle(int title){
        mToolBarTitle.setText(title);
    }



}
