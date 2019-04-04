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

public class Artikli implements JsonSupport {
    class posamezniArtikel {
        public Artikel artikel;
        public double kolicina;
    }
    private List<Artikel> seznam = new ArrayList<>();

    public String izpisPosameznegaArtikla(Artikel a, int longestDrzava, int longestNaziv, int longestId) {
        String izpis;
        izpis = String.valueOf(a.getId()) + '\t';
        for(int i = 0; i < (longestId / 4) - (String.valueOf(a.getId()).length() / 4); i++)
            izpis += '\t';
        izpis += a.getEAN() + '\t' + a.getDrzava() + '\t';
        for(int i = 0; i < (longestDrzava / 4) - (a.getDrzava().length() / 4); i++)
            izpis += '\t';
        izpis += a.getIme() + '\t';
        for(int i = 0; i < (longestNaziv / 4) - (a.getIme().length() / 4); i++)
            izpis += '\t';
        izpis += String.valueOf(a.getCenaBrezDDV()) +
                "\t\t\t" + String.valueOf(a.getDDV()) + "%\t" + String.valueOf(a.getCenaZDDV()) + '\t';
        return izpis;
    }

    public String izpisPosameznegaArtiklaBrezDDV(Artikel a, int longestDrzava, int longestNaziv, int longestId) {
        String izpis;
        izpis = String.valueOf(a.getId()) + '\t';
        for(int i = 0; i < (longestId / 4) - (String.valueOf(a.getId()).length() / 4); i++)
            izpis += '\t';
        izpis += a.getEAN() + '\t' + a.getDrzava() + '\t';
        for(int i = 0; i < (longestDrzava / 4) - (a.getDrzava().length() / 4); i++)
            izpis += '\t';
        izpis += a.getIme() + '\t';
        for(int i = 0; i < (longestNaziv / 4) - (a.getIme().length() / 4); i++)
            izpis += '\t';
        izpis += String.valueOf(a.getCenaBrezDDV()) + "\t\t";
        return izpis;
    }

    public String toString(boolean jeDavcniZavezanec) {
        int longestDrzava = 0;
        int longestNaziv = 0;
        int longestId = 0;
        for (Artikel a : seznam) {
            if(a.getDrzava().length() > longestDrzava)
                longestDrzava = a.getDrzava().length();
            if(a.getIme().length() > longestNaziv)
                longestNaziv = a.getIme().length();
            if(String.valueOf(a.getId()).length() > longestId)
                longestId = String.valueOf(a.getId()).length();
        }
        String izpis = "ID\t";
        for(int i = 0; i < longestId / 4; i++)
            izpis += '\t';
        izpis += "EAN\t\t\t\tDrzava\t";
        for(int i = 1; i < longestDrzava / 4; i++)
            izpis += '\t';
        izpis += "Naziv\t";
        for(int i = 1; i < longestNaziv / 4; i++)
            izpis += '\t';
        izpis += "Cena brez DDV\t";
        if(jeDavcniZavezanec)
            izpis += "DDV\t\tCena z DDV\t";
        izpis += "Kolicina\n";
        for(int i = 0; i < seznam.size(); i++) {
            if (jeDavcniZavezanec) {
                if(seznam.get(i).getEAN().charAt(0) != '2')
                    izpis += izpisPosameznegaArtikla(seznam.get(i), longestDrzava, longestNaziv, longestId) +
                            '\t' + String.valueOf(seznam.get(i).getKolicina()) + '\n';
                else
                    izpis += izpisPosameznegaArtikla(seznam.get(i), longestDrzava, longestNaziv, longestId) +
                            '\t' + String.valueOf((float)(seznam.get(i).getKolicina())) + " kg\n";
            }
            else {
                if(seznam.get(i).getKolicina() == 0.0f) {
                    izpis += izpisPosameznegaArtiklaBrezDDV(seznam.get(i), longestDrzava, longestNaziv, longestId) +
                            '\t' + String.valueOf(seznam.get(i).getKolicina()) + '\n';
                }
                else {
                    izpis += izpisPosameznegaArtiklaBrezDDV(seznam.get(i), longestDrzava, longestNaziv, longestId) +
                            '\t' + String.valueOf((float)(seznam.get(i).getKolicina())) + " kg\n";
                }
            }
        }
        return izpis;
    }

    public List<Artikel> getSeznam() {
        return seznam;
    }

    public void setSeznam(List<Artikel> seznam) {
        this.seznam = seznam;
    }

    public Artikli() {

    }

    public Artikli(List<Artikel> seznam) {
        this.seznam = seznam;
    }

    public void addArtikel(Artikel a, double k) {
        for(int i = 0; i < seznam.size(); i++) {
            if(a.getId() == seznam.get(i).getId()) {
                seznam.get(i).setKolicina(seznam.get(i).getKolicina() + (float)k);
                if(seznam.get(i).getKolicina() == 0.0f)
                    seznam.remove(i);
                return;
            }
        }
        a.setKolicina((float)k);
        this.seznam.add(a);
    }

    public void setKolicina(int index, int k) {
        if(index < seznam.size())
            seznam.get(index).setKolicina(k);
    }

    public double getKolicina(int index) {
        if(index < seznam.size())
            return seznam.get(index).getKolicina();
        else return -1;
    }

    public String getEAN(int index) {
        if(index < seznam.size())
            return seznam.get(index).getEAN();
        System.console().printf("TUKAJ JE NAPAKA");
        return "";
    }

    public void setCenaBrezDDV(int index, float c) {
        if(index < seznam.size())
            seznam.get(index).setCenaBrezDDV(c);
    }

    public float getCenaBrezDDV(int index) {
        if(index < seznam.size())
            return seznam.get(index).getCenaBrezDDV();
        else
            return -1.0f;
    }

    public void setCenaZDDV(int index, float c) {
        if(index < seznam.size())
            seznam.get(index).setCenaZDDV(c);
    }

    public float getCenaZDDV(int index) {
        if(index < seznam.size())
            return seznam.get(index).getCenaZDDV();
        else {
            System.out.println("NAPAKA! Indeks ne obstaja!");
            return 0.0f;
        }
    }

    public float getDDV(int index) {
        if(index < seznam.size())
            return seznam.get(index).getDDV();
        else {
            System.out.println("NAPAKA! Indeks ne obstaja!");
            return 0.0f;
        }
    }

    public long getId(int index) {
        if(index < seznam.size())
            return seznam.get(index).getId();
        else {
            System.out.println("NAPAKA! indeks ne obstaja!");
            return -1;
        }
    }

    public Artikel get(int index) {
        if(index < seznam.size())
            return seznam.get(index);
        else {
            System.console().printf("NAPAKA! indeks ne obstaja!");
            return null;
        }
    }

    public String getIme(int index) {
        if(index < seznam.size())
            return seznam.get(index).getIme();
        else {
            System.out.println("NAPAKA! indeks ne obstaja!");
            return null;
        }
    }

    public int count() {
        return seznam.size();
    }

    @Override
    public void toJson() {
        Gson gson = new Gson();
        Helper.writeToFile("Artikli.json", gson.toJson(this));
    }

    @Override
    public void fromJson() {
        Gson gson = new Gson();
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = new BufferedReader(new FileReader("Artikli.json"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Artikli i = gson.fromJson(bufferedReader, Artikli.class);
        this.seznam = i.seznam;
    }
}
