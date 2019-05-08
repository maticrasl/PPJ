package com.company;

import database.DBHelper;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {



        try(Connection conn = DBHelper.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM sakila.film");) {

            ResultSet rs =  ps.executeQuery();

            while (rs.next()) {

                String title = rs.getString("title");
                System.out.println(title);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        // write your code here
        Podjetje P1 = new Podjetje("Talum", "+38630 808 888", "info@talum.si", 45645645, Long.valueOf("6225480070"), true);
        Podjetje P2 = new Podjetje("Perutnina Ptuj d.d.", "+38630 708 888", "info@pp.si", 45655645, Long.valueOf("6255480070"), false);
        Podjetje P3 = new Podjetje("Henkel Maribor d.o.o.", "+3862 2222100", "henkel.slovenija@henkel.com", 58665765, Long.valueOf("6261752000"), true);

        Companies Podjetja = new Companies();
        /*Podjetja.add(P1);
        Podjetja.add(P2);
        Podjetja.add(P3);

        Podjetja.toJson();*/              //Pisanje seznama podjetij v datoteko
        Podjetja.fromJson();            //Branje seznama podjetij iz datoteke

        Artikel A = new Artikel("1103526350767", "Zobna scetka", 12.0f, 22.0f);
        Artikel B = new Artikel("5226950246132", "Metla", 15.0f, 22.0f);
        Artikel C = new Artikel("1234987908454", "Nogavice", 2.99f, 22.0f);

        Artikel Ar1 = new Artikel("4729520501323", "CEDEVITA POMARANCA", 5.79f, 22);
        Artikel Ar2 = new Artikel("1234567890123", "BISKVIT JAFFA DU", 1.54f, 22);
        Artikel Ar3 = new Artikel("3345678901234", "JAJCA VD KAKOV.A", 1.47f, 22);
        Artikel Ar4 = new Artikel("3456789012345", "BISKVIT JAFFA", 0.59f, 22);
        Artikel Ar5 = new Artikel("4567890123456", "MOKA VD PŠENI.BE", 0.54f, 22);
        Artikel Ar6 = new Artikel("5678901234567", "MLEKO VD 3,5%MM", 0.72f, 22);
        Artikel Ar7 = new Artikel("6789012345678", "KRUH ŠTRUCA BELA", 0.62f, 22);
        Artikel Ar8 = new Artikel("7890123456789", "TUŠGEL AXE AFRIC", 2.48f, 22);
        Artikel Ar9 = new Artikel("3165140520805", "SVEDER HSS/E", 3.79f, 22);
        Artikel Ar10 = new Artikel("2001001", "", 3.79f, 22);
        Artikel Ar11 = new Artikel("2012002", "", 3.79f, 22);

        Artikli VsiArtikli = new Artikli();

        /*VsiArtikli.addArtikel(Ar1, 1);
        VsiArtikli.addArtikel(Ar2, 1);
        VsiArtikli.addArtikel(Ar3, 1);
        VsiArtikli.addArtikel(Ar2, 1);
        VsiArtikli.addArtikel(Ar4, 1);
        VsiArtikli.addArtikel(Ar4, 1);
        VsiArtikli.addArtikel(Ar5, 1);
        VsiArtikli.addArtikel(Ar6, 1);
        VsiArtikli.addArtikel(Ar6, 1);
        VsiArtikli.addArtikel(Ar7, 1);
        VsiArtikli.addArtikel(Ar8, 1);
        VsiArtikli.addArtikel(Ar9, 1);
        VsiArtikli.addArtikel(Ar10, 1);
        VsiArtikli.addArtikel(Ar11, 1);
        VsiArtikli.toJson();*/                //Pisanje seznama artiklov v datoteko
        VsiArtikli.fromJson();              //Branje seznama artiklov iz datoteke

        //KUPONI
        Kupon k1 = new Kupon(0);
        Kupon k2 = new Kupon(1);
        Kupon k3 = new Kupon(2, "4729520501323");
        Kupon k4 = new Kupon(2, "3165140520805");
        Kupon k5 = new Kupon(2, "2001001");
        List<Kupon> Kuponi = new ArrayList<>();
        Kuponi.add(k1);
        Kuponi.add(k2);
        Kuponi.add(k3);
        Kuponi.add(k4);
        Kuponi.add(k5);

        Racun R1 = new Racun(VsiArtikli, Kuponi, "Sabina K.");
        R1.add(Ar1.getEAN(), 1);
        R1.add(Ar2.getEAN(), 1);
        R1.add(Ar3.getEAN(), 1);
        R1.add(Ar4.getEAN(), 1);
        R1.add(Ar5.getEAN(), 1);
        R1.add(Ar6.getEAN(), 1);
        R1.add(Ar7.getEAN(), 1);
        R1.add(Ar8.getEAN(), 1);
        R1.add(Ar10.getEAN(), 0.8);
        R1.add(Ar11.getEAN(), 2.400);
        R1.add(Ar5.getEAN(), 4);
        R1.add(Ar4.getEAN(), -1);
        R1.add(k1.getEAN(), 1);
        R1.add(k3.getEAN(), 1);
        R1.add(k5.getEAN(), 1);


        System.out.print("Racun 1: \n");
        System.out.print(R1.toString());

        Racun R2 = new Racun(VsiArtikli, Kuponi, "GABROVEC LIDIJA", Podjetja.get(0));
        R2.add(Ar9.getEAN(), 2);
        R2.add(k2.getEAN(), 1);
        R2.add(k4.getEAN(), 1);
        R2.add(k5.getEAN(), 1);

        Racun R3 = new Racun(VsiArtikli, Kuponi, "Sabina K.", Podjetja.get(1));
        R3.add(Ar1.getEAN(), 2);
        R3.add(k1.getEAN(), 1);
        //System.out.print("\nRacun 2: \n");
        //System.out.print(R2.toString());
        
        List<Racun> racuni = new ArrayList<>();
        racuni.add(R1);
        racuni.add(R2);
        racuni.add(R3);
        for (Racun R : racuni) {
            System.out.print(R.toString());
        }

        Invoices inv = new Invoices();
        /*inv.add(R1);
        inv.add(R2);
        inv.add(R3);
        inv.toJson();*/

        inv.fromJson();
        System.out.print(inv.get(0).toString());

        System.out.print("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");

        //System.out.println("Prava koda: " + A.checkDigit("6291041500213"));
        //System.out.println("Napacna koda: " + A.checkDigit("6291041500214"));
        //System.out.println("Prava koda: " + A.checkDigit("9789616555104"));

        //System.out.println(A.search("scet"));
        //System.out.println(A.search("zobna"));
        //System.out.println(A.search("Zobna"));
    }
}

