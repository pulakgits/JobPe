package com.codingburn.jobpe.HomeTabUi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.codingburn.jobpe.Adapter.JobRecyclerAdapter;
import com.codingburn.jobpe.Model.JobModel;
import com.codingburn.jobpe.databinding.ActivityJobScrollBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class AfterClickJobType extends AppCompatActivity {
    ActivityJobScrollBinding binding;
    ArrayList<JobModel> arrayList = new ArrayList<>();
    DatabaseReference databaseReference;
    JobRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityJobScrollBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // layoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(AfterClickJobType.this);
        binding.jobs.setLayoutManager(layoutManager);


        Intent intent = getIntent();

        String jobType = intent.getStringExtra("jobType");

        //retrieve data from firebase realtime database
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("AllJob").child(jobType).child(jobType).orderByChild("jobId").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    // Use the JobModel class directly instead of String class
                    JobModel model = dataSnapshot.getValue(JobModel.class);
                    if (model != null) {
                        arrayList.add(model);
                    }
                }
                // Sort the list in descending order based on item ID
                Collections.sort(arrayList, new Comparator<JobModel>() {
                    @Override
                    public int compare(JobModel job1, JobModel job2) {
                        return job2.getJobId().compareTo(job1.getJobId());
                    }
                });

                adapter = new JobRecyclerAdapter(AfterClickJobType.this,arrayList);
                binding.jobs.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AfterClickJobType.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

        // back btn
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });






    }
}