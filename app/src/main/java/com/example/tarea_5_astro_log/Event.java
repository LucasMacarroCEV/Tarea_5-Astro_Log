package com.example.tarea_5_astro_log;

import java.io.Serializable;

public class Event implements Serializable {
    String name;
    String date;
    int categoryPhoto;
    NewEvent.Category category;

    public Event(String name, String date, int categoryPhoto, NewEvent.Category category) {
        this.name = name;
        this.date = date;
        this.categoryPhoto = categoryPhoto;
        this.category = category;
    }
}
