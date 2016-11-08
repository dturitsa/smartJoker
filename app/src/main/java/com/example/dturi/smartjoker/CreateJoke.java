package com.example.dturi.smartjoker;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.math.BigInteger;
import java.util.Random;

import MLabAccess.SaveAsyncTask;
import jokeModel.Joke;

public class CreateJoke extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_joke);
    }

    private void CreateNewJoke(){
        Joke joke = new Joke();
        Random rn = new Random();
        String randomId = new BigInteger(130, rn).toString(32);

        joke.id = randomId;
        joke.jokeText = "newjoke 22";
        joke.creator = "user2";
        joke.rating = 1f;
        saveEntry(joke);
    }

    public void saveEntry(Joke joke){
        SaveAsyncTask saveAsync = new SaveAsyncTask();
        saveAsync.execute(joke);
    }

}
