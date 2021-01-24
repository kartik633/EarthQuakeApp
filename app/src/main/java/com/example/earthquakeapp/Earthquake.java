package com.example.earthquakeapp;

public class Earthquake {

    private double mMagnitude;
    private String place;
    private String date;
    private long mTimeInMilliseconds;
    private String url;

    public Earthquake(double magnit,String pla,long timeInMilliseconds,String ur)
    {
        mMagnitude = magnit;
        place = pla;
        mTimeInMilliseconds = timeInMilliseconds;
        url = ur;
    }

    public double getMagnitude()
    {
        return mMagnitude;
    }

    public String getPlace()
    {
        return place;
    }

    public long getTimeInMilliseconds()
    {
        return mTimeInMilliseconds;
    }
    public String getUrl()
    {
        return url;
    }
}