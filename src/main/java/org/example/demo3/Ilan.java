package org.example.demo3;

public abstract class Ilan {
    private int id;
   private String baslik;
   private double fiyat;
   private String resimyolu;
   private boolean favoriMi;
   private String kategori;
   private String altKategori;
   private String aciklama;


   public Ilan(String baslik, double fiyat, String resimyolu , String kategori ,  String aciklama) {
       this.baslik = baslik;
       this.fiyat = fiyat;
       this.resimyolu = resimyolu;
       this.favoriMi = false;
       this.kategori = kategori;
       this.altKategori = altKategori;
       this.aciklama = aciklama;
   }


   public int getId() {
       return id;
   }
   public void setId(int id) {
       this.id = id;
   }

   public String getAltKategori() {
       return altKategori;
   }

   public String getkategori(){
       return kategori;
   }
   public String getBaslik() {
       return baslik;
   }
   public void setBaslik(String baslik) {
       this.baslik = baslik;
   }
   public double getFiyat() {
       return fiyat;
   }
   public void setFiyat(double fiyat) {
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
    public String getAciklama() {
       return aciklama;
    }
    public void setAciklama(String aciklama) {
       this.aciklama = aciklama;
    }
}
