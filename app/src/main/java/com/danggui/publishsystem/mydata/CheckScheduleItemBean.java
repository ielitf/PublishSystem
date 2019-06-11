package com.danggui.publishsystem.mydata;


public class CheckScheduleItemBean {
    private String title;
    private String comName;

    public CheckScheduleItemBean(String title, String comName) {
        this.title = title;
        this.comName = comName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }

    @Override
    public String toString() {
        return "CheckScheduleItemBean{" +
                "title='" + title + '\'' +
                ", comName='" + comName + '\'' +
                '}';
    }
}
