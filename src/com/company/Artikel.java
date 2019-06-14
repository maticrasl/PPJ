package com.company;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.UUID;

public class Artikel implements Searchable {
    private byte[] id;;
    private String EAN;
    private String ime;
    private float cenaBrezDDV, cenaZDDV;
    private float DDV;
    private String drzava;
    private float kolicina;

    public Artikel(String EAN, String ime, float cena, float DDV) {
        setUUID();
        this.EAN = EAN;
        this.ime = ime;
        this.cenaBrezDDV = cena;
        this.DDV = DDV;
        this.kolicina = 0.0f;
        this.cenaZDDV = cenaBrezDDV + cenaBrezDDV * (DDV / 100);
        this.cenaZDDV = (float)((int)(cenaZDDV * 100f)) / 100f;
        setDrzavaFromEAN();
    }

    public Artikel(String EAN, String ime, float cena, float DDV, float kolicina) {
        this(EAN, ime, cena, DDV);
        this.kolicina = kolicina;
    }

    public Artikel() {
        setUUID();
        this.EAN = "0000000000000";
        this.ime = "";
        this.cenaBrezDDV = 0.0f;
        this.DDV = 0.0f;
        this.cenaBrezDDV = 0.0f;
        this.kolicina = 0.0f;
        this.drzava = "N/A";
    }

    private void setUUID() {
        UUID uuid = UUID.randomUUID();
        this.id = new byte[16];
        ByteBuffer.wrap(this.id).order(ByteOrder.BIG_ENDIAN).putLong(uuid.getMostSignificantBits()).putLong(uuid.getLeastSignificantBits());
    }

    @Override
    public String toString() {
        String izpis = this.EAN + '\t' + this.drzava + '\t' + this.ime + '\t' + String.valueOf(this.cenaBrezDDV) +
                "\t\t\t" + String.valueOf(this.DDV) + "%\t" + String.valueOf(this.cenaZDDV) + "\t\t";
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

    public byte[] getId() {
        return id;
    }

    public void setId(byte[] id) {
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

    public float getKolicina() {
        return kolicina;
    }

    public void setKolicina(float kolicina) {
        this.kolicina = kolicina;
        if(this.EAN.charAt(0) == 2)
            setEANFromTeza();
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
        else if(kodaDrzave >= 200 && kodaDrzave <= 299) {
            drzava = "Interni artikel";
            if(EAN.length() <= 7) {
                for (int i = EAN.length(); i < 13; i++)
                    EAN += '0';
                setEANFromTeza();
                getTezaFromEAN();
            }
            else
                getTezaFromEAN();
        }
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

    public void getTezaFromEAN() {
        int oddelek = 0;
        int tipArtikla = 0;
        float tezaArtikla = 0.0f;
        String[] e = EAN.split("");
        for(int i = 0; i < 3; i++) {
            oddelek *= 10;
            oddelek += Integer.parseInt(e[i]);
        }
        for(int i = 3; i < 7; i++) {
            tipArtikla *= 10;
            tipArtikla += Integer.parseInt(e[i]);
        }
        for(int i = 7; i < 12; i++) {
            tezaArtikla *= 10.0f;
            tezaArtikla += Integer.parseInt(e[i]);
        }
        tezaArtikla /= 1000;
        switch(oddelek) {
            case 200:       //Sadje
                switch(tipArtikla) {
                    case 1000:      //Jabolka
                        this.ime = "Jabolka";
                        break;
                    case 1001:
                        this.ime = "Hruske";
                        break;
                    case 1002:
                        this.ime = "Limone";
                        break;
                    case 1003:
                        this.ime = "Lubenica";
                        break;
                }
            case 201:       //Zelenjava
                switch(tipArtikla) {
                    case 2000:
                        this.ime = "Paprika";
                        break;
                    case 2001:
                        this.ime = "Paradiznik";
                        break;
                    case 2002:
                        this.ime = "Kumare";
                        break;
                    case 2003:
                        this.ime = "Korenje";
                        break;
                }
            case 202:       //Meso
                switch(tipArtikla) {
                    case 3000:
                        this.ime = "Piscancje prsi";
                        break;
                    case 3001:
                        this.ime = "Mleto gov. meso";
                        break;
                    case 3002:
                        this.ime = "Krvavice";
                        break;
                }
        }
        kolicina = tezaArtikla;
    }

    public String setEanCheckDigit() {
        if(EAN.length() < 14)
            for(int i = EAN.length(); i < 14; i++)
                EAN = "0" + EAN;

        String[] d = EAN.split("");
        int[] digits = new int[14];
        for(short i = 0; i < 14; i++) {
            digits[i] = ((int)EAN.charAt(i)) - 48;
            if(i % 2 == 1 && i < 13)
                digits[i] *= 3;
        }
        int sum = 0;
        for(int i = 0; i < 13; i++)
            sum += digits[i];
        int lastDigit = (10 - (sum % 10)) % 10;
        d[12] = String.valueOf(lastDigit);
        String noviEAN = "";
        for(int i = 0; i < 14; i++)
            noviEAN += d[i];
        EAN = noviEAN;

        return noviEAN;
    }

    public boolean testEanCheckDigit() {
        if(EAN.length() < 14)
            for(int i = EAN.length(); i < 14; i++)
                EAN = "0" + EAN;

        String[] d = EAN.split("");
        int[] digits = new int[14];
        for(short i = 0; i < 14; i++) {
            digits[i] = ((int)EAN.charAt(i)) - 48;
            if(i % 2 == 0 && i < 13)
                digits[i] *= 3;
        }
        int sum = 0;
        for(int i = 0; i < 13; i++)
            sum += digits[i];
        int lastDigit = (10 - (sum % 10)) % 10;

        if(lastDigit == EAN.charAt(13) - 48)
            return true;
        return false;
    }

    public void setEANFromTeza() {
        for(int i = EAN.length(); i < 13; i++)
            EAN += '0';
        String[] d = EAN.split("");
        short[] digits = new short[5];
        int TEZA = (int)(kolicina * 1000);
        for(int i = 11; i > 6; i--) {
            d[i] = String.valueOf(TEZA % 10);
            TEZA /= 10;
        }
        String noviEAN = "";
        for(int i = 0; i < 13; i++)
            noviEAN += d[i];
        EAN = noviEAN;
        EAN = setEanCheckDigit();
    }

    @Override
    public boolean search(String s) {
        if(String.valueOf(id).contains(s) || EAN.contains(s) || ime.contains(s) || String.valueOf(cenaZDDV).contains(s) ||
                String.valueOf(cenaBrezDDV).contains(s) || String.valueOf(DDV).contains(s))
            return true;
        return false;
    }

    public static String getGuidFromByteArray(byte[] bytes) {
        ByteBuffer bb = ByteBuffer.wrap(bytes);
        long high = bb.getLong();
        long low = bb.getLong();
        UUID uuid = new UUID(high, low);
        return uuid.toString();
    }
}
