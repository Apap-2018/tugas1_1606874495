package com.apap.tugas1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.apap.tugas1.repository.PegawaiDb;
import com.apap.tugas1.service.PegawaiService;

@Controller
public class PegawaiController {
	
	@Autowired
	private PegawaiService pegawaiService;
	
	@Autowired
	private PegawaiDb pegawaiDb;
	
	@RequestMapping("/")
	private String landing() {
		return "landing";
	}
	
	
}
