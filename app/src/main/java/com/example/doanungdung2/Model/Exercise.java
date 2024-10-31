package com.example.doanungdung2.Model;

import java.io.Serializable;

public class Exercise implements Serializable {
    String maBaiTap;
    String tenBaiTap;
    int soCau;
    String mucDo;
    String thoiGian;
    String moTa;
    String maDangBaiTap;

    public Exercise() {
    }

    public Exercise(String maBaiTap, String tenBaiTap, int soCau, String mucDo, String thoiGian, String moTa, String maDangBaiTap) {
        this.maBaiTap = maBaiTap;
        this.tenBaiTap = tenBaiTap;
        this.soCau = soCau;
        this.mucDo = mucDo;
        this.thoiGian = thoiGian;
        this.moTa = moTa;
        this.maDangBaiTap = maDangBaiTap;
    }

    public String getMaBaiTap() {
        return maBaiTap;
    }

    public void setMaBaiTap(String maBaiTap) {
        this.maBaiTap = maBaiTap;
    }

    public String getTenBaiTap() {
        return tenBaiTap;
    }

    public void setTenBaiTap(String tenBaiTap) {
        this.tenBaiTap = tenBaiTap;
    }

    public int getSoCau() {
        return soCau;
    }

    public void setSoCau(int soCau) {
        this.soCau = soCau;
    }

    public String getMucDo() {
        return mucDo;
    }

    public void setMucDo(String mucDo) {
        this.mucDo = mucDo;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getMaDangBaiTap() {
        return maDangBaiTap;
    }

    public void setMaDangBaiTap(String maDangBaiTap) {
        this.maDangBaiTap = maDangBaiTap;
    }
}
