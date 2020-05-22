package com.example.lab5;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Photos implements Parcelable {
    public static Double imagesCount = 1.0;
    public static Double limit = 0.0;
    private static int pageCount = 1;
    public static String breeds_id = "";
    private int isLike = -1;
    private int iid ;

    @SerializedName("url")
    private String url;

    public Photos(String imageUrl, int isLike) {
        this.url = imageUrl;
        this.isLike = isLike;
    }

    public String getImageUrl() {
        return url;
    }
    public static int getPageCount() {
        pageCount = (int)Math.ceil(imagesCount / limit);
        return pageCount;
    }

    public String getImageId() {
        int pos1 = url.lastIndexOf("/");
        int pos2 = url.lastIndexOf(".");
        String s = url.substring(pos1 + 1, pos2);
        return s;
    }

    public int isLike() {
        return isLike;
    }

    public void setLike(int like) {
        isLike = like;
    }

    public int getId() {
        return iid;
    }

    public void setId(int id) {
        this.iid = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeInt(iid);
    }

    public static final Creator<Photos> CREATOR = new Creator<Photos>() {
        @Override
        public Photos createFromParcel(Parcel source) {
            String u = source.readString();
            int i = source.readInt();
            return new Photos(u, i);
        }

        @Override
        public Photos[] newArray(int size) {
            return new Photos[size];
        }
    };
}
