package com.company;

import java.util.Date;

public class Racun implements Searchable {
    private Artikli artikli;
    private double cenaBrezDDV;
    private double cenaZDDV;
    private long id;
    private String prodajalec;
    private Date datum;
    private int davcnaStPodjetja;
    private boolean podjetjeDavcniZavezanec;
    static private long lastId = 0;

    public Racun(Artikli artikli, String prodajalec, Date datum) {
        this.artikli = artikli;
        this.id = ++lastId;
        this.prodajalec = prodajalec;
        this.datum = datum;
        izracunajCeno();
    }

    public Racun(Artikli artikli, String prodajalec, Date datum, Podjetje podjetje) {
        this.artikli = artikli;
        this.id = ++lastId;
        this.prodajalec = prodajalec;
        this.datum = datum;
        this.davcnaStPodjetja = podjetje.getDavcnaSt();
        this.podjetjeDavcniZavezanec = podjetje.isDavcniZavezanec();
        izracunajCeno();
    }

    public Racun(Artikli artikli, String prodajalec) {
        this.artikli = artikli;
        this.id = ++lastId;
        this.prodajalec = prodajalec;
        this.datum = new Date();
        izracunajCeno();
    }

    public Racun(Artikli artikli, String prodajalec, Podjetje podjetje) {
        this.artikli = artikli;
        this.id = ++lastId;
        this.prodajalec = prodajalec;
        this.datum = new Date();
        this.davcnaStPodjetja = podjetje.getDavcnaSt();
        this.podjetjeDavcniZavezanec = podjetje.isDavcniZavezanec();
        izracunajCeno();
    }

    public Racun() {
        this.artikli = new Artikli();
        this.cenaBrezDDV = 0.0;
        this.cenaZDDV = 0.0;
        this.id = ++lastId;
        this.prodajalec = "";
        this.datum = new Date();
    }

    public Racun(Racun R) {
        this.artikli = R.artikli;
        this.cenaBrezDDV = R.cenaBrezDDV;
        this.cenaZDDV = R.cenaZDDV;
        this.id = ++lastId;
        this.prodajalec = R.prodajalec;
        this.datum = R.datum;
        this.davcnaStPodjetja = R.davcnaStPodjetja;
        this.podjetjeDavcniZavezanec = R.podjetjeDavcniZavezanec;
        setLastId(getLastId());
    }

    @Override
    public String toString() {
        String izpis = "Racun st. " + id + '\n';
        izpis += artikli.toString();
        izpis += "Cena brez DDV: " + cenaBrezDDV + '\n';
        izpis += "Cena z DDV: " + cenaZDDV + '\n';
        if(this.davcnaStPodjetja != 0) {
            izpis += "Davcna stevilka podjetja: " + davcnaStPodjetja + '\n';
            if (this.podjetjeDavcniZavezanec)
                izpis += "Podjetje JE davcni zavezanec.\n";
            else
                izpis += "Podjetje NI davcni zavezanec.\n";
        }
        izpis += "\nProdajalec: " + prodajalec;
        izpis += "\nDatum: " + datum.toString();
        izpis += "\n\nHvala za vas nakup!\n\n";
        return izpis;
    }

    public Artikli getArtikli() {
        return artikli;
    }

    public void setArtikli(Artikli artikli) {
        this.artikli = artikli;
    }

    public double getCenaBrezDDV() {
        return cenaBrezDDV;
    }

    public void setCenaBrezDDV(double cenaBrezDDV) {
        this.cenaBrezDDV = cenaBrezDDV;
    }

    public double getCenaZDDV() {
        return cenaZDDV;
    }

    public void setCenaZDDV(double cenaZDDV) {
        this.cenaZDDV = cenaZDDV;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProdajalec() {
        return prodajalec;
    }

    public void setProdajalec(String prodajalec) {
        this.prodajalec = prodajalec;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public static long getLastId() {
        return lastId;
    }

    public static void setLastId(long lastId) {
        Racun.lastId = lastId;
    }

    public void izracunajCeno() {
        cenaBrezDDV = 0.0;
        cenaZDDV = 0.0;
        double tempCena;
        for(int i = 0; i < artikli.getSeznam().size(); i++) {
            cenaBrezDDV += artikli.getCenaBrezDDV(i) * artikli.getKolicina(i);
            cenaZDDV += artikli.getCenaZDDV(i) * artikli.getKolicina(i);
        }
        this.cenaBrezDDV = (float)((int)(cenaBrezDDV *100f ))/100f;
        this.cenaZDDV = (float)((int)(cenaZDDV *100f ))/100f;
    }
    public boolean search(String s) {
        if(String.valueOf(cenaBrezDDV).contains(s) || String.valueOf(cenaZDDV).contains(s) ||
                String.valueOf(id).contains(s) || prodajalec.contains(s) || String.valueOf(datum).contains(s) || String.valueOf(davcnaStPodjetja).contains(s))
            return true;
        return false;
    }
}
