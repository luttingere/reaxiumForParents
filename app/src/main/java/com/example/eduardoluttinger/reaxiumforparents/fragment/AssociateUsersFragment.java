package com.example.eduardoluttinger.reaxiumforparents.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.eduardoluttinger.reaxiumforparents.GGMainFragment;
import com.example.eduardoluttinger.reaxiumforparents.R;
import com.example.eduardoluttinger.reaxiumforparents.activity.AdminActivity;
import com.example.eduardoluttinger.reaxiumforparents.adapters.AssociatedUserAdapter;
import com.example.eduardoluttinger.reaxiumforparents.beans.AssociatedUsersBean;
import com.example.eduardoluttinger.reaxiumforparents.beans.User;
import com.example.eduardoluttinger.reaxiumforparents.database.AssociatedUsersDAO;
import com.example.eduardoluttinger.reaxiumforparents.global.GGGlobalValues;
import com.example.eduardoluttinger.reaxiumforparents.interfaces.AssociatedUserScreenListener;
import com.example.eduardoluttinger.reaxiumforparents.util.GGUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eduardo Luttinger on 10/05/2016.
 */
public class AssociateUsersFragment extends GGMainFragment implements AssociatedUserScreenListener {

    private RecyclerView associatedUserList;
    private LinearLayoutManager linearLayoutManager;
    private List<User> associatedUsersBeanList;
    private AssociatedUserAdapter associatedUserAdapter;
    private AssociatedUsersDAO associatedUsersDAO;

    @Override
    public String getMyTag() {
        return AssociateUsersFragment.class.getName();
    }

    @Override
    public Integer getToolbarTitle() {
        return R.string.your_students;
    }

    @Override
    protected Integer getFragmentLayout() {
        return R.layout.associated_users_fragment;
    }

    @Override
    public Boolean onBackPressed() {
        return Boolean.TRUE;
    }

    @Override
    protected void setViews(View view) {
        ((AdminActivity) getActivity()).hideBackButton();
        associatedUsersDAO = AssociatedUsersDAO.getInstance(getActivity());
        associatedUserList = (RecyclerView) view.findViewById(R.id.associated_user_list);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        associatedUserList.setLayoutManager(linearLayoutManager);
        loadAssociatedUserData();
        associatedUserAdapter = new AssociatedUserAdapter(getActivity(),associatedUsersBeanList,this);
        associatedUserList.setAdapter(associatedUserAdapter);
    }

    @Override
    protected void setViewsEvents() {

    }

    @Override
    public void clearAllViewComponents() {

    }

    private void loadAssociatedUserData(){
        showProgressDialog("Loading info...");
        associatedUsersBeanList = associatedUsersDAO.getAssociatedUserInSystem();
        if(associatedUsersBeanList == null){
            associatedUsersBeanList = new ArrayList<>();
        }
        hideProgressDialog();
    }

    @Override
    public void trackUser(User user) {
        ((AdminActivity) getActivity()).runMyFragment(new TrackUserFragment(), null, R.id.action_home);
    }

    @Override
    public void showAccessInformation(User user) {
        Bundle arguments = new Bundle();
        arguments.putSerializable(GGGlobalValues.USER_IN_A_BUNDLE,user);
       ((AdminActivity) getActivity()).runMyFragment(new AccessInformationFragment(), arguments, R.id.action_home);
    }
}
