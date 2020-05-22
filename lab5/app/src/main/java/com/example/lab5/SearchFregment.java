package com.example.lab5;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.List;
import okhttp3.Headers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import static android.view.View.GONE;

public class SearchFregment extends Fragment {
    private Spinner spinner;
    private BreedsAdapter breedsAdapter;
    private String selectStringName;
    private RecyclerView recyclerView;
    private Retrofit retrofit;
    private ArrayList<Breeds> breeds;
    private ArrayList<Photos> photos;
    private GridLayoutManager layoutManager;
    private API api;
    private int pager_number = 0;
    private int item_count = 10;
    private boolean isLoading = true;
    private int pastVisibleItems, totalItemCount, visibleItemCount, previousTotal = 0;
    private int viewThreshold = 10;
    private ArrayAdapter<String> adapterArr;
    private Headers headers;

    public SearchFregment() {
        photos = new ArrayList<>();
        breeds = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fregment, container, false);
        spinner = (Spinner) view.findViewById(R.id.spinner);
        recyclerView = (RecyclerView) view.findViewById(R.id.rec);
        retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(API.class);
        workServiceListOfSpinner();
        return view;
    }

    public void createRecyclerView() {
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getActivity(), 1);
        recyclerView.setLayoutManager(layoutManager);
        Photos.limit = Double.valueOf(item_count);
        api.getPhotoForBreed(Photos.breeds_id, item_count, "desc", pager_number).enqueue(new retrofit2.Callback<List<Photos>>() {
            @Override
            public void onResponse(retrofit2.Call<List<Photos>> call, retrofit2.Response<List<Photos>> response) {
                if (response.isSuccessful()) {
                    Photos.imagesCount = Double.parseDouble(response.headers().get("pagination-count"));
                    photos.addAll(response.body());
                    searchLikes();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<List<Photos>> call, Throwable t) {
                t.printStackTrace();
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = layoutManager.getChildCount();
                totalItemCount = layoutManager.getItemCount();
                pastVisibleItems = layoutManager.findFirstVisibleItemPosition();
                if (dy > 0) {
                    if (isLoading) {
                        if (totalItemCount > previousTotal) {
                            isLoading = false;
                            previousTotal = totalItemCount;
                        }
                    }
                    if (!isLoading && (totalItemCount - visibleItemCount) <= (pastVisibleItems + viewThreshold)) {
                        pager_number++;
                        performPageination();
                        isLoading = true;
                    }
                }
            }
        });
    }

    private void searchLikes() {
        api.getVotes(MainActivity.USER_ID).enqueue(new retrofit2.Callback<List<ItemGet>>() {
            @Override
            public void onResponse(retrofit2.Call<List<ItemGet>> call, retrofit2.Response<List<ItemGet>> response) {
                if (response.isSuccessful()) {
                    List<ItemGet> arrayPostFavourites = response.body();
                    for (int i = 0; i < photos.size(); i++) {
                        for (int j = 0; j < arrayPostFavourites.size(); j++) {
                            if (photos.get(i).getImageId().equals(arrayPostFavourites.get(j).getImageId())) {
                                photos.get(i).setLike(arrayPostFavourites.get(j).getValue());
                                photos.get(i).setId(arrayPostFavourites.get(j).getId());
                            }
                        }
                    }
                    breedsAdapter = new BreedsAdapter(getActivity(), photos);
                    recyclerView.setAdapter(breedsAdapter);
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onFailure(retrofit2.Call<List<ItemGet>> call, Throwable t) {
                t.printStackTrace();
            }
        });

    }

    private void performPageination() {
        api.getPhotoForBreed(Photos.breeds_id, item_count, "desc", pager_number).enqueue(new retrofit2.Callback<List<Photos>>() {
            @Override
            public void onResponse(retrofit2.Call<List<Photos>> call, retrofit2.Response<List<Photos>> response) {
                if (response.isSuccessful()) {
                    if (pager_number < Photos.getPageCount()) {
                        List<Photos> responseData = response.body();
                        breedsAdapter.addImages(responseData);
                    } else {
                    }
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onFailure(retrofit2.Call<List<Photos>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public void createSpinner(ArrayList<Breeds> array) {
        breeds = array;
        String[] arrayList = new String[array.size() + 1];
        arrayList[0] = "Породы";
        for (int i = 0; i < array.size(); i++) {
            arrayList[i + 1] = array.get(i).getBreed();
        }
        adapterArr = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item, arrayList);
        adapterArr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterArr);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                photos.clear();
                pager_number = 0;
                previousTotal = 0;
                isLoading = true;
                selectStringName = (String) parent.getItemAtPosition(position);
                if (!selectStringName.equals("Породы")) {
                    for (int i = 0; i < breeds.size(); i++) {
                        if (breeds.get(i).getBreed().equals(selectStringName)) {
                            Photos.breeds_id = breeds.get(i).getId();
                            break;
                        }
                    }
                    createRecyclerView();
                } else {
                    recyclerView.setVisibility(GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void workServiceListOfSpinner() {
        api.getBreeds().enqueue(new retrofit2.Callback<List<Breeds>>() {
            @Override
            public void onResponse(retrofit2.Call<List<Breeds>> call, retrofit2.Response<List<Breeds>> response) {
                if (response.isSuccessful()) {
                    createSpinner((ArrayList<Breeds>) response.body());
                }
            }

            @Override
            public void onFailure(retrofit2.Call<List<Breeds>> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}
