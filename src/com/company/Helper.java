package com.company;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.List;
import java.util.Scanner;

public class Helper {
    public static String readFromFile(String filename) {
        String a;
        try {
            a = new Scanner(new File(filename)).useDelimiter("\\z").next();
        } catch (IOException e) {
            return "Napaka pri branju datoteke!";
        };
        return a;
    }

    public static void writeToFile(String filename, String json) {
        try {
            PrintWriter a = new PrintWriter(filename);
            a.println(json);
            a.close();
        } catch (IOException e) {
            e.printStackTrace();
        };
    }
}
