package com.sakthi.gktrivia;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.sakthi.gktrivia.data.Repo;
import com.sakthi.gktrivia.data.answerarrayasyncresponce;
import com.sakthi.gktrivia.model.Question;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Question> questions = new Repo().getquestion(new answerarrayasyncresponce() {
            @Override
            public void processfinished(ArrayList<Question> questionArrayList) {

                Log.d("Main", "processfinished: "+ questionArrayList.toString());
            }
        });
    }

}