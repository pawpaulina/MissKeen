package com.techno.misskeen;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Paulina on 5/9/2017.
 */
public class Response {
    @SerializedName("status")
    private String status;

    public String getStatus() {
        return status;
    }
}
