package com.example.care_mobile_app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorRegistrationActivity extends AppCompatActivity {



   private EditText docRegName,docRegspecial,docRegworkhos,docRegWokPlace,docRegWorkHours,PhoneNumregDoc,emailDocReg,docRegPassword;
   private Button docRegbtnSubmit;
   private CircleImageView profilepicdocreg;

   private Uri resultUri;

   private FirebaseAuth mAuth;
   private DatabaseReference userDatabaseRef;
   private ProgressDialog loader;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_registration);



        docRegName = findViewById(R.id.docRegName);
        docRegspecial = findViewById(R.id.docRegspecial);
        docRegworkhos = findViewById(R.id.docRegworkhos);
        docRegWokPlace = findViewById(R.id.docRegWokPlace);
        docRegWorkHours = findViewById(R.id.docRegWorkHours);
        PhoneNumregDoc = findViewById(R.id.PhoneNumregDoc);
        emailDocReg = findViewById(R.id.emailDocReg);
        docRegPassword = findViewById(R.id.docRegPassword);
        docRegbtnSubmit = findViewById(R.id.docRegbtnSubmit);
        profilepicdocreg = findViewById(R.id.profilepicdocreg);



        profilepicdocreg.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent,1);
        });

        loader = new ProgressDialog(this);
         mAuth = FirebaseAuth.getInstance();

        docRegbtnSubmit.setOnClickListener(view -> {
            final String name = docRegName.getText().toString().trim();
            final String specialization = docRegspecial.getText().toString().trim();
            final String whospital = docRegworkhos.getText().toString().trim();
            final String wplace = docRegWokPlace.getText().toString().trim();
            final String whours = docRegWorkHours.getText().toString().trim();
            final String phonenum = PhoneNumregDoc.getText().toString().trim();
            final String email = emailDocReg.getText().toString().trim();
            final String password = docRegPassword.getText().toString().trim();

            if(TextUtils.isEmpty(name)){
                docRegName.setError("Name is required!");
                return;
            }
            if(TextUtils.isEmpty(specialization)){
                docRegspecial.setError("specialization is required!");
                return;
            }
            if(TextUtils.isEmpty(whospital)){
                docRegworkhos.setError("Working hospital is required!");
                return;
            }
            if(TextUtils.isEmpty(wplace)){
                docRegWokPlace.setError("Working place is required!");
                return;
            }  if(TextUtils.isEmpty(whours)){
                docRegWorkHours.setError("Working hours is required!");
                return;
            }
            if(TextUtils.isEmpty(phonenum)){
                PhoneNumregDoc.setError("Contact Number is required!");
                return;
            }
            if(TextUtils.isEmpty(email)){
                emailDocReg.setError("Email is required!");
                return;
            }
            if(TextUtils.isEmpty(password)){
                docRegPassword.setError("Password is required!");

            }
            if(resultUri == null){
                Toast.makeText(DoctorRegistrationActivity.this, "Profile is required!",Toast.LENGTH_SHORT).show();
            }
            else{
                loader.setMessage("Registration in progress...");
                loader.setCanceledOnTouchOutside(false);
                loader.show();

                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            String error = task.getException().toString();
                            Toast.makeText(DoctorRegistrationActivity.this,"Error Occured"+error, Toast.LENGTH_SHORT).show();
                        }
                        else {
                            String currentUserId = mAuth.getCurrentUser().getUid();
                            userDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Doctors").child(currentUserId);
                            HashMap userInfo = new HashMap();
                            userInfo.put("name", name);
                            userInfo.put("specialization", specialization);
                            userInfo.put("hospital", whospital);
                            userInfo.put("workplace", wplace);
                            userInfo.put("whours", whours);
                            userInfo.put("phonenumber", phonenum);
                            userInfo.put("email", email);
                            userInfo.put("type", "doctor");

                            userDatabaseRef.updateChildren(userInfo).addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(DoctorRegistrationActivity.this, "Details Set Successfully",Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(DoctorRegistrationActivity.this,task.getException().toString(), Toast.LENGTH_SHORT).show();

                                    }
                                    finish();
                                    loader.dismiss();
                                }
                            });

                            // upload image registration....................................

                            if(resultUri !=null){
                                final StorageReference filepath= FirebaseStorage.getInstance().getReference().child("profile picture").child(currentUserId);
                                Bitmap bitmap = null;
                                try {
                                    bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), resultUri);
                                }catch (IOException e){
                                    e.printStackTrace();
                                }

                                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);
                                byte[] data = byteArrayOutputStream.toByteArray();

                                UploadTask uploadTask = filepath.putBytes(data);
                                uploadTask.addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        finish();
                                    }
                                });
                                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        if (taskSnapshot.getMetadata() != null) {
                                            Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                                            result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    String imageUrl = uri.toString();
                                                    Map newImageMap = new HashMap();
                                                    newImageMap.put("profilepictureurl", imageUrl);

                                                    userDatabaseRef.updateChildren(newImageMap).addOnCompleteListener(new OnCompleteListener() {
                                                        @Override
                                                        public void onComplete(@NonNull Task task) {
                                                            if (task.isSuccessful()) {
                                                                Toast.makeText(DoctorRegistrationActivity.this, "Reg successs", Toast.LENGTH_SHORT).show();
                                                            } else {
                                                                Toast.makeText(DoctorRegistrationActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                                    finish();
                                                }
                                            });
                                        }
                                    }
                                });
                                Intent intent = new Intent(DoctorRegistrationActivity.this,CareAdminDocViewlRecyclerActivity.class);
                                startActivity(intent);
                                finish();
                                loader.dismiss();

                            }



                        }
                    }
                });

            }



        });

    }

    @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode ==1 && resultCode == Activity.RESULT_OK && data!=null){
                resultUri = data.getData();
                profilepicdocreg.setImageURI(resultUri);

            }
    }
}