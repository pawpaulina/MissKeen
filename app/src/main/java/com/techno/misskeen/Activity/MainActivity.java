package com.techno.misskeen.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.techno.misskeen.Helper.PrefHelper;
import com.techno.misskeen.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String mFlag = PrefHelper.getPref(getApplicationContext(),"ID");
        if(mFlag == null)
        {
            new Handler().postDelayed(new Runnable() {
                  @Override
                  public void run() {
                      Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                      startActivity(i);
                  }
            }, 5000);
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    PrefHelper.saveToPref(getApplicationContext(),"ID","");
                    Intent i = new Intent(getApplicationContext(), RecipeListActivity.class);
                    startActivity(i);
                }
            }, 5000);
        }
        setContentView(R.layout.activity_splash_screen);
    }
}
