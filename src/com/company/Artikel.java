package com.company;

public class Artikel implements Searchable {
    private long id;
    private String EAN;
    private String ime;
    private float cenaBrezDDV, cenaZDDV;
    private float DDV;
    private String drzava;
    static private long lastId = 0;

    public Artikel(String EAN, String ime, float cena, float DDV) {
        this.id = ++lastId;
        this.EAN = EAN;
        this.ime = ime;
        this.cenaBrezDDV = cena;
        this.DDV = DDV;
        this.cenaZDDV = cenaBrezDDV + cenaBrezDDV * (DDV / 100);
        this.cenaZDDV = (float)((int)(cenaZDDV *100f ))/100f;
        setDrzavaFromEAN();
    }

    public Artikel(String EAN, String ime, float cena, float DDV, String drzava) {
        this.id = ++lastId;
        this.EAN = EAN;
        this.ime = ime;
        this.cenaBrezDDV = cena;
        this.DDV = DDV;
        this.cenaZDDV = cenaBrezDDV + cenaBrezDDV * (DDV / 100);
        this.cenaZDDV = (float)((int)(cenaZDDV *100f ))/100f;
        this.drzava = drzava;
    }

    public Artikel() {
        this.id = ++lastId;
        this.EAN = "0000000000000";
        this.ime = "";
        this.cenaBrezDDV = 0.0f;
        this.DDV = 0.0f;
        this.cenaBrezDDV = 0.0f;
    }

    @Override
    public String toString() {
        String izpis = this.id + "\t" + this.EAN + '\t' + this.drzava + '\t' + this.ime + '\t' + String.valueOf(this.cenaBrezDDV) +
                '\t' + String.valueOf(this.DDV) + "%\t" + String.valueOf(this.cenaZDDV);
        return izpis;
    }

    public static boolean checkDigit(String crtnaKoda) {
        if(crtnaKoda.length() != 13)
            return false;
        String[] d = crtnaKoda.split("");
        short[] digits = new short[14];
        for(short i = 0; i < 13; i++) {
            digits[i] = Short.parseShort(d[i]);
            if(i % 2 == 1 && i < 12)
                digits[i] *= 3;
        }
        int sum = 0;
        for(int i = 0; i < 12; i++)
            sum += digits[i];
        short lastDigit = (short) ((10 - (sum % 10)) % 10);
        if(digits[12] == lastDigit)
            return true;
        return false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEAN() {
        return EAN;
    }

    public void setEAN(String EAN) {
        this.EAN = EAN;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public float getDDV() {
        return DDV;
    }

    public void setDDV(float DDV) {
        this.DDV = DDV;
    }

    public float getCenaBrezDDV() {
        return cenaBrezDDV;
    }

    public void setCenaBrezDDV(float cena) {
        this.cenaBrezDDV = cena;
    }

    public float getCenaZDDV() {
        return cenaZDDV;
    }

    public void setCenaZDDV(float cenaZDDV) {
        this.cenaZDDV = cenaZDDV;
    }

    public String getDrzava() {
        return drzava;
    }

    public void setDrzava(String drzava) {
        this.drzava = drzava;
    }

    public void setDrzavaFromEAN() {
        int kodaDrzave = 0;
        String[] e = EAN.split("");
        for(int i = 0; i < 3; i++) {
            kodaDrzave *= 10;
            kodaDrzave += Integer.parseInt(e[i]);
        }
        if(kodaDrzave <= 139)
            drzava = "ZDA";
        else if(kodaDrzave == 275)
            drzava = "Palestine";
        else if(kodaDrzave >= 300 && kodaDrzave <= 379)
            drzava = "France and Monaco";
        else if(kodaDrzave == 380)
            drzava = "Bulgaria";
        else if(kodaDrzave == 383)
            drzava = "Slovenia";
        else if(kodaDrzave == 385)
            drzava = "Croatia";
        else if(kodaDrzave == 387)
            drzava = "Bosnia and Herzegovina";
        else if(kodaDrzave == 389)
            drzava = "Montenegro";
        else if(kodaDrzave == 390)
            drzava = "Kosovo";
        else if(kodaDrzave >= 400 && kodaDrzave <= 440)
            drzava = "Germany";
        else if(kodaDrzave >= 450 && kodaDrzave <= 459)
            drzava = "Japan";
        else if(kodaDrzave >= 460 && kodaDrzave <= 469)
            drzava = "Russia";
        else drzava = "N/A";
    }

    public boolean search(String s) {
        if(String.valueOf(id).contains(s) || EAN.contains(s) || ime.contains(s) || String.valueOf(cenaZDDV).contains(s) ||
                String.valueOf(cenaBrezDDV).contains(s) || String.valueOf(DDV).contains(s))
            return true;
        return false;
    }
}
