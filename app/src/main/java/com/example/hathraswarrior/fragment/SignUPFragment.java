package com.example.hathraswarrior.fragment;

import static com.example.hathraswarrior.activites.AuthActivity.isLogInFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
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
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignUPFragment extends Fragment {

    private ImageView closeBtn;
    private TextView logInTv;
    private FrameLayout parentFrameLayout;
    private EditText emailEt,passwordEt,cnfpassEt,fullNameEt,mobileEt;
    private Button signUpButton;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;

    private FirebaseFirestore firestore;

    private String emailPattern ="[a-zA-Z0_9._-]+@[a-z]+.[a-z]+";


    public SignUPFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_u_p, container, false);

        closeBtn = view.findViewById(R.id.closeBtn);
        logInTv = view.findViewById(R.id.logInTv);
        emailEt = view.findViewById(R.id.emailEt);
        passwordEt = view.findViewById(R.id.passwordEt);
        signUpButton = view.findViewById(R.id.sigUpBtn);
        cnfpassEt = view.findViewById(R.id.confPassEt);
        fullNameEt = view.findViewById(R.id.fullNameEt);
        mobileEt = view.findViewById(R.id.phoneEt);
        progressBar = view.findViewById(R.id.progressBar3);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        parentFrameLayout= getActivity().findViewById(R.id.frameLayoutAuth);

        firebaseAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();




        fullNameEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInput();


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        emailEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInput();


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });




        mobileEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInput();


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });





        passwordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInput();


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });




        cnfpassEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkInput();


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (emailEt.getText().toString().matches(emailPattern)){
                    if (passwordEt.getText().toString().equals(cnfpassEt.getText().toString())){
                        // ready to go auth

                        signUpButton.setEnabled(false);

                        progressBar.setVisibility(View.VISIBLE);
                        signUpButton.setText("");

                        String email = emailEt.getText().toString();
                        String password = passwordEt.getText().toString();

                        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (task.isSuccessful()){

                                firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                        if (task.isSuccessful()){

                                            // data --->  firebase

                                            Map<String,Object> basicDetail = new HashMap<>();

                                            basicDetail.put("full_name",fullNameEt.getText().toString());
                                            basicDetail.put("mobile_no",mobileEt.getText().toString());
                                            basicDetail.put("email",emailEt.getText().toString());
                                            basicDetail.put("initialPassword",passwordEt.getText().toString());


                                            firestore.collection("USERS").document(firebaseAuth.getUid())
                                                    .set(basicDetail).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                    if (task.isSuccessful()){

                                                        CollectionReference userDataReference = firestore.collection("USERS").document(firebaseAuth.getUid()).collection("USER_DATA");

                                                        List<String> documentNames = new ArrayList<>();
                                                         documentNames.add("MY_RATINGS");
                                                         documentNames.add("MY_COURSES");
                                                         documentNames.add("MY_TESTS");
                                                         documentNames.add("MY_NOTES");

                                                         Map<String, Long> totalMap = new HashMap<>();
                                                         totalMap.put("total",(long) 0);

                                                        //totalMap.put("arrayOfUsers", FieldValue.arrayUnion(firebaseAuth.getCurrentUser().getUid()));


                                                         for (int i = 0; i<documentNames.size();i++){

                                                             userDataReference.document(documentNames.get(i)).set(totalMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                 @Override
                                                                 public void onComplete(@NonNull Task<Void> task) {
                                                                     if (task.isSuccessful()){

                                                                         Intent intent = new Intent(getContext(),MainActivity.class);
                                                                         startActivity(intent);
                                                                         getActivity().finish();

                                                                     }else {
                                                                         Toast.makeText(getContext(), "Something went wrong" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                                         progressBar.setVisibility(View.INVISIBLE);
                                                                         signUpButton.setText("SIGN UP");
                                                                     }
                                                                 }
                                                             });

                                                         }


                                                    }else {
                                                        firebaseAuth.signOut();
                                                        progressBar.setVisibility(View.INVISIBLE);
                                                        signUpButton.setText("SIGN UP");
                                                        Toast.makeText(getContext(), "Error OcCured" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                    }

                                                }
                                            });

                                        }else {
                                            Toast.makeText(getContext(), "Some Error occured" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            progressBar.setVisibility(View.INVISIBLE);
                                            signUpButton.setText("SIGN UP");
                                        }

                                    }
                                });

                                }else {
                                    signUpButton.setEnabled(true);
                                    Toast.makeText(getContext(), "Some Error occured" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.INVISIBLE);
                                    signUpButton.setText("SIGN UP");
                                }

                            }
                        });


                    }else {
                        cnfpassEt.setError("Password does not matches");
                    }
                }else {
                    emailEt.setError("please enter to valid Email address");
                }





            }
        });

        logInTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isLogInFragment = true;
                changeFragment(new LogInFragment());

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

    private void checkInput(){

        if (!fullNameEt.getText().toString().equals("")){
            //full name has been filled till now
            if (!emailEt.getText().toString().equals("")){

                if (!mobileEt.getText().toString().equals("")){

                    if ((!passwordEt.getText().toString().equals("")) && (passwordEt.getText().toString().length()>=6)){

                        if (!cnfpassEt.getText().toString().equals("")){


                                signUpButton.setEnabled(true);

                        }else {
                            signUpButton.setEnabled(false);
                            cnfpassEt.setError("Please Enter confirm password");
                        }

                    }else {
                        signUpButton.setEnabled(false);
                        passwordEt.setError("Password must be a at least 6 character!");
                    }

                }else {
                    signUpButton.setEnabled(false);
                    mobileEt.setError("Please Enter Phone No...");
                }

            }else {
                signUpButton.setEnabled(false);
                emailEt.setError("Please Enter Email address");
            }

        }else {
            signUpButton.setEnabled(false);
            fullNameEt.setError("Please Enter full name");
        }

    }

    private void  changeFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction =getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.left_to_right,R.anim.right_to_left);
        fragmentTransaction.replace(parentFrameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }

}