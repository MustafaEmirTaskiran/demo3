package org.example.demo3;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VeriTabaniYoneticisi {

    private static VeriTabaniYoneticisi instance;

    public static VeriTabaniYoneticisi getInstance() {
        if (instance == null) instance = new VeriTabaniYoneticisi();
        return instance;
    }

    // --- ÖZEL YARDIMCI METOT: Önce 'Ilan' tablosuna kaydeder, ID'yi döndürür ---
    private int anaIlanEkle(Connection conn, Ilan ilan, String turString) throws SQLException {
        // Resimdeki dbo.Ilan tablosuna göre:
        String sql = "INSERT INTO Ilan (KullaniciID, IlanTarihi, IlanTuru, IlanBaslik, IlanFiyat) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, 1); // Şimdilik Kullanıcı ID'yi 1 varsayıyoruz
            stmt.setDate(2, Date.valueOf(LocalDate.now()));
            stmt.setString(3, turString);
            stmt.setString(4, ilan.getBaslik());
            stmt.setDouble(5, ilan.getFiyat()); // 'money' tipi double ile uyumludur

            stmt.executeUpdate();

            // Oluşan ID'yi al
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        return -1;
    }

    // --- 1. TAŞIT EKLEME ---
    public void tasitEkle(Tasit tasit) {
        // Resimdeki dbo.Tasit tablosu sütunları:
        String sql = "INSERT INTO Tasit (IlanID, TasitTuru, TasitFiyati, TasitAciklamasi, TasitMarka, TasitModel, TasitVites, TasitFoto, TasitKilometre, TasitYakit, TasitModelYili) VALUES (?,?,?,?,?,?,?,?,?,?,?)";

        try (Connection conn = VeritabaniBaglantisi.baglan()) {
            conn.setAutoCommit(false); // Transaction Başlat

            int ilanID = anaIlanEkle(conn, tasit, "Tasit");

            if (ilanID != -1) {
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, ilanID);
                    stmt.setString(2, tasit.getTasitturu().toString());
                    stmt.setDouble(3, tasit.getFiyat());
                    stmt.setString(4, tasit.getAciklama());
                    stmt.setString(5, tasit.getMarka());
                    stmt.setString(6, "-"); // Model bilgisini Tasit sınıfında tutmuyorsan tire koy
                    stmt.setString(7, tasit.getVitesTuru());
                    stmt.setString(8, tasit.getResimyolu());
                    stmt.setInt(9, tasit.getKilometre());
                    stmt.setString(10, tasit.getYakitTipi());
                    stmt.setInt(11, tasit.getModelYili());

                    stmt.executeUpdate();
                    conn.commit(); // Onayla
                    System.out.println("Taşıt SQL'e eklendi. ID: " + ilanID);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- 2. KONUT EKLEME ---
    public void konutEkle(Konut konut) {
        // Resimdeki dbo.Konut tablosu sütunları:
        String sql = "INSERT INTO Konut (IlanID, KonutTuru, KonutInsaatYili, KonutOdaSayisi, KonutSehir, KonutIlce, KonutMahalle, KonutSokak, KonutFiyat, KonutAciklama, KonutFoto) VALUES (?,?,?,?,?,?,?,?,?,?,?)";

        try (Connection conn = VeritabaniBaglantisi.baglan()) {
            conn.setAutoCommit(false);

            int ilanID = anaIlanEkle(conn, konut, "Konut");

            if (ilanID != -1) {
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setInt(1, ilanID);
                    stmt.setString(2, konut.getKonuttipi().toString());
                    stmt.setInt(3, konut.getYapimYili());

                    // DİKKAT: Veritabanında OdaSayısı 'int' ama Java'da 'String'.
                    // "3+1" yazarsan veritabanı patlar. Şimdilik sadece rakam kısmını almaya çalışalım:
                    try {
                        stmt.setInt(4, Integer.parseInt(konut.getOdaSayisi().replaceAll("[^0-9]", "")));
                    } catch (Exception e) { stmt.setInt(4, 0); }

                    stmt.setString(5, konut.getSehir());
                    stmt.setString(6, konut.getIlce());
                    stmt.setString(7, konut.getMahalle());
                    stmt.setString(8, konut.getSokak());
                    stmt.setDouble(9, konut.getFiyat());
                    stmt.setString(10, konut.getAciklama());
                    stmt.setString(11, konut.getResimyolu());

                    stmt.executeUpdate();
                    conn.commit();
                    System.out.println("Konut SQL'e eklendi.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // --- 3. VERİLERİ ÇEKME (LİSTELEME) ---
    public List<Ilan> tumIlanlariGetir() {
        List<Ilan> liste = new ArrayList<>();

        // 1. TAŞITLARI ÇEK
        String sqlTasit = "SELECT * FROM Ilan INNER JOIN Tasit ON Ilan.IlanID = Tasit.IlanID";
        try (Connection conn = VeritabaniBaglantisi.baglan();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlTasit)) {

            while (rs.next()) {
                // .trim() ÇOK ÖNEMLİ: SQL Server nchar boşluklarını temizler
                Tasitturu tTur = Tasitturu.DIGER;
                try {
                    tTur = Tasitturu.valueOf(rs.getString("TasitTuru").trim());
                } catch (Exception e) {}

                Tasit t = new Tasit(
                        rs.getString("IlanBaslik").trim(),
                        rs.getDouble("IlanFiyat"),
                        rs.getString("TasitFoto").trim(),
                        "Otomobil",
                        rs.getString("TasitAciklamasi").trim(),
                        tTur,
                        rs.getString("TasitYakit").trim(),
                        rs.getInt("TasitKilometre"),
                        rs.getString("TasitMarka").trim(),
                        rs.getInt("TasitModelYili"),
                        rs.getString("TasitVites").trim()
                );
                t.setId(rs.getInt("IlanID"));
                liste.add(t);
            }
        } catch (Exception e) { e.printStackTrace(); }

        // 2. KONUTLARI ÇEK
        String sqlKonut = "SELECT * FROM Ilan INNER JOIN Konut ON Ilan.IlanID = Konut.IlanID";
        try (Connection conn = VeritabaniBaglantisi.baglan();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sqlKonut)) {

            while (rs.next()) {
                KonutturuEnum kTur = KonutturuEnum.DAIRE;
                try {
                    kTur = KonutturuEnum.valueOf(rs.getString("KonutTuru").trim());
                } catch (Exception e) {}

                Konut k = new Konut(
                        rs.getString("IlanBaslik").trim(),
                        rs.getDouble("IlanFiyat"),
                        rs.getString("KonutFoto").trim(),
                        "Konut",
                        String.valueOf(rs.getObject("KonutOdaSayisi")),
                        rs.getString("KonutAciklama").trim(),
                        rs.getString("KonutSehir").trim(),
                        rs.getString("KonutIlce").trim(),
                        rs.getString("KonutMahalle").trim(),
                        rs.getString("KonutSokak").trim(),
                        kTur,
                        rs.getInt("KonutInsaatYili")
                );
                k.setId(rs.getInt("IlanID"));
                liste.add(k);
            }
        } catch (Exception e) { e.printStackTrace(); }

        return liste;
    }

    // Not: Giyim, Teknoloji ve Ders ekleme/çekme metodlarını da aynı mantıkla ekleyebilirsin.
}