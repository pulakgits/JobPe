package com.codingburn.jobpe.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.codingburn.jobpe.R;
import com.codingburn.jobpe.databinding.ActivityEditBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class EditActivity extends AppCompatActivity {
    ActivityEditBinding binding;
    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);

    private final  int GALLERY_REQ_CODE = 1000;
    Uri uriFilepath;
    Bitmap bitmap;
    ProgressDialog dialog;
    FirebaseAuth mAuth;
    private final boolean isClicked=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dialog = new ProgressDialog(this);
        dialog.setMessage("Update Profile.....");

        // click save button
        binding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });

        // click set edit image button
        binding.editImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,GALLERY_REQ_CODE);
            }
        });

        // set data into editProfile Activity method
        setData();

        // back to activity
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//              startActivity(new Intent(EditProfileActivity.this, SettingActivity.class));
                onBackPressed();
                finish();
            }
        });
    }
    private void setData() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot userSnapshot : snapshot.getChildren()){

                    if(snapshot.exists()){
                        String name = snapshot.child("profileName").getValue(String.class);
                        String image = snapshot.child("profileImg").getValue(String.class);
                        String aboutMe = snapshot.child("aboutMe").getValue(String.class);
                        String skill = snapshot.child("skill").getValue(String.class);
                        String institute = snapshot.child("institute").getValue(String.class);
                        String passoutYear = snapshot.child("passoutYear").getValue(String.class);
                        String degree = snapshot.child("degree").getValue(String.class);

                        if(name != null || image != null || aboutMe != null || skill != null || institute != null){
                            binding.personName.setText(name);
                            Picasso.get().load(image).into(binding.profileImage);
                            binding.aboutMe.setText(aboutMe);
                            binding.personSkill.setText(skill);
                            binding.personInstitute.setText(institute);
                            binding.personPassoutYear.setText(passoutYear);
                            binding.personDegree.setText(degree);
                        }else {
                            Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // updateProfile method
    private void updateProfile() {
        dialog.show();
        String profileName, aboutMe, skill, instituteName,passoutYear,degree;
        profileName = binding.personName.getText().toString();
        aboutMe = binding.aboutMe.getText().toString();
        skill = binding.personSkill.getText().toString();
        instituteName = binding.personInstitute.getText().toString();
        passoutYear = binding.personPassoutYear.getText().toString();
        degree = binding.personDegree.getText().toString();

        if (uriFilepath != null) {
            // Upload the image to Firebase Storage
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReference().child("images/" + userId + ".jpg");
            UploadTask uploadTask = storageRef.putFile(uriFilepath);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Get the download URL of the uploaded image
                    storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            // Update the user's profile with the image URL and other details
                            String imageUrl = uri.toString();

                            Map<String, Object> updates = new HashMap<>();
                            updates.put("profileImg", imageUrl); // add the image URL to the updates
                            updates.put("profileName", profileName);
                            updates.put("aboutMe", aboutMe);
                            updates.put("skill", skill);
                            updates.put("institute", instituteName);
                            updates.put("passoutYear",passoutYear);
                            updates.put("degree",degree);

                            databaseReference.updateChildren(updates, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                    if(error != null){
                                        Log.e("Firebase Update Error", error.getMessage());
                                        Toast.makeText(getApplicationContext(), "Update failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                                    }else{
                                        dialog.dismiss();
                                        onBackPressed();
                                        Toast.makeText(getApplicationContext(), "Update successful!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });
                }
            });
        } else {
            // If the user didn't select a new image, update the profile with other details only
            Map<String, Object> updates = new HashMap<>();
            updates.put("profileName", profileName);
            updates.put("aboutMe", aboutMe);
            updates.put("skill", skill);
            updates.put("institute", instituteName);
            updates.put("passoutYear",passoutYear);
            updates.put("degree",degree);

            databaseReference.updateChildren(updates, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                    if(error != null){
                        Log.e("Firebase Update Error", error.getMessage());
                        Toast.makeText(getApplicationContext(), "Update failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }else{
                        dialog.dismiss();
                        onBackPressed();
                        Toast.makeText(getApplicationContext(), "Update successful!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == GALLERY_REQ_CODE && resultCode == RESULT_OK) {
            uriFilepath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uriFilepath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                binding.profileImage.setImageBitmap(bitmap);
            } catch (Exception exception) {
                System.out.printf(String.valueOf(exception));
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}