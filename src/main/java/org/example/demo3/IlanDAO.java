package org.example.demo3;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IlanDAO {

    public List<Ilan> tumIlanlariGetir() {
        List<Ilan> ilanListesi = new ArrayList<>();

        // 1. TAŞITLARI ÇEK (Tablo adı: Tasit)
        // Ekran Görüntüsü: TasitFiyati (sonunda i var)
        String sqlTasit = "SELECT t.*, i.IlanTuru FROM Tasit t INNER JOIN Ilan i ON t.IlanID = i.IlanID";
        verileriCekVeEkle(sqlTasit, "Tasit", ilanListesi);

        // 2. KONUTLARI ÇEK (Tablo adı: Konut)
        // Ekran Görüntüsü: KonutFiyat (sonunda i YOK!)
        String sqlKonut = "SELECT k.*, i.IlanTuru FROM Konut k INNER JOIN Ilan i ON k.IlanID = i.IlanID";
        verileriCekVeEkle(sqlKonut, "Konut", ilanListesi);

        // 3. GİYİM ÇEK (Tablo adı: Giyim)
        // Ekran Görüntüsü: GiyimFiyat (sonunda i YOK!)
        String sqlGiyim = "SELECT g.*, i.IlanTuru FROM Giyim g INNER JOIN Ilan i ON g.IlanID = i.IlanID";
        verileriCekVeEkle(sqlGiyim, "Giyim", ilanListesi);

        // 4. TEKNOLOJİ (Tablo adı: Elektronik - Resimden gördüğüm)
        // Ekran Görüntüsü: ElektronikFiyat (sonunda i YOK!)
        String sqlTekno = "SELECT e.*, i.IlanTuru FROM Elektronik e INNER JOIN Ilan i ON e.IlanID = i.IlanID";
        verileriCekVeEkle(sqlTekno, "Elektronik", ilanListesi);

        // 5. ÖZEL DERS (Tablo adı: OzelDers)
        // Ekran Görüntüsü: OzelDersFiyat (sonunda i YOK!)
        String sqlDers = "SELECT o.*, i.IlanTuru FROM OzelDers o INNER JOIN Ilan i ON o.IlanID = i.IlanID";
        verileriCekVeEkle(sqlDers, "OzelDers", ilanListesi);

        return ilanListesi;
    }

    private void verileriCekVeEkle(String sql, String tabloTuru, List<Ilan> liste) {
        try (Connection conn = DBbaglanti.baglan();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("IlanID");

                try {
                    // --- TAŞIT ---
                    if (tabloTuru.equals("Tasit")) {
                        String tasitTuru = rs.getString("TasitTuru") != null ? rs.getString("TasitTuru").trim() : "DIGER";
                        Tasitturu turEnum;
                        try { turEnum = Tasitturu.valueOf(tasitTuru); } catch (Exception e) { turEnum = Tasitturu.DIGER; }

                        Tasit t = new Tasit(
                                rs.getString("TasitMarka") + " " + rs.getString("TasitModel"),
                                rs.getDouble("TasitFiyati"), // DİKKAT: Burada 'i' var
                                rs.getString("TasitFoto"),
                                "Tasit",
                                rs.getString("TasitAciklamasi"),
                                turEnum,
                                rs.getString("TasitYakit"),
                                rs.getInt("TasitKilometre"),
                                rs.getString("TasitMarka"),
                                rs.getInt("TasitModelYili"),
                                rs.getString("TasitVites")
                        );
                        t.setId(id);
                        liste.add(t);
                    }
                    // --- KONUT ---
                    else if (tabloTuru.equals("Konut")) {
                        String konutTuru = rs.getString("KonutTuru") != null ? rs.getString("KonutTuru").trim() : "DAIRE";
                        KonutturuEnum kEnum;
                        try { kEnum = KonutturuEnum.valueOf(konutTuru); } catch (Exception e) { kEnum = KonutturuEnum.DAIRE; }

                        // Konut tablosunda 'KonutBaslik' yok, elle oluşturuyoruz:
                        String baslik = rs.getString("KonutSehir") + " " + rs.getString("KonutIlce") + " Satılık";

                        Konut k = new Konut(
                                baslik,
                                rs.getDouble("KonutFiyat"), // DİKKAT: Burada 'i' YOK
                                rs.getString("KonutFoto"),
                                "Konut",
                                String.valueOf(rs.getInt("KonutOdaSayisi")),
                                rs.getString("KonutAciklama"),
                                rs.getString("KonutSehir"),
                                rs.getString("KonutIlce"),
                                rs.getString("KonutMahalle"),
                                rs.getString("KonutSokak"),
                                kEnum,
                                rs.getInt("KonutInsaatYili")
                        );
                        k.setId(id);
                        liste.add(k);
                    }
                    // --- GİYİM ---
                    else if (tabloTuru.equals("Giyim")) {
                        String giyimTuru = rs.getString("GiyimTuru") != null ? rs.getString("GiyimTuru").trim() : "UST_GIYIM";
                        GiyimEnum gEnum;
                        try { gEnum = GiyimEnum.valueOf(giyimTuru); } catch (Exception e) { gEnum = GiyimEnum.UST_GIYIM; }

                        // Giyim tablosunda Başlık yok
                        String baslik = rs.getString("GiyimTuru") + " (" + rs.getString("GiyimBeden") + ")";

                        Giyim g = new Giyim(
                                baslik,
                                rs.getDouble("GiyimFiyat"), // DİKKAT: 'i' YOK
                                rs.getString("GiyimFoto"),
                                "Giyim",
                                rs.getString("GiyimAciklama"),
                                "-",
                                "-",
                                rs.getString("GiyimBeden"),
                                rs.getString("GiyimCinsiyet"),
                                gEnum
                        );
                        g.setId(id);
                        liste.add(g);
                    }
                    // --- ELEKTRONİK (Teknoloji) ---
                    else if (tabloTuru.equals("Elektronik")) {
                        // TeknolojituruEnum çevrimi
                        String teknoTuru = rs.getString("ElektronikTuru") != null ? rs.getString("ElektronikTuru").trim() : "BILGISAYAR";
                        TeknolojituruEnum tEnum;
                        try { tEnum = TeknolojituruEnum.valueOf(teknoTuru); } catch (Exception e) { tEnum = TeknolojituruEnum.BILGISAYAR; }

                        String baslik = rs.getString("ElektronikMarka") + " " + rs.getString("ElektronikModel");

                        Teknoloji tek = new Teknoloji(
                                baslik,
                                rs.getDouble("ElektronikFiyat"), // 'i' YOK
                                rs.getString("ElektronikFoto"),
                                "Teknoloji",
                                rs.getString("ElektronikAciklama"),
                                rs.getString("ElektronikMarka"),
                                rs.getString("ElektronikModel"),
                                tEnum
                        );
                        tek.setId(id);
                        liste.add(tek);
                    }
                    // --- ÖZEL DERS ---
                    else if (tabloTuru.equals("OzelDers")) {
                        // Enum çevrimi (Varsayılan TYT_AYT)
                        OzeldersEnum oEnum = OzeldersEnum.TYT_AYT_LGS;

                        OzelDers o = new OzelDers(
                                rs.getString("OzelDersDers"), // Başlık olarak Ders adını alıyoruz
                                rs.getDouble("OzelDersFiyat"), // 'i' YOK
                                null, // Resim sütunu yok gibi
                                rs.getString("OzelDersAciklama"),
                                "OzelDers",
                                oEnum,
                                oEnum
                        );
                        o.setId(id);
                        liste.add(o);
                    }

                } catch (Exception e) {
                    System.err.println("HATA (" + tabloTuru + " satırı işlenirken): " + e.getMessage());
                    // Bir satırda hata olsa bile diğerlerini çekmeye devam etsin
                }
            }
        } catch (SQLException e) {
            System.err.println(tabloTuru + " Tablosu Çekilemedi! SQL Hatası: " + e.getMessage());
        }
    }
}