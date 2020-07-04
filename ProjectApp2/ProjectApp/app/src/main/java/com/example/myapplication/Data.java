package com.example.myapplication;


public class Data{

    private String title;
    private String title2;
    private String content;
    private String time;
    private int resId;
    private int type; // if type == 0 is other , 1 is me

    public Data(){

    }
    public Data(String useremail, String userName, String message,String time,int resid, int type) {
        this.title = useremail;
        this.title2 = userName;
        this.content = message;
        this.time = time;
        this.resId = resid;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle2() {
        return title2;
    }

    public void setTitle2(String title) {
        this.title2 = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }


    public void setType(int type){
        this.type = type;
    }

    public int getType(){

        return type;
    }
}