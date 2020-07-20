package com.example.newshunt;

public class Articles {
    private String title;
    private String source;
    private String description;
    private String url;
    private String imgUrl;
    private String date;

    public Articles(){}
    public Articles(String title,String source, String description , String url, String imgUrl, String date)
    {
        this.date = date;
        this.source = source;
        this.description = description;
        this.imgUrl = imgUrl;
        this.title = title;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
