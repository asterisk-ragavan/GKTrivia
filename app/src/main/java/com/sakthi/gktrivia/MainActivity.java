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
    private int currentQuestionIndex = 0;
    List<Question> questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        questions = new Repo().getquestion(new answerarrayasyncresponce() {
            @Override
            public void processfinished(ArrayList<Question> questionArrayList) {

                binding.questionText.setText(questionArrayList.get(currentQuestionIndex).getAnswer());

            }
        });

    }

}