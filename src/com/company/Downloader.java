package com.company;

import java.io.*;
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
        try {
            URL infoUrl = new URL("https://wolnelektury.pl/api/books/"+titleForApi);
            if (apiRequest(infoUrl, false)){
                System.out.println("Znaleziono książkę, trwa pobieranie.");
                book = new Book();
                book.createBookFromJSON(responseContent);
                if(apiRequest(book.getTxt(),true))System.out.println("Pobieranie zakończone sukcesem.");

             }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

    }

    private String cutTitle(){

        return titleFromUI.replace(" ","-").toLowerCase();
    }

    private boolean apiRequest(URL url, boolean bookText){
        try {

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int status = connection.getResponseCode();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String inputLine;
            responseContent = String.valueOf(new StringBuffer());
            while ((inputLine = in.readLine()) != null) {
                responseContent = responseContent + inputLine+" \n ";
            }
            if(bookText)saveBook(responseContent);
            in.close();
            connection.disconnect();
            return true;


        } catch (IOException e) {
            e.printStackTrace();
            return false;

        }

    }

    private void saveBook(String bookText)
    {
        try {
            PrintWriter printWriter = new PrintWriter(book.getTitle()+".txt");
            printWriter.print(bookText);
            printWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


}
