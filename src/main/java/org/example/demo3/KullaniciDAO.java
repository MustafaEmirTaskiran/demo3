package org.example.demo3;

import java.sql.*;

public class KullaniciDAO {

    public boolean kullaniciKaydet(String isim, String soyisim, String kadi, String sifre, String email, String tel) {
        // SÜTUN İSİMLERİ RESME GÖRE DÜZELTİLDİ
        String sql = "INSERT INTO Kullanicilar (KullaniciIsim, KullaniciSoyIsim, KullaniciAdi, Sifre, Mail, TelNumara) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBbaglanti.baglan();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, isim);
            stmt.setString(2, soyisim);
            stmt.setString(3, kadi);
            stmt.setString(4, sifre);
            stmt.setString(5, email);
            stmt.setString(6, tel);

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int girisYap(String kadi, String sifre) {
        // ID Sütun ismi 'KullaniciID' imiş
        String sql = "SELECT KullaniciID FROM Kullanicilar WHERE KullaniciAdi = ? AND Sifre = ?";
        try (Connection conn = DBbaglanti.baglan();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, kadi);
            stmt.setString(2, sifre);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("KullaniciID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}