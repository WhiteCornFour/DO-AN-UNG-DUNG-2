package com.example.doanungdung2.Model;

public class AssignmentDetail {
    String maChiTietBaiLam;
    String cauTraLoi;
    String ketQuaCauTraLoi;
    String maCauHoi;
    String maBaiLam;

    public AssignmentDetail(String maChiTietBaiLam, String cauTraLoi, String ketQuaCauTraLoi, String maCauHoi, String maBaiLam) {
        this.maChiTietBaiLam = maChiTietBaiLam;
        this.cauTraLoi = cauTraLoi;
        this.ketQuaCauTraLoi = ketQuaCauTraLoi;
        this.maCauHoi = maCauHoi;
        this.maBaiLam = maBaiLam;
    }

    public AssignmentDetail() {
    }

    public String getMaChiTietBaiLam() {
        return maChiTietBaiLam;
    }

    public void setMaChiTietBaiLam(String maChiTietBaiLam) {
        this.maChiTietBaiLam = maChiTietBaiLam;
    }

    public String getCauTraLoi() {
        return cauTraLoi;
    }

    public void setCauTraLoi(String cauTraLoi) {
        this.cauTraLoi = cauTraLoi;
    }

    public String getKetQuaCauTraLoi() {
        return ketQuaCauTraLoi;
    }

    public void setKetQuaCauTraLoi(String ketQuaCauTraLoi) {
        this.ketQuaCauTraLoi = ketQuaCauTraLoi;
    }

    public String getMaCauHoi() {
        return maCauHoi;
    }

    public void setMaCauHoi(String maCauHoi) {
        this.maCauHoi = maCauHoi;
    }

    public String getMaBaiLam() {
        return maBaiLam;
    }

    public void setMaBaiLam(String maBaiLam) {
        this.maBaiLam = maBaiLam;
    }
}
