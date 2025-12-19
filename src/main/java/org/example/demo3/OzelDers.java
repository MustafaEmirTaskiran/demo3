package org.example.demo3;

public class OzelDers extends Ilan{
    private OzeldersEnum ozeldersTuru;
    private String egitimSeviyesi;

    public OzelDers(String baslik, double fiyat, String resimYolu,String aciklama ,String kategori, OzeldersEnum ozeldersTuru, String egitimSeviyesi) {
        super(baslik, fiyat, resimYolu, aciklama, kategori);
        this.ozeldersTuru = ozeldersTuru;
        this.egitimSeviyesi = egitimSeviyesi;
    }
    public OzeldersEnum getOzeldersTuru() {
        return ozeldersTuru;
    }
    public void setOzeldersTuru(OzeldersEnum ozeldersTuru) {
        this.ozeldersTuru = ozeldersTuru;
    }

    public String getEgitimSeviyesi() {
        return egitimSeviyesi;
    }
    public void setEgitimSeviyesi(String egitimSeviyesi) {
        this.egitimSeviyesi = egitimSeviyesi;
    }
}
