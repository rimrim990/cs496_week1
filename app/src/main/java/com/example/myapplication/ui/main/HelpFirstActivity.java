package com.example.myapplication.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

//import com.example.myapplication.ContactsRecViewAdapter;

import com.example.myapplication.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class HelpFirstActivity extends AppCompatActivity {
    public int position;
    private String text1;
    private String text2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_first);

        Intent intent = getIntent();
        text1 = intent.getStringExtra(ContactsRecViewAdapter.EXTRA_TEXT_1);
        text2 = intent.getStringExtra(ContactsRecViewAdapter.EXTRA_TEXT_2);
        position = intent.getIntExtra(ContactsRecViewAdapter.EXTRA_TEXT_3, 0);

        TextView textView1 = (TextView) findViewById(R.id.textview1);
        TextView textView2 = (TextView) findViewById(R.id.textview2);

        textView1.setText(text1);
        textView2.setText(text2);

        ImageButton messageButton = findViewById(R.id.callButton);
        messageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePhoneCall();
            }
        });
    }

    private void makePhoneCall(){
        String dial = "tel: " + text2;
        startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
    }
}