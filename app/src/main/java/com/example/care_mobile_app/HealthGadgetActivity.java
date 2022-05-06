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

public class HealthGadgetActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ItemAdapter itemAdapter;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_gadget);

        recyclerView = (RecyclerView)findViewById(R.id.recView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<ItemModel> options =
                new FirebaseRecyclerOptions.Builder<ItemModel>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("item"),ItemModel.class)
                .build();

        itemAdapter = new ItemAdapter(options);
        recyclerView.setAdapter(itemAdapter);

        floatingActionButton = (FloatingActionButton)findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AddItemActivity.class));
            }
        });
    }

    public HealthGadgetActivity() {
        super();
    }

    @Override
    protected void onStart() {
        super.onStart();
        itemAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        itemAdapter.stopListening();
    }

    /**@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search,menu);
        MenuItem item = menu.findItem(R.id.);
        SearchView searchView = (SearchView)item.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                itmSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                itmSearch(query);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    private void itmSearch(String str) {
        FirebaseRecyclerOptions<ItemModel> options =
                new FirebaseRecyclerOptions.Builder<ItemModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("item").orderByChild("title").startAt(str).endAt(str+"~"),ItemModel.class)
                        .build();

        itemAdapter = new ItemAdapter(options);
        itemAdapter.startListening();
        recyclerView.setAdapter(itemAdapter);
    }*/

}