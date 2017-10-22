package com.example.tugas1apap.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tugas1apap.dao.KeluargaMapper;
import com.example.tugas1apap.model.KeluargaModel;
import com.example.tugas1apap.model.PendudukModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class KeluargaServiceDatabase implements KeluargaService {
	@Autowired
	private KeluargaMapper keluargaMapper;
	
	@Override	
	public KeluargaModel selectKeluarga(String nkk) {
		log.info ("select keluarga with nkk {}", nkk);
		return keluargaMapper.selectKeluarga(nkk);
	}
	
	@Override	
	public KeluargaModel selectKeluargaWithID(String id) {
		log.info ("select keluarga with id {}", id);
		return keluargaMapper.selectKeluargaWithID(id);
	}
	
	@Override
	public List<PendudukModel> selectAnggotaKeluarga(String id) {
		log.info("select penduduk with id {}", id);
		return keluargaMapper.selectAnggotaKeluarga(id);
	}

	@Override
	public String findSimilarNKK(String nkkPart) {
		log.info("select count(*) from penduduk where nkk like {}", nkkPart);
		String nkkParts = nkkPart + "%";
		return keluargaMapper.findSimilarNKK(nkkParts);
	}

	@Override
	public String selectLastID() {
		log.info("SELECT id FROM keluarga ORDER BY ID DESC LIMIT 1");
		return keluargaMapper.selectLastID();
	}

	@Override
	public void addKeluarga(KeluargaModel keluarga) {
		log.info("insert into keluarga(id, nomor_kk, alamat, RT, RW, id_kelurahan, is_tidak_berlaku) values {}", keluarga);
		keluargaMapper.addKeluarga(keluarga);
	}

	@Override
	public void updateKeluarga(KeluargaModel keluarga, String nkkLama) {
		log.info("update keluarga set where nkk={}", nkkLama);
		keluargaMapper.updateKeluarga(keluarga, nkkLama);
	}

	@Override
	public int countIsNotWafat(String idKeluarga) {
		log.info("select count(*) from penduduk where id_keluarga={} and is_wafat=0", idKeluarga);
		return keluargaMapper.countIsNotWafat(idKeluarga);
	}

	@Override
	public void setTidakBerlaku(String idKeluarga) {
		log.info("update keluarga set is_tidak_berlaku=1 where id={}", idKeluarga);
		keluargaMapper.setTidakBerlaku(idKeluarga);
	}

}
