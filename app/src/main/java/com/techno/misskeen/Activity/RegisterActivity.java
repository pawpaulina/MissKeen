package com.techno.misskeen.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.techno.misskeen.Client;
import com.techno.misskeen.Helper.PrefHelper;
import com.techno.misskeen.R;
import com.techno.misskeen.Rest;
import com.techno.misskeen.User;
import com.techno.misskeen.UserObject;

import retrofit2.Call;
import retrofit2.Callback;

public class RegisterActivity extends AppCompatActivity {
    private EditText editTextNama;
    private EditText editTextEmail;
    private EditText editTextTelp;
    private EditText editTextAlamat;
    private EditText editTextPass;
    private Button btnRegis;
    private Button btnBatal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editTextNama = (EditText)findViewById(R.id.editTextNama);
        editTextEmail = (EditText)findViewById(R.id.editTextEmail);
        editTextTelp = (EditText)findViewById(R.id.editTextTelp);
        editTextAlamat = (EditText)findViewById(R.id.editTextAlamat);
        editTextPass = (EditText)findViewById(R.id.editTextPass);

        btnRegis = (Button)findViewById(R.id.btnRegis);
        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonRegis(v);
            }
        });
        btnBatal = (Button)findViewById(R.id.btnBatal);
    }
    public void buttonRegis(View v)
    {
        UserObject user = new UserObject(editTextEmail.getText().toString(),editTextPass.getText().toString(),editTextNama.getText().toString(),editTextTelp.getText().toString(),editTextAlamat.getText().toString());
        Rest rest = Client.getClient().create(Rest.class);
        Call<User> call = rest.getRegis(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, retrofit2.Response<User> response) {
                if(response.body().getStatus().equals("true"))
                {
                    PrefHelper.saveToPref(getApplicationContext(), "email", "password");
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(RegisterActivity.this);
                    alertDialogBuilder.setMessage(response.body().getInfo());
                    alertDialogBuilder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(i);
                        }
                    });
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();

                }
                else
                if(response.body().getStatus().equals("false"))
                {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(RegisterActivity.this);
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
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(RegisterActivity.this);
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
    public void buttonBatal(View v)
    {
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i);
    }
    public boolean isOnline()
    {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
