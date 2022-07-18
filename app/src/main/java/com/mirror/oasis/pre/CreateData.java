package com.mirror.oasis.pre;

import java.util.ArrayList;

class CreateData {
    private String id;
    private String nickName;
    private String title;
    private String content;
    private ArrayList<String> photoKeys;
    private String key;
    private String firstUri;
    private String profile;

    public CreateData() {}

    public CreateData(String id, String nickName, String title, String content, ArrayList<String> photoKeys, String key, String firstUri, String profile) {
        this.id = id;
        this.nickName = nickName;
        this.title = title;
        this.content = content;
        this.photoKeys = photoKeys;
        this.key = key;
        this.firstUri = firstUri;
        this.profile = profile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArrayList<String> getPhotoKeys() {
        return photoKeys;
    }

    public void setPhotoKeys(ArrayList<String> photoKeys) {
        this.photoKeys = photoKeys;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getFirstUri() {
        return firstUri;
    }

    public void setFirstUri(String firstUri) {
        this.firstUri = firstUri;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
