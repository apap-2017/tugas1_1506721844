package com.example.tugas1apap.service;

import java.util.List;

import com.example.tugas1apap.model.KelurahanModel;

public interface KelurahanService {
	KelurahanModel selectKelurahan(String id);
	List<KelurahanModel> selectAllKelurahan();
	List<KelurahanModel> selectKelurahanByKecamatan(String idKecamatan);
}
