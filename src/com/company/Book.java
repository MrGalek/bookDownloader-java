package com.company;

import com.google.gson.Gson;
import netscape.javascript.JSObject;

import java.net.URL;

public class Book {

    private String title;
    //JSObject authors[];
    private URL txt;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public URL getTxt() {
        return txt;
    }

    public void setTxt(URL txt) {
        this.txt = txt;
    }

    public void createBookFromJSON(String responseContent) {


        Gson g = new Gson();
        Book b  = g.fromJson(responseContent, Book.class);

        this.title = b.getTitle();
        this.txt = b.getTxt();


    }
}
