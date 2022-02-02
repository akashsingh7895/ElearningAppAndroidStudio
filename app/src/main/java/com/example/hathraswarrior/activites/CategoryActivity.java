package com.example.hathraswarrior.activites;

import static com.example.hathraswarrior.classes.HomepageModel.BANNER;
import static com.example.hathraswarrior.classes.HomepageModel.GRID;
import static com.example.hathraswarrior.classes.HomepageModel.HORIZONTAL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.hathraswarrior.R;
import com.example.hathraswarrior.classes.HomepageAdapter;
import com.example.hathraswarrior.classes.HomepageModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

   private String categoryName;
   private FirebaseFirestore firestore;
   private List<HomepageModel> homeList;
   private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

         homeList = new ArrayList<>();
         recyclerView = findViewById(R.id.homeRecyclerView);
         categoryName   = getIntent().getStringExtra("category_name");
         firestore = FirebaseFirestore.getInstance();

         getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         getSupportActionBar().setDisplayShowTitleEnabled(true);
         getSupportActionBar().setTitle(categoryName);

        loadCategoryItems();
    }


    private void loadCategoryItems(){

        firestore.collection("CATEGORIES")
                .document(categoryName.toUpperCase())
                .collection("TODISPLAY")
                .orderBy("index")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()){

                    for (DocumentSnapshot shot : task.getResult()){

                        long layoutType = (long) shot.get("layout_type");

                        if (layoutType ==BANNER){
                            // this is a banned layout

                            List<String> bannerList =(List<String>) shot.get("poster_url");
                            homeList.add(new HomepageModel(BANNER,bannerList));

                        } else if (layoutType ==HORIZONTAL){
                            // this is a horizontal
                            String layoutTitle = (String) shot.get("layout_title");
                            List<String> productList = (List<String>) shot.get("product_list");

                            homeList.add(new HomepageModel(HORIZONTAL,layoutTitle,productList));

                        }else if (layoutType==GRID){
                            // this is a Grid
                            String layoutTitle = (String) shot.get("layout_title");
                            List<String> productList = (List<String>) shot.get("product_list");
                            String bgColor = (String) shot.get("bg_color");

                            homeList.add(new HomepageModel(GRID,layoutTitle,bgColor,productList));

                        }else {
                            // don't do anythings
                        }

                    }

                    LinearLayoutManager homeLm = new LinearLayoutManager(CategoryActivity.this);
                    homeLm.setOrientation(RecyclerView.VERTICAL);
                    recyclerView.setLayoutManager(homeLm);

                    HomepageAdapter homepageAdapter = new HomepageAdapter(homeList);
                    recyclerView.setAdapter(homepageAdapter);
                    homepageAdapter.notifyDataSetChanged();


                }else {
                    Toast.makeText(CategoryActivity.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}