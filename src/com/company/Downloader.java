package com.company;

import com.sun.jndi.toolkit.url.Uri;
import sun.misc.IOUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Downloader {

    String titleFromUI;
    String titleForApi;
    String responseContent;
    Book book;
    URL bookAdress;

    public void setTitleFromUI(String titleFromUI) {
        this.titleFromUI = titleFromUI;
    }

    public void downloadBook(){
        titleForApi = cutTitle();
        if (apiRequest()){
            book = new Book();
            book.createBookFromJSON(responseContent);
            bookAdress = book.getTxt();

            downloadAndSaveTXT();
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
            if(status == 200) System.out.println("Znaleziono książkę, trwa pobieranie");
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String inputLine;
            responseContent = String.valueOf(new StringBuffer());
            while ((inputLine = in.readLine()) != null) {
                responseContent = responseContent + inputLine;
            }
            in.close();
            connection.disconnect();
            return true;


        } catch (IOException e) {
            e.printStackTrace();
            return false;

        }

    }

    private void downloadAndSaveTXT()
    {
        try {
            HttpURLConnection connection = (HttpURLConnection) bookAdress.openConnection();
            connection.setRequestMethod("GET");
            int status = connection.getResponseCode();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()));
            String inputLine;
            responseContent = String.valueOf(new StringBuffer());
            while ((inputLine = in.readLine()) != null) {
                responseContent = responseContent + inputLine +" \n ";
            }

            System.out.println(responseContent);

            in.close();
            connection.disconnect();
            saveBook(responseContent);
        }catch (IOException e)
        {}
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
