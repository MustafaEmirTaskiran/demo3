package org.example.demo3;

import java.util.PropertyResourceBundle;

public class Giyim extends Ilan{
    private GiyimEnum giyimturu;
    private String marka;
    private String asinmaMiktari;
    private String beden;
    private String cinsiyet;
    public Giyim(String baslik, double fiyat, String resimYolu, String kategori, String aciklama,
                 String marka ,String asinmaMiktari, String beden, String cinsiyet, GiyimEnum giyimturu) {
        super(baslik,fiyat,resimYolu,kategori,aciklama);
        this.giyimturu = giyimturu;
        this.marka = marka;
        this.asinmaMiktari = asinmaMiktari;
        this.beden = beden;
        this.cinsiyet = cinsiyet;
    }
    public GiyimEnum getGiyimturu() {
        return giyimturu;
    }
    public void setGiyimturu(GiyimEnum giyimturu) {
        this.giyimturu = giyimturu;
    }
    public String getMarka() {
        return marka;
    }
    public void setMarka(String marka) {
        this.marka = marka;
    }
    public String getAsinmaMiktari() {
        return asinmaMiktari;
    }
    public void setAsinmaMiktari(String asinmaMiktari) {
        this.asinmaMiktari = asinmaMiktari;
    }
    public String getBeden() {
        return beden;
    }
    public void setBeden(String beden) {
        this.beden = beden;
    }
    public String getCinsiyet() {
        return cinsiyet;
    }
    public void setCinsiyet(String cinsiyet) {
        this.cinsiyet = cinsiyet;
    }
}
