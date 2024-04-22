package com.example.tarea_5_astro_log;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TimePicker;

import com.example.tarea_5_astro_log.databinding.ActivityMainBinding;
import com.example.tarea_5_astro_log.databinding.ActivityNewEventBinding;
import com.example.tarea_5_astro_log.databinding.DatePickerBinding;
import com.example.tarea_5_astro_log.databinding.TimePickerBinding;

import java.util.Date;

public class NewEvent extends AppCompatActivity {

    private ActivityNewEventBinding binding;
    private DatePickerBinding DPbinding;
    private TimePickerBinding TPbinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DPbinding = DatePickerBinding.inflate(getLayoutInflater());
        TPbinding = TimePickerBinding.inflate(getLayoutInflater());
        binding = ActivityNewEventBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.btnDatePicker.setOnClickListener(view1 -> DatePicker());
        binding.btnTimePicker.setOnClickListener(view1 -> TimePicker());
    }

    private void DatePicker(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.date_picker);
        dialog.show();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DPbinding.dpDate.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker datePicker, int year, int month, int day) {
                    // Hacer algo con la fecha elegida

                }
            });
        }
    }
    private void TimePicker(){
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.time_picker);
        dialog.show();

        TPbinding.timeTP.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                // Hacer algo con la hora elegida
            }
        });
    }
}