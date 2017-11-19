package com.app.foodify.foodifyinc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecipeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private List<RecipeItem> recipeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recipeList = new ArrayList<>();
        for(int i = 0; i < 10; i++){
            RecipeItem recipeItem = new RecipeItem("Heading " + (i+1), "Lorem ipsum dummy text");
            recipeList.add(recipeItem);
        }

        adapter = new RecipeAdapter(recipeList, this);
        recyclerView.setAdapter(adapter);
    }
}
