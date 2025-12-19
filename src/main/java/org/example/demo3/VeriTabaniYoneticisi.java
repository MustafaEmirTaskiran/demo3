package org.example.demo3;

public class VeriTabaniYoneticisi {

    // Singleton Pattern (İsteğe bağlı ama önerilir, her yerden tek nesneye ulaşmak için)
    private static VeriTabaniYoneticisi instance;

    public static VeriTabaniYoneticisi getInstance() {
        if (instance == null) instance = new VeriTabaniYoneticisi();
        return instance;
    }

    // --- 1. ANA TABLO (ILANLAR) KAYDI ---
    // Bu metodu dışarıdan çağırmayacağız, alt metotlar bunu kullanacak.
    private int anaIlanKaydet(Ilan ilan) {
        int yeniId = -1;
        System.out.println("Veritabanı: Ana İlan tablosuna kayıt yapılıyor... Başlık: " + ilan.getBaslik());

        // ***************** ARKADAŞININ GÖREVİ *****************
        // Buraya SQL INSERT komutu gelecek.
        // Tablo: 'ilanlar' (id, baslik, fiyat, resim_yolu, kategori, aciklama)
        // INSERT INTO ilanlar (...) VALUES (...)
        // İşlem bitince veritabanının ürettiği ID'yi (Generated Key) 'yeniId' değişkenine atamalı.
        // ******************************************************

        // Şimdilik test için rastgele bir ID döndürüyoruz
        yeniId = (int) (Math.random() * 1000);
        return yeniId;
    }

    // --- 2. TAŞIT EKLEME ---
    public void tasitEkle(Tasit tasit) {
        int ilanId = anaIlanKaydet(tasit); // Önce ana tabloya kaydettik

        if (ilanId != -1) {
            System.out.println("Veritabanı: Taşıt detayları ekleniyor... ID: " + ilanId);

            // ***************** ARKADAŞININ GÖREVİ *****************
            // Tablo: 'tasitlar' (ilan_id, tasit_turu, yakit_tipi, kilometre, marka, model_yili, vites_turu)
            // VALUES (ilanId, tasit.getTasitturu().toString(), tasit.getYakitTipi(), ...)
            // ******************************************************
        }
    }

    // --- 3. KONUT EKLEME ---
    public void konutEkle(Konut konut) {
        int ilanId = anaIlanKaydet(konut);

        if (ilanId != -1) {
            System.out.println("Veritabanı: Konut detayları ekleniyor... ID: " + ilanId);

            // ***************** ARKADAŞININ GÖREVİ *****************
            // Tablo: 'konutlar' (ilan_id, konut_tipi, yapim_yili, oda_sayisi, sehir, ilce, mahalle...)
            // VALUES (ilanId, konut.getKonuttipi().toString(), konut.getYapimYili(), ...)
            // ******************************************************
        }
    }

    // --- 4. TEKNOLOJİ EKLEME ---
    public void teknolojiEkle(Teknoloji teknoloji) {
        int ilanId = anaIlanKaydet(teknoloji);

        if (ilanId != -1) {
            System.out.println("Veritabanı: Teknoloji detayları ekleniyor...");

            // ***************** ARKADAŞININ GÖREVİ *****************
            // Tablo: 'teknoloji' (ilan_id, tur, marka, model)
            // ******************************************************
        }
    }

    // --- 5. GİYİM EKLEME ---
    public void giyimEkle(Giyim giyim) {
        int ilanId = anaIlanKaydet(giyim);

        if (ilanId != -1) {
            System.out.println("Veritabanı: Giyim detayları ekleniyor...");

            // ***************** ARKADAŞININ GÖREVİ *****************
            // Tablo: 'giyim' (ilan_id, tur, beden, cinsiyet...)
            // ******************************************************
        }
    }

    // --- 6. ÖZEL DERS EKLEME ---
    public void ozelDersEkle(OzelDers ders) {
        int ilanId = anaIlanKaydet(ders);

        if (ilanId != -1) {
            System.out.println("Veritabanı: Ders detayları ekleniyor...");

            // ***************** ARKADAŞININ GÖREVİ *****************
            // Tablo: 'dersler' (ilan_id, ders_turu, egitim_seviyesi)
            // ******************************************************
        }
    }
}
