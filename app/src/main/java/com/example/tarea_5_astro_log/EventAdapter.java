package com.example.tarea_5_astro_log;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class EventAdapter extends ArrayAdapter
{
    Context context;
    int idItemLayout;
    List<Event> events;

    public EventAdapter(@NonNull Context context, int idItemLayout, @NonNull List<Event> events) {
        super(context, idItemLayout, events);
        this.context = context;
        this.idItemLayout = idItemLayout;
        this.events = events;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Crear vista de esta fila
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(idItemLayout, parent, false);

        // Persona en esta posición
        Event event = events.get(position);

        // Poner el nombre
        TextView tvName = convertView.findViewById(R.id.tvEventName);
        tvName.setText(event.name);

        // Poner la foto
        //ImageView ivCategoryPhoto = convertView.findViewById(R.id.ivEventCategoryImage);
        //ivCategoryPhoto.setImageDrawable(context.getDrawable(event.categoryPhoto));

        //Poner la fecha
        TextView tvDate = convertView.findViewById(R.id.tvEventDate);
        tvDate.setText(event.date);

        Log.i("getView", "fila: " + position + " nombre: "+ event.name);
        return convertView;
    }

}
