package com.example.sngnews.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sngnews.R;
import com.example.sngnews.app.App;
import com.example.sngnews.databinding.FragmentLoginBinding;
import com.google.firebase.auth.FirebaseAuth;


public class LoginFragment extends Fragment {

    private boolean isPasswordHidden = true;
    private FragmentLoginBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        applyClick();

    }

    private void applyClick() {
        binding.eyeBtn.setOnClickListener(v -> {
            if (isPasswordHidden){
                binding.eyeBtn.setImageDrawable(requireContext().getDrawable(R.drawable.eye_open));
                binding.passwordEditText.setTransformationMethod(null);
            } else {
                binding.eyeBtn.setImageDrawable(requireContext().getDrawable(R.drawable.eye_close));
                binding.passwordEditText.setTransformationMethod(new PasswordTransformationMethod());
            }
            isPasswordHidden = !isPasswordHidden;
        });

        binding.forgotPassword.setOnClickListener(v -> {

        });

        binding.signInButton.setOnClickListener(v -> {
            auth(binding.emailEditText.getText().toString(),binding.passwordEditText.getText().toString());
        });

        binding.signUpText.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_registerFragment);
        });
    }

    private void auth(String email, String password) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (binding.checkboxRememberMe.isChecked()) App.sharedManager.logIn();
                        Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_homeFragment);
                        Toast.makeText(requireContext(), "Authorization successful", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(requireContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void sendPasswordResetEmail(String email){

    }



}