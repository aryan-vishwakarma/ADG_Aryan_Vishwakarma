package com.example.task4;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface JsonPlaceHolderApi {
    @GET("breeds/list/all")
    Call<Root> getRoot();

    @GET("/{breed_name}/images/random")
    Call<ImgUrl> getUrl(@Path("breed_name") String breed_name);

}
