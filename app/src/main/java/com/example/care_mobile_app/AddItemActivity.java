package com.example.care_mobile_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddItemActivity extends AppCompatActivity {

    private Button postBtn,backBtn;
    private ImageView imageView;
    private ProgressBar progressBar;
    private final DatabaseReference root = FirebaseDatabase.getInstance().getReference().child("item");
    private final StorageReference reference= FirebaseStorage.getInstance().getReference();
    private Uri img;
    EditText des,name,number,price,title;
    AutoCompleteTextView category,con,district;

    String[] dis = {"Colombo","Gampaha","Kalutara","Kandy","Matale","Nuwara Eliya","Galle",
    "Matara","Hambantota","Jaffna","Kilinochchi","Mannar","Vavuniya","Mullaitivu","Batticaloa",
    "Ampara","Trincomalee","Kurunegala","Puttalam","Anuradhapura","Polonnaruwa","Badulla","Moneragala",
    "Ratnapura","Kegalle"};

    String[] cat= {"Health","Fitness"};

    String[] condition = {"Brand New","Used like Brand New","Used","Out of Stock"};

    AutoCompleteTextView autoCompleteDistrict;
    AutoCompleteTextView autoCompleteCategory;
    AutoCompleteTextView autoCompleteCondition;
    ArrayAdapter<String> adapterDistrict;
    ArrayAdapter<String> adapterCategory;
    ArrayAdapter<String> adapterCondition;

    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        category = (AutoCompleteTextView)findViewById(R.id.auto_complete_category);
        con = (AutoCompleteTextView)findViewById(R.id.auto_complete_condition);
        des = (EditText)findViewById(R.id.txtDescription);
        district = (AutoCompleteTextView)findViewById(R.id.auto_complete_district);
        name = (EditText)findViewById(R.id.txtName);
        number = (EditText)findViewById(R.id.txtNumber);
        price = (EditText)findViewById(R.id.txtPrice);
        title = (EditText)findViewById(R.id.txtTitle);

        backBtn = (Button)findViewById(R.id.btnBack);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        autoCompleteDistrict = findViewById(R.id.auto_complete_district);
        autoCompleteCategory = findViewById(R.id.auto_complete_category);
        autoCompleteCondition = findViewById(R.id.auto_complete_condition);
        adapterDistrict = new ArrayAdapter<String>(this,R.layout.list_district,dis);
        adapterCategory = new ArrayAdapter<String>(this,R.layout.list_district,cat);
        adapterCondition = new ArrayAdapter<String>(this,R.layout.list_district,condition);
        autoCompleteDistrict.setAdapter(adapterDistrict);
        autoCompleteCategory.setAdapter(adapterCategory);
        autoCompleteCondition.setAdapter(adapterCondition);
        autoCompleteDistrict.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String district = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "District: "+district, Toast.LENGTH_SHORT).show();
            }
        });
        autoCompleteCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String category = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Category: "+category, Toast.LENGTH_SHORT).show();
            }
        });
        autoCompleteCondition.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String condition = parent.getItemAtPosition(position).toString();
                Toast.makeText(getApplicationContext(), "Condition: "+condition, Toast.LENGTH_SHORT).show();
            }
        });

        //create upload image

        postBtn = findViewById(R.id.btnPost);
        progressBar = findViewById(R.id.progressBar);
        imageView = findViewById(R.id.upload_img);

        progressBar.setVisibility(View.INVISIBLE);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 2);
            }
        });
        
        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
                clearAll();
                if(img != null) {
                    uploadToFirebase(img);
                } else {
                    Toast.makeText(AddItemActivity.this, "Please Select Image", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 2 && resultCode == RESULT_OK && data != null) {
                img = data.getData();
                imageView.setImageURI(img);
        }
    }

    private void uploadToFirebase(Uri uri) {
        StorageReference fileRef = reference.child(System.currentTimeMillis() + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        ItemModel itemModel = new ItemModel(uri.toString());
                        String item = root.push().getKey();
                        assert item != null;
                        root.child(item).setValue(itemModel);
                        progressBar.setVisibility(View.INVISIBLE);
                        Toast.makeText(AddItemActivity.this, " Image Uploaded Successfully", Toast.LENGTH_SHORT).show();
                        imageView.setImageResource(R.drawable.ic_baseline_add_photo_alternate_24);
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                progressBar.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(AddItemActivity.this, "Uploading Failed !!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getFileExtension(Uri mUri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));
    }

    private void insertData() {
        Map<String,Object> map = new HashMap<>();

        map.put("name",name.getText().toString());
        map.put("title",title.getText().toString());
        map.put("district",district.getText().toString());
        map.put("number",number.getText().toString());
        map.put("category",category.getText().toString());
        map.put("price",price.getText().toString());
        map.put("condition",con.getText().toString());
        map.put("description",des.getText().toString());

        FirebaseDatabase.getInstance().getReference().child("item").push()
                .setValue(map)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddItemActivity.this, "Data Inserted Successfully", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(AddItemActivity.this, "Error while Insertion", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void clearAll() {
        name.setText("");
        title.setText("");
        district.setText("");
        number.setText("");
        category.setText("");
        price.setText("");
        con.setText("");
        des.setText("");
    }

}