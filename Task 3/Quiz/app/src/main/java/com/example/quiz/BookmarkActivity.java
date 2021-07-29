package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.quiz.Adapter.BookmarkAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BookmarkActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    public static final String FILE_NAME = "Quiz";
    public static final String KEY_NAME = "Questions";

    private List<QuestionModel> bookmarksList;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmark);

        recyclerView = findViewById(R.id.bookmarks_recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        preferences = getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
        gson = new Gson();

        getBookmarks();

//        list.add(new QuestionModel("What is the capital of India?","New Delhi"));
//        list.add(new QuestionModel("What is the capital of India?","New Delhi"));
//        list.add(new QuestionModel("What is the capital of India?","New Delhi"));
//        list.add(new QuestionModel("What is the capital of India?","New Delhi"));
//        list.add(new QuestionModel("What is the capital of India?","New Delhi"));
//        list.add(new QuestionModel("What is the capital of India?","New Delhi"));
        BookmarkAdapter adapter = new BookmarkAdapter(bookmarksList);
        recyclerView.setAdapter(adapter);
    }
    @Override
    protected void onPause() {
        super.onPause();
        storeBookmarks();
    }

    private void getBookmarks(){
        String json = preferences.getString(KEY_NAME,"");
        Type type = new TypeToken<List<QuestionModel>>(){}.getType();
        bookmarksList = gson.fromJson(json,type);
        if(bookmarksList == null){
            bookmarksList = new ArrayList<>();
        }
    }
    private void storeBookmarks(){
        String json = gson.toJson(bookmarksList);
        editor.putString(KEY_NAME, json);
        editor.commit();
    }
}