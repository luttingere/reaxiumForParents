package com.example.eduardoluttinger.reaxiumforparents.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.eduardoluttinger.reaxiumforparents.GGMainActivity;
import com.example.eduardoluttinger.reaxiumforparents.GGMainFragment;
import com.example.eduardoluttinger.reaxiumforparents.R;
import com.example.eduardoluttinger.reaxiumforparents.beans.ApiResponse;
import com.example.eduardoluttinger.reaxiumforparents.beans.ParentInfo;
import com.example.eduardoluttinger.reaxiumforparents.beans.User;
import com.example.eduardoluttinger.reaxiumforparents.database.AssociatedUsersDAO;
import com.example.eduardoluttinger.reaxiumforparents.global.APPEnvironment;
import com.example.eduardoluttinger.reaxiumforparents.global.GGGlobalValues;
import com.example.eduardoluttinger.reaxiumforparents.service.PushUtil;
import com.example.eduardoluttinger.reaxiumforparents.util.GGUtil;
import com.example.eduardoluttinger.reaxiumforparents.util.JsonObjectRequestUtil;
import com.example.eduardoluttinger.reaxiumforparents.util.JsonUtil;
import com.example.eduardoluttinger.reaxiumforparents.util.MySingletonUtil;
import com.example.eduardoluttinger.reaxiumforparents.util.SharedPreferenceUtil;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 * Created by Eduardo Luttinger on 10/05/2016.
 */
public class LoginActivity extends GGMainActivity {

    private EditText userNameInput;
    private EditText passwordInput;
    private RelativeLayout loginButton;
    private SharedPreferenceUtil sharedPreferenceUtil;
    AssociatedUsersDAO associatedUsersDAO;

    @Override
    protected Integer getMainLayout() {
        return R.layout.login_activity;
    }

    @Override
    protected void setViews() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        associatedUsersDAO = AssociatedUsersDAO.getInstance(this);
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(this);
        userNameInput = (EditText) findViewById(R.id.username_input);
        passwordInput = (EditText) findViewById(R.id.password_input);
        loginButton = (RelativeLayout) findViewById(R.id.login_container);

    }

    @Override
    protected void setViewsEvents() {
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    @Override
    protected GGMainFragment getMainFragment() {
        return null;
    }

    @Override
    protected Integer getMainToolbarId() {
        return null;
    }

    @Override
    public void runMyFragment(GGMainFragment fragment, Bundle params, int drawerId) {

    }


    /**
     * Make a login to the system
     */
    private void login() {
        if (GGUtil.isNetworkAvailable(this)) {
            if (!"".equals(userNameInput.getText().toString().trim()) && !"".equals(passwordInput.getText().toString().trim())) {
                showProgressDialog(getString(R.string.progress_dialog_message));
                Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Type responseType = new TypeToken<ApiResponse<ParentInfo>>() {}.getType();
                            ApiResponse<ParentInfo> apiResponse = JsonUtil.getEntityFromJSON(response, responseType);
                            if (apiResponse.getReaxiumResponse().getCode() == GGGlobalValues.SUCCESSFUL_API_RESPONSE_CODE) {
                                if (apiResponse.getReaxiumResponse().getObject() != null) {
                                    if (!apiResponse.getReaxiumResponse().getObject().isEmpty()) {
                                        if (apiResponse.getReaxiumResponse().getObject().get(0).getParentInformation() != null) {
                                            User user = apiResponse.getReaxiumResponse().getObject().get(0).getParentInformation();
                                            if (user.getUserType().getUserTypeName().equalsIgnoreCase("ADMINISTRATOR") ||
                                                    user.getUserType().getUserTypeName().equalsIgnoreCase("STAKEHOLDER")) {
                                                storeUserLogin(user);
                                                associatedUsersDAO.deleteAllValuesFromReaxiumAssociatedUserTable();
                                                associatedUsersDAO.fillAssociatedUsersTable(apiResponse.getReaxiumResponse().getObject().get(0).getParentRelationship());
                                                dismissProgressDialog();
                                                Intent goToMainScreen = new Intent(LoginActivity.this, AdminActivity.class);
                                                startActivity(goToMainScreen);
                                            } else {
                                                dismissProgressDialog();
                                                GGUtil.showAToast(LoginActivity.this, "Invalid User Account");
                                            }
                                        } else {
                                            dismissProgressDialog();
                                            GGUtil.showAToast(LoginActivity.this, "No parent information available in server");
                                        }
                                    } else {
                                        dismissProgressDialog();
                                        GGUtil.showAToast(LoginActivity.this, "No parent information available in server(1)");
                                    }
                                } else {
                                    dismissProgressDialog();
                                    GGUtil.showAToast(LoginActivity.this, "No parent information available in server(2)");
                                }
                            } else {
                                dismissProgressDialog();
                                GGUtil.showAToast(LoginActivity.this, apiResponse.getReaxiumResponse().getMessage());
                            }
                        } catch (Exception e) {
                            dismissProgressDialog();
                            GGUtil.showAToast(LoginActivity.this, "Internal server error, "+e.getMessage());
                            Log.e(TAG,"Error obteniendo la informacion del parent",e);
                        }
                    }
                };
                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dismissProgressDialog();
                        GGUtil.showAToast(LoginActivity.this, R.string.bad_connection_message);
                    }
                };
                JsonObjectRequestUtil jsonObjectRequest = new JsonObjectRequestUtil(Request.Method.POST, APPEnvironment.createURL(GGGlobalValues.VALIDATE_ACCESS), loadLoginParameters(), responseListener, errorListener);
                jsonObjectRequest.setShouldCache(false);
                MySingletonUtil.getInstance(LoginActivity.this).addToRequestQueue(jsonObjectRequest);
            } else {
                GGUtil.showAToast(this, "Please fill username and password input fields");
            }
        } else {
            GGUtil.showAToast(this, R.string.no_network_available);
        }
    }

    private JSONObject loadLoginParameters() {
        JSONObject loginParams = null;
        try {
            loginParams = new JSONObject();
            JSONObject reaxiumParameters = new JSONObject();
            JSONObject userAccessData = new JSONObject();
            userAccessData.put("user_login_name", userNameInput.getText().toString());
            userAccessData.put("user_password", passwordInput.getText().toString());
            userAccessData.put("device_token", PushUtil.getRegistrationId(this));
            userAccessData.put("device_platform", "ANDROID");
            reaxiumParameters.put("UserAccessData", userAccessData);
            loginParams.put("ReaxiumParameters", reaxiumParameters);
        } catch (Exception e) {
            GGUtil.showAToast(this, "Unknown error login, contact Reaxium Support");
            Log.e(TAG, "Error logint to the reaxium device", e);
        }
        return loginParams;
    }

    private void storeUserLogin(User user) {
        sharedPreferenceUtil.save(GGGlobalValues.USER_ID_IN_SESSION, user.getUserId());
        sharedPreferenceUtil.saveString(GGGlobalValues.USER_FULL_NAME_IN_SESSION, user.getFirstName() + " " + user.getFirstLastName());
        if (user.getUserType() != null) {
            sharedPreferenceUtil.saveString(GGGlobalValues.USER_FULL_TYPE_IN_SESSION, user.getUserType().getUserTypeName());
        }
    }


}
