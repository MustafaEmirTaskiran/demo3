package org.example.demo3;

public class Teknoloji  extends Ilan {
    private String marka;
    private String model;
    private TeknolojituruEnum teknolojituru;

    public Teknoloji(String baslik, double fiyat, String resimYolu, String kategori, String aciklama
            , String marka, String model, TeknolojituruEnum teknolojituru) {
        super(baslik, fiyat, resimYolu, kategori, aciklama);
        this.marka = marka;
        this.model = model;
        this.teknolojituru = teknolojituru;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public TeknolojituruEnum getTeknolojituru() {
        return teknolojituru;
    }

    public void  setTeknolojituru(TeknolojituruEnum teknolojituru) {
        this.teknolojituru = teknolojituru;
    }


}
