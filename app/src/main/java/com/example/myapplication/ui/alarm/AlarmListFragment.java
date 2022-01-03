package com.example.myapplication.ui.alarm;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AlarmListFragment extends Fragment {
    private AlarmRecyclerviewAdapter alarmRecyclerViewAdapter;
    private RecyclerView alarmsRecyclerView;
    private Button addAlarm;
    private ArrayList<Alarm> alarms;
    private final String ARG_ALARM_LIST = "ARG_ALARM_LIST";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.alarms = PreferenceManager.getAlarms(getActivity(), ARG_ALARM_LIST);
        alarmRecyclerViewAdapter = new AlarmRecyclerviewAdapter(this::onToggle, alarms);
        /*
        alarmsListViewModel = ViewModelProviders.of(this).get(AlarmsListViewModel.class);
        alarmsListViewModel.getAlarmsLiveData().observe(this, new Observer<List<Alarm>>() {
            @Override
            public void onChanged(List<Alarm> alarms) {
                if (alarms != null) {
                    alarmRecyclerViewAdapter.setAlarms(alarms);
                }
            }
        });
        */
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_alarm_list, container, false);

        alarmsRecyclerView = view.findViewById(R.id.alarm_recyclerView);
        alarmsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        alarmsRecyclerView.setAdapter(alarmRecyclerViewAdapter);

        addAlarm = view.findViewById(R.id.add_alarm);
        addAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigation.findNavController(v).navigate(R.id.action_alarmsListFragment_to_createAlarmFragment);
                // ((MainActivity)getActivity()).alarmListToCreateAlarm(alarms);
                // addAlarm.setVisibility(View.GONE);

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View alarmView = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_create_alarm, null, false);
                builder.setView(alarmView);

                TimePicker timePicker = (TimePicker) alarmView.findViewById(R.id.fragment_createalarm_timePicker);
                EditText title = (EditText) alarmView.findViewById(R.id.fragment_createalarm_title);
                Button scheduleAlarm = (Button) alarmView.findViewById(R.id.fragment_createalarm_scheduleAlarm);

                final AlertDialog dialog = builder.create();
                scheduleAlarm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String alarmTitle = title.getText().toString();
                        int curHour = timePicker.getCurrentHour();
                        int curMinute = timePicker.getCurrentMinute();

                        scheduleAlarm(curHour, curMinute, alarmTitle);
                        alarmRecyclerViewAdapter.notifyDataSetChanged();

                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        return view;
    }

    public void onToggle(Alarm alarm) {
        if (alarm.getStarted()) {
            alarm.cancelAlarm(getContext(), false);
            PreferenceManager.setAlarms(getActivity(), ARG_ALARM_LIST, alarms);
            //alarmRecyclerViewAdapter.deleteAlarm(alarm);
            // alarmsListViewModel.update(alarm);
        } else {
            alarm.schedule(getContext());
            PreferenceManager.setAlarms(getActivity(), ARG_ALARM_LIST, alarms);
            //alarmRecyclerViewAdapter.setAlarm(alarm);
        }
    }

    private void scheduleAlarm(int hour, int minute, String title) {
        int alarmId = new Random().nextInt(Integer.MAX_VALUE);

        Alarm alarm = new Alarm(
                alarmId,
                hour,
                minute,
                true,
                title
        );

        alarm.schedule(getContext());
        alarms.add(alarm);

        PreferenceManager.setAlarms(getActivity(), ARG_ALARM_LIST, alarms);
    }
}
