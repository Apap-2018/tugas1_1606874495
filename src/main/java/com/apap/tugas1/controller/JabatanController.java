package com.apap.tugas1.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.repository.JabatanDb;
import com.apap.tugas1.service.JabatanService;

@Controller
public class JabatanController {
	
	@Autowired
	private JabatanService jabatanService;
	
	@Autowired
	private JabatanDb jabatanDb;
	
	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.GET)
	private String pageAddJabatan(Model model) {
		model.addAttribute("jabatan", new JabatanModel());
		return "add-jabatan";
	}
	
	@RequestMapping(value = "/jabatan/tambah", method = RequestMethod.POST)
	private String addJabatan(@ModelAttribute JabatanModel jabatan){
		jabatanService.addJabatan(jabatan);
		return "add-jabatan";
	}
	
	@RequestMapping(value = "/jabatan/view", method = RequestMethod.GET)
	private String viewPageJabatan(@RequestParam("jabatanId") long jabatanId, Model model) {
		JabatanModel jabatan = jabatanService.viewJabatan(jabatanId);
		model.addAttribute("jabatan", jabatan);
		return "view-jabatan";
	}
	
	@RequestMapping(value = "/jabatan/ubah", method = RequestMethod.GET)
	private String ubahJabatan(@RequestParam("jabatanId") long jabatanId, Model model) {
		JabatanModel jabatan = jabatanService.viewJabatan(jabatanId);
		model.addAttribute("jabatan",jabatan);
		return "update-jabatan";
	}
	
	@RequestMapping(value = "/jabatan/ubah", method = RequestMethod.POST)
	private String ubahJabatanSubmit(@ModelAttribute JabatanModel jabatan, Model model) {
		jabatanService.ubahJabatan(jabatan.getId(), jabatan);
		model.addAttribute("jabatan", jabatan);
		return "update-jabatan";
	}
	
	@RequestMapping(value = "/jabatan/hapus", method = RequestMethod.POST)
	private String delete(@ModelAttribute JabatanModel jabatan, Model model) {
		jabatanService.deleteJabatan(jabatan);
		return "redirect:/";
	}
}
