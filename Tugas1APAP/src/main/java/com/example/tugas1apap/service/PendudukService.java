package com.example.tugas1apap.service;

import java.util.List;

import com.example.tugas1apap.model.PendudukModel;

public interface PendudukService {
	PendudukModel selectPenduduk(String nik);
	String findSimilarNIK(String nikPart);
	void addPenduduk (PendudukModel penduduk);
	String selectLastID ();
	void updatePenduduk (PendudukModel penduduk, String nikLama);
	void matikanPenduduk (String nik);
	List<PendudukModel> selectPendudukByKelurahan (String kode_kelurahan);
	PendudukModel selectPendudukTermuda(String id_kelurahan);
	PendudukModel selectPendudukTertua(String id_kelurahan);
}
