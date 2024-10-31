package com.example.doanungdung2.Model;

  public class TopicSentence {

    String maChuDeMauCau;
    String tenChuDeMauCau;
    String moTa;


    public TopicSentence() {
    }

    public TopicSentence(String maChuDeMauCau, String tenChuDeMauCau, String moTa) {
        this.maChuDeMauCau = maChuDeMauCau;
        this.tenChuDeMauCau = tenChuDeMauCau;
        this.moTa = moTa;
    }

    public void setTenChuDeMauCau(String tenChuDeMauCau) {
        this.tenChuDeMauCau = tenChuDeMauCau;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public void setMaChuDeMauCau(String maChuDeMauCau) {
        this.maChuDeMauCau = maChuDeMauCau;
    }

    public String getTenChuDeMauCau() {
        return tenChuDeMauCau;
    }

    public String getMoTa() {
        return moTa;
    }

    public String getMaChuDeMauCau() {
        return maChuDeMauCau;
    }
}
