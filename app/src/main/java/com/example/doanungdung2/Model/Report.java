package com.example.doanungdung2.Model;

import java.io.Serializable;

public class Report implements Serializable {
    private String maBaoCao;
    private String noiDungBaoCao;
    private String ngayBaoCao;
    private String trangThaiBaoCao;
    private byte[] anhBaoCao;
    private String maNguoiDung;

    public Report(String maBaoCao, String noiDungBaoCao, String ngayBaoCao, String trangThaiBaoCao, byte[] anhBaoCao, String maNguoiDung) {
        this.maBaoCao = maBaoCao;
        this.noiDungBaoCao = noiDungBaoCao;
        this.ngayBaoCao = ngayBaoCao;
        this.trangThaiBaoCao = trangThaiBaoCao;
        this.anhBaoCao = anhBaoCao;
        this.maNguoiDung = maNguoiDung;
    }

    public Report() {
    }

    public String getMaBaoCao() {
        return maBaoCao;
    }

    public void setMaBaoCao(String maBaoCao) {
        this.maBaoCao = maBaoCao;
    }

    public String getNoiDungBaoCao() {
        return noiDungBaoCao;
    }

    public void setNoiDungBaoCao(String noiDungBaoCao) {
        this.noiDungBaoCao = noiDungBaoCao;
    }

    public String getNgayBaoCao() {
        return ngayBaoCao;
    }

    public void setNgayBaoCao(String ngayBaoCao) {
        this.ngayBaoCao = ngayBaoCao;
    }

    public String getTrangThaiBaoCao() {
        return trangThaiBaoCao;
    }

    public void setTrangThaiBaoCao(String trangThaiBaoCao) {
        this.trangThaiBaoCao = trangThaiBaoCao;
    }

    public byte[] getAnhBaoCao() {
        return anhBaoCao;
    }

    public void setAnhBaoCao(byte[] anhBaoCao) {
        this.anhBaoCao = anhBaoCao;
    }

    public String getMaNguoiDung() {
        return maNguoiDung;
    }

    public void setMaNguoiDung(String maNguoiDung) {
        this.maNguoiDung = maNguoiDung;
    }
}
