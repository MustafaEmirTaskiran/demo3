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
import java.util.Objects;

public class IlanCell extends ListCell<Ilan> {

    @FXML private AnchorPane ihAnchorPane;
    @FXML private ImageView ihImageView;
    @FXML private Label ihFiyatLabel;
    @FXML private Label ihBaslikLabel;
    @FXML private Button ihIlaniInceleButton;
    @FXML private ToggleButton favEkleToggleButton;

    private FXMLLoader mlLoader;

    // --- RESİM YÜKLEME KISMI (İsimleri senin verdiklerinle güncelledim) ---
    // NOT: Dosyaların uzantısı .png değilse (örn: .jpg) aşağıdan değiştirmelisin.
    private static final Image KALP_BOS_IMG = new Image(Objects.requireNonNull(IlanCell.class.getResourceAsStream("/Bos_Kalp_Icon.png")));
    private static final Image KALP_DOLU_IMG = new Image(Objects.requireNonNull(IlanCell.class.getResourceAsStream("/Dolu_Kalp_Icon.png")));

    // Her hücre için ikon görünümü
    private final ImageView bosKalpView = new ImageView(KALP_BOS_IMG);
    private final ImageView doluKalpView = new ImageView(KALP_DOLU_IMG);

    public IlanCell() {
        // İkon boyutlarını ideal boyuta (24x24) ayarlıyoruz
        bosKalpView.setFitHeight(24); bosKalpView.setFitWidth(24);
        doluKalpView.setFitHeight(24); doluKalpView.setFitWidth(24);
    }

    // İkon değiştirme yardımcısı
    private void updateFavoriIcon(boolean favoriMi) {
        if (favoriMi) {
            favEkleToggleButton.setGraphic(doluKalpView);
        } else {
            favEkleToggleButton.setGraphic(bosKalpView);
        }
        favEkleToggleButton.setText("");
    }

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
                try { mlLoader.load(); } catch (IOException e) { e.printStackTrace(); }
            }

            // Bilgileri Doldur
            ihBaslikLabel.setText(ilan.getBaslik());
            ihFiyatLabel.setText(ilan.getFiyat() + " TL");

            // İlan Resmini Ayarla
            String resimYollari = ilan.getResimyolu();
            if (resimYollari != null && !resimYollari.isEmpty()) {
                String[] yollar = resimYollari.split(" \\| ");
                try {
                    File dosya = new File(yollar[0]);
                    ihImageView.setImage(new Image(dosya.toURI().toString()));
                } catch (Exception e) { ihImageView.setImage(null); }
            } else { ihImageView.setImage(null); }

            // --- FAVORİ İKONU MANTIĞI ---
            favEkleToggleButton.setOnAction(null);
            FavoriDAO favDao = new FavoriDAO();

            // Başlangıç Durumu
            if (Oturum.aktifKullaniciId > 0) {
                boolean dbFavoriMi = favDao.isFavori(Oturum.aktifKullaniciId, ilan.getId());
                favEkleToggleButton.setSelected(dbFavoriMi);
                ilan.setFavoriMi(dbFavoriMi);
                updateFavoriIcon(dbFavoriMi); // İkonu güncelle
            } else {
                favEkleToggleButton.setSelected(false);
                updateFavoriIcon(false); // Boş kalp
            }

            // Tıklama Olayı
            favEkleToggleButton.setOnAction(event -> {
                if (Oturum.aktifKullaniciId == 0) {
                    System.out.println("UYARI: Giriş yapmalısınız!");
                    favEkleToggleButton.setSelected(false);
                    updateFavoriIcon(false);
                    return;
                }

                boolean seciliMi = favEkleToggleButton.isSelected();
                if (seciliMi) {
                    if (favDao.favoriEkle(Oturum.aktifKullaniciId, ilan.getId())) {
                        ilan.setFavoriMi(true);
                        updateFavoriIcon(true); // Dolu kalp yap
                        System.out.println("Favorilendi: " + ilan.getBaslik());
                    }
                } else {
                    if (favDao.favoriCikar(Oturum.aktifKullaniciId, ilan.getId())) {
                        ilan.setFavoriMi(false);
                        updateFavoriIcon(false); // Boş kalp yap
                        System.out.println("Favori Silindi: " + ilan.getBaslik());
                    }
                }
            });

            // Detay Butonu
            ihIlaniInceleButton.setOnAction(event -> {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/ilanigoruntule.fxml"));
                    Parent root = loader.load();
                    IlanDetayController controller = loader.getController();
                    controller.veriAl(ilan);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException e) { e.printStackTrace(); }
            });

            setGraphic(ihAnchorPane);
        }
    }
}