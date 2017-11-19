package com.app.foodify.foodifyinc;

public class RecipeItem {

    private String heading;
    private String description;

    public RecipeItem(String heading, String description) {
        this.heading = heading;
        this.description = description;
    }

    public String getHeading() {
        return heading;
    }

    public String getDescription() {
        return description;
    }

}
