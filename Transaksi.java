import java.io.Serializable;
import java.util.Date;

public abstract class Transaksi implements Serializable { // adel - Fitur abstrac (Implements)
    protected Integer idTransaksi;
    protected double amount;
    protected Date tglTransaksi;
    protected Kategori kategori; // untuk menghubungkan transaksi dengan kategorinya

    public Transaksi(Integer idTransaksi, double amount, Date tglTransaksi, Kategori kategori) {
        setIdTransaksi(idTransaksi);
        setAmount(amount);
        this.tglTransaksi = tglTransaksi;
        this.kategori = kategori;
    }

    public final Kategori getKategori() { // caca - Final class
        return this.kategori;
    }

    public final double getAmount() { // caca - Final class
        return this.amount;
    }

    public final Integer getIdTransaksi() { // caca - Final class
        return this.idTransaksi;
    }

    public final Date getTglTransaksi() { // caca - Final class
        return this.tglTransaksi;
    }

    public void setIdTransaksi(int idTransaksi) {
        if (idTransaksi > 0) {
            this.idTransaksi = idTransaksi;
        } else {
            System.out.println("ID Transaksi harus lebih besar dari 0.");
        }
    }

    public void setAmount(double amount) {
        if (amount > 0) {
            this.amount = amount;
        } else {
            System.out.println("Masukkan Jumlah lebih dari 0.");
        }
    }

    public abstract boolean prosesTransaksi(Dompet d, User u, int inputPin) // emil - Fitur Throw
            throws PinSalahException, SaldoKurangException, LimitBudgetException;

    public void tampilkanInformasi() {
        System.out.println("=== Detail Transaksi ===");
        System.out.println("ID Transaksi      : " + this.idTransaksi);
        System.out.println("Tanggal Transaksi : " + this.tglTransaksi);
        System.out.println("Amount            : Rp" + this.amount);
        if (this.kategori != null) {
            System.out.println("Kategori          : " + this.kategori.getNamaKategori() + " ("
                    + this.kategori.getJenisKategori() + ")");
        }
    }
}
