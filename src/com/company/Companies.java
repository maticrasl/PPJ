package com.company;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Companies implements JsonSupport {
    private List<Podjetje> podjetja = new ArrayList<>();

    @Override
    public String toString() {
        String izpis = "";
        for(int i = 0; i < podjetja.size(); i++) {
            izpis += podjetja.get(i).toString() + "\n\n";
        }
        return izpis;
    }

    public void add(Podjetje p) {
        podjetja.add(p);
    }

    public Podjetje get(int i) {
        if(i < podjetja.size() && i >= 0)
            return podjetja.get(i);
        else {
            System.out.println("Neveljaven indeks podjetja!");
        }
        return new Podjetje();
    }

    public void remove(int i) {
        if(i < podjetja.size() && i >= 0)
            podjetja.remove(i);
        else {
            System.out.println("Neveljaven indeks podjetja!");
        }
    }

    @Override
    public void toJson() {
        Gson gson = new Gson();
        Helper.writeToFile("Companies.json", gson.toJson(this));
    }

    @Override
    public void fromJson() {
        Gson gson = new Gson();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("Companies.json"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Companies i = gson.fromJson(bufferedReader, Companies.class);
        this.podjetja = i.podjetja;
    }
}
