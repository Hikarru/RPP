package com.example.lab5;

import java.util.List;
//Retrofit — это библиотека для сетевого взаимодействия.
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface API {
    @Headers("x-api-key: 9442c6b9-5419-424f-9a41-1fb096fe582d")
    @GET("breeds")
    Call<List<Breeds>> getBreeds();

    @Headers("x-api-key: 9442c6b9-5419-424f-9a41-1fb096fe582d")
    @GET("images/search?mime_types=gif,jpg,png")
    Call<List<Photos>> getPhotoForBreed(@Query("breed_ids") String breed,
                                        @Query("limit") int limit,
                                        @Query("order") String desc,
                                        @Query("page") int page
    );

    @Headers("x-api-key: 43ceed6e-a972-48da-934c-b999b2382222")
    @GET("votes")
    Call<List<ItemGet>> getVotes(@Query("sub_id") String sub_id);

    @Headers("x-api-key: 43ceed6e-a972-48da-934c-b999b2382222")
    @GET("images/{image_id}")
    Call<Photos> getVotesLike(@Path("image_id") String image_id);

    @Headers("x-api-key: 43ceed6e-a972-48da-934c-b999b2382222")
    @DELETE("votes/{vote_id}")
    Call<Void> delVote(@Path("vote_id") int vote_id);

    @Headers("x-api-key: 43ceed6e-a972-48da-934c-b999b2382222")
    @POST("votes")
    Call<Votes> setPostFavourites(@Body ItemCreate postCreate);
}