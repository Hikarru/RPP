package com.example.rpplab;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

// Этот код служит как дополнение адаптера(здесь сохраняется текст к элементу)

public class CustomHolder extends RecyclerView.ViewHolder {
    private TextView ItemText;

    public CustomHolder(View itemView) {
        super(itemView);
        ItemText = (TextView) itemView.findViewById(R.id.textView);
    }

    public void setItemText(String text) {
        ItemText.setText(text);
    }
}