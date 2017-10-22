package com.example.tugas1apap.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.tugas1apap.model.PendudukModel;

@Mapper
public interface PendudukMapper {
	@Select("select * " + 
    		"from penduduk " + 
    		 "where nik = #{nik}")
	@Results(value = {
			@Result(property="nik", column="nik"),
		    @Result(property="nama", column="nama"),
		    @Result(property="tempatLahir", column="tempat_lahir"),
		    @Result(property="tanggalLahir", column="tanggal_lahir"),
		    @Result(property="jenisKelamin", column="jenis_kelamin"),
		    @Result(property="isWni", column="is_wni"),
		    @Result(property="idKeluarga", column="id_keluarga"),
		    @Result(property="agama", column="agama"),
		    @Result(property="occupation", column="pekerjaan"),
		    @Result(property="statusPerkawinan", column="status_perkawinan"),
		    @Result(property="statusDalamKeluarga", column="status_dalam_keluarga"),
		    @Result(property="golonganDarah", column="golongan_darah"),
		    @Result(property="isWafat", column="is_wafat")
		}) PendudukModel selectPenduduk (@Param("nik") String nik);
	
	@Select("select count(*) as nik " +
			"from penduduk " +
			"where nik like #{nikPart}")
	String findSimilarNIK (@Param("nikPart") String nikPart);
	
	@Select("SELECT id FROM penduduk ORDER BY ID DESC LIMIT 1")
	String selectLastID();
	
	@Insert("insert into penduduk (id, nik, nama, tempat_lahir, tanggal_lahir, "
			+ "jenis_kelamin, is_wni, id_keluarga, agama, pekerjaan, status_perkawinan, status_dalam_keluarga, "
			+ "golongan_darah, is_wafat) "
			+ "values (#{penduduk.id}, #{penduduk.nik}, #{penduduk.nama}, #{penduduk.tempatLahir}, #{penduduk.tanggalLahir}, "
					+ "#{penduduk.jenisKelamin}, #{penduduk.isWni}, #{penduduk.idKeluarga}, #{penduduk.agama}, #{penduduk.occupation}, "
					+ "#{penduduk.statusPerkawinan}, #{penduduk.statusDalamKeluarga}, #{penduduk.golonganDarah}, #{penduduk.isWafat})")
	void addPenduduk(@Param("penduduk") PendudukModel penduduk);

	@Update("update penduduk set nik=#{penduduk.nik}, nama=#{penduduk.nama}, tempat_lahir=#{penduduk.tempatLahir}, tanggal_lahir=#{penduduk.tanggalLahir}, jenis_kelamin=#{penduduk.jenisKelamin}, is_wni=#{penduduk.isWni}, id_keluarga=#{penduduk.idKeluarga}, agama=#{penduduk.agama}, pekerjaan=#{penduduk.occupation}, status_perkawinan=#{penduduk.statusPerkawinan}, status_dalam_keluarga=#{penduduk.statusDalamKeluarga}, golongan_darah=#{penduduk.golonganDarah}, is_wafat=#{penduduk.isWafat} "
			+ "where nik=#{nikLama}")
	void updatePenduduk(
			@Param("penduduk") PendudukModel penduduk,
			@Param("nikLama") String nikLama
			);
	@Update("update penduduk set is_wafat=1 "
			+ "where nik=#{nik}")
	void matikanPenduduk(@Param("nik") String nik);
	
	@Select("select p.nik, p.nama, p.jenis_kelamin from penduduk p join keluarga k on p.id_keluarga=k.id join kelurahan kel on k.id_kelurahan = kel.id join kecamatan kec on kel.id_kecamatan = kec.id join kota kot on kec.id_kota = kot.id where kel.id=#{kode_kelurahan}")
	@Results(value = {
			@Result(property="nik", column="nik"),
		    @Result(property="nama", column="nama"),
		    @Result(property="jenisKelamin", column="jenis_kelamin")})
	List<PendudukModel> selectPendudukByKelurahan(@Param("kode_kelurahan") String kode_kelurahan);
	
	@Select("select p.nik, p.nama, p.tanggal_lahir from penduduk p join keluarga k on p.id_keluarga=k.id join kelurahan kel on k.id_kelurahan = kel.id where kel.id=#{id_kelurahan} order by tanggal_lahir desc limit 1")
	@Results(value = {
			@Result(property="tanggalLahir", column="tanggal_lahir")
	})
	PendudukModel selectPendudukTermuda(@Param("id_kelurahan") String id_kelurahan);
	
	@Select("select p.nik, p.nama, p.tanggal_lahir from penduduk p join keluarga k on p.id_keluarga=k.id join kelurahan kel on k.id_kelurahan = kel.id where kel.id=#{id_kelurahan} order by tanggal_lahir asc limit 1")
	@Results(value = {
			@Result(property="tanggalLahir", column="tanggal_lahir")
	})
	PendudukModel selectPendudukTertua(@Param("id_kelurahan") String id_kelurahan);
}