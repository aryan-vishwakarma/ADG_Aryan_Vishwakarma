package com.example.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.quiz.Adapter.CatagoryAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CategoriesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference MyRef = database.getReference();
    private Dialog loadingDialog;

    private List<CategoryModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Categories");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadingDialog =new Dialog(this);
        loadingDialog.setContentView(R.layout.loading);
        loadingDialog.getWindow().setLayout(ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        loadingDialog.setCancelable(false);

        recyclerView=findViewById(R.id.recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        list = new ArrayList<>();
//        list.add(new CategoryModel("https://images.dog.ceo/breeds/affenpinscher/n02110627_11263.jpg","Category 1"));
//        list.add(new CategoryModel("https://images.dog.ceo/breeds/affenpinscher/n02110627_11263.jpg","Category 1"));
//        list.add(new CategoryModel("https://images.dog.ceo/breeds/affenpinscher/n02110627_11263.jpg","Category 1"));
//        list.add(new CategoryModel("https://images.dog.ceo/breeds/affenpinscher/n02110627_11263.jpg","Category 1"));
//        list.add(new CategoryModel("https:/images.dog.ceo/breeds/affenpinscher/n02110627_11263.jpg","Category 1"));
//        list.add(new CategoryModel("https:/images.dog.ceo/breeds/affenpinscher/n02110627_11263.jpg","Category 1"));
//        list.add(new CategoryModel("https:/images.dog.ceo/breeds/affenpinscher/n02110627_11263.jpg","Category 1"));
//        list.add(new CategoryModel("https:/images.dog.ceo/breeds/affenpinscher/n02110627_11263.jpg","Category 1"));

        CatagoryAdapter adapter=new CatagoryAdapter(list);
        recyclerView.setAdapter(adapter);

        loadingDialog.show();
        MyRef.child("Categories").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    list.add(dataSnapshot.getValue(CategoryModel.class));
                }
                adapter.notifyDataSetChanged();
                loadingDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CategoriesActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                loadingDialog.dismiss();
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(item.getItemId() == android.R.id.home){
            startActivity(new Intent(CategoriesActivity.this,StartActivity.class));
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}