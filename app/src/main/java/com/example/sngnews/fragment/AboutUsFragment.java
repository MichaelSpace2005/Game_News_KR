package com.example.sngnews.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sngnews.R;
import com.example.sngnews.databinding.FragmentAboutUsBinding;
import com.example.sngnews.databinding.FragmentLoginBinding;


public class AboutUsFragment extends Fragment {

    private FragmentAboutUsBinding binding;



    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAboutUsBinding.inflate(inflater);
        return binding.getRoot();
    }
}
