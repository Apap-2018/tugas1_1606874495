package com.apap.tugas1.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.repository.InstansiDb;
import com.apap.tugas1.repository.JabatanDb;
import com.apap.tugas1.repository.ProvinsiDb;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;
import com.apap.tugas1.service.ProvinsiService;

@Controller
public class PegawaiController {

	@Autowired
	private ProvinsiService provinsiService;
	
	@Autowired
	private PegawaiService pegawaiService;
	
	@Autowired
	private JabatanService jabatanService;
	
	@Autowired
	private JabatanDb jabatanDb;
	
	@Autowired
	private InstansiDb instansiDb;
	
	@Autowired
	private ProvinsiDb provinsiDb;
	
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
	
	@RequestMapping(value = "/pegawai/tambah")
	private String addPegawai(Model model) {
		PegawaiModel pegawai = new PegawaiModel();
		if(pegawai.getJabatanList()==null) pegawai.setJabatanList(new ArrayList<>());
		pegawai.getJabatanList().add(new JabatanModel());
		List<ProvinsiModel> provinsi = provinsiService.provinsiList();
		List<JabatanModel> jabatan = jabatanService.viewAll();
		model.addAttribute("provinsiList", provinsi);
		model.addAttribute("jabatanList", jabatan);
		model.addAttribute("pegawai", pegawai);
		return "add-pegawai";
	}
	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST, params= {"submit"})
	private String addPegawaiSubmit(@ModelAttribute PegawaiModel pegawai, Model model) {
		System.out.println(pegawai.getNama());
		System.out.println(pegawai.getTahunMasuk());
		String nipPegawai = pegawaiService.buatNip(pegawai);
		pegawai.setNip(nipPegawai);
		pegawaiService.addPegawai(pegawai);
		model.addAttribute("pegawai", pegawai);
		return "add";
	}
	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST, params= {"addRow"})
	private String addRow(@ModelAttribute PegawaiModel pegawai, Model model, BindingResult bindingResult) {
		if (pegawai.getJabatanList() == null) {
			pegawai.setJabatanList(new ArrayList());
		}
		System.out.println(pegawai.getJabatanList().size());
		pegawai.getJabatanList().add(new JabatanModel());
		
		List<JabatanModel> jab = jabatanDb.findAll();
		List<ProvinsiModel> prov = provinsiDb.findAll();
		model.addAttribute("provinsiList", prov);
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("jabatanList",jab);
		return "add-pegawai";
	}

	@RequestMapping(value = "/pegawai/tambah/instansi",method = RequestMethod.GET)
	private @ResponseBody List<InstansiModel> cekInstansi(@RequestParam(value="provinsiId") long provinsiId) {
		ProvinsiModel getProv = provinsiDb.findById(provinsiId).get();
		
		return getProv.getInstansiList();
	}
}
