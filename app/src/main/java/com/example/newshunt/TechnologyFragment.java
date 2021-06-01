package com.example.newshunt;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TechnologyFragment extends Fragment {
    Retrofit retrofit;
    private Api api;
    RecyclerView recyclerView;
    List<Articles> articles;
    ProgressBar progressBar;
    Adapter adapter;
    RecyclerViewOnclickInterface recyclerViewOnclickInterface;

    public TechnologyFragment(RecyclerViewOnclickInterface listener)
    {
        recyclerViewOnclickInterface = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.technology_fragment,container,false);
        recyclerView = view.findViewById(R.id.technology_articles);
        articles = new ArrayList<>();
        progressBar = view.findViewById(R.id.progressBarTechnology);
        retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(Api.class);
        extractArticles();
        return view;
    }

    private void extractArticles() {
        Call<JsonObject> call = api.getCategory("in","technology","d23fe9e55fce497c94b663b0b23b510b");

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(@NonNull Call<JsonObject> call,@NonNull Response<JsonObject> response) {
                try {
                    assert response.body() != null;
                    JsonArray jsonArray = response.body().getAsJsonArray("articles");
                    for (int i = 0; i < jsonArray.size(); i++) {
                        JsonObject jsonObject = (JsonObject) jsonArray.get(i);
                        try{
                            JsonObject source = jsonObject.getAsJsonObject("source");

                            Articles article = new Articles();
                            article.setSource(source.get("name").getAsString());

                            // Setting date format
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                            sdf.setTimeZone(TimeZone.getTimeZone("IST"));
                            Date date = sdf.parse(jsonObject.get("publishedAt").getAsString());
                            SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy HH:mm");
                            String strDate = formatter.format(date);

                            article.setDate(strDate);
                            article.setTitle(jsonObject.get("title").getAsString());
                            article.setImgUrl(jsonObject.get("urlToImage").getAsString());
                            article.setUrl(jsonObject.get("url").getAsString());
                            articles.add(article);
                        }catch (Exception ignored){
                        }
                    }
                }
                catch (Exception e){
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                adapter = new Adapter(getActivity(),articles,recyclerViewOnclickInterface);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(@NonNull Call<JsonObject> call,@NonNull Throwable t) {
                Toast.makeText(getActivity(), "Some Error Occurred!", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
