package com.company;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
	// write your code here
        Podjetje P1 = new Podjetje("Talum", "+38630 808 888", "info@talum.si", 45645645, Long.valueOf("6225480070"), true);
        Podjetje P2 = new Podjetje("Perutnina Ptuj d.d.", "+38630 708 888", "info@pp.si", 45655645, Long.valueOf("6255480070"), false);
        Podjetje P3 = new Podjetje("Henkel Maribor d.o.o.", "+3862 2222100", "henkel.slovenija@henkel.com", 58665765, Long.valueOf("6261752000"), true);
        System.out.println(P1.toString());
        System.out.println(P2.toString());
        System.out.println(P3.toString());

        Artikel A = new Artikel("1103526350767", "Zobna scetka", 12.0f, 22.0f);
        Artikel B = new Artikel("5226950246132", "Metla", 15.0f, 22.0f);
        Artikel C = new Artikel("1234987908454", "Nogavice", 2.99f, 22.0f);

        Artikel Ar1 = new Artikel("4729520501323", "CEDEVITA POMARANCA", 5.79f, 22);
        Artikel Ar2 = new Artikel("1234567890123", "BISKVIT JAFFA DU", 1.54f, 22);
        Artikel Ar3 = new Artikel("2345678901234", "JAJCA VD KAKOV.A", 1.47f, 22);
        Artikel Ar4 = new Artikel("3456789012345", "BISKVIT JAFFA", 0.59f, 22);
        Artikel Ar5 = new Artikel("4567890123456", "MOKA VD PŠENI.BE", 0.54f, 22);
        Artikel Ar6 = new Artikel("5678901234567", "MLEKO VD 3,5%MM", 0.72f, 22);
        Artikel Ar7 = new Artikel("6789012345678", "KRUH ŠTRUCA BELA", 0.62f, 22);
        Artikel Ar8 = new Artikel("7890123456789", "TUŠGEL AXE AFRIC", 2.48f, 22);

        Artikli A1 = new Artikli();
        A1.addArtikel(Ar1, 1);
        A1.addArtikel(Ar2, 1);
        A1.addArtikel(Ar3, 1);
        A1.addArtikel(Ar2, -1);
        A1.addArtikel(Ar4, 1);
        A1.addArtikel(Ar4, 1);
        A1.addArtikel(Ar5, 1);
        A1.addArtikel(Ar6, 1);
        A1.addArtikel(Ar6, 1);
        A1.addArtikel(Ar7, 1);
        A1.addArtikel(Ar8, 1);
        Racun R1 = new Racun(A1, "Sabina K.");
        Artikli A2 = new Artikli();
        A2.addArtikel(C, 4);
        A2.addArtikel(A, 2);
        A2.addArtikel(C, 3);
        System.out.print("Racun 1: \n");
        System.out.print(R1.toString());
       /* try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        Racun R2 = new Racun(A2, "Anton Črešnjak");
        //System.out.print("\nRacun 2: \n");
        //System.out.print(R2.toString());
        
        List<Racun> racuni = new ArrayList<>();
        racuni.add(R1);
        racuni.add(R2);
        for (Racun R : racuni) {
            System.out.print(R.toString());
        }
        System.out.println(A.checkDigit("6291041500213"));
        System.out.println(A.checkDigit("6291041500214"));
        System.out.println(A.search("scet"));
        System.out.println(A.search("23"));
    }
}

