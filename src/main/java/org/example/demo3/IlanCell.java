package org.example.demo3;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
import javafx.scene.Node; // Node eklemeyi unutma

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
            if (mlLoader == null) {
                mlLoader = new FXMLLoader(getClass().getResource("/Ilan_hucre.fxml"));
                mlLoader.setController(this);

                try {
                    mlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            // Temel Bilgileri Doldur
            ihBaslikLabel.setText(ilan.getBaslik());
            ihFiyatLabel.setText(ilan.getFiyat() + " TL");

            // Kapak Resmini Ayarla (İlk resmi al)
            String resimYollari = ilan.getResimyolu();
            if (resimYollari != null && !resimYollari.isEmpty()) {
                String[] yollar = resimYollari.split(" \\| ");
                try {
                    File dosya = new File(yollar[0]);
                    ihImageView.setImage(new Image(dosya.toURI().toString()));
                } catch (Exception e) {
                    // Resim yoksa boş geç
                }
            }


            favEkleToggleButton.setOnAction(null);


            favEkleToggleButton.setSelected(ilan.isFavoriMi());


            favEkleToggleButton.setOnAction(event -> {
                boolean seciliMi = favEkleToggleButton.isSelected();


                ilan.setFavoriMi(seciliMi);

                if (seciliMi) {
                    System.out.println(ilan.getBaslik() + " favorilere eklendi");

                } else {
                    System.out.println(ilan.getBaslik() + " favorilerden çıkarıldı");

                }
            });

            // --- İŞTE EKSİK OLAN KISIM BURASI ---
            // İlanı İncele Butonu Bağlantısı
            ihIlaniInceleButton.setOnAction(event -> {
                try {
                    // 1. Detay sayfasının tasarımını yükle
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ilanigoruntule.fxml"));
                    Parent root = loader.load();

                    // 2. Detay sayfasının beynine (Controller) ulaş
                    IlanDetayController controller = loader.getController();

                    // 3. Veriyi (Şu anki ilanı) karşıya gönder!
                    controller.veriAl(ilan);

                    // 4. Sayfayı değiştir
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException e) {
                    System.err.println("Hata: Detay sayfası (ilandetay.fxml) açılamadı!");
                    e.printStackTrace();
                }
            });
            // ------------------------------------

            setGraphic(ihAnchorPane);
        }
    }
}