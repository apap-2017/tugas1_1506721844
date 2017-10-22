package com.example.tugas1apap.service;

import java.util.List;

import com.example.tugas1apap.model.KecamatanModel;

public interface KecamatanService {
	KecamatanModel selectKecamatan(String id);
	List<KecamatanModel> selectAllKecamatan();
	List<KecamatanModel> selectKecamatanByKota(String idKota);
}