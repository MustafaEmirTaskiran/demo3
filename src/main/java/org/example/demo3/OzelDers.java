package org.example.demo3;

public class OzelDers extends Ilan {
    private OzeldersEnum ozeldersTuru; // Enum Tipi (Bu doğruydu)
    private OzeldersEnum egitimSeviyesi; // DÜZELTME: String yerine Enum yaptık!

    // Constructor güncellendi
    public OzelDers(String baslik, double fiyat, String resimYolu, String aciklama, String kategori,
                    OzeldersEnum ozeldersTuru, OzeldersEnum egitimSeviyesi) {
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

    // Getter ve Setter da Enum oldu
    public OzeldersEnum getEgitimSeviyesi() {
        return egitimSeviyesi;
    }

    public void setEgitimSeviyesi(OzeldersEnum egitimSeviyesi) {
        this.egitimSeviyesi = egitimSeviyesi;
    }
}