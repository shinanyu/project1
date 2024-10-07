package com.example.demo.dao;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.model.Dust;
import com.example.demo.model.Weather;

@Mapper
public interface DustDAO {
	public int insert(Dust dust);
	public int autoDelete();
	public Dust getOne(Weather weather);
	public Dust getTwo(Weather weather);
}
