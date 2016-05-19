package com.example.eduardoluttinger.reaxiumforparents.beans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Eduardo Luttinger on 16/05/2016.
 */
public class TrafficType extends AppBean {

    @SerializedName("traffic_type_id")
    private Long trafficTypeId;

    @SerializedName("traffic_type_name")
    private String trafficTypeName;

    public Long getTrafficTypeId() {
        return trafficTypeId;
    }

    public void setTrafficTypeId(Long trafficTypeId) {
        this.trafficTypeId = trafficTypeId;
    }

    public String getTrafficTypeName() {
        return trafficTypeName;
    }

    public void setTrafficTypeName(String trafficTypeName) {
        this.trafficTypeName = trafficTypeName;
    }
}
