package com.example.thymen.positivenews;

import android.app.Application;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyApplication extends Application {

    private String key, value;
    private HashMap<String, String> positiveWordsMap;

    public HashMap<String,String> getPositiveWords() {
        return positiveWordsMap;
    }

    public void setPositiveWords(HashMap<String, String> positiveWordsMap) {
        this.positiveWordsMap = positiveWordsMap;
    }
}
