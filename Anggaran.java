import java.io.Serializable;

public final class Anggaran implements Serializable { // adel di cek caca
    private Kategori kategori;
    private double limitBulanan;
    private double totalTerpakai;

    public Anggaran(Kategori kategori, double limitBulanan) {
        this.kategori = kategori;
        this.limitBulanan = limitBulanan;
        this.totalTerpakai = 0.0;
    }

    public void tambahPengeluaran(double jumlah) {
        if (jumlah > 0) {
            this.totalTerpakai += jumlah;
        }
    }

    public boolean cekApakahOverBudget() {
        return this.totalTerpakai > this.limitBulanan;
    }

    public Kategori getKategori() {
        return this.kategori;
    }

    public double getSisaAnggaran() {
        return this.limitBulanan - this.totalTerpakai;
    }

    public double getLimitBulanan() {
        return this.limitBulanan;
    }

    public double getTotalTerpakai() {
        return this.totalTerpakai;
    }
}
