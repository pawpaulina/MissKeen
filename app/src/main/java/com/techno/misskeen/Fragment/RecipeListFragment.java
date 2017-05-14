package com.techno.misskeen.Fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class RecipeListFragment extends Fragment {

    ListView list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.activity_recipe_list, container, false);
        list = (ListView) getView().findViewById(R.id.recipelist);

        getDataResep();
        return view;
    }
    public static RecipeListFragment newInstance() {

        Bundle args = new Bundle();

        RecipeListFragment fragment = new RecipeListFragment();
        fragment.setArguments(args);
        return fragment;
    }
    //    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//    }


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
                        Toast.makeText(getContext(), error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    private void showJSON(String json) {
        JSONArray recipe = null;
        ArrayList<HashMap<String, String>> recipelist = new ArrayList<HashMap<String, String>>();
        ;

        try {

            recipe = new JSONArray(json);

            for (int i = 0; i < recipe.length(); i++) {
                JSONObject c = recipe.getJSONObject(i);
                String id = c.getString("id");
                String name = c.getString("name");
                String description = c.getString("description");
                String image = c.getString("image");
                String rating = c.getString("rating");

                HashMap<String, String> recipes = new HashMap<String, String>();

                recipes.put("id", id);
                recipes.put("name", name);
                recipes.put("description", description);
                recipes.put("image", image);
                recipes.put("rating", rating);

                recipelist.add(recipes);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ListAdapter adapter = new SimpleAdapter(
                getContext(), recipelist, R.layout.recipe_item,
                new String[]{"id", "name", "description", "image", "rating"},
                new int[]{R.id.recipeid, R.id.recipename, R.id.recipedescription, R.id.recipethumbnail, R.id.reciperating}
        );
        list.setAdapter(adapter);
    }
}
