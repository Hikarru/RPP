package com.example.lab6;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UserActivity extends AppCompatActivity {

    private DataBaseHelper databaseHelper;
    private SQLiteDatabase db;

    private EditText nameBox;
    private EditText priceBox;
    private EditText countBox;

    private long id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        nameBox = findViewById(R.id.name);
        priceBox = findViewById(R.id.price);
        countBox = findViewById(R.id.count);

        databaseHelper = new DataBaseHelper(this);
        db = databaseHelper.getReadableDatabase();

        setDataItem();
    }
    private void setDataItem(){
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            id = extras.getLong("ID");
        }
        if(id > 0){
            Cursor cursor = db.rawQuery("SELECT * FROM " + DataBaseHelper.TABLE_NAME + " WHERE " +
                    DataBaseHelper.COLUMN_ID + "=?", new String[]{String.valueOf(id)});
            cursor.moveToFirst();
            nameBox.setText(cursor.getString(1));
            priceBox.setText(cursor.getString(2));
            countBox.setText(cursor.getString(3));
            cursor.close();
        }
        else{
            Button delete = findViewById(R.id.deleteButton);
            delete.setVisibility(View.GONE);
        }
    }
    public void onSaveClick(View view){
        String name = nameBox.getText().toString();
        String price = priceBox.getText().toString();
        String count = countBox.getText().toString();
        if(name.length() != 0 && price.length() != 0 && count.length() != 0){
            databaseHelper.update(id, name, price, count);
            goBack();
        }
        else{
            Toast.makeText(getApplicationContext(), "Проверьте введенные данные!", Toast.LENGTH_SHORT).show();
        }
    }
    public void goBack(){
        db.close();
        Intent intent = new Intent(this, BackendActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }
    public void onDeleteClick(View view){
        databaseHelper.delete(id);
        goBack();
    }
}
