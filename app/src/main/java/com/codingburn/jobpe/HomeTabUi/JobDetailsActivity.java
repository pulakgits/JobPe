package com.codingburn.jobpe.HomeTabUi;

import static androidx.core.content.ContentProviderCompat.requireContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.codingburn.jobpe.Model.JobModel;
import com.codingburn.jobpe.R;
import com.codingburn.jobpe.databinding.ActivityJobDetailsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class JobDetailsActivity extends AppCompatActivity {
    ActivityJobDetailsBinding binding;
    // Retrieve the online application website link from the database
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJobDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseReference = FirebaseDatabase.getInstance().getReference();

        // Getting the intent that started the activity
        Intent intent = getIntent();

        // click back button
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        // Retrieving the extra data using the key
        String link = intent.getStringExtra("link");
        String jobId = intent.getStringExtra("jobId");
        String jobHeadline = intent.getStringExtra("jobHeadline");
        String companyLogo = intent.getStringExtra("companyLogo");
        String aboutCompany = intent.getStringExtra("aboutCompany");
        String companyName = intent.getStringExtra("companyName");
        String postName = intent.getStringExtra("postName");
        String salary = intent.getStringExtra("salary");
        String experience = intent.getStringExtra("experience");
        String jobLocation = intent.getStringExtra("jobLocation");
        String batch = intent.getStringExtra("batch");
        String companyWebsite = intent.getStringExtra("companyWebsite");
        String lastDate = intent.getStringExtra("lastDate");
        String qualifyRequired = intent.getStringExtra("qualifyRequired");
        String skill = intent.getStringExtra("skill");
        String selectionProcess = intent.getStringExtra("selectionProcess");
        String applyProcess = intent.getStringExtra("applyProcess");

        //fill company Details page
        binding.jobHeadline.setText(jobHeadline);
        Picasso.get().load(companyLogo).into(binding.companyImage);
        binding.aboutCompany.setText(aboutCompany);
        binding.companyTableHeadline.setText(companyName+ " Requriments on 2023");
        binding.companyName.setText(companyName);
        binding.postName.setText(postName);
        binding.salary.setText(salary);
        binding.experience.setText(experience);
        binding.jobLocation.setText(jobLocation);
        binding.batch.setText(batch);
        binding.companyWebsite.setText(companyWebsite);
        binding.lastDate.setText(lastDate);
        binding.qualifyRequired.setText(qualifyRequired);
        binding.skill.setText(skill.replace("\\n", "\n"));
        binding.selectionProcess.setText(selectionProcess);
        binding.applyProcess.setText(applyProcess);

        // apply button functionality
        binding.applyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child(jobId).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (link != null && !link.isEmpty()) {
                            // Redirect to the website using a web browser
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                            startActivity(intent);
                        } else {
                            Toast.makeText(JobDetailsActivity.this, "No application link found", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(JobDetailsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}