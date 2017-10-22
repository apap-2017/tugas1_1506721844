package com.example.tugas1apap.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
public class KeluargaController {
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
	
	@RequestMapping("/keluarga")
	public String index (@RequestParam(value = "nkk") String nkk, Model model) {
		KeluargaModel km = keluargaDAO.selectKeluarga(nkk);
		
		if(km != null) {
			model.addAttribute("km", km);
			
			KelurahanModel kelm = kelurahanDAO.selectKelurahan(km.getIdKelurahan());
			model.addAttribute("kelm", kelm);
			
			KecamatanModel kecm = kecamatanDAO.selectKecamatan(kelm.getIdKecamatan());
			model.addAttribute("kecm", kecm);
			
			KotaModel kotm = kotaDAO.selectKota(kecm.getIdKota());
			model.addAttribute("kotm", kotm);
			
			List<PendudukModel> selectAnggotaKeluarga = keluargaDAO.selectAnggotaKeluarga(km.getId());
			model.addAttribute("listkeluarga", selectAnggotaKeluarga);
			
			return "viewkeluarga";
		} else {
			model.addAttribute("nkk", nkk);
			return "not-found";
		}
	}
	
	@RequestMapping(value = "/keluarga/tambah", method = RequestMethod.GET)
	public String addKeluarga (PendudukModel penduduk, Model model) {
		model.addAttribute("keluarga", new KeluargaModel());
		
		List<KelurahanModel> kelurahans = kelurahanDAO.selectAllKelurahan();
		model.addAttribute("kelurahans", kelurahans);
		
		List<KecamatanModel> kecamatans = kecamatanDAO.selectAllKecamatan();
		model.addAttribute("kecamatans", kecamatans);
		
		List<KotaModel> kotas = kotaDAO.selectAllKota();
		model.addAttribute("kotas", kotas);
		
		return "addkeluarga";
	}
	
	@RequestMapping(value = "/keluarga/tambah", method = RequestMethod.POST)
	public String addKeluargaPost (KeluargaModel keluarga, Model model) {
		model.addAttribute("keluarga", keluarga);
		
		String nkk = "";
		
		KelurahanModel kelm = kelurahanDAO.selectKelurahan(keluarga.getIdKelurahan());
		
		nkk += kelm.getKodeKelurahan().substring(0, 6);
		
		DateFormat dateFormat = new SimpleDateFormat("ddMMyy");
		Date date = new Date();
		
		String currentDate = dateFormat.format(date);
		
		nkk += currentDate;
		
		int offset = Integer.parseInt(keluargaDAO.findSimilarNKK(nkk)) + 1;
		
		if(offset < 10)
			nkk += "000" + offset;
		else if(offset < 100)
			nkk += "00" + offset;
		else if(offset < 1000)
			nkk += "0" + offset;
		else
			nkk += offset;
		
		keluarga.setNomorKk(nkk);
		keluarga.setIsTidakBerlaku("0");
		
		String lastID = keluargaDAO.selectLastID();
		keluarga.setId("" + (Integer.parseInt(lastID) + 1));
		
		keluargaDAO.addKeluarga(keluarga);
		
		model.addAttribute("nkk", nkk);	
		return "addkeluarga";
	}
	
	@RequestMapping(value = "/keluarga/ubah/{NKK}", method = RequestMethod.GET)
    public String updateKeluarga (Model model, @PathVariable(value = "NKK") String nkk)
    {
        KeluargaModel keluarga = keluargaDAO.selectKeluarga(nkk);

        if (keluarga != null) {
        	model.addAttribute("keluarga", keluarga);
        	
        	KelurahanModel currentKelurahan = kelurahanDAO.selectKelurahan(keluarga.getIdKelurahan());
        	KecamatanModel currentKecamatan = kecamatanDAO.selectKecamatan(currentKelurahan.getIdKecamatan());
        	KotaModel currentKota = kotaDAO.selectKota(currentKecamatan.getIdKota());
        	
        	List<KelurahanModel> kelurahans = kelurahanDAO.selectAllKelurahan();
    		model.addAttribute("kelurahans", kelurahans);
    		model.addAttribute("currentKelurahan", currentKelurahan.getId());
    		
    		List<KecamatanModel> kecamatans = kecamatanDAO.selectAllKecamatan();
    		model.addAttribute("kecamatans", kecamatans);
    		model.addAttribute("currentKecamatan", currentKecamatan.getId());
    		
    		List<KotaModel> kotas = kotaDAO.selectAllKota();
    		model.addAttribute("kotas", kotas);
    		model.addAttribute("currentKota", currentKota.getId());
        	
            return "updatekeluarga";
        } else {
        	model.addAttribute("nkk", nkk);
        	return "not-found";
        }
    }
	
	@RequestMapping(value = "/keluarga/ubah/{NKK}", method = RequestMethod.POST)
    public String updateKeluargaPost (KeluargaModel keluarga, Model model, @PathVariable(value = "NKK") String nkk)
    {
		model.addAttribute("keluarga", keluarga);
        String nkkLama = nkk;
        KeluargaModel keluargaOld = keluargaDAO.selectKeluarga(nkkLama);
        
        keluarga.setNomorKk(nkkLama);
        keluarga.setIsTidakBerlaku(keluargaOld.getIsTidakBerlaku());
        
        if(!keluargaOld.getIdKelurahan().equalsIgnoreCase(keluarga.getIdKelurahan())) {
        	String nkkBaru = "";
        	
        	KelurahanModel kelm = kelurahanDAO.selectKelurahan(keluarga.getIdKelurahan());
    		
        	nkkBaru += kelm.getKodeKelurahan().substring(0, 6);
        	
        	DateFormat dateFormat = new SimpleDateFormat("ddMMyy");
    		Date date = new Date();
    		
    		String currentDate = dateFormat.format(date);
    		
    		nkk += currentDate;
    		
    		int offset = Integer.parseInt(keluargaDAO.findSimilarNKK(nkkBaru)) + 1;
    		
    		if(offset < 10)
    			nkkBaru += "000" + offset;
    		else if(offset < 100)
    			nkkBaru += "00" + offset;
    		else if(offset < 1000)
    			nkkBaru += "0" + offset;
    		else
    			nkkBaru += offset;
    		
    		keluarga.setNomorKk(nkkBaru);
        }
        
        keluargaDAO.updateKeluarga(keluarga, nkkLama);
        
        model.addAttribute("nkkLama", nkkLama);
        return "updatekeluarga";
    }
}