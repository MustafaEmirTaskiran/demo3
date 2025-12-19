package org.example.demo3;

import java.util.Date;

public class Konut extends Ilan{

    private String odaSayisi;
    private int yapimYili;
    private KonutturuEnum konuttipi;
    private String sehir;
    private  String ilce;
    private String mahalle;
    private String sokak;





    public Konut(String baslik, double fiyat, String resimYolu, String kategori, String odaSayisi, String aciklama,
                 String sehir, String ilce, String mahalle, String sokak,KonutturuEnum konuttipi,int yapimYili) {
        super(baslik,fiyat,resimYolu,kategori,aciklama);
        this.odaSayisi = odaSayisi;
        this.yapimYili = yapimYili;
        this.konuttipi = konuttipi;
        this.sehir = sehir;
        this.ilce = ilce;
        this.mahalle = mahalle;
        this.sokak = sokak;
    }
    public String getOdaSayisi() {
        return odaSayisi;
    }
    public void setOdaSayisi(String odaSayisi) {
        this.odaSayisi = odaSayisi;
    }

    public int getYapimYili() {
        return yapimYili;
    }
    public void setYapimYili(int yapimYili) {
        this.yapimYili = yapimYili;
    }

    public KonutturuEnum getKonuttipi() {
        return konuttipi;
    }
    public void setKonuttipi(KonutturuEnum konuttipi) {
        this.konuttipi = konuttipi;
    }

    public String getSehir() {
        return sehir;
    }
    public void setSehir(String sehir) {
        this.sehir = sehir;
    }
    public String getIlce() {
        return ilce;
    }
    public void setIlce(String ilce) {
        this.ilce = ilce;
    }
    public String getMahalle() {
        return mahalle;
    }
    public void setMahalle(String mahalle) {
        this.mahalle = mahalle;
    }
    public String getSokak() {
        return sokak;
    }
    public void setSokak(String sokak) {
        this.sokak = sokak;
    }
}

