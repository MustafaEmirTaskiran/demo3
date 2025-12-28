package org.example.demo3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBbaglanti {

    // --- BURALARI ARKADAŞINDAN ÖĞRENİP DEĞİŞTİR ---
    // 1. Arkadaşının Bilgisayarının IP Adresi (Örn: 192.168.1.35)
    private static final String IP_ADRESI = "10.231.124.148";

    // 2. Veritabanının Adı (Ekran görüntüsünde tablo isimleri vardı ama DB adını o bilir)
    private static final String DB_ISMI = "spotalDB";

    // 3. SQL Server Kullanıcı Adı (Genelde 'sa' olur ama değişmiş olabilir)
    private static final String KULLANICI = "kerem";

    // 4. SQL Server Şifresi
    private static final String SIFRE = "TommyVercetti1907.";
    // --------------------------------------------------

    public static Connection baglan() {
        Connection conn = null;
        try {
            // Bağlantı cümlesi (Connection String)
            // encrypt=true ve trustServerCertificate=true ayarları sertifika hatası almanı engeller
            String dbURL = "jdbc:sqlserver://" + IP_ADRESI + ":1433;" +
                    "databaseName=" + DB_ISMI + ";" +
                    "encrypt=true;trustServerCertificate=true;";

            conn = DriverManager.getConnection(dbURL, KULLANICI, SIFRE);
            System.out.println("✅ BAŞARILI: Veritabanına bağlantı sağlandı!");

        } catch (SQLException e) {
            System.err.println("❌ HATA: Veritabanına bağlanılamadı!");
            System.err.println("Hata Mesajı: " + e.getMessage());
        }
        return conn;
    }

    // Bu Main metodu sadece test içindir.
    // Dosyaya sağ tıklayıp "Run DBBaglanti.main()" diyerek testi yapabilirsin.
    public static void main(String[] args) {
        baglan();
    }
}
