package com.apap.tugas1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.repository.JabatanDb;

@Service
public class JabatanServiceImpl implements JabatanService{
	
	@Autowired
	private JabatanDb jabatanDb;
	
	@Override
	public void addJabatan(JabatanModel jabatan) {
		jabatanDb.save(jabatan);
	}
	
	@Override
	public JabatanModel viewJabatan(long id) {
		return jabatanDb.getOne(id);
	}
	
	@Override
	public void ubahJabatan(long id, JabatanModel jabatan) {
		JabatanModel jabatanUpdate = jabatanDb.getOne(id);
		jabatanUpdate.setId(jabatan.getId());
		jabatanUpdate.setNama(jabatan.getNama());
		jabatanUpdate.setDeskripsi(jabatan.getDeskripsi());
		jabatanUpdate.setGajiPokok(jabatan.getGajiPokok());
		jabatanDb.save(jabatanUpdate);
	}
}
