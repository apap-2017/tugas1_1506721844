package com.example.tugas1apap.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KelurahanModel {
	private int id;
	private String kodeKelurahan;
	private String idKecamatan;
	private String namaKelurahan;
	private String kodePos;
}
