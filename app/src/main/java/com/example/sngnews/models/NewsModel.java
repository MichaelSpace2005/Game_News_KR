package com.example.sngnews.models;

public class NewsModel {

    String title;
    String description;
    int id;
    String link;

    public NewsModel(String title, String link, int id, String description) {
        this.title = title;
        this.link = link;
        this.id = id;
        this.description = description;
    }

    public NewsModel() {
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
