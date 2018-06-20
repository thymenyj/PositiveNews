package com.example.thymen.positivenews;

import java.util.List;

class UrlsToLogos {
    private String small, medium, large;

    public String getSmall() {
        return small;
    }

    public void setSmall(String small) {
        this.small = small;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }
}

public class NewsSource {
    String id;
    String name;
    String description;
    String url;
    String category;
    String language;
    String country;
    private UrlsToLogos urlsToLogos;
    private List<String> sortBysAvaible;

    public NewsSource() {

    }

    public NewsSource(String id, String name, String description, String url, String category, String language, String country, UrlsToLogos urlsToLogos, List<String> sortBysAvaible) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.url = url;
        this.category = category;
        this.language = language;
        this.country = country;
        this.urlsToLogos = urlsToLogos;
        this.sortBysAvaible = sortBysAvaible;
    }
}
