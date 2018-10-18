package com.apap.tugas1.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.JabatanDb;
import com.apap.tugas1.repository.PegawaiDb;
import com.apap.tugas1.repository.ProvinsiDb;

@Service
public class PegawaiServiceImpl implements PegawaiService{
	
	@Autowired
	private PegawaiDb pegawaiDb;
	
	@Autowired
	private JabatanDb jabatanDb;
	
	@Autowired
	private ProvinsiDb provinsiDb;
	
	@Override
	public Optional<PegawaiModel> getPegawaiDetailByNip(String nip) {
		return pegawaiDb.findByNip(nip);
	}
	
	@Override
	public double findHighestGaji(String nip) {
		PegawaiModel pegawai = pegawaiDb.findByNip(nip).get();
		double gajiTertinggi = 0;
		for(JabatanModel jabatan : pegawai.getJabatanList()) {
			if( gajiTertinggi < jabatan.getGajiPokok() ) {
				gajiTertinggi = jabatan.getGajiPokok();
			}
		}
		return gajiTertinggi;
	}

	@Override
	public int countGaji(String nip) {
		PegawaiModel pegawai = pegawaiDb.findByNip(nip).get();
		double persentaseTunjangan = pegawai.getInstansi().getProvinsi().getPresentaseTunjangan();
		double gajiPokok = findHighestGaji(pegawai.getNip());
		int gajiFinal = (int) (gajiPokok+(persentaseTunjangan/100*gajiPokok));
		return gajiFinal;
	}
}
