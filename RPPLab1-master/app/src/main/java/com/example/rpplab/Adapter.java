package com.example.rpplab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

// Адаптер нужен чтобы правильно реализовать список для конкретно нашей задачи

public class Adapter extends RecyclerView.Adapter<CustomHolder> {
    private LayoutInflater LInflater;
    public int Count;

    public Adapter(Context context, int count) {
        LInflater = LayoutInflater.from(context);
        Count = count;
    }

    @Override
    public CustomHolder onCreateViewHolder(ViewGroup parent, int viewType) { // возвращает объект ViewHolder, который будет хранить данные по одному объекту Phone.
        return new CustomHolder(LInflater.inflate(R.layout.resource, parent, false));
    }

    @Override
    public void onBindViewHolder(CustomHolder holder, int position) { // Метод для создания элемента списка // выполняет привязку объекта ViewHolder к объекту Phone по определенной позиции.
        if (position % 2 == 0) { // Если позиция элемента четная фон белый, иначе серый
            holder.itemView.setBackgroundColor(Color.WHITE);
        } else {
            holder.itemView.setBackgroundColor(Color.GRAY);
        }
        holder.setItemText(intoText.digitsToText(position + 1)); // Устанавливаем текст для элемента
    }

    @Override
    public int getItemCount() {
        return Count;
    } // возвращает количество объектов в списке
}