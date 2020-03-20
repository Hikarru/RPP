package com.example.lab3_1;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class StudentViewHolder extends RecyclerView.ViewHolder {

    public TextView FIO;

    public StudentViewHolder(@NonNull View itemView) {
        super(itemView);
        FIO = itemView.findViewById(R.id.FIO);
    }
}
