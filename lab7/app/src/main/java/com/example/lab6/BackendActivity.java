package com.example.lab6;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import com.example.lab6.DataBaseHelper;
import com.example.lab6.MainActivity;
import com.example.lab6.PageFragment;
import com.example.lab6.R;
import com.example.lab6.StorefrontActivity;
import com.example.lab6.UserActivity;
import java.util.HashSet;
import java.util.Random;

public class BackendActivity extends AppCompatActivity {

    private DataBaseHelper databaseHelper;
    private ProgressBar progressBar;

    public static SimpleCursorAdapter adapter;
    private HashSet<String> setPos;
    private long pos;

    private SharedPreferences settings;
    private SharedPreferences.Editor prefEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backend);

        databaseHelper = new DataBaseHelper(getApplicationContext());

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(ProgressBar.INVISIBLE);

        settings = getSharedPreferences("APP", Context.MODE_PRIVATE);
        setPos = (HashSet<String>) settings.getStringSet("SET", new HashSet<String>());

        ListView listView = findViewById(R.id.list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (!setPos.contains(String.valueOf(position))) {
                    prefEditor = settings.edit();
                    setPos.add(String.valueOf(position));
                    prefEditor.putStringSet("SET", setPos)
                            .apply();

                    Intent intent = new Intent(getApplicationContext(), UserActivity.class);
                    intent.putExtra("ID", id);
                    intent.putExtra("POSITION", position);
                    startActivityForResult(intent, 1);
                }
            }
        });

        String[] headers = new String[] {DataBaseHelper.COLUMN_NAME,
                DataBaseHelper.COLUMN_PRICE, DataBaseHelper.COLUMN_COUNT};
        adapter = new SimpleCursorAdapter(this, R.layout.list_item, databaseHelper.getAllLines(),
                headers, new int[] {R.id.name, R.id.price, R.id.count}, 0);

        listView.setAdapter(adapter);
    }

    public void onAddItemClick(View view) {
        Intent intent = new Intent(this, UserActivity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1) {
            pos = data.getIntExtra("POSITION", -1);

            if (resultCode == RESULT_OK) {
                boolean isDelete = data.getBooleanExtra("TRUE", false);

                if (pos != -1 && !isDelete) {
                    new ProgressTask(data).execute();
                } else if (isDelete) {
                    removePos();
                    adapter.changeCursor(databaseHelper.getAllLines());
                }
            }
            else {
                removePos();
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void removePos() {
        setPos.remove(String.valueOf(pos));
        prefEditor.remove("SET")
                .putStringSet("SET", setPos)
                .commit();
    }

    private class ProgressTask extends AsyncTask<Void, Integer, Void> {
        private int seconds = new Random().nextInt(2) + 3;
        private int start = 0;
        private long id;
        private String name;
        private String price;
        private String count;

        private ProgressTask(Intent data) {
            id = data.getLongExtra("ID", 0);
            name = data.getStringExtra("NAME");
            price = data.getStringExtra("PRICE");
            count = data.getStringExtra("COUNT");
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setMax(seconds);
            progressBar.setVisibility(ProgressBar.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            while (start != seconds) {
                publishProgress(start);
                SystemClock.sleep(1000);
                start++;
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            progressBar.setProgress(values[0] + 1);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (setPos.contains(String.valueOf(pos))) {
                removePos();
            }
            databaseHelper.update(id, name, price, count);
            adapter.changeCursor(databaseHelper.getAllLines());

            if (StorefrontActivity.adapter != null) {
                PageFragment.products.get((int) pos).setName(name);
                PageFragment.products.get((int) pos).setPrice(price);
                PageFragment.products.get((int) pos).setCount(Integer.parseInt(count));

                if (Integer.parseInt(count) <= 0) {
                    PageFragment.products.remove((int) pos);
                }
                StorefrontActivity.adapter.notifyDataSetChanged();
            }

            if (!setPos.isEmpty()) {
                setPos.clear();
                prefEditor.remove("SET")
                        .putStringSet("SET", setPos)
                        .commit();
            }

            progressBar.setVisibility(ProgressBar.INVISIBLE);
        }
    }
}