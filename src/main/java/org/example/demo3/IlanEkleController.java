package org.example.demo3;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class IlanEkleController implements Initializable {

    // --- 1. ORTAK ALANLAR (Üst Kısım) ---
    @FXML private TextField baslikTextField;
    @FXML private TextField fiyatTextField;
    @FXML private TextField resimyoluTextField;
    @FXML private ComboBox<String> kategoriSecici;

    // --- 2. DİNAMİK KUTULAR (VBOX) ---
    @FXML private VBox otomobilFormVBox;
    @FXML private VBox konutFormVBox;
    @FXML private VBox teknoFormVBox;
    @FXML private VBox giyimFormVBox;
    @FXML private VBox dersFormVBox;

    // --- 3. TAŞIT FORMU ---
    @FXML private TextField tasitturuTextField;
    @FXML private TextField yakittipiTextField;
    @FXML private TextField kilometreTextField;
    @FXML private TextField omarkaTextField;
    @FXML private TextField oModelTextField;
    @FXML private TextField omodelyiliTextField;
    @FXML private TextField vitesTextField;
    @FXML private TextArea oaciklamaTextArea;

    // --- 4. KONUT FORMU ---
    @FXML private TextField konuttipiTextField;
    @FXML private TextField konutyapimyiliTextField;
    @FXML private TextField odasayisiTextField;
    @FXML private TextField sehirTextField;
    @FXML private TextField ilceTextField;
    @FXML private TextField mahalleTextField;
    @FXML private TextField sokakTextField;
    @FXML private TextArea aciklamaTextArea; // Konut Açıklama

    // --- 5. TEKNOLOJİ FORMU ---
    @FXML private TextField teknoturuTextField;
    @FXML private TextField tmarkaTextField;
    @FXML private TextField tmodelTextField;
    @FXML private TextArea taciklamaTextArea;

    // --- 6. GİYİM FORMU ---
    @FXML private TextField giyimturTextField;
    @FXML private TextField gmarkaTextField;
    @FXML private TextField gbedenTextField;
    @FXML private TextField asinmamiktarTextField;
    @FXML private ComboBox<String> cinsiyetComboBox;
    @FXML private TextArea kaciklamaTextArea;

    // --- 7. ÖZEL DERS FORMU ---
    @FXML private TextField dersturuTextField;
    @FXML private ComboBox<String> egitimSeviyesiComboBox;
    @FXML private TextArea dersTextArea;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Kategori Seçiciyi Doldur
        kategoriSecici.setItems(FXCollections.observableArrayList(
                "Taşıt", "Konut", "Teknoloji", "Giyim", "Özel Ders"
        ));

        // Cinsiyet ve Eğitim Seviyesi ComboBox'larını doldur
        cinsiyetComboBox.setItems(FXCollections.observableArrayList("Erkek", "Kadın", "Unisex"));
        egitimSeviyesiComboBox.setItems(FXCollections.observableArrayList("İlk-Orta", "Lise", "Üniversite", "Genel"));

        // Başlangıçta tüm alt formları gizle
        formlariGizle();

        // Kategori Seçildiğinde İlgili Formu Aç
        kategoriSecici.setOnAction(event -> {
            formlariGizle();
            String secilen = kategoriSecici.getValue();
            if (secilen == null) return;

            switch (secilen) {
                case "Taşıt":
                    otomobilFormVBox.setVisible(true);
                    otomobilFormVBox.setManaged(true);
                    break;
                case "Konut":
                    konutFormVBox.setVisible(true);
                    konutFormVBox.setManaged(true);
                    break;
                case "Teknoloji":
                    teknoFormVBox.setVisible(true);
                    teknoFormVBox.setManaged(true);
                    break;
                case "Giyim":
                    giyimFormVBox.setVisible(true);
                    giyimFormVBox.setManaged(true);
                    break;
                case "Özel Ders":
                    dersFormVBox.setVisible(true);
                    dersFormVBox.setManaged(true);
                    break;
            }
        });
    }

    private void formlariGizle() {
        otomobilFormVBox.setVisible(false);
        otomobilFormVBox.setManaged(false);
        konutFormVBox.setVisible(false);
        konutFormVBox.setManaged(false);
        teknoFormVBox.setVisible(false);
        teknoFormVBox.setManaged(false);
        giyimFormVBox.setVisible(false);
        giyimFormVBox.setManaged(false);
        dersFormVBox.setVisible(false);
        dersFormVBox.setManaged(false);
    }

    @FXML
    void gozatButton(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Resim Seç");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Resim Dosyaları", "*.png", "*.jpg", "*.jpeg")
        );
        File secilenDosya = fileChooser.showOpenDialog(null);
        if (secilenDosya != null) {
            // Dosya yolunu text alanına yaz
            resimyoluTextField.setText(secilenDosya.getAbsolutePath());
        }
    }

    @FXML
    void ilanPaylas(ActionEvent event) {
        // 1. GİRİŞ KONTROLÜ
        if (Oturum.aktifKullaniciId == 0) {
            showAlert("Hata", "İlan paylaşmak için giriş yapmalısınız!");
            return;
        }

        String secilenKategori = kategoriSecici.getValue();
        if (secilenKategori == null) {
            showAlert("Uyarı", "Lütfen bir kategori seçiniz.");
            return;
        }

        IlanEkleDAO dao = new IlanEkleDAO();
        boolean basarili = false;

        try {
            // ORTAK VERİLERİ AL (Fiyat, Resim vb.)
            String baslik = baslikTextField.getText();
            double fiyat = Double.parseDouble(fiyatTextField.getText());
            String resimYolu = resimyoluTextField.getText();

            // KATEGORİYE GÖRE İŞLEM YAP
            if (secilenKategori.equals("Taşıt")) {
                // Taşıt Nesnesi Oluştur
                Tasit t = new Tasit(
                        baslik,
                        fiyat,
                        resimYolu,
                        "Tasit",
                        oaciklamaTextArea.getText(),
                        Tasitturu.OTOMOBIL, // Varsayılan veya TextField'dan Enum çevrilebilir
                        yakittipiTextField.getText(),
                        Integer.parseInt(kilometreTextField.getText()),
                        omarkaTextField.getText(),
                        Integer.parseInt(omodelyiliTextField.getText()),
                        vitesTextField.getText()
                );
                // DAO ile kaydet
                basarili = dao.tasitEkle(t, Oturum.aktifKullaniciId);

            } else if (secilenKategori.equals("Konut")) {
                // Konut Nesnesi Oluştur (KonutEkleDAO yazılmalı veya genel DAO genişletilmeli)
                // Şimdilik örnek uyarı:
                showAlert("Bilgi", "Konut ekleme henüz aktif değil (DAO metodunu ekleyiniz).");
                return;
            }
            // Diğer kategoriler için else-if blokları eklenecek...

            if (basarili) {
                showAlert("Başarılı", "İlanınız başarıyla yayınlandı!");
                geridonButtonclick(event); // Ana sayfaya dön
            } else {
                showAlert("Hata", "Veritabanına kayıt sırasında bir sorun oluştu.");
            }

        } catch (NumberFormatException e) {
            showAlert("Hata", "Fiyat, Yıl veya Kilometre alanlarına sadece sayı giriniz!");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Hata", "Beklenmedik bir hata: " + e.getMessage());
        }
    }

    @FXML
    void geridonButtonclick(ActionEvent event) {
        try {
            // Ana sayfaya (spotal.fxml) dön
            URL url = getClass().getResource("/spotal.fxml");
            if (url == null) url = getClass().getResource("spotal.fxml");

            Parent root = FXMLLoader.load(url);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
