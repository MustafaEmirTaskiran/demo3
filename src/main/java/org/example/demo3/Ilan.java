package org.example.demo3;

public abstract class Ilan {
    private int id;
   private String baslik;
   private String fiyat;
   private String resimyolu;
   private boolean favoriMi;
   private String kategori;
   private String altKategori;


   public Ilan(String baslik, String fiyat, String resimyolu , String kategori , String altKategori) {
       this.baslik = baslik;
       this.fiyat = fiyat;
       this.resimyolu = resimyolu;
       this.favoriMi = false;
       this.kategori = kategori;
       this.altKategori = altKategori;
   }

   public String getAltKategori() {
       return altKategori;
   }

   public String getKatogerikatogeri(){
       return kategori;
   }
   public String getBaslik() {
       return baslik;
   }
   public void setBaslik(String baslik) {
       this.baslik = baslik;
   }
   public String getFiyat() {
       return fiyat;
   }
   public void setFiyat(String fiyat) {
       this.fiyat = fiyat;
   }
   public String getResimyolu() {
       return resimyolu;
   }
   public void setResimyolu(String resimyolu) {
       this.resimyolu = resimyolu;
   }
   public boolean isFavoriMi() {
       return favoriMi;
   }
   public void setFavoriMi(boolean favoriMi) {
       this.favoriMi = favoriMi;
   }
}
