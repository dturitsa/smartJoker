package com.example.dturi.smartjoker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import MLabAccess.QueryBuilder;
import MLabAccess.SaveAsyncTask;
import jokeModel.Joke;
import MLabAccess.AsyncResponse;
import MLabAccess.RequestTask;
import org.json.JSONArray;
import org.json.JSONObject;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;

public class ViewJokeActivity extends AppCompatActivity implements AsyncResponse{
    JSONArray json;
    RequestTask getResultsTask = new RequestTask();
    TextView jokeTextView, jokeCreatorTextView, jokeRatingTextView;
    Joke joke = new Joke();
    int jokeNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_joke);

        jokeTextView = (TextView) findViewById(R.id.jokeText);
        jokeCreatorTextView = (TextView) findViewById(R.id.jokeRatingText);
        jokeRatingTextView = (TextView) findViewById(R.id.jokeRatingText);
        //runs the search query
        QueryBuilder q = new QueryBuilder();
        getResultsTask.execute(q.getAllJokesURL());
        getResultsTask.delegate = this;
    }

    //Async saving of entry
    public void saveEntry(Joke joke){
        SaveAsyncTask saveAsync = new SaveAsyncTask();
        saveAsync.execute(joke);
    }

    //gets rating from user. updates entry with the rating. displays next joke
    public void rateJoke(View v){
        //Toast.makeText(this, , Toast.LENGTH_SHORT).show();
        String buttonTag = v.getTag().toString();
        float ratingModifier;
        if(buttonTag.equals("rateUpBut"))
            ratingModifier = 1f;
        else if(buttonTag.equals("rateDownBut"))
            ratingModifier = -1f;
        else
            ratingModifier = 0f;

        jokeNum++;
        displayJoke();
        joke.rating+= ratingModifier;
        saveEntry(joke);
        Log.d("ViewJoke.rateJoke()", Float.toString(ratingModifier));
    }

    //displays the current joke on the screen
    private void displayJoke(){
        try{
            JSONObject e = json.getJSONObject(jokeNum % json.length());
            joke.id = e.getString("_id");
            joke.jokeText = e.getString("jokeText");
            joke.creator = e.getString("creator");
            joke.rating = Float.parseFloat(e.getString("rating"));
            jokeTextView.setText(joke.jokeText);
            jokeCreatorTextView.setText("Created by: " + joke.creator);
            jokeRatingTextView.setText(("Current rating: " + joke.rating));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //runs when the Async database access finishes. Loads jokes
    public void processFinish(String output) {
        //Toast.makeText(this, output, Toast.LENGTH_SHORT).show();
        try {
            json = new JSONArray(output);
            displayJoke();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "No Entries Found", Toast.LENGTH_SHORT).show();
        }


    }
}
