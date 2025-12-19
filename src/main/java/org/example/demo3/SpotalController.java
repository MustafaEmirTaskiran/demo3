package org.example.demo3;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SpotalController implements Initializable {
    @FXML
    private ListView<Ilan> ilanListView;

    // 2. İlanları tutacak olan ANA LİSTEMİZ bu
    private ObservableList<Ilan> tumIlanlarListesi;
    private ObservableList<Ilan> gosterilenListe;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Listeyi hafızada oluştur
        tumIlanlarListesi = FXCollections.observableArrayList();



        // 3. Listeyi ve Özel Tasarımı ListView'a Bağla
        ilanListView.setItems(tumIlanlarListesi); // Verileri gönder
        ilanListView.setCellFactory(param -> new IlanCell()); // Tasarımı gönder
        favButton.setOnAction(event -> {
            if (favButton.isSelected()) {
                // Sadece favori olanları ayıkla
                ObservableList<Ilan> sadeceFavoriler = FXCollections.observableArrayList();
                for (Ilan ilan : tumIlanlarListesi) {
                    if (ilan.isFavoriMi()) { // Ilan sınıfında bu kontrol olmalı
                        sadeceFavoriler.add(ilan);
                    }
                }
                // Listeyi güncelle
                ilanListView.setItems(sadeceFavoriler);
                favButton.setText("Tüm İlanları Göster");
            } else {
                // Tekrar hepsini göster
                ilanListView.setItems(tumIlanlarListesi);
                favButton.setText("Sadece Favoriler");
            }
        });



    }





    @FXML private VBox tasitAltVBox;



    @FXML private Button tasitButton;
    @FXML
    protected void tasitButtonclick(ActionEvent event) {
        boolean isVisible = tasitAltVBox.isVisible();
        tasitAltVBox.setVisible(!isVisible);
        tasitAltVBox.setManaged(!isVisible);
        System.out.println("tasitButtonclick");

    }

    @FXML private Button otomobilButton;
    @FXML
    protected void otomobilButtonclick() {}

    @FXML private Button motorButton;
    @FXML
    protected void motorButtonclick() {}

    @FXML private Button kamyonetButton;
    @FXML
    protected void kamyonetButtonclick(){}

    @FXML private Button digerButton;
    @FXML
    protected void digerButtonclick(){}

    @FXML private VBox konutAltVBox;

    @FXML private Button konutButton;
    @FXML
    protected void konutButtonclick(ActionEvent event) {
        boolean isVisible = konutAltVBox.isVisible();
        konutAltVBox.setVisible(!isVisible);
        konutAltVBox.setManaged(!isVisible);
        System.out.println("konutButtonclick");
    }

    @FXML private Button arsaButton;
    @FXML
    protected void arsaButtonclick(){}

    @FXML private Button daireButton;
    @FXML
    protected void daireButtonclick(){}

    @FXML private Button mustakilButton;
    @FXML
    protected void mustakilButtonclick(){}

    @FXML VBox teknoAltVBox;
    @FXML private Button teknoButton;
    @FXML
    protected void teknoButtonclick(ActionEvent event) {
        boolean isVisible = teknoAltVBox.isVisible();
        teknoAltVBox.setVisible(!isVisible);
        teknoAltVBox.setManaged(!isVisible);
        System.out.println("teknoButtonclick");
    }

    @FXML private Button ceptelefonuButton;
    @FXML
    protected void ceptelefonuButtonclick(){}

    @FXML private Button bilgisayaraButton;
    @FXML
    protected void bilgisayaraButtonclick(){}

    @FXML private Button beyazesyaButton;
    @FXML
    protected void beyazesyaButtonclick(){}

    @FXML private  Button cpequiptButton;
    @FXML
    protected void cpequiptButtonclick(){}

    @FXML VBox ozeldersAltVBox;
    @FXML private Button ozeldersButton;
    @FXML
    protected void ozeldersButtonclick(ActionEvent event) {
        boolean isVisible = ozeldersAltVBox.isVisible();
        ozeldersAltVBox.setVisible(!isVisible);
        ozeldersAltVBox.setManaged(!isVisible);
        System.out.println("ozeldersButtonclick");
    }

    @FXML private Button arasinifButton;
    @FXML
    protected void arasinifButtonclick(){}

    @FXML private Button tytButton;
    @FXML
    protected void tytButtonclick(){}

    @FXML VBox giyimAltVBox;
    @FXML private Button giyimButton;
    @FXML protected void giyimButtonclick(ActionEvent event) {
        boolean isVisible = giyimAltVBox.isVisible();
        giyimAltVBox.setVisible(!isVisible);
        giyimAltVBox.setManaged(!isVisible);
        System.out.println("giyimButtonclick");
    }

    @FXML private Button ustgiyimButton;
    @FXML
    protected void ustgiyimButtonclick(){}

    @FXML private Button altgiyimButton;
    @FXML
    protected void altgiyimButtonclick(){}

    @FXML private Button kayitButton;
    @FXML
    protected void kayitButtonclick(ActionEvent event){
        System.out.println("kayitButtonclick");
        try {

            URL url = SpotalController.class.getResource("/kayitol.fxml");
            System.out.println("kayit.fxml URL = " + url);

            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource())
                    .getScene().getWindow();

            Scene scene = new Scene(root, 700, 500);
            stage.setScene(scene);
            stage.setTitle("Kayit Ol");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML private Button girisButton;
    @FXML
    protected void girisButtonclick(){}

    @FXML private ToggleButton favButton;


    @FXML private Button filtreButton;
    @FXML
    protected void filtreButtonclick(){}

    @FXML private Button siralaButton;
    @FXML
    protected void siralaButtonclick(){}

    @FXML private Button ilanekButton;
    @FXML
    protected void ilanekButtonclick(ActionEvent event){
        try {
            // 1. Yeni FXML dosyasını yükle
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ilanekle.fxml"));
            Parent root = loader.load();

            // 2. Mevcut Stage (Pencere) bilgisini al
            // (Bu, 'ilanekButton'un ait olduğu penceredir)
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // 3. Mevcut Stage'in Scene'ini yeni yüklenen kök öğesi (root) ile değiştir
            // NOT: Scene'i tamamen yenilemek yerine, root'u değiştirmek de bir yöntemdir

            // Mevcut Scene'i al
            Scene currentScene = stage.getScene();

            Scene newScene = new Scene(root);
            stage.setScene(newScene);
            stage.show();

            stage.setTitle("Yeni İlan Ekle");
            // stage.show() çağrısına gerek yok, zaten görünür durumdadır.


        } catch (Exception e) {
            System.err.println("Beklenmedik bir hata oluştu.");
            e.printStackTrace();
        }
    }

}




