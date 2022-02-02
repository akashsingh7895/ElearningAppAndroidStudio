package com.example.hathraswarrior.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hathraswarrior.MainActivity;
import com.example.hathraswarrior.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;


import org.json.JSONObject;

public class DeliveryActivity extends AppCompatActivity implements PaymentResultListener {

    public static final int TYPE_NOTES = 2,TYPE_COURSE = 1,TYPE_TEST = 3;

   private String productId,sellPrice,normalPrice,productTitle,productImage;

   private TextView productTitleTv,sellPriceTv,normalPriceTv,itemPriceTv,totalAmountSmallTv,totalAmountLargeTv,
           savedAmountTv;
   private ImageView imageView;
   private Button continueBtn;
   private FirebaseFirestore firestore;
   private FirebaseUser user;

   private int productType;

   private Button materialAccessButton;
   private ConstraintLayout constraintLayoutRoot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery);


        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Purchase");

        imageView = findViewById(R.id.product_image);
        productTitleTv = findViewById(R.id.product_title);
        sellPriceTv = findViewById(R.id.product_price);
        normalPriceTv = findViewById(R.id.cutted_price);
        itemPriceTv = findViewById(R.id.priceTv);
        totalAmountSmallTv = findViewById(R.id.totalPrice);
        totalAmountLargeTv = findViewById(R.id.dilevery_total_price);
        savedAmountTv = findViewById(R.id.savedPrice);
        continueBtn = findViewById(R.id.continue_btn);
        materialAccessButton = findViewById(R.id.starting_learning);
        constraintLayoutRoot = findViewById(R.id.order_confirmLayout);


        // diloag create for


        // dilog code



        firestore = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();




        if (getIntent() !=null){
            productId = getIntent().getStringExtra("product_id_intent");
            sellPrice= getIntent().getStringExtra("product_sell_price_intent");
            normalPrice = getIntent().getStringExtra("product_norman_price_intent");
            productImage = getIntent().getStringExtra("product_image_intent");
            productTitle = getIntent().getStringExtra("product_title_intent");


            productTitleTv.setText(productTitle);
            sellPriceTv.setText("Rs."+sellPrice+"/-");
            normalPriceTv.setText("Rs."+normalPrice+"/-");
            itemPriceTv.setText("Rs."+sellPrice+"/-");
            totalAmountLargeTv.setText("Rs."+sellPrice+"/-");
            totalAmountSmallTv.setText("Rs."+sellPrice+"/-");

            int a = Integer.parseInt(normalPrice);
            int b = Integer.parseInt(sellPrice);
            int c = a-b;
            savedAmountTv.setText(String.valueOf("You saved "+"Rs. "+c+"/-"));
            Glide.with(getApplicationContext()).load(productImage).into(imageView);

        }else {
            finish();
            Toast.makeText(getApplicationContext(), "Something went wrong ! please contact " +
                    "helpline", Toast.LENGTH_SHORT).show();
        }


        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (user != null) {
                   // startPayment(sellPrice);
                    testMethod();
                }else {
                    Dialog dialog = new Dialog(DeliveryActivity.this);
                    dialog.setContentView(R.layout.sigin_dilog);
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                    dialog.setCancelable(true);

                    Button loginButton = dialog.findViewById(R.id.log_diloag_button);
                    loginButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(DeliveryActivity.this,AuthActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });

                    dialog.show();
                }

            }
        });


        materialAccessButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DeliveryActivity.this,MainActivity.class);
                MainActivity.currentFragment=productType;
                startActivity(intent);
                finish();


            }
        });





    }

    public void startPayment(String amount) {

        final Activity activity = this;

        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", "Hathras worier");
            options.put("description", productTitle);
            options.put("send_sms_hash",true);
            options.put("allow_rotation", true);
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");
            options.put("amount", amount+ "00");

            JSONObject preFill = new JSONObject();
            preFill.put("email", "singhakash4099@gmail.com");
            preFill.put("contact", "8979859635");

            options.put("prefill", preFill);

            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPaymentSuccess(String s) {

        // todo: order place this order



    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_SHORT).show();
    }

    private void testMethod(){

        constraintLayoutRoot.setVisibility(View.VISIBLE);
        firestore.collection("PRODUCTS").document(productId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()){
                    DocumentSnapshot snapshot = task.getResult();
                    Long number = (long) snapshot.get("product_type");
                    productType = number.intValue();

                    if (productType==TYPE_COURSE){

                       firestore.collection("USERS").document(user.getUid())
                               .collection("USER_DATA")
                               .document("MY_COURSES")
                               .update("products",FieldValue.arrayUnion(productId))
                               .addOnCompleteListener(new OnCompleteListener<Void>() {
                                   @Override
                                   public void onComplete(@NonNull Task<Void> task) {

                                       if (task.isSuccessful()){
                                           Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                                       }else {
                                           Toast.makeText(getApplicationContext(), "faild   "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                       }

                                   }
                               });

                    }else if (productType==TYPE_NOTES){

                        firestore.collection("USERS").document(user.getUid())
                                .collection("USER_DATA")
                                .document("MY_NOTES")
                                .update("products",FieldValue.arrayUnion(productId))
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()){
                                            Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                                        }else {
                                            Toast.makeText(getApplicationContext(), "faild   "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });


                    }else if (productType==TYPE_TEST){


                        firestore.collection("USERS").document(user.getUid())
                                .collection("USER_DATA")
                                .document("MY_TESTS")
                                .update("products",FieldValue.arrayUnion(productId))
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {

                                        if (task.isSuccessful()){
                                            Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                                        }else {
                                            Toast.makeText(getApplicationContext(), "faild   "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });


                    }else {
                        Toast.makeText(getApplicationContext(), "product type is not recognize", Toast.LENGTH_SHORT).show();
                        // todo: notify to billing teem
                    }

                }else {
                    Toast.makeText(getApplicationContext(), ""+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    // todo: notify to billing teem
                }


            }
        });
    }
}