package com.techno.misskeen;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Paulina on 5/9/2017.
 */
public class UserObject
{
    @SerializedName("email")
    private String email;
    @SerializedName("password")
    private String password;
    @SerializedName("id")
    private String id;
    @SerializedName("fullname")
    private String fullname;
    @SerializedName("telp")
    private String telp;

    public UserObject(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
