import java.io.Serializable;
import java.util.ArrayList;

public final class Kategori implements Serializable { // adel - Fitur abstrac (Implements) & Serializable (penyimanan) _di cek caca
    private static final long serialVersionUID = 1L;

    private static int counterKategori = 0;
    private String idKategori;
    private String namaKategori;
    private String jenisKategori;

    public Kategori(String namaKategori, String jenisKategori) {
        counterKategori++;
        this.idKategori = "KAT" + counterKategori;
        this.namaKategori = namaKategori;
        this.jenisKategori = jenisKategori;
    }

    public String getIdKategori() {
        return idKategori;
    }

    public String getNamaKategori() {
        return namaKategori;
    }

    public String getJenisKategori() {
        return jenisKategori;
    }
}
