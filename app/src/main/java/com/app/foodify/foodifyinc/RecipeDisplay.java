package com.app.foodify.foodifyinc;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Arrays;

public class RecipeDisplay extends AppCompatActivity {

    private ArrayList<String> recipesList=new ArrayList <>();
    private ArrayAdapter<String> arrayAdapter;
    private EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_display);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String[] item={"One","Two","Three","Four"};
        recipesList=new ArrayList <>(Arrays.asList(item));




    }
}
