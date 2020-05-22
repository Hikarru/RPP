package com.example.lab5;

import com.google.gson.annotations.SerializedName;

public class ItemCreate {

    @SerializedName("image_id")
    private String image_id;

    @SerializedName("sub_id")
    private String id;

    @SerializedName("value")
    private int value;

    public ItemCreate(String id, String image_id) {
        this.id = id;
        this.image_id = image_id;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String getImageId() {
        return image_id;
    }
}