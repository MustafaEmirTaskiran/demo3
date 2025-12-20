package org.example.demo3;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.io.File;
import java.io.IOException;

public class IlanekleController {
    @FXML
    protected void gozatButton(ActionEvent event)  {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Lütfen kapak resmi seçiniz");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter ( "Resim Dosyaları" ,"*.jpeg","*.png","*.jpg")
        );
        Stage stage =(Stage)((Node)event.getSource()).getScene().getWindow();
        List<File> secilenDosyalar = fileChooser.showOpenMultipleDialog(stage);
        if (secilenDosyalar != null && !secilenDosyalar.isEmpty()) {

            StringBuilder yollar = new StringBuilder();
            for (File dosya : secilenDosyalar) {
                yollar.append(dosya.getAbsolutePath()).append(" | ");
            }
            resimyoluTextField.setText(yollar.toString());
            System.out.println("Seçilen dosyalar: " + secilenDosyalar.size() + " adet.");
        }
    }

    // --- 1. ORTAK FXML ALANLARI ---
    @FXML private ComboBox<String> kategoriSecici;
    @FXML private TextField baslikTextField;
    @FXML private TextField fiyatTextField;
    @FXML private TextField resimyoluTextField;

    // --- 2. DİNAMİK FORM KONTEYNERLERİ ---
    @FXML private VBox otomobilFormVBox;
    @FXML private VBox teknoFormVBox;
    @FXML private VBox giyimFormVBox;
    @FXML private VBox konutFormVBox;
    @FXML private VBox dersFormVBox;

    // --- 3. OTOMOBİL ÖZEL ALANLARI ---
    @FXML private TextField yakittipiTextField;
    @FXML private TextField kilometreTextField;
    @FXML private TextField tasitturuTextField;
    @FXML private TextField omarkaTextField;
    @FXML private TextField oModelTextField; // Model İsmi
    @FXML private TextField omodelyiliTextField; // Model Yılı (Sayısal)
    @FXML private TextField vitesTextField;
    @FXML private TextArea oaciklamaTextArea;

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
    @FXML private ComboBox<String> cinsiyetComboBox;
    @FXML private TextArea gaciklamaTextArea;

    // --- 6. KONUT ÖZEL ALANLARI ---
    @FXML private TextField konuttipiTextField;
    @FXML private TextField konutyapimyiliTextField;
    @FXML private TextField odasayisiTextField;
    @FXML private TextField sehirTextField;
    @FXML private TextField ilceTextField;
    @FXML private TextField mahalleTextField;
    @FXML private TextField sokakTextField;
    @FXML private TextArea kaciklamaTextArea;

    // --- 7. DERS ÖZEL ALANLARI ---
    @FXML private TextField dersturuTextField;
    // DÜZELTME: TextField yerine ComboBox tanımladık
    @FXML private ComboBox<OzeldersEnum> egitimSeviyesiComboBox;
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

        // ÖZEL DERS SEVİYESİ ComboBox'ını Enum ile doldur
        egitimSeviyesiComboBox.setItems(FXCollections.observableArrayList(OzeldersEnum.values()));

        // Başlangıçta tüm dinamik formları gizle
        gizleTumDinamikFormlar();
    }

    private void gizleTumDinamikFormlar() {
        VBox[] formlar = {otomobilFormVBox, teknoFormVBox, giyimFormVBox, konutFormVBox, dersFormVBox};
        for (VBox form : formlar) {
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

    @FXML
    protected void resimSec(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("İlan Kapak Resmi Seçin");
        FileChooser.ExtensionFilter resimFiltresi = new FileChooser.ExtensionFilter("Resim Dosyaları", "*.jpg", "*.jpeg", "*.png");
        fileChooser.getExtensionFilters().add(resimFiltresi);

        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        File secilenDosya = fileChooser.showOpenDialog(stage);

        if (secilenDosya != null) {
            resimyoluTextField.setText(secilenDosya.getAbsolutePath());
        }
    }

    @FXML
    protected void ilanPaylas(ActionEvent event) {
        String secilenKategori = kategoriSecici.getSelectionModel().getSelectedItem();

        if (secilenKategori == null) {
            System.err.println("Lütfen bir kategori seçin.");
            return;
        }

        try {
            // 1. ORTAK VERİLERİ AL
            String baslik = baslikTextField.getText();
            double fiyat = fiyatTextField.getText().isEmpty() ? 0.0 : Double.parseDouble(fiyatTextField.getText());
            String resimYolu = resimyoluTextField.getText();
            if(resimYolu == null || resimYolu.isEmpty()) resimYolu = "default.png";

            // Veritabanı Yöneticisini Çağır
            VeriTabaniYoneticisi db = VeriTabaniYoneticisi.getInstance();

            // 2. KATEGORİYE GÖRE AYRIŞTIR VE KAYDET
            switch (secilenKategori) {

                case "Otomobil":
                    Tasitturu tTur = Tasitturu.DIGER;
                    try {
                        tTur = Tasitturu.valueOf(tasitturuTextField.getText().toUpperCase().replace("İ", "I"));
                    } catch (Exception e) {
                        System.out.println("Geçersiz taşıt türü girildi, varsayılan 'DIGER' seçildi.");
                    }

                    int km = kilometreTextField.getText().isEmpty() ? 0 : Integer.parseInt(kilometreTextField.getText());
                    int modelYili = omodelyiliTextField.getText().isEmpty() ? 0 : Integer.parseInt(omodelyiliTextField.getText());

                    Tasit yeniTasit = new Tasit(
                            baslik, fiyat, resimYolu, "Otomobil", oaciklamaTextArea.getText(),
                            tTur,
                            yakittipiTextField.getText(),
                            km,
                            omarkaTextField.getText(),
                            modelYili,
                            vitesTextField.getText()
                    );
                    db.tasitEkle(yeniTasit);
                    break;

                case "Konut":
                    KonutturuEnum kTur = KonutturuEnum.DAIRE;
                    try {
                        kTur = KonutturuEnum.valueOf(konuttipiTextField.getText().toUpperCase().replace("İ", "I").replace("Ü", "U"));
                    } catch (Exception e) {
                        System.out.println("Geçersiz konut tipi, varsayılan atandı.");
                    }

                    int yapimYili = konutyapimyiliTextField.getText().isEmpty() ? 0 : Integer.parseInt(konutyapimyiliTextField.getText());

                    Konut yeniKonut = new Konut(
                            baslik, fiyat, resimYolu, "Konut",
                            odasayisiTextField.getText(),
                            kaciklamaTextArea.getText(),
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
                            cinsiyetComboBox.getValue(),
                            gTur
                    );
                    db.giyimEkle(yeniGiyim);
                    break;

                case "Ders":
                    // --- DERS KISMI GÜNCELLENDİ ---

                    // 1. Ders Türünü TextField'dan alıp Enum'a çevir (Örn: TYT_AYT_LGS)
                    OzeldersEnum dTur = OzeldersEnum.ARA_SINIF; // Varsayılan
                    try {
                        // Kullanıcı büyük/küçük harf hatası yaparsa diye önlem
                        dTur = OzeldersEnum.valueOf(dersturuTextField.getText().toUpperCase().replace(" ", "_"));
                    } catch (Exception e) {
                        System.out.println("Geçersiz ders türü, varsayılan atandı.");
                    }

                    // 2. Eğitim Seviyesini ComboBox'tan al (Direkt Enum gelir)
                    OzeldersEnum secilenSeviye = egitimSeviyesiComboBox.getValue();
                    if(secilenSeviye == null) secilenSeviye = OzeldersEnum.ARA_SINIF; // Seçilmediyse

                    OzelDers yeniDers = new OzelDers(
                            baslik,
                            fiyat,
                            resimYolu,
                            dersTextArea.getText(), // Açıklama
                            "Ders",                 // Kategori String
                            dTur,                   // Ders Türü (Enum)
                            secilenSeviye           // Eğitim Seviyesi (Enum - ComboBox'tan)
                    );
                    db.ozelDersEkle(yeniDers);
                    break;
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Başarılı");
            alert.setHeaderText(null);
            alert.setContentText("İlan başarıyla sisteme kaydedildi!");
            alert.showAndWait();

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

    @FXML private Button geridonButton;
    @FXML private void geridonButtonclick(ActionEvent event) {
        try {
            URL url = getClass().getResource("/spotal.fxml");
            if (url == null) {
                url = getClass().getResource("spotal.fxml");
            }
            if (url == null) {
                System.err.println("KRİTİK HATA: spotal.fxml dosyası hiçbir yerde bulunamadı!");
                return;
            }

            FXMLLoader fxmlLoader = new FXMLLoader(url);
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}