import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpenseTracker implements Serializable { // adel di cek caca
    private List<Anggaran> daftarAnggaran;

    public ExpenseTracker() {
        this.daftarAnggaran = new ArrayList<>();
    }

    public void setBudgetKategori(Kategori kategori, double limit) {
        this.daftarAnggaran.add(new Anggaran(kategori, limit));
        System.out.println("Budget untuk " + kategori.getNamaKategori() + " diset sebesar Rp" + limit);
    }

    public void updatePengeluaranBudget(Kategori kategori, double jumlah) // emil
            throws PinSalahException, SaldoKurangException, LimitBudgetException {
        for (Anggaran a : daftarAnggaran) {
            if (a.getKategori().getIdKategori() == kategori.getIdKategori()) {
                a.tambahPengeluaran(jumlah);
                if (a.cekApakahOverBudget()) {
                    System.out.println(" PERINGATAN: Pengeluaran kategori [" + kategori.getNamaKategori()
                            + "] telah melebihi budget!");
                    throw new LimitBudgetException("Alokasi dana untuk " + kategori.getNamaKategori()
                            + " sudah melampaui batas anggaran bulanan Anda!");
                }
                break;
            }
        }
    }

    public Map<String, Double> hitungTotalPengeluaranPerKategori(List<Transaksi> listTx, int bulan) {
        Map<String, Double> totalPerKategori = new HashMap<>();
        for (Transaksi tx : listTx) {

            if (tx.getKategori() != null && "Pengeluaran".equalsIgnoreCase(tx.getKategori().getJenisKategori())) {
                String namaCat = tx.getKategori().getNamaKategori();
                totalPerKategori.put(namaCat, totalPerKategori.getOrDefault(namaCat, 0.0) + tx.getAmount());
            }
        }
        return totalPerKategori;
    }

    public void tampilkanLaporanBulanan(List<Transaksi> listTx, int bulan) {
        System.out.println("\n=== LAPORAN KEUANGAN BULAN KE-" + bulan + " ===");
        Map<String, Double> pengeluaran = hitungTotalPengeluaranPerKategori(listTx, bulan);

        System.out.println("--- Ringkasan Pengeluaran ---");
        for (Map.Entry<String, Double> entry : pengeluaran.entrySet()) {
            System.out.println("- " + entry.getKey() + " : Rp" + entry.getValue());
        }

        System.out.println("\n--- Status Anggaran (Budget) ---");
        for (Anggaran a : daftarAnggaran) {
            System.out.println("> " + a.getKategori().getNamaKategori());
            System.out.println("  Limit    : Rp" + a.getLimitBulanan());
            System.out.println("  Terpakai : Rp" + a.getTotalTerpakai());
            System.out.println("  Sisa     : Rp" + a.getSisaAnggaran());
        }
    }
}