package com.doc24x7.Notification.service;

public class CallModel {
    private String status;
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    private String uid;

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    private String target;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private String userId;
    private String token_with_int_uid;

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    private String activityType;

    public String getToken_with_int_uid() {
        return token_with_int_uid;
    }

    public void setToken_with_int_uid(String token_with_int_uid) {
        this.token_with_int_uid = token_with_int_uid;
    }

    public String getToken_with_user_account() {
        return token_with_user_account;
    }

    public void setToken_with_user_account(String token_with_user_account) {
        this.token_with_user_account = token_with_user_account;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getEncryption_type() {
        return encryption_type;
    }

    public void setEncryption_type(String encryption_type) {
        this.encryption_type = encryption_type;
    }
    private String token_with_user_account;
    private String room;
    private String encryption_type;
    public String getRtm_token() {
        return rtm_token;
    }
    public void setRtm_token(String rtm_token) {
        this.rtm_token = rtm_token;
    }
    private String rtm_token;
    public String getEncryption_key() {
        return encryption_key;
    }
    public void setEncryption_key(String encryption_key) {
        this.encryption_key = encryption_key;
    }
    private String encryption_key;
}
