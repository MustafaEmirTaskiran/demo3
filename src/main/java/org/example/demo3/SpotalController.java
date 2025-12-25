package org.example.demo3;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class SpotalController implements Initializable {

    @FXML private ListView<Ilan> ilanListView;

    // FXML'de bu ID'leri TextField ve PasswordField'a vermeyi unutma!
    @FXML private TextField kAdiTextField; // Kullanıcı Adı kutusu
    @FXML private PasswordField kSifrePasswordField; // Şifre kutusu

    // İlanları tutacak olan ANA LİSTEMİZ
    private ObservableList<Ilan> tumIlanlarListesi;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // 1. Listeyi hafızada oluştur
        tumIlanlarListesi = FXCollections.observableArrayList();

        // ===========================================================================
        // --- YENİ KISIM: VERİLERİ VERİTABANINDAN ÇEK ---
        // ===========================================================================
        System.out.println("Veritabanına bağlanılıyor ve ilanlar çekiliyor...");

        try {
            IlanDAO dao = new IlanDAO();
            List<Ilan> dbGelenIlanlar = dao.tumIlanlariGetir();

            if (dbGelenIlanlar.isEmpty()) {
                System.out.println("Veritabanından ilan gelmedi veya boş.");
            } else {
                tumIlanlarListesi.addAll(dbGelenIlanlar);
                System.out.println(dbGelenIlanlar.size() + " adet ilan yüklendi.");
            }
        } catch (Exception e) {
            System.err.println("Veritabanı bağlantı hatası: " + e.getMessage());
            e.printStackTrace();
        }

        // 2. Listeyi ListView'a Bağla
        ilanListView.setItems(tumIlanlarListesi);
        ilanListView.setCellFactory(param -> new IlanCell());

        // ===========================================================================
        // --- FAVORİ FİLTRELEME BUTONU ---
        // ===========================================================================
        if (favButton != null) {
            favButton.setOnAction(event -> {

                // A) Giriş Kontrolü
                if (Oturum.aktifKullaniciId == 0) {
                    System.out.println("UYARI: Giriş yapmadan favorileri göremezsin!");
                    showAlert("Giriş Yapmalısınız", "Favorilerinizi görmek için lütfen giriş yapın.");
                    favButton.setSelected(false); // Butonu geri attır
                    return;
                }

                if (favButton.isSelected()) {
                    // B) BUTONA BASILDI (Sadece Favoriler)
                    System.out.println("Favoriler filtreleniyor...");

                    FavoriDAO fDao = new FavoriDAO();
                    // Veritabanından bu kullanıcının favori ilan ID'lerini al
                    List<Integer> favoriIdler = fDao.getKullanicininFavoriIlanIdleri(Oturum.aktifKullaniciId);

                    ObservableList<Ilan> sadeceFavoriler = FXCollections.observableArrayList();

                    // Ana listedeki ilanları tara, ID'si favori listesinde olanı seç
                    for (Ilan ilan : tumIlanlarListesi) {
                        if (favoriIdler.contains(ilan.getId())) {
                            sadeceFavoriler.add(ilan);
                        }
                    }

                    ilanListView.setItems(sadeceFavoriler);
                    favButton.setText("Tüm İlanları Göster");

                } else {
                    // C) BUTON KAPATILDI (Her Şeyi Göster)
                    ilanListView.setItems(tumIlanlarListesi);
                    favButton.setText("Favorileri Görüntüle");
                }
            });
        }
    }

    // --- GİRİŞ YAP BUTONU ---
    @FXML private Button girisButton;
    @FXML
    protected void girisButtonclick() {
        // TextField'lardan veriyi al (FXML'de fx:id verdiğinden emin ol)
        String kadi = kAdiTextField.getText(); // Kullanıcı adı kutusu
        String sifre = kSifrePasswordField.getText(); // Şifre kutusu

        if (kadi.isEmpty() || sifre.isEmpty()) {
            showAlert("Hata", "Lütfen bilgileri doldurun.");
            return;
        }

        KullaniciDAO dao = new KullaniciDAO();
        int id = dao.girisYap(kadi, sifre);

        if (id > 0) {
            Oturum.aktifKullaniciId = id; // Giriş yapanın ID'sini kaydet
            showAlert("Başarılı", "Giriş yapıldı! Hoşgeldin " + kadi);

            // Giriş butonlarını gizleyip "Çıkış Yap" butonu gösterebilirsin (Opsiyonel)
        } else {
            showAlert("Hata", "Kullanıcı adı veya şifre hatalı!");
        }


        // Şimdilik test için ID'yi 1 yapıyorum ki favorileri deneyebilesin:
        Oturum.aktifKullaniciId = 1;
        System.out.println("Giriş yapıldı (Test Modu). Kullanıcı ID: 1 olarak ayarlandı.");
        showAlert("Giriş Başarılı", "Hoşgeldiniz! Artık favori ekleyebilirsiniz.");
    }

    // --- YARDIMCI METOT: UYARI PENCERESİ ---
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    // ============================================================
    // --- KATEGORİ BUTONLARI VE FİLTRELEME ---
    // ============================================================

    @FXML private VBox tasitAltVBox;
    @FXML private Button tasitButton;
    @FXML
    protected void tasitButtonclick(ActionEvent event) {
        tasitAltVBox.setVisible(!tasitAltVBox.isVisible());
        tasitAltVBox.setManaged(!tasitAltVBox.isManaged());
        KatogeriFiltrele(ilan -> ilan instanceof Tasit);
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

    // --- KONUT ---
    @FXML private VBox konutAltVBox;
    @FXML private Button konutButton;
    @FXML
    protected void konutButtonclick(ActionEvent event) {
        konutAltVBox.setVisible(!konutAltVBox.isVisible());
        konutAltVBox.setManaged(!konutAltVBox.isManaged());
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

    // --- TEKNOLOJİ ---
    @FXML VBox teknoAltVBox;
    @FXML private Button teknoButton;
    @FXML
    protected void teknoButtonclick(ActionEvent event) {
        teknoAltVBox.setVisible(!teknoAltVBox.isVisible());
        teknoAltVBox.setManaged(!teknoAltVBox.isManaged());
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

    // --- ÖZEL DERS ---
    @FXML VBox ozeldersAltVBox;
    @FXML private Button ozeldersButton;
    @FXML
    protected void ozeldersButtonclick(ActionEvent event) {
        ozeldersAltVBox.setVisible(!ozeldersAltVBox.isVisible());
        ozeldersAltVBox.setManaged(!ozeldersAltVBox.isManaged());
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

    // --- GİYİM ---
    @FXML VBox giyimAltVBox;
    @FXML private Button giyimButton;
    @FXML protected void giyimButtonclick(ActionEvent event) {
        giyimAltVBox.setVisible(!giyimAltVBox.isVisible());
        giyimAltVBox.setManaged(!giyimAltVBox.isManaged());
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
        try {
            // URL alma yöntemini daha güvenli hale getirdim
            URL url = getClass().getResource("/kayitol.fxml");
            if(url == null) url = getClass().getResource("kayitol.fxml");

            FXMLLoader loader = new FXMLLoader(url);
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root); // Boyutları FXML'den alması daha sağlıklı
            stage.setScene(scene);
            stage.setTitle("Kayit Ol");
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML private ToggleButton favButton;
    @FXML private Button filtreButton;
    @FXML protected void filtreButtonclick(){}
    @FXML private Button siralaButton;
    // Sıralama durumunu tutmak için sınıfın en üstüne (tanımlamaların oraya) bu değişkeni ekle:
    private boolean artanSiradaMi = true;

    @FXML
    protected void siralaButtonclick() {
        // Mevcut ekranda ne görünüyorsa onu al (Filtrelenmiş olabilir)
        ObservableList<Ilan> mevcutListe = ilanListView.getItems();

        if (artanSiradaMi) {
            // UCUZDAN -> PAHALIYA
            FXCollections.sort(mevcutListe, Comparator.comparingDouble(Ilan::getFiyat));
            siralaButton.setText("Sırala (Önce En Pahalı)");
            System.out.println("Ucuzdan pahalıya sıralandı.");
        } else {
            // PAHALIDAN -> UCUZA
            FXCollections.sort(mevcutListe, (i1, i2) -> Double.compare(i2.getFiyat(), i1.getFiyat()));
            siralaButton.setText("Sırala (Önce En Ucuz)");
            System.out.println("Pahalıdan ucuza sıralandı.");
        }

        // Bir sonraki tıklama için durumu tersine çevir
        artanSiradaMi = !artanSiradaMi;
    }

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
            e.printStackTrace();
        }
    }

    // --- FİLTRELEME YARDIMCISI ---
    private void KatogeriFiltrele(java.util.function.Predicate<Ilan> kriter) {
        ObservableList<Ilan> suzulenListe = FXCollections.observableArrayList();
        for (Ilan ilan : tumIlanlarListesi) {
            if (kriter.test(ilan)) {
                suzulenListe.add(ilan);
            }
        }
        ilanListView.setItems(suzulenListe);
    }
}