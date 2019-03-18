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

    @Override
    public String toString() {
        String izpis = "ID\tEAN\tDrzava\tNaziv\tCena brez DDV\tDDV\tCena z DDV\tKolicina\n";
        for(int i = 0; i < seznam.size(); i++)
            izpis += seznam.get(i).artikel.toString() + '\t' + String.valueOf(seznam.get(i).kolicina) + '\n';
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
