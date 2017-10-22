package com.example.tugas1apap.service;

import java.util.List;

import com.example.tugas1apap.model.KeluargaModel;
import com.example.tugas1apap.model.PendudukModel;

public interface KeluargaService {
	KeluargaModel selectKeluarga(String nkk);
	KeluargaModel selectKeluargaWithID(String id);
	List<PendudukModel> selectAnggotaKeluarga(String id);
	String findSimilarNKK(String nkkPart);
	String selectLastID ();
	void addKeluarga(KeluargaModel keluarga);
	void updateKeluarga(KeluargaModel keluarga, String nkkLama);
	int countIsNotWafat(String idKeluarga);
	void setTidakBerlaku(String idKeluarga);
}