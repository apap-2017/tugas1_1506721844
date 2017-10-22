package com.example.tugas1apap.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.tugas1apap.model.KeluargaModel;
import com.example.tugas1apap.model.PendudukModel;

@Mapper
public interface KeluargaMapper {
	@Select("select * " + 
    		"from keluarga " + 
    		"where nomor_kk = #{nkk}")
	@Results(value = {
			@Result(property="id", column="id"),
			@Result(property="nomorKk", column="nomor_kk"),
			@Result(property="alamat", column="alamat"),
			@Result(property="rt", column="RT"),
			@Result(property="rw", column="RW"),
			@Result(property="idKelurahan", column="id_kelurahan"),
			@Result(property="isTidakBerlaku", column="is_tidak_berlaku")
		}) KeluargaModel selectKeluarga (@Param("nkk") String nkk);
	
	@Select("select * " + 
    		"from keluarga " + 
    		"where id = #{id}")
	@Results(value = {
			@Result(property="id", column="id"),
			@Result(property="nomorKk", column="nomor_kk"),
			@Result(property="alamat", column="alamat"),
			@Result(property="rt", column="RT"),
			@Result(property="rw", column="RW"),
			@Result(property="idKelurahan", column="id_kelurahan"),
			@Result(property="isTidakBerlaku", column="is_tidak_berlaku")
		}) KeluargaModel selectKeluargaWithID (@Param("id") String id);
	
	@Select("select * " +
			"from penduduk " +
			"where id_keluarga = #{id}")
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
		}) List<PendudukModel> selectAnggotaKeluarga(@Param("id") String id);
	
	@Select("select count(*) as nkk " +
			"from keluarga " +
			"where nomor_kk like #{nkkPart}")
	String findSimilarNKK (@Param("nkkPart") String nkkPart);
	
	@Select("SELECT id FROM keluarga ORDER BY ID DESC LIMIT 1")
	String selectLastID();
	
	@Insert("insert into keluarga (id, nomor_kk, alamat, RT, RW, id_kelurahan, is_tidak_berlaku) "
			+ "values (#{keluarga.id}, #{keluarga.nomorKk}, #{keluarga.alamat}, #{keluarga.rt}, #{keluarga.rw}, "
			+ "#{keluarga.idKelurahan}, #{keluarga.isTidakBerlaku})")
	void addKeluarga(@Param("keluarga") KeluargaModel keluarga);
	
	@Update("update keluarga set nomor_kk=#{keluarga.nomorKk}, alamat=#{keluarga.alamat}, RT=#{keluarga.rt}, RW=#{keluarga.rw}, "
			+ "id_kelurahan=#{keluarga.idKelurahan}, is_tidak_berlaku=#{keluarga.isTidakBerlaku} "
			+ "where nomor_kk = #{nkkLama}")
	void updateKeluarga(
			@Param("keluarga") KeluargaModel keluarga,
			@Param("nkkLama") String nkkLama
			);
	
	@Select("select count(*) from penduduk where id_keluarga=#{idKeluarga} and is_wafat=0")
	int countIsNotWafat(@Param("idKeluarga") String idKeluarga);
	
	@Update("update keluarga set is_tidak_berlaku=1 where id=#{idKeluarga}")
	void setTidakBerlaku(@Param("idKeluarga") String idKeluarga);
}
