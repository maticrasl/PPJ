package com.company;

import java.math.BigInteger;

public class Podjetje implements Searchable {
    private String ime;
    private String telSt;
    private String email;
    private int davcnaSt;
    private long maticnaSt;
    private boolean davcniZavezanec;

    public Podjetje(String ime, String telSt, String email, int davcnaSt, long maticnaSt, boolean davcniZavezanec) {
        this.ime = ime;
        this.telSt = telSt;
        this.email = email;
        this.davcnaSt = davcnaSt;
        this.maticnaSt = maticnaSt;
        this.davcniZavezanec = davcniZavezanec;
    }

    @Override
    public String toString() {
        return "Podjetje{" +
                "ime='" + ime + '\'' +
                ", telSt='" + telSt + '\'' +
                ", email='" + email + '\'' +
                ", davcnaSt=" + davcnaSt +
                ", maticnaSt=" + maticnaSt +
                ", davcniZavezanec=" + davcniZavezanec +
                '}';
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getTelSt() {
        return telSt;
    }

    public void setTelSt(String telSt) {
        this.telSt = telSt;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getDavcnaSt() {
        return davcnaSt;
    }

    public void setDavcnaSt(int davcnaSt) {
        this.davcnaSt = davcnaSt;
    }

    public long getMaticnaSt() {
        return maticnaSt;
    }

    public void setMaticnaSt(long maticnaSt) {
        this.maticnaSt = maticnaSt;
    }

    public boolean isDavcniZavezanec() {
        return davcniZavezanec;
    }

    public void setDavcniZavezanec(boolean davcniZavezanec) {
        this.davcniZavezanec = davcniZavezanec;
    }

    public boolean search(String s) {
        if(ime.contains(s) || telSt.contains(s) || email.contains(s) || String.valueOf(davcnaSt).contains(s) ||
                String.valueOf(maticnaSt).contains(s) || String.valueOf(davcniZavezanec).contains(s))
            return true;
        return false;
    }
}

