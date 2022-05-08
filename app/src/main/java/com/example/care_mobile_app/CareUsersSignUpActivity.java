package com.example.care_mobile_app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class CareUsersSignUpActivity extends AppCompatActivity {

    TextView alreadyhaveaccount;

    //initialize variables
    EditText inputEmail,inputPaasword,inputConformPaasword;
    Button btncuserSingUp;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;


    FirebaseAuth mAuth;
    FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_users_sign_up);

        alreadyhaveaccount = findViewById(R.id.alreadyhaveaccount);

        inputEmail=findViewById(R.id.inputEmail);
        inputPaasword=findViewById(R.id.inputPaasword);
        inputConformPaasword=findViewById(R.id.inputConformPaasword);
        btncuserSingUp=findViewById(R.id.btncuserSingUp);
        progressDialog=new ProgressDialog(this);
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();



//link CareUsersSignUpActivity and CareUsersSignInActivity
        alreadyhaveaccount.setOnClickListener(view -> startActivity(new Intent(CareUsersSignUpActivity.this,CareUsersSignInActivity.class)));

         btncuserSingUp.setOnClickListener(view -> PerforAuth());

    }

    private void PerforAuth() {

        String email=inputEmail.getText().toString();
        String password=inputPaasword.getText().toString();
        String confrimPassword=inputConformPaasword.getText().toString();

        if(!email.matches(emailPattern))
        {
            inputEmail.setError("Enter Correct Email");
        }else if(password.isEmpty()|| password.length()<6)
        {
            inputPaasword.setError("Enter Proper Password");
        }else if(!password.equals(confrimPassword))
        {
            inputConformPaasword.setError("Password Not match both field");
        }else
        {
            progressDialog.setMessage("Please Wait  While Registration...");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();


            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
                progressDialog.dismiss();
                if(task.isSuccessful())
                 {
                     String uid=task.getResult().getUser().getUid();
                     CDuser cDuser = new CDuser( uid,inputEmail.getText().toString(),inputPaasword.getText().toString(),0);
                     FirebaseDatabase.getInstance().getReference().child("CDuser").child(uid).setValue(cDuser);
                     sendUserToNextActivity();
                     Toast.makeText(CareUsersSignUpActivity.this, "Registration Successfully", Toast.LENGTH_SHORT).show();
                 }else
                 {
                     Toast.makeText(CareUsersSignUpActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                 }
            });
        }

    }

    private void sendUserToNextActivity() {


        Intent intent = new Intent(CareUsersSignUpActivity.this,CareUsersSignInActivity.class);
        intent.setFlags((Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK));
        startActivity(intent);
    }
}