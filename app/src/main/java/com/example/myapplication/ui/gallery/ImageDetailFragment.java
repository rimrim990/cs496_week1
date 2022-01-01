package com.example.myapplication.ui.gallery;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;

import java.io.File;

public class ImageDetailFragment extends Fragment {

    private static final String ARG_PARAM1 = "imgPath";

    private ImageView fullView;
    private ScaleGestureDetector scaleGestureDetector;

    private float mScaleFactor = 1.0f;

    private String imgPath;
    private Context mContext;

    public ImageDetailFragment() {
        // empty constructor
    }

    public static ImageDetailFragment newInstance(String imgPath) {
        Bundle args = new Bundle();
        args.putString("imgPath", imgPath);
        ImageDetailFragment fragment = new ImageDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            imgPath = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
<<<<<<< HEAD
<<<<<<< HEAD
        View view = inflater.inflate(R.layout.fragment_image_detail, container, false);
=======
        View view = inflater.inflate(R.layout.activity_image_detail, container, false);
>>>>>>> 7c8d952 (gallery finish)
=======
        View view = inflater.inflate(R.layout.activity_image_detail, container, false);
>>>>>>> 7c8d952 (gallery finish)
        fullView = (ImageView)view.findViewById(R.id.full_iv_icon);

        fullView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                // inside on touch event method we are calling on
                // touch event method and passing our motion event to it.
                scaleGestureDetector.onTouchEvent(motionEvent);
                return true;
            }
        });

        // initializing our scale gesture detector for zoom in and out for our image.
        scaleGestureDetector = new ScaleGestureDetector(mContext, new ScaleListener());

        File imageFile = new File(imgPath);

        // if the file exits then we are loading that image in our image view.
        if (imageFile.exists()) {
            Glide.with(mContext).load(imageFile).into(fullView);
        }

        /*
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imageFile.exists()) {
                    imageFile.delete();
                    mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(new File(imgPath))));
                }
            }
        });
        */

        return view;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        // creating a class for our scale
        // listener and extending it with gesture listener.
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {

            // inside on scale method we are setting scale
            // for our image in our image view.
            mScaleFactor *= scaleGestureDetector.getScaleFactor();
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 10.0f));

            // setting scale x and scale y to our image view.
            fullView.setScaleX(mScaleFactor);
            fullView.setScaleY(mScaleFactor);
            return true;
        }
    }

}
