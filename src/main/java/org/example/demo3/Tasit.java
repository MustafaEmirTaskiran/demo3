package org.example.demo3;

public class Tasit extends Ilan {
    private int modelYili;
    private String yakiTipi;
    private int kilometre;
    public Tasit(String baslik, String fiyat,String resimYolu, String yakiTipi, int modelYili,String altKategori ) {
        super(baslik, fiyat, resimYolu, "Taşıt", altKategori);
        this.modelYili = modelYili;
        this.yakiTipi = yakiTipi;
    }
    public int getModelYili() {
        return modelYili;
    }
}
