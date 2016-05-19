package com.example.eduardoluttinger.reaxiumforparents.beans;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Eduardo Luttinger on 16/05/2016.
 */
public class ParentInfo extends AppBean {

    @SerializedName("parent")
    private User parentInformation;

    @SerializedName("parentRelationship")
    private List<User> parentRelationship;


    public User getParentInformation() {
        return parentInformation;
    }

    public void setParentInformation(User parentInformation) {
        this.parentInformation = parentInformation;
    }

    public List<User> getParentRelationship() {
        return parentRelationship;
    }

    public void setParentRelationship(List<User> parentRelationship) {
        this.parentRelationship = parentRelationship;
    }
}
