package com.techno.misskeen.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.techno.misskeen.Client;
import com.techno.misskeen.Helper.PrefHelper;
import com.techno.misskeen.R;
import com.techno.misskeen.Rest;
import com.techno.misskeen.User;
import com.techno.misskeen.UserObject;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Paulina on 5/8/2017.
 */
public class LoginActivity extends AppCompatActivity {
    private EditText editUsername;
    private EditText editPassword;

    private Button btnLogin;
    private Button btnRegis;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegis = (Button)findViewById(R.id.btnRegis);
    }
    public void buttonRegis(View v)
    {
        Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(i);
    }
    public void buttonLogin(View v)
    {
        editUsername = (EditText) findViewById(R.id.editUsername);
        editPassword = (EditText) findViewById(R.id.editPassword);
        final UserObject user = new UserObject(editUsername.getText().toString(), editPassword.getText().toString());
        Rest rest = Client.getClient().create(Rest.class);
        Call<User> call = rest.getLogin(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, retrofit2.Response<User> response) {
                if(response.body().getStatus().equals("true"))
                {
                    UserObject userObject = new UserObject();
                    userObject = response.body().getUser();
                    saveObjectToSharedPreference(getApplicationContext(), "mPreference", "mObjectKey", userObject);

                    PrefHelper.saveToPref(getApplicationContext(), "email", "password");
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);


//                    RecipeListFragment.newInstance();
//                    FragmentManager fm= getSupportFragmentManager();
//                    fm.beginTransaction().add(RecipeListFragment.newInstance(),"").commit();
                }
                else
                    if(response.body().getStatus().equals("false"))
                    {
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginActivity.this);
                        alertDialogBuilder.setMessage(response.body().getInfo());
                        alertDialogBuilder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                if(isOnline()) {
                        Log.d("Error Login : ", t.toString());
                }
                else
                {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginActivity.this);
                    alertDialogBuilder.setMessage("Koneksi internet tidak tersedia");
                    alertDialogBuilder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                }
            }
        });

    }
    public static void saveObjectToSharedPreference(Context context, String preferenceFileName, String serializedObjectKey, Object object) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preferenceFileName, 0);
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        final Gson gson = new Gson();
        String serializedObject = gson.toJson(object);
        sharedPreferencesEditor.putString(serializedObjectKey, serializedObject);
        sharedPreferencesEditor.apply();
    }
//
//    public void buttonLogin(View v)
//    {
//        try {
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        editUsername = (EditText) findViewById(R.id.editUsername);
//        editPassword = (EditText) findViewById(R.id.editPassword);
//        String url = "http://ditoraharjo.co/misskeen/api/v1/user/auth".toString().trim();
//        JSONObject jsonBody = new JSONObject();
//            jsonBody.put("email", editUsername.getText().toString());
//            jsonBody.put("password", editPassword.getText().toString());
//
//        final String requestBody = jsonBody.toString();
//
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                    Log.d("Response : ", response);
//                    PrefHelper.saveToPref(getApplicationContext(), "email", "password");
//                    Intent i = new Intent(getApplicationContext(), RecipeListFragment.class);
//                    startActivity(i);
//
//            }}, new Response.ErrorListener()
//            {
//                @Override
//                public void onErrorResponse(VolleyError error) {
//                    if(isOnline()) {
//                        Log.d("Error Login : ", error.toString());
//                    }
//                    else
//                    {
//                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginActivity.this);
//                        alertDialogBuilder.setMessage("Koneksi internet tidak tersedia");
//                        alertDialogBuilder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                            }
//                        });
//                        AlertDialog alertDialog = alertDialogBuilder.create();
//                        alertDialog.show();
//                    }
//                }
//            }) {
//            @Override
//            public String getBodyContentType() {
//                return "application/json; charset=utf-8";
//            }
//
//            @Override
//            public byte[] getBody() throws AuthFailureError {
//                try {
//                    return requestBody == null ? null : requestBody.getBytes("utf-8");
//                } catch (UnsupportedEncodingException uee) {
//                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
//                    return null;
//                }
//            }
//
//            @Override
//            protected Response<String> parseNetworkResponse(NetworkResponse response) {
//                String responseString = "";
//                if (response != null) {
//                    responseString = String.valueOf(response.statusCode);
//                    // can get more details such as response.headers
//                }
//                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
//            }
//        };
//        requestQueue.add(stringRequest);
//        }catch (Exception e)
//        {
//            Log.d("Exception Login : ", e.toString());
//        }
//    }
//
//    private void showJSON(String json){
//        JSONArray user = null;
//        try {
//            JSONObject jObject = new JSONObject(json);
//            user = jObject.getJSONArray("result");
//
//            for(int i=0; i<user.length(); i++){
//                JSONObject c = user.getJSONObject(i);
//                String id = c.getString("id");
//                String username = c.getString("username");
//                String password = c.getString("password");
//                String token = c.getString("token");
//
//                HashMap<String,String> users = new HashMap<String,String>();
//
//                users.put("id",id);
//                users.put("username",username);
//                users.put("password",password);
//                users.put("token",token);
//            }
//        }
//        catch (JSONException e)
//        {
//            e.printStackTrace();
//        }
//    }

    public boolean isOnline()
    {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
