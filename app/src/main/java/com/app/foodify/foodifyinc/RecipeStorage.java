package com.app.foodify.foodifyinc;

import java.util.ArrayList;

/**
 * Created by agoston on 11/19/17.
 */

/**
 * Stores Recipes so they can be transferred between Activities.
 */
public class RecipeStorage {
    private static RecipeStorage instance = null;

    private ArrayList<Recipe> recipes;

    protected RecipeStorage() {
        recipes = new ArrayList<>();
    }

    public static RecipeStorage getInstance() {
        if(instance == null) {
            instance = new RecipeStorage();
        }

        return instance;
    }


    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
    }
}
