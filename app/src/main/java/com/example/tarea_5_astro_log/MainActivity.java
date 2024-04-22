package com.example.tarea_5_astro_log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ListView;

import com.example.tarea_5_astro_log.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Bundle data;

    private ActivityMainBinding binding;
    EventAdapter adapter;
    ArrayList<Event> events = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //Event newEvent = (Event) getIntent().getSerializableExtra("NewEvent");
        //events.add(newEvent);
        //data = getIntent().getExtras();
        //Data.Card card = (Data.Card) data.getSerializable("Data");

        // Datos del listado

        // Vincular la vista de cada fila a los datos
        adapter = new EventAdapter(this, R.layout.event_item, events);

        // Vincular el adapta a la vista del listado
        binding.lvEvents.setAdapter(adapter);

        // Detectar pulsación en la lista
        //binding.lvEvents.setOnItemClickListener((adapterView, view1, i, l) -> );

        binding.ivAddEvent.setOnClickListener(view1 -> CreateNewEvent());

        UpdateEventCount();
    }

    void UpdateEventCount(){
        if (events.size() == 1){
            binding.tvEventsCount.setText("¡Has visto " + events.size() + " astro!");
        }
        else {
            binding.tvEventsCount.setText("¡Has visto " + events.size() + " astros!");
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
                events.add(newEvent);
                adapter.notifyDataSetChanged();
                UpdateEventCount();
            }
        }
    }
}