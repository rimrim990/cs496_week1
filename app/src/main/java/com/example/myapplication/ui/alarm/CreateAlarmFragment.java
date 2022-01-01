package com.example.myapplication.ui.alarm;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;

import java.util.Random;

public class CreateAlarmFragment extends Fragment {
    @BindView(R.id.fragment_create_alarm_timePicker) TimePicker timePicker;
    @BindView(R.id.fragment_create_alarm_title) EditText title;
    @BindView(R.id.fragment_create_alarm_scheduleAlarm) Button scheduleAlarm;
    @BindView(R.id.fragment_create_alarm_recurring) CheckBox recurring;
    @BindView(R.id.fragment_create_alarm_checkMon) CheckBox mon;
    @BindView(R.id.fragment_create_alarm_checkTue) CheckBox tue;
    @BindView(R.id.fragment_create_alarm_checkWed) CheckBox wed;
    @BindView(R.id.fragment_create_alarm_checkThu) CheckBox thu;
    @BindView(R.id.fragment_create_alarm_checkFri) CheckBox fri;
    @BindView(R.id.fragment_create_alarm_checkSat) CheckBox sat;
    @BindView(R.id.fragment_create_alarm_checkSun) CheckBox sun;
    @BindView(R.id.fragment_create_alarm_recurring_options) LinearLayout recurringOptions;

    private CreateAlarmViewModel createAlarmViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        createAlarmViewModel = ViewModelProvider.of(this).get(CreateViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_alarm, container, false);

        recurring.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    recurringOptions.setVisibility(View.VISIBLE);
                } else {
                    recurringOptions.setVisibility(View.GONE);
                }
            }
        });

        scheduleAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scheduleAlarm();
                Navigation.findNavController(v).navigate(R.id.action_createAlarmFragment_to_alarmListFragment);
            }
        });

        return view;
    }

    private void scheduleAlarm() {
        int alarmId = new Random().nextInt(Integer.MAX_VALUE);

        Alarm alarm = new Alarm(
                alarmId,
                TimePickerUtil.getTimePickerHour(timePicker),
                TimePickerUtil.getTimePickerMinute(timePicker),
                title.getText().toString(),
                true,
                recurring.isChecked(),
                mon.isChecked(),
                tue.isChecked(),
                wed.isChecked(),
                thu.isChecked(),
                fri.isChecked(),
                sat.isChecked(),
                sun.isChecked()
        );

        createAlarmViewModel.insert(alarm);

        alarm.schedule(getContext());
    }
}
