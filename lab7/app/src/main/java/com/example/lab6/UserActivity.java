package com.example.lab6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lab6.BackendActivity;
import com.example.lab6.DataBaseHelper;
import com.example.lab6.MainActivity;
import com.example.lab6.R;

public class UserActivity extends AppCompatActivity {

    private DataBaseHelper databaseHelper;

    private EditText nameBox;
    private EditText priceBox;
    private EditText countBox;

    private Intent intent;

    private long id = 0;
    private int pos = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        nameBox = findViewById(R.id.name);
        priceBox = findViewById(R.id.price);
        countBox = findViewById(R.id.count);

        databaseHelper = new DataBaseHelper(this);
        intent = new Intent(this, BackendActivity.class);

        setDataItem();
    }

    private void setDataItem() {
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            id = extras.getLong("ID");
            pos = extras.getInt("POSITION");
        }

        if (id > 0) {
            nameBox.setText(databaseHelper.getDataByID(id).get(0));
            priceBox.setText(databaseHelper.getDataByID(id).get(1));
            countBox.setText(databaseHelper.getDataByID(id).get(2));
        } else {
            Button delButton = findViewById(R.id.deleteButton);
            delButton.setVisibility(View.GONE);
        }
    }

    public void onSaveClick(View view) {
        String name = nameBox.getText().toString();
        String price = priceBox.getText().toString();
        String count = countBox.getText().toString();

        if (name.length() != 0 && price.length() != 0 && count.length() != 0) {
                goBack(name, price, count);
            }
        else {
            Toast.makeText(getApplicationContext(),
                    "Проверьте введенные данные", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public void onDeleteClick(View view) {

        databaseHelper.delete(id);
        intent.putExtra("TRUE", true);
        goBack(nameBox.getText().toString(), priceBox.getText().toString(),
                countBox.getText().toString());
    }

    @Override
    public void onBackPressed() {
        intent.putExtra("POSITION", pos);
        setResult(RESULT_CANCELED, intent);
        finish();
    }

    private void goBack(String name, String price, String count) {
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        intent.putExtra("ID", id);
        intent.putExtra("POSITION", pos);
        intent.putExtra("NAME", name);
        intent.putExtra("PRICE", price);
        intent.putExtra("COUNT", count);

        setResult(RESULT_OK, intent);
        finish();
    }
}
