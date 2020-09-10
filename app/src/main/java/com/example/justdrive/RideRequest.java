package com.example.justdrive;

public class RideRequest {

    public String requestId;
    public double latt;
    public double longg;
    public String email;

    public RideRequest(){

    }

    public RideRequest(String requestId, double latt, double longg, String email) {
        this.requestId = requestId;
        this.latt = latt;
        this.longg = longg;
        this.email = email;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public double getLatt() {
        return latt;
    }

    public void setLatt(double latt) {
        this.latt = latt;
    }

    public double getLongg() {
        return longg;
    }

    public void setLongg(double longg) {
        this.longg = longg;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
