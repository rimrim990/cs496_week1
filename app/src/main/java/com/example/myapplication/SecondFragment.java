package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SecondFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SecondFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private String TAG = SecondFragment.class.getSimpleName();

    private Context mContext;

    private RecyclerviewAdapter adapter;
    private RecyclerView recyclerView;

    MainActivity activity;

    public SecondFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SecondFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SecondFragment newInstance(String param1, String param2) {
        SecondFragment fragment = new SecondFragment();
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
    public void onAttach(Context context){
        super.onAttach(context);

        mContext = context;
        activity = (MainActivity)getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        activity = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_second, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter = new RecyclerviewAdapter();

        recyclerView.setAdapter(adapter);

        // Adapter 안에 아이템의 정보 담기
        adapter.addItem(new BearItem("1", "테스트", R.drawable.ic_launcher_foreground));
        adapter.addItem(new BearItem("2", "테스트", R.drawable.ic_launcher_foreground));
        adapter.addItem(new BearItem("3", "테스트", R.drawable.ic_launcher_foreground));
        adapter.addItem(new BearItem("4", "테스트", R.drawable.ic_launcher_foreground));
        adapter.addItem(new BearItem("5", "테스트", R.drawable.ic_launcher_foreground));
        adapter.addItem(new BearItem("6", "테스트", R.drawable.ic_launcher_foreground));
        adapter.addItem(new BearItem("7", "테스트", R.drawable.ic_launcher_foreground));
        adapter.addItem(new BearItem("8", "테스트", R.drawable.ic_launcher_foreground));
        adapter.addItem(new BearItem("9", "테스트", R.drawable.ic_launcher_foreground));
        adapter.addItem(new BearItem("10", "테스트", R.drawable.ic_launcher_foreground));
        adapter.addItem(new BearItem("11", "테스트", R.drawable.ic_launcher_foreground));
        adapter.addItem(new BearItem("12", "테스트", R.drawable.ic_launcher_foreground));
        adapter.addItem(new BearItem("13", "테스트", R.drawable.ic_launcher_foreground));
        adapter.addItem(new BearItem("14", "테스트", R.drawable.ic_launcher_foreground));
        adapter.addItem(new BearItem("15", "테스트", R.drawable.ic_launcher_foreground));
        adapter.addItem(new BearItem("16", "테스트", R.drawable.ic_launcher_foreground));
        adapter.addItem(new BearItem("17", "테스트", R.drawable.ic_launcher_foreground));
        adapter.addItem(new BearItem("18", "테스트", R.drawable.ic_launcher_foreground));
        adapter.addItem(new BearItem("19", "테스트", R.drawable.ic_launcher_foreground));
        adapter.addItem(new BearItem("20", "테스트", R.drawable.ic_launcher_foreground));

        adapter.setOnItemClickListener(new RecyclerviewAdapter.OnItemClickEventListener() {
            @Override
            public void onItemClick(View view, int pos) {
                String imageUrl = "./";

                Intent intent = new Intent(getActivity(), ImageDetailActivity.class);
                intent.putExtra("imageUrl", imageUrl);

                startActivity(intent);
            }
        });

        return view;
    }
}