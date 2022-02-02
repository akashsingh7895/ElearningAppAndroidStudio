package com.example.hathraswarrior.activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hathraswarrior.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UpdateUserProfileActivity extends AppCompatActivity {


    private EditText nameET,  phoneET, oldPasswordET, newPasswordET, confirmNewPasswordET;
    private Button updateBtn;
    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private FirebaseUser user;
    public static Dialog loadingDialog;
    private String name,phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_profile);


        Toolbar toolbar = findViewById(R.id.userInfoToolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Update Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nameET = findViewById(R.id.name);
        phoneET = findViewById(R.id.phone);
        oldPasswordET = findViewById(R.id.old_password);
        newPasswordET = findViewById(R.id.new_password);
        confirmNewPasswordET = findViewById(R.id.cnf_new_password);
        updateBtn = findViewById(R.id.update_button);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///loading Dialog
                loadingDialog = new Dialog(UpdateUserProfileActivity.this);
                loadingDialog.setContentView(R.layout.loading_progress_dialog);
                loadingDialog.setCancelable(false);
                loadingDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.slider_background));
                loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                loadingDialog.show();
                /////end loading dialog

                if (TextUtils.isEmpty(newPasswordET.getText())){
                    //user is not changing the password only updating the above details
                    Map<String, Object> updateUserData = new HashMap<>();
                    updateUserData.put("full_name",nameET.getText().toString());
                    updateUserData.put("mobile_no",phoneET.getText().toString());

                    firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid())
                            .update(updateUserData).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(UpdateUserProfileActivity.this, "Profile Updated Successfully!", Toast.LENGTH_SHORT).show();
                                finish();
                            }else {
                                Toast.makeText(UpdateUserProfileActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            loadingDialog.dismiss();
                        }
                    });

                } else {
                    //user is changing the password
                    if (!TextUtils.isEmpty(oldPasswordET.getText())) {
                        if (newPasswordET.getText().toString().equals(confirmNewPasswordET.getText().toString())) {
                            //update in authenticaiton
                            user = FirebaseAuth.getInstance().getCurrentUser();
                            final String email = user.getEmail();

                            AuthCredential credential = EmailAuthProvider.getCredential(email, oldPasswordET.getText().toString());

                            user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        user.updatePassword(newPasswordET.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    //Update in databse
                                                    Map<String, Object> updateUserPass = new HashMap<>();
                                                    updateUserPass.put("full_name", nameET.getText().toString());
                                                    updateUserPass.put("mobile_no", phoneET.getText().toString());
                                                    updateUserPass.put("initialPassword", newPasswordET.getText().toString());

                                                    firebaseFirestore.collection("USERS").document(FirebaseAuth.getInstance().getUid())
                                                            .update(updateUserPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Toast.makeText(UpdateUserProfileActivity.this, "Password & Profile Updated Successfully!", Toast.LENGTH_SHORT).show();
                                                                loadingDialog.dismiss();
                                                                finish();

                                                            } else {
                                                                Toast.makeText(UpdateUserProfileActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                                loadingDialog.dismiss();

                                                            }
                                                        }
                                                    });
                                                } else {
                                                    Toast.makeText(UpdateUserProfileActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                    loadingDialog.dismiss();

                                                }
                                            }
                                        });
                                    } else {
                                        Toast.makeText(UpdateUserProfileActivity.this, "Authentication Failed: Wrong Credentials!", Toast.LENGTH_SHORT).show();
                                        loadingDialog.dismiss();

                                    }
                                }
                            });
                            //end update in authentication

                        } else {
                            Toast.makeText(UpdateUserProfileActivity.this, "Please enter same Password in Confirm Password!", Toast.LENGTH_SHORT).show();
                            loadingDialog.dismiss();

                        }
                    }else {
                        Toast.makeText(UpdateUserProfileActivity.this, "Please Enter Your Old Password!", Toast.LENGTH_SHORT).show();
                        loadingDialog.dismiss();

                    }
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            finish();
            return  true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("USERS")
                .document(FirebaseAuth.getInstance().getUid())
                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){

                    DocumentSnapshot shot = task.getResult();

                    name = (String) shot.get("full_name");
                    phone = (String) shot.get("mobile_no");

                    nameET.setText(name);
                    phoneET.setText(phone);

                }else {
                    Toast.makeText(getApplicationContext(), ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
