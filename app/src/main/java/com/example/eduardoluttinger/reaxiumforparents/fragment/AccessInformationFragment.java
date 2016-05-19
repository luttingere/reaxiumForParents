package com.example.eduardoluttinger.reaxiumforparents.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eduardoluttinger.reaxiumforparents.GGMainFragment;
import com.example.eduardoluttinger.reaxiumforparents.R;
import com.example.eduardoluttinger.reaxiumforparents.activity.AdminActivity;
import com.example.eduardoluttinger.reaxiumforparents.adapters.AccessInfoAdapter;
import com.example.eduardoluttinger.reaxiumforparents.beans.AccessMessage;
import com.example.eduardoluttinger.reaxiumforparents.beans.User;
import com.example.eduardoluttinger.reaxiumforparents.database.AccessMessageDAO;
import com.example.eduardoluttinger.reaxiumforparents.global.GGGlobalValues;
import com.example.eduardoluttinger.reaxiumforparents.service.PushUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eduardo Luttinger on 11/05/2016.
 */
public class AccessInformationFragment extends GGMainFragment {

    private RecyclerView accessInfoList;
    private LinearLayoutManager linearLayoutManager;
    private List<AccessMessage> accessMessages;
    private AccessInfoAdapter accessInfoAdapter;
    private AccessMessageDAO accessMessageDAO;
    private User userSelected;


    @Override
    public String getMyTag() {
        return AccessInformationFragment.class.getName();
    }

    @Override
    public Integer getToolbarTitle() {
        return R.string.student_access_information;
    }

    @Override
    protected Integer getFragmentLayout() {
        return R.layout.access_information_fragment;
    }

    @Override
    public Boolean onBackPressed() {
        ((AdminActivity) getActivity()).runMyFragment(new AssociateUsersFragment(), null, R.id.action_home);
        return Boolean.TRUE;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void setViews(View view) {
        ((AdminActivity) getActivity()).showBackButton();
        if (getArguments() != null) {
            userSelected = (User) getArguments().getSerializable(GGGlobalValues.USER_IN_A_BUNDLE);
        }
        accessMessageDAO = AccessMessageDAO.getInstance(getActivity());
        linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,true);
        accessInfoList = (RecyclerView) view.findViewById(R.id.access_info_list);
        accessInfoList.setLayoutManager(linearLayoutManager);
        loadAccessInformation();
        accessInfoAdapter = new AccessInfoAdapter(getActivity(), accessMessages);
        accessInfoList.setAdapter(accessInfoAdapter);
        PushUtil.cancelAllNotification(getActivity());
    }

    @Override
    protected void setViewsEvents() {

    }

    @Override
    public void clearAllViewComponents() {

    }

    /**
     * refresh the message panel
     */
    public void refreshList() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG,"refresh the access message list");
                loadAccessInformation();
                accessInfoAdapter.refreshDataSet(accessMessages);
                accessInfoList.scrollToPosition(0);
            }
        });
    }


    private void loadAccessInformation() {
        accessMessages = accessMessageDAO.getAccessMessagesByUserId(userSelected.getUserId());
        if (accessMessages == null) {
            accessMessages = new ArrayList<>();
        }
    }
}
