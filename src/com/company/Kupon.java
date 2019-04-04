package com.company;

public class Kupon {
    private int tip;        //Tip kupona - 0 = 25% popust na celotni nakup, 1 = naključni popust, 2 = popust na določen izdelek
    private double popust;
    private String EANIzdelka;
    private String EAN;

    public Kupon(int tip) {
        this.tip = tip;
        this.EANIzdelka = "0000000000000";
        setEAN(EANIzdelka);
    }

    public Kupon(int tip, String EANIzdelka) {
        this.tip = tip;
        this.EANIzdelka = EANIzdelka;
        setEAN(EANIzdelka);
    }

    public int getTip() {
        return tip;
    }

    public void setTip(int tip) {
        this.tip = tip;
    }

    public double getPopust() {
        return popust;
    }

    public String getEANIzdelka() {
        return EANIzdelka;
    }

    public void setEANIzdelka(String EANIzdelka) {
        this.EANIzdelka = EANIzdelka;
    }

    public String getEAN() {
        return EAN;
    }

    public String setEanCheckDigit() {
        String[] d = EAN.split("");
        int[] digits = new int[13];
        for(short i = 0; i < 13; i++) {
            digits[i] = Integer.parseInt(d[i]);
            if(i % 2 == 1 && i < 12)
                digits[i] *= 3;
        }
        int sum = 0;
        for(int i = 0; i < 12; i++)
            sum += digits[i];
        short lastDigit = (short) ((10 - (sum % 10)) % 10);
        d[12] = String.valueOf(lastDigit);
        String noviEAN = "";
        for(int i = 0; i < 13; i++)
            noviEAN += d[i];
        //EAN = noviEAN;

        return noviEAN;
    }

    public String getPopustKodaIzdelka() {
        int interniIzdelek = 0;
        for(int i = 0; i < 7; i++) {
            interniIzdelek *= 10;
            interniIzdelek += EANIzdelka.charAt(i) - 48;
        }
        return String.valueOf(interniIzdelek);
    }

    public void generirajNakljucniPopust(double min, double max) {
        this.popust = (double)((int)(min + Math.random() * (max - min) * 100)) / 100;
    }

    public void setEAN(String EAN) {
        String sestavljen = "981";
        if(tip < 10)
            sestavljen += '0';
        sestavljen += String.valueOf(tip);
        if(tip == 0)
            popust = 0.25;
        if(tip == 2){           //Popust na določen izdelek
            sestavljen += getPopustKodaIzdelka();
            popust = 0.25;
        }
        else
            sestavljen += "0000000";
        if(tip == 1)
            generirajNakljucniPopust(0.10, 0.99);
        sestavljen += '0';
        this.EAN = sestavljen;

        this.EAN = setEanCheckDigit();
    }
}
