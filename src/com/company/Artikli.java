package com.company;

import java.util.ArrayList;
import java.util.List;

public class Artikli {
    class posamezniArtikel {
        public Artikel artikel;
        public int kolicina;

        public posamezniArtikel(Artikel artikel, int kolicina) {
            this.artikel = artikel;
            this.kolicina = kolicina;
        }
    }
    private List<posamezniArtikel> seznam = new ArrayList<>();

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

    public String toString(boolean jeDavcniZavvezanec) {
        int longestDrzava = 0;
        int longestNaziv = 0;
        int longestId = 0;
        for (posamezniArtikel a : seznam) {
            if(a.artikel.getDrzava().length() > longestDrzava)
                longestDrzava = a.artikel.getDrzava().length();
            if(a.artikel.getIme().length() > longestNaziv)
                longestNaziv = a.artikel.getIme().length();
            if(String.valueOf(a.artikel.getId()).length() > longestId)
                longestId = String.valueOf(a.artikel.getId()).length();
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
        if(jeDavcniZavvezanec)
            izpis += "DDV\t\tCena z DDV\t";
        izpis += "Kolicina\n";
        for(int i = 0; i < seznam.size(); i++) {
            if (jeDavcniZavvezanec)
                izpis += izpisPosameznegaArtikla(seznam.get(i).artikel, longestDrzava, longestNaziv, longestId) +
                        '\t' + String.valueOf(seznam.get(i).kolicina) + '\n';
            else
                izpis += izpisPosameznegaArtiklaBrezDDV(seznam.get(i).artikel, longestDrzava, longestNaziv, longestId) +
                        '\t' + String.valueOf(seznam.get(i).kolicina) + '\n';
        }
        return izpis;
    }

    public List<posamezniArtikel> getSeznam() {
        return seznam;
    }

    public void setSeznam(List<posamezniArtikel> seznam) {
        this.seznam = seznam;
    }

    public Artikli() {

    }

    public Artikli(List<posamezniArtikel> seznam) {
        this.seznam = seznam;
    }

    public void addArtikel(Artikel a, int k) {
        for(int i = 0; i < seznam.size(); i++) {
            if(a.getId() == seznam.get(i).artikel.getId()) {
                seznam.get(i).kolicina += k;
                return;
            }
        }
        this.seznam.add(new posamezniArtikel(a, k));
    }

    public void setKolicina(int index, int k) {
        if(index < seznam.size())
            seznam.get(index).kolicina = k;
    }

    public int getKolicina(int index) {
        if(index < seznam.size())
            return seznam.get(index).kolicina;
        else return -1;
    }

    public void setCenaBrezDDV(int index, float c) {
        if(index < seznam.size())
            seznam.get(index).artikel.setCenaBrezDDV(c);
    }

    public float getCenaBrezDDV(int index) {
        if(index < seznam.size())
            return seznam.get(index).artikel.getCenaBrezDDV();
        else
            return -1.0f;
    }

    public void setCenaZDDV(int index, float c) {
        if(index < seznam.size())
            seznam.get(index).artikel.setCenaZDDV(c);
    }

    public float getCenaZDDV(int index) {
        if(index < seznam.size())
            return seznam.get(index).artikel.getCenaZDDV();
        else {
            System.out.println("NAPAKA! Indeks ne obstaja!");
            return 0.0f;
        }
    }

    public float getDDV(int index) {
        if(index < seznam.size())
            return seznam.get(index).artikel.getDDV();
        else {
            System.out.println("NAPAKA! Indeks ne obstaja!");
            return 0.0f;
        }
    }

    public long getId(int index) {
        if(index < seznam.size())
            return seznam.get(index).artikel.getId();
        else {
            System.out.println("NAPAKA! indeks ne obstaja!");
            return -1;
        }
    }
}
