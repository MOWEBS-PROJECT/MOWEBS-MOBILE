package com.example.mowebs;

public class MobilObject {

    private String id;
    private String deskripsi;
    private String harga;
    private String jenis;
    private String merk;
    private String plat;
    private ReviewsMobilObject[] reviews;
    private MobilSpesifikasiObject spesifikasi;
    private String url_gambar;

    public MobilObject() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public void setPlat(String plat) {
        this.plat = plat;
    }

    public void setReviews(ReviewsMobilObject[] reviews) {
        this.reviews = reviews;
    }

    public void setSpesifikasi(MobilSpesifikasiObject spesifikasi) {
        this.spesifikasi = spesifikasi;
    }

    public void setUrl_gambar(String url_gambar) {
        this.url_gambar = url_gambar;
    }

    public String getId() {
        return id;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getHarga() {
        return harga;
    }

    public String getJenis() {
        return jenis;
    }

    public String getMerk() {
        return merk;
    }

    public String getPlat() {
        return plat;
    }

    public ReviewsMobilObject[] getReviews() {
        return reviews;
    }

    public String getUrl_gambar() {
        return url_gambar;
    }

    public MobilSpesifikasiObject getSpesifikasi() {
        return spesifikasi;
    }
}
