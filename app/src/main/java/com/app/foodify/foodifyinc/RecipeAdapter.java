package com.app.foodify.foodifyinc;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private List<RecipeItem> recipeItems;
    private Context context;

    public RecipeAdapter(List<RecipeItem> recipeItems, Context context) {
        this.recipeItems = recipeItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RecipeItem recipeItem = recipeItems.get(position);

        holder.textViewHeading.setText(recipeItem.getHeading());
        holder.textViewDescription.setText(recipeItem.getDescription());
    }

    @Override
    public int getItemCount() {
        return recipeItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView textViewHeading;
        TextView textViewDescription;

        ViewHolder(View itemView) {
            super(itemView);

            textViewHeading = itemView.findViewById(R.id.textViewHead);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
        }
    }
}
