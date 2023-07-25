package com.codingburn.jobpe.HomeTabUi;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.codingburn.jobpe.Adapter.JobRecyclerAdapter;
import com.codingburn.jobpe.Model.JobModel;
import com.codingburn.jobpe.R;
import com.codingburn.jobpe.databinding.FragmentFreshersJobBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FreshersJobFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentFreshersJobBinding binding;
    DatabaseReference databaseReference;
    ArrayList<JobModel> jobModelArrayList = new ArrayList<>();
    JobRecyclerAdapter adapter;
    androidx.appcompat.widget.SearchView searchView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentFreshersJobBinding.inflate(inflater, container, false);


        // LinearLayout Manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext());
        binding.fresherJobRecycler.setLayoutManager(layoutManager);


        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.child("Job").child("FreshersJob").child("FreshersJob").orderByChild("jobId")
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
                        binding.fresherJobRecycler.setVisibility(View.VISIBLE);
                        binding.shimmer.setVisibility(View.GONE);
                        adapter = new JobRecyclerAdapter(getContext(), jobModelArrayList);
                        binding.fresherJobRecycler.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(requireContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        return binding.getRoot();
    }
    private void filteredList(String newText) {
        // Filter the ArrayList based on the search query
        ArrayList<JobModel> filteredList = new ArrayList<>();
        for (JobModel model : jobModelArrayList) {
            if (model.getJobHeadline().toLowerCase().contains(newText.toLowerCase()) && !filteredList.contains(model)) {
                filteredList.add(model);
            }
        }
        // Update the RecyclerView adapter with the filtered data
        if (filteredList.isEmpty()) {
            Toast.makeText(getContext(), "No data found", Toast.LENGTH_SHORT).show();
        } else {
            adapter.setFilter(filteredList);
        }
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.action_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchItem.getActionView();

        // Set the query text listener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Handle search query submission if needed
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Filter the ArrayList based on the search query
                filteredList(newText);

                return true;
            }
        });

        // Get the search AutoCompleteTextView
        SearchView.SearchAutoComplete searchAutoComplete = searchView.findViewById(androidx.appcompat.R.id.search_src_text);

        // Change the search text color
        searchAutoComplete.setTextColor(Color.WHITE);

        // Change the hint and hit text color
        searchAutoComplete.setHintTextColor(Color.WHITE);
        searchAutoComplete.setHighlightColor(Color.WHITE);

        // Set the hint text
        searchView.setQueryHint("Search.."); // Replace "search" with your desired hint text

        // Change the close button color
        ImageView closeButton = searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
        closeButton.setColorFilter(Color.WHITE);

        // Change the back button color
        int backButtonId = androidx.appcompat.R.id.search_mag_icon;
        ImageView backButton = searchView.findViewById(backButtonId);
        if (backButton != null) {
            backButton.setColorFilter(Color.WHITE);
        }

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.action_search) {
            // Handle search action here
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
