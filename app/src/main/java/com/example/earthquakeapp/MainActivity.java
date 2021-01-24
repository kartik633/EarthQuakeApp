package com.example.earthquakeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Earthquake> earthquakes = new ArrayList<>();
    EarthAdapter earthAdapter;
    Earthquake earthquakeing;
    ListView listView;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.mylist);

        progressBar = findViewById(R.id.loading_indicator);


        fetchdata();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Earthquake curr = earthquakes.get(i);
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(curr.getUrl()));
                startActivity(intent);

            }
        });



    }

    private void fetchdata() {

        String url = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6";

        RequestQueue requestQueue;
        requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
//                    String kk = response.getString("type");

                    JSONArray jsonArray = response.getJSONArray("features");

                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject = jsonArray.getJSONObject(i).getJSONObject("properties");
                        double mag = jsonObject.getDouble("mag");
                        String place = jsonObject.getString("place");
                        long time = jsonObject.getLong("time");
                        String url = jsonObject.getString("url");

                        long timeInMilliseconds = time;

                        Log.d("myapp", time +" "+place);

                        earthquakeing = new Earthquake(mag,place,timeInMilliseconds,url);
                        earthquakes.add(earthquakeing);

                    }

                    earthAdapter = new EarthAdapter(MainActivity.this,earthquakes);
                    listView.setAdapter(earthAdapter);

                    progressBar.setVisibility(View.GONE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.d("my app is not working","chutiya");
            }
        });

        requestQueue.add((jsonObjectRequest));
    }


}