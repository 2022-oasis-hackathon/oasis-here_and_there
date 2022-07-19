package com.mirror.oasis;

class UserInfo {
    private String key;
    private String id;
    private String password;
    private String nickName;
    private String profileUri;

    public UserInfo() {}

    public UserInfo(String key, String id, String password, String nickName, String profileUri) {
        this.key = key;
        this.id = id;
        this.password = password;
        this.nickName = nickName;
        this.profileUri = profileUri;
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
}
