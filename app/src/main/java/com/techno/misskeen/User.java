package com.techno.misskeen;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Paulina on 5/9/2017.
 */
public class User {
    @SerializedName("status")
    private String status;
    @SerializedName("info")
    private String info;
    @SerializedName("user")
    private UserObject user;

    public UserObject getUser() {
        return user;
    }

    public String getInfo() {
        return info;
    }

    public String getStatus() {
        return status;
    }

    //    public void setToken(String token)
//    {
//        this.token=token;
//    }
//
//    public String getToken()
//    {
//        return token;
//    }
//
//    public void setError(String error)
//    {
//        this.error = error;
//    }
//
//    public String getError()
//    {
//        return error;
//    }
}

