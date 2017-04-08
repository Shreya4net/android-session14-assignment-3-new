package com.dce.puja.myapp0;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    String url = "http://10.0.2.2:8080/AndroidServiceDemo/Test";
    TextView tvdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvdata = (TextView) findViewById(R.id.tvdata);

    }

    public void get(View v) {
        new MyTask().execute(url);
    }

    private class MyTask extends AsyncTask<String, Integer, JSONObject> {
        ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Getting data!!...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();

        }


        @Override
        protected JSONObject doInBackground(String... strings) {
            JSONObject json = null;
            try {
                URL url1 = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url1.openConnection();
                urlConnection.setRequestMethod("POST");
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        urlConnection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "n");
                    json = new JSONObject(sb.toString());

                }
                Log.d("JSON Parser", "obj" + sb.toString());


            } catch (MalformedURLException e) {
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }catch(JSONException e){
                e.printStackTrace();
            }
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            super.onPostExecute(jsonObject);
            pDialog.dismiss();
            try {
                JSONArray array = jsonObject.getJSONArray("user");
                JSONObject user1 = array.getJSONObject(0);
                tvdata.setText("Name " + user1.get("name") + "\n Email " + user1.get("email"));

            } catch (JSONException e){
                e.printStackTrace();
            }


        }
    }


}



