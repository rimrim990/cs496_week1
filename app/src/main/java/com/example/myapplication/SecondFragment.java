package com.example.myapplication;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.MediaStore;
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

    private static final int PERMISSION_REQUEST_CODE = 200;

    //private ArrayList<BearItem> listData = new ArrayList<BearItem>();
    private ArrayList<String> imagePaths = new ArrayList<String>();

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

        adapter = new RecyclerviewAdapter(mContext, imagePaths);
        //adapter = new RecyclerviewAdapter(listData);

        // we are calling a method to request
        // the permissions to read external storage.
        requestPermissions();

        recyclerView.setAdapter(adapter);

        // Adapter 안에 아이템의 정보 담기
        /*
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
        */

        /*
        adapter.setOnItemClickListener(new RecyclerviewAdapter.OnItemClickEventListener() {
            @Override
            public void onItemClick(View view, int pos) {

                Intent intent = new Intent(getActivity(), ImageDetailActivity.class);
                intent.putExtra("imagePath", imagePaths.get(pos));
                //intent.putExtra("imageId", listData.get(pos).getResId());
                // intent.putExtra("imageName", listData.get(pos).getName());

                startActivity(intent);
            }
        });
        */

        return view;
    }

    private boolean checkPermission() {
        // in this method we are checking if the permissions are granted or not and returning the result.
        int result = ActivityCompat.checkSelfPermission(requireActivity(), READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        if (checkPermission()) {
            // if the permissions are already granted we are calling
            // a method to get all images from our external storage.
            Toast.makeText(mContext, "Permission granted..", Toast.LENGTH_SHORT).show();
            getImagePath();
        } else {
            // if the permissions are not granted we are
            // calling a method to request permissions.
            Toast.makeText(mContext, "requestPermission..", Toast.LENGTH_SHORT).show();
            requestPermission();
        }
    }

    private void requestPermission() {
        // on below line we are requesting the rea external storage permissions.
        ActivityCompat.requestPermissions(requireActivity(), new String[]{READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        //requestPermissions(new String[]{READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    private void getImagePath() {
        // in this method we are adding all our image paths
        // in our arraylist which we have created.
        // on below line we are checking if the device is having an sd card or not.
        boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);

        if (isSDPresent) {

            // if the sd card is present we are creating a new list in
            // which we are getting our images data with their ids
            final String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};

            // on below line we are creating a new
            // string to order our images by string
            final String orderBy = MediaStore.Images.Media._ID;

            // this method will stores all the images
            // from the gallery in Cursor
            Cursor cursor = mContext.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy);

            // below line is to get total number of images
            int count = cursor.getCount();

            // on below line we are running a loop to add
            // the image file path in our array list/
            for (int i=0; i < count; i++) {

                // on below line we are moving our cursor position
                cursor.moveToPosition(i);

                // on below line we are getting image file path
                int dataColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);

                // after that we are getting the image file path
                // and adding that path in our array list.
                imagePaths.add(cursor.getString(dataColumnIndex));
            }

            adapter.notifyDataSetChanged();
            // after adding the data to our
            // array list we are closing our cursor.
            cursor.close();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        // this method is called after permissions has benn granted.
        switch(requestCode) {
            // we are checking the permission code.
            case PERMISSION_REQUEST_CODE:
                // in this case we are checking if the permissions are accepted or not.
                if (grantResults.length > 0) {
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (storageAccepted) {
                        // if the permissions are accepted we are displaying a toast message
                        // and calling a method to get image path.
                        Toast.makeText(mContext, "Permission Granted..", Toast.LENGTH_SHORT).show();
                        getImagePath();
                    } else {
                        // if permissions are denied we are closing the app and displaying the toast message
                        if (ActivityCompat.shouldShowRequestPermissionRationale(
                                requireActivity(),
                                READ_EXTERNAL_STORAGE
                        )) {
                            Log.d("TAG", "User declined, but i can still ask for more");
                            ActivityCompat.requestPermissions(
                                    requireActivity(),
                                    new String[]{READ_EXTERNAL_STORAGE},
                                    PERMISSION_REQUEST_CODE
                            );
                        } else {
                            Log.d("TAG", "User declined and i can't ask");
                            Toast.makeText(mContext, "Permission denied, Permissions are required to use the app..", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        }
    }
}