package com.example.tarea_5_astro_log;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.tarea_5_astro_log.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    EventAdapter adapter;
    ArrayList<Event> events = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Datos del listado
        events.add(new Event("Urano", 0, "12 ene 9:30"));
        events.add(new Event("Estrella fugaz", 0, "08 may 16:14"));
        events.add(new Event("Galaxia", 0, "09 nov 23:49"));
        events.add(new Event("Galaxia", 0, "09 nov 23:49"));
        events.add(new Event("Galaxia", 0, "09 nov 23:49"));
        events.add(new Event("Galaxia", 0, "09 nov 23:49"));
        events.add(new Event("Galaxia", 0, "09 nov 23:49"));
        events.add(new Event("Galaxia", 0, "09 nov 23:49"));
        events.add(new Event("Galaxia", 0, "09 nov 23:49"));
        events.add(new Event("Galaxia", 0, "09 nov 23:49"));
        events.add(new Event("Galaxia", 0, "09 nov 23:49"));
        events.add(new Event("Galaxia", 0, "09 nov 23:49"));
        events.add(new Event("Galaxia", 0, "09 nov 23:49"));
        events.add(new Event("Galaxia", 0, "09 nov 23:49"));

        // Vincular la vista de cada fila a los datos
        adapter = new EventAdapter(this, R.layout.event_item, events);

        // Vincular el adapta a la vista del listado
        binding.lvEvents.setAdapter(adapter);

        // Detectar pulsación en la lista
        //binding.lvEvents.setOnItemClickListener((adapterView, view1, i, l) -> );

        binding.ivAddEvent.setOnClickListener(view1 -> startActivity(new Intent(this, NewEvent.class)));

        UpdateEventCount();
    }

    void UpdateEventCount(){
        binding.tvEventsCount.setText("¡Has visto " + events.size() + " astros!");
    }
}