package com.example.newshunt;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    List<Articles> articles;
    LayoutInflater inflater;
    RecyclerViewOnclickInterface listener;

    public Adapter(Context ctx,List<Articles> articles,RecyclerViewOnclickInterface listener)
    {
        this.inflater = LayoutInflater.from(ctx);
        this.articles = articles;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.articleSource.setText(articles.get(position).getSource());
        holder.articleTitle.setText(articles.get(position).getTitle());
        holder.articleDate.setText(articles.get(position).getDate());
        Picasso.get().load(articles.get(position).getImgUrl()).into(holder.articleImg);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView articleTitle,articleSource,articleDate;
        ImageView articleImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            articleDate = itemView.findViewById(R.id.articleDate);
            articleImg = itemView.findViewById(R.id.articleImg);
            articleSource = itemView.findViewById(R.id.articleSrc);
            articleTitle = itemView.findViewById(R.id.articleTitle);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String url = articles.get(getAdapterPosition()).getUrl();
                    listener.onClick(url);
                }
            });
        }
    }
}
