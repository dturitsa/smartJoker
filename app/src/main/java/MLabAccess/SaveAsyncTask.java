package MLabAccess;

/**
 * Created by dturi on 2016-11-01.
 */

import jokeModel.Joke;
import android.os.AsyncTask;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.*;
import android.util.Log;

/*Async task for saving stuff in the mongodb database
* not used in the current version of the app. To be used to allow users/asmins to
* add recipies to the database in future versions*/
public class SaveAsyncTask extends AsyncTask<Joke, Void, Boolean> {

    public Boolean doInBackground(Joke... arg0) {
        HttpURLConnection connection = null;
        Joke joke = arg0[0];
        QueryBuilder qb = new QueryBuilder();
        try {
            URL url=new URL(qb.buildJokesSaveURL());
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            OutputStreamWriter streamWriter = new OutputStreamWriter(connection.getOutputStream());
            streamWriter.write(qb.createJokeEntry(joke));
            streamWriter.flush();
            StringBuilder stringBuilder = new StringBuilder();
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(streamReader);
                String response = null;
                while ((response = bufferedReader.readLine()) != null) {
                    stringBuilder.append(response + "\n");
                }
                bufferedReader.close();

                Log.d("test", stringBuilder.toString());
                return true;
            } else {
                Log.e("test", connection.getResponseMessage());
                return false;
            }
        } catch (Exception exception){
            Log.e("test", exception.toString());
            return false;
        } finally {
            if (connection != null){
                connection.disconnect();
            }
        }
    }
}
