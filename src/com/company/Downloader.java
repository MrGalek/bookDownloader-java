package com.company;

import com.sun.jndi.toolkit.url.Uri;
import sun.misc.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Downloader {

    String titleFromUI;
    String titleForApi;
    String responseContent;
    Book book;


    public void setTitleFromUI(String titleFromUI) {
        this.titleFromUI = titleFromUI;
    }

    public void downloadBook(){
        titleForApi = cutTitle();
        if (apiRequest()){
            book = new Book();
            book.createBookFromJSON(responseContent);

        }

    }

    private String cutTitle(){

        return titleFromUI.replace(" ","-").toLowerCase();
    }

    private boolean apiRequest(){
        try {
            URL url = new URL("https://wolnelektury.pl/api/books/"+titleForApi);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int status = connection.getResponseCode();
            System.out.print(status);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String inputLine;
            responseContent = String.valueOf(new StringBuffer());
            while ((inputLine = in.readLine()) != null) {
                responseContent = responseContent + inputLine;
            }
            System.out.print(responseContent);
            in.close();
            connection.disconnect();
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;

        }

    }


}
