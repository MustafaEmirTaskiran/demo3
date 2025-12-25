package org.example.demo3;

import java.sql.*;

public class IlanEkleDAO {

    public boolean tasitEkle(Tasit tasit, int kullaniciId) {
        Connection conn = null;
        PreparedStatement stmtIlan = null;
        PreparedStatement stmtTasit = null;
        ResultSet rs = null;

        try {
            conn = DBbaglanti.baglan();
            conn.setAutoCommit(false); // Transaction Başlat

            // 1. ANA TABLOYA EKLE (Ilan)
            // Tablo sütunları: IlanID(Auto), KullaniciID, IlanTarihi, IlanTuru
            String sqlIlan = "INSERT INTO Ilan (KullaniciID, IlanTarihi, IlanTuru) VALUES (?, GETDATE(), ?)";

            stmtIlan = conn.prepareStatement(sqlIlan, Statement.RETURN_GENERATED_KEYS);
            stmtIlan.setInt(1, kullaniciId);
            stmtIlan.setString(2, "Tasit"); // IlanTuru
            stmtIlan.executeUpdate();

            // ID'yi Al
            rs = stmtIlan.getGeneratedKeys();
            int yeniIlanId = 0;
            if (rs.next()) {
                yeniIlanId = rs.getInt(1);
            } else {
                throw new SQLException("Ilan ID oluşmadı!");
            }

            // 2. DETAY TABLOSUNA EKLE (Tasit)
            // Sütunlar resme göre: IlanID, TasitTuru, TasitFiyati, TasitAciklamasi, TasitMarka...
            String sqlTasit = "INSERT INTO Tasit (IlanID, TasitTuru, TasitFiyati, TasitAciklamasi, TasitMarka, TasitModel, TasitVites, TasitKilometre, TasitYakit, TasitModelYili, TasitFoto) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            stmtTasit = conn.prepareStatement(sqlTasit);
            stmtTasit.setInt(1, yeniIlanId);
            stmtTasit.setString(2, tasit.getTasitturu().toString());
            stmtTasit.setDouble(3, tasit.getFiyat());
            stmtTasit.setString(4, tasit.getAciklama());
            stmtTasit.setString(5, tasit.getMarka());
            stmtTasit.setString(6, "-"); // Model kodu yoksa tire
            stmtTasit.setString(7, tasit.getVitesTuru());
            stmtTasit.setInt(8, tasit.getKilometre());
            stmtTasit.setString(9, tasit.getYakitTipi());
            stmtTasit.setInt(10, tasit.getModelYili());
            stmtTasit.setString(11, tasit.getResimyolu());

            stmtTasit.executeUpdate();

            conn.commit(); // Onayla
            return true;

        } catch (SQLException e) {
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) {}
            e.printStackTrace();
            return false;
        } finally {
            // Kaynakları kapat... (try-with-resources kullanılabilir ama transaction için manuel kapattık)
            try { if (rs != null) rs.close(); if (stmtIlan != null) stmtIlan.close(); if (stmtTasit != null) stmtTasit.close(); if (conn != null) conn.close(); } catch (Exception e) {}
        }
    }
}