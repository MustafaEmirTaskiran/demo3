package org.example.demo3;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class IlanDAO {

    public List<Ilan> tumIlanlariGetir() {
        List<Ilan> ilanListesi = new ArrayList<>();

        // 1. TAŞITLARI ÇEK
        String sqlTasit = "SELECT t.*, i.IlanTuru FROM Tasit t INNER JOIN Ilan i ON t.IlanID = i.IlanID";
        verileriCekVeEkle(sqlTasit, "Tasit", ilanListesi);

        // 2. KONUTLARI ÇEK
        String sqlKonut = "SELECT k.*, i.IlanTuru FROM Konut k INNER JOIN Ilan i ON k.IlanID = i.IlanID";
        verileriCekVeEkle(sqlKonut, "Konut", ilanListesi);

        // 3. GİYİM ÇEK
        String sqlGiyim = "SELECT g.*, i.IlanTuru FROM Giyim g INNER JOIN Ilan i ON g.IlanID = i.IlanID";
        verileriCekVeEkle(sqlGiyim, "Giyim", ilanListesi);

        // 4. TEKNOLOJİ (Elektronik tablosu varmış resimlerde)
        String sqlTekno = "SELECT e.*, i.IlanTuru FROM Elektronik e INNER JOIN Ilan i ON e.IlanID = i.IlanID";
        verileriCekVeEkle(sqlTekno, "Elektronik", ilanListesi);

        // 5. ÖZEL DERS
        String sqlDers = "SELECT o.*, i.IlanTuru FROM OzelDers o INNER JOIN Ilan i ON o.IlanID = i.IlanID";
        verileriCekVeEkle(sqlDers, "OzelDers", ilanListesi);

        return ilanListesi;
    }

    // Yardımcı Metot: Tekrar eden kodları azalttım
    private void verileriCekVeEkle(String sql, String tabloTuru, List<Ilan> liste) {
        try (Connection conn = DBbaglanti.baglan();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("IlanID");

                // Dikkat: Tablolarda sütun isimleri farklı (TasitMarka, ElektronikMarka vb.)
                // Bu yüzden her tablo için özel isimlendirme kontrolü yapıyoruz.

                if (tabloTuru.equals("Tasit")) {
                    String tasitTuru = rs.getString("TasitTuru") != null ? rs.getString("TasitTuru").trim() : "DIGER";
                    Tasitturu turEnum;
                    try { turEnum = Tasitturu.valueOf(tasitTuru); } catch (Exception e) { turEnum = Tasitturu.DIGER; }

                    Tasit t = new Tasit(
                            rs.getString("TasitMarka") + " " + rs.getString("TasitModel"), // Başlık (Marka+Model)
                            rs.getDouble("TasitFiyati"), // Fiyat
                            rs.getString("TasitFoto"),   // Resim
                            "Tasit",                     // Kategori
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
                else if (tabloTuru.equals("Konut")) {
                    String konutTuru = rs.getString("KonutTuru") != null ? rs.getString("KonutTuru").trim() : "DAIRE";
                    KonutturuEnum kEnum;
                    try { kEnum = KonutturuEnum.valueOf(konutTuru); } catch (Exception e) { kEnum = KonutturuEnum.DAIRE; }

                    Konut k = new Konut(
                            rs.getString("KonutBaslik") != null ? rs.getString("KonutBaslik") : "Satılık Konut",
                            rs.getDouble("KonutFiyat"),
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
                else if (tabloTuru.equals("Giyim")) {
                    String giyimTuru = rs.getString("GiyimTuru") != null ? rs.getString("GiyimTuru").trim() : "UST_GIYIM";
                    GiyimEnum gEnum;
                    try { gEnum = GiyimEnum.valueOf(giyimTuru); } catch (Exception e) { gEnum = GiyimEnum.UST_GIYIM; }

                    Giyim g = new Giyim(
                            "Giyim Ürünü", // Tabloda GiyimBaslik yok, sabit başlık verdim
                            rs.getDouble("GiyimFiyat"),
                            rs.getString("GiyimFoto"),
                            "Giyim",
                            rs.getString("GiyimAciklama"),
                            "-", // Marka sütunu yok
                            "-", // Aşınma yok
                            rs.getString("GiyimBeden"),
                            rs.getString("GiyimCinsiyet"),
                            gEnum
                    );
                    g.setId(id);
                    liste.add(g);
                }
                // Elektronik ve Özel Ders için de benzer bloklar eklenebilir...
            }
        } catch (SQLException e) {
            System.err.println(tabloTuru + " verileri çekilirken hata: " + e.getMessage());
        }
    }
}