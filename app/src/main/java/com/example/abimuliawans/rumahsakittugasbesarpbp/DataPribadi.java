package com.example.abimuliawans.rumahsakittugasbesarpbp;

public class DataPribadi {

    String dataEmail;
    String dataNama;
    String dataTanggalLahir;
    String dataJenisKelamin;
    String dataAlamat;
    String dataTinggiBadan;
    String dataBeratBadan;
    String dataGolonganDarah;

    public DataPribadi(){

    }

    public DataPribadi(String dataEmail, String dataNama, String dataTanggalLahir, String dataJenisKelamin, String dataAlamat, String dataTinggiBadan, String dataBeratBadan, String dataGolonganDarah) {
        this.dataEmail = dataEmail;
        this.dataNama = dataNama;
        this.dataTanggalLahir = dataTanggalLahir;
        this.dataJenisKelamin = dataJenisKelamin;
        this.dataAlamat = dataAlamat;
        this.dataTinggiBadan = dataTinggiBadan;
        this.dataBeratBadan = dataBeratBadan;
        this.dataGolonganDarah = dataGolonganDarah;
    }


    public String getDataEmail() {
        return dataEmail;
    }

    public String getDataNama() {
        return dataNama;
    }

    public String getDataTanggalLahir() {
        return dataTanggalLahir;
    }

    public String getDataJenisKelamin() {
        return dataJenisKelamin;
    }

    public String getDataAlamat() {
        return dataAlamat;
    }

    public String getDataTinggiBadan() {
        return dataTinggiBadan;
    }

    public String getDataBeratBadan() {
        return dataBeratBadan;
    }

    public String getDataGolonganDarah() {
        return dataGolonganDarah;
    }


    public void setDataEmail(String dataEmail) {
        this.dataEmail = dataEmail;
    }

    public void setDataNama(String dataNama) {
        this.dataNama = dataNama;
    }

    public void setDataTanggalLahir(String dataTanggalLahir) {
        this.dataTanggalLahir = dataTanggalLahir;
    }

    public void setDataJenisKelamin(String dataJenisKelamin) {
        this.dataJenisKelamin = dataJenisKelamin;
    }

    public void setDataAlamat(String dataAlamat) {
        this.dataAlamat = dataAlamat;
    }

    public void setDataTinggiBadan(String dataTinggiBadan) {
        this.dataTinggiBadan = dataTinggiBadan;
    }

    public void setDataBeratBadan(String dataBeratBadan) {
        this.dataBeratBadan = dataBeratBadan;
    }

    public void setDataGolonganDarah(String dataGolonganDarah) {
        this.dataGolonganDarah = dataGolonganDarah;
    }
}
