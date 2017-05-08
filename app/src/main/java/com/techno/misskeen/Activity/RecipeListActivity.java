package com.techno.misskeen.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.techno.misskeen.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class RecipeListActivity extends AppCompatActivity {

    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        list = (ListView) findViewById(R.id.recipelist);
        getDataResep();
    }



    private void getDataResep() {
        String url = "http://ditoraharjo.co/misskeen/api/v1/recipe".toString().trim();

        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RecipeListActivity.this,error.getMessage().toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void showJSON(String json){


        JSONArray recipe = null;
        ArrayList<HashMap<String, String>> recipelist = new ArrayList<HashMap<String,String>>();;



        try {
            JSONObject jObject = new JSONObject(json);
            recipe = jObject.getJSONArray("result");




            for(int i=0;i<recipe.length();i++){
                JSONObject c = recipe.getJSONObject(i);
                String id = c.getString("id");
                String name = c.getString("name");
                String description = c.getString("description");
                String image = c.getString("image");
                String rating = c.getString("rating");

                HashMap<String,String> recipes = new HashMap<String,String>();

                recipes.put("id",id);
                recipes.put("name",name);
                recipes.put("description",description);
                recipes.put("image",image);
                recipes.put("rating",rating);

                recipelist.add(recipes);

            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        ListAdapter adapter = new SimpleAdapter(
                RecipeListActivity.this, recipelist, R.layout.recipe_item,
                new String[]{"id","name","description","image","rating"},
                new int[]{R.id.recipeid, R.id.recipedescription, R.id.recipethumbnail, R.id.reciperating}
        );
        list.setAdapter(adapter);
    }
}
