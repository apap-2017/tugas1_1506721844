package com.example.tugas1apap.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KeluargaModel {
	private String id;
	private String nomorKk;
	private String alamat;
	private String rt;
	private String rw;
	private String idKelurahan;
	private String isTidakBerlaku;
}
