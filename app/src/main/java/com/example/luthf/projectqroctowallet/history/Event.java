package com.example.luthf.projectqroctowallet.history;

/**
 * Created by luthf on 9/29/2015.
 */
public class Event {
    private String title, thumbnailUrl, date, desc, letter;

    public Event() {
    }

    public Event(String name, String thumbnailUrl, String year, String rating) {
        this.title = name;
        this.thumbnailUrl = thumbnailUrl;
        this.date = date;
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getLetter() {
        return letter;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

}
