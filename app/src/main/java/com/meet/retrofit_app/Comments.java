package com.meet.retrofit_app;

import com.google.gson.annotations.SerializedName;

public class Comments {

    private int postId;

    private int id;

    @SerializedName("name")
    private String title;
    private String email;

    @SerializedName("body")
    private String text;

    public int getPostId() {
        return postId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getEmail() {
        return email;
    }

    public String getText() {
        return text;
    }
}
