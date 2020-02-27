package com.example.rinat.lab2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class PageFragment extends Fragment {
    String graphic;
    String helptext;

    public PageFragment() {}

    @SuppressLint("ValidFragment")
    public PageFragment(String graphic, String helptext) {
        Bundle arguments = new Bundle();
        this.graphic = graphic;
        this.helptext = helptext;
        arguments.putString("helptext", helptext);
        arguments.putString("graphic", graphic);
        this.setArguments(arguments);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment, container, false);

        ImageView imageGraphic = view.findViewById(R.id.graphic);

        if(getArguments().getString("graphic") != null)
            graphic = getArguments().getString("graphic");
        if(getArguments().getString("helptext") != null)
            helptext = getArguments().getString("helptext");

        if(graphic != null)
            Glide.with(this).
                    load("https://raw.githubusercontent.com/wesleywerner/ancient-tech/02decf875616dd9692b31658d92e64a20d99f816/src/images/tech/" + graphic).
                    into(imageGraphic);

        TextView tvPage = (TextView) view.findViewById(R.id.tvPage);
        if(helptext != null)
            tvPage.setText(helptext);
        else
            tvPage.setText("No description found");

        return view;
    }
}