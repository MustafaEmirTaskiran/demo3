package org.example.demo3;

public class Tasit extends Ilan {
    private Tasitturu tasitturu;
    private int modelYili;
    private String yakitTipi;
    private int kilometre;
    private String vitesTuru;
    private String marka;


    public Tasit(String baslik, double fiyat, String resimYolu, String kategori, String aciklama,
                     Tasitturu tasitTuru, String yakitTipi, int kilometre, String marka, int modelYili, String vitesTuru) {
        super(baslik, fiyat, resimYolu, kategori, aciklama);
        this.tasitturu = tasitTuru;
        this.kilometre = kilometre;
        this.vitesTuru = vitesTuru;
        this.modelYili = modelYili;
        this.yakitTipi = yakitTipi;
        this.marka = marka;
    }



    public int getModelYili() {
        return modelYili;
    }
    public void setModelYili(int modelYili) {
        this.modelYili = modelYili;
    }



    public String getYakitTipi() {
        return yakitTipi;
    }
    public void setYakitTipi(String yakitTipi) {
        this.yakitTipi = yakitTipi;
    }



    public int getKilometre() {
        return kilometre;
    }
    public void setKilometre(int kilometre) {
        this.kilometre = kilometre;
    }




    public String getVitesTuru() {
        return vitesTuru;
    }
    public void setVitesTuru(String vitesTuru) {
        this.vitesTuru = vitesTuru;
    }



    public String getMarka() {
        return marka;
    }
    public void setMarka(String marka) {
        this.marka = marka;
    }



    public Tasitturu getTasitturu() {
        return tasitturu;
    }
    public void setTasitturu(Tasitturu tasitturu) {
        this.tasitturu = tasitturu;
    }

}
