package com.example.tugas1apap.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.example.tugas1apap.model.KecamatanModel;

@Mapper
public interface KecamatanMapper {
	@Select("select * " + 
    		"from kecamatan " + 
    		 "where id = #{id}")
	@Results(value = {
			@Result(property="id", column="id"),
			@Result(property="idKota", column="id_kota"),
			@Result(property="kodeKecamatan", column="kode_kecamatan"),
			@Result(property="namaKecamatan", column="nama_kecamatan"),
		}) KecamatanModel selectKecamatan (@Param("id") String id);
	
	@Select("select * from kecamatan order by nama_kecamatan asc")
    @Results(value = {
    		@Result(property="id", column="id"),
    		@Result(property="kodeKecamatan", column="kode_kecamatan"),
    		@Result(property="idKota", column="id_kota"),
    		@Result(property="namaKecamatan", column="nama_kecamatan")
    })
    List<KecamatanModel> selectAllKecamatan ();
	
	@Select("select * from kecamatan where id_kota = #{idKota} order by nama_kecamatan asc")
    @Results(value = {
    		@Result(property="id", column="id"),
    		@Result(property="kodeKecamatan", column="kode_kecamatan"),
    		@Result(property="idKota", column="id_kota"),
    		@Result(property="namaKecamatan", column="nama_kecamatan")
    })
    List<KecamatanModel> selectKecamatanByKota (@Param("idKota") String idKota);
}