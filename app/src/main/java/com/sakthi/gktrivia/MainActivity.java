package com.sakthi.gktrivia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

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
    private static final String score_data_id = "score data";
    int reset_count = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences get_spref_data = getSharedPreferences(score_data_id,MODE_PRIVATE);
        score = get_spref_data.getInt("score_data",0);
        currentQuestionIndex = get_spref_data.getInt("currentQuestionIndex",0);

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
        binding.textScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(reset_count==0){
                    score = 1;
                    currentQuestionIndex=0;
                    binding.textScore.setText("score: "+score);
                    updatequestion();
                    Toast.makeText(MainActivity.this,"you have reseted your score and questions successfully -_-",Toast.LENGTH_SHORT).show();
                    reset_count = 4;
                    }
                else {
                    reset_count--;
                    Toast.makeText(MainActivity.this,"clicking on score "+ reset_count +" more times will reset your score",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences sharedPreferences = getSharedPreferences(score_data_id,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("score_data",score);
        editor.putInt("currentQuestionIndex",currentQuestionIndex);
        editor.apply();
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