package com.apap.tugas1.service;

import java.util.List;

import com.apap.tugas1.model.JabatanModel;

public interface JabatanService {
	void addJabatan(JabatanModel jabatan);
	
	JabatanModel viewJabatan(long id);

	void ubahJabatan(long id, JabatanModel jabatan);

	void deleteJabatan(JabatanModel jabatan);

	List<JabatanModel> viewAll();
}
