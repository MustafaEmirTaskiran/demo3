package org.example.demo3;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import javafx.scene.image.ImageView;

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
        }
        else {
            if (mlLoader == null) {
                mlLoader = new FXMLLoader(getClass().getResource("/Ilan_hucre.fxml"));
                mlLoader.setController(this);

                try {
                    mlLoader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            ihBaslikLabel.setText(ilan.getBaslik());
            ihFiyatLabel.setText(ilan.getFiyat() + "TL");

            favEkleToggleButton.setOnAction(event -> {
                if (favEkleToggleButton.isSelected()) {
                    System.out.println(ilan.getBaslik() + "ilan favorilere eklendi");
                } else {
                    System.out.println(ilan.getBaslik() + "ilan favorilerden çıkarıldı");
                }
            });
            ihIlaniInceleButton.setOnAction(event -> {
                System.out.println(ilan.getBaslik() + "ilan sayfasına gidiliyor");

            });
            setGraphic(ihAnchorPane);

        }
    }


}
