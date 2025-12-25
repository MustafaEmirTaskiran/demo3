package org.example.demo3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FavoriDAO {

    // 1. Favori Ekle
    public boolean favoriEkle(int kullaniciId, int ilanId) {
        String sql = "INSERT INTO Favoriler (KullaniciId, IlanId) VALUES (?, ?)";
        try (Connection conn = DBbaglanti.baglan();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, kullaniciId);
            stmt.setInt(2, ilanId);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 2. Favori Çıkar
    public boolean favoriCikar(int kullaniciId, int ilanId) {
        String sql = "DELETE FROM Favoriler WHERE KullaniciId = ? AND IlanId = ?";
        try (Connection conn = DBbaglanti.baglan();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, kullaniciId);
            stmt.setInt(2, ilanId);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 3. Tek bir ilanın favori olup olmadığını kontrol et (Kalp dolu mu boş mu gelecek?)
    public boolean isFavori(int kullaniciId, int ilanId) {
        String sql = "SELECT COUNT(*) FROM Favoriler WHERE KullaniciId = ? AND IlanId = ?";
        try (Connection conn = DBbaglanti.baglan();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, kullaniciId);
            stmt.setInt(2, ilanId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 4. Kullanıcının tüm favori ilanlarının ID'lerini liste olarak getir (Filtreleme için)
    public List<Integer> getKullanicininFavoriIlanIdleri(int kullaniciId) {
        List<Integer> idListesi = new ArrayList<>();
        String sql = "SELECT IlanId FROM Favoriler WHERE KullaniciId = ?";
        try (Connection conn = DBbaglanti.baglan();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, kullaniciId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                idListesi.add(rs.getInt("IlanId"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return idListesi;
    }
}
