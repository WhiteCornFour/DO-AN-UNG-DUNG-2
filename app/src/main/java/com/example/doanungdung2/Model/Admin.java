package com.example.doanungdung2.Model;

public class Admin {
    private String maAdmin;
    private String tenAdmin;
    private String taiKhoanAdmin;
    private String matKhauAdmin;
    private String emailAdmin;

    public String getMaAdmin() {
        return maAdmin;
    }

    public void setMaAdmin(String maAdmin) {
        this.maAdmin = maAdmin;
    }

    public String getTenAdmin() {
        return tenAdmin;
    }

    public void setTenAdmin(String tenAdmin) {
        this.tenAdmin = tenAdmin;
    }

    public String getTaiKhoan() {
        return taiKhoanAdmin;
    }

    public void setTaiKhoan(String taiKhoan) {
        this.taiKhoanAdmin = taiKhoan;
    }

    public String getMatKhau() {
        return matKhauAdmin;
    }

    public void setMatKhau(String matKhau) {
        this.matKhauAdmin = matKhau;
    }

    public String getEmail() {
        return emailAdmin;
    }

    public void setEmail(String email) {
        this.emailAdmin = email;
    }


    public Admin(String maAdmin, String tenAdmin, String taiKhoan, String matKhau, String email) {
        this.maAdmin = maAdmin;
        this.tenAdmin = tenAdmin;
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
        this.email = email;
    }

    public Admin() {
    }
}
