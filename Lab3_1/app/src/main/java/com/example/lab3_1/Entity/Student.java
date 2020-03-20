package com.example.lab3_1.Entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Student implements Parcelable{
    private int id;
    private String FIO;

    public Student(Parcel parcel){
        id = parcel.readInt();
        FIO = parcel.readString();
    }

    public Student(int id, String FIO){
        this.id = id;
        this.FIO = FIO;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFIO() {
        return FIO;
    }

    public void setFIO(String FIO) {
        this.FIO = FIO;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(FIO);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    @Override
    public String toString() {
        return "\nID = " + id + " | FIO = " + FIO;
    }
}
