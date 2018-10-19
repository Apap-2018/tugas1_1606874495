package com.apap.tugas1.service;

import java.util.Optional;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;

public interface PegawaiService {
	
	Optional<PegawaiModel> getPegawaiDetailByNip(String nip);

	public double findHighestGaji(String nip);
	
	public int countGaji(String nip);

	PegawaiModel findYoungest(InstansiModel instansi);

	PegawaiModel findOldest(InstansiModel instansi);

	String buatNip(PegawaiModel pegawai);
	
	PegawaiModel addPegawai(PegawaiModel pegawai);
}
