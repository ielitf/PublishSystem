package com.hskj.publishsystem.bean;


public class NewsItemBean {
    private String title;
    private String startTime;
    private String endTime;
    private String control;

    public NewsItemBean(String title,String startTime,String endTime) {
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    public NewsItemBean(String title,String startTime,String endTime,String control) {
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.control = control;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getControl() {
        return control;
    }

    public void setControl(String control) {
        this.control = control;
    }

    @Override
    public String toString() {
        return "NewsItemBean{" +
                "title='" + title + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", control='" + control + '\'' +
                '}';
    }
}
