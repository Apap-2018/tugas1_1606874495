package com.apap.tugas1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.InstansiDb;
import com.apap.tugas1.repository.JabatanDb;
import com.apap.tugas1.service.PegawaiService;

@Controller
public class PegawaiController {
	
	@Autowired
	private PegawaiService pegawaiService;
	
	@Autowired
	private JabatanDb jabatanDb;
	
	@Autowired
	private InstansiDb instansiDb;
	
	@RequestMapping("/")
	private String landing(Model model) {
		List<JabatanModel> jabatanList = jabatanDb.findAll();
		List<InstansiModel> instansiList = instansiDb.findAll();
		model.addAttribute("jabatanList",jabatanList);
		model.addAttribute("instansiList",instansiList);
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
	
	@RequestMapping(value = "/pegawai/termuda-tertua", method = RequestMethod.GET)
	private String viewMudaTua(@RequestParam ("instansiId") long instansiId, Model model) {
		PegawaiModel pegawaiMuda = pegawaiService.findYoungest(instansiDb.getOne(instansiId));
		PegawaiModel pegawaiTua = pegawaiService.findOldest(instansiDb.getOne(instansiId));
		model.addAttribute("pegawaiMuda", pegawaiMuda);
		model.addAttribute("pegawaiTua", pegawaiTua);
		return "view-pegawai-muda-tua";
	}
}
