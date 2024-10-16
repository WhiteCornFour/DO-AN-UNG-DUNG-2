package com.example.doanungdung2.Model;

public class Admin {
    private String maAdmin;
    private String tenAdmin;
    private String taiKhoan;
    private String matKhau;
    private String email;

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
        return taiKhoan;
    }

    public void setTaiKhoan(String taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
