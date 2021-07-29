package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ScoreActivity extends AppCompatActivity {

    private TextView myscore, outof;
    private Button done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        myscore = findViewById(R.id.score_score);
        outof = findViewById(R.id.score_out_of);
        done = findViewById(R.id.score_button);

        myscore.setText(String.valueOf(getIntent().getIntExtra("score",0)));
        outof.setText("Out Of "+ (getIntent().getIntExtra("outOf",0)));

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}