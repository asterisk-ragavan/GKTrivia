package com.sakthi.gktrivia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.sakthi.gktrivia.data.Repo;
import com.sakthi.gktrivia.data.answerarrayasyncresponce;
import com.sakthi.gktrivia.databinding.ActivityMainBinding;
import com.sakthi.gktrivia.model.Question;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    public int currentQuestionIndex = 0;
    public List<Question> questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        questions = new Repo().getquestion(new answerarrayasyncresponce() {
            @Override
            public void processfinished(ArrayList<Question> questionArrayList) {
                binding.questionText.setText(questionArrayList.get(currentQuestionIndex).getAnswer());
                binding.textQuestionNo.setText("Question: " + (currentQuestionIndex+1 ) + "/" + questions.size());


            binding.buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentQuestionIndex = (currentQuestionIndex+1) % questions.size();
                updatequestion();
            }

        });

            binding.buttonPrevious.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentQuestionIndex = (currentQuestionIndex-1) % questions.size();
                    updatequestion();
                }
            });


            }
        });
    }

    private void updatequestion() {
        if (currentQuestionIndex<0)currentQuestionIndex = 0;
        binding.textQuestionNo.setText("Question: " + (currentQuestionIndex+1 ) + "/" + questions.size());
        binding.questionText.setText(questions.get(currentQuestionIndex).getAnswer());
    }
}