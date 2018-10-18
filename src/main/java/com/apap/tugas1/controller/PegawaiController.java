package com.apap.tugas1.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.JabatanDb;
import com.apap.tugas1.repository.PegawaiDb;
import com.apap.tugas1.service.PegawaiService;

@Controller
public class PegawaiController {
	
	@Autowired
	private PegawaiService pegawaiService;
	
	@Autowired
	private PegawaiDb pegawaiDb;
	
	@Autowired
	private JabatanDb jabatanDb;
	
	@RequestMapping("/")
	private String landing(Model model) {
		List<JabatanModel> jabatanList = jabatanDb.findAll();
		model.addAttribute("jabatanList",jabatanList);
		return "landing";
	}
	
	@RequestMapping(value ="/pegawai/", method = RequestMethod.GET)
	private String view(@RequestParam (value="nip") String nip, Model model) {
		PegawaiModel pegawai = pegawaiService.getPegawaiDetailByNip(nip).get();
		List<JabatanModel> jabatanList = pegawai.getJabatanList();
		int gaji = pegawaiService.countGaji(nip);
		model.addAttribute("gaji", gaji);
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("instansi", pegawai.getInstansi().getNama());
		model.addAttribute("jabatanList", jabatanList);
		return "view-pegawai";
	}
}
