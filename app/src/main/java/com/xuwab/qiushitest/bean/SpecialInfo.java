package com.xuwab.qiushitest.bean;

/**
 * Created by xuwab on 2016/10/18.
 */
public class SpecialInfo {
    private String user_id;
    private String user_name;
    private String user_icon;
    private String content;
    private String content_image;
    private String userHeaderUrl;

    public SpecialInfo(){}

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_icon() {
        return user_icon;
    }

    public void setUser_icon(String user_icon) {
        this.user_icon = user_icon;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent_image() {
        return content_image;
    }

    public void setContent_image(String content_image) {
        this.content_image = content_image;
    }

    public void setUserHeaderUrl(String userHeaderUrl) {
        this.userHeaderUrl = userHeaderUrl;
    }

    public String getUserHeaderUrl() {
        return userHeaderUrl;
    }
}
