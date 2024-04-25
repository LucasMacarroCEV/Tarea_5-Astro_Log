package com.example.tarea_5_astro_log;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tarea_5_astro_log.databinding.ActivityMainBinding;
import com.example.tarea_5_astro_log.databinding.DetailedEventBinding;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private DetailedEventBinding DeBinding;
    EventAdapter adapter;
    Events events;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        DeBinding = DetailedEventBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        LoadEvents();

        // Vincular la vista de cada fila a los datos
        adapter = new EventAdapter(this, R.layout.event_item, events.events);

        // Vincular el adapta a la vista del listado
        binding.lvEvents.setAdapter(adapter);

        // Detectar pulsación en la lista
        binding.lvEvents.setOnItemClickListener((adapterView, view1, i, l) -> {
            ShowEventInfo(i);
        });
        binding.lvEvents.setOnItemLongClickListener((adapterView, view1, index, l) -> {
            DeleteEvent(index);
            return true;
        });

        binding.ivAddEvent.setOnClickListener(view1 -> CreateNewEvent());
        binding.ivDeleteEvents.setOnClickListener(view1 -> DeleteEvents());

        UpdateEventCount();
    }

    @Override
    protected  void onStop(){
        super.onStop();
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
        int LAUNCH_NEWEVENT_ACTIVITY = 1;
        Intent newEventIntent = new Intent(MainActivity.this, NewEvent.class);
        startActivityForResult(newEventIntent, LAUNCH_NEWEVENT_ACTIVITY);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1){
            if (resultCode == Activity.RESULT_OK){
                Bundle bundle = data.getExtras();
                Event newEvent = null;
                newEvent = (Event) bundle.getSerializable("newevent");
                if (newEvent != null){
                    events.events.add(newEvent);
                    adapter.notifyDataSetChanged();
                    UpdateEventCount();
                }
            }
            if (resultCode == Activity.RESULT_CANCELED){

            }
        }
    }

    void DeleteEvents(){
        events.events.clear();
        adapter.notifyDataSetChanged();
        SaveEvents();
        UpdateEventCount();
    }
    void DeleteEvent(int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("¿Quieres eliminar este evento?")
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        events.events.remove(position);
                        adapter.notifyDataSetChanged();
                        UpdateEventCount();
                        Toast.makeText(getApplicationContext(), "Evento eliminado", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();
    }

    void ShowEventInfo(int position){
        final Dialog dialog = new Dialog(this);
        RelativeLayout customLayout = DeBinding.rlDetailedEvent;
        if(customLayout.getParent() != null){
            ((ViewGroup)customLayout.getParent()).removeView(customLayout);
        }
        dialog.setContentView(customLayout);

        TextView name = (TextView) dialog.findViewById(R.id.tvDeName);
        name.setText(events.events.get(position).name);

        TextView description = (TextView) dialog.findViewById(R.id.tvDeDescription);
        description.setText(events.events.get(position).description);

        TextView date = (TextView) dialog.findViewById(R.id.tvDeDate);
        date.setText(events.events.get(position).date);

        ImageView image = (ImageView) dialog.findViewById(R.id.ivDeCategory);
        image.setImageDrawable(AppCompatResources.getDrawable(this, events.events.get(position).categoryPhoto));

        dialog.show();

        /*AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ConstraintLayout customLayout = DeBinding.clDetailedEvent;

        builder.setView(R.layout.detailed_event);

        DeBinding.tvDeName.setText(events.events.get(position).name);
        DeBinding.tvDeDescription.setText(events.events.get(position).description);
        DeBinding.tvDeDate.setText(events.events.get(position).date);
        DeBinding.ivDeCategory.setImageDrawable(AppCompatResources.getDrawable(this, events.events.get(position).categoryPhoto));

        AlertDialog dialog = builder.create();
        dialog.show();*/
    }

    void SaveEvents(){
        String json = events.toJSON();
        SharedPreferences preferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("events", json);
        editor.apply();
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