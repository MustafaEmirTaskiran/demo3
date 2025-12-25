package org.example.demo3;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class KayitolController {

    @FXML public TextField isimTextID;
    @FXML public TextField soyisimTextID;
    @FXML public TextField kullaniciadTextID;
    @FXML public TextField sifreTextID;
    @FXML public TextField sifretkrTextID;
    @FXML public TextField emailTextID;
    @FXML public TextField telefonnoTextID;

    // FXML dosyasındaki Kayıt Ol butonuna onAction="#kayitOlButonBasildi" eklemelisin!
    @FXML
    public void kayitOlButonBasildi(ActionEvent event) {
        // 1. Şifreler uyuşuyor mu?
        if (!sifreTextID.getText().equals(sifretkrTextID.getText())) {
            showAlert("Hata", "Şifreler uyuşmuyor!");
            return;
        }

        // 2. Veritabanına Kaydet
        KullaniciDAO dao = new KullaniciDAO();
        boolean sonuc = dao.kullaniciKaydet(
                isimTextID.getText(),
                soyisimTextID.getText(),
                kullaniciadTextID.getText(),
                sifreTextID.getText(),
                emailTextID.getText(),
                telefonnoTextID.getText()
        );

        if (sonuc) {
            showAlert("Başarılı", "Kayıt işlemi tamamlandı! Giriş yapabilirsiniz.");
            // Pencereyi kapat
            ((Stage) isimTextID.getScene().getWindow()).close();
        } else {
            showAlert("Hata", "Kayıt yapılamadı. Kullanıcı adı veya Email kullanılıyor olabilir.");
        }
    }

    private void showAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    // Eski boş metotları silebilirsin, TextField actionlarına gerek yok.
}