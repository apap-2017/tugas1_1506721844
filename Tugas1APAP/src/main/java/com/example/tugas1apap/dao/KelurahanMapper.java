package com.example.tugas1apap.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.example.tugas1apap.model.KelurahanModel;

@Mapper
public interface KelurahanMapper {
	@Select("select * " + 
    		"from kelurahan " + 
    		 "where id = #{id}")
	@Results(value = {
			@Result(property="id", column="id"),
			@Result(property="kodeKelurahan", column="kode_kelurahan"),
			@Result(property="idKecamatan", column="id_kecamatan"),
			@Result(property="namaKelurahan", column="nama_kelurahan"),
			@Result(property="kodePos", column="kode_pos")
		}) KelurahanModel selectKelurahan (@Param("id") String id);
	
	@Select("select * from kelurahan order by nama_kelurahan asc")
    @Results(value = {
    		@Result(property="id", column="id"),
    		@Result(property="kodeKelurahan", column="kode_kelurahan"),
    		@Result(property="idKecamatan", column="id_kecamatan"),
    		@Result(property="namaKelurahan", column="nama_kelurahan"),
    		@Result(property="kodePos", column="kode_pos")
    })
    List<KelurahanModel> selectAllKelurahan ();
	
	@Select("select * from kelurahan where id_kecamatan = #{idKecamatan} order by nama_kelurahan asc")
    @Results(value = {
    		@Result(property="id", column="id"),
    		@Result(property="kodeKelurahan", column="kode_kelurahan"),
    		@Result(property="idKecamatan", column="id_kecamatan"),
    		@Result(property="namaKelurahan", column="nama_kelurahan"),
    		@Result(property="kodePos", column="kode_pos")
    })
    List<KelurahanModel> selectKelurahanByKecamatan (@Param("idKecamatan") String idKecamatan);
}
