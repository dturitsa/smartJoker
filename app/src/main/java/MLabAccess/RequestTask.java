package MLabAccess;

/**
 * Created by dturi on 2016-11-01.
 */

import android.os.AsyncTask;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
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

/*Gets results from the mongodb*/
public class RequestTask extends AsyncTask<String, String, String> {
    //makes instance of AsyncResponse
    public AsyncResponse delegate = null;


    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection urlConnection = null;
        String result = "";
        try {
            URL url = new URL(params[0]);
            urlConnection = (HttpURLConnection) url.openConnection();

            int code = urlConnection.getResponseCode();

            if(code==200){
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                if (in != null) {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
                    String line = "";

                    while ((line = bufferedReader.readLine()) != null)
                        result += line;
                }
                in.close();
            }

            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }

        finally {
            urlConnection.disconnect();
        }
        return result;

    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        delegate.processFinish(result);
    }
}
