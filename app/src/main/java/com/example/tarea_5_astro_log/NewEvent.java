package com.example.tarea_5_astro_log;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Debug;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.TimePicker;

import com.example.tarea_5_astro_log.databinding.ActivityNewEventBinding;
import com.example.tarea_5_astro_log.databinding.EventCategoryDialogBinding;

import java.util.ArrayList;
import java.util.Calendar;

public class NewEvent extends AppCompatActivity {

    public enum Category{
        COMET,
        MILKYWAY,
        SHOOTINGSTAR,
        FULLMOON,
        PLANET,
        ECLIPSE,
        ROCKET,
        SATELLITE,
        UFO
    }

    private ActivityNewEventBinding binding;
    private EventCategoryDialogBinding categoryBinding;

    String eventName;
    String eventDate;
    String date;
    String time;

    Category eventCategory;
    int eventCategoryPhoto = 0;

    boolean bError;

    ArrayList<EventCategory> categories = new ArrayList<>();
    EventCategory currentCategory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewEventBinding.inflate(getLayoutInflater());
        categoryBinding = EventCategoryDialogBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.ivDatePicker.setOnClickListener(view1 -> DatePicker());
        binding.ivTimePicker.setOnClickListener(view1 -> TimePicker());

        binding.ivAdd.setOnClickListener(view1 -> CreateEvent());

        categories.add(new EventCategory(R.drawable.comet, Category.COMET));
        categories.add(new EventCategory(R.drawable.milkyway, Category.MILKYWAY));
        categories.add(new EventCategory(R.drawable.shootingstar, Category.SHOOTINGSTAR));
        categories.add(new EventCategory(R.drawable.fullmoon, Category.FULLMOON));
        categories.add(new EventCategory(R.drawable.planeta, Category.PLANET));
        categories.add(new EventCategory(R.drawable.eclipse, Category.ECLIPSE));
        categories.add(new EventCategory(R.drawable.rocket, Category.ROCKET));
        categories.add(new EventCategory(R.drawable.satellite, Category.SATELLITE));
        categories.add(new EventCategory(R.drawable.ufo, Category.UFO));

        binding.ivCategory.setOnClickListener(view1 -> Category());
    }

    private void CreateEvent(){
        SetName();
        SetDate();

        if (binding.tilEventName.getEditText().getText().toString().isEmpty() || eventCategoryPhoto == 0 || eventCategory == null || eventDate.isEmpty()){
            binding.tvError.setText("Rellene todos los campos, por favor.");
            bError = true;
        }
        else{
            try{
                bError = false;
                binding.tvError.setText("");
            }catch(NumberFormatException | NullPointerException e){
                bError = true;
                binding.tvError.setText("Error de sintaxis.");
            }
        }

        if (!bError){
            SendNewEvent();
        }
    }
    private void SendNewEvent(){
        Event newEvent = new Event(eventName, eventDate, eventCategoryPhoto, eventCategory);
        Intent intent =new Intent(NewEvent.this, MainActivity.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("newevent", newEvent);

        intent.putExtras(bundle);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    private void SetName(){
        eventName = binding.tilEventName.getEditText().getText().toString();
    }

    private void SetCategory(EventCategory category){
        eventCategory = category.eventCategory;
        eventCategoryPhoto = category.eventCategoryImage;

        /*Category selectedCategory = null;
        int selectedCategoryIco = 0;

        switch (eventcategory){
            case COMET:
                selectedCategory = Category.COMET;
                selectedCategoryIco = R.drawable.comet;
                break;
            case MILKYWAY:
                selectedCategory = Category.MILKYWAY;
                selectedCategoryIco = R.drawable.milkyway;
                break;
            case SHOOTINGSTAR:
                selectedCategory = Category.SHOOTINGSTAR;
                selectedCategoryIco = R.drawable.shootingstar;
                break;
            case FULLMOON:
                selectedCategory = Category.FULLMOON;
                selectedCategoryIco = R.drawable.fullmoon;
                break;
            case PLANET:
                selectedCategory = Category.PLANET;
                selectedCategoryIco = R.drawable.planeta;
                break;
            case ECLIPSE:
                selectedCategory = Category.ECLIPSE;
                selectedCategoryIco = R.drawable.eclipse;
                break;
            case ROCKET:
                selectedCategory = Category.ROCKET;
                selectedCategoryIco = R.drawable.rocket;
                break;
            case SATELLITE:
                selectedCategory = Category.SATELLITE;
                selectedCategoryIco = R.drawable.satellite;
                break;
            case UFO:
                selectedCategory = Category.UFO;
                selectedCategoryIco = R.drawable.ufo;
                break;
            default: break;
        }

        eventCategory = selectedCategory;
        eventCategoryPhoto = selectedCategoryIco;*/
    }
    private void Category(){
        AlertDialog.Builder builder = new AlertDialog.Builder(NewEvent.this);
        EventCategoryAdapter adapter = new EventCategoryAdapter(this, R.layout.event_category_item, categories);
        GridView customLayout = categoryBinding.gvCategories;
        builder.setView(customLayout);

        customLayout.setAdapter(adapter);
        if(customLayout.getParent() != null){
            ((ViewGroup)customLayout.getParent()).removeView(customLayout);
        }
        customLayout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                currentCategory = categories.get(i);
                SetCategory(currentCategory);
            }
        });

        builder.setTitle("Selecciona una categoría");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                binding.ivCategory.setImageDrawable(getDrawable(currentCategory.eventCategoryImage));
            }
        });
        builder.setCancelable(false);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void SetDate(){
        eventDate = date + "  " + time;
    }
    private void DatePicker(){
        final Calendar c = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DialogTheme , new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                date = day + "." + (month + 1) + "." + year;
                binding.tvDate.setText(date);
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }
    private void TimePicker(){
        final Calendar c = Calendar.getInstance();

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                time = hour + ":" + minute;
                binding.tvTime.setText(time);
            }
        }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), false);

        timePickerDialog.show();
    }
}