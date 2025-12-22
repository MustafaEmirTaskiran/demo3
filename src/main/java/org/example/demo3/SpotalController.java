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
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SpotalController implements Initializable {
    @FXML
    private ListView<Ilan> ilanListView;

    // 2. İlanları tutacak olan ANA LİSTEMİZ bu
    private ObservableList<Ilan> tumIlanlarListesi;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tumIlanlarListesi = FXCollections.observableArrayList();

        // --- VERİTABANI BAĞLANTISI ---
        try {
            // Veritabanından gerçek verileri çek
            List<Ilan> dbGelenler = VeriTabaniYoneticisi.getInstance().tumIlanlariGetir();
            tumIlanlarListesi.addAll(dbGelenler);

            System.out.println("Veritabanından " + dbGelenler.size() + " adet ilan çekildi.");
        } catch (Exception e) {
            System.err.println("Veritabanı bağlantı hatası: " + e.getMessage());
        }

        // Listeyi Ekrana Bağla
        ilanListView.setItems(tumIlanlarListesi);
        ilanListView.setCellFactory(param -> new IlanCell());

        // ... (Favori buton ve diğer buton kodların aynen kalsın) ...
    }


    // --- DİĞER BUTON VE MENÜ TANIMLAMALARI ---

    @FXML private VBox tasitAltVBox;

    @FXML private Button tasitButton;
    @FXML
    protected void tasitButtonclick(ActionEvent event) {
        boolean isVisible = tasitAltVBox.isVisible();
        tasitAltVBox.setVisible(!isVisible);
        tasitAltVBox.setManaged(!isVisible);
        System.out.println("tasitButtonclick");
        KatogeriFiltrele(ilan -> ilan instanceof Tasit);
        System.out.println("taşitlar listelendi");
    }

    @FXML private Button otomobilButton;
    @FXML protected void otomobilButtonclick() {
        KatogeriFiltrele(ilan -> ilan instanceof Tasit && ((Tasit) ilan).getTasitturu() == Tasitturu.OTOMOBIL);
    }

    @FXML private Button motorButton;
    @FXML protected void motorButtonclick() {
        KatogeriFiltrele(ilan -> ilan instanceof Tasit && ((Tasit) ilan).getTasitturu() == Tasitturu.MOTOR);

    }

    @FXML private Button kamyonetButton;
    @FXML protected void kamyonetButtonclick(){
        KatogeriFiltrele(ilan -> ilan instanceof Tasit && ((Tasit) ilan).getTasitturu() == Tasitturu.KAMYONET);

    }

    @FXML private Button digerButton;
    @FXML protected void digerButtonclick(){
        KatogeriFiltrele(ilan -> ilan instanceof Tasit && ((Tasit) ilan).getTasitturu() == Tasitturu.DIGER);

    }

    @FXML private VBox konutAltVBox;

    @FXML private Button konutButton;
    @FXML
    protected void konutButtonclick(ActionEvent event) {
        boolean isVisible = konutAltVBox.isVisible();
        konutAltVBox.setVisible(!isVisible);
        konutAltVBox.setManaged(!isVisible);
        System.out.println("konutButtonclick");
        KatogeriFiltrele(ilan -> ilan instanceof Konut);

    }

    @FXML private Button arsaButton;
    @FXML protected void arsaButtonclick(){
        KatogeriFiltrele(ilan -> ilan instanceof Konut && ((Konut) ilan).getKonuttipi() == KonutturuEnum.ARSA);

    }

    @FXML private Button daireButton;
    @FXML protected void daireButtonclick(){
        KatogeriFiltrele(ilan -> ilan instanceof Konut && ((Konut) ilan).getKonuttipi() == KonutturuEnum.DAIRE);

    }

    @FXML private Button mustakilButton;
    @FXML protected void mustakilButtonclick(){
        KatogeriFiltrele(ilan -> ilan instanceof Konut && ((Konut) ilan).getKonuttipi() == KonutturuEnum.MUSTAKIL);

    }

    @FXML VBox teknoAltVBox;
    @FXML private Button teknoButton;
    @FXML
    protected void teknoButtonclick(ActionEvent event) {
        boolean isVisible = teknoAltVBox.isVisible();
        teknoAltVBox.setVisible(!isVisible);
        teknoAltVBox.setManaged(!isVisible);
        System.out.println("teknoButtonclick");
        KatogeriFiltrele(ilan -> ilan instanceof Teknoloji);

    }

    @FXML private Button ceptelefonuButton;
    @FXML protected void ceptelefonuButtonclick(){
        KatogeriFiltrele(ilan -> ilan instanceof Teknoloji && ((Teknoloji) ilan).getTeknolojituru() == TeknolojituruEnum.CEP_TELEFONU);

    }

    @FXML private Button bilgisayaraButton;
    @FXML protected void bilgisayaraButtonclick(){
        KatogeriFiltrele(ilan -> ilan instanceof Teknoloji && ((Teknoloji) ilan).getTeknolojituru() == TeknolojituruEnum.BILGISAYAR);

    }

    @FXML private Button beyazesyaButton;
    @FXML protected void beyazesyaButtonclick(){
        KatogeriFiltrele(ilan -> ilan instanceof Teknoloji && ((Teknoloji) ilan).getTeknolojituru() == TeknolojituruEnum.BEYAZ_ESYA);

    }

    @FXML private  Button cpequiptButton;
    @FXML protected void cpequiptButtonclick(){
        KatogeriFiltrele(ilan -> ilan instanceof Teknoloji && ((Teknoloji) ilan).getTeknolojituru() == TeknolojituruEnum.BILGISAYAR_EKIPMANLARI);

    }

    @FXML VBox ozeldersAltVBox;
    @FXML private Button ozeldersButton;
    @FXML
    protected void ozeldersButtonclick(ActionEvent event) {
        boolean isVisible = ozeldersAltVBox.isVisible();
        ozeldersAltVBox.setVisible(!isVisible);
        ozeldersAltVBox.setManaged(!isVisible);
        System.out.println("ozeldersButtonclick");
        KatogeriFiltrele(ilan -> ilan instanceof OzelDers);

    }

    @FXML private Button arasinifButton;
    @FXML protected void arasinifButtonclick(){
        KatogeriFiltrele(ilan -> ilan instanceof OzelDers && ((OzelDers) ilan).getEgitimSeviyesi() == OzeldersEnum.ARA_SINIF);

    }

    @FXML private Button tytButton;
    @FXML protected void tytButtonclick(){
        KatogeriFiltrele(ilan -> ilan instanceof OzelDers && ((OzelDers) ilan).getEgitimSeviyesi() == OzeldersEnum.TYT_AYT_LGS);

    }

    @FXML VBox giyimAltVBox;
    @FXML private Button giyimButton;
    @FXML protected void giyimButtonclick(ActionEvent event) {
        boolean isVisible = giyimAltVBox.isVisible();
        giyimAltVBox.setVisible(!isVisible);
        giyimAltVBox.setManaged(!isVisible);
        System.out.println("giyimButtonclick");
        KatogeriFiltrele(ilan -> ilan instanceof Giyim);

    }

    @FXML private Button ustgiyimButton;
    @FXML protected void ustgiyimButtonclick(){
        KatogeriFiltrele(ilan -> ilan instanceof Giyim && ((Giyim) ilan).getGiyimturu() == GiyimEnum.UST_GIYIM);

    }

    @FXML private Button altgiyimButton;
    @FXML protected void altgiyimButtonclick(){
        KatogeriFiltrele(ilan -> ilan instanceof Giyim && ((Giyim) ilan).getGiyimturu() == GiyimEnum.ALT_GIYIM);

    }

    @FXML private Button kayitButton;
    @FXML
    protected void kayitButtonclick(ActionEvent event){
        System.out.println("kayitButtonclick");
        try {
            URL url = SpotalController.class.getResource("/kayitol.fxml");
            System.out.println("kayit.fxml URL = " + url);

            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(root, 700, 500);
            stage.setScene(scene);
            stage.setTitle("Kayit Ol");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML private Button girisButton;
    @FXML protected void girisButtonclick(){}

    // DÜZELTME BURADA YAPILDI (Eskiden (){} parantezleri vardı)
    @FXML private ToggleButton favButton;

    @FXML private Button filtreButton;
    @FXML protected void filtreButtonclick(){}

    @FXML private Button siralaButton;
    @FXML protected void siralaButtonclick(){}

    @FXML private Button ilanekButton;
    @FXML
    protected void ilanekButtonclick(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ilanekle.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene newScene = new Scene(root);
            stage.setScene(newScene);
            stage.show();

            stage.setTitle("Yeni İlan Ekle");

        } catch (Exception e) {
            System.err.println("Beklenmedik bir hata oluştu.");
            e.printStackTrace();
        }
    }
    // --- FİLTRELEME YARDIMCISI (HER YERDE KULLANACAĞIZ) ---
    private void KatogeriFiltrele(java.util.function.Predicate<Ilan> kriter) {
        ObservableList<Ilan> suzulenListe = FXCollections.observableArrayList();

        for (Ilan ilan : tumIlanlarListesi) {
            // Gönderilen kritere uyuyor mu? (Örn: İlan bir Taşıt mı?)
            if (kriter.test(ilan)) {
                suzulenListe.add(ilan);
            }
        }
        ilanListView.setItems(suzulenListe);
    }
}