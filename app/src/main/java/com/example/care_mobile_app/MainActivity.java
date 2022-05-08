package com.example.care_mobile_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button getstartbutton = findViewById(R.id.getstartbutton);
        getstartbutton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,CareUsersSignInActivity.class);
            startActivity(intent);
        });

    }
}