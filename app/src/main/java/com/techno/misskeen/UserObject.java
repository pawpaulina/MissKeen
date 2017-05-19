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
    @SerializedName("alamat")
    private String alamat;

    public UserObject(String email, String password) {
        this.setEmail(email);
        this.password = password;
    }
    public UserObject(String email, String password,String fullname,String telp,String alamat)
    {
        this.setEmail(email);
        this.password = password;
        this.setFullname(fullname);
        this.setTelp(telp);
        this.setAlamat(alamat);
    }
    public UserObject()
    {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getTelp() {
        return telp;
    }

    public void setTelp(String telp) {
        this.telp = telp;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }
}
