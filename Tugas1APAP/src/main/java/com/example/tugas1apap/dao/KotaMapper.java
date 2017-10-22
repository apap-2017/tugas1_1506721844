package com.example.tugas1apap.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import com.example.tugas1apap.model.KotaModel;

@Mapper
public interface KotaMapper {
	@Select("select * " + 
    		"from kota " + 
    		 "where id = #{id}")
	@Results(value = {
			@Result(property="id", column="id"),
			@Result(property="kodeKota", column="kode_kota"),
			@Result(property="namaKota", column="nama_kota")
		}) KotaModel selectKota (@Param("id") String id);
	
	@Select("select * from kota order by nama_kota asc")
    @Results(value = {
    		@Result(property="id", column="id"),
    		@Result(property="kodeKota", column="kode_kota"),
    		@Result(property="namaKota", column="nama_kota")
    })
    List<KotaModel> selectAllKota ();
}
