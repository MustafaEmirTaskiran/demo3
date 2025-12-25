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
import java.util.ResourceBundle;

public class SpotalController implements Initializable {
    @FXML
    private ListView<Ilan> ilanListView;

    // 2. İlanları tutacak olan ANA LİSTEMİZ bu
    private ObservableList<Ilan> tumIlanlarListesi;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 1. Listeyi hafızada oluştur
        tumIlanlarListesi = FXCollections.observableArrayList();

        // ===========================================================================
        // --- 1. TAŞIT ÖRNEKLERİ (Filtreler: Otomobil, Motor, Kamyonet) ---
        // ===========================================================================

        Tasit araba1 = new Tasit(
                "Sahibinden Hatasız BMW 5.20",
                2250000.0,
                "C:\\resimler\\bmw.jpg",
                "Otomobil",
                "Makam aracı olarak kullanıldı, hatasız boyasız.",
                Tasitturu.OTOMOBIL, "Dizel", 120000, "BMW", 2018, "Otomatik"
        );

        Tasit araba2 = new Tasit(
                "Acil Satılık Fiat Egea",
                850000.0,
                "C:\\resimler\\egea.jpg",
                "Otomobil",
                "Aile aracı, bakımları yeni yapıldı.",
                Tasitturu.OTOMOBIL, "Benzin/LPG", 45000, "Fiat", 2021, "Manuel"
        );

        Tasit motor1 = new Tasit(
                "Yamaha R25 - Düşük KM",
                210000.0,
                "C:\\resimler\\motor.jpg",
                "Otomobil", // Kategori Stringi (Filtre Enum'dan çalışır)
                "Garaj motorudur, kazası yok.",
                Tasitturu.MOTOR, "Benzin", 5000, "Yamaha", 2023, "Manuel"
        );

        Tasit kamyon1 = new Tasit(
                "İş Değişikliği Nedeniyle Ford Transit",
                600000.0,
                "C:\\resimler\\kamyonet.jpg",
                "Otomobil",
                "Yük taşımaya uygun, kasalı.",
                Tasitturu.KAMYONET, "Dizel", 250000, "Ford", 2015, "Manuel"
        );

        // ===========================================================================
        // --- 2. KONUT ÖRNEKLERİ (Filtreler: Daire, Arsa, Müstakil) ---
        // ===========================================================================

        Konut ev1 = new Konut(
                "Merkezde 3+1 Kiralık Daire",
                25000.0,
                "C:\\resimler\\ev1.jpg",
                "Konut",
                "3+1", "Metroya yürüme mesafesinde.",
                "Ankara", "Çankaya", "Bahçelievler", "7. Cadde",
                KonutturuEnum.DAIRE, 15
        );

        Konut villa1 = new Konut(
                "Deniz Manzaralı Lüks Villa",
                15000000.0,
                "C:\\resimler\\villa.jpg",
                "Konut",
                "5+2", "Havuzlu, güvenlikli site içerisinde.",
                "İzmir", "Urla", "Kekliktepe", "Manzara Sk.",
                KonutturuEnum.MUSTAKIL, 2
        );

        Konut arsa1 = new Konut(
                "Yatırımlık İmarlı Arsa",
                3000000.0,
                "C:\\resimler\\arsa.jpg",
                "Konut",
                "-", "Köşe parsel, %30 imarlı.",
                "Bursa", "Nilüfer", "Görükle", "-",
                KonutturuEnum.ARSA, 0
        );

        // ===========================================================================
        // --- 3. TEKNOLOJİ ÖRNEKLERİ (Filtreler: Telefon, PC, Beyaz Eşya) ---
        // ===========================================================================

        Teknoloji telefon1 = new Teknoloji(
                "iPhone 14 Pro - Çiziksiz",
                55000.0,
                "C:\\resimler\\iphone.jpg",
                "Teknoloji",
                "Pil sağlığı %95, garantisi devam ediyor.",
                "Apple", "14 Pro", TeknolojituruEnum.CEP_TELEFONU
        );

        Teknoloji pc1 = new Teknoloji(
                "Oyun Bilgisayarı RTX 4060",
                35000.0,
                "C:\\resimler\\pc.jpg",
                "Teknoloji",
                "Tüm oyunları ultra ayarda açar.",
                "Asus", "Tuf Gaming", TeknolojituruEnum.BILGISAYAR
        );

        Teknoloji beyazEsya = new Teknoloji(
                "Arçelik No-Frost Buzdolabı",
                15000.0,
                "C:\\resimler\\dolap.jpg",
                "Teknoloji",
                "Taşınma sebebiyle satılık, sorunsuz.",
                "Arçelik", "XXL Model", TeknolojituruEnum.BEYAZ_ESYA
        );

        // ===========================================================================
        // --- 4. GİYİM ÖRNEKLERİ (Filtreler: Üst, Alt) ---
        // ===========================================================================

        Giyim mont = new Giyim(
                "North Face Kışlık Mont",
                4500.0,
                "C:\\resimler\\mont.jpg",
                "Giyim",
                "Sadece 2 kere giyildi, orijinal.",
                "North Face", "Az Kullanılmış", "L", "Erkek", GiyimEnum.UST_GIYIM
        );

        Giyim pantolon = new Giyim(
                "Levi's Kot Pantolon",
                800.0,
                "C:\\resimler\\kot.jpg",
                "Giyim",
                "Rengi solmamış, temiz.",
                "Levis", "İyi", "32/32", "Unisex", GiyimEnum.ALT_GIYIM
        );

        // ===========================================================================
        // --- 5. ÖZEL DERS ÖRNEKLERİ (Filtreler: TYT/LGS, Ara Sınıf) ---
        // ===========================================================================

        OzelDers matDers = new OzelDers(
                "ODTÜ'lüden Matematik Özel Ders",
                750.0,
                "C:\\resimler\\mat.jpg",
                "Öğrenci evinde veya online.",
                "Ders",
                OzeldersEnum.TYT_AYT_LGS, // Ders Türü
                OzeldersEnum.TYT_AYT_LGS  // Seviye
        );

        OzelDers piyano = new OzelDers(
                "Başlangıç Seviye Piyano Dersi",
                1000.0,
                "C:\\resimler\\piyano.jpg",
                "Her yaş grubu için uygundur.",
                "Ders",
                OzeldersEnum.ARA_SINIF,
                OzeldersEnum.ARA_SINIF
        );

        // ---------------------------------------------------------------------------
        // LİSTEYE HEPSİNİ EKLE
        // ---------------------------------------------------------------------------
        tumIlanlarListesi.addAll(
                araba1, araba2, motor1, kamyon1,   // Taşıtlar
                ev1, villa1, arsa1,                // Konutlar
                telefon1, pc1, beyazEsya,          // Teknoloji
                mont, pantolon,                    // Giyim
                matDers, piyano                    // Ders
        );

        // 3. Listeyi ListView'a Bağla
        ilanListView.setItems(tumIlanlarListesi);
        ilanListView.setCellFactory(param -> new IlanCell());

        // --- FAVORİ FİLTRELEME BUTONU ---
        if (favButton != null) {
            favButton.setOnAction(event -> {
                if (favButton.isSelected()) {
                    ObservableList<Ilan> sadeceFavoriler = FXCollections.observableArrayList();
                    for (Ilan ilan : tumIlanlarListesi) {
                        if (ilan.isFavoriMi()) sadeceFavoriler.add(ilan);
                    }
                    ilanListView.setItems(sadeceFavoriler);
                    favButton.setText("Tüm İlanları Göster");
                } else {
                    ilanListView.setItems(tumIlanlarListesi);
                    favButton.setText("Favorileri Görüntüle");
                }
            });
        }
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
        KatogeriFiltrele(ilan -> ilan instanceof Giyim && ((Giyim) ilan).getGiyimturu() == GiyimEnum.ALT_GIYIM);

    }

    @FXML private Button altgiyimButton;
    @FXML protected void altgiyimButtonclick(){}

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