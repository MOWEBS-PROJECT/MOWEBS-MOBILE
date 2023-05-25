package com.example.mowebs;

public class OrderObject {

    private String id, id_mobil, status;
    private int durasi, totalHarga;
    private long w_pengembalian, w_pembayaran, w_pemesanan;

    public OrderObject() {}


    public void setId_mobil(String id_mobil) {
        this.id_mobil = id_mobil;
    }

    public void setDurasi(int durasi) {
        this.durasi = durasi;
    }

    public void setTotalHarga(int totalHarga) {
        this.totalHarga = totalHarga;
    }

    public void setW_pembayaran(long w_pembayaran) {
        this.w_pembayaran = w_pembayaran;
    }

    public void setW_pemesanan(long w_pemesanan) {
        this.w_pemesanan = w_pemesanan;
    }

    public void setW_pengembalian(long w_pengembalian) {
        this.w_pengembalian = w_pengembalian;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public int getDurasi() {
        return durasi;
    }

    public int getTotalHarga() {
        return totalHarga;
    }

    public long getW_pembayaran() {
        return w_pembayaran;
    }

    public long getW_pemesanan() {
        return w_pemesanan;
    }

    public long getW_pengembalian() {
        return w_pengembalian;
    }

    public String getId_mobil() {
        return id_mobil;
    }

    public String getStatus() {
        return status;
    }
}
