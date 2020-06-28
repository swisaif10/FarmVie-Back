package com.vape.sec.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vape.sec.Dto.UserDto;
import com.vape.sec.exception.AppException;
import com.vape.sec.model.Articles;
import com.vape.sec.model.Projet;
import com.vape.sec.model.Role;
import com.vape.sec.model.RoleName;
import com.vape.sec.model.Suivie;
import com.vape.sec.model.User;
import com.vape.sec.repo.ArticleRep;
import com.vape.sec.repo.ProjetRepo;
import com.vape.sec.repo.RoleRepo;
import com.vape.sec.repo.SuivieRep;
import com.vape.sec.repo.UserRepo;
import com.vape.sec.security.UserPrincipal;

 
@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserRepo userRepo;
	@Autowired
	RoleRepo roleRepo;
	@Autowired
	ArticleRep articleRepo;
	@Autowired
	SuivieRep suivieRep;
	@Autowired
	ProjetRepo projetRep ;
	 @Autowired
	    private PasswordEncoder passwordEncoder;
	 
	 
	@GetMapping("/getcourent")

	public ResponseEntity<User>getUser(){
		String mail;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		mail = ((UserPrincipal) principal).getEmail();
		User user = userRepo.findByEmail(mail).orElseThrow(
				() -> new UsernameNotFoundException("User not found with username or email : " + mail));		
		 
 
		try {
		  		File f=new File(user.getPhoto()); 
	 	 
				 System.out.println("user   "+user.getPhoto());

					 String encodeBase64=null;
					 String extense=FilenameUtils.getExtension(f.getName());
					 System.out.println("user   "+user.getPhoto());

						FileInputStream fileInputStream=new FileInputStream(f);
		 			byte [] bytes=new byte[(int)f.length()];
		 			fileInputStream.read(bytes);
		 			encodeBase64=Base64.getEncoder().encodeToString(bytes);
		 			user.setPhoto("data:image/"+extense+";base64,"+encodeBase64);
 		 			 
		 			fileInputStream.close();
				 System.out.println("user  3 "+user.getPhoto());

				 } catch (Exception e) {
					 System.out.println("user  3 "+e);
	 				}
 		
			return new ResponseEntity<User>(user, HttpStatus.OK);

	}


	
	
    @PostMapping("/add")

    public ResponseEntity<String> addUser(@RequestBody UserDto userDto) {

        User user = new User();

        if (userRepo.existsByEmail(userDto.email)) {
            return new ResponseEntity(("Adresse e-mail déjà utilisée!"), HttpStatus.BAD_REQUEST);

        }

        user.setPhoto("photos_profil/u.jpg");


        //user.setEp(test);
        user.setEmail(userDto.email);
        user.setName(userDto.name);
        user.setPrenom(userDto.prenom);
        user.setAdresseP(userDto.adresseP);
        user.setCodeP(userDto.codeP);
        user.setDelegation(userDto.delegation);
        user.setGovernorat(userDto.governorat);
        user.setNumtel(userDto.numtel);
        user.setRS(userDto.rs);
        user.setArgent(50000);
        user.setPassword(passwordEncoder.encode(userDto.password));
        user.setDdn(userDto.ddn);

        Role userRole = roleRepo.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));
        userRepo.save(user);
        return new ResponseEntity(("User enregistré avec succès"), HttpStatus.OK);


    }

	 @PostMapping("/update")

	 public void modifierphoto(@RequestParam("imageFile") MultipartFile file) throws IOException {
		 
		 long id;
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			id = ((UserPrincipal) principal).getId();
			User user = userRepo.findById(id).orElseThrow(
					() -> new UsernameNotFoundException("User not found  " ));		
 		 
			 byte [] bytes=file.getBytes();
			  Path path = FileSystems.getDefault().getPath("photos_profil/"+file.getOriginalFilename());

			  Files.write(path, bytes);
			   user.setPhoto("photos_profil/"+file.getOriginalFilename());
			   userRepo.save(user);

		 
	 }
	 
	 
	 
	 @GetMapping("/getall")

		public ResponseEntity<Collection<User>>getAllUser(){
 			 
			 Collection<User> cu=userRepo.findAll();
	 for(User user:cu) {
			try {
			  		File f=new File(user.getPhoto()); 
		 	 
 
						 String encodeBase64=null;
						 String extense=FilenameUtils.getExtension(f.getName());
 
							FileInputStream fileInputStream=new FileInputStream(f);
			 			byte [] bytes=new byte[(int)f.length()];
			 			fileInputStream.read(bytes);
			 			encodeBase64=Base64.getEncoder().encodeToString(bytes);
			 			user.setPhoto("data:image/"+extense+";base64,"+encodeBase64);
	 		 			 
			 			fileInputStream.close();
 
					 } catch (Exception e) {
 		 				}
	 }
				return new ResponseEntity<Collection<User>>(cu, HttpStatus.OK);

		}
	 @DeleteMapping("/delete/{ida}")

	 public void deleteArticle(@PathVariable("ida") long ida) {
		 List<Long>userIds=new ArrayList<Long>();
		 userIds.add(ida);
		 List<User>la=userRepo.findByIdIn(userIds);
 		 for(User u:la) {
 			userRepo.delete(u);
		 }
		 Collection<Articles> ca=articleRepo.findByidUser(ida);
		 
		 for(Articles a:ca) {
			 articleRepo.delete(a);
			 Collection<Suivie> s=suivieRep.findByIdArticel(a.getIdArtcile());
			 for(Suivie sui:s) {
				 suivieRep.delete(sui);
			 }
		 }
		 
		 
	 }
	 
	 @GetMapping("/argent")

		public ResponseEntity<Float>getArgentUser(){
			String mail;
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			mail = ((UserPrincipal) principal).getEmail();
			User user = userRepo.findByEmail(mail).orElseThrow(
					() -> new UsernameNotFoundException("User not found with username or email : " + mail));	
			
			 
			return new ResponseEntity<Float>(user.getArgent(), HttpStatus.OK);

	 
	 }
	 @PostMapping("/investe")

		public ResponseEntity<String>investe( @RequestParam("quantity") String quantity, @RequestParam("id") String idP){
			String mail;
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			mail = ((UserPrincipal) principal).getEmail();
			User user = userRepo.findByEmail(mail).orElseThrow(
					() -> new UsernameNotFoundException("User not found with username or email : " + mail));	
			float argentuser=user.getArgent();
			Float quantityy=Float.parseFloat(quantity);
			long idp2=Long.parseLong(idP);
			Projet p=projetRep.findByIdProjet(idp2);
			float montantmin=Float.parseFloat(p.getMontantMin());
			if(argentuser<quantityy) {
				return new ResponseEntity<String>("L'argent ne suffit pas", HttpStatus.BAD_REQUEST);

				
			}
			
			else if (quantityy<montantmin) {
				return new ResponseEntity<String>("L'argent inférieur a montant minimum", HttpStatus.BAD_REQUEST);

			}
			else {
				p.setMontantTotal(p.getMontantTotal()-quantityy);
				projetRep.save(p);
				user.setArgent(user.getArgent()-quantityy);
				userRepo.save(user);
				return new ResponseEntity<String>("vous avez investe", HttpStatus.OK);

			}
 
			 
 
	 
	 }
 }
