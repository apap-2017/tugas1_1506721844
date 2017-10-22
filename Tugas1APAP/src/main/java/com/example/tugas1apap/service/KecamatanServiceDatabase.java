package com.example.tugas1apap.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tugas1apap.dao.KecamatanMapper;
import com.example.tugas1apap.model.KecamatanModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KecamatanServiceDatabase implements KecamatanService {
	@Autowired
	private KecamatanMapper kecamatanMapper;
	
	@Override	
	public KecamatanModel selectKecamatan(String id) {
		log.info ("select kecamatan with id {}", id);
		return kecamatanMapper.selectKecamatan(id);
	}

	@Override
	public List<KecamatanModel> selectAllKecamatan() {
		log.info("select * from kecamatan");
		return kecamatanMapper.selectAllKecamatan();
	}

	@Override
	public List<KecamatanModel> selectKecamatanByKota(String idKota) {
		log.info("select * from kecamatan where id_kota = {}", idKota);
		return kecamatanMapper.selectKecamatanByKota(idKota);
	}

}
