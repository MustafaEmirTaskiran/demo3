package org.example.demo3;

public class Ilan {
   private String baslik;
   private String fiyat;
   private String resimyolu;
   private boolean favoriMi;

   public Ilan(String baslik, String fiyat, String resimyolu) {
       this.baslik = baslik;
       this.fiyat = fiyat;
       this.resimyolu = resimyolu;
       this.favoriMi = false;
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
