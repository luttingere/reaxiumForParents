package com.example.eduardoluttinger.reaxiumforparents.beans;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Eduardo Luttinger on 16/05/2016.
 */
public class AccessMessage extends AppBean {


    public static final String IN_INFO_TYPE = "IN";
    public static final String OUT_INFO_TYPE = "OUT";
    public static final String SERVER_INFO_TYPE = "SERVER";

    @SerializedName("user_id")
    private Long userId;

    @SerializedName("traffic_info")
    private String accessInfo;

    @SerializedName("traffic_type")
    private TrafficType trafficType;

    @SerializedName("datetime")
    private String accessDate;

    @SerializedName("reaxium_device")
    private ReaxiumDevice device;

    @SerializedName("access_message_id")
    private Long accessMessageId;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getAccessInfo() {
        return accessInfo;
    }

    public void setAccessInfo(String accessInfo) {
        this.accessInfo = accessInfo;
    }

    public TrafficType getTrafficType() {
        return trafficType;
    }

    public void setTrafficType(TrafficType trafficType) {
        this.trafficType = trafficType;
    }

    public String getAccessDate() {
        return accessDate;
    }

    public void setAccessDate(String accessDate) {
        this.accessDate = accessDate;
    }

    public ReaxiumDevice getDevice() {
        return device;
    }

    public void setDevice(ReaxiumDevice device) {
        this.device = device;
    }

    public Long getAccessMessageId() {
        return accessMessageId;
    }

    public void setAccessMessageId(Long accessMessageId) {
        this.accessMessageId = accessMessageId;
    }
}
