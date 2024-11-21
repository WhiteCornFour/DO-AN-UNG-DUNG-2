package com.example.doanungdung2.Model;

import java.io.Serializable;

public class Assigment implements Serializable {
    String maBaiLam;
    String thoiGianBatDau;
    String thoiGianKetThuc;
    String tongThoiGianLamBai;
    int soLuongCauDung;
    float diem;
    int lanLam;
    String maBaiTap;
    String maNguoiDung;

    public Assigment(String maBaiLam, String thoiGianBatDau, String thoiGianKetThuc, String tongThoiGianLamBai, int soLuongCauDung, float diem, int lanLam, String maBaiTap, String maNguoiDung) {
        this.maBaiLam = maBaiLam;
        this.thoiGianBatDau = thoiGianBatDau;
        this.thoiGianKetThuc = thoiGianKetThuc;
        this.tongThoiGianLamBai = tongThoiGianLamBai;
        this.soLuongCauDung = soLuongCauDung;
        this.diem = diem;
        this.lanLam = lanLam;
        this.maBaiTap = maBaiTap;
        this.maNguoiDung = maNguoiDung;
    }

    public Assigment() {
    }

    public String getMaBaiLam() {
        return maBaiLam;
    }

    public void setMaBaiLam(String maBaiLam) {
        this.maBaiLam = maBaiLam;
    }

    public String getThoiGianBatDau() {
        return thoiGianBatDau;
    }

    public void setThoiGianBatDau(String thoiGianBatDau) {
        this.thoiGianBatDau = thoiGianBatDau;
    }

    public String getThoiGianKetThuc() {
        return thoiGianKetThuc;
    }

    public void setThoiGianKetThuc(String thoiGianKetThuc) {
        this.thoiGianKetThuc = thoiGianKetThuc;
    }

    public String getTongThoiGianLamBai() {
        return tongThoiGianLamBai;
    }

    public void setTongThoiGianLamBai(String tongThoiGianLamBai) {
        this.tongThoiGianLamBai = tongThoiGianLamBai;
    }

    public int getSoLuongCauDung() {
        return soLuongCauDung;
    }

    public void setSoLuongCauDung(int soLuongCauDung) {
        this.soLuongCauDung = soLuongCauDung;
    }

    public float getDiem() {
        return diem;
    }

    public void setDiem(float diem) {
        this.diem = diem;
    }

    public int getLanLam() {
        return lanLam;
    }

    public void setLanLam(int lanLam) {
        this.lanLam = lanLam;
    }

    public String getMaBaiTap() {
        return maBaiTap;
    }

    public void setMaBaiTap(String maBaiTap) {
        this.maBaiTap = maBaiTap;
    }

    public String getMaNguoiDung() {
        return maNguoiDung;
    }

    public void setMaNguoiDung(String maNguoiDung) {
        this.maNguoiDung = maNguoiDung;
    }
}
