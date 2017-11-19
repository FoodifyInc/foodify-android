package com.app.foodify.foodifyinc;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecipeDisplay extends AppCompatActivity {

    private ArrayList<String> recipesList=new ArrayList <>();
    private ArrayAdapter<String> arrayAdapter;
    private EditText text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_display);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ListView listView = (ListView)findViewById(R.id.displayRecipes);

        setSupportActionBar(toolbar);

        String[] item={"One","Two","Three","Four"};
        recipesList=new ArrayList <>(Arrays.asList(item));

        arrayAdapter = new ArrayAdapter<>(this, R.layout.content_recipe_display,recipesList);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView <?> parent, View view, int position, long id) {
                String gettingURL=recipesList.get(position);
                Toast.makeText(getApplicationContext(),gettingURL, Toast.LENGTH_LONG).show();
            }
        });



    }
}
