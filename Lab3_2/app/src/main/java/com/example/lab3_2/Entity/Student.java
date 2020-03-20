package com.example.lab3_2.Entity;

import android.os.Parcel;
import android.os.Parcelable;

public class Student implements Parcelable{
    private int id;
    private String surname, name, patronymic ;

    public Student(Parcel parcel){
        id = parcel.readInt();
        surname = parcel.readString();
        name = parcel.readString();
        patronymic = parcel.readString();
    }

    public Student(int id, String surname, String name, String patronymic){
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(surname);
        dest.writeString(name);
        dest.writeString(patronymic);
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
        return "id = " + id + " | " + surname + " " + name + " " + patronymic;
    }
}
