package com.example.rpplab;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;


public class secondActivity extends Activity {

    private RecyclerView recyclerView;
    private Adapter recycleViewCustomAdapter;
    private static final String RecAdapterPosition = "RecycleViewCustomAdapterPosition";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        recycleViewCustomAdapter = new Adapter(this, 1000000); // Создаем список на 1 миллион элементов
        recyclerView = new RecyclerView(this);

        recyclerView.setAdapter(recycleViewCustomAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setHasFixedSize(true);
        setContentView(recyclerView); //отображаем настроенный список элементов на экране
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putInt(RecAdapterPosition, ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstCompletelyVisibleItemPosition());
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        recyclerView.scrollToPosition(savedInstanceState.getInt(RecAdapterPosition));
    }
}