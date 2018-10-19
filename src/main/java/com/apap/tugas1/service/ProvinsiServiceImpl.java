package com.apap.tugas1.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.repository.ProvinsiDb;

@Transactional
@Service
public class ProvinsiServiceImpl implements ProvinsiService{

	@Autowired
	private ProvinsiDb provinsiDb;

	@Override
	public List<ProvinsiModel> provinsiList() {
		return provinsiDb.findAll();
	}
}
