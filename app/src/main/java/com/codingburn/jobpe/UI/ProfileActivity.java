package com.codingburn.jobpe.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.codingburn.jobpe.R;
import com.codingburn.jobpe.databinding.ActivityProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {
    com.codingburn.jobpe.databinding.ActivityProfileBinding binding;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // retrieve data from firebase realtime database
        databaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        String id = mAuth.getCurrentUser().getUid();
        databaseReference.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    if (snapshot.exists()) {
                        String profileImg = snapshot.child("profileImg").getValue(String.class);
                        String name = snapshot.child("profileName").getValue(String.class);
                        String aboutMe = snapshot.child("aboutMe").getValue(String.class);
                        String skill = snapshot.child("skill").getValue(String.class);
                        String edu1Institute = snapshot.child("institute").getValue(String.class);
                        String edu1PassOutYear = snapshot.child("passoutYear").getValue(String.class);
                        String edu1Degree = snapshot.child("degree").getValue(String.class);
                        if (name != null) {
                            binding.profileName.setText(name);
                            binding.aboutMe.setText(aboutMe);
                            Picasso.get().load(profileImg).into(binding.profileImg);
                            binding.skill.setText(skill);
                            binding.edu1Institute.setText(edu1Institute);
                            binding.edu1PasoutDate.setText(edu1PassOutYear);
                            binding.edu1Degree.setText(edu1Degree);
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        // click editButton
        binding.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, EditActivity.class);
                startActivity(intent);
            }
        });

        // back button functionality
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}