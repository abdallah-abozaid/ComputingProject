package com.abdulla.bmi.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;


import com.example.bmiProject.Foods;
import com.example.bmiProject.listFoodsAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


import java.util.ArrayList;
import java.util.List;

public class activityListFood extends AppCompatActivity {
    RecyclerView recyclerView;
    com.example.bmiProject.listFoodsAdapter listFoodsAdapter;
    List<Foods> foods;
    String[] strings;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.bmiProject.R.layout.activity_list_food);
        recyclerView = findViewById(com.example.bmiProject.R.id.rv_foods);
        strings = getResources().getStringArray(com.example.bmiProject.R.array.planets_array);
        foods = new ArrayList<>();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("food").whereEqualTo("uId", firebaseAuth.getUid()).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String nameFoods = document.getData().get("nameFoods").toString();
                                String claryFoods = document.getData().get("claryFoods").toString();
                                String FBUri = document.getData().get("fburi").toString();
                                int idCategoryFoods = Integer.parseInt(document.getData().get("categoryFoodsName").toString());
                                String category = strings[idCategoryFoods];
                                String did = document.getId();
                                foods.add(new Foods(firebaseAuth.getUid(), claryFoods, nameFoods, FBUri, category, did, idCategoryFoods));
                            }
                            listFoodsAdapter = new listFoodsAdapter(getApplicationContext(), foods);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            recyclerView.setAdapter(listFoodsAdapter);
                            listFoodsAdapter.OnItemCliclkLisener(new listFoodsAdapter.ClickListener() {
                                @Override
                                public void onClick(Foods result) {
                                    Intent i = new Intent(getApplicationContext(), EditFoodActivity.class);
                                    i.putExtra("name", result.getNameFoods());
                                    i.putExtra("c", result.getClaryFoods());
                                    i.putExtra("image", result.getFBUri());
                                    i.putExtra("dId", result.getdIDFoods());
                                    i.putExtra("cat", result.getCategoryFoodsName());
                                    i.putExtra("id", result.getIdCategoryFoods());
                                    startActivity(i);
                                }
                            });

                        }
                    }
                });
    }

}