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
            // ORTAK VERİLERİ AL
            String baslik = baslikTextField.getText();
            // Fiyat boşsa hata vermesin, 0 olsun
            double fiyat = 0;
            try { fiyat = Double.parseDouble(fiyatTextField.getText()); } catch (Exception e) {}
            String resimYolu = resimyoluTextField.getText();

            // =========================================================
            // --- KATEGORİYE GÖRE DAO ÇAĞIR ---
            // =========================================================

            if (secilenKategori.equals("Taşıt")) {
                Tasit t = new Tasit(
                        baslik, fiyat, resimYolu, "Tasit", oaciklamaTextArea.getText(),
                        Tasitturu.OTOMOBIL, // Varsayılan veya TextField
                        yakittipiTextField.getText(),
                        tryParseInt(kilometreTextField.getText()),
                        omarkaTextField.getText(),
                        tryParseInt(omodelyiliTextField.getText()),
                        vitesTextField.getText()
                );
                basarili = dao.tasitEkle(t, Oturum.aktifKullaniciId, oModelTextField.getText());
            }
            else if (secilenKategori.equals("Konut")) {
                Konut k = new Konut(
                        baslik, fiyat, resimYolu, "Konut",
                        odasayisiTextField.getText(),
                        aciklamaTextArea.getText(),
                        sehirTextField.getText(),
                        ilceTextField.getText(),
                        mahalleTextField.getText(),
                        sokakTextField.getText(),
                        KonutturuEnum.DAIRE, // Veya ComboBox'tan alabilirsin
                        tryParseInt(konutyapimyiliTextField.getText())
                );
                basarili = dao.konutEkle(k, Oturum.aktifKullaniciId);
            }
            else if (secilenKategori.equals("Giyim")) {
                String secilenCinsiyet = cinsiyetComboBox.getValue() != null ? cinsiyetComboBox.getValue() : "Unisex";
                Giyim g = new Giyim(
                        baslik, fiyat, resimYolu, "Giyim",
                        kaciklamaTextArea.getText(),
                        gmarkaTextField.getText(),
                        asinmamiktarTextField.getText(),
                        gbedenTextField.getText(),
                        secilenCinsiyet,
                        GiyimEnum.UST_GIYIM // Veya ComboBox
                );
                basarili = dao.giyimEkle(g, Oturum.aktifKullaniciId);
            }
            else if (secilenKategori.equals("Teknoloji")) {
                Teknoloji tek = new Teknoloji(
                        baslik, fiyat, resimYolu, "Teknoloji",
                        taciklamaTextArea.getText(),
                        tmarkaTextField.getText(),
                        tmodelTextField.getText(),
                        TeknolojituruEnum.BILGISAYAR // Veya ComboBox
                );
                basarili = dao.teknolojiEkle(tek, Oturum.aktifKullaniciId);
            }
            else if (secilenKategori.equals("Özel Ders")) {
                String seviyeStr = egitimSeviyesiComboBox.getValue();
                // Basit bir eşleştirme (Gerçek projede daha detaylı yapılabilir)
                OzeldersEnum seviye = OzeldersEnum.ARA_SINIF;
                if(seviyeStr != null && seviyeStr.contains("Lise")) seviye = OzeldersEnum.TYT_AYT_LGS;

                OzelDers ders = new OzelDers(
                        dersturuTextField.getText(), // Başlık olarak Ders Türü kullanıldı
                        fiyat,
                        null, // Özel derste resim olmayabilir
                        dersTextArea.getText(),
                        "OzelDers",
                        seviye,
                        seviye
                );
                basarili = dao.ozelDersEkle(ders, Oturum.aktifKullaniciId);
            }

            // =========================================================

            if (basarili) {
                showAlert("Başarılı", secilenKategori + " ilanı başarıyla yayınlandı!");
                geridonButtonclick(event); // Ana sayfaya dön
            } else {
                showAlert("Hata", "Veritabanına kayıt sırasında bir sorun oluştu.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Hata", "Beklenmedik bir hata: " + e.getMessage());
        }
    }

    // Yardımcı: Sayı girilmezse hata verip çökmemesi için 0 döndüren metot
    private int tryParseInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
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
