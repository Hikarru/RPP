package com.example.rpplab;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.lang.Thread;

public class MainActivity extends AppCompatActivity {

    static final String IsResumed = "is resumed";
    static final String IsOnBackPressed = "is on back pressed";

    private boolean isResumed = false;
    private volatile boolean isOnBackPressed = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) { // Этот метод вызывается при запуске активити
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) // Этот метод - когда система завершает активность, сохраняя ресурсы
    {
        savedInstanceState.putBoolean(IsResumed, isResumed);
        savedInstanceState.putBoolean(IsOnBackPressed, isOnBackPressed);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onResume() { // Этот метод работает как бесконечный жизненный цикл приложения
        super.onResume();
        if (!isResumed)
        {
            Thread thread = new Thread(new Runnable() { // Создаем поток в приложении
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000); // Засыпаем на 2 секунды

                        if (!isOnBackPressed) // Если не была нажата клавиша "Назад"
                        {
                            Intent intent = new Intent(MainActivity.this, secondActivity.class); //Переключаемся с мейн активити на секонд
                            startActivity(intent); // Запускаем секонд активити
                            finish(); // Убиваем мейн активити
                        }
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
            isResumed = true;
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) // восстановление сохраненных данных
    {
        super.onRestoreInstanceState(savedInstanceState);
        isResumed = savedInstanceState.getBoolean(IsResumed);
        isOnBackPressed = savedInstanceState.getBoolean(IsOnBackPressed);
    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        isOnBackPressed = true;
    }
}
