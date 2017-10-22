package com.example.tugas1apap.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.tugas1apap.model.KecamatanModel;
import com.example.tugas1apap.model.KeluargaModel;
import com.example.tugas1apap.model.KelurahanModel;
import com.example.tugas1apap.model.KotaModel;
import com.example.tugas1apap.model.PendudukModel;
import com.example.tugas1apap.service.KecamatanService;
import com.example.tugas1apap.service.KeluargaService;
import com.example.tugas1apap.service.KelurahanService;
import com.example.tugas1apap.service.KotaService;
import com.example.tugas1apap.service.PendudukService;

@Controller
public class PendudukController {
	@Autowired
    PendudukService pendudukDAO;
	
	@Autowired
	KeluargaService keluargaDAO;
	
	@Autowired
	KotaService kotaDAO;
	
	@Autowired
	KecamatanService kecamatanDAO;
	
	@Autowired
	KelurahanService kelurahanDAO;
	
	@RequestMapping("/penduduk")
	public String viewPenduduk (@RequestParam(value = "nik") String nik, Model model) {
		PendudukModel pm = pendudukDAO.selectPenduduk(nik);
		
		if(pm != null) {		
			String tanggalLahirLengkap = pm.getTanggalLahir();
			String tanggalLahir = tanggalLahirLengkap.substring(8, 10);
			String bulanLahir = tanggalLahirLengkap.substring(5, 7);
			String tahunLahir = tanggalLahirLengkap.substring(0, 4);
			
			model.addAttribute("pm", pm);
			model.addAttribute("tanggalLahir", tanggalLahir + "-" + bulanLahir + "-" + tahunLahir);
			int jenisKelamin = pm.getJenisKelamin();
			if(jenisKelamin == 0)
				model.addAttribute("jeniskelamin", "Pria");
			else
				model.addAttribute("jeniskelamin", "Wanita");
			
			if(pm.getIsWafat().equals("1"))
				model.addAttribute("statuskematian", "Wafat");
			else
				model.addAttribute("statuskematian", "Hidup");
			
			if(pm.getIsWni().equals("1"))
				model.addAttribute("isWni", "WNI");
			else
				model.addAttribute("isWni", "WNA");
			
			KeluargaModel km = keluargaDAO.selectKeluargaWithID(pm.getIdKeluarga());
			model.addAttribute("km", km);
			
			KelurahanModel kem = kelurahanDAO.selectKelurahan(km.getIdKelurahan());
			model.addAttribute("kem", kem);
			
			KecamatanModel kec = kecamatanDAO.selectKecamatan(kem.getIdKecamatan());
			model.addAttribute("kec", kec);
			
			KotaModel kot = kotaDAO.selectKota(kec.getIdKota());
			model.addAttribute("kot", kot);
			
			return "viewpenduduk";
		} else {
			model.addAttribute("nik", nik);
			return "not-found";
		}
	}
	
	@RequestMapping(value = "/penduduk/tambah", method = RequestMethod.GET)
	public String addPenduduk (PendudukModel penduduk, Model model) {
		model.addAttribute("penduduk", new PendudukModel());
		return "addpenduduk";
	}
	
	@RequestMapping(value = "/penduduk/tambah", method = RequestMethod.POST)
	public String addPendudukPost (PendudukModel penduduk, Model model) {
		model.addAttribute("penduduk", penduduk);
		
		String nik = "";
		String tanggalLahirLengkap = penduduk.getTanggalLahir();
		String tanggalLahir = tanggalLahirLengkap.substring(8, 10);
		String bulanLahir = tanggalLahirLengkap.substring(5, 7);
		String tahunLahir = tanggalLahirLengkap.substring(2, 4);
		
		KeluargaModel kem = keluargaDAO.selectKeluargaWithID(penduduk.getIdKeluarga());
		KelurahanModel kelm = kelurahanDAO.selectKelurahan(kem.getIdKelurahan());
		nik += kelm.getKodeKelurahan().substring(0, 6);
		
		if(penduduk.getJenisKelamin() == 0)
			nik += tanggalLahir;
		else
			nik += (Integer.parseInt(tanggalLahir) + 40);
		
		nik += bulanLahir;
		nik += tahunLahir;
		
		String lastSimilarNIK = pendudukDAO.findSimilarNIK(nik);
		int lastDigitSimilarNIK = Integer.parseInt(lastSimilarNIK.substring(lastSimilarNIK.length() - 1));
		
		int offset = lastDigitSimilarNIK + 1;
		
		if(offset < 10)
			nik += "000" + offset;
		else if(offset < 100)
			nik += "00" + offset;
		else if(offset < 1000)
			nik += "0" + offset;
		else
			nik += offset;
		
		penduduk.setNik(nik);
		
		String lastID = pendudukDAO.selectLastID();
		penduduk.setId(Integer.parseInt(lastID) + 1);
		
		pendudukDAO.addPenduduk(penduduk);
		
		model.addAttribute("nik", nik);	
		return "addpenduduk";
	}
	
	@RequestMapping(value = "/penduduk/ubah/{NIK}", method = RequestMethod.GET)
    public String updatePenduduk (Model model, @PathVariable(value = "NIK") String nik)
    {
        PendudukModel penduduk = pendudukDAO.selectPenduduk(nik);

        if (penduduk != null) {
        	model.addAttribute("penduduk", penduduk);
            return "updatependuduk";
        } else {
        	model.addAttribute("nik", nik);
        	return "not-found";
        }
    }
	
	@RequestMapping(value = "/penduduk/ubah/{NIK}", method = RequestMethod.POST)
    public String updatePendudukPost (PendudukModel penduduk, Model model, @PathVariable(value = "NIK") String nik)
    {
		model.addAttribute("penduduk", penduduk);
        String nikLama = nik;
        PendudukModel pendudukOld = pendudukDAO.selectPenduduk(nikLama);
        
        penduduk.setNik(nikLama);
        
        if(!pendudukOld.getTanggalLahir().equalsIgnoreCase(penduduk.getTanggalLahir()) ||
        		pendudukOld.getJenisKelamin() != penduduk.getJenisKelamin() ||
        		!pendudukOld.getIdKeluarga().equalsIgnoreCase(penduduk.getIdKeluarga())) {
        	
        	String nikBaru = "";
    		String tanggalLahirLengkap = penduduk.getTanggalLahir();
    		String tanggalLahir = tanggalLahirLengkap.substring(8, 10);
    		String bulanLahir = tanggalLahirLengkap.substring(5, 7);
    		String tahunLahir = tanggalLahirLengkap.substring(2, 4);
    		
    		KeluargaModel kem = keluargaDAO.selectKeluargaWithID(penduduk.getIdKeluarga());
    		KelurahanModel kelm = kelurahanDAO.selectKelurahan(kem.getIdKelurahan());
    		nikBaru += kelm.getKodeKelurahan().substring(0, 6);
    		
    		if(penduduk.getJenisKelamin() == 0)
    			nikBaru += tanggalLahir;
    		else
    			nikBaru += (Integer.parseInt(tanggalLahir) + 40);
    		
    		nikBaru += bulanLahir;
    		nikBaru += tahunLahir;
    		
    		int offset = Integer.parseInt(pendudukDAO.findSimilarNIK(nikBaru)) + 1;
    		
    		if(offset < 10)
    			nikBaru += "000" + offset;
    		else if(offset < 100)
    			nikBaru += "00" + offset;
    		else if(offset < 1000)
    			nikBaru += "0" + offset;
    		else
    			nikBaru += offset;
    		
    		penduduk.setNik(nikBaru);  	
        }
        
        pendudukDAO.updatePenduduk(penduduk, nikLama); 
        
        model.addAttribute("nikLama", nikLama);
        return "updatependuduk";
    }
	
	@RequestMapping(value = "/penduduk/mati", method = RequestMethod.POST)
	public String matikanPenduduk (String nik, RedirectAttributes redirectAttributes) {
		pendudukDAO.matikanPenduduk(nik);
		 
		int countIsNotWafat = keluargaDAO.countIsNotWafat(pendudukDAO.selectPenduduk(nik).getIdKeluarga());
		if(countIsNotWafat == 0)
			keluargaDAO.setTidakBerlaku(pendudukDAO.selectPenduduk(nik).getIdKeluarga());
		
		redirectAttributes.addAttribute("nik", nik);
		redirectAttributes.addFlashAttribute("isWafat", true);
		redirectAttributes.addFlashAttribute("nik", nik);
		return "redirect:/penduduk";
	}
	
	@RequestMapping("/penduduk/cari")
	public String cariPenduduk (@RequestParam(value = "kt") Optional<String> kt, 
								@RequestParam(value = "kc") Optional<String> kc,
								@RequestParam(value = "kl") Optional<String> kl, 
								Model model) {
		List<KotaModel> kotas = kotaDAO.selectAllKota();
		model.addAttribute("kotas", kotas);
		
		if(kt.isPresent()) {
			String namaKota = kotaDAO.selectKota(kt.get()).getNamaKota();
			model.addAttribute("namaKota", namaKota);
			model.addAttribute("currentKota", kt.get());
			List<KecamatanModel> kecamatans = kecamatanDAO.selectKecamatanByKota(kt.get());
			model.addAttribute("kecamatans", kecamatans);
		}
		if(kc.isPresent()) {
			String namaKecamatan = kecamatanDAO.selectKecamatan(kc.get()).getNamaKecamatan();
			model.addAttribute("namaKecamatan", namaKecamatan);
			model.addAttribute("currentKecamatan", kc.get());
			List<KelurahanModel> kelurahans = kelurahanDAO.selectKelurahanByKecamatan(kc.get());
			model.addAttribute("kelurahans", kelurahans);
		}
		if(kl.isPresent()) {
			String namaKelurahan = kelurahanDAO.selectKelurahan(kl.get()).getNamaKelurahan();
			model.addAttribute("namaKelurahan", namaKelurahan);
			model.addAttribute("currentKelurahan", kl.get());
			
			List<PendudukModel> penduduks = pendudukDAO.selectPendudukByKelurahan(kl.get());
			model.addAttribute("penduduks", penduduks);
			
			PendudukModel youngestCitizen = pendudukDAO.selectPendudukTermuda(kl.get());
			PendudukModel oldestCitizen = pendudukDAO.selectPendudukTertua(kl.get());
			
			String tanggalLahirLengkap = youngestCitizen.getTanggalLahir();
			String tanggalLahir = tanggalLahirLengkap.substring(8, 10);
			String bulanLahir = tanggalLahirLengkap.substring(5, 7);
			String tahunLahir = tanggalLahirLengkap.substring(0, 4);
			String tanggalLahirBaru = tanggalLahir + "-" + bulanLahir + "-" + tahunLahir;
			
			model.addAttribute("tanggalLahirYoungest", tanggalLahirBaru);
			
			tanggalLahirLengkap = oldestCitizen.getTanggalLahir();
			tanggalLahir = tanggalLahirLengkap.substring(8, 10);
			bulanLahir = tanggalLahirLengkap.substring(5, 7);
			tahunLahir = tanggalLahirLengkap.substring(0, 4);
			tanggalLahirBaru = tanggalLahir + "-" + bulanLahir + "-" + tahunLahir;
			
			model.addAttribute("tanggalLahirOldest", tanggalLahirBaru);
			
			model.addAttribute("youngestCitizen", youngestCitizen);
			model.addAttribute("oldestCitizen", oldestCitizen);
		}
		
		return "findpenduduk";
	}
}