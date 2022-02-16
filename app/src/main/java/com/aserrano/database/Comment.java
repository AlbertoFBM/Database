package com.aserrano.database;

public class Comment {

    // ATTRIBUTES
    private int id;
    private String name;
    private String comment;

    public Comment(int id, String name, String comment) {
        this.id = id;
        this.name = name;
        this.comment = comment;
    }

    // GETTERS AND SETTERS

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return name;
    }
}
