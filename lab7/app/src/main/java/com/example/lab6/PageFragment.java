package com.example.lab6;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.lab6.BackendActivity;
import com.example.lab6.DataBaseHelper;
import com.example.lab6.Product;
import com.example.lab6.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class PageFragment extends Fragment {

    private String name;
    private String price;
    private int count;
    private int pos;

    public static ArrayList<Product> products;
    private HashSet<String> setPos;

    private Button button;
    private ProgressBar progressBar;

    private SharedPreferences settings;
    private SharedPreferences.Editor prefEditor;

    public PageFragment() { }

    public interface OnFragmentDataListener {
        void onFragmentDataListener();
    }

    private OnFragmentDataListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentDataListener) {
            mListener = (OnFragmentDataListener) context;
        } else {
            throw new RuntimeException(context.toString());
        }
    }

    public static PageFragment newInstance(int i, ArrayList<Product> productList) {
        PageFragment fragment = new PageFragment();
        products = productList;

        Bundle args = new Bundle();
        args.putString("NAME", productList.get(i).getName());
        args.putString("PRICE", productList.get(i).getPrice());
        args.putInt("COUNT", productList.get(i).getCount());
        args.putInt("POSITION", i);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        name = getArguments().getString("NAME");
        price = getArguments().getString("PRICE");
        count = getArguments().getInt("COUNT");
        pos = getArguments().getInt("POSITION");
        settings = getActivity().getSharedPreferences("APP", Context.MODE_PRIVATE);
        setPos = (HashSet<String>) settings.getStringSet("SET", new HashSet<String>());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, container, false);
        TextView nameTextView = view.findViewById(R.id.nameText);
        nameTextView.setText(name);

        TextView priceTextView = view.findViewById(R.id.priceText);
        priceTextView.setText(String.format("Цена: "+ price));

        TextView countTextView = view.findViewById(R.id.countText);
        countTextView.setText(String.format("Количество: "+ count));

        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setVisibility(ProgressBar.INVISIBLE);

        button = view.findViewById(R.id.buyButton);

        if (setPos.contains(String.valueOf(pos))) {
            button.setClickable(false);
        } else {
            button.setClickable(true);
        }


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                prefEditor = settings.edit();
                setPos.add(String.valueOf(pos));
                prefEditor.putStringSet("SET", setPos)
                        .apply();

                new ProgressTask(getContext()).execute();
            }
        });


        return view;
    }

    private class ProgressTask extends AsyncTask<Void, Integer, Void> {
        private Context context;
        private int seconds = new Random().nextInt(2) + 3;
        private int start = 0;

        private ProgressTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            button.setClickable(false);
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
            DataBaseHelper databaseHelper = new DataBaseHelper(context);
            int newCount = databaseHelper.decrementCount(name, price, count);
            products.get(pos).setCount(newCount);

            if (setPos.contains(String.valueOf(pos))) {
                setPos.remove(String.valueOf(pos));
                prefEditor.remove("SET")
                        .putStringSet("SET", setPos)
                        .commit();
            }

            if (newCount == 0) {
                products.remove(pos);
            }

            mListener.onFragmentDataListener();
            Toast.makeText(context, "Вы купили  " + name , Toast.LENGTH_SHORT).show();

            if (BackendActivity.adapter != null) {
                BackendActivity.adapter.changeCursor(new DataBaseHelper(context).getAllLines());
            }

            button.setClickable(true);
            progressBar.setVisibility(ProgressBar.INVISIBLE);
        }
    }
}