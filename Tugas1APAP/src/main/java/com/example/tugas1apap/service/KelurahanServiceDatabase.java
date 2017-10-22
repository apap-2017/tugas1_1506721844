package com.example.tugas1apap.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tugas1apap.dao.KelurahanMapper;
import com.example.tugas1apap.model.KelurahanModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KelurahanServiceDatabase implements KelurahanService {
	@Autowired
	private KelurahanMapper kelurahanMapper;
	
	@Override	
	public KelurahanModel selectKelurahan(String id) {
		log.info ("select kelurahan with id {}", id);
		return kelurahanMapper.selectKelurahan(id);
	}

	@Override
	public List<KelurahanModel> selectAllKelurahan() {
		log.info("select * from kelurahan");
		return kelurahanMapper.selectAllKelurahan();
	}

	@Override
	public List<KelurahanModel> selectKelurahanByKecamatan(String idKecamatan) {
		log.info("select * from kelurahan where id_kecamatan = {}", idKecamatan);
		return kelurahanMapper.selectKelurahanByKecamatan(idKecamatan);
	}
}
