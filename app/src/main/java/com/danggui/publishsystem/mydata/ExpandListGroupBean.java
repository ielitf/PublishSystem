package com.danggui.publishsystem.mydata;


public class ExpandListGroupBean {
    private int icon;
    private String title;

    public ExpandListGroupBean(int icon, String title) {
        this.icon = icon;
        this.title = title;
    }
    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public String toString() {
        return "CommonBean{" +
                "icon=" + icon +
                ", title='" + title + '\'' +
                '}';
    }
}
