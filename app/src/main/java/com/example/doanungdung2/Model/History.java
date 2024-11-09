package com.example.doanungdung2.Model;

public class History {
    String maLichSu;
    String maTuVung;
    String maNguoiDung;
    String uaThich;

    public History(String maLichSu, String maTuVung, String maNguoiDung, String uaThich) {
        this.maLichSu = maLichSu;
        this.maTuVung = maTuVung;
        this.maNguoiDung = maNguoiDung;
        this.uaThich = uaThich;
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

    public String getUaThich() {
        return uaThich;
    }

    public void setUaThich(String uaThich) {
        this.uaThich = uaThich;
    }
}
