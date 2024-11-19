package com.example.doanungdung2.Model;

public class GrammarCategory {
    private String maDangNguPhap;
    private String tenDangNguPhap;
    private String moTa;
    boolean selected;
    public GrammarCategory() {
    }

    public GrammarCategory(String maDangNguPhap, String tenDangNguPhap, String moTa) {
        this.maDangNguPhap = maDangNguPhap;
        this.tenDangNguPhap = tenDangNguPhap;
        this.moTa = moTa;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
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
