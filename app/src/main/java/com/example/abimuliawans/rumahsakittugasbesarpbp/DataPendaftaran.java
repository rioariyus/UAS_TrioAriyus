package com.example.abimuliawans.rumahsakittugasbesarpbp;

public class DataPendaftaran {
    String dokter;
    String tanggalPendaftaran;

    public DataPendaftaran(){}

    public DataPendaftaran(String dokter, String tanggalPendaftaran) {
        this.dokter = dokter;
        this.tanggalPendaftaran = tanggalPendaftaran;
    }

    public String getDokter() {
        return dokter;
    }

    public void setDokter(String dokter) {
        this.dokter = dokter;
    }

    public String getTanggalPendaftaran() {
        return tanggalPendaftaran;
    }

    public void setTangalPendaftaran(String tangalPendaftaran) {
        this.tanggalPendaftaran = tangalPendaftaran;
    }
}
