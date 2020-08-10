package com.example.android.quakereport;

public class Earthquake {
    private double eMagnitude;
    private String eLocation;
    private Long eTimeInMilliseconds;
    private String mUrl;

    public Earthquake(double magnitude, String location, Long timeInMilliseconds, String url) {
        eMagnitude = magnitude;
        eLocation = location;
        eTimeInMilliseconds = timeInMilliseconds;
        mUrl = url;

    }

    public double getMagnitude() {
        return eMagnitude;

    }

    public String getLocation() {
        return eLocation;

    }

    public Long getTimeInMilliseconds() {
        return eTimeInMilliseconds;

    }

    /**
     * Returns the website URL to find more information about the earthquake.
     */
    public String getUrl() {
        return mUrl;
    }

}
