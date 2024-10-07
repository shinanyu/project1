package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dao.BestLocDAO;
import com.example.demo.model.Bestloc;

@Service
public class BestlocService  {
	
	@Autowired
	private BestLocDAO bestLocDAO;
	
	public List<Bestloc> getlist() {
		return bestLocDAO.select();	
	}

}
