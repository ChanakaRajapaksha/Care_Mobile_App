package com.example.care_mobile_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.FirebaseDatabase;

public class TipsTricks extends AppCompatActivity {

    RecyclerView recyclerView;
    TipAdapter tipAdapter;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);

        recyclerView = findViewById(R.id.tiprv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<TipsModel> options =
                new FirebaseRecyclerOptions.Builder<TipsModel>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Tips"), TipsModel.class)
                        .build();

        tipAdapter = new TipAdapter(options);
        recyclerView.setAdapter(tipAdapter);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AddTips.class));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        tipAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        tipAdapter.stopListening();
    }
}
