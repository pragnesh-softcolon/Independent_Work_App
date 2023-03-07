
package com.example.independentworkapp.Models.JoinedEvents;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetJoinedEvents {

    @SerializedName("userDetails")
    @Expose
    private UserDetails userDetails;

    public UserDetails getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

}
