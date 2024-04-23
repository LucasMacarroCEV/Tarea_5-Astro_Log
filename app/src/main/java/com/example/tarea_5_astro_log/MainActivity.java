package com.example.tarea_5_astro_log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ListView;

import com.example.tarea_5_astro_log.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    EventAdapter adapter;
    Events events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        LoadEvents();

        // Vincular la vista de cada fila a los datos
        adapter = new EventAdapter(this, R.layout.event_item, events.events);

        // Vincular el adapta a la vista del listado
        binding.lvEvents.setAdapter(adapter);

        // Detectar pulsación en la lista
        //binding.lvEvents.setOnItemClickListener((adapterView, view1, i, l) -> );

        binding.ivAddEvent.setOnClickListener(view1 -> CreateNewEvent());
        binding.ivDeleteEvents.setOnClickListener(view1 -> DeleteEvents());

        UpdateEventCount();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();

        SaveEvents();
    }

    void UpdateEventCount(){
        if (events.events.size() == 1){
            binding.tvEventsCount.setText("¡Has visto " + events.events.size() + " astro!");
        }
        else {
            binding.tvEventsCount.setText("¡Has visto " + events.events.size() + " astros!");
        }
    }

    void CreateNewEvent(){
        Intent newEventIntent = new Intent(MainActivity.this, NewEvent.class);
        startActivityForResult(newEventIntent, 1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        Bundle bundle = data.getExtras();
        Event newEvent = null;

        if (requestCode == 1){
            newEvent = (Event) bundle.getSerializable("newevent");
            if (newEvent != null){
                events.events.add(newEvent);
                adapter.notifyDataSetChanged();
                UpdateEventCount();
            }
        }
    }
    void DeleteEvents(){
        events.events.clear();
        adapter.notifyDataSetChanged();
        SaveEvents();
        UpdateEventCount();
    }

    void SaveEvents(){
        String json = events.toJSON();
        SharedPreferences preferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("events", json);
        editor.commit();
    }
    void LoadEvents(){
        SharedPreferences preferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        String json = preferences.getString("events", null);
        if (json == null) {
            events = new Events();
        } else {
            events = Events.toJava(json);
        }
    }

}