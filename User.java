import java.io.Serializable;

public class User implements Serializable { // adel - Fitur Implements
    private int idUser;
    private String namaUser;
    private String email;
    private String password;
    private String noTelp;
    private Dompet dompet; // Komposisi

    public User(int idUser, String namaUser, String email, String password, String noTelp, int pin) {
        this.idUser = idUser;
        this.namaUser = namaUser;
        this.email = email;
        this.password = password;
        this.noTelp = noTelp;

        this.dompet = new Dompet(0.0, pin);
    }

    public int getIdUser() {
        return idUser;
    }

    public String getNamaUser() {
        return namaUser;
    }

    public String getEmail() {
        return email;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public String getPassword() {
        return password;
    }

    public Dompet getDompet() {
        return dompet;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        if (email.contains("@")) {
            this.email = email;
        } else {
            System.out.println("Format email salah.");
        }
    }

    public void daftar() {
        System.out.println("Proses pendaftaran user: " + this.namaUser);
    }

    public boolean login(String inputEmail, String inputPassword) {
        if (this.email.equals(inputEmail) && this.password.equals(inputPassword)) {
            return true;
        } else {
            return false;
        }
    }

    public void tampilkanProfil() {
        System.out.println("--- PROFIL PENGGUNA ---");
        System.out.println("ID User   : " + this.getIdUser());
        System.out.println("Nama      : " + this.getNamaUser());
        System.out.println("Email     : " + this.getEmail());
        System.out.println("No. Telp  : " + this.getNoTelp());

        System.out.println("Dompet    : Saldo Rp" + this.getDompet().getsaldo());
    }

    public void logout() {
        System.out.println("User " + this.namaUser + " telah logout.");
    }
}
