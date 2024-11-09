package com.example.doanungdung2.Model;

import java.io.Serializable;

public class Dictionary implements Serializable {
    String maTuVung;
    String tuTiengAnh;
    String tuTiengViet;
    String gioiTuDiKem;
    String cachPhatAm;
    String loaiTu;
    String viDu;
    String viDuTiengViet;
    byte[] anhTuVung;
    boolean isChecked = false;

    public Dictionary(String maTuVung, String tuTiengAnh, String tuTiengViet, String gioiTuDiKem, String cachPhatAm, String loaiTu, String viDu, String viDuTiengViet, byte[] anhTuVung) {
        this.maTuVung = maTuVung;
        this.tuTiengAnh = tuTiengAnh;
        this.tuTiengViet = tuTiengViet;
        this.gioiTuDiKem = gioiTuDiKem;
        this.cachPhatAm = cachPhatAm;
        this.loaiTu = loaiTu;
        this.viDu = viDu;
        this.viDuTiengViet = viDuTiengViet;
        this.anhTuVung = anhTuVung;
    }

    public Dictionary() {
    }

    public String getMaTuVung() {
        return maTuVung;
    }

    public void setMaTuVung(String maTuVung) {
        this.maTuVung = maTuVung;
    }

    public String getTuTiengAnh() {
        return tuTiengAnh;
    }

    public void setTuTiengAnh(String tuTiengAnh) {
        this.tuTiengAnh = tuTiengAnh;
    }

    public String getTuTiengViet() {
        return tuTiengViet;
    }

    public void setTuTiengViet(String tuTiengViet) {
        this.tuTiengViet = tuTiengViet;
    }

    public String getGioiTuDiKem() {
        return gioiTuDiKem;
    }

    public void setGioiTuDiKem(String gioiTuDiKem) {
        this.gioiTuDiKem = gioiTuDiKem;
    }

    public String getCachPhatAm() {
        return cachPhatAm;
    }

    public void setCachPhatAm(String cachPhatAm) {
        this.cachPhatAm = cachPhatAm;
    }

    public String getLoaiTu() {
        return loaiTu;
    }

    public void setLoaiTu(String loaiTu) {
        this.loaiTu = loaiTu;
    }

    public String getViDu() {
        return viDu;
    }

    public void setViDu(String viDu) {
        this.viDu = viDu;
    }

    public String getViDuTiengViet() {
        return viDuTiengViet;
    }

    public void setViDuTiengViet(String viDuTiengViet) {
        this.viDuTiengViet = viDuTiengViet;
    }

    public byte[] getAnhTuVung() {
        return anhTuVung;
    }

    public void setAnhTuVung(byte[] anhTuVung) {
        this.anhTuVung = anhTuVung;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
