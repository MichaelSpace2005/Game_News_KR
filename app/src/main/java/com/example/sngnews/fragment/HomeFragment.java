package com.example.sngnews.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sngnews.R;
import com.example.sngnews.adapters.NewsAdapter;
import com.example.sngnews.app.App;
import com.example.sngnews.databinding.FragmentHomeBinding;
import com.example.sngnews.models.DataBaseUser;
import com.example.sngnews.models.NewsModel;
import com.example.sngnews.repository.FirebaseRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class HomeFragment extends Fragment implements NewsAdapter.OnClick {
    private FirebaseRepository repository;
    private DataBaseUser user;
    @Override
    public void clickMain(String link) {
        requireActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
    }

    @Override
    public void clickEye(int id) {
        repository.banId(id, FirebaseAuth.getInstance().getUid(), requireContext());
        repository.getNews();
    }


    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater);
        repository = new ViewModelProvider(this).get(FirebaseRepository.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        applyClick();
        setObservers();
        repository.getNews();

    }

    public void applyClick(){
        binding.aboutUsTxt.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_aboutUsFragment);
        });

        binding.logOutIcon.setOnClickListener(v -> {
            App.sharedManager.logOut();
            Navigation.findNavController(v).navigate(R.id.action_homeFragment_to_loginFragment);
        });
    }

    public void setAdapter(ArrayList<NewsModel> list){
        list.removeIf(it -> user.getBanIds().contains(it.getId()));
        binding.rvNews.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvNews.setAdapter(new NewsAdapter(list,this));

    }

    private void setObservers(){
        repository.isTaskReady.observe(getViewLifecycleOwner(),data-> {
            if (data){
                FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getUid()).get()
                        .addOnSuccessListener(result -> {
                            user = result.toObject(DataBaseUser.class);
                            setAdapter(repository.listNews);
                        });
                binding.pbLoad.setVisibility(View.GONE);
                repository.isTaskReady.setValue(false);
            }
        });
    }
}