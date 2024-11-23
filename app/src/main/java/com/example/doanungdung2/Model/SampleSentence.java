package com.example.doanungdung2.Model;

public class SampleSentence {

    String maMauCau;
    String maChuDeMauCau;
    String MauCau;
    String phienDich;
    String tinhHuongSuDung;
    boolean selected;


    public SampleSentence() {
    }

    public SampleSentence(String maMauCau, String maChuDeMauCau, String MauCau, String phienDich, String tinhHuongSuDung) {
        this.maMauCau = maMauCau;
        this.maChuDeMauCau = maChuDeMauCau;
        this.MauCau = MauCau;
        this.phienDich = phienDich;
        this.tinhHuongSuDung = tinhHuongSuDung;
    }

    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    public void setMaMauCau(String maMauCau) {
        this.maMauCau = maMauCau;
    }

    public void setMaChuDeMauCau(String maChuDeMauCau) {
        this.maChuDeMauCau = maChuDeMauCau;
    }

    public void setMauCau(String MauCau) {
        this.MauCau = MauCau;
    }

    public void setPhienDich(String phienDich) {
        this.phienDich = phienDich;
    }

    public void setTinhHuongSuDung(String tinhHuongSuDung) {
        this.tinhHuongSuDung = tinhHuongSuDung;
    }

    public String getMaMauCau() {
        return maMauCau;
    }

    public String getMaChuDeMauCau() {
        return maChuDeMauCau;
    }

    public String getMauCau() {
        return MauCau;
    }

    public String getPhienDich() {
        return phienDich;
    }

    public String getTinhHuongSuDung() {
        return tinhHuongSuDung;
    }
}
