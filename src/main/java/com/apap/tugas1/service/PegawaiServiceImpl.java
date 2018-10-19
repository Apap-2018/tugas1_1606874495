package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
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
	
	@Override
	public PegawaiModel findYoungest(InstansiModel instansi){
		List<PegawaiModel> pegawaiInstansi = pegawaiDb.findByInstansiOrderByTanggalLahirAsc(instansi);
		return pegawaiInstansi.get(0);
	}
	
	@Override
	public PegawaiModel findOldest(InstansiModel instansi){
		List<PegawaiModel> pegawaiInstansi = pegawaiDb.findByInstansiOrderByTanggalLahirAsc(instansi);
		return pegawaiInstansi.get(pegawaiInstansi.size()-1);
	}
	
}
