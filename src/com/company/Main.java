package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Downloader downloader = new Downloader();
        downloader.setTitleFromUI(getTitleFromUser());
        downloader.downloadBook();
    }

    static String getTitleFromUser() {

        System.out.println("Podaj tytuł szukanej lektury: ");
        String title;
        Scanner reader = new Scanner(System.in);

        title = reader.nextLine();
        return title;

    }
}
