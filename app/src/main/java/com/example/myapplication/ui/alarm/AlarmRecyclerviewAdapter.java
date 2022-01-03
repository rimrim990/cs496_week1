package com.example.myapplication.ui.alarm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class AlarmRecyclerviewAdapter extends RecyclerView.Adapter<AlarmViewHolder>{

    // adapter 에 들어갈 list
    private ArrayList<Alarm> alarms;
    private OnToggleAlarmListener listener;

    // constructor
    public AlarmRecyclerviewAdapter(OnToggleAlarmListener listener, ArrayList<Alarm> alarms) {
        this.alarms = alarms;
        this.listener = listener;
    }

    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_alarm_item, parent, false);
        //return new ViewHolderItem(view, mItemClickListener);

        return new AlarmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmViewHolder holder, int position) {
        Alarm alarm = alarms.get(position);
        holder.bind(alarm, listener);
    }

    @Override
    public int getItemCount() {
        return alarms.size();
    }

    @Override
    public void onViewRecycled(@NonNull AlarmViewHolder holder) {
        super.onViewRecycled(holder);
        holder.alarmStarted.setOnCheckedChangeListener(null);
    }

    public void setAlarm(Alarm alarm) {
        this.alarms.add(alarm);
    }

    public void deleteAlarm(Alarm alarm){
        this.alarms.remove(alarm);
    }
}




