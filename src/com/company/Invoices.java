package com.company;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Invoices implements JsonSupport {
    private List<Racun> racuni = new ArrayList<>();

    @Override
    public String toString() {
        String izpis = "";
        for(int i = 0; i < racuni.size(); i++) {
            izpis += racuni.get(i).toString() + "\n\n";
        }
        return izpis;
    }

    public void add(Racun r) {
        racuni.add(r);
    }

    public Racun get(int i) {
        if(i < racuni.size() && i >= 0)
            return racuni.get(i);
        else {
            System.out.println("Neveljaven indeks racuna!");
        }
        return new Racun();
    }

    public void remove(int i) {
        if(i < racuni.size() && i >= 0)
            racuni.remove(i);
        else {
            System.out.println("Neveljaven indeks racuna!");
        }
    }

    @Override
    public void toJson() {
        Gson gson = new Gson();
        Helper.writeToFile("Invoices.json", gson.toJson(this));
    }

    @Override
    public void fromJson() {
        Gson gson = new Gson();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("Invoices.json"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Invoices i = gson.fromJson(bufferedReader, Invoices.class);
        this.racuni = i.racuni;
    }
}
