package com.example.eduardoluttinger.reaxiumforparents.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.eduardoluttinger.reaxiumforparents.R;
import com.example.eduardoluttinger.reaxiumforparents.beans.AssociatedUsersBean;
import com.example.eduardoluttinger.reaxiumforparents.beans.User;
import com.example.eduardoluttinger.reaxiumforparents.holders.AssociatedUserHolder;
import com.example.eduardoluttinger.reaxiumforparents.interfaces.AssociatedUserScreenListener;
import com.example.eduardoluttinger.reaxiumforparents.util.MySingletonUtil;

import java.util.List;

/**
 * Created by Eduardo Luttinger on 10/05/2016.
 */
public class AssociatedUserAdapter extends  RecyclerView.Adapter<AssociatedUserHolder> {

    private Context context;
    private List<User> associatedUsersBeanList;
    private AssociatedUserScreenListener associatedUserScreenListener;
    private User associatedUser;
    private ImageLoader mImageLoader;


    public AssociatedUserAdapter(Context context,List<User> associatedUsersBeanList, AssociatedUserScreenListener associatedUserScreenListener) {
        this.associatedUsersBeanList = associatedUsersBeanList;
        this.context = context;
        this.associatedUserScreenListener = associatedUserScreenListener;
        mImageLoader = MySingletonUtil.getInstance(context).getImageLoader();
    }

    @Override
    public AssociatedUserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.associate_user_item, null);
        return new AssociatedUserHolder(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(final AssociatedUserHolder holder, int position) {
        associatedUser = associatedUsersBeanList.get(position);
        final User user = associatedUser;
        holder.userPhotoLoader.getIndeterminateDrawable().setColorFilter(context.getResources().getColor(R.color.colorPrimary), android.graphics.PorterDuff.Mode.SRC_ATOP);
        holder.userName.setText(associatedUser.getFirstName());
        holder.userDocumentId.setText(associatedUser.getDocumentId());
        holder.schoolName.setText(associatedUser.getBusiness().getBusinessName());
        holder.trackUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                associatedUserScreenListener.trackUser(user);
            }
        });

        holder.accessInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                associatedUserScreenListener.showAccessInformation(user);
            }
        });

        if(associatedUser.getPhoto() != null && !"".equals(associatedUser.getPhoto())){
            holder.userPhotoLoader.setVisibility(View.VISIBLE);
            mImageLoader.get(associatedUser.getPhoto(), new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    Bitmap bitmap = response.getBitmap();
                    if (bitmap != null) {
                        holder.userPhoto.setImageBitmap(bitmap);
                    }
                    holder.userPhotoLoader.setVisibility(View.GONE);
                }
                @Override
                public void onErrorResponse(VolleyError error) {
                    holder.userPhotoLoader.setVisibility(View.GONE);
                }
            });
        }else{
            holder.userPhotoLoader.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return associatedUsersBeanList.size();
    }


}
