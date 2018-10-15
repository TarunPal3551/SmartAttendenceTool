package com.example.android.testin3;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by shubhashish on 20-01-2018.
 */


//if we want our objects to be passed through activities we need to implent parcelable to the custom class from which objects are made
public class Values implements Parcelable {
    // basics
    private int no_of_periods_of_this_class;
    private int no_of_working_days_of_thid_class;

    // main constructor
    public Values(int periods, int days) {
        this.no_of_periods_of_this_class = periods;
        this.no_of_working_days_of_thid_class = days;
    }

    // getters
    public int getPeriods() { return no_of_periods_of_this_class; }
    public int getdays() { return no_of_working_days_of_thid_class; }


    //all the code below here is supposed to be kept like this always for paarcelable implements
    // write object values to parcel for storage
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(no_of_periods_of_this_class);
        dest.writeInt(no_of_working_days_of_thid_class);
    }

    public Values(Parcel parcel) {
        no_of_periods_of_this_class = parcel.readInt();
        no_of_working_days_of_thid_class = parcel.readInt();
    }

    public static final Parcelable.Creator<Values> CREATOR = new Parcelable.Creator<Values>() {

        @Override
        public Values createFromParcel(Parcel parcel) {
            return new Values(parcel);
        }

        @Override
        public Values[] newArray(int size) {
            return new Values[0];
        }
    };

    public int describeContents() {
        return hashCode();
    }
}

