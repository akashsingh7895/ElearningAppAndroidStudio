package com.example.hathraswarrior.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hathraswarrior.R;
import com.example.hathraswarrior.classes.ProductImagesAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout viewpagerDots;

    // Layout Element

    private TextView titleTV , subTitleTV, sellPriceTV,normalPriceTV,totalRatingsSmallTV,totalRatingsBottomTV,
    totalRatingsSmallBelowTV,averageRatingAboveTv,averageRatingLargeTv, productDetailTv,star1Tv,star2Tv,star3Tv,
    star4Tv,star5Tv;

    private Button buyNowButton;

    // End Layout element

    // Database variable


    private String title;
    private String subtitle;
    private String productDetail;
    private double avrgRating;
    private String sellPrice;
    private String normalPrice;
    private String productId;

    private List<String> productImagesList = new ArrayList<>();

    private long productType,star1,star2,star3,star4,star5,ratingCount;
    // End database variable


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       // inliaize layout element


        viewPager = findViewById(R.id.productImagesViewPager);
        viewpagerDots = findViewById(R.id.viewpagerDots);

        titleTV = findViewById(R.id.productTitle);
        subTitleTV = findViewById(R.id.productSubTitle);
        sellPriceTV = findViewById(R.id.sellPriceTv);
        normalPriceTV = findViewById(R.id.normalPriceTv);
        averageRatingAboveTv = findViewById(R.id.smallRatingTv);
        totalRatingsBottomTV = findViewById(R.id.totalRatingBelowTv);
        totalRatingsSmallBelowTV = findViewById(R.id.totalRatingsBelowTv);
        totalRatingsSmallTV = findViewById(R.id.totalRatingTv);
        averageRatingLargeTv = findViewById(R.id.largeRatingTv);
        productDetailTv = findViewById(R.id.elborateDetail);
        star1Tv = findViewById(R.id.total1StarRatingTv);
        star2Tv = findViewById(R.id.total2StarRatingTv);
        star3Tv = findViewById(R.id.total3StarRatingTv);
        star4Tv = findViewById(R.id.total4StarRatingTv);
        star5Tv = findViewById(R.id.total5StarRatingTv);
        buyNowButton = findViewById(R.id.butNowButton);


        if (getIntent() !=null){
            productId = getIntent().getStringExtra("product_id");
        }


        // junkCode

        FirebaseFirestore.getInstance().collection("PRODUCTS").document(productId)
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()){

                    DocumentSnapshot shot = task.getResult();

                    title = (String) shot.get("product_title");
                    subtitle = (String) shot.get("product_subtitle");
                    productDetail = (String) shot.get("product_details");
                    avrgRating = (double) shot.get("average_rating");
                    sellPrice = (String) shot.get("product_price");
                    normalPrice = (String) shot.get("product_normal_price");
                    ratingCount = (long) shot.get("rating_count");
                    productType = (long) shot.get("product_type");
                    star1 = (long) shot.get("star_1");
                    star2 = (long) shot.get("star_2");
                    star3 = (long) shot.get("star_3");
                    star4 = (long) shot.get("star_4");
                    star5 = (long) shot.get("star_5");
                    productImagesList = (List<String>) shot.get("product_images");



                    // satting data
                    titleTV.setText(title);
                    subTitleTV.setText(subtitle);
                    sellPriceTV.setText("Rs. " + sellPrice+ "/-");
                    normalPriceTV.setText("Rs. "+normalPrice+"/-");

                    // problems productDetails data not set
                    productDetailTv.setText(productDetail);


                    averageRatingAboveTv.setText(String.valueOf(avrgRating));
                    averageRatingLargeTv.setText((String.valueOf(avrgRating)));
                    totalRatingsBottomTV.setText("("+ratingCount+") Ratings");
                    totalRatingsSmallBelowTV.setText("" +ratingCount);
                    totalRatingsSmallTV.setText("("+ ratingCount+") Ratings");
                    star1Tv.setText(""+star1);
                    star2Tv.setText(""+star2);
                    star3Tv.setText(""+star3);
                    star4Tv.setText(""+star4);
                    star5Tv.setText(""+star5);


                    ProductImagesAdapter adapter = new ProductImagesAdapter(productImagesList);
                    viewPager.setAdapter(adapter);
                    viewpagerDots.setupWithViewPager(viewPager,true);

                  //  End satting data




                }else {
                    Toast.makeText(ProductDetailActivity.this, "" +task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });

        // end junk code


        buyNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent deliveryIntent = new Intent(ProductDetailActivity.this,DeliveryActivity.class);
                deliveryIntent.putExtra("product_id_intent",productId);
                deliveryIntent.putExtra("product_title_intent",title);
                deliveryIntent.putExtra("product_sell_price_intent",sellPrice);
                deliveryIntent.putExtra("product_norman_price_intent",normalPrice);
                deliveryIntent.putExtra("product_image_intent",productImagesList.get(0));
                startActivity(deliveryIntent);
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