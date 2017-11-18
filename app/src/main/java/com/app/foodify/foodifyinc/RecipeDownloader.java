package com.app.foodify.foodifyinc;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by agoston on 11/18/17.
 */

public class RecipeDownloader extends AsyncTask<String, Void, ArrayList<Recipe>> {

    private static final String APP_ID = "4df6fe39";
    private static final String API_KEY = "1fbf798ae6b2bc5f75ee3a6a60de044a";
    private static final String API_PATH = "https://api.edamam.com/search?app_id=" + APP_ID +
            "&app_key=" + API_KEY;

    @Override
    protected ArrayList<Recipe> doInBackground(String... strings) {
        String foodName = strings[0];
        String query = API_PATH + "&q=" + foodName;

        ArrayList<Recipe> recipes = new ArrayList<>();

        StringBuilder builder = new StringBuilder();

        // Send request to API and store response as a String. The String will
        // then be converted to a JSONObject, from which the recipes will be
        // parsed
        try {
            // Send request to API with food name
            URL url = new URL(query);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            InputStream input = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            String line = null;

            // Store request as String so it can be converted to JSON
            while((line = reader.readLine()) != null) {
                builder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            // Convert returned String from API into JSON
            JSONObject json = new JSONObject(builder.toString());

            // "hits" is the field that contains all the recipes that were found
            if(json.has("hits")) {
                JSONArray hits = json.getJSONArray("hits");

                for(int i = 0; i < hits.length(); ++i) {
                    // Each "recipe" is a JSON object
                    JSONObject jsonRecipe = hits.getJSONObject(i);

                    Recipe recipe = extractRecipe(jsonRecipe);
                    recipes.add(recipe);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return recipes;
    }

    /**
     * Extracts a Recipe from a JSON recipe.
     * @param jsonRecipe Serialized recipe
     * @return Recipe
     * @throws JSONException
     * @throws MalformedURLException
     */
    private Recipe extractRecipe(JSONObject jsonRecipe) throws JSONException, MalformedURLException {
        // Get name of recipe
        String recipeName = jsonRecipe.getString("label");
        // Get recipe image location
        URL imgURL = new URL(jsonRecipe.getString("image"));
        // Get recipe location
        URL recipeUrl = new URL(jsonRecipe.getString("url"));

        Bitmap img = fetchImg(imgURL);

        return new Recipe(recipeName, recipeUrl, img);
    }

    /**
     * Downloads a bitmap from the specified URL.
     * @param url URL to download image from
     * @return Bitmap if it's an image, null otherwise
     */
    private Bitmap fetchImg(URL url) {
        // Download image from URL
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;
        } catch (IOException e) {
            return null;
        }
    }
}