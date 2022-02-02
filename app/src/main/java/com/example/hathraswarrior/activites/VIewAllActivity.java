package com.example.hathraswarrior.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.hathraswarrior.R;
import com.example.hathraswarrior.classes.ViewAllAdapter;
import com.example.hathraswarrior.classes.ViewAllModel;

import java.util.List;

public class VIewAllActivity extends AppCompatActivity {

    public static List<ViewAllModel> viewAllActivityList;

    private RecyclerView recyclerView;
    private String layoutTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);

        recyclerView = findViewById(R.id.viewAllRV);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(manager);

        layoutTitle = getIntent().getStringExtra("layoutTitle");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle(layoutTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ViewAllAdapter adapter = new ViewAllAdapter(viewAllActivityList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}