package com.example.thymen.positivenews;

import java.util.List;

import javax.xml.transform.Source;

public class WebSite {
    private String status;
    private List<Source> sources;

    public WebSite() {

    }

    public WebSite(String status, List<Source> sources) {
        this.status = status;
        this.sources = sources;
    }
}
