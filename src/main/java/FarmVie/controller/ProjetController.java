package FarmVie.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import FarmVie.model.Articles;
import FarmVie.model.Projet;
import FarmVie.modelDTO.ProjetDTO;
import FarmVie.modelDTO.UserDTO;
import FarmVie.repository.ProjetRepo;
import FarmVie.security.UserPrincipal;
import FarmVie.service.ProjectService;
import payloads.ProjetRequest;

@RestController
@RequestMapping("/projet")
public class ProjetController {
	@Autowired
	private ProjectService projectService;

	@PostMapping("/add")
	public ResponseEntity<Projet> addprojet(@RequestBody ProjetDTO projet) throws IOException {
		try {
			return new ResponseEntity<Projet>(projectService.add(projet), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Projet>(new Projet(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/getall")
	public ResponseEntity<List<Projet>> getArticle() {
		try {
			return new ResponseEntity<List<Projet>>(projectService.getAll(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<List<Projet>>( new ArrayList<Projet>() , HttpStatus.BAD_REQUEST);
		}		

	}

}
