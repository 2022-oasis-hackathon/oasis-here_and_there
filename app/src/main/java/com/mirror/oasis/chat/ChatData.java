package com.mirror.oasis.chat;

public class ChatData {

    private String user;
    private String message;
    private String time;
    private String profile;

    public ChatData() {}

    public ChatData(String user, String message, String time, String profile) {
        this.user = user;
        this.message = message;
        this.time = time;
        this.profile = profile;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }
}
