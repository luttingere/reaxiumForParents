package com.example.eduardoluttinger.reaxiumforparents.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eduardoluttinger.reaxiumforparents.R;
import com.example.eduardoluttinger.reaxiumforparents.beans.AccessMessage;
import com.example.eduardoluttinger.reaxiumforparents.holders.AccessInfoHolder;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eduardo Luttinger on 11/05/2016.
 */
public class AccessInfoAdapter extends  RecyclerView.Adapter<AccessInfoHolder> {

    private Context context;
    private List<AccessMessage> accessMessageList = new ArrayList<>();
    private AccessInfoHolder accessInfoHolder;
    private static final int IN_INFO_TYPE = 1;
    private static final int OUT_INFO_TYPE = 2;
    private static final int SERVER_INFO_TYPE = 3;

    public AccessInfoAdapter(Context context,List<AccessMessage> accessMessageList) {
        this.context = context;
        this.accessMessageList = accessMessageList;
    }

    @Override
    public AccessInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = null;
        switch (viewType){
            case IN_INFO_TYPE:
                itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.access_in_item, null);
                break;
            case OUT_INFO_TYPE:
                itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.access_out_item, null);
                break;
            case SERVER_INFO_TYPE:
                //TODO: crear layout para mensajes de servidor
                itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.access_out_item, null);
            default:
//                itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.access_in_item, null);
                break;
        }
        accessInfoHolder = new AccessInfoHolder(itemLayoutView);
        return accessInfoHolder;
    }

    @Override
    public void onBindViewHolder(AccessInfoHolder holder, int position) {
        AccessMessage accessMessage = accessMessageList.get(position);
        holder.infoAccess.setText(accessMessage.getAccessInfo());
    }

    @Override
    public int getItemViewType(int position) {
        int infoType = 0;
        AccessMessage accessMessage = accessMessageList.get(position);
        if(accessMessage != null){
            switch (accessMessage.getTrafficType().getTrafficTypeName()){
                case AccessMessage.IN_INFO_TYPE:
                    infoType = IN_INFO_TYPE;
                    break;
                case AccessMessage.OUT_INFO_TYPE:
                    infoType = OUT_INFO_TYPE;
                    break;
                case AccessMessage.SERVER_INFO_TYPE:
                    infoType = SERVER_INFO_TYPE;
                    break;
            }
        }
        return infoType;
    }

    public void refreshDataSet(List<AccessMessage> accessMessageList){
        this.accessMessageList = accessMessageList;
        notifyItemInserted(0);
    }


    @Override
    public int getItemCount() {
        return accessMessageList.size();
    }
}
