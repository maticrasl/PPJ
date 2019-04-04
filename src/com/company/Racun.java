package com.company;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Racun implements Searchable {
    private Artikli vsiArtikli;
    private Artikli mojiArtikli;
    private List<Kupon> vsiKuponi;
    private List<Kupon> mojiKuponi;
    private double cenaBrezDDV;
    private double cenaZDDV;
    private double popust;
    private long id;
    private String prodajalec;
    private Date datum;
    private int davcnaStPodjetja;
    private boolean podjetjeDavcniZavezanec;
    private boolean originalRacun;
    static private long lastId = 0;

    public Racun(Artikli vsiArtikli, List<Kupon> vsiKuponi, String prodajalec, Date datum) {
        this(vsiArtikli, vsiKuponi, prodajalec);
        this.datum = datum;
    }

    public Racun(Artikli vsiArtikli, List<Kupon> vsiKuponi, String prodajalec, Date datum, Podjetje podjetje) {
        this(vsiArtikli, vsiKuponi, prodajalec, datum);
        this.davcnaStPodjetja = podjetje.getDavcnaSt();
        this.podjetjeDavcniZavezanec = podjetje.isDavcniZavezanec();
    }

    public Racun(Artikli vsiArtikli, List<Kupon> vsiKuponi, String prodajalec) {
        this.vsiArtikli = vsiArtikli;
        this.mojiArtikli = new Artikli();
        this.vsiKuponi = vsiKuponi;
        this.mojiKuponi = new ArrayList<>();
        this.id = ++lastId;
        this.prodajalec = prodajalec;
        this.datum = new Date();
        this.originalRacun = false;
    }

    public Racun(Artikli vsiArtikli, List<Kupon> vsiKuponi, String prodajalec, Podjetje podjetje) {
        this(vsiArtikli, vsiKuponi, prodajalec);
        this.davcnaStPodjetja = podjetje.getDavcnaSt();
        this.podjetjeDavcniZavezanec = podjetje.isDavcniZavezanec();
        this.originalRacun = true;
    }

    public Racun() {
        this.vsiArtikli = new Artikli();
        this.mojiArtikli = new Artikli();
        this.vsiKuponi = new ArrayList<>();
        this.mojiKuponi = new ArrayList<>();
        this.cenaBrezDDV = 0.0;
        this.cenaZDDV = 0.0;
        this.id = ++lastId;
        this.prodajalec = "";
        this.datum = new Date();
        this.originalRacun = false;
    }

    public Racun(Racun R) {
        this.vsiArtikli = R.vsiArtikli;
        this.mojiArtikli = R.mojiArtikli;
        this.vsiKuponi = R.vsiKuponi;
        this.mojiKuponi = R.mojiKuponi;
        this.cenaBrezDDV = R.cenaBrezDDV;
        this.cenaZDDV = R.cenaZDDV;
        this.id = ++lastId;
        this.prodajalec = R.prodajalec;
        this.datum = R.datum;
        this.davcnaStPodjetja = R.davcnaStPodjetja;
        this.podjetjeDavcniZavezanec = R.podjetjeDavcniZavezanec;
        setLastId(getLastId());
        this.originalRacun = R.isOriginalRacun();
    }

    public void add(String EAN, double kolicina) {
        String vrstaIzdelka = "";
        String iskaniEAN;
        for(int i = 0; i < 3; i++)
            vrstaIzdelka += EAN.charAt(i);
        if(Integer.parseInt(vrstaIzdelka) >= 200 && Integer.parseInt(vrstaIzdelka) <= 299) {        //Interni izdelek
            for(int i = 3; i < 7; i++) {
                vrstaIzdelka += EAN.charAt(i);
            }
            for(int i = 0; i < vsiArtikli.count(); i++) {
                if(vsiArtikli.getEAN(i).contains(vrstaIzdelka)) {
                    mojiArtikli.addArtikel(vsiArtikli.get(i), kolicina);
                    break;
                }
            }
        }
        else if(Integer.parseInt(vrstaIzdelka) == 981) {        //Kupon
            for(int i = 0; i < vsiKuponi.size(); i++) {
                if(vsiKuponi.get(i).getEAN() == EAN) {
                    mojiKuponi.add(vsiKuponi.get(i));
                }
            }
        }
        else {      //Navaden izdelek
            int stVsehArtiklov = vsiArtikli.count();
            for(int i = 0; i < stVsehArtiklov; i++) {
                if(vsiArtikli.getEAN(i) == EAN) {
                    mojiArtikli.addArtikel(vsiArtikli.get(i), kolicina);
                    break;
                }
            }
        }
    }

    @Override
    public String toString() {
        izracunajCeno();
        String izpis = "Racun st. " + id + '\n';
        if(originalRacun == true && podjetjeDavcniZavezanec == false)
            izpis += mojiArtikli.toString(false);
        else
            izpis += mojiArtikli.toString(true);
        boolean kuponNaCelotniNakup = false;
        double popust = 0.0;
        if(mojiKuponi.size() > 0) {
            izpis += "\nKUPONI:\n";
            for(int i = 0; i < mojiKuponi.size(); i++) {
                if((mojiKuponi.get(i).getTip() == 0 || mojiKuponi.get(i).getTip() == 1) && !kuponNaCelotniNakup) {       //Fiksen kupon na celotni nakup
                    popust += (double)((int)(cenaBrezDDV * mojiKuponi.get(i).getPopust() * 100)) / 100;
                    izpis += "\t" + String.valueOf(mojiKuponi.get(i).getPopust() * 100) + " % popusta na celotni nakup.\n";
                    kuponNaCelotniNakup = true;
                }
                else if(mojiKuponi.get(i).getTip() == 2) {  //Kupon na določen izdelek
                    String EANIzdelka = mojiKuponi.get(i).getEANIzdelka();
                    for(int j = 0; j < mojiArtikli.count(); j++) {
                        if(mojiArtikli.getEAN(j).contains(EANIzdelka)) {
                            izpis += "\t" + String.valueOf(mojiKuponi.get(i).getPopust() * 100) + " % popusta na " +
                                    mojiArtikli.getIme(j) + "\n";
                        }
                    }
                }
            }
        }
        izpis += "\nCena brez DDV:\t" + String.format("%.02f", cenaBrezDDV) + '\n';
        if(originalRacun == false || podjetjeDavcniZavezanec == true)
            izpis += "Cena z DDV:\t\t" + String.format("%.02f", cenaZDDV) + '\n';
        if(this.originalRacun) {
            izpis += "\nOriginal racun:\n";
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

    public Artikli getMojiArtikli() {
        return mojiArtikli;
    }

    public void setMojiArtikli(Artikli mojiArtikli) {
        this.mojiArtikli = mojiArtikli;
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

    public boolean isOriginalRacun() {
        return originalRacun;
    }

    public void setOriginalRacun(boolean originalRacun) {
        this.originalRacun = originalRacun;
    }

    public void izracunajCeno() {
        cenaBrezDDV = 0.0;
        cenaZDDV = 0.0;
        double tempCena;
        for(int i = 0; i < mojiArtikli.count(); i++) {
            cenaBrezDDV += mojiArtikli.getCenaBrezDDV(i) * mojiArtikli.getKolicina(i);
            cenaZDDV += mojiArtikli.getCenaZDDV(i) * mojiArtikli.getKolicina(i);
        }
        this.cenaBrezDDV = (float)((int)(cenaBrezDDV *100f ))/100f;
        this.cenaZDDV = (float)((int)(cenaZDDV *100f ))/100f;

        double popustZDDV = 0;

        boolean kuponNaCeliNakup = false;
        String EANIzdelka;
        for(int i = 0; i < mojiKuponi.size(); i++) {
            if((mojiKuponi.get(i).getTip() == 0 || mojiKuponi.get(i).getTip() == 1) && !kuponNaCeliNakup) {        //Kupon na celi nakup - fiksni ali naključni
                popust += (double) ((int) (cenaBrezDDV * mojiKuponi.get(i).getPopust() * 100)) / 100;
                kuponNaCeliNakup = true;
                popustZDDV += (double) ((int) (cenaZDDV * mojiKuponi.get(i).getPopust() * 100)) / 100;
            }
            else if(mojiKuponi.get(i).getTip() == 2) {              //Kupon na določeni artikel
                EANIzdelka = mojiKuponi.get(i).getEANIzdelka();
                for(int j = 0; j < mojiArtikli.count(); j++) {
                    if(mojiArtikli.getEAN(j).contains(EANIzdelka)) {
                        popust += (double) ((int)(mojiArtikli.getKolicina(j) * mojiArtikli.getCenaBrezDDV(j) * mojiKuponi.get(i).getPopust() * 100)) / 100;
                        popustZDDV += (double) ((int)(mojiArtikli.getKolicina(j) * mojiArtikli.getCenaZDDV(j) * mojiKuponi.get(i).getPopust() * 100)) / 100;
                    }
                }
            }
        }
        cenaZDDV -= popustZDDV;
        cenaBrezDDV -= popust;
        if(cenaBrezDDV < 0.0)
            cenaBrezDDV = 0.0;
        if(cenaZDDV < 0.0)
            cenaZDDV = 0.0;
    }
    public boolean search(String s) {
        if(String.valueOf(cenaBrezDDV).contains(s) || String.valueOf(cenaZDDV).contains(s) ||
                String.valueOf(id).contains(s) || prodajalec.contains(s) || String.valueOf(datum).contains(s) || String.valueOf(davcnaStPodjetja).contains(s))
            return true;
        return false;
    }
}
