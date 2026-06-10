import java.util.Date;

final class Transfer extends Transaksi { // adel dan di cek caca
    private int idPenerima;
    private String namaPenerima;
    private String transferType;
    private String kodeBank;

    public Transfer(int id, double amount, Date tgl, Kategori kategori, int idPenerima, String namaPenerima,
            String transferType, String kodeBank) {
        super(id, amount, tgl, kategori);
        this.idPenerima = idPenerima;
        this.namaPenerima = namaPenerima;
        this.transferType = transferType;
        this.kodeBank = kodeBank;
    }

    public int getIdPenerima() {
        return this.idPenerima;
    }

    public String getNamaPenerima() {
        return this.namaPenerima;
    }

    public String getTransferType() {
        return this.transferType;
    }

    public String getKodeBank() {
        return this.kodeBank;
    }

    @Override
    public boolean prosesTransaksi(Dompet d, User u, int inputPin) // adel dan emil
            throws PinSalahException, SaldoKurangException, LimitBudgetException {
        if (inputPin != d.getpin()) {
            System.out.println("Transaksi Gagal: PIN Salah!");
            throw new PinSalahException("PIN salah saat mencoba Transfer.");
        }
        if (this.amount > 0 && this.amount <= d.getsaldo()) {
            d.setsaldo(d.getsaldo() - this.amount);
            System.out.println("Transfer ke " + this.namaPenerima + " berhasil: -Rp" + this.amount);
            d.cekSaldo();
            return true;
        } else {
            System.out.println("Transfer gagal! Saldo tidak cukup.");
            d.cekSaldo();
            throw new SaldoKurangException("Transfer gagal! Saldo tidak cukup.");
        }
    }

    @Override
    public void tampilkanInformasi() {
        super.tampilkanInformasi();
        System.out.println("\n=== DETAIL TRANSFER ===");
        System.out.println("Id Penerima       : " + this.idPenerima);
        System.out.println("Nama Penerima     : " + this.namaPenerima);
        System.out.println("Tipe Transfer     : " + this.transferType);
        System.out.println("Kode Bank         : " + this.kodeBank);
    }
}