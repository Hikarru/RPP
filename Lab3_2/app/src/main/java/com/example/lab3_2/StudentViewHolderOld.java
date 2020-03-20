package com.example.lab3_2;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class StudentViewHolderOld extends RecyclerView.ViewHolder {

    public TextView FIO;

    public StudentViewHolderOld(@NonNull View itemView) {
        super(itemView);
        FIO = itemView.findViewById(R.id.FIO);
    }
}
