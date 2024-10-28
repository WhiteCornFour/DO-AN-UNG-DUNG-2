package com.example.doanungdung2.Model;

public class Dictionary {
    String maTuVung;
    String tuTiengAnh;
    String tuTiengViet;
    String gioiTuDiKem;
    String cachPhatAm;
    String loaiTu;
    String viDu;

    boolean isChecked = false;

    public Dictionary(String maTuVung, String tuTiengAnh, String tuTiengViet, String gioiTuDiKem, String cachPhatAm, String loaiTu, String viDu) {
        this.maTuVung = maTuVung;
        this.tuTiengAnh = tuTiengAnh;
        this.tuTiengViet = tuTiengViet;
        this.gioiTuDiKem = gioiTuDiKem;
        this.cachPhatAm = cachPhatAm;
        this.loaiTu = loaiTu;
        this.viDu = viDu;
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


    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
