import java.util.Date;

final class TopUp extends Transaksi { // adel dan di cek caca
    private String metode;
    private String notelp;

    public TopUp(int id, double amount, Date tgl, Kategori kategori, String metode, String notelp) {
        super(id, amount, tgl, kategori);
        this.metode = metode;
        this.notelp = notelp;
    }

    public String getMetode() {
        return this.metode;
    }

    public String getNotelp() {
        return this.notelp;
    }

    @Override
    public boolean prosesTransaksi(Dompet d, User u, int inputPin) // emil
            throws PinSalahException, SaldoKurangException, LimitBudgetException {
        boolean adaError = false;

        if (!this.notelp.trim().equals(u.getNoTelp().trim())) {
            System.out.println("TopUp Gagal: Nomor telepon tidak sesuai dengan nomor akun Anda!");
            return false;
        }

        if (inputPin != d.getpin()) {
            adaError = true;
            if (adaError) {
                throw new PinSalahException("PIN salah saat mencoba Top Up.");
            }
        }
        if (this.amount > 0) {
            d.setsaldo(d.getsaldo() + this.amount);
            System.out.println("TopUp berhasil dari " + this.metode + " sebesar +Rp" + this.amount);
            d.cekSaldo();
            return true;
        } else {
            System.out.println("TopUp gagal! Jumlah tidak valid.");
            return false;
        }
    }

    @Override
    public void tampilkanInformasi() {
        super.tampilkanInformasi();
        System.out.println("\n=== DETAIL TOPUP ===");
        System.out.println("Metode Top-up Via      : " + this.metode);
        System.out.println("No-telp : " + this.notelp);
    }
}