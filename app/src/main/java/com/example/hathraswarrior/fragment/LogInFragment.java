package com.example.hathraswarrior.fragment;

import static com.example.hathraswarrior.activites.AuthActivity.isLogInFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hathraswarrior.MainActivity;
import com.example.hathraswarrior.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInFragment extends Fragment {

    private TextView forgotPassTv, signUpTv;
    private FrameLayout parentFrameLayout;
    private ImageView closeBtn;
    private EditText emailEt,passwordEt;
    private Button loginButton;
    private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;



    public LogInFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_log_in, container, false);

        forgotPassTv = view.findViewById(R.id.forgotPass);
        signUpTv = view.findViewById(R.id.signUpTv);
        closeBtn = view.findViewById(R.id.closeBtn);
        emailEt = view.findViewById(R.id.emailEt);
        passwordEt = view.findViewById(R.id.passwordEt);
        loginButton = view.findViewById(R.id.loginBtn);
        progressBar = view.findViewById(R.id.progressBar3);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       parentFrameLayout = getActivity().findViewById(R.id.frameLayoutAuth);
       firebaseAuth = FirebaseAuth.getInstance();

       loginButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               if (!emailEt.getText().toString().equals("")){
                   if (!passwordEt.getText().toString().equals("")){


                       progressBar.setVisibility(View.VISIBLE);
                       loginButton.setText("");
                       loginButton.setEnabled(false);

                       // ready to auth
                       String email = emailEt.getText().toString().trim();
                       String password = passwordEt.getText().toString().trim();

                       firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                           @Override
                           public void onComplete(@NonNull Task<AuthResult> task) {

                               if (task.isSuccessful()) {

                                   Intent intent = new Intent(getContext(),MainActivity.class);
                                   startActivity(intent);
                                   getActivity().finish();


                               }else {
                                   Toast.makeText(getContext(), "Some Error occured" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                   loginButton.setEnabled(true);
                               }

                               progressBar.setVisibility(View.INVISIBLE);
                               loginButton.setText("Login");




                           }
                       });




                   }else {
                       passwordEt.setError("Please Enter password address");
                   }
               }else {
                   emailEt.setError("Please Enter email address");
               }


           }
       });



        signUpTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isLogInFragment = false;
                changeFragment(new SignUPFragment());

            }
        });

        forgotPassTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isLogInFragment = false;
                changeFragment(new ForPassFragment());

            }
        });


        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isLogInFragment = false;
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();


            }
        });



    }

    private void  changeFragment(Fragment fragment){


        FragmentTransaction fragmentTransaction =getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.left_to_right,R.anim.right_to_left);
        fragmentTransaction.replace(parentFrameLayout.getId(),fragment);

        fragmentTransaction.commit();
    }



}