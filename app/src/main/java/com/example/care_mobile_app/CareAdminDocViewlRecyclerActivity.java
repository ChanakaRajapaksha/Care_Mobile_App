package com.example.care_mobile_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class CareAdminDocViewlRecyclerActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    CareAdminDocViewlRecycleAdapter mainAdapter;

    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_admin_doc_viewl_recycler);


        recyclerView = findViewById(R.id.adminrvuserview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<CareAdminDocViewlRecycleModel> options =
                new FirebaseRecyclerOptions.Builder<CareAdminDocViewlRecycleModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Doctors"), CareAdminDocViewlRecycleModel.class)
                        .build();

        mainAdapter = new CareAdminDocViewlRecycleAdapter(options);
        recyclerView.setAdapter(mainAdapter);


        floatingActionButton = findViewById(R.id.adminfdocaddButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CareAdminDocViewlRecyclerActivity.this,DoctorRegistrationActivity.class));
            }
        });



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

        getMenuInflater().inflate(R.menu.admin_search_doc,menu);
        MenuItem item= menu.findItem(R.id.adminsearchdoc);
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
        FirebaseRecyclerOptions<CareAdminDocViewlRecycleModel> options =
                new FirebaseRecyclerOptions.Builder<CareAdminDocViewlRecycleModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Doctors").orderByChild("name").startAt(str).endAt(str+"~"),CareAdminDocViewlRecycleModel.class)
                        .build();

        mainAdapter = new CareAdminDocViewlRecycleAdapter(options);
        mainAdapter.startListening();
        recyclerView.setAdapter(mainAdapter);
    }

}
