package com.example.sngnews.repository;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sngnews.models.DataBaseUser;
import com.example.sngnews.models.NewsModel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class FirebaseRepository extends ViewModel {
    public ArrayList<NewsModel> listNews = new ArrayList<NewsModel>();
    public MutableLiveData<Boolean> isTaskReady = new MutableLiveData<>(false);
    private FirebaseFirestore dataBase = FirebaseFirestore.getInstance();

    private List<Integer> banIds = new ArrayList<>();

    public void banId(Integer id, String uid, Context context) {
        dataBase.collection("Users").document(uid).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DataBaseUser user = task.getResult().toObject(DataBaseUser.class);
                        banIds = user.getBanIds();
                        banIds.add(id);
                        user.setBanIds(banIds);
                        dataBase.collection("Users").document(uid).set(user)
                                .addOnFailureListener(error -> {
                                    Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                    } else {
                        Toast.makeText(context, task.getException().getMessage() == null ? "Возникла ошибка" : task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void getNews() {
        listNews.clear();
        dataBase.collection("New").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot snapshot : task.getResult().getDocuments()) {
                            NewsModel news = snapshot.toObject(NewsModel.class);
                            listNews.add(news);
                        }

                        isTaskReady.setValue(true);
                    } else {
                        Log.d("ERROR", task.getException().getMessage());
                    }
                });
    }
}
