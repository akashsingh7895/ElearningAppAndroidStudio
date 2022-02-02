package com.example.hathraswarrior.fragment;

import static com.example.hathraswarrior.classes.HomepageModel.BANNER;
import static com.example.hathraswarrior.classes.HomepageModel.GRID;
import static com.example.hathraswarrior.classes.HomepageModel.HORIZONTAL;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hathraswarrior.MainActivity;
import com.example.hathraswarrior.R;
import com.example.hathraswarrior.classes.CategoryAdapter;
import com.example.hathraswarrior.classes.CategoryModel;
import com.example.hathraswarrior.classes.HomepageAdapter;
import com.example.hathraswarrior.classes.HomepageModel;
import com.example.hathraswarrior.classes.HorizontalItemModel;
import com.example.hathraswarrior.classes.ViewAllModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

  private RecyclerView categoriesRv,homeMainRv;
  private TextView scrollableTv;

  private ImageView noInternetImage;
  private Button retryButton;

  private List<CategoryModel> categoryModelList;
  private List<HorizontalItemModel>horizontalList;
  private List<HomepageModel>homeList;
  private List<ViewAllModel>viewAllList;
  private List<String> posterUrlList;

  private ConnectivityManager connectivityManager;
  private NetworkInfo networkInfo;

  private SwipeRefreshLayout refreshLayout;

  // fakeList

  private List<String>bannerFakeList;
  private List<CategoryModel>categoryFakeList;
  private List<HorizontalItemModel>mixedFakeList;
  private List<HomepageModel>homeFakeList;
  private List<String>productFakeList;

  // End fake list

  public FirebaseFirestore firestore;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        categoriesRv = view.findViewById(R.id.categoriesRv);
        scrollableTv = view.findViewById(R.id.scrollableTv);
        homeMainRv = view.findViewById(R.id.homeRecyclerView);
        refreshLayout = view.findViewById(R.id.swipeRefreshLayout);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        scrollableTv.setSelected(true);
        scrollableTv.setText("This is the text of my land to gud of india is then so i can we live and to is hai");

        categoryModelList = new ArrayList<>();
        horizontalList =new ArrayList<>();
        homeList = new ArrayList<>();
        viewAllList = new ArrayList<>();
        posterUrlList = new ArrayList<>();

        // fakeList
        bannerFakeList = new ArrayList<>();
        categoryFakeList = new ArrayList<>();
        mixedFakeList = new ArrayList<>();
        homeFakeList = new ArrayList<>();
        productFakeList = new ArrayList<>();
        //End fake list

        firestore = FirebaseFirestore.getInstance();

       // posters

//        posterUrlList.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRiqBlNo0notGw7-MHdZ0NP2dxePCPKF5mjDw&usqp=CAU");
//        posterUrlList.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSGQEYjWrexTn1y-vaGGyINQW4ddGWG0ZDyfA&usqp=CAU");
//        posterUrlList.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTfS6aSvTroN_6083n4QVGwnBTvbmiiDUdROQ&usqp=CAU");
//        posterUrlList.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRKrQxBDxz9wbkIsXAz1Km0Frnkdzr6NRS5fg&usqp=CAU");
//        posterUrlList.add("https://images.unsplash.com/photo-1607252650355-f7fd0460ccdb?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8YW5kcm9pZHxlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&w=1000&q=80");
//



        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(RecyclerView.HORIZONTAL);
        categoriesRv.setLayoutManager(manager);


        LinearLayoutManager homeLm = new LinearLayoutManager(getContext());
        homeLm.setOrientation(RecyclerView.VERTICAL);
        homeMainRv.setLayoutManager(homeLm);



        // fill fakeList

        productFakeList.add("");
        productFakeList.add("");
        productFakeList.add("");
        productFakeList.add("");

        bannerFakeList.add("");
        bannerFakeList.add("");
        bannerFakeList.add("");
        bannerFakeList.add("");


        categoryFakeList.add(new CategoryModel("",""));
        categoryFakeList.add(new CategoryModel("",""));
        categoryFakeList.add(new CategoryModel("",""));
        categoryFakeList.add(new CategoryModel("",""));


        mixedFakeList.add(new HorizontalItemModel("","","","",""));
        mixedFakeList.add(new HorizontalItemModel("","","","",""));
        mixedFakeList.add(new HorizontalItemModel("","","","",""));
        mixedFakeList.add(new HorizontalItemModel("","","","",""));
        mixedFakeList.add(new HorizontalItemModel("","","","",""));
        mixedFakeList.add(new HorizontalItemModel("","","","",""));



        homeFakeList.add(new HomepageModel(BANNER,bannerFakeList));
        homeFakeList.add(new HomepageModel(HORIZONTAL,"",productFakeList));
        homeFakeList.add(new HomepageModel(HORIZONTAL,"","#ffffff",productFakeList));
        homeFakeList.add(new HomepageModel(HORIZONTAL,"",productFakeList));

        //callFakeAdapter();


        CategoryAdapter adapter = new CategoryAdapter(categoryFakeList);
        categoriesRv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        HomepageAdapter hAdapter = new HomepageAdapter(homeFakeList);
        homeMainRv.setAdapter(hAdapter);
        hAdapter.notifyDataSetChanged();

        //End fill fakeList


        connectivityManager = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE) ;
        networkInfo = connectivityManager.getActiveNetworkInfo();



       if (networkInfo!=null && networkInfo.isConnected()){

           MainActivity.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
//
              noInternetImage = view.findViewById(R.id.connectingImage);
              retryButton = view.findViewById(R.id.retryBtn);

            homeMainRv.setVisibility(View.VISIBLE);
            categoriesRv.setVisibility(View.VISIBLE);
            scrollableTv.setVisibility(View.VISIBLE);

            noInternetImage.setVisibility(View.INVISIBLE);
            retryButton.setVisibility(View.INVISIBLE);

            loadCategories();
            loadHomeFragment();

          }else {

           MainActivity.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            homeMainRv.setVisibility(View.INVISIBLE);
            categoriesRv.setVisibility(View.INVISIBLE);
            scrollableTv.setVisibility(View.INVISIBLE);

            noInternetImage.setVisibility(View.VISIBLE);
            retryButton.setVisibility(View.VISIBLE);



        }


        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!reloadPage()){
                    Toast.makeText(getContext(), "Sorry, No internet connection available", Toast.LENGTH_SHORT).show();
                }

            }
        });



        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(true);
                reloadPage();

            }
        });



    }

    private boolean reloadPage(){

        networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo!=null && networkInfo.isConnected()){

            MainActivity.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

            categoryModelList.clear();
            homeList.clear();

            homeMainRv.setVisibility(View.VISIBLE);
            categoriesRv.setVisibility(View.VISIBLE);
            scrollableTv.setVisibility(View.VISIBLE);

            noInternetImage.setVisibility(View.INVISIBLE);
            retryButton.setVisibility(View.INVISIBLE);

            CategoryAdapter adapter = new CategoryAdapter(categoryFakeList);
            categoriesRv.setAdapter(adapter);
            adapter.notifyDataSetChanged();

            HomepageAdapter hAdapter = new HomepageAdapter(homeFakeList);
            homeMainRv.setAdapter(hAdapter);
            hAdapter.notifyDataSetChanged();


            loadCategories();
            loadHomeFragment();
            return true;

        }else {

            MainActivity.drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

            homeMainRv.setVisibility(View.INVISIBLE);
            categoriesRv.setVisibility(View.INVISIBLE);
            scrollableTv.setVisibility(View.INVISIBLE);

            noInternetImage.setVisibility(View.VISIBLE);
           // noInternetImage.setImageResource(R.drawable.no_internet_connection_image);
            retryButton.setVisibility(View.VISIBLE);

            if (refreshLayout.isRefreshing()){
                refreshLayout.setRefreshing(false);

            }
            return false;

        }

    }


    public void loadCategories(){


       firestore.collection("CATEGORIES").orderBy("index").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
           @Override
           public void onComplete(@NonNull Task<QuerySnapshot> task) {

               if (task.isSuccessful()){

                   for (DocumentSnapshot shot : task.getResult()) {

                       String name =(String) shot.get("category_name");
                       String icon = (String) shot.get("category_image");

                       categoryModelList.add(new CategoryModel(name,icon));


                   }

                   CategoryAdapter adapter = new CategoryAdapter(categoryModelList);
                   categoriesRv.setAdapter(adapter);
                   adapter.notifyDataSetChanged();

               }else {
                   Toast.makeText(getContext(), ""+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
               }


           }
       });

    }
    private void loadHomeFragment(){

        firestore.collection("CATEGORIES")
                .document("HOME")
                .collection("TODISPLAY")
                .orderBy("index")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()){

                    for (DocumentSnapshot shot : task.getResult()){

                        long layoutType = (long) shot.get("layout_type");

                        if (layoutType ==BANNER){
                            // this is a banner layout

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



                    HomepageAdapter homepageAdapter = new HomepageAdapter(homeList);
                    homeMainRv.setAdapter(homepageAdapter);
                    homepageAdapter.notifyDataSetChanged();


                   if (refreshLayout.isRefreshing()){
                       refreshLayout.setRefreshing(false);
                   }

                }else {
                    Toast.makeText(getContext(), ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }



            }
        });

    }
}