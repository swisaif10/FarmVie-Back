package com.vape.sec.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.List;

import javax.websocket.server.PathParam;
import javax.xml.ws.soap.Addressing;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vape.sec.model.Articles;
import com.vape.sec.model.Suivie;
import com.vape.sec.repo.ArticleRep;
import com.vape.sec.repo.SuivieRep;
import com.vape.sec.security.UserPrincipal;

@RestController
@RequestMapping("/suivie")
public class SuivieController {

	@Autowired
	SuivieRep suivieRep;
	@Autowired
	ArticleRep articleRep;

	@PostMapping("/add")
void  addSuivie(@RequestParam("ida") String ida2) {
		long ida=Long.parseLong(ida2);
		long idu = -1;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		idu = ((UserPrincipal) principal).getId();
 if(suivieRep.findByIdArticelAndUserid(ida, idu).size()==0) {
		Suivie suivie = new Suivie(ida, idu);
		suivieRep.save(suivie);
 
}
 
	}

	@GetMapping("/suivies")

	public ResponseEntity<Collection<Articles>> getSuivie() {
		long idu = -1;
		Collection<Articles> la2 = new ArrayList<>();

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		idu = ((UserPrincipal) principal).getId();
		Collection<Suivie> ls = suivieRep.findByUserid(idu);
		Collection<Articles> la = new ArrayList<Articles>();
		for (Suivie s : ls) {
			la2 = articleRep.findByidArtcile(s.getIdArticel());

			la.addAll(la2);
			la2.clear();

		}
		for (Articles l : la) {

			try {
				File f = new File(l.getImage());

				String encodeBase64 = null;
				String extense = FilenameUtils.getExtension(f.getName());

				FileInputStream fileInputStream = new FileInputStream(f);
				byte[] bytes = new byte[(int) f.length()];
				fileInputStream.read(bytes);
				encodeBase64 = Base64.getEncoder().encodeToString(bytes);
				l.setImage("data:image/" + extense + ";base64," + encodeBase64);
				fileInputStream.close();

			} catch (Exception e) {
 			}

		}

		return new ResponseEntity<Collection<Articles>>(la, HttpStatus.OK);

	}

	@DeleteMapping("/delete/{ids}")

	void deleteSuivie(@PathVariable("ids") long ida) {
		long idu;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		idu = ((UserPrincipal) principal).getId();

		Collection<Suivie> suivie = suivieRep.findByIdArticelAndUserid(ida, idu);
		for (Suivie s : suivie)
			suivieRep.delete(s);

	}

}