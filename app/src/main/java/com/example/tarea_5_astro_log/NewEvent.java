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
import android.widget.LinearLayout;
import android.widget.TimePicker;

import com.example.tarea_5_astro_log.databinding.ActivityNewEventBinding;
import com.example.tarea_5_astro_log.databinding.EventCategoryDialogBinding;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class NewEvent extends AppCompatActivity {
    private ActivityNewEventBinding binding;
    private EventCategoryDialogBinding categoryBinding;

    public enum Category{
        COMET, MILKYWAY, SHOOTINGSTAR, FULLMOON, PLANET, ECLIPSE, ROCKET, SATELLITE, UFO
    }
    String[] months = new String[]{
            "Ene", "Feb", "Mar", "Abr", "May", "Jun", "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"
    };
    int[] eventBackgrounds = new int[]{
            R.drawable.eventback01, R.drawable.eventback02, R.drawable.eventback03, R.drawable.eventback04, R.drawable.eventback05,
            R.drawable.eventback06, R.drawable.eventback07, R.drawable.eventback08, R.drawable.eventback09
    };

    String eventName;
    String eventDescription;
    String eventSimpleDate;
    String eventDetailedDate;
    String date;
    String time;
    int eventYear;
    int eventMonth;
    int eventDay;
    int eventBackground;

    Category eventCategory;
    EventCategory currentCategory;
    ArrayList<EventCategory> categories = new ArrayList<>();
    int eventCategoryPhoto = 0;

    boolean bError;

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

    /**
     * Método que comprueba y asigna los valores del evento a las variables locales
     */
    private void CreateEvent(){
        SetName();
        SetDescription();
        SetDate();
        SetBackground();

        if (binding.tilEventName.getEditText().getText().toString().isEmpty() || eventCategoryPhoto == 0 || eventCategory == null || eventSimpleDate.isEmpty()){
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
    /**
     * Método que crea el evento y lo envía a la MainActivity
     */
    private void SendNewEvent(){
        Event newEvent = new Event(eventName, eventDescription, eventSimpleDate, eventDetailedDate, eventCategoryPhoto, eventCategory, eventYear, eventMonth, eventDay, eventBackground);
        Intent intent =new Intent(NewEvent.this, MainActivity.class);

        Bundle bundle = new Bundle();
        bundle.putSerializable("newevent", newEvent);

        intent.putExtras(bundle);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

    /**
     * Método que asigna el nombre introducido en el campo correspondiente a su respectiva variable
     */
    private void SetName(){
        eventName = binding.tilEventName.getEditText().getText().toString();
    }

    /**
     * Método que asigna la descripción introducida en el campo correspondiente a su respectiva variable
     */
    private void SetDescription(){
        eventDescription = binding.tilEventDescription.getEditText().getText().toString();
    }

    /**
     * Método que asigna la categoría y la imagen correspondiente a sus respectivas variables
     * @param category
     */
    private void SetCategory(EventCategory category){
        eventCategory = category.eventCategory;
        eventCategoryPhoto = category.eventCategoryImage;
    }
    /**
     * Método que crea un AlertDialog para seleccionar la categoría
     */
    private void Category(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        EventCategoryAdapter adapter = new EventCategoryAdapter(this, R.layout.event_category_item, categories);
        GridView customLayout = categoryBinding.gvCategories;
        builder.setView(customLayout);
        customLayout.setAdapter(adapter);
        if(customLayout.getParent() != null){
            ((ViewGroup)customLayout.getParent()).removeView(customLayout);
        }

        AlertDialog dialog = builder.create();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                binding.ivCategory.setImageDrawable(getDrawable(currentCategory.eventCategoryImage));
            }
        });

        categoryBinding.gvCategories.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                currentCategory = categories.get(i);
                SetCategory(currentCategory);
                dialog.dismiss();
            }
        });
        builder.setTitle("Selecciona una categoría");

        dialog.show();
    }

    /**
     * Método que asigna la fecha introducida a sus respectivas variables
     */
    private void SetDate(){
        eventSimpleDate = months[eventMonth] + " " + eventYear;
        eventDetailedDate = date + "  " + time;
    }
    /**
     * Método que crea un DatePickerDialog y guarda la fecha introducida por el usuario
     */
    private void DatePicker(){
        final Calendar c = Calendar.getInstance();

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, R.style.DialogTheme , new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                eventYear = year;
                eventMonth = month;
                eventDay = day;
                date = day + "." + (month + 1) + "." + year;
                binding.tvDate.setText(date);
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }
    /**
     * Método que crea un TimePickerDialog y guarda la hora introducida por el ususario
     */
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

    /**
     * Método que selecciona y asigna un fondo aleatorio a su respectiva variable
     */
    private void SetBackground(){
        int random = new Random().nextInt(eventBackgrounds.length);
        eventBackground = eventBackgrounds[random];
    }
}