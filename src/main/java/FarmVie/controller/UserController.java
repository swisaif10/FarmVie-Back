package FarmVie.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Collection;

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

import FarmVie.model.Articles;
import FarmVie.model.Role;
import FarmVie.model.RoleName;
import FarmVie.model.Suivie;
import FarmVie.model.User;
import FarmVie.modelDTO.UserDTO;
import FarmVie.repository.ArticleRep;
import FarmVie.repository.RoleRepo;
import FarmVie.repository.SuivieRep;
import FarmVie.repository.UserRepo;
import FarmVie.security.UserPrincipal;
import FarmVie.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/get")
	public ResponseEntity<User> getUser() {
		try {
			User user = userService.getCurrent();
			return new ResponseEntity<User>(user, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<User>(userService.getCurrent(), HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/add")

	public ResponseEntity<String> addUser(@RequestBody UserDTO user) {
		try {
			if (userService.add(user))
				return new ResponseEntity("User enregistré avec succès", HttpStatus.OK);
			else
				return new ResponseEntity("Adresse e-mail déjà utilisée!", HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			return new ResponseEntity(("Problème lors de l'insertion de l'utilisateur!"), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/updatePhoto")
	public ResponseEntity<String> updatePhoto(@RequestParam("imageFile") MultipartFile file) throws IOException {
		try {
			if (userService.updatePhoto(file))
				return new ResponseEntity<String>("Photo modifié avec succès", HttpStatus.OK);
			else
				return new ResponseEntity<String>("Erreur s'est produite", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<String>("Erreur s'est produite", HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/update/{id}")
	public ResponseEntity<String> updateUser(@RequestBody UserDTO user, @PathVariable("id") long id) throws IOException {
		System.out.println("5555" + id);
		try {
			if (userService.updateUser(user, id))
				return new ResponseEntity<String>("User modifié avec succès", HttpStatus.OK);
			else
				return new ResponseEntity<String>("User s'est produite", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return new ResponseEntity<String>("User s'est produite", HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/getall")

	public ResponseEntity<Collection<User>> getAllUser() {
		try {
			return new ResponseEntity<Collection<User>>(userService.getAll(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Collection<User>>(userService.getAll(), HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/delete/{id}")
	public void deleteArticle(@PathVariable("id") long id) {
		try {
			userService.delete(id);
		} catch (Exception e) {
			ResponseEntity.status(400).body(null);
		}

	}

}
