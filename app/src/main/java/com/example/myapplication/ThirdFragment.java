package com.example.myapplication;

import static android.content.Context.ALARM_SERVICE;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.myapplication.ui.alarm.AlarmReceiver;

import java.text.DateFormat;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThirdFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThirdFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private TextView mTextView;
    TimePicker alarmTimePicker;
    PendingIntent pendingIntent;
    AlarmManager alarmManager;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ThirdFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ThirdFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ThirdFragment newInstance(String param1, String param2) {
        ThirdFragment fragment = new ThirdFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_third, container, false);


        alarmTimePicker = (TimePicker) mView.findViewById(R.id.timePicker);
        alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);

        mTextView = mView.findViewById(R.id.textView);
        ToggleButton toggleButton = mView.findViewById(R.id.toggleButton);

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                long time;
                if(toggleButton.isChecked()){
                    Toast.makeText(getActivity(), "ALARM ON", Toast.LENGTH_SHORT).show();
                    Calendar calendar = Calendar.getInstance();

                    // calendar is called to get current time in hour and minute
                    calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getCurrentHour());
                    calendar.set(Calendar.MINUTE, alarmTimePicker.getCurrentMinute());

                    // using intent i have class AlarmReceiver class which inherits
                    // BroadcastReceiver
                    Intent intent = new Intent(getActivity(), AlarmReceiver.class);

                    // we call broadcast using pendingIntent
                    pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, intent, 0);

                    time = (calendar.getTimeInMillis() - (calendar.getTimeInMillis() % 60000));
                    if (System.currentTimeMillis() > time) {
                        // setting time as AM and PM
                        if (calendar.AM_PM == 0)
                            time = time + (1000 * 60 * 60 * 12);
                        else
                            time = time + (1000 * 60 * 60 * 24);
                    }
                    String timeText = "Alarm set for: ";
                    timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime());
                    mTextView.setText(timeText);

                    // Alarm rings continuously until toggle button is turned off
                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time, 10000, pendingIntent);
                    // alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (time * 1000), pendingIntent);
                }
                else{
                    alarmManager.cancel(pendingIntent);
                    mTextView.setText("Alarm Canceled");
                    Toast.makeText(getActivity(), "ALARM OFF", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return mView;
    }

//    public void OnToggleClicked(View view) {
//        long time;
//        if (((ToggleButton) view).isChecked()) {
//            Toast.makeText(getActivity(), "ALARM ON", Toast.LENGTH_SHORT).show();
//            Calendar calendar = Calendar.getInstance();
//
//            // calendar is called to get current time in hour and minute
//            calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getCurrentHour());
//            calendar.set(Calendar.MINUTE, alarmTimePicker.getCurrentMinute());
//
//            // using intent i have class AlarmReceiver class which inherits
//            // BroadcastReceiver
//            Intent intent = new Intent(getActivity(), AlarmReceiver.class);
//
//            // we call broadcast using pendingIntent
//            pendingIntent = PendingIntent.getBroadcast(getActivity(), 0, intent, 0);
//
//            time = (calendar.getTimeInMillis() - (calendar.getTimeInMillis() % 60000));
//            if (System.currentTimeMillis() > time) {
//                // setting time as AM and PM
//                if (calendar.AM_PM == 0)
//                    time = time + (1000 * 60 * 60 * 12);
//                else
//                    time = time + (1000 * 60 * 60 * 24);
//            }
//            // Alarm rings continuously until toggle button is turned off
//            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time, 10000, pendingIntent);
//            // alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + (time * 1000), pendingIntent);
//        } else {
//            alarmManager.cancel(pendingIntent);
//            Toast.makeText(getActivity(), "ALARM OFF", Toast.LENGTH_SHORT).show();
//        }
//    }
}