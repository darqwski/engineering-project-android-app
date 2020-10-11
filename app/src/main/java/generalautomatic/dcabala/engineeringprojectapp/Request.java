package generalautomatic.dcabala.engineeringprojectapp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Request extends AsyncTask<String, String, String> {
    private static final String SERVER_ADDRESS = "https://dariuszcabala.pl/engineering";
    private Context context;
    private ResponseAction responseAction;
    private int responseCode;
    private static String PHP_SESSION_COOKIE = "";
    public Request(Context context, ResponseAction responseAction){
        this.context = context;
        this.responseAction = responseAction;
    }
    @Override
    protected String doInBackground(String... strings) {
        String method = strings[0];
        String urlString = strings[1];
        String body = strings[2];
        OutputStream out = null;
        Log.wtf("url",SERVER_ADDRESS+urlString);
        String response = "";
        try {
            URL url = new URL(SERVER_ADDRESS+urlString);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setInstanceFollowRedirects(false);
            urlConnection.setRequestMethod(method);
            urlConnection.addRequestProperty("Cookie", PHP_SESSION_COOKIE);
            urlConnection.setRequestProperty("Accept-Charset", "UTF-8");
            //urlConnection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            if(method != "GET"){
                out = new BufferedOutputStream(urlConnection.getOutputStream());
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out, "UTF-8"));
                writer.write(body);
                writer.flush();
                writer.close();
                out.close();
            }
            responseCode=urlConnection.getResponseCode();
            String cookie = urlConnection.getHeaderField("Set-Cookie");
            if(cookie != null){
                PHP_SESSION_COOKIE = cookie;
            }
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;
                }
            }
            else {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(urlConnection.getErrorStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;
                }
            }

            urlConnection.connect();
        } catch (Exception e) {
            return e.getMessage();
        }

        return response;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        responseAction.action(responseCode, s);
    }
}
