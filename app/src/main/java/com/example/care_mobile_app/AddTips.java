package com.example.care_mobile_app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.Loader;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddTips extends AppCompatActivity {

    EditText addTitle, addDescription, addImg;
    Button ADDbtn, BACKbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_tips);

        addTitle = findViewById(R.id.add_tip_title);
        addDescription = findViewById(R.id.add_tip_description);
        addImg = findViewById(R.id.add_img_url);

        ADDbtn = (Button) findViewById(R.id.btnInsertTip);
        BACKbtn = (Button) findViewById(R.id.btnBackTip);

        ADDbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertTips();
                clearAll();

            }
        });

        BACKbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddTips.this, TipsTricks.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void insertTips(){

        ADDbtn.setOnClickListener(view -> {
            final String Ttitle = addTitle.getText().toString().trim();
            final String Tdescription = addDescription.getText().toString().trim();
            final String Turl = addImg.getText().toString().trim();

            //check all fields empty
            if(TextUtils.isEmpty(Ttitle)){
                addTitle.setError("Title is required!");
                return;
            }
            if(TextUtils.isEmpty(Tdescription)){
                addDescription.setError("Description is required!");
                return;
            }
            if(TextUtils.isEmpty(Turl)){
                addImg.setError("Image URL is required!");
                return;
            }
            else {

                Map<String, Object> map = new HashMap<>();
                map.put("title", addTitle.getText().toString());
                map.put("description", addDescription.getText().toString());
                map.put("turl", addImg.getText().toString());

                FirebaseDatabase.getInstance().getReference().child("Tips").push()
                        .setValue(map)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(AddTips.this, "Data Inserted Successfully.", Toast.LENGTH_SHORT).show();
                                clearAll();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(Exception e) {
                                Toast.makeText(AddTips.this, "Error while Inserting Data.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    private void clearAll(){
        addTitle.setText("");
        addDescription.setText("");
        addImg.setText("");
    }
}