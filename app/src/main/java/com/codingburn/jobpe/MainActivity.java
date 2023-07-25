package com.codingburn.jobpe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.codingburn.jobpe.UI.CategoryFragment;
import com.codingburn.jobpe.UI.HomeFragment;
import com.codingburn.jobpe.UI.LoginActivity;
import com.codingburn.jobpe.UI.ProfileActivity;
import com.codingburn.jobpe.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    FirebaseAuth mAuth;
    ProgressDialog dialog;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        String currentUserId = mAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference();

         databaseReference.child("Users").child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){

                    if(snapshot.exists()){
                        String name = snapshot.child("profileName").getValue(String.class);
                        String firstName = name.split("\\s+")[0];
                        if(name != null){
                            // set user name to mainActivity toolBar
                            binding.userName.setText("Hello, "+ firstName);
                        }else{
                            binding.userName.setText("Null Value");
                        }
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        dialog = new ProgressDialog(this);
        dialog.setMessage("Logout..");
        // after click logoutButton
        binding.Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                mAuth.signOut();
                dialog.dismiss();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });

        // bottom navigation items color & txt color set
        BottomNavigationView bottomNavigationView = findViewById(R.id.bnView);
        @SuppressLint({"ResourceType", "UseCompatLoadingForColorStateLists"})
        ColorStateList iconColorStateList = getResources().getColorStateList(R.drawable.bnview_item_color);
        @SuppressLint({"ResourceType", "UseCompatLoadingForColorStateLists"}) ColorStateList
                textColorStateList = getResources().getColorStateList(R.drawable.bnview_item_text_color);
        bottomNavigationView.setItemIconTintList(iconColorStateList);
        bottomNavigationView.setItemTextColor(textColorStateList);

        // click item perform operation
        binding.bnView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                // find id for MenuItem
                int id = item.getItemId();

                if(id== R.id.home){
                    loadFragment(new HomeFragment(),false);
//                    binding.toolbar.setVisibility(View.VISIBLE);
                } else if (id == R.id.category) {
                    loadFragment(new CategoryFragment(),false);
//                    binding.toolbar.setVisibility(View.VISIBLE);
                } else if (id==R.id.profile) {
                   Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                   startActivity(intent);

                }
                return true;
            }

        });
        binding.bnView.setSelectedItemId(R.id.home);
    }

    private void loadFragment(Fragment fragment, boolean flag) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        if(flag){
            ft.add(R.id.fragment_container,fragment);
        }else {
            ft.replace(R.id.fragment_container,fragment);
        }
        ft.commit();
    }
}