package com.techno.misskeen.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.scalified.fab.ActionButton;
import com.techno.misskeen.Activity.CreateRecipe;
import com.techno.misskeen.Activity.RecipeDetail;
import com.techno.misskeen.AppController;
import com.techno.misskeen.LruBitmapCache;
import com.techno.misskeen.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.techno.misskeen.Recipe;
import com.techno.misskeen.RecipeListAdapter;

public class RecipeListFragment extends ListFragment {


    private ImageLoader mImageLoader;
    private static final String url = "http://ditoraharjo.co/misskeen/api/v1/recipe".toString().trim();
    private ProgressDialog pDialog;
    private List<Recipe> recipeList = new ArrayList<Recipe>();
    private ListView listView;
    private RecipeListAdapter adapter;
    private ActionButton btntambah;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.activity_recipe_list, container, false);
        return view;
    }
    public void onViewCreated(View view, Bundle savedInstanceState){

        listView = (ListView) getView().findViewById(R.id.recipelist);
        adapter = new RecipeListAdapter(this.getActivity(), recipeList);
        listView.setAdapter(adapter);


        pDialog = new ProgressDialog(getActivity());
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();
        getDataResep();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String recipeid = ((TextView) view.findViewById(R.id.recipeid)).getText().toString();
                Intent in = new Intent(getActivity(),RecipeDetail.class);
                in.putExtra("recipeid",recipeid);
                startActivity(in);
            }
        });
        btntambah=(ActionButton)getView().findViewById(R.id.btntambahresep);
        btntambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getActivity(), CreateRecipe.class);
                startActivity(in);
            }
        });

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


        JsonArrayRequest recipeReq = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {

            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {

                        JSONObject obj = response.getJSONObject(i);
                        Recipe recipe = new Recipe();
                        recipe.setRecipeid(obj.getString("id"));
                        recipe.setRecipethumbnail(obj.getString("image"));
                        recipe.setRecipedescription(obj.getString("description"));
                        recipe.setRecipename(obj.getString("name"));
                        recipe.setReciperating(obj.getString("rating"));

                        // adding movie to movies array
                        recipeList.add(recipe);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                // notifying list adapter about data changes
                // so that it renders the list view with updated data
                adapter.notifyDataSetChanged();

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.getMessage().toString(), Toast.LENGTH_LONG).show();
                    }
                });


        AppController.getInstance().addToRequestQueue(recipeReq);
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
        listView.setAdapter(adapter);
        pDialog.dismiss();
    }
}
