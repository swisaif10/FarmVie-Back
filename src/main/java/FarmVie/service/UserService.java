package FarmVie.service;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import FarmVie.modelDTO.UserDTO;
import FarmVie.model.Articles;
import FarmVie.model.Projet;
import FarmVie.model.Role;
import FarmVie.model.RoleName;
import FarmVie.model.Suivie;
import FarmVie.model.User;
import FarmVie.repository.ProjetRepo;
import FarmVie.repository.RoleRepo;
import FarmVie.repository.SuivieRep;
import FarmVie.repository.UserRepo;
import FarmVie.security.UserPrincipal;

@Service
public class UserService {

	@Autowired
	private UserRepo userRepo;
	@Autowired
	ProjetRepo projetRepo;
	@Autowired
	SuivieRep suiviRepo;
	@Autowired
	RoleRepo roleRepo;
	private PasswordEncoder passwordEncoder;

	public Collection<User> getAll() {
//		try {
////			this.userRepo.findAll();
////			Collection<User> users = this.userRepo.findAll();
////			
////			for (User user : users) {
////				try {
////					File f = new File(user.getPhoto());
////					String encodeBase64 = null;
////					String extense = FilenameUtils.getExtension(f.getName());
////					FileInputStream fileInputStream = new FileInputStream(f);
////					byte[] bytes = new byte[(int) f.length()];
////					fileInputStream.read(bytes);
////					encodeBase64 = Base64.getEncoder().encodeToString(bytes);
////					user.setPhoto("data:image/" + extense + ";base64," + encodeBase64);
////					fileInputStream.close();
//				} catch (Exception e) {
//					System.out.println("erruer de lors de la convertion photos d'utilisateur : " + e.getClass() + "===>"
//							+ e.getMessage());
//				}
//			}
//			return this.userRepo.findAll();
//		} catch (Exception e) {
//			System.out.println(
//					"erruer de lors de la récupération des utilisateurs : " + e.getClass() + "===>" + e.getMessage());
//		}
		System.out.println(this.roleRepo);
		return this.userRepo.findAll();
		// return null;
	}

	public void delete(Long id) {
		try {
			User user = this.userRepo.findById(id).get();
			userRepo.delete(user);
			Collection<Projet> projets = projetRepo.findByidUser(id);
			if (projets.size() != 0)
				for (Projet projet : projets) {
					projetRepo.delete(projet);
					Collection<Suivie> suivis = suiviRepo.findByIdProjet(projet.getIdProjet());
					for (Suivie suivi : suivis) {
						suiviRepo.delete(suivi);
					}
				}
		} catch (Exception e) {
			System.out.println(
					"erruer de lors de la suppression de l'utilisateur : " + e.getClass() + "===>" + e.getMessage());
		}

	}

	public User getCurrent() {
		Long id;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		id = ((UserPrincipal) principal).getId();
		id = (long) 1;
		Optional<User> user = this.userRepo.findById(id);

//		try {
//			File f = new File(user.getPhoto());
//			System.out.println("user   " + user.getPhoto());
//			String encodeBase64 = null;
//			String extense = FilenameUtils.getExtension(f.getName());
//			System.out.println("user   " + user.getPhoto());
//			FileInputStream fileInputStream = new FileInputStream(f);
//			byte[] bytes = new byte[(int) f.length()];
//			fileInputStream.read(bytes);
//			encodeBase64 = Base64.getEncoder().encodeToString(bytes);
//			user.setPhoto("data:image/" + extense + ";base64," + encodeBase64);
//			fileInputStream.close();
//			System.out.println("user  3 " + user.getPhoto());
//		} catch (Exception e) {
//			System.out.println("user  3 " + e);
//		}
		return user.get();
	}

	public boolean add(FarmVie.modelDTO.UserDTO user) {
		if (userRepo.existsByEmail(user.email)) {
			return false;
		}
		User userAdd = new User();
		userAdd.setPhoto("photos_profil/u.jpg");
		userAdd.setEmail(user.email);
		userAdd.setName(user.nom);
		userAdd.setPrenom(user.prenom);
		userAdd.setAdresseP(user.adresseP);
		userAdd.setCodeP(user.codeP);
		userAdd.setDelegation(user.delegation);
		userAdd.setGovernorat(user.governorat);
		userAdd.setNumtel(user.numtel);
		userAdd.setRS(user.rs);
		String pass = new BCryptPasswordEncoder().encode(user.password);
//		String pass = passwordEncoder.encode(user.password);
		userAdd.setPassword(pass);
		Role userRole = roleRepo.findByName(RoleName.ROLE_USER).get();
		userAdd.setRoles(Collections.singleton(userRole));
		userRepo.save(userAdd);
		return true;
	}

	public boolean updatePhoto(MultipartFile file) {
		try {
			long id;
			Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			id = ((UserPrincipal) principal).getId();
			User user = userRepo.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found  "));
			byte[] bytes = file.getBytes();
			Path path = FileSystems.getDefault().getPath("photos_profil/" + file.getOriginalFilename());
			Files.write(path, bytes);
			user.setPhoto("photos_profil/" + file.getOriginalFilename());
			userRepo.save(user);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean updateUser(FarmVie.modelDTO.UserDTO user, Long id) {
		try {
			User userUpdate = userRepo.findById(id).get();
			if (user.nom != null)
				userUpdate.setName(user.nom);
			if (user.prenom != null)
				userUpdate.setPrenom(user.prenom);
			if (user.email != null)
				userUpdate.setEmail(user.email);
			if (user.password != null)
				userUpdate.setPassword(new BCryptPasswordEncoder().encode(user.password));
			if (user.ddn != null) {
				SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
				userUpdate.setDdn(formatter.parse(user.ddn));
			}
			if (user.rs != null)
				userUpdate.setRS(user.rs);
			if (user.adresseP != null)
				userUpdate.setAdresseP(user.adresseP);
			if (user.governorat != null)
				userUpdate.setGovernorat(user.governorat);
			if (user.delegation != null)
				userUpdate.setDelegation(user.delegation);
			if (user.codeP != null)
				userUpdate.setCodeP(user.codeP);
			if (user.numtel != null)
				userUpdate.setNumtel(user.numtel);
			userRepo.save(userUpdate);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
