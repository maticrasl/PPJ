package com.company;

import DAO.MySqlArticle;
import DAO.MySqlCompany;
import DAO.MySqlInvoice;
import database.DBHelper;

import java.io.*;
import java.math.BigInteger;
import java.sql.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        // write your code here
        Podjetje P1 = new Podjetje("Talum", "+38630 808 888", "info@talum.si", 45645645, Long.valueOf("6225480070"), true);
        Podjetje P2 = new Podjetje("Perutnina Ptuj d.d.", "+38630 708 888", "info@pp.si", 45655645, Long.valueOf("6255480070"), false);
        Podjetje P3 = new Podjetje("Henkel Maribor d.o.o.", "+3862 2222100", "henkel.slovenija@henkel.com", 58665765, Long.valueOf("6261752000"), true);

        Companies Podjetja = new Companies();
        /*Podjetja.add(P1);
        Podjetja.add(P2);
        Podjetja.add(P3);*/

        //Podjetja.toJson();              //Pisanje seznama podjetij v datoteko
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
/*
        VsiArtikli.addArtikel(Ar1, 1);
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

        VsiArtikli.toJson();                //Pisanje seznama artiklov v datoteko
        */
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

        Racun R1 = new Racun(VsiArtikli, Kuponi, "Sabina K.", Podjetja.get(2));
        R1.add(VsiArtikli.get(0), 1);
        R1.add(VsiArtikli.get(1), 1);
        R1.add(VsiArtikli.get(2), 1);
        R1.add(VsiArtikli.get(3), 1);
        R1.add(VsiArtikli.get(4), 1);
        R1.add(VsiArtikli.get(5), 1);
        R1.add(VsiArtikli.get(6), 1);
        R1.add(VsiArtikli.get(7), 1);
        R1.add(VsiArtikli.get(9), 0.8);
        R1.add(VsiArtikli.get(10), 2.400);
        R1.add(VsiArtikli.get(4), 4);
        R1.add(VsiArtikli.get(3), -1);
        R1.add(k1);
        R1.add(k3);
        R1.add(k5);


        System.out.print("Racun 1: \n");
        System.out.print(R1.toString());

        Racun R2 = new Racun(VsiArtikli, Kuponi, "GABROVEC LIDIJA", Podjetja.get(2), Podjetja.get(0));
        R2.add(VsiArtikli.get(8), 2);
        R2.add(k2);
        R2.add(k4);
        R2.add(k5);

        Racun R3 = new Racun(VsiArtikli, Kuponi, "Sabina K.", Podjetja.get(2), Podjetja.get(1));
        R3.add(VsiArtikli.get(0), 2);
        R3.add(k1);
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

        //napolniArtikleIzDatoteke("Grocery_UPC_Database.csv");
        //napolniArtikleIzDatoteke("en.openfoodfacts.org.products.csv");

        MySqlArticle MA = new MySqlArticle();
        Artikel a1 = MA.getByEAN("00000000000017");
        //Artikel a2 = MA.getById(UUID.fromString("78bf57fb-d553-4960-b34d-327199b37b9e"));
        System.out.println(a1.toString());
        //System.out.println(a2.toString());

        MySqlCompany MC = new MySqlCompany();
        MC.insert(Podjetja.get(0));
        MC.insert(Podjetja.get(1));
        MC.insert(Podjetja.get(2));

        Podjetje CC = MC.getByIme("Talum");
        System.out.println(CC.toString());

        MySqlInvoice MI = new MySqlInvoice();
        MI.insert(inv.get(0));
        MI.insert(inv.get(1));
        MI.insert(inv.get(2));
    }

    static void napolniArtikleIzDatoteke(String imeDatoteke) {
        System.out.println("Polnjenje baze iz datoteke:");
        Random r = new Random();
        try (BufferedReader br = new BufferedReader(new FileReader(imeDatoteke))) {
            try(Connection conn = DBHelper.getConnection();) {
                conn.setAutoCommit(false);
                String line;
                String[] parts;

                int i = 0;

                PreparedStatement ps = conn.prepareStatement("INSERT INTO mydb.article(id, EAN, ime, cenaBrezDDV, cenaZDDV, DDV, drzava, zaloga, deleted, created) VALUES (UUID_TO_BIN(UUID()), ?, ?, ?, ?, ?, ?, ?, FALSE, CURRENT_TIMESTAMP());");

                /* Grocery_UPC_Database.csv
                line = br.readLine();
                while((line = br.readLine()) != null) {
                    for(int j = 0; j < 1000 && line != null; j++) {
                        parts = line.split(";");
                        Artikel temp = new Artikel(parts[0], parts[1], (float)(r.nextInt(100000) + 1) / 100, 22);
                        temp.setKolicina(1000);
                        if (temp.getDrzava() != "Interni artikel" && temp.testEanCheckDigit()) {
                            ps.setString(1, temp.getEAN());
                            ps.setString(2, temp.getIme());
                            ps.setFloat(3, temp.getCenaBrezDDV());
                            ps.setFloat(4, temp.getCenaZDDV());
                            ps.setFloat(5, temp.getDDV());
                            ps.setString(6, temp.getDrzava());
                            ps.setFloat(7, temp.getKolicina());
                            ps.addBatch();
                            line = br.readLine();
                        }
                    }
                    ps.executeBatch();
                    conn.commit();
                    System.out.println(++i);
                }
                */

                /*en.openfoodfacts.org.products.csv*/
                line = br.readLine();
                while((line = br.readLine()) != null) {
                    for(int j = 0; j < 1000 && line != null; j++) {
                        parts = line.split("\t");
                        Artikel temp = new Artikel(parts[0], parts[7], (float)(r.nextInt(100000) + 1) / 100, 22);
                        temp.setKolicina(1000);
                        if (temp.getDrzava() != "Interni artikel" && temp.getEAN().length() <= 14 && temp.testEanCheckDigit()) {
                            ps.setString(1, temp.getEAN());
                            ps.setString(2, temp.getIme());
                            ps.setFloat(3, temp.getCenaBrezDDV());
                            ps.setFloat(4, temp.getCenaZDDV());
                            ps.setFloat(5, temp.getDDV());
                            ps.setString(6, temp.getDrzava());
                            ps.setFloat(7, temp.getKolicina());
                            ps.addBatch();
                            line = br.readLine();
                        }
                    }
                    ps.executeBatch();
                    conn.commit();
                    System.out.println(++i);
                }




            }
            catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

