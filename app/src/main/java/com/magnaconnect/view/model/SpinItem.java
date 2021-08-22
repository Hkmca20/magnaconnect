/*
 * Created by Hariom.Gupta.Gurugram on 30/01/20.
 * hk.mca08@gmail.com
 * 8510887828
 */
package com.magnaconnect.view.model;

public class SpinItem {

    private String SNo;
    private String type;

    private String title;
    private String text;
    private int icon;
    private int iconSelected;
    private int navigationId;

    public SpinItem(){
    }
    public SpinItem(String SNo, String type, String title, String text, int icon, int iconSelected, int navigationId) {
        this.SNo = SNo;
        this.type = type;
        this.title = title;
        this.text = text;
        this.icon = icon;
        this.iconSelected = iconSelected;
        this.navigationId = navigationId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSNo() {
        return SNo;
    }

    public void setSNo(String SNo) {
        this.SNo = SNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getIconSelected() {
        return iconSelected;
    }

    public void setIconSelected(int iconSelected) {
        this.iconSelected = iconSelected;
    }

    public int getNavigationId() {
        return navigationId;
    }

    public void setNavigationId(int navigationId) {
        this.navigationId = navigationId;
    }

    @Override
    public String toString() {
        return "SpinItem{" +
                "SNo='" + SNo + '\'' +
                ", type='" + type + '\'' +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", icon=" + icon +
                ", iconSelected=" + iconSelected +
                ", navigationId=" + navigationId +
                '}';
    }
}
