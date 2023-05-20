package com.example.yelpassignment;

public class RecyclerInput {


    private String title;
    private String description;
    private String tim;
    private  String mai;

    // constructor for our title and description.
    public RecyclerInput(String title, String description, String tim, String mai) {
        this.title = title;
        this.description = description;
        this.tim=tim;
        this.mai=mai;
    }

    // creating getter and setter methods.
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMai() {
        return mai;
    }

    public void setMai(String mai) {
        this.mai = mai;
    }

    public String getTim() {
        return tim;
    }

    public void setTim(String tim) {
        this.tim = tim;
    }
}
