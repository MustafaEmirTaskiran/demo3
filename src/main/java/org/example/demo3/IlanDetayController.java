package org.example.demo3;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class IlanDetayController {



    // --- 1. ORTAK ALANLAR ---
    @FXML
    private Label igBaslik;
    @FXML
    private Label igFiyat;
    @FXML
    private TextArea igAciklama;
    @FXML
    private HBox igResimEkle; // Resimlerin ekleneceği kutu

    // --- 2. KUTULAR (VBOX - StackPane içindekiler) ---
    @FXML
    private VBox igVBoxTasit;
    @FXML
    private VBox igVBoxKonut;
    @FXML
    private VBox igVBoxTeknoloji;
    @FXML
    private VBox igVBoxGiyim;
    @FXML
    private VBox igVBoxOzelders;

    // --- 3. TAŞIT LABEL'LARI ---
    @FXML
    private Label igTasitTur;
    @FXML
    private Label igOMarka;
    @FXML
    private Label igOModel;
    @FXML
    private Label igKilometre;
    @FXML
    private Label igOModelyili;
    @FXML
    private Label igYakitTipi;
    @FXML
    private Label igVitesTipi;

    // --- 4. KONUT LABEL'LARI ---
    @FXML
    private Label igKonutTipi;
    @FXML
    private Label igYapimYili;
    @FXML
    private Label igOdaSayisi;
    @FXML
    private Label igSehir;
    @FXML
    private Label igIlce;
    @FXML
    private Label igMahalle;
    @FXML
    private Label igSokak;

    // --- 5. TEKNOLOJİ LABEL'LARI ---
    @FXML
    private Label igTeknoTipi;
    @FXML
    private Label igTeknoMarka;
    @FXML
    private Label igTeknoModel;

    // --- 6. GİYİM LABEL'LARI ---
    @FXML
    private Label igGiysiTipi;
    @FXML
    private Label igGiysiMarka;
    @FXML
    private Label igGiysiBeden;
    @FXML
    private Label igAsinmaMiktari;
    @FXML
    private Label igCinsiyet;

    // --- 7. ÖZEL DERS LABEL'LARI ---
    @FXML
    private Label igDersTipi;
    @FXML
    private Label igEgitimSeviyesi;

    public void veriAl(Ilan ilan) {
        kutulariGizle();
        igBaslik.setText(ilan.getBaslik());
        igAciklama.setText(ilan.getAciklama());
        igFiyat.setText(ilan.getFiyat() + "TL");

        resimleriYukle(ilan.getResimyolu());
        if (ilan instanceof Tasit) {
            Tasit t = (Tasit) ilan;
            igVBoxTasit.setVisible(true);
            igVBoxTasit.setManaged(true);
            igTasitTur.setText(t.getTasitturu().toString());
            igOMarka.setText(t.getMarka());
            igOModel.setText("-");
            igKilometre.setText(String.valueOf(t.getKilometre()));
            igOModelyili.setText(String.valueOf(t.getModelYili()));
            igYakitTipi.setText(t.getYakitTipi());
            igVitesTipi.setText(t.getVitesTuru());
        } else if (ilan instanceof Konut) {
            Konut k = (Konut) ilan;
            igVBoxKonut.setVisible(true);
            igVBoxKonut.setManaged(true);
            igKonutTipi.setText(k.getKonuttipi().toString());
            igOdaSayisi.setText(k.getOdaSayisi());
            igYapimYili.setText(String.valueOf(k.getYapimYili()));
            igSehir.setText(k.getSehir());
            igIlce.setText(k.getIlce());
            igMahalle.setText(k.getMahalle());
            igSokak.setText(k.getSokak());
        } else if (ilan instanceof Giyim) {
            Giyim g = (Giyim) ilan;
            igVBoxGiyim.setVisible(true);
            igVBoxGiyim.setManaged(true);
            igGiysiTipi.setText(g.getGiyimturu().toString());
            igAsinmaMiktari.setText(g.getAsinmaMiktari());
            igCinsiyet.setText(g.getCinsiyet());
            igGiysiBeden.setText(g.getBeden());
            igGiysiMarka.setText(g.getMarka());
        } else if (ilan instanceof Teknoloji) {
            Teknoloji tek = (Teknoloji) ilan;
            igVBoxTeknoloji.setVisible(true);
            igVBoxTeknoloji.setManaged(true);
            igTeknoTipi.setText(tek.getTeknolojituru().toString());
            igTeknoMarka.setText(tek.getMarka());
            igTeknoModel.setText(tek.getModel());
        } else if (ilan instanceof OzelDers) {
            OzelDers o = (OzelDers) ilan;
            igVBoxOzelders.setVisible(true);
            igVBoxOzelders.setManaged(true);
            igDersTipi.setText(o.getOzeldersTuru().toString());
            igEgitimSeviyesi.setText(o.getEgitimSeviyesi().toString());
        }


    }
    // Yardımcı Metot: Resimleri parçalayıp ekrana basar
    private void resimleriYukle(String resimYollari) {
        igResimEkle.getChildren().clear(); // Önce temizle

        if (resimYollari == null || resimYollari.isEmpty()) return;

        // " | " işaretine göre yolları ayır
        String[] yollar = resimYollari.split(" \\| ");

        for (String yol : yollar) {
            try {
                File dosya = new File(yol);
                // Resim dosyasını yükle
                Image img = new Image(dosya.toURI().toString());
                ImageView imgView = new ImageView(img);

                // Boyut ayarları (HBox içine sığsın diye)
                imgView.setFitHeight(200);
                imgView.setPreserveRatio(true);

                // Kutuya ekle
                igResimEkle.getChildren().add(imgView);

            } catch (Exception e) {
                System.out.println("Resim yüklenemedi: " + yol);
            }
        }
    }
    private void kutulariGizle() {
        igVBoxTasit.setVisible(false);
        igVBoxKonut.setVisible(false);
        igVBoxTeknoloji.setVisible(false);
        igVBoxGiyim.setVisible(false);
        igVBoxOzelders.setVisible(false);
    }
    @FXML
    public void igGeriDon(ActionEvent event) {
        try {
            // 1. Önce kaynak klasörünün en tepesine bak (/spotal.fxml)
            String dosyaAdi = "/spotal.fxml";
            java.net.URL url = getClass().getResource(dosyaAdi);

            // 2. Eğer orada yoksa, bu sınıfın olduğu pakete bak (spotal.fxml)
            if (url == null) {
                System.out.println(dosyaAdi + " bulunamadı, yan klasörlere bakılıyor...");
                dosyaAdi = "spotal.fxml"; // Başındaki / işaretini kaldırdık
                url = getClass().getResource(dosyaAdi);
            }

            // 3. Hala yoksa hata ver ve dur (Program çökmesin)
            if (url == null) {
                System.err.println("KRİTİK HATA: spotal.fxml dosyası hiçbir yerde bulunamadı!");
                System.err.println("Lütfen dosyanın adının (büyük/küçük harf) ve yerinin doğru olduğundan emin ol.");
                return;
            }

            // 4. Dosya bulunduysa yükle
            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



































