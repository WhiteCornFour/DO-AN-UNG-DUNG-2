package com.example.doanungdung2.Model;

import java.io.Serializable;

public class User implements Serializable {
    String maNguoiDung;
    String tenNguoiDung;
    String taiKhoan;
    String matKhau;
    String soDienThoai;
    String email;
    String cheDoXacNhan;
    String maXacNhan;
    byte[] anhNguoiDung;

    public String getMaNguoiDung() {
        return maNguoiDung;
    }

    public void setMaNguoiDung(String maNguoiDung) {
        this.maNguoiDung = maNguoiDung;
    }

    public String getTenNguoiDung() {
        return tenNguoiDung;
    }

    public void setTenNguoiDung(String tenNguoiDung) {
        this.tenNguoiDung = tenNguoiDung;
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

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCheDoXacNhan() {
        return cheDoXacNhan;
    }

    public void setCheDoXacNhan(String cheDoXacNhan) {
        this.cheDoXacNhan = cheDoXacNhan;
    }

    public String getMaXacNhan() {
        return maXacNhan;
    }

    public void setMaXacNhan(String maXacNhan) {
        this.maXacNhan = maXacNhan;
    }

    public byte[] getAnhNguoiDung() {
        return anhNguoiDung;
    }

    public void setAnhNguoiDung(byte[] anhNguoiDung) {
        this.anhNguoiDung = anhNguoiDung;
    }

    public User(String maNguoiDung, String tenNguoiDung, String taiKhoan, String matKhau, String soDienThoai, String email, String cheDoXacNhan, String maXacNhan, byte[] anhNguoiDung) {
        this.maNguoiDung = maNguoiDung;
        this.tenNguoiDung = tenNguoiDung;
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.cheDoXacNhan = cheDoXacNhan;
        this.maXacNhan = maXacNhan;
        this.anhNguoiDung = anhNguoiDung;
    }

    public User() {
    }
}
