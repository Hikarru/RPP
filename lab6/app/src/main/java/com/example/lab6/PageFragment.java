package com.example.lab6;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import java.util.ArrayList;

public class PageFragment extends Fragment {
    private static String name1 = "NAME";
    private static String price1 = "PRICE";
    private static String amount1 = "COUNT";
    private String name;
    private String price;
    private int count;

    public PageFragment(){}
    public static PageFragment newInstance(int i, ArrayList<Product> products){
        PageFragment fragment = new PageFragment();
        Bundle b = new Bundle();
        b.putString(name1, products.get(i).getName());
        b.putString(price1, products.get(i).getPrice());
        b.putInt(amount1, products.get(i).getCount());
        fragment.setArguments(b);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedIns){
        super.onCreate(savedIns);
        name = getArguments().getString(name1);
        price = getArguments().getString(price1);
        count = getArguments().getInt(amount1);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, container, false);
        TextView nametxt = view.findViewById(R.id.nameText);
        nametxt.setText(name);
        TextView pricetxt = view.findViewById(R.id.priceText);
        pricetxt.setText("Цена: " + price);
        TextView counttxt = view.findViewById(R.id.countText);
        counttxt.setText("Кол-во: " + count);
        return view;
    }
}
