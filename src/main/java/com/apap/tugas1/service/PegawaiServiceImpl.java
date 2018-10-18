package com.apap.tugas1.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.PegawaiDb;

@Service
public class PegawaiServiceImpl implements PegawaiService{
	
	@Autowired
	private PegawaiDb pegawaiDb;
	
	@Override
	public Optional<PegawaiModel> getPegawaiDetailByNip(String nip) {
		return pegawaiDb.findByNip(nip);
	}

}
