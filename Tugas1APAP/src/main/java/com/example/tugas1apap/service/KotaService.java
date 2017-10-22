package com.example.tugas1apap.service;

import java.util.List;

import com.example.tugas1apap.model.KotaModel;

public interface KotaService {
	KotaModel selectKota(String id);
	List<KotaModel> selectAllKota();
}