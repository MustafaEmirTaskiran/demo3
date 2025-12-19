package org.example.demo3;


import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.util.List;



import java.io.File;
import java.io.IOException;

public class IlanekleController {
    @FXML
    protected void gozatButton(ActionEvent event)  {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("lütfen kapak resmi seçiniz");
        fileChooser.getExtensionFilters().addAll(
             new FileChooser.ExtensionFilter ( "resim dosyaları" ,"*.jpeg","*.png","*.jpg")
        );
        Stage stage =(Stage)((Node)event.getSource()).getScene().getWindow();
        List<File> secilenDosyalar = fileChooser.showOpenMultipleDialog(stage);
        if (secilenDosyalar != null && !secilenDosyalar.isEmpty()) {

            // Seçilen tüm dosya yollarını birleştirmek için StringBuilder kullanıyoruz
            StringBuilder yollar = new StringBuilder();

            for (File dosya : secilenDosyalar) {
                // Her dosyanın yolunu ekle ve sonuna " | " işareti koy
                yollar.append(dosya.getAbsolutePath()).append(" | ");
            }

            // Sonuç şöyle görünecek: "C:\resim1.jpg | C:\resim2.jpg | "
            // Sondaki fazlalık " | " işaretini temizlemek istersen opsiyoneldir ama TextField'a basalım:
            resimyoluTextField.setText(yollar.toString());

            System.out.println("Seçilen dosyalar: " + secilenDosyalar.size() + " adet.");
        }



    }
    // --- 1. ORTAK FXML ALANLARI (Kırmızı VBox) ---
    @FXML private ComboBox<String> kategoriSecici; // FXML'de katogeriSecici olarak görünüyor, Java'da düzeltildi
    @FXML private TextField baslikTextField;
    @FXML private TextField fiyatTextField;
    @FXML private TextField resimyoluTextField; // Gözat butonunun yanındaki TextField

    // --- 2. DİNAMİK FORM KONTEYNERLERİ (dinamikFormKonteyniri içindeki VBox'lar) ---
    @FXML private VBox otomobilFormVBox;
    @FXML private VBox teknoFormVBox;
    @FXML private VBox giyimFormVBox;
    @FXML private VBox konutFormVBox;
    @FXML private VBox dersFormVBox;

    // --- 3. OTOMOBİL ÖZEL ALANLARI ---
    @FXML private TextField yakittipiTextField;
    @FXML private TextField kilometreTextField;
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
            resimyoluTextField.setText(secilenDosya.getAbsolutePath());
        }
    }


    // --- İLANI KAYDETME ---
    // FXML'deki 'İlanı Paylaş' butonunun onAction'ı #ilanPaylas
    @FXML
    protected void ilanPaylas(ActionEvent event)
        {
            String secilenKategori = kategoriSecici.getSelectionModel().getSelectedItem();

            if (secilenKategori == null) {
                System.err.println("Lütfen bir kategori seçin.");
                return;
            }

            try {
                // 1. ORTAK VERİLERİ AL
                String baslik = baslikTextField.getText();
                // Fiyat boşsa 0.0 kabul et veya hata fırlat
                double fiyat = fiyatTextField.getText().isEmpty() ? 0.0 : Double.parseDouble(fiyatTextField.getText());
                String resimYolu = resimyoluTextField.getText();
                if(resimYolu == null || resimYolu.isEmpty()) resimYolu = "default.png"; // Resim seçilmezse

                // Veritabanı Yöneticisini Çağır
                VeriTabaniYoneticisi db = VeriTabaniYoneticisi.getInstance();

                // 2. KATEGORİYE GÖRE AYRIŞTIR VE KAYDET
                switch (secilenKategori) {

                    case "Otomobil":
                        // String'den Enum'a dönüşüm (OTOMOBIL, MOTOR, KAMYONET...)
                        // Kullanıcı TextField'a "Otomobil" yazarsa -> "OTOMOBIL" enumunu bulur.
                        Tasitturu tTur = Tasitturu.DIGER;
                        try {
                            tTur = Tasitturu.valueOf(tasitturuTextField.getText().toUpperCase().replace("İ", "I"));
                        } catch (Exception e) {
                            System.out.println("Geçersiz taşıt türü girildi, varsayılan 'DIGER' seçildi.");
                        }

                        int km = kilometreTextField.getText().isEmpty() ? 0 : Integer.parseInt(kilometreTextField.getText());
                        int modelYili = omodelTextField.getText().isEmpty() ? 0 : Integer.parseInt(omodelTextField.getText()); // Senin FXML'de ID omodelTextField ama label Model Yılı idi.

                        Tasit yeniTasit = new Tasit(
                                baslik, fiyat, resimYolu, "Otomobil", oaciklamaTextArea.getText(),
                                tTur,
                                yakittipiTextField.getText(),
                                km,
                                omarkaTextField.getText(), // FXML ID'sinde 'TextFied' yazım hatası var, aynen kullandım
                                modelYili,
                                vitesTextField.getText()
                        );

                        db.tasitEkle(yeniTasit);
                        break;

                    case "Konut":
                        KonutturuEnum kTur = KonutturuEnum.DAIRE; // Varsayılan
                        try {
                            kTur = KonutturuEnum.valueOf(konuttipiTextField.getText().toUpperCase().replace("İ", "I").replace("Ü", "U"));
                        } catch (Exception e) {
                            System.out.println("Geçersiz konut tipi, varsayılan atandı.");
                        }

                        int yapimYili = konutyapimyiliTextField.getText().isEmpty() ? 0 : Integer.parseInt(konutyapimyiliTextField.getText());

                        Konut yeniKonut = new Konut(
                                baslik, fiyat, resimYolu, "Konut",
                                odasayisiTextField.getText(),
                                kaciklamaTextArea.getText(), // Dikkat: FXML'de aciklamaTextArea, kodda kaciklamaTextArea olabilir, FXML'e bak
                                sehirTextField.getText(),
                                ilceTextField.getText(),
                                mahalleTextField.getText(),
                                sokakTextField.getText(),
                                kTur,
                                yapimYili
                        );

                        db.konutEkle(yeniKonut);
                        break;

                    case "Teknoloji":
                        TeknolojituruEnum tekTur = TeknolojituruEnum.BILGISAYAR;
                        try {
                            tekTur = TeknolojituruEnum.valueOf(teknoturuTextField.getText().toUpperCase().replace(" ", "_"));
                        } catch (Exception e) {}

                        Teknoloji yeniTekno = new Teknoloji(
                                baslik, fiyat, resimYolu, "Teknoloji", taciklamaTextArea.getText(),
                                tmarkaTextField.getText(),
                                tmodelTextField.getText(),
                                tekTur
                        );
                        db.teknolojiEkle(yeniTekno);
                        break;

                    case "Giyim":
                        GiyimEnum gTur = GiyimEnum.UST_GIYIM;
                        try {
                            gTur = GiyimEnum.valueOf(giyimturTextField.getText().toUpperCase().replace(" ", "_"));
                        } catch(Exception e) {}

                        Giyim yeniGiyim = new Giyim(
                                baslik, fiyat, resimYolu, "Giyim", gaciklamaTextArea.getText(),
                                gmarkaTextField.getText(),
                                asinmamiktarTextField.getText(),
                                gbedenTextField.getText(),
                                cinsiyetComboBox.getValue(), // ComboBox'tan geliyor
                                gTur
                        );
                        db.giyimEkle(yeniGiyim);
                        break;

                    case "Ders":
                        OzeldersEnum dTur = OzeldersEnum.ARA_SINIF;
                        // Enum çevirimi buraya da eklenebilir

                        OzelDers yeniDers = new OzelDers(
                                baslik, fiyat, resimYolu, dersTextArea.getText(), "Ders",
                                dTur,
                                egitimseviyesiTextField.getText()
                        );
                        db.ozelDersEkle(yeniDers);
                        break;
                }

                // İşlem başarılı mesajı
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Başarılı");
                alert.setHeaderText(null);
                alert.setContentText("İlan başarıyla sisteme kaydedildi!");
                alert.showAndWait();

                // Başarılı ise sayfayı kapatabilir veya formu temizleyebilirsin
                // ((Node)(event.getSource())).getScene().getWindow().hide();

            } catch (NumberFormatException e) {
                System.err.println("Hata: Sayısal alanlara (Fiyat, KM, Yıl) lütfen sadece rakam giriniz.");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Fiyat, Kilometre veya Yıl alanlarına sadece sayı giriniz!");
                alert.show();
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Beklenmedik bir hata oluştu: " + e.getMessage());
            }
        }
}

