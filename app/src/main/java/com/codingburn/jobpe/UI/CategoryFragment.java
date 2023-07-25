package com.codingburn.jobpe.UI;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codingburn.jobpe.Adapter.JobCategoryAdapter;
import com.codingburn.jobpe.Adapter.JobRecyclerAdapter;
import com.codingburn.jobpe.Model.JobModel;
import com.codingburn.jobpe.R;
import com.codingburn.jobpe.databinding.FragmentCategoryBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.codingburn.jobpe.Model.categoryModel;

import java.util.ArrayList;

public class CategoryFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentCategoryBinding binding;
    DatabaseReference databaseReference;
    ArrayList<categoryModel> arrayList;
    JobCategoryAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCategoryBinding.inflate(inflater,container,false);

        arrayList = new ArrayList<>();

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),2);
        binding.newAgeJob.setLayoutManager(layoutManager);


        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("AllJob").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    // Use the JobModel class directly instead of String class
                    categoryModel model = dataSnapshot.getValue(categoryModel.class);
                    if (model != null) {
                        arrayList.add(model);
                    }
                }
                adapter = new JobCategoryAdapter(getContext(),arrayList);
                binding.newAgeJob.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
       return binding.getRoot();
    }
}