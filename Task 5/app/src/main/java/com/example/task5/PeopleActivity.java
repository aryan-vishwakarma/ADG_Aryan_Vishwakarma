package com.example.task5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.task5.adapter.PeopleAdapter;
import com.example.task5.adapter.petsAdapter;

import java.util.ArrayList;

public class PeopleActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PeopleAdapter adapter;
    private ArrayList<People> peopleArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);
        setTitle("People");

        recyclerView=findViewById(R.id.people_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        peopleArrayList=new ArrayList<>();

        peopleArrayList.add(new People(R.drawable.people1, "Jack Daniel", "Director, Cinematographer","12.6k","330","1211"));
        peopleArrayList.add(new People(R.drawable.people2,"John Walker","Photographer, Artist","128.6k","150","90"));

        adapter=new PeopleAdapter(PeopleActivity.this,peopleArrayList);
        recyclerView.setAdapter(adapter);
    }
}