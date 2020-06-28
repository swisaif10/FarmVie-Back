package com.vape.sec.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vape.sec.model.Investe;
import com.vape.sec.model.Projet;
import com.vape.sec.model.User;
import com.vape.sec.repo.InvesteRep;
import com.vape.sec.repo.ProjetRepo;
import com.vape.sec.repo.UserRepo;
import com.vape.sec.security.UserPrincipal;

import payloads.Userinvest;

@RestController
@RequestMapping("/investe")
public class InvesteController {
	
	@Autowired
	UserRepo userRepo;
	@Autowired
	ProjetRepo projetRep ;
	@Autowired
	InvesteRep investRep;
	 @PostMapping("/add")

		public ResponseEntity<String>investe( @RequestParam("quantity") String quantity, @RequestParam("id") String idP){
			String mail;
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			mail = ((UserPrincipal) principal).getEmail();
			User user = userRepo.findByEmail(mail).orElseThrow(
					() -> new UsernameNotFoundException("User not found with username or email : " + mail));	
			float argentuser=user.getArgent();
			Float quantityy=Float.parseFloat(quantity);
			long idp2=Long.parseLong(idP);
			Investe investe=investRep.findByIdPAndIdU(idp2, user.getId());
			Projet p=projetRep.findByIdProjet(idp2);
			float montantmin=Float.parseFloat(p.getMontantMin());
			if(investe!=null) {
				p.setMontantRecu(p.getMontantRecu()+quantityy);
				p.setPourcentage((p.getMontantRecu()*100)/p.getMontantTotal());
				projetRep.save(p);
				user.setArgent(user.getArgent()-quantityy);
				userRepo.save(user);
				investe.setMontant(investe.getMontant()+quantityy);
				investRep.save(investe);
				return new ResponseEntity<String>("vous avez investe", HttpStatus.OK);

			}
			else if(argentuser<quantityy) {
				return new ResponseEntity<String>("L'argent ne suffit pas", HttpStatus.BAD_REQUEST);

				
			}
			
			else if ((quantityy<montantmin)&&(user.getEp().equals("entreprise"))) {
				return new ResponseEntity<String>("L'argent inférieur a montant minimum", HttpStatus.BAD_REQUEST);

			}
			else if (quantityy>(p.getMontantTotal()-p.getMontantRecu())) {
				return new ResponseEntity<String>("montant supérieur au montant manque", HttpStatus.BAD_REQUEST);

			}
			else {
				p.setMontantRecu(p.getMontantRecu()+quantityy);
				p.setPourcentage((p.getMontantRecu()*100)/p.getMontantTotal());
				projetRep.save(p);
				user.setArgent(user.getArgent()-quantityy);
				userRepo.save(user);
				Investe i=new Investe();
				i.setIdP(p.getIdProjet());
				i.setIdU(user.getId());
				i.setMontant(quantityy);
				
				investRep.save(i);
				return new ResponseEntity<String>("vous avez investe", HttpStatus.OK);

			}}
	 
		@GetMapping("/getinveset/{ida}")

		 
		 public Collection<Object >  getUserArticle(@PathVariable("ida") long ida){
			 long id;
				Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				id = ((UserPrincipal) principal).getId();
				 List<Object > l2=investRep.getprojet2(ida);
				  
				 return l2;
 		 
 			
			
			
		}

}
