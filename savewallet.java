import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public final class savewallet { // adel dan di cek caca
    private static final String NAMA_FILE = "save_ewallet.dat";

    public static void simpanData(ArrayList<User> databaseUser, HashMap<String, Dompet> databaseDompet) {
        try {
            FileOutputStream fileOut = new FileOutputStream(NAMA_FILE);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);

            ArrayList<Object> dataSave = new ArrayList<>();
            dataSave.add(databaseUser);
            dataSave.add(databaseDompet);

            objectOut.writeObject(dataSave);
            objectOut.close();
            fileOut.close();

            System.out.println(">>> [SISTEM] Seluruh data dompet & user berhasil disimpan! <<<");
        } catch (IOException e) {
            System.out.println(">>> [ERROR] Gagal menyimpan data: " + e.getMessage() + " <<<");
        }
    }

    public static ArrayList<Object> loadData() {
        ArrayList<Object> dataTermuat = new ArrayList<>();
        File fileSave = new File(NAMA_FILE);

        if (fileSave.exists()) {
            try {
                FileInputStream fileIn = new FileInputStream(NAMA_FILE);
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);

                dataTermuat = (ArrayList<Object>) objectIn.readObject();
                objectIn.close();
                fileIn.close();
                System.out.println("[SISTEM] Sinkronisasi database dari file berhasil dilakukan!");
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("[SISTEM] Gagal memuat file lama, menggunakan data memori aktif.");
            }
        }
        return dataTermuat;
    }
}
