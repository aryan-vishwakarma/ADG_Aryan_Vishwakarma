package com.example.task4;


import retrofit2.converter.gson.GsonConverterFactory;

public class DogRetrofit {

    public retrofit2.Retrofit retrofit= new retrofit2.Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    public JsonPlaceHolderApi api= retrofit.create(JsonPlaceHolderApi.class);
}