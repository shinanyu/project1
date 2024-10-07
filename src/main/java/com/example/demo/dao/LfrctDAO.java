package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.Lfrct;

@Mapper
public interface LfrctDAO {
	public int insert(Lfrct lfrct);
	public int autoDelete();
	public List<Lfrct> getWeek(String regid);
}
