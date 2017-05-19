package com.techno.misskeen.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.techno.misskeen.R;
import com.techno.misskeen.UserObject;

public class ProfileActivity extends AppCompatActivity {

    private ImageView imageprofile;
    private TextView txtprofilename,txtprofilemail,txtprofiletelp,txtprofilealamat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        imageprofile = (ImageView)findViewById(R.id.imageprofile);
        txtprofilename = (TextView)findViewById(R.id.profilename);
        txtprofilemail = (TextView)findViewById(R.id.profilemail);
        txtprofiletelp=(TextView)findViewById(R.id.profiletelp);
        txtprofilealamat=(TextView)findViewById(R.id.profilealamat);
        getDataProfile();
    }
    private void getDataProfile() {
        UserObject userObject = new UserObject();
        userObject = getSavedObjectFromPreference(getApplicationContext(), "mPreference", "mObjectKey", UserObject.class);
        txtprofilename.setText(userObject.getFullname());
        txtprofilemail.setText(userObject.getEmail());
        txtprofiletelp.setText(userObject.getTelp());
        txtprofilealamat.setText(userObject.getAlamat());
    }

    public static <GenericClass> GenericClass getSavedObjectFromPreference(Context context, String preferenceFileName, String preferenceKey, Class<GenericClass> classType) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preferenceFileName, 0);
        if (sharedPreferences.contains(preferenceKey)) {
            final Gson gson = new Gson();
            return gson.fromJson(sharedPreferences.getString(preferenceKey, ""), classType);
        }
        return null;
    }
}
