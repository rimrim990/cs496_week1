package com.example.myapplication.ui.alarm;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
                ((MainActivity)getActivity()).alarmListToCreateAlarm(alarms);
                addAlarm.setVisibility(View.GONE);
            }
        });

        return view;
    }

    public void onToggle(Alarm alarm) {
        if (alarm.getStarted()) {
            alarm.cancelAlarm(getContext());
            alarmRecyclerViewAdapter.deleteAlarm(alarm);
            // alarmsListViewModel.update(alarm);
        } else {
            alarm.schedule(getContext());
            alarmRecyclerViewAdapter.setAlarm(alarm);
            // alarmsListViewModel.update(alarm);
        }
    }
}
