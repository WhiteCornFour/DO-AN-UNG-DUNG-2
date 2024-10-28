package com.example.doanungdung2.Model;

public class Grammar {
    String maNguPhap;
    String tenNguPhap;
    String noiDung;
    String congThuc;
    String viDu;
    String maDangNguPhap;

    public Grammar() {
    }

    public Grammar(String maNguPhap, String tenNguPhap, String noiDung, String congThuc, String viDu, String maDangNguPhap) {
        this.maNguPhap = maNguPhap;
        this.tenNguPhap = tenNguPhap;
        this.noiDung = noiDung;
        this.congThuc = congThuc;
        this.viDu = viDu;
        this.maDangNguPhap = maDangNguPhap;
    }

    public String getMaNguPhap() {
        return maNguPhap;
    }

    public void setMaNguPhap(String maNguPhap) {
        this.maNguPhap = maNguPhap;
    }

    public String getTenNguPhap() {
        return tenNguPhap;
    }

    public void setTenNguPhap(String tenNguPhap) {
        this.tenNguPhap = tenNguPhap;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getCongThuc() {
        return congThuc;
    }

    public void setCongThuc(String congThuc) {
        this.congThuc = congThuc;
    }

    public String getViDu() {
        return viDu;
    }

    public void setViDu(String viDu) {
        this.viDu = viDu;
    }

    public String getMaDangNguPhap() {
        return maDangNguPhap;
    }

    public void setMaDangNguPhap(String maDangNguPhap) {
        this.maDangNguPhap = maDangNguPhap;
    }
}
