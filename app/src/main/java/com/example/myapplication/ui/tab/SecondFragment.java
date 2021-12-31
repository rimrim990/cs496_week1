package com.example.myapplication.ui.tab;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_OK;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private MainActivity activity;

    private ArrayList<String> imagePaths = new ArrayList<String>();

    private static boolean PERMISSION;
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    String currentPhotoPath;

    public SecondFragment(boolean PERMISSION) {
        // Required empty public constructor
        this.PERMISSION = PERMISSION;
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
        SecondFragment fragment = new SecondFragment(PERMISSION);
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

        FloatingActionButton button = (FloatingActionButton)view.findViewById(R.id.cameraBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePhotoIntent();
            }

        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter = new RecyclerviewAdapter(mContext, imagePaths);

        // we are calling a method to request
        // the permissions to read external storage.
        // requestPermissions();

        if (PERMISSION) {
            getImagePath();
        }

        recyclerView.setAdapter(adapter);

        return view;
    }

    private void dispatchTakePhotoIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Ensure that there's a camera activity to handle the intent
        if(takePictureIntent.resolveActivity(activity.getPackageManager()) != null) {

            // Create the File where a photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch(IOException e) {
                // ...error Occurred while creating the File
                e.printStackTrace();
            }

            // Continue only if File was successfully created.
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(mContext, "com.example.myapplication", photoFile);
                // Uri photoURI = Uri.fromFile(photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            imagePaths.add(String.valueOf(currentPhotoPath));
            adapter.notifyDataSetChanged();

            galleryAddPic();
        }
    }

    private File createImageFile() throws IOException {

        // Create an Image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        File storageDir = new File(Environment.getExternalStorageDirectory() + "/DCIM/Camera");
        File image = File.createTempFile(
                imageFileName, /* prefix */
                ".jpg",
                storageDir
        );

        // Save a file : path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void galleryAddPic() {
        File f = new File(currentPhotoPath);
        MediaScannerConnection.scanFile(mContext, new String[]{f.toString()}, null, null);
    }

    /*
    private void requestPermissions() {
        if (ActivityCompat.checkSelfPermission(activity, WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            // if the permissions are already granted we are calling
            // a method to get all images from our external storage.
            Toast.makeText(mContext, "Permission granted..", Toast.LENGTH_SHORT).show();
            getImagePath();
        } else {
            // if the permissions are not granted we are
            // calling a method to request permissions.
            requestPermission();
        }
    }

    private void requestPermission() {
        // on below line we are requesting the rea external storage permissions.
        ActivityCompat.requestPermissions(requireActivity(), new String[]{WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }
    */

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

    /*
    // function to check permission
    private boolean checkAndRequestPermissions() {
        int WExtstorePermission = ActivityCompat.checkSelfPermission(activity, WRITE_EXTERNAL_STORAGE);
        int cameraPermission = ActivityCompat.checkSelfPermission(activity, CAMERA);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (cameraPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(WRITE_EXTERNAL_STORAGE);
        }
        if (WExtstorePermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(WRITE_EXTERNAL_STORAGE);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(activity, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),
                    PERMISSION_REQUEST_CODE);
            return false;
        }
        return true;
    }

    // handled permission result
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch(requestCode) {
            case PERMISSION_REQUEST_CODE:
                if(ActivityCompat.checkSelfPermission(activity, CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(mContext, "FlagUp Requires Access to Camera.", Toast.LENGTH_SHORT);
                } else if (ActivityCompat.checkSelfPermission(mContext, WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(mContext, "FlagUp Requires Access to Your Storage.", Toast.LENGTH_SHORT).show();
                } else {
                    getImagePath();
                }
                break;
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
                                WRITE_EXTERNAL_STORAGE
                        )) {
                            Log.d("TAG", "User declined, but i can still ask for more");
                            ActivityCompat.requestPermissions(
                                    requireActivity(),
                                    new String[]{WRITE_EXTERNAL_STORAGE},
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
    */
}