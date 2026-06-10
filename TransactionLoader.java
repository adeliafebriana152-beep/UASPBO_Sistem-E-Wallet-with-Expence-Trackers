public final class TransactionLoader implements Runnable { // sisil dan di cek caca
    private volatile boolean isDone = false;

    public void stopLoader() {
        this.isDone = true;
    }

    @Override
    public void run() {
        String[] animasi = { "|", "/", "-", "\\" };
        int i = 0;
        System.out.print("Memproses verifikasi jaringan perbankan aman ");

        while (!isDone) {
            try {
                System.out.print("\rMemproses verifikasi jaringan perbankan aman [" + animasi[i % 4] + "]");
                i++;
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        System.out.print("\r[SISTEM] Jaringan Terverifikasi Aman!                       \n");
    }
}