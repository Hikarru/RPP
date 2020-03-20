package com.example.lab3_2;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class StudentViewHolder extends RecyclerView.ViewHolder {

    public TextView surname, name, patronymic;

    public StudentViewHolder(@NonNull View itemView) {
        super(itemView);
        surname = itemView.findViewById(R.id.surname);
        name = itemView.findViewById(R.id.name);
        patronymic = itemView.findViewById(R.id.patronymic);
    }
}
