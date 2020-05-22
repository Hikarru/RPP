package com.example.lab5;

import com.google.gson.annotations.SerializedName;

public class Votes {
    @SerializedName("id")
    private int id;

    public Votes(int vote_id) {
        this.id = vote_id;
    }

    public int getVote_id() {
        return id;
    }
}
