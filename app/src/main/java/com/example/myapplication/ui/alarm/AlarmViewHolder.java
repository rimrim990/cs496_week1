package com.example.myapplication.ui.alarm;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class AlarmViewHolder extends RecyclerView.ViewHolder{

    private TextView alarmTime;
    private TextView alarmTitle;
    private ImageView alarmRecurring;
    private TextView alarmRecurringDays;

    Switch alarmStarted;

    public AlarmViewHolder(@NonNull View itemView) {
        super(itemView);

        alarmTime = (TextView) itemView.findViewById(R.id.item_alarm_time);
        alarmStarted = (Switch) itemView.findViewById(R.id.item_alarm_started);
        alarmTitle = (TextView) itemView.findViewById(R.id.item_alarm_title);
        alarmRecurringDays = (TextView) itemView.findViewById(R.id.item_alarm_recurringDays);
    }

    public void bind(Alarm alarm, OnToggleAlarmListener listener) {
        String alarmText = String.format("%02d:%02d", alarm.getHour(), alarm.getMinute());

        alarmTime.setText(alarmText);
        alarmStarted.setChecked(alarm.getStarted());

        if (alarm.getRecurring()) {
            // alarmRecurring.setImageResource(R.drawable.ic_launcher_foreground);
            alarmRecurringDays.setText(alarm.getRecurringDaysText());
        } else {
            // alarmRecurring.setImageResource(R.drawable.ic_launcher_foreground);
            alarmRecurringDays.setText("Once Off");
        }

        if (alarm.getTitle().length() != 0) {
            alarmTitle.setText(alarm.getTitle());
        } else {
            alarmTitle.setText("My alarm");
        }

        alarmStarted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                listener.onToggle(alarm);
            }
        });
    }
}
