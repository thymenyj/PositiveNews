package com.example.thymen.positivenews.Object;

import java.io.Serializable;
import java.util.List;

public class NewsSource implements Serializable {
    String id;
    String name;
    String description;
    String url;

    public NewsSource() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
