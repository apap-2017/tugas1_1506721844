package com.example.tugas1apap.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tugas1apap.dao.PendudukMapper;
import com.example.tugas1apap.model.PendudukModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PendudukServiceDatabase implements PendudukService {
	@Autowired
	private PendudukMapper pendudukMapper;
	
	@Override	
	public PendudukModel selectPenduduk(String nik) {
		log.info ("select penduduk with nik {}", nik);
		return pendudukMapper.selectPenduduk(nik);
	}

	@Override
	public String findSimilarNIK(String nikPart) {
		log.info("select nik from penduduk where nik like {} order by nik desc limit 1", nikPart);
		String nikParts = nikPart + "%";
		return pendudukMapper.findSimilarNIK(nikParts);
	}

	@Override
	public void addPenduduk(PendudukModel penduduk) {
		log.info("insert into penduduk (nik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, is_wni, id_keluarga, agama, pekerjaan, status_perkawinan, status_dalam_keluarga, golongan_darah, is_wafat) values {}", penduduk);
		pendudukMapper.addPenduduk(penduduk);
	}
	
	@Override
	public String selectLastID() {
		log.info("SELECT id FROM penduduk ORDER BY ID DESC LIMIT 1");
		return pendudukMapper.selectLastID();
	}

	@Override
	public void updatePenduduk (PendudukModel penduduk, String nikLama) {
		log.info("update penduduk set where nik = {}", nikLama);
		pendudukMapper.updatePenduduk(penduduk, nikLama);
	}

	@Override
	public void matikanPenduduk(String nik) {
		log.info("update penduduk set is_wafat = 1 where nik = {}", nik);
		pendudukMapper.matikanPenduduk(nik);
	}

	@Override
	public List<PendudukModel> selectPendudukByKelurahan(String kode_kelurahan) {
		log.info("select p.nik, p.nama, p.jenis_kelamin from penduduk p join keluarga k on p.id_keluarga=k.id join kelurahan kel on k.id_kelurahan = kel.id where kel.id={}", kode_kelurahan);
		return pendudukMapper.selectPendudukByKelurahan(kode_kelurahan);
	}

	@Override
	public PendudukModel selectPendudukTermuda(String id_kelurahan) {
		log.info("select p.nik, p.nama, p.tanggal_lahir from penduduk p join keluarga k on p.id_keluarga=k.id join kelurahan kel on k.id_kelurahan = kel.id where kel.id={} order by tanggal_lahir desc limit 1", id_kelurahan);
		return pendudukMapper.selectPendudukTermuda(id_kelurahan);
	}

	@Override
	public PendudukModel selectPendudukTertua(String id_kelurahan) {
		log.info("select p.nik, p.nama, p.tanggal_lahir from penduduk p join keluarga k on p.id_keluarga=k.id join kelurahan kel on k.id_kelurahan = kel.id where kel.id={} order by tanggal_lahir asc limit 1", id_kelurahan);
		return pendudukMapper.selectPendudukTertua(id_kelurahan);
	}
}
