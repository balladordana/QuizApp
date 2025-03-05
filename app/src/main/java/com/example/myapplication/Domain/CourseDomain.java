package com.example.myapplication.Domain;

public class CourseDomain {
    private String title;
    private int percent;
    private String picPath;
    private String progressTxt;

    public String getProgressTxt() {
        return progressTxt;
    }

    public void setProgressTxt(String progressTxt) {
        this.progressTxt = progressTxt;
    }

    public CourseDomain(String title, int percent, String picPath) {
        this.title = title;
        this.percent = percent;
        this.picPath = picPath;
    }
    public CourseDomain(String title, String picPath) {
        this.title = title;
        this.picPath = picPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }
}
