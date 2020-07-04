package com.example.myapplication;

public class ItemData {
    private String email;
    private String nickname;
    private String title;
    private String content;
    private String price;
    private String time;
    private String imageUrl;
    private String postnum;

    public ItemData() {

    }

    public ItemData(String email, String nickname, String title, String content, String price, String time, String Url, String postnum) {
        this.email = email;
        this.nickname = nickname;
        this.title = title;
        this.content = content;
        this.price = price;
        this.time = time;
        this.imageUrl = Url;
        this.postnum = postnum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setimageUrl(String Url ){imageUrl = Url;}

    public String getimageUrl() { return imageUrl;}

    public String getPostnum() {
        return postnum;
    }

    public void setPostnum(String postnum) {
        this.postnum = postnum;
    }
}