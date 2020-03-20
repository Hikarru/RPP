package com.example.lab3_2.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.lab3_2.Entity.Student;
import com.example.lab3_2.Entity.StudentOld;

import java.util.ArrayList;
import java.util.Date;

public class DBHelper extends SQLiteOpenHelper {

    public static int newVersion = 1;

    public DBHelper(Context context){
        super(context, "StudentsDB", null, newVersion);
    }

    public DBHelper(Context context, int newVersion){
        super(context, "StudentsDB", null, newVersion);
        this.newVersion = newVersion;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (newVersion == 1) {
            db.execSQL("CREATE TABLE IF NOT EXISTS Students" +
                    "(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    "FIO TEXT," +
                    "Time REAL  NOT NULL)"
            );
        }
        else
            db.execSQL("CREATE TABLE IF NOT EXISTS Students" +
                    "(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    "Surname TEXT," +
                    "Name TEXT," +
                    "Patronymic TEXT," +
                    "Time REAL NOT NULL)"
            );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == 1 && newVersion > oldVersion){
            oldVersion++;
            db.execSQL("CREATE TABLE IF NOT EXISTS Students2" +
                    "(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    "Surname TEXT," +
                    "Name TEXT," +
                    "Patronymic TEXT," +
                    "Time REAL NOT NULL)"
            );
            Cursor c = db.rawQuery("SELECT id, " +
                    "SUBSTR(FIO, 1, INSTR(FIO, ' ') - 1), " +
                    "SUBSTR(SUBSTR(FIO, INSTR(FIO, ' ')+1), 1, INSTR(SUBSTR(FIO, INSTR(FIO, ' ')+1), ' ') - 1), " +
                    "SUBSTR(SUBSTR(FIO, INSTR(FIO, ' ')+1), INSTR(SUBSTR(FIO, INSTR(FIO, ' ')+1), ' ')+1), " +
                    "Time FROM Students", null);
            ContentValues cv = new ContentValues();
            if (c.moveToFirst()){
                do {
                    cv.clear();
                    cv.put("id", c.getInt(0));
                    cv.put("Surname", c.getString(1));
                    cv.put("Name", c.getString(2));
                    cv.put("Patronymic", c.getString(3));
                    cv.put("Time", c.getLong(4));
                    db.insert("Students2", null, cv);
                }while (c.moveToNext());
            }
            db.execSQL("DROP TABLE IF EXISTS Students");
            db.execSQL("ALTER TABLE Students2 RENAME TO Students");
            c.close();
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion == 2 && newVersion == 1){
            oldVersion++;
            db.execSQL("CREATE TABLE IF NOT EXISTS Students2" +
                    "(id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                    "FIO TEXT," +
                    "Time REAL NOT NULL)"
            );
            Cursor c = db.rawQuery("SELECT id, Surname, Name, Patronymic, Time FROM Students", null);
            ContentValues cv = new ContentValues();
            if (c.moveToFirst()){
                do {
                    cv.clear();
                    cv.put("id", c.getInt(0));
                    String FIO = c.getString(1) + " " + c.getString(2)  + " " +  c.getString(3);
                    cv.put("FIO", FIO);
                    cv.put("Time", c.getLong(4));
                    db.insert("Students2", null, cv);
                }while (c.moveToNext());
            }
            db.execSQL("DROP TABLE IF EXISTS Students");
            db.execSQL("ALTER TABLE Students2 RENAME TO Students");
            c.close();
        }
    }

    public void changeStudent(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("SELECT MAX(id) FROM Students", null);
        c.moveToFirst();
        int id = c.getInt(0);
        String surname = "Иванов", name = "Иван", patronymic = "Иванович";
        db.execSQL("UPDATE Students SET Surname = '" + surname + "', Name = '" + name + "', Patronymic = '" + patronymic + "'" + " WHERE id = " + id);
        db.close();
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
        cv.put("id", id + 1);
        cv.put("Surname", arraySurname[randSurname]);
        cv.put("Name", arrayName[randName]);
        cv.put("Patronymic", arrayPatronymic[randPatronymic]);
        cv.put("Time", date.getTime());
        db.insert("Students", null, cv);
        c.close();
        db.close();
    }

    public ArrayList<Student> readAll(){
        ArrayList<Student> list = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("SELECT id, Surname, Name, Patronymic FROM Students ORDER BY Time", null);
        if (c.moveToFirst()){
            do{
                list.add(new Student(c.getInt(0), c.getString(1), c.getString(2), c.getString(3)));
            }while(c.moveToNext());
        }
        c.close();
        db.close();
        return list;
    }

    public void changeStudentOld(){
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("SELECT MAX(id) FROM Students", null);
        c.moveToFirst();
        int id = c.getInt(0);
        String t = "Иванов Иван Иванович";
        db.execSQL("UPDATE Students SET FIO = '" + t + "' WHERE id = " + id);
    }

    public void addStudentOld(){
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

    public void addFiveStudentsOld(){
        SQLiteDatabase db = getWritableDatabase();
        //db.execSQL("DELETE FROM Students");
        Cursor c = db.rawQuery("SELECT COUNT(0) FROM Students", null);
        c.moveToFirst();
        if (c.getInt(0) ==0)
        for (int i = 0; i < 5; i++)
            addStudentOld();
        db.close();
    }

    public ArrayList<StudentOld> readAllOld(){
        ArrayList<StudentOld> list = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM Students ORDER BY Time", null);
        if (c.moveToFirst()){
            do{
                list.add(new StudentOld(c.getInt(0), c.getString(1)));
                Log.i("КОНТАКТЫ = ", list.toString());
            }while(c.moveToNext());
        }
        c.close();
        db.close();
        return list;
    }

    public int getNewVersion() {
        return newVersion;
    }

    public void setNewVersion(int newVersion) {
        this.newVersion = newVersion;
    }
}
