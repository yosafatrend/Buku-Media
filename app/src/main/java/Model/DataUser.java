package Model;

import com.google.gson.annotations.SerializedName;

public class DataUser {
    @SerializedName("id")
    private int status;
    @SerializedName("nama")
    private String nama;
    @SerializedName("email")
    private String email;
    @SerializedName("role")
    private String role;

    public DataUser(int status, String nama, String email, String role) {
        this.status = status;
        this.nama = nama;
        this.email = email;
        this.role = role;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
