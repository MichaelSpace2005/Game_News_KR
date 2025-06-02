package com.example.sngnews.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sngnews.R;
import com.example.sngnews.app.App;
import com.example.sngnews.databinding.FragmentRegisterBinding;
import com.example.sngnews.models.DataBaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;
    private boolean isPasswordHidden = true;
    private boolean isRepeatPasswordHidden = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        applyClick();
    }

    private void applyClick(){
        binding.signInText.setOnClickListener(v -> {
            App.sharedManager.logOut();
            Navigation.findNavController(requireView()).navigate(R.id.action_homeFragment_to_loginFragment);
        });
        binding.signUpButton.setOnClickListener(v -> {
            register();
        });
        binding.eyeBtnPassword.setOnClickListener(v -> {
            if (isPasswordHidden){
                binding.eyeBtnPassword.setImageDrawable(requireContext().getDrawable(R.drawable.eye_open));
                binding.passwordEditText.setTransformationMethod(null);
            } else {
                binding.eyeBtnPassword.setImageDrawable(requireContext().getDrawable(R.drawable.eye_close));
                binding.passwordEditText.setTransformationMethod(new PasswordTransformationMethod());
            }
            isPasswordHidden = !isPasswordHidden;
        });
        binding.eyeBtnRepeatPassword.setOnClickListener(v -> {
            if (isRepeatPasswordHidden){
                binding.eyeBtnRepeatPassword.setImageDrawable(requireContext().getDrawable(R.drawable.eye_open));
                binding.repeatPasswordEditText.setTransformationMethod(null);
            } else {
                binding.eyeBtnRepeatPassword.setImageDrawable(requireContext().getDrawable(R.drawable.eye_close));
                binding.repeatPasswordEditText.setTransformationMethod(new PasswordTransformationMethod());
            }
            isRepeatPasswordHidden = !isRepeatPasswordHidden;
        });
    }

    private void register(){
        if (checkData()) {
            String email = binding.emailEditText.getText().toString();
            String password = binding.passwordEditText.getText().toString();
            String username = binding.userNameEditText.getText().toString();
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()){
                            createUser(email, username, task.getResult().getUser().getUid());
                            Navigation.findNavController(requireView()).popBackStack();
                        } else {
                            Toast.makeText(requireContext(), "Регистрация не выполнена: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void createUser(String email, String username, String documentPath){
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        database.collection("Users").document(documentPath).set(new DataBaseUser(username, email, new ArrayList<>()));

    }

    private boolean checkData(){
        if (!Patterns.EMAIL_ADDRESS.matcher(binding.emailEditText.getText()).matches()){
            Toast.makeText(requireContext(), "Некорректный email", Toast.LENGTH_SHORT).show();
            return false;
        } else if (binding.passwordEditText.getText().toString().length() < 6 || binding.passwordEditText.getText().toString().contains(" ")) {
            Toast.makeText(requireContext(), "Пароль должен быть не меньше 6ти символов", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!binding.passwordEditText.getText().toString().equals(binding.repeatPasswordEditText.getText().toString())) {
            Toast.makeText(requireContext(), "Пароли не совпадают", Toast.LENGTH_SHORT).show();
            return false;
        } else if (binding.userNameEditText.getText().toString().length() < 4 || binding.userNameEditText.getText().toString().contains(" ")) {
            Toast.makeText(requireContext(), "Имя пользователя должно быть не меньше 4х символов", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!binding.checkboxAgreeTermsConditions.isChecked()) {
            Toast.makeText(requireContext(), "Согласитесь с условиями приложения!!!", Toast.LENGTH_SHORT).show();
            return false;
        } else return true;
    }
}