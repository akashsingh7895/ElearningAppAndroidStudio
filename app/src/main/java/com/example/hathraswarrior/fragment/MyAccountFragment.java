package com.example.hathraswarrior.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hathraswarrior.R;
import com.example.hathraswarrior.activites.AuthActivity;
import com.example.hathraswarrior.activites.ProductDetailActivity;
import com.example.hathraswarrior.activites.UpdateUserProfileActivity;
import com.example.hathraswarrior.activites.VIewAllActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MyAccountFragment extends Fragment {

    public MyAccountFragment() {
        // Required empty public constructor
    }

    private TextView nameTv,emailTv,phoneNumberTv;
    private ImageButton floatingActionButton;
    private Button signOutBtn;
    private FirebaseFirestore firestore;
    private String name,phone,email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_my_account, container, false);

        nameTv = view.findViewById(R.id.name);
        emailTv = view.findViewById(R.id.email);
        phoneNumberTv = view.findViewById(R.id.phone);
        floatingActionButton = view.findViewById(R.id.updateuser);
        signOutBtn = view.findViewById(R.id.btn_signOut);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firestore = FirebaseFirestore.getInstance();

        firestore.collection("USERS")
                .document(FirebaseAuth.getInstance().getUid())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    DocumentSnapshot snapshot  = task.getResult();
                    name = (String) snapshot.get("full_name");
                    phone = (String) snapshot.get("mobile_no");
                    email = (String) snapshot.get("email");

                    nameTv.setText(name);
                    phoneNumberTv.setText(phone);
                    emailTv.setText(email);

                }else {
                    Toast.makeText(getContext(), ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UpdateUserProfileActivity.class);
                intent.putExtra("user_name",name);
                intent.putExtra("user_mobile",phone);
                getActivity().startActivity(intent);
            }
        });
        
        signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Successfully Logged out!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), AuthActivity.class);
                getActivity().startActivity(intent);
                getActivity().finish();
            }
        });
    }
}