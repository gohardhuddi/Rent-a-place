package com.example.Rentup;

public class AvailableplacesHelper {
    public String mImageUrl,mColony,mRent;

    public AvailableplacesHelper() {
        //empty
    }

    public AvailableplacesHelper(String mImageUrl, String mColony, String mRent) {
        this.mImageUrl = mImageUrl;
        this.mColony = mColony;
        this.mRent = mRent;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public void setImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getmColony() {
        return mColony;
    }

    public void setmColony(String mColony) {
        this.mColony = mColony;
    }

    public String getmRent() {
        return mRent;
    }

    public void setmRent(String mRent) {
        this.mRent = mRent;
    }
}
