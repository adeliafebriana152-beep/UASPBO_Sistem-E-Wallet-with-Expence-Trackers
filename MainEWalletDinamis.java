import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MainEWalletDinamis { // caca - Static method

    private static ArrayList<User> databaseUser = new ArrayList<>();
    private static HashMap<String, Dompet> databaseDompet = new HashMap<>();

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        ArrayList<Kategori> daftarKategori = new ArrayList<>();
        daftarKategori.add(new Kategori("Gaji", "Pemasukan"));
        daftarKategori.add(new Kategori("Makan", "Pengeluaran"));
        daftarKategori.add(new Kategori("Transportasi", "Pengeluaran"));
        daftarKategori.add(new Kategori("Kebutuhan Rumah", "Pengeluaran"));

        System.out.println("=======================================");
        System.out.println("APLIKASI E-WALLET");
        System.out.println("=======================================\n");
        System.out.println("Selamat datang di sistem manajemen keuangan Anda!");

        User defaultUser = new User(1, "Admin", "admin@mail.com", "admin123", "08123456789", 123456);
        defaultUser.getDompet().setsaldo(500000.0);
        databaseUser.add(defaultUser);
        databaseDompet.put(defaultUser.getEmail(), defaultUser.getDompet());

        SilentAutoSave autoSaveRunnable = new SilentAutoSave(databaseUser, databaseDompet); // sisil - Multithreading
        Thread threadAutoSave = new Thread(autoSaveRunnable);
        threadAutoSave.setDaemon(true);
        threadAutoSave.start();

        User currentUser = null; // caca
        Dompet currentDompet = null;
        int idCounterTrx = 1;

        boolean berjalan = true;

        while (berjalan) { // caca
            try {
                if (currentUser == null) {
                    // --- MODE SEBELUM LOGIN ---
                    System.out.println("\n--- USER MASUK ---");
                    System.out.println("1. Daftar (Sign In)");
                    System.out.println("2. Login");
                    System.out.println("0. Keluar Aplikasi\n");
                    System.out.print("Pilih aksi (1/2/0): ");
                    int pilihan = input.nextInt();
                    input.nextLine();

                    if (pilihan == 1) {
                        System.out.print("Masukkan Nama Lengkap: ");
                        String nama = input.nextLine();

                        System.out.print("Masukkan Email: ");
                        String email = input.next();

                        System.out.print("Masukkan Password: ");
                        String password = input.next();

                        System.out.print("Masukkan No HP: ");
                        String noHp = input.next();

                        System.out.print("Buat PIN Dompet Anda (Angka): ");
                        int pinBaru = input.nextInt();
                        input.nextLine();

                        int newId = databaseUser.size() + 1;

                        User u = new User(newId, nama, email, password, noHp, pinBaru);
                        databaseUser.add(u);

                        System.out.println("Sign in berhasil dilakukan! Silakan login menggunakan akun baru Anda.");
                    } else if (pilihan == 2) {

                        ArrayList<Object> dataLoad = savewallet.loadData();
                        if (!dataLoad.isEmpty()) {
                            synchronized (databaseUser) {
                                databaseUser.clear();
                                databaseUser.addAll((ArrayList<User>) dataLoad.get(0));

                                databaseDompet.clear();
                                databaseDompet.putAll((HashMap<String, Dompet>) dataLoad.get(1));
                            }
                        }
                        System.out.print("Email: ");
                        String em = input.next();
                        System.out.print("Pass : ");
                        String pw = input.next();
                        input.nextLine();
                        boolean loginsukses = false;
                        for (User u : databaseUser) {
                            if (u.login(em, pw)) {
                                currentUser = u;
                                loginsukses = true;
                                System.out.println("Login berhasil! Selamat datang, " + currentUser.getNamaUser());
                                currentDompet = currentUser.getDompet();
                                break;
                            }
                        }
                        if (!loginsukses) {
                            System.out.println("Login Gagal! Email atau Password salah.");
                        }
                    } else if (pilihan == 0) {
                        System.out.println("Terima kasih telah menggunakan E-Wallet. Sampai jumpa!");
                        berjalan = false;
                    }
                } else {
                    // --- MODE SETELAH LOGIN ---
                    System.out.println("\n--- STATUS DOMPET ---");
                    System.out.println("Pengguna : " + currentUser.getNamaUser());
                    System.out.println("Saldo    : Rp" + currentDompet.getsaldo());
                    System.out.println("Total Trx: " + currentDompet.getDaftarTransaksi().size());
                    System.out.println("------------------------------");
                    System.out.println("3. Lakukan Top Up");
                    System.out.println("4. Bayar Merchant (Payment)");
                    System.out.println("5. Transfer Uang");
                    System.out.println("6. Lihat Riwayat (Cek Polimorfisme)");
                    System.out.println("7. Atur Anggaran (Budget) Pengeluaran");
                    System.out.println("8. Cetak Laporan Expense Tracker");
                    System.out.println("9. [SAVE DATA] Simpan seluruh data user ke file");
                    System.out.println("0. Logout");
                    System.out.print("\nPilih aksi (0-9): ");
                    int pilihan1 = input.nextInt();
                    input.nextLine();

                    if (pilihan1 == 0) {
                        System.out.println("User " + currentUser.getNamaUser() + " telah logout.");
                        currentUser = null;
                        currentDompet = null;
                        continue;
                    } else if (pilihan1 == 9) {

                        savewallet.simpanData(databaseUser, databaseDompet);
                        continue;
                    }

                    if (pilihan1 < 1 || pilihan1 > 9) {
                        System.out.println("Pilihan tidak valid!");
                        continue;
                    } else if (pilihan1 >= 3 && pilihan1 <= 5) {
                        System.out.print("Masukkan nominal transaksi: Rp");
                        double amount = input.nextDouble();
                        input.nextLine(); 
                        if (Double.isNaN(amount) || Double.compare(amount, 0.0) <= 0) {
                            System.out.println("Nominal tidak valid!");
                            continue;
                        }
                        if (amount < 10000) {
                            System.out.println(" Nominal minimal transaksi adalah Rp10.000!");
                            continue;
                        }

                        Transaksi tBaru = null;
                        int pinInput = 0;

                        if (pilihan1 == 3) {
                            // --- TOP UP ---
                            System.out.print("Masukkan Metode Top-up (Cth: Transfer Bank, Indomaret, DANA) : ");
                            String metode = input.nextLine();
                            System.out.print("Masukkan Nomor Telepon       : ");
                            String notelp = input.nextLine();

                            System.out.print("Konfirmasi dengan PIN Dompet Anda  : ");
                            pinInput = input.nextInt();
                            input.nextLine();

                            tBaru = new TopUp(idCounterTrx++, amount, new Date(), daftarKategori.get(0), metode,
                                    notelp);

                        } else if (pilihan1 == 4) {
                            // --- PAYMENT ---
                            System.out.print("Masukkan Nama Merchant (Cth: Steam): ");
                            String merchant = input.nextLine();

                            System.out.println("Pilih Kategori Pengeluaran:");
                            System.out.println("1. Makan");
                            System.out.println("2. Transportasi");
                            System.out.println("3. Kebutuhan Rumah");
                            System.out.print("Pilihan Anda (1/2/3): ");
                            int pilihKat = input.nextInt();
                            input.nextLine();
                            Kategori katTerpilih = (pilihKat == 1) ? daftarKategori.get(1)
                                    : (pilihKat == 2) ? daftarKategori.get(2) : daftarKategori.get(3);

                            System.out.print("Konfirmasi dengan PIN Dompet Anda  : ");
                            pinInput = input.nextInt();
                            input.nextLine();

                            tBaru = new Payment(idCounterTrx++, amount, new Date(), katTerpilih, 45, merchant);

                        } else if (pilihan1 == 5) {
                            // --- TRANSFER ---
                            System.out.print("Masukkan Nama Penerima Uang        : ");
                            String penerima = input.nextLine();
                            System.out.print("Masukkan Kode Bank Tujuan          : ");
                            String kodeBank = input.nextLine();

                            System.out.println("Pilih Kategori Pengeluaran:");
                            System.out.println("1. Makan");
                            System.out.println("2. Transportasi");
                            System.out.println("3. Kebutuhan Rumah");
                            System.out.print("Pilihan Anda (1/2/3): ");
                            int pilihKat = input.nextInt();
                            input.nextLine();
                            Kategori katTerpilih = (pilihKat == 1) ? daftarKategori.get(1)
                                    : (pilihKat == 2) ? daftarKategori.get(2) : daftarKategori.get(3);

                            System.out.print("Konfirmasi dengan PIN Dompet Anda  : ");
                            pinInput = input.nextInt();
                            input.nextLine();

                            tBaru = new Transfer(idCounterTrx++, amount, new Date(), katTerpilih, 88, penerima,
                                    "Transfer Instan", kodeBank);
                        }

                        if (tBaru != null) { // sisil - Multithreading  dan caca
                            TransactionLoader loader = new TransactionLoader();
                            Thread threadLoader = new Thread(loader);
                            threadLoader.start();

                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }

                            loader.stopLoader();
                            try {
                                threadLoader.join();
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }

                            synchronized (databaseUser) {
                                currentDompet.tambahTransaksi(tBaru, currentUser, pinInput);
                            }
                        }
                    } else if (pilihan1 == 6) {
                        // --- LIHAT RIWAYAT ---
                        System.out.println("\n<<< DAFTAR RIWAYAT TRANSAKSI >>>");
                        if (currentDompet.getDaftarTransaksi().isEmpty()) {
                            System.out.println("Belum ada riwayat transaksi.");
                        } else {
                            for (Transaksi t : currentDompet.getDaftarTransaksi()) {

                                t.tampilkanInformasi();

                                if (t instanceof Transfer) {
                                    System.out.println("[PERINGATAN! UANG KELUAR KE REKENING LAIN]");
                                    Transfer tr = (Transfer) t;
                                    System.out.println(" -> Mengirim ke Bank: " + tr.getKodeBank() + " a/n "
                                            + tr.getNamaPenerima());
                                } else if (t instanceof TopUp) {
                                    System.out.println("[INFO! UANG MASUK KE DOMPET]");
                                } else if (t instanceof Payable) {
                                    Payable p = (Payable) t;
                                    System.out.println(
                                            ">> [INFO PAYABLE] Biaya Admin Terpotong: Rp" + p.hitungBiayaAdmin());
                                    System.out.println(
                                            ">> [INFO PAYABLE] Alamat Tujuan Transaksi: " + p.getTujuanPembayaran());
                                }
                                System.out.println("------------------------------------");
                            }
                        }
                    } else if (pilihan1 == 7) {
                        // --- SET BUDGET ---
                        System.out.println("\n--- ATUR BUDGET PENGELUARAN ---");
                        System.out.println("1. Makan");
                        System.out.println("2. Transportasi");
                        System.out.println("3. Kebutuhan Rumah");
                        System.out.print("Pilihan Anda (1/2/3): ");
                        int katPilihan = input.nextInt();
                        input.nextLine();

                        Kategori katTerpilih = (katPilihan == 1) ? daftarKategori.get(1)
                                : (katPilihan == 2) ? daftarKategori.get(2) : daftarKategori.get(3);

                        System.out.print("Masukkan Limit Anggaran untuk " + katTerpilih.getNamaKategori() + ": Rp");
                        double limit = input.nextDouble();
                        input.nextLine();

                        currentDompet.getTracker().setBudgetKategori(katTerpilih, limit);
                        System.out.println(">>> BERHASIL: Budget telah diterapkan. <<<");
                    } else if (pilihan1 == 8) {
                        // --- PRINT LAPORAN BULANAN ---
                        currentDompet.lihatLaporanKeuangan(1);
                    }
                }

            } catch (InputMismatchException e) { // emil - Try-Catch
                System.out.println(
                        "[INPUT ERROR] Terjadi kesalahan input! Pastikan format memasukkan data/angka benar.");
                input.nextLine();
            } catch (PinSalahException e) {
                System.out.println("[KEAMANAN ERROR] " + e.getMessage());
            } catch (SaldoKurangException e) {
                System.out.println("[SALDO ERROR] " + e.getMessage());
            } catch (LimitBudgetException e) {
                System.out.println("[BUDGET WARNING] " + e.getMessage());
            } catch (Exception e) {
                System.out.println("[SISTEM ERROR] Notifikasi Umum: " + e.getMessage());
                continue;
            } finally {

                System.out.println(">> [SISTEM] Pemrosesan menu selesai.");
            }
        }

        autoSaveRunnable.stop();
        threadAutoSave.interrupt();

        input.close();
        System.out.println("Aplikasi ditutup.");
    }
}
