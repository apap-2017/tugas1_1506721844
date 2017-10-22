package com.example.tugas1apap.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PendudukModel {
	private int id;
	private String nik;
	private String nama;
	private String tempatLahir;
	private String tanggalLahir;
	private int jenisKelamin;;
	private String isWni;
	private String idKeluarga;
	private String agama;
	private String occupation;
	private String statusPerkawinan;
	private String statusDalamKeluarga;
	private String golonganDarah;
	private String isWafat;
}
