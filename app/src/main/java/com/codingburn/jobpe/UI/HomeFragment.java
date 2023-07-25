package com.codingburn.jobpe.UI;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codingburn.jobpe.Adapter.HomeViewPager;
import com.codingburn.jobpe.R;
import com.codingburn.jobpe.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentHomeBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater,container,false);

//         set up viewPager
        HomeViewPager viewPagerAdapter = new HomeViewPager(getChildFragmentManager());
        binding.viewPager.setAdapter(viewPagerAdapter);
        binding.tab.setupWithViewPager(binding.viewPager);


        return binding.getRoot();
    }
}