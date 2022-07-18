package com.mirror.oasis;

class UserInfo {
    private String key;
    private String id;
    private String password;
    private String nickName;
    private String profileUri;
    private String userInfo1;
    private String userInfo2;
    private String userInfo3;

    public UserInfo() {}

    public UserInfo(String key, String id, String password, String nickName, String profileUri, String userInfo1, String userInfo2, String userInfo3) {
        this.key = key;
        this.id = id;
        this.password = password;
        this.nickName = nickName;
        this.profileUri = profileUri;
        this.userInfo1 = userInfo1;
        this.userInfo2 = userInfo2;
        this.userInfo3 = userInfo3;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getProfileUri() {
        return profileUri;
    }

    public void setProfileUri(String profileUri) {
        this.profileUri = profileUri;
    }

    public String getUserInfo1() {
        return userInfo1;
    }

    public void setUserInfo1(String userInfo1) {
        this.userInfo1 = userInfo1;
    }

    public String getUserInfo2() {
        return userInfo2;
    }

    public void setUserInfo2(String userInfo2) {
        this.userInfo2 = userInfo2;
    }

    public String getUserInfo3() {
        return userInfo3;
    }

    public void setUserInfo3(String userInfo3) {
        this.userInfo3 = userInfo3;
    }
}
