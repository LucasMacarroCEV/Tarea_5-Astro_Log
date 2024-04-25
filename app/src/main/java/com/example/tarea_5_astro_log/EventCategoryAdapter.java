package com.example.tarea_5_astro_log;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class EventCategoryAdapter extends ArrayAdapter<EventCategory> {
    Context context;
    int idItemLayout;
    List<EventCategory> eventCategories;

    public EventCategoryAdapter(@NonNull Context context, int idItemLayout, @NonNull List<EventCategory> eventCategories) {
        super(context, idItemLayout, eventCategories);
        this.context = context;
        this.idItemLayout = idItemLayout;
        this.eventCategories = eventCategories;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Crear vista de esta fila
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(idItemLayout, parent, false);

        // Categoría en esta posición
        EventCategory eventCategory = eventCategories.get(position);

        // Poner la foto
        ImageView ivCategoryPhoto = convertView.findViewById(R.id.ivEventCategoryImage);
        ivCategoryPhoto.setImageDrawable(context.getDrawable(eventCategory.eventCategoryImage));

        return convertView;
    }
}
