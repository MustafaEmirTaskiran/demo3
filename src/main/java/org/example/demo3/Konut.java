package org.example.demo3;

public class Konut extends Ilan{
    private String odaSayisi;
    private int metreKare;
    public Konut(String baslik, String fiyat, String resimYolu, String altKategori, String odaSayisi, int metreKare) {
        super(baslik, fiyat, resimYolu, "Konut", altKategori);
        this.odaSayisi = odaSayisi;
        this.metreKare = metreKare;
    }
}
