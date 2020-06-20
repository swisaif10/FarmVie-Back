package com.vape.sec.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;

import org.apache.commons.io.FilenameUtils;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import javax.servlet.ServletContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.RequestEntity.BodyBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.vape.sec.model.*;
import com.vape.sec.repo.*;
 import com.vape.sec.security.UserPrincipal;
 
@RestController
@RequestMapping("/article")
public class ArticleController {

	 @Autowired
	  private ArticleRep articleRep;
	 @Autowired
	 private VoteRep voteRep;
	 @Autowired
	 private SuivieRep suivieRep;
	 
	 
	 public float getvoteArticle(long id) {
			
		 try {
				return voteRep.findMoyNoteArticle(id);

		 }catch(Exception e) {
			 return 0;
			 
		 }
 	}
	
 
public float getvoteUser(long id) {
	
	
 		
		Collection<Articles> ca=articleRep.findByidUser(id);
		float somme=0;
		int x=0;
		
		for(Articles a :ca) {
			if(getvoteArticle(a.getIdArtcile())!=0) {
				System.out.println(getvoteArticle(a.getIdArtcile())+"voteee");
			somme=somme+getvoteArticle(a.getIdArtcile());
			x++;
			}
		}
		if(x!=0)
 		somme=somme/x;
		else
		somme=0;
		return somme;
	}
	 
	
	 @GetMapping("/articles")
	  public Collection<Articles> getAllArticle() {
	    return articleRep.findAll();
	  }
	 @PostMapping("/upload")
	  
	     public void uplaod(@RequestParam("imageFile") MultipartFile file,@RequestParam("nomArticle") String nomarticle,
	    		 
	    		 @RequestParam("contact")String contact,@RequestParam("prix")String prix,
	    		 @RequestParam("description") String description,@RequestParam("lieu")String lieu ) 
	    		  throws IOException {
		 long id=-1;
		 String email=null;
		 Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 if (principal instanceof UserPrincipal) {
		  id = ((UserPrincipal)principal).getId();
		  email=((UserPrincipal)principal).getEmail();
 
		 }  
		
	         System.out.println("Original Image Byte Size - " + file.getBytes().length);
 	  byte [] bytes=file.getBytes();
	  //Path path=Paths.get(URI.create( folder+file.getOriginalFilename()) );
	  Path path = FileSystems.getDefault().getPath("/photos/"+file.getOriginalFilename());

	  Files.write(path, bytes);
	  Articles article=new Articles();

	  article.setImage("/photos/"+file.getOriginalFilename());

	  DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		article.setDate(dateFormat.format(date));
 	  article.setIdUser(id);
 	  article.setEmail(email);
	  article.setNomArticle(nomarticle);
	  article.setContact(contact);
	  article.setPrix(prix);
	  article.setDescription(description);
	  article.setLieu(lieu);

	  article.setIdUser(id);
	  articleRep.save(article);


	  

	          
	  
 	  
	     }
	 @GetMapping("/getimage")

	 @CrossOrigin
	 public ResponseEntity<List<Articles>> getArticle(){
		 List<Articles>la=articleRep.findAll();
		 long id=-1;
 		 Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		 if (principal instanceof UserPrincipal) {
		  id = ((UserPrincipal)principal).getId();
  
		 }
		 System.out.println("iddd"+id);
		 for(Articles l :la) {
			 l.setArticlevote(getvoteArticle(l.getIdArtcile()));
			  l.setUservote(getvoteUser(l.getIdUser()));
			  
 			  l.setIsfavorite(suivieRep.findByIdArticelAndUserid(l.getIdArtcile(), id).size()!=0);
 
			 try {
	  		File f=new File(l.getImage()); 
 	 
 					System.out.println (f.getName());

				 String encodeBase64=null;
				 String extense=FilenameUtils.getExtension(f.getName());
				  
					FileInputStream fileInputStream=new FileInputStream(f);
	 			byte [] bytes=new byte[(int)f.length()];
	 			fileInputStream.read(bytes);
	 			encodeBase64=Base64.getEncoder().encodeToString(bytes);
	 			l.setImage("data:image/"+extense+";base64,"+encodeBase64);
 	 			 
	 			fileInputStream.close();
			 
					
			 } catch (Exception e) {
					// TODO Auto-generated catch block
 				}
 		 }
		
		 
		 
		 
		 return new ResponseEntity<List<Articles>>(la,HttpStatus.OK);
		 }
	@GetMapping("/getimage/{id}")
	 @CrossOrigin
	 public ResponseEntity<Articles> getArticlebyId(@PathVariable("id") Long id ){
		 Articles as=null;
		  float note=0;
		 Collection<Articles>la=articleRep.findByidArtcile(id);
 		 for(Articles l :la) {
 			 l.setArticlevote(getvoteArticle(l.getIdArtcile()));
  
l.setUservote(getvoteUser(l.getIdUser()));

				 try {
		  		File f=new File(l.getImage()); 
	 	 
	 					System.out.println (f.getName());

					 String encodeBase64=null;
					 String extense=FilenameUtils.getExtension(f.getName());
					  
						FileInputStream fileInputStream=new FileInputStream(f);
		 			byte [] bytes=new byte[(int)f.length()];
		 			fileInputStream.read(bytes);
		 			encodeBase64=Base64.getEncoder().encodeToString(bytes);
		 			l.setImage("data:image/"+extense+";base64,"+encodeBase64);
		 			fileInputStream.close();
				 
						
				 } catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			   as=l;
			  
			 		 }
		 return new ResponseEntity<Articles>(as,HttpStatus.OK);

		 
		 
	 }
	 
	@GetMapping("/getcourentArticles")

 
 public ResponseEntity<Collection<Articles>>getUserArticle(){
	 long id;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		id = ((UserPrincipal) principal).getId();
	 Collection<Articles> ca=articleRep.findByidUser(id);
	 for(Articles l :ca) {
		 l.setArticlevote(getvoteArticle(l.getIdArtcile()));
		  l.setUservote(getvoteUser(l.getIdUser()));
	 try {
	  		File f=new File(l.getImage()); 
 	 
 					System.out.println (f.getName());

				 String encodeBase64=null;
				 String extense=FilenameUtils.getExtension(f.getName());
				  
					FileInputStream fileInputStream=new FileInputStream(f);
	 			byte [] bytes=new byte[(int)f.length()];
	 			fileInputStream.read(bytes);
	 			encodeBase64=Base64.getEncoder().encodeToString(bytes);
	 			l.setImage("data:image/"+extense+";base64,"+encodeBase64);
	 			System.out.println(l.getIdArtcile()+"  id  "+l.getIdUser());
	 			 
	 			fileInputStream.close();
			 
					
			 } catch (Exception e) {
					// TODO Auto-generated catch block
 				}
	 }
		return new ResponseEntity<Collection<Articles>>(ca, HttpStatus.OK);

	 
 }
	@DeleteMapping("/delete/{ida}")

	 public void deleteArticle(@PathVariable("ida") long ida) {
		 Collection<Articles>la=articleRep.findByidArtcile(ida);
		 Collection<Suivie> s=suivieRep.findByIdArticel(ida);
		 for(Suivie sui:s) {
			 suivieRep.delete(sui);
		 }
		 for(Articles a:la) {
			 articleRep.delete(a);
		 }
		 
		 
	 }
	
	 
}
