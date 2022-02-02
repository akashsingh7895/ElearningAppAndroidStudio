package com.example.hathraswarrior.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.hathraswarrior.R;
import com.example.hathraswarrior.classes.CommonAdapter;
import com.example.hathraswarrior.classes.CommonModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;


public class MyTestFragment extends Fragment {

    public MyTestFragment() {
        // Required empty public constructor
    }

    private RecyclerView courseRecyclerview;
    List<CommonModel>list;
    private FirebaseFirestore firestore;
    private List<String> productlist;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_test, container, false);
        courseRecyclerview = view.findViewById(R.id.course_recyclerview);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        list = new ArrayList<>();
        firestore = FirebaseFirestore.getInstance();


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        courseRecyclerview.setLayoutManager(layoutManager);


        firestore.collection("USERS")
                .document(FirebaseAuth.getInstance().getUid())
                .collection("USER_DATA")
                .document("MY_TESTS")
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()){

                    DocumentSnapshot snapshot = task.getResult();

                    if (snapshot.contains("products")) {
                        productlist = (List<String>) snapshot.get("products");


                        for (int x = 0; x < productlist.size(); x++) {

                            int a = x;

                            firestore.collection("PRODUCTS")
                                    .document(productlist.get(x))
                                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {

                                        String title = (String) task.getResult().get("product_title");
                                        String subtitle = (String) task.getResult().get("product_subtitle");
                                        List<String> productImageList = (List<String>) task.getResult().get("product_images");
                                        String image = productImageList.get(0);

                                        list.add(new CommonModel(title, subtitle, image));

                                        if (a == productlist.size() - 1) {

                                            CommonAdapter adapter = new CommonAdapter(list);
                                            courseRecyclerview.setAdapter(adapter);
                                            adapter.notifyDataSetChanged();
                                        }

                                    } else {
                                        Toast.makeText(getContext(), "" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                }
                            });

                        }

                    }

                }else {
                    Toast.makeText(getContext(), "" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });



    }
}