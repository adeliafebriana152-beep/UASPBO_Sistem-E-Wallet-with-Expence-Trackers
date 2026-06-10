import java.util.Date;

final class Payment extends Transaksi implements Payable { // adel dan di cek caca
    private int idMerchant;
    private String namaMerchant;

    public Payment(int id, double amount, Date tgl, Kategori kategori, int idMerchant, String namaMerchant) {
        super(id, amount, tgl, kategori);
        this.idMerchant = idMerchant;
        this.namaMerchant = namaMerchant;
    }

    public int getIdMerchant() {
        return this.idMerchant;
    }

    public String getNamaMerchant() {
        return this.namaMerchant;
    }

    @Override
    public double hitungBiayaAdmin() {
        return 2500.0;
    }

    @Override
    public String getTujuanPembayaran() {
        return "Merchant: " + this.namaMerchant + ")";
    }

    @Override
    public boolean prosesTransaksi(Dompet d, User u, int inputPin) // emil
            throws PinSalahException, SaldoKurangException, LimitBudgetException {
        if (inputPin != d.getpin()) {
            throw new PinSalahException("PIN yang dimasukkan salah.");
        }
        double totalWajibBayar = this.amount + hitungBiayaAdmin();

        if (totalWajibBayar <= d.getsaldo()) {
            d.setsaldo(d.getsaldo() - totalWajibBayar);
            System.out.println("Payment ke " + this.namaMerchant + " berhasil!");
            System.out.println("Detail Potong Saldo: Besaran Rp" + this.amount + " + Admin Rp" + hitungBiayaAdmin());
            d.cekSaldo();
            return true;
        } else {
            System.out.println("Payment gagal! Saldo tidak cukup untuk nominal + admin."); //
            d.cekSaldo();
            throw new SaldoKurangException("Saldo tidak cukup untuk nominal + admin.");
        }
    }

    @Override
    public void tampilkanInformasi() {
        super.tampilkanInformasi();
        System.out.println("\n=== DETAIL PAYMENT ===");
        System.out.println("Id Merchant      : " + this.idMerchant);
        System.out.println("Nama Merchant    : " + this.namaMerchant);
        System.out.println("Tujuan           : " + getTujuanPembayaran());
    }
}