package com.example.demo.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.Sfrct;

@Mapper
public interface SfrctDAO {
	public int insert(Sfrct sfrct);
	public int autoDelete();
	public List<Sfrct> getWeather(String regid);
	public List<Sfrct> allRegWeather(String hour1);
}
