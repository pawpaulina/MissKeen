package com.techno.misskeen;


import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

public class RecipeListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Recipe> recipeItems;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public RecipeListAdapter(Activity activity, List<Recipe> recipeItems) {
        this.activity = activity;
        this.recipeItems = recipeItems;
    }

    @Override
    public int getCount() {
        return recipeItems.size();
    }

    @Override
    public Object getItem(int location) {
        return recipeItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.recipe_item, null);

        if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.recipethumbnail);
        TextView recipeid=(TextView) convertView.findViewById(R.id.recipeid);
        TextView recipename = (TextView) convertView.findViewById(R.id.recipename);
        TextView recipedescription = (TextView) convertView.findViewById(R.id.recipedescription);
        TextView reciperating = (TextView) convertView.findViewById(R.id.reciperating);
   

        // getting movie data for the row
        Recipe m = recipeItems.get(position);

     
        thumbNail.setImageUrl(m.getRecipethumbnail(), imageLoader);
        recipeid.setText(m.getRecipeid());
        recipename.setText(m.getRecipename());
        recipedescription.setText(m.getRecipedescription());
        reciperating.setText(m.getReciperating());

        
        

        return convertView;
    }

}

