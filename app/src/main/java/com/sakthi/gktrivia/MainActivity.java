package com.sakthi.gktrivia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import com.google.android.material.snackbar.Snackbar;
import com.sakthi.gktrivia.data.Repo;
import com.sakthi.gktrivia.data.answerarrayasyncresponce;
import com.sakthi.gktrivia.databinding.ActivityMainBinding;
import com.sakthi.gktrivia.model.Question;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    public int currentQuestionIndex;
    public List<Question> questions;
    public int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        binding.textScore.setText("score: "+score);

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

            binding.buttonTrue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkanswer(true);
                    updatequestion();
                }
            });

            binding.buttonFalse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkanswer(false);
                    updatequestion();
                }
            });

            }
        });
    }

    private void checkanswer(boolean option) {
        boolean answer = questions.get(currentQuestionIndex).answeropt;
        int snackmesssage = 0;
        if(option == answer){
            score = score + 100;
            binding.textScore.setText("score: "+score);
            snackmesssage = R.string.correctanswer;
            fadeanimation();
            currentQuestionIndex = (currentQuestionIndex+1) % questions.size();
            updatequestion();
        }else {
            score = score - 100;
            if(score<0)score=0;
            binding.textScore.setText("score: "+score);
            snackmesssage = R.string.incorrect;
            shakeanimation();
        }
        Snackbar.make(binding.cardView,snackmesssage,Snackbar.LENGTH_SHORT).show();
    }

    private void updatequestion() {
        if (currentQuestionIndex<0) currentQuestionIndex = 0;
        binding.textQuestionNo.setText("Question: " + (currentQuestionIndex+1 ) + "/" + questions.size());
        binding.questionText.setText(questions.get(currentQuestionIndex).getAnswer());
    }

    private void shakeanimation(){
        Animation shake = AnimationUtils.loadAnimation(MainActivity.this,R.anim.shake_animation);
        binding.cardView.setAnimation(shake);
        shake.setAnimationListener(new Animation.AnimationListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onAnimationStart(Animation animation) {
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(200);
                binding.questionText.setTextColor(Color.RED);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                int defalt = getResources().getColor(R.color.text);
                binding.questionText.setTextColor(defalt);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void fadeanimation(){
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f,0.0f);
        alphaAnimation.setDuration(300);
        alphaAnimation.setRepeatCount(1);
        alphaAnimation.setRepeatMode(Animation.REVERSE);
        binding.cardView.setAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                v.vibrate(50);
                binding.questionText.setTextColor(Color.GREEN);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                int defalt = getResources().getColor(R.color.text);
                binding.questionText.setTextColor(defalt);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}