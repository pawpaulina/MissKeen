package com.techno.misskeen.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.techno.misskeen.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.RequestBody;

/**
 * Created by Paulina on 5/8/2017.
 */
public class LoginActivity extends AppCompatActivity {
    private EditText editUsername;
    private EditText editPassword;

    private Button btnLogin;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = (Button) findViewById(R.id.btnLogin);
    }

    public void buttonLogin(View v)
    {
        try {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        editUsername = (EditText) findViewById(R.id.editUsername);
        editPassword = (EditText) findViewById(R.id.editPassword);
        String url = "http://ditoraharjo.co/misskeen/api/v1/user/auth".toString().trim();
        JSONObject jsonBody = new JSONObject();
            jsonBody.put("email", editUsername.getText().toString());
            jsonBody.put("password", editPassword.getText().toString());

        final String requestBody = jsonBody.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Intent i = new Intent(getApplicationContext(), RecipeListActivity.class);
                startActivity(i);
            }}, new Response.ErrorListener()
            {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Error Login : ", error.toString());
                }
            }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                    return null;
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {
                    responseString = String.valueOf(response.statusCode);
                    // can get more details such as response.headers
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }
        };
        requestQueue.add(stringRequest);
        }catch (Exception e)
        {
            Log.d("Exception Login : ", e.toString());
        }
    }

    private void showJSON(String json){


        JSONArray user = null;

        try {
            JSONObject jObject = new JSONObject(json);
            user = jObject.getJSONArray("result");

            for(int i=0; i<user.length(); i++){
                JSONObject c = user.getJSONObject(i);
                String id = c.getString("id");
                String username = c.getString("username");
                String password = c.getString("password");
                String token = c.getString("token");

                HashMap<String,String> users = new HashMap<String,String>();

                users.put("id",id);
                users.put("username",username);
                users.put("password",password);
                users.put("token",token);
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
}
