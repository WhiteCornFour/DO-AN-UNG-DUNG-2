package com.example.doanungdung2.Model;

public class GrammarCategory {
    private String maDangNguPhap;
    private String tenDangNguPhap;
    private String moTa;

    public GrammarCategory() {
    }

    public GrammarCategory(String maDangNguPhap, String tenDangNguPhap, String moTa) {
        this.maDangNguPhap = maDangNguPhap;
        this.tenDangNguPhap = tenDangNguPhap;
        this.moTa = moTa;
    }

    public String getMaDangNguPhap() {
        return maDangNguPhap;
    }

    public void setMaDangNguPhap(String maDangNguPhap) {
        this.maDangNguPhap = maDangNguPhap;
    }

    public String getTenDangNguPhap() {
        return tenDangNguPhap;
    }

    public void setTenDangNguPhap(String tenDangNguPhap) {
        this.tenDangNguPhap = tenDangNguPhap;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
}
