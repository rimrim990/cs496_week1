package com.example.myapplication.ui.alarm;

import static android.content.Context.ALARM_SERVICE;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.ui.alarm.AlarmReceiver;
import com.example.myapplication.ui.alarm.AlarmService;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class CreateAlarmFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_ALARM_LIST = "ARG_ALARM_LIST";

    TimePicker timePicker;
    EditText title;
    Button scheduleAlarm;
    CheckBox mon;
    CheckBox tue;
    CheckBox wed;
    CheckBox thu;
    CheckBox fri;
    CheckBox sat;
    CheckBox sun;

    ArrayList<Alarm> alarms;

    TimePicker tp;

    // TODO: Rename and change types of parameters
    //private String mParam1;
    // private String mParam2;

    public CreateAlarmFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static CreateAlarmFragment newInstance(ArrayList<Alarm> alarms) {
        CreateAlarmFragment fragment = new CreateAlarmFragment();
        Bundle args = new Bundle();
        // args.putString(ARG_PARAM1, param1);
        // args.putString(ARG_PARAM2, param2);
        args.putSerializable("ARG_ALARM_LIST", alarms);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // if (getArguments() != null) {
            // mParam1 = getArguments().getString(ARG_PARAM1);
            // mParam2 = getArguments().getString(ARG_PARAM2);
        // }
        if (getArguments() != null) {
            this.alarms = (ArrayList<Alarm>) getArguments().getSerializable("ARG_ALARM_LIST");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.fragment_create_alarm, container, false);

        timePicker = mView.findViewById(R.id.fragment_createalarm_timePicker);
        title = mView.findViewById(R.id.fragment_createalarm_title);
        scheduleAlarm = mView.findViewById(R.id.fragment_createalarm_scheduleAlarm);

        tp = (TimePicker) mView.findViewById(R.id.fragment_createalarm_timePicker);

        scheduleAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scheduleAlarm();
                ((MainActivity)getActivity()).popBackStack();
            }
        });

        return mView;
    }

    private void scheduleAlarm() {
        int alarmId = new Random().nextInt(Integer.MAX_VALUE);

        Alarm alarm = new Alarm(
                alarmId,
                tp.getCurrentHour(),
                tp.getCurrentMinute(),
                true,
                title.getText().toString()
        );

        alarm.schedule(getContext());
        alarms.add(alarm);

        PreferenceManager.setAlarms(getActivity(), ARG_ALARM_LIST, alarms);
    }
}
