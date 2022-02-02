package com.example.hathraswarrior.fragment;

import static com.example.hathraswarrior.activites.AuthActivity.isLogInFragment;

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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hathraswarrior.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForPassFragment extends Fragment {


    private TextView goBackTv;
    private FrameLayout frameLayout;
    private EditText emailEt;
    private Button resetBtn;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;


    public ForPassFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_for_pass, container, false);
           goBackTv = view.findViewById(R.id.goBackTv);
           emailEt = view.findViewById(R.id.emailEt);
           resetBtn = view.findViewById(R.id.resetPassBtn);
           progressBar = view.findViewById(R.id.progressBar3);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        frameLayout = getActivity().findViewById(R.id.frameLayoutAuth);
        firebaseAuth = FirebaseAuth.getInstance();


        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!emailEt.getText().toString().equals("")){

                    progressBar.setVisibility(View.VISIBLE);
                    resetBtn.setEnabled(false);
                    resetBtn.setText("");
                    firebaseAuth.sendPasswordResetEmail(emailEt.getText().toString().trim()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()){

                                Toast.makeText(getContext(), "Email send SuccessFully", Toast.LENGTH_LONG).show();


                            }else {
                                Toast.makeText(getContext(), "SomeThing went wrong" + task.getException().getMessage(), Toast.LENGTH_LONG).show();

                            }
                            progressBar.setVisibility(View.INVISIBLE);
                            resetBtn.setEnabled(true);
                            resetBtn.setText("Reset Password");
                        }
                    });

                }else {
                    emailEt.setError("Please Enter Email");
                }





            }
        });


        goBackTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isLogInFragment = true;
                changeFragment(new LogInFragment());

            }
        });
    }

    private void  changeFragment(Fragment fragment)

    {
        FragmentTransaction fragmentTransaction =getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.left_to_right,R.anim.right_to_left);
        fragmentTransaction.replace(frameLayout.getId(),fragment);
        fragmentTransaction.commit();
    }
}