package org.example.demo3;

import java.sql.*;

public class IlanEkleDAO {

    // --- 1. TAŞIT EKLEME (Zaten Vardı) ---
    public boolean tasitEkle(Tasit tasit, int kullaniciId, String modelIsmi) {
        return genelIlanEkle(kullaniciId, "Tasit", (conn, ilanId) -> {
            String sql = "INSERT INTO Tasit (IlanID, TasitTuru, TasitFiyati, TasitAciklamasi, TasitMarka, TasitModel, TasitVites, TasitFoto, TasitKilometre, TasitYakit, TasitModelYili) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, ilanId);
                stmt.setString(2, tasit.getTasitturu().toString());
                stmt.setDouble(3, tasit.getFiyat());
                stmt.setString(4, tasit.getAciklama());
                stmt.setString(5, tasit.getMarka());
                stmt.setString(6, (modelIsmi != null ? modelIsmi : "-"));
                stmt.setString(7, tasit.getVitesTuru());
                stmt.setString(8, tasit.getResimyolu());
                stmt.setInt(9, tasit.getKilometre());
                stmt.setString(10, tasit.getYakitTipi());
                stmt.setInt(11, tasit.getModelYili());
                stmt.executeUpdate();
            }
        });
    }

    // --- 2. KONUT EKLEME (YENİ) ---
    public boolean konutEkle(Konut konut, int kullaniciId) {
        return genelIlanEkle(kullaniciId, "Konut", (conn, ilanId) -> {
            // Sütunlar resimden: KonutTuru, KonutInsaatYili, KonutOdaSayisi, KonutSatisKira(Yok saydım), KonutSehir, KonutIlce, KonutSokak, KonutFiyat, KonutAciklama, KonutMahalle, KonutFoto
            String sql = "INSERT INTO Konut (IlanID, KonutTuru, KonutFiyat, KonutAciklama, KonutSehir, KonutIlce, KonutMahalle, KonutSokak, KonutOdaSayisi, KonutInsaatYili, KonutFoto) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, ilanId);
                stmt.setString(2, konut.getKonuttipi().toString());
                stmt.setDouble(3, konut.getFiyat());
                stmt.setString(4, konut.getAciklama());
                stmt.setString(5, konut.getSehir());
                stmt.setString(6, konut.getIlce());
                stmt.setString(7, konut.getMahalle());
                stmt.setString(8, konut.getSokak());
                // Oda sayısını String'den int'e çevirmeyi dene, olmazsa 0 yaz
                try { stmt.setInt(9, Integer.parseInt(konut.getOdaSayisi())); } catch (Exception e) { stmt.setInt(9, 0); }
                stmt.setInt(10, konut.getYapimYili());
                stmt.setString(11, konut.getResimyolu());
                stmt.executeUpdate();
            }
        });
    }

    // --- 3. GİYİM EKLEME (YENİ) ---
    public boolean giyimEkle(Giyim giyim, int kullaniciId) {
        return genelIlanEkle(kullaniciId, "Giyim", (conn, ilanId) -> {
            // Sütunlar resimden: GiyimTuru, GiyimBeden, GiyimFiyat, GiyimAciklama, GiyimFoto, GiyimCinsiyet
            String sql = "INSERT INTO Giyim (IlanID, GiyimTuru, GiyimBeden, GiyimFiyat, GiyimAciklama, GiyimFoto, GiyimCinsiyet) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, ilanId);
                stmt.setString(2, giyim.getGiyimturu().toString());
                stmt.setString(3, giyim.getBeden());
                stmt.setDouble(4, giyim.getFiyat());
                stmt.setString(5, giyim.getAciklama());
                stmt.setString(6, giyim.getResimyolu());
                stmt.setString(7, giyim.getCinsiyet());
                stmt.executeUpdate();
            }
        });
    }

    // --- 4. TEKNOLOJİ (ELEKTRONİK) EKLEME (YENİ) ---
    public boolean teknolojiEkle(Teknoloji tekno, int kullaniciId) {
        return genelIlanEkle(kullaniciId, "Elektronik", (conn, ilanId) -> {
            // Sütunlar resimden: ElektronikTuru, ElektronikMarka, ElektronikFiyat, ElektronikAciklama, ElektronikFoto, ElektronikModel
            String sql = "INSERT INTO Elektronik (IlanID, ElektronikTuru, ElektronikMarka, ElektronikFiyat, ElektronikAciklama, ElektronikFoto, ElektronikModel) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, ilanId);
                stmt.setString(2, tekno.getTeknolojituru().toString());
                stmt.setString(3, tekno.getMarka());
                stmt.setDouble(4, tekno.getFiyat());
                stmt.setString(5, tekno.getAciklama());
                stmt.setString(6, tekno.getResimyolu());
                stmt.setString(7, tekno.getModel());
                stmt.executeUpdate();
            }
        });
    }

    // --- 5. ÖZEL DERS EKLEME (YENİ) ---
    public boolean ozelDersEkle(OzelDers ders, int kullaniciId) {
        return genelIlanEkle(kullaniciId, "OzelDers", (conn, ilanId) -> {
            // Sütunlar resimden: OzelDersDers, OzelDersSinif, OzelDersFiyat, OzelDersAciklama
            String sql = "INSERT INTO OzelDers (IlanID, OzelDersDers, OzelDersSinif, OzelDersFiyat, OzelDersAciklama) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, ilanId);
                stmt.setString(2, ders.getBaslik()); // Ders adı başlık olsun
                stmt.setString(3, ders.getEgitimSeviyesi().toString());
                stmt.setDouble(4, ders.getFiyat());
                stmt.setString(5, ders.getAciklama());
                stmt.executeUpdate();
            }
        });
    }

    // --- YARDIMCI METOT (Kod Tekrarını Önlemek İçin) ---
    private boolean genelIlanEkle(int kullaniciId, String kategoriTuru, ChildTableInserter childInserter) {
        Connection conn = null;
        PreparedStatement stmtIlan = null;
        ResultSet rs = null;
        try {
            conn = DBbaglanti.baglan();
            conn.setAutoCommit(false); // Transaction Başlat

            // 1. Ana Tabloya Ekle
            String sqlIlan = "INSERT INTO Ilan (KullaniciID, IlanTarihi, IlanTuru) VALUES (?, GETDATE(), ?)";
            stmtIlan = conn.prepareStatement(sqlIlan, Statement.RETURN_GENERATED_KEYS);
            stmtIlan.setInt(1, kullaniciId);
            stmtIlan.setString(2, kategoriTuru);
            stmtIlan.executeUpdate();

            // 2. ID Al
            rs = stmtIlan.getGeneratedKeys();
            int yeniIlanId = 0;
            if (rs.next()) yeniIlanId = rs.getInt(1);
            else throw new SQLException("ID Oluşmadı");

            // 3. Alt Tabloya Ekle (Interface ile dinamik hale getirdik)
            childInserter.insert(conn, yeniIlanId);

            conn.commit();
            System.out.println("✅ " + kategoriTuru + " ilanı eklendi. ID: " + yeniIlanId);
            return true;
        } catch (Exception e) {
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) {}
            e.printStackTrace();
            return false;
        } finally {
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
    }

    // Functional Interface (Lambda kullanmak için)
    @FunctionalInterface
    interface ChildTableInserter {
        void insert(Connection conn, int ilanId) throws SQLException;
    }
}