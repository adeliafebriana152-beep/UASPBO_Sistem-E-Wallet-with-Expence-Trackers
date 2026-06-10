import java.util.ArrayList;
import java.util.HashMap;

public final class SilentAutoSave implements Runnable { // sisil - Fitur Runnable dan di cek caca
    private final ArrayList<User> databaseUser; // caca - Final class
    private final HashMap<String, Dompet> databaseDompet; // caca - Final class
    private volatile boolean running = true;

    public SilentAutoSave(ArrayList<User> databaseUser, HashMap<String, Dompet> databaseDompet) {
        this.databaseUser = databaseUser;
        this.databaseDompet = databaseDompet;
    }

    public void stop() {
        this.running = false;
    }

    @Override
    public void run() { //sisil - Fitur Thread
        while (running) {
            try {
                // Waktu penyimpanan
                Thread.sleep(30000);

                synchronized (databaseUser) {
                    if (!databaseUser.isEmpty()) {
                        savewallet.simpanData(databaseUser, databaseDompet);
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            } catch (Exception e) {
            }
        }
    }
}
