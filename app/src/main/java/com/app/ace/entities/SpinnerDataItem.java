package com.app.ace.entities;

/**
 * Created by muniyemiftikhar on 4/29/2017.
 */

public class SpinnerDataItem {
    private String id;
    private String title;
    private boolean selected;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
