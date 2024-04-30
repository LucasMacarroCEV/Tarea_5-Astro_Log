package com.example.tarea_5_astro_log;

import java.io.Serializable;

public class Event implements Serializable {
    String name;
    String description;
    String simpleDate;
    String detailedDate;
    int year;
    int month;
    int day;
    int categoryPhoto;
    int backgroundPhoto;
    NewEvent.Category category;

    public Event(String name, String description, String simpleDate, String detailedDate, int categoryPhoto, NewEvent.Category category, int year, int month, int day, int backgroundPhoto) {
        this.name = name;
        this.description = description;
        this.simpleDate = simpleDate;
        this.detailedDate = detailedDate;
        this.categoryPhoto = categoryPhoto;
        this.category = category;
        this.year = year;
        this.month = month;
        this.day = day;
        this.backgroundPhoto = backgroundPhoto;
    }
}
