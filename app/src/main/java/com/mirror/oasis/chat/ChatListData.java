package com.mirror.oasis.chat;

class ChatListData {
    private String key;
    private String userId;
    private String userProfile;
    private String userKey;
    private String userNickName;
    private String lastMessage;

    public ChatListData(){}

    public ChatListData(String key, String userId, String userProfile, String userKey, String userNickName, String lastMessage) {
        this.key = key;
        this.userId = userId;
        this.userProfile = userProfile;
        this.userKey = userKey;
        this.userNickName = userNickName;
        this.lastMessage = lastMessage;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(String userProfile) {
        this.userProfile = userProfile;
    }

    public String getUserKey() {
        return userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }
}
