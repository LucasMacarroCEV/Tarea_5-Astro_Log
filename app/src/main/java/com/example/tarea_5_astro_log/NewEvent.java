package com.example.tarea_5_astro_log;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.tarea_5_astro_log.databinding.ActivityNewEventBinding;

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

    String eventName;
    String eventDate;
    String date;
    String time;

    Category eventCategory;
    int eventCategoryPhoto = 0;

    boolean bError;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewEventBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.btnDatePicker.setOnClickListener(view1 -> DatePicker());
        binding.btnTimePicker.setOnClickListener(view1 -> TimePicker());

        binding.btnAdd.setOnClickListener(view1 -> CreateEvent());

        binding.ivComet.setOnClickListener(view1 -> SetCategory(Category.COMET));
        binding.ivMilkyway.setOnClickListener(view1 -> SetCategory(Category.MILKYWAY));
        binding.ivShootingstar.setOnClickListener(view1 -> SetCategory(Category.SHOOTINGSTAR));
        binding.ivFullmoon.setOnClickListener(view1 -> SetCategory(Category.FULLMOON));
        binding.ivPlanet.setOnClickListener(view1 -> SetCategory(Category.PLANET));
        binding.ivEclipse.setOnClickListener(view1 -> SetCategory(Category.ECLIPSE));
        binding.ivRocket.setOnClickListener(view1 -> SetCategory(Category.ROCKET));
        binding.ivSatellite.setOnClickListener(view1 -> SetCategory(Category.SATELLITE));
        binding.ivUfo.setOnClickListener(view1 -> SetCategory(Category.UFO));
    }

    private void CreateEvent(){
        SetName();
        SetDate();

        SendNewEvent();
    }
    private void SendNewEvent(){
        Event newEvent = new Event(eventName, eventDate, eventCategoryPhoto, eventCategory);
        Intent intent =new Intent(NewEvent.this, MainActivity.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("newevent", newEvent);

        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void SetName(){
        eventName = binding.tilEventName.getEditText().getText().toString();
    }

    private void SetCategory(Category eventcategory){
        Category selectedCategory = null;
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
        eventCategoryPhoto = selectedCategoryIco;
    }

    private void SetDate(){
        eventDate = date + "  " + time;
    }
    private void DatePicker(){
        final Calendar c = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DialogTheme , new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                date = String.valueOf(day) + "." + String.valueOf(month+1) + "." + String.valueOf(year);
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }
    private void TimePicker(){
        final Calendar c = Calendar.getInstance();

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                time = String.valueOf(hour) + ":" + String.valueOf(minute);
            }
        }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), false);

        timePickerDialog.show();
    }
}