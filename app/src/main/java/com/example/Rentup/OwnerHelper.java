package com.example.Rentup;

import android.net.Uri;

public class OwnerHelper {
    public String mColony, mAddress, mDescription, mRent, mPhoneNumber,mPropertyty1,mPropertyty2,mPropertyty3;
    private String mImageUrl;

    public OwnerHelper() {
    }

    public OwnerHelper(String mColony, String mAddress, String mDescription, String mRent, String mPhoneNumber, String mPropertyty1, String mPropertyty2, String mPropertyty3, String mImageUrl) {
        this.mColony = mColony;
        this.mAddress = mAddress;
        this.mDescription = mDescription;
        this.mRent = mRent;
        this.mPhoneNumber = mPhoneNumber;
        this.mPropertyty1 = mPropertyty1;
        this.mPropertyty2 = mPropertyty2;
        this.mPropertyty3 = mPropertyty3;
        this.mImageUrl = mImageUrl;
    }
    public String getmColony() {
        return mColony;
    }

    public void setmColony(String mColony) {
        this.mColony = mColony;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmRent() {
        return mRent;
    }

    public void setmRent(String mRent) {
        this.mRent = mRent;
    }

    public String getmPhoneNumber() {
        return mPhoneNumber;
    }

    public void setmPhoneNumber(String mPhoneNumber) {
        this.mPhoneNumber = mPhoneNumber;
    }

    public String getmPropertyty1() {
        return mPropertyty1;
    }

    public void setmPropertyty1(String mPropertyty1) {
        this.mPropertyty1 = mPropertyty1;
    }

    public String getmPropertyty2() {
        return mPropertyty2;
    }

    public void setmPropertyty2(String mPropertyty2) {
        this.mPropertyty2 = mPropertyty2;
    }

    public String getmPropertyty3() {
        return mPropertyty3;
    }

    public void setmPropertyty3(String mPropertyty3) {
        this.mPropertyty3 = mPropertyty3;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }
}

