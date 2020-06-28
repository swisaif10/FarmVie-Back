package com.vape.sec.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vape.sec.model.Articles;
import com.vape.sec.model.Projet;
import com.vape.sec.model.Suivie;
import com.vape.sec.repo.InvesteRep;
import com.vape.sec.repo.ProjetRepo;
import com.vape.sec.security.UserPrincipal;

import payloads.ProjetRequest;
  @RestController
  @RequestMapping("/projet")
public class ProjetController {
	  @Autowired
	  ProjetRepo prjetrepo;
	  @Autowired
	  InvesteRep investRepo;
	  


	    @PostMapping("/add")
	    public void addprojet(@RequestParam("gevernorat")String gevernorat,@RequestParam("delegation")String delegation,@RequestParam("superficieE")String superficieE,@RequestParam("superficieT")String superficieT,
	                          @RequestParam("irrigation")String irrigation,@RequestParam("siege")String siege,@RequestParam("nomProjet")String nomProjet,@RequestParam("biologique")String biologique,@RequestParam("type")String type,
	                          @RequestParam("description")String description,@RequestParam("montantMin")String montantMin,@RequestParam("typeFinance")String typeFinance,@RequestParam("photoProjet")MultipartFile photoProjet,@RequestParam("montanttotal")String montantTotal) throws IOException {

	        long id=-1;
	        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	        if (principal instanceof UserPrincipal) {
	            id = ((UserPrincipal)principal).getId();

	        }
	        Projet p=new Projet();
	        //sol copier
	        byte [] bytes=photoProjet.getBytes();
	        Path path = FileSystems.getDefault().getPath("sol/"+photoProjet.getOriginalFilename());

	        Files.write(path, bytes);


	         p.setPhotoProjet("photos_profil/"+photoProjet.getOriginalFilename());
	        p.setIdUser(id);
	        p.setDescription(description);
	        p.setGevernorat(gevernorat);
	        p.setSuperficieE(superficieE);
	        p.setSuperficieT(superficieT);
	        float montantTotal2=Float.parseFloat(montantTotal);
	        p.setMontantTotal(montantTotal2);
	        p.setMontantRecu(0);
	        p.setIrrigation(irrigation);
	        p.setSiege(siege);
	        p.setNomProjet(nomProjet);
	        p.setType(type);
	        p.setMontantMin(montantMin);
	        p.setDelegation(delegation);
	        p.setBiologique(biologique);
	        p.setIp("");
	        p.setPourcentage(0);
	        p.setEtat("en attend");
	        p.setTypeFinance(typeFinance);

	        p.setDatadeployment(LocalDate.now());
	        prjetrepo.save(p);



	    }
	    
	@GetMapping("/getimage")

	 @CrossOrigin
	 public ResponseEntity<List<Projet>> getArticle(){
		 	
 		 List<Projet>la=prjetrepo.findByEtat("accepter");
		 
 		 for(Projet l :la) {
			  
			  
 
			 try {
	  		File f=new File(l.getPhotoProjet()); 
	 
					System.out.println (f.getName());

				 String encodeBase64=null;
				 String extense=FilenameUtils.getExtension(f.getName());
				 Period period = Period.between( l.getDatadeployment(),LocalDate.now());
				  System.out.println("deff="+period.getDays());
				  if(period.getMonths()==2) {
					  l.setEtat("expirer");
					  
					  prjetrepo.save(l);
  
				  }
					FileInputStream fileInputStream=new FileInputStream(f);
	 			byte [] bytes=new byte[(int)f.length()];
	 			fileInputStream.read(bytes);
	 			encodeBase64=Base64.getEncoder().encodeToString(bytes);
	 			l.setPhotoProjet("data:image/"+extense+";base64,"+encodeBase64);
	 			fileInputStream.close();
			 
					
			 } catch (Exception e) {
					// TODO Auto-generated catch block
				}
		 }
 		 
		 return new ResponseEntity<List<Projet>>(la,HttpStatus.OK);

	}
	@GetMapping("/getcourentArticles")

	 
	 public ResponseEntity<Collection<Projet>>getUserArticle(){
		 long id;
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			id = ((UserPrincipal) principal).getId();
		 Collection<Projet> ca=prjetrepo.findByidUser(id);
		 for(Projet l :ca) {
 			 l.setIp("publier");

		 try {
		  		File f=new File(l.getPhotoProjet()); 
	 	 
				System.out.println ("taille 333=="+l.getIp());

					 String encodeBase64=null;
					 String extense=FilenameUtils.getExtension(f.getName());
					  
						FileInputStream fileInputStream=new FileInputStream(f);
		 			byte [] bytes=new byte[(int)f.length()];
		 			fileInputStream.read(bytes);
		 			encodeBase64=Base64.getEncoder().encodeToString(bytes);
		 			l.setPhotoProjet("data:image/"+extense+";base64,"+encodeBase64);
 		 			fileInputStream.close();
				 
						
				 } catch (Exception e) {
						// TODO Auto-generated catch block
	 				}
		 }
		 Collection<Projet> l2=investRepo.getprojet(id);
	

		 for(Projet p2 :l2) {
				System.out.println ("taille =="+p2.getIp());
				
			p2.setIp("investe");
			 
			 
			 try {
 			  		File f=new File(p2.getPhotoProjet()); 
		 	 
 
						 String encodeBase64=null;
						 String extense=FilenameUtils.getExtension(f.getName());
						  
							FileInputStream fileInputStream=new FileInputStream(f);
			 			byte [] bytes=new byte[(int)f.length()];
			 			fileInputStream.read(bytes);
			 			encodeBase64=Base64.getEncoder().encodeToString(bytes);
			 			p2.setPhotoProjet("data:image/"+extense+";base64,"+encodeBase64);
 			 			
			 			fileInputStream.close();
					 
							
					 } catch (Exception e) {
							// TODO Auto-generated catch block
		 				}
			 }
ca.addAll(l2);
 
			return new ResponseEntity<Collection<Projet>>(ca, HttpStatus.OK);

		 
	 }
	@GetMapping("/getArticles/{category}")

	 
	 public ResponseEntity<List<Projet>>getprojet(@PathVariable("category") String idP){
 		List<Projet> lp;
 		System.out.println("aaaaaaaaaaa"+idP);
    if(idP.equals("all") ) {
    	 lp=prjetrepo.findByEtat("accepter");
			System.out.println("ccccccccc"+lp.size());}
    else {
		
		  lp=prjetrepo.findByType (idP);

    }
		System.out.println("bbbbbb"+lp.size());


		for(Projet l :lp) {
			 try {
			  		File f=new File(l.getPhotoProjet()); 
			 
							System.out.println (f.getName());

						 String encodeBase64=null;
						 String extense=FilenameUtils.getExtension(f.getName());
						 Period period = Period.between( l.getDatadeployment(),LocalDate.now());
						  System.out.println("deff="+period.getDays());
						  if(period.getMonths()==2) {
							  l.setEtat("expirer");
							  
							  prjetrepo.save(l);
		  
						  }
							FileInputStream fileInputStream=new FileInputStream(f);
			 			byte [] bytes=new byte[(int)f.length()];
			 			fileInputStream.read(bytes);
			 			encodeBase64=Base64.getEncoder().encodeToString(bytes);
			 			l.setPhotoProjet("data:image/"+extense+";base64,"+encodeBase64);
			 			fileInputStream.close();
					 
							
					 } catch (Exception e) {
							// TODO Auto-generated catch block
						}
				 }
		 
			return new ResponseEntity<List<Projet>>(lp, HttpStatus.OK);

		 
	 }
	@GetMapping("/getArticles2/{id}")

	 
	 public ResponseEntity<Projet>getprojet2(@PathVariable("id") long idP){
	Projet l;
		 
l=prjetrepo.findByIdProjet(idP);

 			 try {
			  		File f=new File(l.getPhotoProjet()); 
			 
							System.out.println (f.getName());

						 String encodeBase64=null;
						 String extense=FilenameUtils.getExtension(f.getName());
						 Period period = Period.between( l.getDatadeployment(),LocalDate.now());
						  System.out.println("deff="+period.getDays());
						  if(period.getMonths()==2) {
							  l.setEtat("expirer");
							  
							  prjetrepo.save(l);
		  
						  }
							FileInputStream fileInputStream=new FileInputStream(f);
			 			byte [] bytes=new byte[(int)f.length()];
			 			fileInputStream.read(bytes);
			 			encodeBase64=Base64.getEncoder().encodeToString(bytes);
			 			l.setPhotoProjet("data:image/"+extense+";base64,"+encodeBase64);
			 			fileInputStream.close();
			 			File f2=new File(l.getSol()); 
						 
						System.out.println (f2.getName());

					   encodeBase64=null;
					   extense=FilenameUtils.getExtension(f2.getName());
					  
						  fileInputStream=new FileInputStream(f2);
		 			  bytes=new byte[(int)f2.length()];
		 			fileInputStream.read(bytes);
		 			encodeBase64=Base64.getEncoder().encodeToString(bytes);
		 			l.setSol("data:image/"+extense+";base64,"+encodeBase64);
		 			fileInputStream.close();
					 
							
					 } catch (Exception e) {
							// TODO Auto-generated catch block
						}
				 
		 
			return new ResponseEntity<Projet>(l, HttpStatus.OK);

		 
	 }
	@DeleteMapping("/delete/{ida}")

	 public void deleteArticle(@PathVariable("ida") long ida) {
		Projet l=prjetrepo.findByIdProjet(ida);
		 
		 
			 prjetrepo.delete(l);
		 
		 
		 
	 }
	@GetMapping("/getall")

	 @CrossOrigin
	 public ResponseEntity<List<Projet>> getArticles(){
		 	
		 List<Projet>la=prjetrepo.findAll();
		 
		 for(Projet l :la) {
			  
			  
System.out.println(l.getDescription());
			 try {
	  		File f=new File(l.getPhotoProjet()); 
	 
					System.out.println (f.getName());

				 String encodeBase64=null;
				 String extense=FilenameUtils.getExtension(f.getName());
				 Period period = Period.between( l.getDatadeployment(),LocalDate.now());
				  System.out.println("deff="+period.getDays());
				  if(period.getMonths()==2) {
					  l.setEtat("expirer");
					  
					  prjetrepo.save(l);
 
				  }
					FileInputStream fileInputStream=new FileInputStream(f);
	 			byte [] bytes=new byte[(int)f.length()];
	 			fileInputStream.read(bytes);
	 			encodeBase64=Base64.getEncoder().encodeToString(bytes);
	 			l.setPhotoProjet("data:image/"+extense+";base64,"+encodeBase64);
	 			fileInputStream.close();
			 
					
			 } catch (Exception e) {
					// TODO Auto-generated catch block
				}
		 }
		 
		 return new ResponseEntity<List<Projet>>(la,HttpStatus.OK);

	}
	@DeleteMapping("/accepter/{ida}")

	 public void accepter(@PathVariable("ida") long ida) {
		Projet l=prjetrepo.findByIdProjet(ida);
		 l.setEtat("accepter");
		 prjetrepo.save(l);
		 
	 }
	@PostMapping("/update/{ida}")
	public void update(@PathVariable("ida") long ida,@RequestParam("gevernorat")String gevernorat,@RequestParam("delegation")String delegation,@RequestParam("superficieE")String superficieE,@RequestParam("superficieT")String superficieT,
			@RequestParam("irrigation")String irrigation,@RequestParam("siege")String siege,@RequestParam("nomProjet")String nomProjet,@RequestParam("biologique")String biologique,@RequestParam("type")String type,
			@RequestParam("description")String description,@RequestParam("montantMin")String montantMin,@RequestParam("typeFinance")String typeFinance,@RequestParam("montanttotal")String montantTotal) throws IOException {

		Projet p=prjetrepo.findByIdProjet(ida);
	
	
		 p.setDescription(description);
		  p.setGevernorat(gevernorat);
		  p.setSuperficieE(superficieE);
		  p.setSuperficieT(superficieT);
		  float montantTotal2=Float.parseFloat(montantTotal);
p.setMontantTotal(montantTotal2);
p.setMontantRecu(0);
		  p.setIrrigation(irrigation);
		  p.setSiege(siege);
		  p.setNomProjet(nomProjet);
		  p.setType(type);
		  p.setMontantMin(montantMin);
		  p.setDelegation(delegation);
		  p.setBiologique(biologique);
		  p.setIp("");
		  p.setPourcentage(0);
p.setEtat("en attend");
		  p.setTypeFinance(typeFinance);
		  prjetrepo.save(p);
	}
	
  
  
  }
