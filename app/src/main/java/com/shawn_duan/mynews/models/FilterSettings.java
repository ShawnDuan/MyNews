package com.shawn_duan.mynews.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sduan on 10/22/16.
 */

public class FilterSettings implements Parcelable {

    String beginDate;
    String sortOrder;

    List<String> newsDeskValues;

    public FilterSettings() {

    }

    protected FilterSettings(Parcel in) {
        beginDate = in.readString();
        sortOrder = in.readString();
        if (in.readByte() == 0x01) {
            newsDeskValues = new ArrayList<String>();
            in.readList(newsDeskValues, String.class.getClassLoader());
        } else {
            newsDeskValues = null;
        }
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public List<String> getNewsDeskValues() {
        return newsDeskValues;
    }

    public void setNewsDeskValues(List<String> newsDeskValues) {
        this.newsDeskValues = newsDeskValues;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(beginDate);
        dest.writeString(sortOrder);
        if (newsDeskValues == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(newsDeskValues);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<FilterSettings> CREATOR = new Parcelable.Creator<FilterSettings>() {
        @Override
        public FilterSettings createFromParcel(Parcel in) {
            return new FilterSettings(in);
        }

        @Override
        public FilterSettings[] newArray(int size) {
            return new FilterSettings[size];
        }
    };
}