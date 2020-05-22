package com.example.lab5;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LikeFragment extends Fragment {

    private LikesAdapter likesAdapter;
    private RecyclerView recyclerView;
    private ArrayList<ItemGet> posts;
    private ArrayList<Photos> photos;
    private GridLayoutManager layoutManager;
    private Retrofit retrofit;
    private API api;

    public LikeFragment() {
        posts = new ArrayList<>();
        photos = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.like_fregment, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.lastLike);
        recyclerView.setVisibility(View.VISIBLE);
        retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(API.class);
        api.getVotes(MainActivity.USER_ID).enqueue(new retrofit2.Callback<List<ItemGet>>() {
            @Override
            public void onResponse(retrofit2.Call<List<ItemGet>> call, retrofit2.Response<List<ItemGet>> response) {
                if (response.isSuccessful()) {

                    photos.clear();
                    posts.clear();
                    posts = new ArrayList<ItemGet> (response.body());
                    getPhotos();
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(retrofit2.Call<List<ItemGet>> call, Throwable t) {
                t.printStackTrace();
            }
        });

        return view;
    }

    public void getPhotos(){
        for (int i = posts.size() - 1, j = 0; i >= 0 && j < 10; i--) {
            if (posts.get(i).getValue() == 1) {
                j++;
                api.getVotesLike(posts.get(i).getImageId()).enqueue(new retrofit2.Callback<Photos>() {
                    @Override
                    public void onResponse(retrofit2.Call<Photos> call, retrofit2.Response<Photos> response) {
                        if (response.isSuccessful()) {
                            photos.add(response.body());
                            createRecyclerView();
                        }
                    }
                    @Override
                    public void onFailure(retrofit2.Call<Photos> call, Throwable t) {
                        t.printStackTrace();
                    }
                });
            }
        }
    }

    private void createRecyclerView(){
        if(photos.size() != 0){
            layoutManager = new GridLayoutManager(getActivity(), 1);
            recyclerView.setLayoutManager(layoutManager);
            likesAdapter = new LikesAdapter(getActivity(), photos);
            recyclerView.setAdapter(likesAdapter);
        }
    }
}
