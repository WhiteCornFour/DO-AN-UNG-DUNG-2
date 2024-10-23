package com.example.doanungdung2.Model;

import java.io.Serializable;

public class Question implements Serializable {
    String maCauHoi;
    String noiDungCauHoi;
    String cauA;
    String cauB;
    String cauC;
    String cauD;
    String dapAn;
    String mucDo;
    String maBaiTap;
    String maDangBaiTap;

    public Question(String maCauHoi, String noiDungCauHoi, String cauA, String cauB, String cauC, String cauD, String dapAn, String mucDo, String maBaiTap, String maDangBaiTap) {
        this.maCauHoi = maCauHoi;
        this.noiDungCauHoi = noiDungCauHoi;
        this.cauA = cauA;
        this.cauB = cauB;
        this.cauC = cauC;
        this.cauD = cauD;
        this.dapAn = dapAn;
        this.mucDo = mucDo;
        this.maBaiTap = maBaiTap;
        this.maDangBaiTap = maDangBaiTap;
    }

    public Question() {
    }

    public String getMaCauHoi() {
        return maCauHoi;
    }

    public void setMaCauHoi(String maCauHoi) {
        this.maCauHoi = maCauHoi;
    }

    public String getNoiDungCauHoi() {
        return noiDungCauHoi;
    }

    public void setNoiDungCauHoi(String noiDungCauHoi) {
        this.noiDungCauHoi = noiDungCauHoi;
    }

    public String getCauA() {
        return cauA;
    }

    public void setCauA(String cauA) {
        this.cauA = cauA;
    }

    public String getCauB() {
        return cauB;
    }

    public void setCauB(String cauB) {
        this.cauB = cauB;
    }

    public String getCauC() {
        return cauC;
    }

    public void setCauC(String cauC) {
        this.cauC = cauC;
    }

    public String getCauD() {
        return cauD;
    }

    public void setCauD(String cauD) {
        this.cauD = cauD;
    }

    public String getDapAn() {
        return dapAn;
    }

    public void setDapAn(String dapAn) {
        this.dapAn = dapAn;
    }

    public String getMucDo() {
        return mucDo;
    }

    public void setMucDo(String mucDo) {
        this.mucDo = mucDo;
    }

    public String getMaBaiTap() {
        return maBaiTap;
    }

    public void setMaBaiTap(String maBaiTap) {
        this.maBaiTap = maBaiTap;
    }

    public String getMaDangBaiTap() {
        return maDangBaiTap;
    }

    public void setMaDangBaiTap(String maDangBaiTap) {
        this.maDangBaiTap = maDangBaiTap;
    }
}
