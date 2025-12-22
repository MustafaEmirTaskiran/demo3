
package org.example.demo3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class VeritabaniBaglantisi {
    // Windows Authentication (Şifresiz) kullanıyorsan bu URL çalışır.
    // Şifreli ise: "...;user=sa;password=sifren;..." ekle.
    private static final String URL = "jdbc:sqlserver://localhost;databaseName=spotalDB;integratedSecurity=true;encrypt=true;trustServerCertificate=true;";

    public static Connection baglan() throws SQLException {
        return DriverManager.getConnection(URL);
    }
}