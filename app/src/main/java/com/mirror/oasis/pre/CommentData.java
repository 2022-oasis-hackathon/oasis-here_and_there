package com.mirror.oasis.pre;

class CommentData {
    private String nickName;
    private String comment;
    private String time;
    private String profile;

    public CommentData() {}

    public CommentData(String nickName, String comment, String time, String profile) {
        this.nickName = nickName;
        this.comment = comment;
        this.time = time;
        this.profile = profile;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
