package com.codingburn.jobpe.HomeTabUi;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codingburn.jobpe.Adapter.JobRecyclerAdapter;
import com.codingburn.jobpe.Model.JobModel;
import com.codingburn.jobpe.R;
import com.codingburn.jobpe.databinding.FragmentInternShipBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;


public class InternShipFragment extends Fragment {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    DatabaseReference databaseReference;
    ArrayList<JobModel> jobModelArrayList = new ArrayList<>();
    JobRecyclerAdapter adapter;

    FragmentInternShipBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInternShipBinding.inflate(inflater,container,false);


        // LinearLayout Manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        binding.internshipRecycler.setLayoutManager(layoutManager);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("Job").child("InternshipJob").child("InternshipJob").orderByChild("jobId")
                .limitToLast(1000000000).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        jobModelArrayList.clear();

                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            // Use the JobModel class directly instead of String class
                            JobModel model = dataSnapshot.getValue(JobModel.class);
                            if (model != null) {
                                jobModelArrayList.add(model);
                            }
                        }

                        // Sort the list in descending order based on item ID
                        Collections.sort(jobModelArrayList, new Comparator<JobModel>() {
                            @Override
                            public int compare(JobModel job1, JobModel job2) {
                                return job2.getJobId().compareTo(job1.getJobId());
                            }
                        });

                        adapter = new JobRecyclerAdapter(getContext(),jobModelArrayList);
                        binding.internshipRecycler.setAdapter(adapter);
                    }



                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(requireContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });



        return binding.getRoot();
    }
}