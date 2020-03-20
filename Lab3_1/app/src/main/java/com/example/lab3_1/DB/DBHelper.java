package com.example.lab3_1.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.lab3_1.Entity.Student;

import java.util.ArrayList;
import java.util.Date;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context){
        super(context, "StudentsDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS Students" +
                "(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                "FIO TEXT NOT NULL," +
                "Time REAL NOT NULL)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void changeStudent(){
        SQLiteDatabase db = getWritableDatabase();
        Date date = new Date();
        Cursor c = db.rawQuery("SELECT MAX(id) FROM Students", null);
        c.moveToFirst();
        int id = c.getInt(0);
        String t = "Иванов Иван Иванович";
        long time = date.getTime();
        db.execSQL("UPDATE Students SET FIO = '" + t + "', Time = '" + time + "' WHERE id = " + id);
    }

    public void addStudent(){
        String arraySurname[] = {
                "Имасс", "Хабибулин", "Судариков", "Шачнов", "Леонов", "Башкин",
                "Филин", "Родников", "Трофимов"
        };
        String arrayName[] = {
                "Дмитрий", "Ринат", "Кирилл", "Никита", "Антон", "Антон",
                "Александр", "Илья", "Никита"
        };
        String arrayPatronymic [] = {
                "Никитович", "Анатольевич", "Игоревич", "Александрович", "Дмитриевич",
                "Алексеевич", "Артёмович", "Сергеевич", "Иванович"
        };

        SQLiteDatabase db = getWritableDatabase();
        Date date = new Date();
        Cursor c = db.rawQuery("SELECT MAX(id) FROM Students", null);
        c.moveToFirst();
        int id = c.getInt(0);
        ContentValues cv = new ContentValues();
        int randSurname = (int)(Math.random() * arraySurname.length), randName = (int)(Math.random() * arrayName.length), randPatronymic = (int)(Math.random() * arrayPatronymic.length);
        String FIO = arraySurname[randSurname] + " " + arrayName[randName] + " " + arrayPatronymic[randPatronymic];
        cv.put("id", id + 1);
        cv.put("FIO", FIO);
        cv.put("Time", date.getTime());
        db.insert("Students", null, cv);
        c.close();
        db.close();
    }

    public void addFiveStudents(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("SELECT COUNT(0) FROM Students", null);
        //db.execSQL("DELETE FROM Students");
        c.moveToFirst();
        if (c.getInt(0) ==0)
            for (int i = 0; i < 5; i++)
                addStudent();
        db.close();
    }

    public ArrayList<Student> readAll(){
        ArrayList<Student> list = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Students ORDER BY Time", null);
        if (c.moveToFirst()){
            do{
                Date t = new Date();
                t.setTime((long) c.getDouble(2));
                //list.add(new Student(c.getInt(0), c.getInt(0) + " " + c.getString(1) + " " + t.toLocaleString()  ));
                list.add(new Student(c.getInt(0),  c.getString(1)  ));
            }while(c.moveToNext());
        }
        c.close();
        db.close();
        return list;
    }
}
