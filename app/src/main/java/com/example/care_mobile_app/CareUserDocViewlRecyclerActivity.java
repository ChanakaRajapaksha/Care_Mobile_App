package com.example.care_mobile_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class CareUserDocViewlRecyclerActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CareUserDocViewlRecycleAdapter mainAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_user_doc_viewl_recycler);

        recyclerView = findViewById(R.id.rvuserview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<CareUserDocViewlRecycleModel> options =
                new FirebaseRecyclerOptions.Builder<CareUserDocViewlRecycleModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Doctors"), CareUserDocViewlRecycleModel.class)
                        .build();

        mainAdapter = new CareUserDocViewlRecycleAdapter(options);
        recyclerView.setAdapter(mainAdapter);



    }

    @Override
    protected void onStart() {
        super.onStart();
        mainAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mainAdapter.startListening();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.user_search_doc,menu);
        MenuItem item= menu.findItem(R.id.usersearchdoc);
        SearchView searchView = (SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                txtSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                txtSearch(query);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void txtSearch(String str)
    {
        FirebaseRecyclerOptions<CareUserDocViewlRecycleModel> options =
                new FirebaseRecyclerOptions.Builder<CareUserDocViewlRecycleModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Doctors").orderByChild("specialization").startAt(str).endAt(str+"~"),CareUserDocViewlRecycleModel.class)
                        .build();

        mainAdapter = new CareUserDocViewlRecycleAdapter(options);
        mainAdapter.startListening();
        recyclerView.setAdapter(mainAdapter);
    }

}