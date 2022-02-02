package com.example.hathraswarrior.classes;

import static com.example.hathraswarrior.activites.VIewAllActivity.viewAllActivityList;
import static com.example.hathraswarrior.classes.HomepageModel.BANNER;
import static com.example.hathraswarrior.classes.HomepageModel.GRID;
import static com.example.hathraswarrior.classes.HomepageModel.HORIZONTAL;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.gridlayout.widget.GridLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.hathraswarrior.R;
import com.example.hathraswarrior.activites.ProductDetailActivity;
import com.example.hathraswarrior.activites.VIewAllActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class HomepageAdapter extends RecyclerView.Adapter {

    private List<HomepageModel>list;
    private FirebaseFirestore firestore;

    public HomepageAdapter(List<HomepageModel> list) {
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {

        switch (list.get(position).getType()){
            case BANNER:
                return 0;

            case HORIZONTAL:
                return 1;

            case GRID:
                return 2;

            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        switch (viewType){

            case BANNER:
                View view0 = LayoutInflater.from(parent.getContext()).inflate(R.layout.poster_slider_layout,parent,false);
                return new PosterSliderView(view0);

            case HORIZONTAL:
                View view1 = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_slider_layout,parent,false);
                return new HorizontalProductView(view1);

            case GRID:

                View view2 = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_product_layout,parent,false);
                return new GridProductView(view2);


            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        switch (list.get(position).getType()){

            case BANNER:
                ((PosterSliderView)holder).setPosters(list.get(position).getPostersList());
            break;

            case HORIZONTAL:
                ((HorizontalProductView)holder).setHorizontalData(list.get(position)
                        .getLayoutTitle(),list.get(position).getProductList()
                        );
                break;
            case GRID:
                ((GridProductView)holder).setGridData(list.get(position).getBgColor(),
                        list.get(position).getLayoutTitle(),
                        list.get(position).getProductList());
                break;
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HorizontalProductView extends RecyclerView.ViewHolder {

        private TextView layoutTitleTv;
        private RecyclerView recyclerView;
        private Button viewAllBtn;
        private List<HorizontalItemModel> horizontalList;
        private List<ViewAllModel>viewAllList;

        public HorizontalProductView(@NonNull View itemView) {
            super(itemView);

            layoutTitleTv = itemView.findViewById(R.id.layoutTitle);
            recyclerView = itemView.findViewById(R.id.horizontal_scroll_rv);
            viewAllBtn = itemView.findViewById(R.id.viewAllBtn);

            firestore = FirebaseFirestore.getInstance();



        }

        public void setHorizontalData(String title,List<String> productList){
            layoutTitleTv.setText(title);
            horizontalList = new ArrayList<>();
            viewAllList = new ArrayList<>();
            // Fetching Product Detail

            LinearLayoutManager horizontalRvManager = new LinearLayoutManager(itemView.getContext());
            horizontalRvManager.setOrientation(RecyclerView.HORIZONTAL);
            recyclerView.setLayoutManager(horizontalRvManager);

             if (!productList.get(0).equals("")) {

                for (int x = 0; x < productList.size(); x++) {

                    int a = x;

                    firestore.collection("PRODUCTS").document(productList.get(x)).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                            DocumentSnapshot shot = task.getResult();

                            if (task.isSuccessful()) {

                                String productTitle = (String) shot.get("product_title");
                                String productSubtitle = (String) shot.get("product_subtitle");
                                String productSellPrice = (String) shot.get("product_price");
                                String productNormalPrice = (String) shot.get("product_normal_price");

                                List<String> productImageList = (List<String>) shot.get("product_images");
                                String imageUrlLIst = productImageList.get(0);

                                horizontalList.add(new HorizontalItemModel(productList.get(a),imageUrlLIst, productTitle, productSubtitle, productSellPrice));
                                viewAllList.add(new ViewAllModel(productList.get(a),imageUrlLIst, productTitle, productSubtitle, productSellPrice, productNormalPrice));

                                if (a == productList.size() - 1) {

                                    HorizontalProductAdapter horiAdapter = new HorizontalProductAdapter(horizontalList);
                                    recyclerView.setAdapter(horiAdapter);
                                    horiAdapter.notifyDataSetChanged();


                                }

                            } else {
                                Toast.makeText(itemView.getContext(), "" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                }
//
            }else {
                horizontalList.add(new HorizontalItemModel("","","","",""));
                horizontalList.add(new HorizontalItemModel("","","","",""));
                horizontalList.add(new HorizontalItemModel("","","","",""));
                horizontalList.add(new HorizontalItemModel("","","","",""));

                HorizontalProductAdapter adapter = new HorizontalProductAdapter(horizontalList);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
            // End
            viewAllBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    viewAllActivityList = viewAllList;
                    Intent intent = new Intent(itemView.getContext(), VIewAllActivity.class);
                    intent.putExtra("layoutTitle",title);
                    itemView.getContext().startActivity(intent);
                }
            });

        }
    }

    public class GridProductView extends RecyclerView.ViewHolder {

         private TextView layoutTitle;
         private ConstraintLayout rootLayout;
         private GridLayout gridLayout;
         private Button viewAllBtn;

         private List<HorizontalItemModel> gridList;
         private List<ViewAllModel>viewAllList;


        public GridProductView(@NonNull View itemView) {
            super(itemView);

            layoutTitle = itemView.findViewById(R.id.layoutTitle);
            rootLayout = itemView.findViewById(R.id.gridParentLayout);
            gridLayout = itemView.findViewById(R.id.gridProductLayout);
            viewAllBtn = itemView.findViewById(R.id.viewAllBtn);
            firestore = FirebaseFirestore.getInstance();

        }

        private void setGridData(String backgroundColor,String title,List<String> productList){

            //rootLayout.setBackgroundColor(getResources().getColor(R.color.)));
            //rootLayout.setBackgroundColor(Color.parseColor(backgroundColor));

            rootLayout.setBackgroundColor(Color.parseColor(String.valueOf(backgroundColor)));
            layoutTitle.setText(title);
            gridList = new ArrayList<>();
            viewAllList = new ArrayList<>();

            if (!productList.get(0).equals("")) {

                   for (int x = 0; x < productList.size(); x++) {

                     int a = x;

                     firestore.collection("PRODUCTS").document(productList.get(x)).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                         @Override
                         public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                             DocumentSnapshot shot = task.getResult();

                             if (task.isSuccessful()) {

                                 String prodTitle = (String) shot.get("product_title");
                                 String productSubtitle = (String) shot.get("product_subtitle");
                                 String productSellPrice = (String) shot.get("product_price");
                                 String productNormalPrice = (String) shot.get("product_normal_price");

                                 List<String> productImageList = (List<String>) shot.get("product_images");
                                 String imageUrlLIst = productImageList.get(0);

                                 gridList.add(new HorizontalItemModel(productList.get(a),imageUrlLIst, prodTitle, productSubtitle, productSellPrice));
                                 viewAllList.add(new ViewAllModel(productList.get(a),imageUrlLIst, prodTitle, productSubtitle, productSellPrice, productNormalPrice));

                                 if (a == productList.size() - 1) {


                                     for (int x = 0; x < 4; x++) {
                                         TextView productTitle = gridLayout.getChildAt(x).findViewById(R.id.titleTv);
                                         TextView productSuBTitle = gridLayout.getChildAt(x).findViewById(R.id.subTitleTv);
                                         TextView productPrice = gridLayout.getChildAt(x).findViewById(R.id.priceTv);
                                         ImageView prodImage = gridLayout.getChildAt(x).findViewById(R.id.productImageView);

                                         // problem is here

                                         Glide.with(itemView.getContext())
                                                 .load(gridList.get(x).getImageResource()).into(prodImage);
                                         productTitle.setText(gridList.get(x).getTitle());
                                         productSuBTitle.setText(gridList.get(x).getSubTitle());
                                         productPrice.setText("Rs." + gridList.get(x).getPrice() + "/-");


                                         gridLayout.getChildAt(x).setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View view) {

                                                 // todo: to pass identification data throw intent
                                                 Intent intent = new Intent(itemView.getContext(), ProductDetailActivity.class);
                                                 intent.putExtra("product_id",productList.get(a));
                                                 itemView.getContext().startActivity(intent);

                                             }
                                         });
                                     }

                                 }

                             } else {
                                 Toast.makeText(itemView.getContext(), "" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                             }
                         }
                     });


                 }
            }else {


                // im not enter fake data done


                 for (int x = 0; x < 4; x++) {
                     TextView productTitle = gridLayout.getChildAt(x).findViewById(R.id.titleTv);
                     TextView productSuBTitle = gridLayout.getChildAt(x).findViewById(R.id.subTitleTv);
                     TextView productPrice = gridLayout.getChildAt(x).findViewById(R.id.priceTv);

                     productTitle.setText(gridList.get(x).getTitle());
                     productSuBTitle.setText(gridList.get(x).getSubTitle());
                     productPrice.setText( gridList.get(x).getPrice());



                 }
             }

            viewAllBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    viewAllActivityList = viewAllList;
                    Intent intent = new Intent(itemView.getContext(), VIewAllActivity.class);
                    intent.putExtra("layoutTitle",title);
                    itemView.getContext().startActivity(intent);

                }
            });

        }
    }

        public class PosterSliderView extends RecyclerView.ViewHolder {

        private ViewPager posterViewPager;
        private List<String> arrangeList;
        private int currentPage;
        private Timer timer;
        final static  int DELAY_TIME = 3000,PERIOD_TIME = 3000;

        public PosterSliderView(@NonNull View itemView) {
            super(itemView);

            posterViewPager = itemView.findViewById(R.id.posterSliderViewPager);

        }
        private void setPosters(List<String> imageUrls){

            if (timer !=null){
                timer.cancel();
            }

            currentPage = 2;
            arrangeList = new ArrayList<>();

            for (int x = 0; x < imageUrls.size();x++){
                arrangeList.add(imageUrls.get(x));
            }

            arrangeList.add(0,imageUrls.get(imageUrls.size()-1));
            arrangeList.add(0,imageUrls.get(imageUrls.size()-2));

            arrangeList.add(imageUrls.get(0));
            arrangeList.add(imageUrls.get(1));

            PostersAdapter adapter = new PostersAdapter(arrangeList);
            posterViewPager.setAdapter(adapter);
            posterViewPager.setCurrentItem(currentPage);

            startSliding(arrangeList);
            posterViewPager.setPageMargin(20);

          ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
              @Override
              public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

              }

              @Override
              public void onPageSelected(int position) {
                  currentPage = position;

              }

              @Override
              public void onPageScrollStateChanged(int state) {
                  if (state == ViewPager.SCROLL_STATE_IDLE){
                      posterInfiniteLoop(arrangeList);
                  }

              }
          };

          posterViewPager.addOnPageChangeListener(listener);
          posterViewPager.setOnTouchListener(new View.OnTouchListener() {
              @Override
              public boolean onTouch(View view, MotionEvent motionEvent) {
                  posterInfiniteLoop(arrangeList);
                  stopSliding();

                  if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                      startSliding(arrangeList);
                  }

                  return false;
              }
          });


        }

        private void posterInfiniteLoop(List<String> list){

            if (currentPage == list.size() -2){
                currentPage = 2;
                posterViewPager.setCurrentItem(currentPage,false);
            }

            if (currentPage ==1){
                currentPage = list.size() -3;
                posterViewPager.setCurrentItem(currentPage,false);
            }

        }

        private void startSliding(List<String> list){
            final   Handler handler = new Handler();
            final Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    if (currentPage >= list.size()){
                        currentPage = 1;
                    }
                    posterViewPager.setCurrentItem(currentPage++,true);

                }
            };

            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(runnable);
                }
            },DELAY_TIME,PERIOD_TIME);

      }

      private void stopSliding(){
            timer.cancel();
      }


   }
}
