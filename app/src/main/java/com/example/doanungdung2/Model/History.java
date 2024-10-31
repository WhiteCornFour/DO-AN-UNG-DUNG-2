package com.example.doanungdung2.Model;

public class History {
    String maLichSu;
    String maTuVung;
    String maNguoiDung;

    public History(String maLichSu, String maTuVung, String maNguoiDung) {
        this.maLichSu = maLichSu;
        this.maTuVung = maTuVung;
        this.maNguoiDung = maNguoiDung;
    }

    public History() {
    }

    public String getMaLichSu() {
        return maLichSu;
    }

    public void setMaLichSu(String maLichSu) {
        this.maLichSu = maLichSu;
    }

    public String getMaTuVung() {
        return maTuVung;
    }

    public void setMaTuVung(String maTuVung) {
        this.maTuVung = maTuVung;
    }

    public String getMaNguoiDung() {
        return maNguoiDung;
    }

    public void setMaNguoiDung(String maNguoiDung) {
        this.maNguoiDung = maNguoiDung;
    }
}
