package com.codingburn.jobpe.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.codingburn.jobpe.MainActivity;
import com.codingburn.jobpe.Model.User;
import com.codingburn.jobpe.R;
import com.codingburn.jobpe.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUpActivity extends AppCompatActivity {
    ActivitySignUpBinding binding;
    FirebaseAuth mAuth;
    ProgressDialog dialog;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        dialog = new ProgressDialog(this);
        dialog.setMessage("Creating new account....");

        binding.signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Authentication();
            }
        });

        // this page to login page
        binding.Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void Authentication() {

        dialog.show();
        String profileName,personEmail,personPassword;
        profileName = binding.eTName.getText().toString();
        personEmail = binding.eTEmail.getText().toString();
        personPassword = binding.eTPassword.getText().toString();

        if(profileName.isEmpty() || personEmail.isEmpty() || personPassword.isEmpty()){
            dialog.dismiss();
            Toast.makeText(getApplicationContext(), "Please Fill Up", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(personEmail,personPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    dialog.dismiss();

                    String aboutMe = "Hi";
                    String personDob = "30/10/2000";
                    String profileImg = "https://assets.stickpng.com/images/585e4bf3cb11b227491c339a.png";
                    String skill = "Hi";
                    String institute = "Dream";
                    String passoutYear = "2023";
                    String degree = "B.Tech";


                    User firebaseUser = new User(profileImg,profileName,personEmail,personPassword,aboutMe,skill,institute,passoutYear,degree);
                    String uid = task.getResult().getUser().getUid();


                    // database reference
                    databaseReference = FirebaseDatabase.getInstance().getReference("Users");
                    databaseReference.child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                // Item already exists in database
                                intent();
                            } else {
                                // Item doesn't exist in database, add it
                                databaseReference.child(uid).setValue(firebaseUser);
                                intent();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(SignUpActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    // start intent method
    private  void  intent(){
        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser() != null){
            intent();
        }
    }

//    private void intents() {
//        Intent intent = new Intent(SignUpActivity.this, com.basetechz.showbox.E_Authentication.UpdateProfileActivity.class);
//        startActivity(intent);
//        finish();
//    }
}