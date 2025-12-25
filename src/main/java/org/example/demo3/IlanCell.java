package org.example.demo3;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class IlanCell extends ListCell<Ilan> {

    @FXML
    private AnchorPane ihAnchorPane;
    @FXML
    private ImageView ihImageView;
    @FXML
    private Label ihFiyatLabel;
    @FXML
    private Label ihBaslikLabel;
    @FXML
    private Button ihIlaniInceleButton;
    @FXML
    private ToggleButton favEkleToggleButton;

    private FXMLLoader mlLoader;

    @Override
    protected void updateItem(Ilan ilan, boolean empty) {
        super.updateItem(ilan, empty);

        if (empty || ilan == null) {
            setText(null);
            setGraphic(null);
        } else {
            // FXML Yükleyiciyi sadece bir kez oluştur (Performans için)
            if (mlLoader == null) {
                mlLoader = new FXMLLoader(getClass().getResource("/Ilan_hucre.fxml"));
                mlLoader.setController(this);

                try {
                    mlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // --- 1. TEMEL BİLGİLERİ DOLDUR ---
            ihBaslikLabel.setText(ilan.getBaslik());
            ihFiyatLabel.setText(ilan.getFiyat() + " TL");

            // --- 2. KAPAK RESMİNİ AYARLA ---
            String resimYollari = ilan.getResimyolu();
            if (resimYollari != null && !resimYollari.isEmpty()) {
                // Birden fazla resim varsa "| " ile ayrılmıştı, ilkini alıyoruz
                String[] yollar = resimYollari.split(" \\| ");
                try {
                    File dosya = new File(yollar[0]);
                    ihImageView.setImage(new Image(dosya.toURI().toString()));
                } catch (Exception e) {
                    // Resim bulunamazsa veya hatalıysa boş bırak
                    ihImageView.setImage(null);
                }
            } else {
                ihImageView.setImage(null);
            }

            // --- 3. FAVORİ SİSTEMİ (VERİTABANI ENTEGRASYONU) ---

            // Önce eski tıklama olayını temizle (Liste kaydırılınca karışmasın diye)
            favEkleToggleButton.setOnAction(null);

            FavoriDAO favDao = new FavoriDAO();

            // A) Başlangıç Durumu: Kullanıcı giriş yapmışsa veritabanından kontrol et
            if (Oturum.aktifKullaniciId > 0) {
                // Veritabanına sor: Bu kullanıcı bu ilanı beğenmiş mi?
                boolean dbFavoriMi = favDao.isFavori(Oturum.aktifKullaniciId, ilan.getId());

                favEkleToggleButton.setSelected(dbFavoriMi); // Kalbi doldur veya boşalt
                ilan.setFavoriMi(dbFavoriMi); // Java nesnesini de güncelle
            } else {
                // Giriş yapmamışsa kalp boş olsun
                favEkleToggleButton.setSelected(false);
            }

            // B) Tıklama Olayı: Kullanıcı kalbe bastığında ne olsun?
            favEkleToggleButton.setOnAction(event -> {
                // 1. Giriş kontrolü
                if (Oturum.aktifKullaniciId == 0) {
                    System.out.println("UYARI: Favorilere eklemek için lütfen giriş yapınız!");
                    favEkleToggleButton.setSelected(false); // İşlemi geri al (Kalbi söndür)
                    return;
                }

                boolean seciliMi = favEkleToggleButton.isSelected();

                if (seciliMi) {
                    // --- VERİTABANINA EKLE ---
                    boolean basarili = favDao.favoriEkle(Oturum.aktifKullaniciId, ilan.getId());
                    if (basarili) {
                        ilan.setFavoriMi(true);
                        System.out.println("DB: Favorilendi -> " + ilan.getBaslik());
                    }
                } else {
                    // --- VERİTABANINDAN SİL ---
                    boolean basarili = favDao.favoriCikar(Oturum.aktifKullaniciId, ilan.getId());
                    if (basarili) {
                        ilan.setFavoriMi(false);
                        System.out.println("DB: Favoriden Çıkarıldı -> " + ilan.getBaslik());
                    }
                }
            });

            // --- 4. İLANI İNCELE BUTONU ---
            ihIlaniInceleButton.setOnAction(event -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ilanigoruntule.fxml"));
                    Parent root = loader.load();

                    // Detay sayfasına veriyi gönder
                    IlanDetayController controller = loader.getController();
                    controller.veriAl(ilan);

                    // Sayfayı değiştir
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException e) {
                    System.err.println("Hata: Detay sayfası (ilanigoruntule.fxml) açılamadı!");
                    e.printStackTrace();
                }
            });

            // Görünümü hücreye ata
            setGraphic(ihAnchorPane);
        }
    }
}