package com.example.lab6;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import androidx.appcompat.app.AppCompatActivity;

public class BackendActivity extends AppCompatActivity {

    private DataBaseHelper databaseHelper;
    private SQLiteDatabase db;
    private Cursor cursor;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backend);

        databaseHelper = new DataBaseHelper(getApplicationContext());

        listView = findViewById(R.id.list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                intent.putExtra("ID", id);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        db = databaseHelper.getReadableDatabase();
        cursor = db.rawQuery("select * from " + DataBaseHelper.TABLE_NAME, null);

        String[] headers = new String[] {DataBaseHelper.COLUMN_NAME, DataBaseHelper.COLUMN_PRICE, DataBaseHelper.COLUMN_COUNT};
        SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.list_item, cursor,
                headers, new int[] {R.id.name, R.id.price, R.id.count}, 0);
        listView.setAdapter(adapter);
    }

    public void onAddItemClick(View view) {
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        db.close();
        cursor.close();
    }
}
