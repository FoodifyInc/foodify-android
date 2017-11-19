package com.app.foodify.foodifyinc;

/**
 * Created by agoston on 11/18/17.
 */

import android.graphics.Bitmap;

import java.net.URL;

/**
 * Created by agoston on 11/18/17.
 */

public class Recipe {
    /**
     * Name of the recipe
     */
    private String recipeName;

    /**
     * The URL to the recipe
     */
    private URL recipeUrl;

    /**
     * The image of the final result
     */
    private Bitmap image;

    public Recipe(String recipeName){
        this.recipeName = recipeName;
    }

    public Recipe(String recipeName, URL recipeUrl, Bitmap image) {
        this.recipeName = recipeName;
        this.recipeUrl = recipeUrl;
        this.image = image;
    }

    public URL getRecipeUrl() {
        return recipeUrl;
    }

    public void setRecipeUrl(URL recipeUrl) {
        this.recipeUrl = recipeUrl;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getRecipeName() {
        return recipeName;
    }

    public void setRecipeName(String recipeName) {
        this.recipeName = recipeName;
    }
}
