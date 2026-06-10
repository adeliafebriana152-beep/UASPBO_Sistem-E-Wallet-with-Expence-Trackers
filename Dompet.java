import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Dompet implements Serializable { // adel - Fitur Abstrac (Implements) & Serializable (penyimpanan)
    private static final long serialVersionUID = 1L;

    private double saldo;
    private int pin;
    private String noTelp;
    private List<Transaksi> daftarTransaksi;
    private ExpenseTracker tracker;

    public String getNoTelp() {
        return this.noTelp;
    }

    public void setnoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    public double getsaldo() {
        return this.saldo;
    }

    public Dompet(double saldo, int pin) {
        this.setsaldo(saldo);
        this.setpin(pin);
        this.daftarTransaksi = new ArrayList<>();
        this.tracker = new ExpenseTracker(); // Relasi - Komposisi
    }

    public int getpin() {
        return this.pin;
    }

    public String getnoTelp() {
        return this.noTelp;
    }

    public List<Transaksi> getDaftarTransaksi() {
        return this.daftarTransaksi;
    }

    public ExpenseTracker getTracker() {
        return this.tracker;
    }

    public void setsaldo(double saldo) {
        if (saldo >= 0) {
            this.saldo = saldo;
        } else {
            System.out.println("Saldo tidak boleh negatif!");
        }
    }

    public void setpin(int pin) {
        if (pin > 0) {
            this.pin = pin;
        } else {
            System.out.println("PIN tidak valid!");
        }
    }

    public void tambahTransaksi(Transaksi transaksi, User u, int inputPin)
            throws PinSalahException, SaldoKurangException, LimitBudgetException {

        boolean sukses = transaksi.prosesTransaksi(this, u, inputPin);
        if (sukses) {
            this.daftarTransaksi.add(transaksi);
            if (transaksi.getKategori() != null
                    && "Pengeluaran".equalsIgnoreCase(transaksi.getKategori().getJenisKategori())) {

                this.tracker.updatePengeluaranBudget(transaksi.getKategori(), transaksi.getAmount());
            }
        }
    }

    public void lihatLaporanKeuangan(int bulan) {
        this.tracker.tampilkanLaporanBulanan(this.daftarTransaksi, bulan);
    }

    public void cekSaldo() {
        System.out.println("Saldo saat ini: Rp" + this.saldo);
    }

    public void tampilkanInformasi() {
        System.out.println("\n=== DATA DOMPET ===");
        System.out.println("Saldo : Rp" + this.saldo);
        System.out.println("PIN   : " + this.pin);
    }
}
