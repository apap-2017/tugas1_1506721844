package com.example.tugas1apap.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KecamatanModel {
	private int id;
	private String kodeKecamatan;
	private String idKota;
	private String namaKecamatan;
}	
