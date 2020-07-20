package com.example.newshunt;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class SportsFragment extends Fragment {
    RecyclerView recyclerView;
    List<Articles> articles;
    ProgressBar progressBar;
    Adapter adapter;
    private String JSON_URL = "https://newsapi.org/v2/top-headlines?country=in&category=sports&apiKey=d23fe9e55fce497c94b663b0b23b510b";
    RecyclerViewOnclickInterface recyclerViewOnclickInterface;

    public SportsFragment(RecyclerViewOnclickInterface listener)
    {
        recyclerViewOnclickInterface = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sports_fragment,container,false);
        recyclerView = view.findViewById(R.id.sports_articles);
        articles = new ArrayList<Articles>();
        progressBar = view.findViewById(R.id.progressBarSports);
        extractArticles();
        return view;
    }

    private void extractArticles() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, JSON_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("articles");

                    for(int i=0;i<jsonArray.length();i++)
                    {
                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                        JSONObject source = jsonObject.getJSONObject("source");

                        Articles article = new Articles();
                        article.setSource(source.getString("name"));

                        // Setting date format
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                        sdf.setTimeZone(TimeZone.getTimeZone("IST"));
                        Date date = sdf.parse(jsonObject.getString("publishedAt"));
                        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy HH:mm");
                        String strDate = formatter.format(date);

                        article.setDate(strDate);
                        article.setTitle(jsonObject.getString("title"));
                        article.setImgUrl(jsonObject.getString("urlToImage"));
                        article.setUrl(jsonObject.getString("url"));
                        articles.add(article);
                    }
                } catch (JSONException | ParseException e) {
                    e.printStackTrace();
                }
//                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                adapter = new Adapter(getActivity(),articles,recyclerViewOnclickInterface);
                recyclerView.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error",error.getMessage());
            }
        });

        requestQueue.add(jsonObjectRequest);

        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<String>() {
            @Override
            public void onRequestFinished(Request<String> request) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
