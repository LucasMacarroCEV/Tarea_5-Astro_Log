package com.example.tarea_5_astro_log;

import com.google.gson.Gson;

import java.util.ArrayList;

public class Events {
    public ArrayList<Event> events;

    public Events(){
        events = new ArrayList<>();
    }

    public String toJSON(){
        Gson gson = new Gson();
        String json = gson.toJson(this);
        return json;
    }
    public static Events toJava(String json){
        Gson gson = new Gson();
        Events events = gson.fromJson(json, Events.class);
        return events;
    }
}
