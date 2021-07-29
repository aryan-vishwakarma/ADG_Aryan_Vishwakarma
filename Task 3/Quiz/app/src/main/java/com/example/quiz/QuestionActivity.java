package com.example.quiz;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.animation.Animator;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class QuestionActivity extends AppCompatActivity {

    public static final String FILE_NAME = "Quiz";
    public static final String KEY_NAME = "Questions";

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference MyRef = database.getReference();

    private TextView question, op1, op2, op3, op4, count;
    private FloatingActionButton bookmark;
    private Button next;
    List<QuestionModel> list;
    private int position=-1;
    private int optionClicked=9;
    private boolean isAnswerChecked = false;
    private int score;
    private String category;
    private int setNo;
    private Dialog loadingDialog;

    private List<QuestionModel> bookmarksList;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Gson gson;
    private int matchedQuestionPosition;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        question=findViewById(R.id.question_question);
        op1=findViewById(R.id.option1);
        op2=findViewById(R.id.option2);
        op3=findViewById(R.id.option3);
        op4=findViewById(R.id.option4);
        count=findViewById(R.id.question_count);
        bookmark=findViewById(R.id.bookmark_btn);
        next=findViewById(R.id.queztion_next_btn);

        preferences = getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        editor = preferences.edit();
        gson = new Gson();

        getBookmarks();

        category=getIntent().getStringExtra("category");
        setNo=getIntent().getIntExtra("setNo",1);

        loadingDialog =new Dialog(this);
        loadingDialog.setContentView(R.layout.loading);
        loadingDialog.getWindow().setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.MATCH_PARENT);
        loadingDialog.setCancelable(false);

        list = new ArrayList<>();
//        list.add(new QuestionModel("Question 1","a","b","c","d","a"));
//        list.add(new QuestionModel("Question 2","a","b","c","d","b"));
//        list.add(new QuestionModel("Question 3","a","b","c","d","c"));
//        list.add(new QuestionModel("Question 4","a","b","c","d","d"));
//        list.add(new QuestionModel("Question 5","a","b","c","d","a"));
//        list.add(new QuestionModel("Question 6","a","b","c","d","b"));
//        list.add(new QuestionModel("Question 7","a","b","c","d","a"));

        bookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (modelMatch()){
                    bookmarksList.remove(matchedQuestionPosition);
                    bookmark.setImageDrawable(getDrawable(R.drawable.bookmark));
                }else {
                    bookmarksList.add(list.get(position));
                    bookmark.setImageDrawable(getDrawable(R.drawable.bookmark_filled));
                }
            }
        });

        loadingDialog.show();
        MyRef.child("SETS").child(category).child("questions").orderByChild("setNo").equalTo(setNo)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                            list.add(dataSnapshot.getValue(QuestionModel.class));
                        }
                        if(list.size()>0){
                            position++;
                            playAnim(question, 0, list.get(position).getQuestion());
                            playAnim(op1, 0, list.get(position).getOption1());
                            playAnim(op2, 0, list.get(position).getOption2());
                            playAnim(op3, 0, list.get(position).getOption3());
                            playAnim(op4, 0, list.get(position).getOption4());
                            count.setText((position+1)+"/"+list.size());
                        }
                        else{
                            Toast.makeText(QuestionActivity.this, "No Questions...", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        loadingDialog.dismiss();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(QuestionActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        loadingDialog.dismiss();
                    }
                });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isAnswerChecked) {
                    if (position < list.size()-1) {
                        position++;
                        playAnim(question, 0, list.get(position).getQuestion());
                        playAnim(op1, 0, list.get(position).getOption1());
                        playAnim(op2, 0, list.get(position).getOption2());
                        playAnim(op3, 0, list.get(position).getOption3());
                        playAnim(op4, 0, list.get(position).getOption4());
                        count.setText((position+1)+"/"+list.size());
                    }
                    else{
                        Intent intent=new Intent(QuestionActivity.this,ScoreActivity.class);
                        intent.putExtra("score",score);
                        intent.putExtra("outOf",list.size());
                        startActivity(intent);
                        finish();
                    }
                }
                else {
                    checkAnswer(position,optionClicked);
                    isAnswerChecked=true;
                }
            }
        });

        op1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (optionClicked){
                    case 1:
                        op2.setBackground(ContextCompat.getDrawable(v.getContext(),R.drawable.ic_quiz_text));
                        break;
                    case 2:
                        op3.setBackground(ContextCompat.getDrawable(v.getContext(),R.drawable.ic_quiz_text));
                        break;
                    case 3:
                        op4.setBackground(ContextCompat.getDrawable(v.getContext(),R.drawable.ic_quiz_text));
                }
                optionClicked=0;
                op1.setBackground(ContextCompat.getDrawable(v.getContext(),R.drawable.option_clicked));
                next.setEnabled(true);
                next.setAlpha(1);
            }
        });
        op2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (optionClicked){
                    case 0:
                        op1.setBackground(ContextCompat.getDrawable(v.getContext(),R.drawable.ic_quiz_text));
                        break;
                    case 2:
                        op3.setBackground(ContextCompat.getDrawable(v.getContext(),R.drawable.ic_quiz_text));
                        break;
                    case 3:
                        op4.setBackground(ContextCompat.getDrawable(v.getContext(),R.drawable.ic_quiz_text));
                }
                optionClicked=1;
                op2.setBackground(ContextCompat.getDrawable(v.getContext(),R.drawable.option_clicked));
                next.setEnabled(true);
                next.setAlpha(1);
            }
        });
        op3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (optionClicked){
                    case 0:
                        op1.setBackground(ContextCompat.getDrawable(v.getContext(),R.drawable.ic_quiz_text));
                        break;
                    case 1:
                        op2.setBackground(ContextCompat.getDrawable(v.getContext(),R.drawable.ic_quiz_text));
                        break;
                    case 3:
                        op4.setBackground(ContextCompat.getDrawable(v.getContext(),R.drawable.ic_quiz_text));
                }
                optionClicked=2;
                op3.setBackground(ContextCompat.getDrawable(v.getContext(),R.drawable.option_clicked));
                next.setEnabled(true);
                next.setAlpha(1);
            }
        });
        op4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (optionClicked){
                    case 0:
                        op1.setBackground(ContextCompat.getDrawable(v.getContext(),R.drawable.ic_quiz_text));
                        break;
                    case 1:
                        op2.setBackground(ContextCompat.getDrawable(v.getContext(),R.drawable.ic_quiz_text));
                        break;
                    case 2:
                        op3.setBackground(ContextCompat.getDrawable(v.getContext(),R.drawable.ic_quiz_text));
                }
                optionClicked=3;
                op4.setBackground(ContextCompat.getDrawable(v.getContext(),R.drawable.option_clicked));
                next.setEnabled(true);
                next.setAlpha(1);
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        storeBookmarks();
    }

    private void playAnim(final View view, final int value, final String data){
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(100).setStartDelay(10)
                .setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if(value==0) {
                    ((TextView)view).setText(data);
                    op1.setBackground(ContextCompat.getDrawable(QuestionActivity.this,R.drawable.ic_quiz_text));
                    op2.setBackground(ContextCompat.getDrawable(QuestionActivity.this,R.drawable.ic_quiz_text));
                    op3.setBackground(ContextCompat.getDrawable(QuestionActivity.this,R.drawable.ic_quiz_text));
                    op4.setBackground(ContextCompat.getDrawable(QuestionActivity.this,R.drawable.ic_quiz_text));
                    next.setAlpha((float) 0.6);
                    next.setEnabled(false);
                    next.setText(R.string.submit);
                    optionClicked=9;
                    playAnim(view, 1, data);
                    isAnswerChecked=false;
                    op1.setEnabled(true);
                    op2.setEnabled(true);
                    op3.setEnabled(true);
                    op4.setEnabled(true);
                    if (modelMatch()){
                        bookmark.setImageDrawable(getDrawable(R.drawable.bookmark_filled));
                    }else {
                        bookmark.setImageDrawable(getDrawable(R.drawable.bookmark));
                    }
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void checkAnswer(int position, int optionClicked){
        if(list.get(position).getCorrectAns().equals(list.get(position).getOptNo(optionClicked))){
            switch (optionClicked){
                case 0:
                    op1.setBackground(ContextCompat.getDrawable(QuestionActivity.this,R.drawable.correct_ans));
                    break;
                case 1:
                    op2.setBackground(ContextCompat.getDrawable(QuestionActivity.this,R.drawable.correct_ans));
                    break;
                case 2:
                    op3.setBackground(ContextCompat.getDrawable(QuestionActivity.this,R.drawable.correct_ans));
                    break;
                case 3:
                    op4.setBackground(ContextCompat.getDrawable(QuestionActivity.this,R.drawable.correct_ans));
                    break;
            }
            score++;
        }
        else{
            int correctAnsNumber=9;
            if(list.get(position).getCorrectAns().equals(list.get(position).getOptNo(0)))
                correctAnsNumber=0;
            else if(list.get(position).getCorrectAns().equals(list.get(position).getOptNo(1)))
                correctAnsNumber=1;
            else if(list.get(position).getCorrectAns().equals(list.get(position).getOptNo(2)))
                correctAnsNumber=2;
            else if(list.get(position).getCorrectAns().equals(list.get(position).getOptNo(3)))
                correctAnsNumber=3;

            switch (correctAnsNumber){
                case 0:
                    op1.setBackground(ContextCompat.getDrawable(QuestionActivity.this,R.drawable.correct_ans));
                    break;
                case 1:
                    op2.setBackground(ContextCompat.getDrawable(QuestionActivity.this,R.drawable.correct_ans));
                    break;
                case 2:
                    op3.setBackground(ContextCompat.getDrawable(QuestionActivity.this,R.drawable.correct_ans));
                    break;
                case 3:
                    op4.setBackground(ContextCompat.getDrawable(QuestionActivity.this,R.drawable.correct_ans));
                    break;
            }
            switch (optionClicked){
                case 0:
                    op1.setBackground(ContextCompat.getDrawable(QuestionActivity.this,R.drawable.wrong_ans));
                    break;
                case 1:
                    op2.setBackground(ContextCompat.getDrawable(QuestionActivity.this,R.drawable.wrong_ans));
                    break;
                case 2:
                    op3.setBackground(ContextCompat.getDrawable(QuestionActivity.this,R.drawable.wrong_ans));
                    break;
                case 3:
                    op4.setBackground(ContextCompat.getDrawable(QuestionActivity.this,R.drawable.wrong_ans));
                    break;
            }
        }
        if(position==list.size()-1)
            next.setText(R.string.show_score);
        else
            next.setText(R.string.next);
        op1.setEnabled(false);
        op2.setEnabled(false);
        op3.setEnabled(false);
        op4.setEnabled(false);
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
    private boolean modelMatch(){
        int i =0;
        boolean matched = false;
        for(QuestionModel model : bookmarksList){
            if(model.getQuestion().equals(list.get(position).getQuestion())
                    && model.getCorrectAns().equals(list.get(position).getCorrectAns())
                    && model.getSetNo() == list.get(position).getSetNo()) {
                matched = true;
                matchedQuestionPosition=i;
            }
            i++;
        }
        return matched;
    }
}