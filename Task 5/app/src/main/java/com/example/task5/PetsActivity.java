package com.example.task5;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.example.task5.adapter.petsAdapter;

import java.util.ArrayList;

public class PetsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private petsAdapter adapter;
    private ArrayList<Pet> petArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pets);

        setTitle("Pets");

        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        petArrayList=new ArrayList<>();

        petArrayList.add(new Pet(R.drawable.pets_cat_img,"Cats"));
        petArrayList.add(new Pet(R.drawable.pets_dog,"Dogs"));
        petArrayList.add(new Pet(R.drawable.pets_hamster,"Hamsters"));
        petArrayList.add(new Pet(R.drawable.pets_parrot,"Parrots"));
        petArrayList.add(new Pet(R.drawable.pets_goldfish,"Goldfish"));

        adapter=new petsAdapter(PetsActivity.this,petArrayList);
        recyclerView.setAdapter(adapter);
    }
}