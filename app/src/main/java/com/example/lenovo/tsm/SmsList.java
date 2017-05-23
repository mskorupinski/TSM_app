package com.example.lenovo.tsm;

/**
 * Created by Lenovo on 20.05.2017.
 */

public class SmsList {

    private String Grupa;

    private String Imie;

    private String Nazwisko;

    private int Aktywnosc;

    private String Nastepna_data;


    public SmsList(String Grupa, int Aktywnosc, String Nastepna_data){
        this.Grupa = Grupa;
        this.Aktywnosc = Aktywnosc;
        this.Nastepna_data = Nastepna_data;
    }

    public SmsList(String Imie, String Nazwisko, int Aktywnosc, String Nastepna_data){
        this.Imie = Imie;
        this.Nazwisko = Nazwisko;
        this.Aktywnosc = Aktywnosc;
        this.Nastepna_data = Nastepna_data;
    }
    public String getGrupa() {
        return Grupa;
    }

    public void setGrupa(String grupa) {
        Grupa = grupa;
    }

    public String getImie() {
        return Imie;
    }

    public void setImie(String imie) {
        Imie = imie;
    }

    public String getNazwisko() {
        return Nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        Nazwisko = nazwisko;
    }

    public int getAktywnosc() {
        return Aktywnosc;
    }

    public void setAktywnosc(int aktywnosc) {
        Aktywnosc = aktywnosc;
    }

    public String getNastepna_data() {
        return Nastepna_data;
    }

    public void setNastepna_data(String nastepna_data) {
        Nastepna_data = nastepna_data;
    }
}
