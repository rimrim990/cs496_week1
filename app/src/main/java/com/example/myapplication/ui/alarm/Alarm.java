package com.example.myapplication.ui.alarm;

import static android.os.UserHandle.readFromParcel;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;

public class Alarm {
    private int alarmId;

    private int hour, minute;
    private boolean started, recurring;
    private String title;
    private boolean monday, tuesday, wednesday, thursday, friday, saturday, sunday;

    public Alarm(int alarmId, int hour, int minute, boolean started, String title, boolean recurring, boolean monday,
                 boolean tuesday, boolean wednesday, boolean thursday, boolean friday, boolean saturday, boolean sunday) {
        this.alarmId = alarmId;
        this.hour = hour;
        this.minute = minute;
        this.started = started;
        this.title = title;

        this.recurring =recurring;

        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
    }

    public void cancelAlarm(Context context, boolean isDelete) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context, alarmId, intent, 0);
        alarmManager.cancel(alarmPendingIntent);
        this.started = false;

        if (!isDelete) {
            String toastText = String.format("Alarm cancelled for %02d:%02d", hour, minute);
            Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show();
            Log.i("cancel", toastText);
        } else {
            Toast.makeText(context, "Alarm deleted", Toast.LENGTH_SHORT).show();
        }

    }

    public void schedule(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmReceiver.class);

        intent.putExtra("RECURRING", recurring);
        intent.putExtra("MONDAY", monday);
        intent.putExtra("TUESDAY", tuesday);
        intent.putExtra("WEDNESDAY", wednesday);
        intent.putExtra("THURSDAY", thursday);
        intent.putExtra("FRIDAY", friday);
        intent.putExtra("SATURDAY", saturday);
        intent.putExtra("SUNDAY", sunday);

        intent.putExtra("TITLE", title);

        PendingIntent alarmPendingIntent = PendingIntent.getBroadcast(context, alarmId, intent, 0);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // if alarm time has already passed, increment day by 1
        if (calendar.getTimeInMillis() <= System.currentTimeMillis()) {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        }

        if (!recurring) {
            String toastText = null;

            try {
                toastText = String.format("One Time Alarm %s scheduled for at %02d:%02d", title, hour, minute);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show();

            alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    alarmPendingIntent
            );
        } else {
            String toastText = String.format("Recurring Alarm %s scheduled for at %02d:%02d", title,  hour, minute);
            Toast.makeText(context, toastText, Toast.LENGTH_LONG).show();

            final long RUN_DAILY = 24 * 60 * 60 * 1000;
            alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    RUN_DAILY,
                    alarmPendingIntent
            );
        }

        this.started = true;
    }


    public int getAlarmId(){
        return alarmId;
    }
    public void setAlarmId(int alarmId) {
        this.alarmId = alarmId;
    }

    public int getHour(){
        return hour;
    }
    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute(){
        return minute;
    }
    public void setMinute(int minute) {
        this.minute = minute;
    }

    public boolean getStarted(){
        return started;
    }
    public void setStarted(boolean started) {
        this.started = started;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getRecurring() {
        return recurring;
    }

    public String getRecurringDaysText() {
        String recurringDays = "";

        if (this.monday) {
            recurringDays += "Mon ";
        }

        if (this.tuesday) {
            recurringDays += "Tue ";
        }

        if (this.wednesday) {
            recurringDays += "Wed ";
        }

        if (this.thursday) {
            recurringDays += "Thu ";
        }

        if (this.friday) {
            recurringDays += "Fri ";
        }

        if (this.saturday) {
            recurringDays += "Sat ";
        }

        if (this.sunday) {
            recurringDays += "Sun ";
        }

        return recurringDays;
    }
}
