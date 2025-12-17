package org.example.demo3;


import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class IlanekleController {
    @FXML
    protected void gozatButton(){}
    // --- 1. ORTAK FXML ALANLARI (Kırmızı VBox) ---
    @FXML private ComboBox<String> kategoriSecici; // FXML'de katogeriSecici olarak görünüyor, Java'da düzeltildi
    @FXML private TextField baslikTextField;
    @FXML private TextField fiyatTextField;
    @FXML private TextField resimYoluTextField; // Gözat butonunun yanındaki TextField

    // --- 2. DİNAMİK FORM KONTEYNERLERİ (dinamikFormKonteyniri içindeki VBox'lar) ---
    @FXML private VBox otomobilFormVBox;
    @FXML private VBox teknoFormVBox;
    @FXML private VBox giyimFormVBox;
    @FXML private VBox konutFormVBox;
    @FXML private VBox dersFormVBox;

    // --- 3. OTOMOBİL ÖZEL ALANLARI ---
    @FXML private TextField tasitturuTextField; // FXML'de var
    @FXML private TextField omarkaTextField;    // FXML'de var (omarkaTextField olarak düzeltilmeli)
    @FXML private TextField omodelTextField;   // FXML'de var
    @FXML private TextField vitesTextField;    // FXML'de var
    @FXML private TextArea oaciklamaTextArea; // FXML'de var

    // --- 4. TEKNOLOJİ ÖZEL ALANLARI ---
    @FXML private TextField teknoturuTextField;
    @FXML private TextField tmarkaTextField;
    @FXML private TextField tmodelTextField;
    @FXML private TextArea taciklamaTextArea;

    // --- 5. GİYİM ÖZEL ALANLARI ---
    @FXML private TextField giyimturTextField;
    @FXML private TextField gmarkaTextField;
    @FXML private TextField gbedenTextField;
    @FXML private TextField asinmamiktarTextField;
    @FXML private ComboBox<String> cinsiyetComboBox; // ComboBox'ın da tipi tanımlanmalı
    @FXML private TextArea gaciklamaTextArea;

    // --- 6. KONUT ÖZEL ALANLARI ---
    @FXML private TextField konuttipiTextField;
    @FXML private TextField konutyapimyiliTextField;
    @FXML private TextField odasayisiTextField;
    @FXML private TextField sehirTextField;
    @FXML private TextField ilceTextField;
    @FXML private TextField mahalleTextField;
    @FXML private TextField sokakTextField;
    @FXML private TextArea kaciklamaTextArea; // FXML'deki adı aciklamaTextArea idi, çakışmayı önlemek için düzeltildi

    // --- 7. DERS ÖZEL ALANLARI ---
    @FXML private TextField dersturuTextField;
    @FXML private TextField egitimseviyesiTextField;
    @FXML private TextArea dersTextArea;


    // --- BAŞLANGIÇ VE DİNAMİK YÖNETİM ---

    @FXML
    public void initialize() {
        // Kategori ComboBox'ı doldur
        kategoriSecici.setItems(FXCollections.observableArrayList(
                "Otomobil", "Teknoloji", "Giyim", "Konut", "Ders"
        ));

        // Cinsiyet ComboBox'ı doldur
        cinsiyetComboBox.setItems(FXCollections.observableArrayList("Erkek", "Kadın", "Unisex"));

        // ComboBox'ta seçim yapıldığında kategoridegisti metodunu çağır
        kategoriSecici.setOnAction(this::kategoriDegisti);

        // Başlangıçta tüm dinamik formları gizle
        gizleTumDinamikFormlar();
    }

    private void gizleTumDinamikFormlar() {
        // Tüm dinamik form VBox'ları
        VBox[] formlar = {otomobilFormVBox, teknoFormVBox, giyimFormVBox, konutFormVBox, dersFormVBox};
        for (VBox form : formlar) {
            // FXML'de bu VBox'lara visible=false ve managed=false atadığınızdan emin olun!
            if (form != null) {
                form.setVisible(false);
                form.setManaged(false);
            }
        }
    }

    private void kategoriDegisti(ActionEvent event) {
        String secilenKategori = kategoriSecici.getSelectionModel().getSelectedItem();

        gizleTumDinamikFormlar();

        if (secilenKategori == null) return;

        switch (secilenKategori) {
            case "Otomobil":
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
            case "Ders":
                dersFormVBox.setVisible(true);
                dersFormVBox.setManaged(true);
                break;
        }
    }

    // --- RESİM SEÇME (FILE CHOOSER) ---
    // FXML'deki 'gözat..' butonunun onAction'ı #resimSec olarak ayarlanmalı
    @FXML
    protected void resimSec(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("İlan Kapak Resmi Seçin");
        FileChooser.ExtensionFilter resimFiltresi = new FileChooser.ExtensionFilter("Resim Dosyaları", "*.jpg", "*.jpeg", "*.png");
        fileChooser.getExtensionFilters().add(resimFiltresi);

        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

        File secilenDosya = fileChooser.showOpenDialog(stage);

        if (secilenDosya != null) {
            // Mutlak yolun TextField'a yazılması
            resimYoluTextField.setText(secilenDosya.getAbsolutePath());
        }
    }


    // --- İLANI KAYDETME ---
    // FXML'deki 'İlanı Paylaş' butonunun onAction'ı #ilanPaylas
    @FXML
    protected void ilanPaylas(ActionEvent event) {
        String secilenKategori = kategoriSecici.getSelectionModel().getSelectedItem();

        if (secilenKategori == null) {
            System.err.println("Lütfen bir kategori seçin.");
            return;
        }

        try {
            // Ortak Veri Alımı ve Validasyon
            String baslik = baslikTextField.getText();
            double fiyat = Double.parseDouble(fiyatTextField.getText());
            String resimYolu = resimYoluTextField.getText();
            Ilan yeniIlan = null;

            if ("Otomobil".equals(secilenKategori)) {

                // Otomobil verileri
                String tasitTuru = tasitturuTextField.getText();
                String marka = omarkaTextField.getText();
                String model = omodelTextField.getText(); // FXML'de model yılı
                String vites = vitesTextField.getText();
                String aciklama = oaciklamaTextArea.getText();

                // ILan Servisine Ekleme (YeniIlan = new OtomobilIlan(...));
                // **BU KISIMDA ILAN VE OTOMOBILILAN SINIFLARINIZIN CONSTRUCTOR'INI KULLANACAKSINIZ.**

                // Örnek:
                // yeniIlan = new OtomobilIlan(baslik, fiyat, resimYolu, aciklama, model, marka, vites);

                // Şimdilik sadece konsola yazdıralım:
                System.out.println("Otomobil İlanı Hazır. Marka: " + marka);

            } else if ("Konut".equals(secilenKategori)) {

                String konutTipi = konuttipiTextField.getText();
                String sehir = sehirTextField.getText();
                // ... KonutIlan nesnesi oluşturulacak ...
                System.out.println("Konut İlanı Hazır. Şehir: " + sehir);

            }
            // ... Diğer Kategoriler (Teknoloji, Giyim, Ders) ...

            if (yeniIlan != null) {
                // IlanServisi.getInstance().ilanEkle(yeniIlan);
                // İlan başarıyla eklendikten sonra ana sayfaya dönme.
            }

        } catch (NumberFormatException e) {
            System.err.println("Hata: Fiyat veya sayısal bir alan yanlış girildi.");
        } catch (Exception e) {
            System.err.println("Beklenmedik bir hata oluştu: " + e.getMessage());
        }
    }
}

