package com.example.thymen.positivenews;

public class NewsArticle {
    private String uri;
    private String lang;
    private Boolean isDuplicate;
    private String date;
    private String time;
    private String dateTime;
    private String sim;
    private String url;
    private String body;
    private String source;
    private String categories;
    private String image;
    private String video;
    private String location;

    public NewsArticle(String uri, String lang, Boolean isDuplicate, String date, String time, String dateTime, String sim, String url, String body, String source, String categories, String image, String video, String location) {
        this.uri = uri;
        this.lang = lang;
        this.isDuplicate = isDuplicate;
        this.date = date;
        this.time = time;
        this.dateTime = dateTime;
        this.sim = sim;
        this.url = url;
        this.body = body;
        this.source = source;
        this.categories = categories;
        this.image = image;
        this.video = video;
        this.location = location;
    }

}
