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
@RequestMapping("/contact")
public class ContactController {

	 @Autowired
	  private ContactRep contactRep;
	 @PostMapping("/add") 
  public String add( @RequestParam("name") String name,@RequestParam("email") String email,
		  @RequestParam("subject") String subject,@RequestParam("description") String description) {
	try {
		Contact c = new Contact();
		c.setName(name);
		c.setSubject(subject);
		c.setEmail(email);
		c.setDescription(description);
		contactRep.save(c);
		return "success";
	}catch (Exception e) {
		return e.getMessage();
	}
  }
	 @GetMapping("/getall")

	 @CrossOrigin
	 public ResponseEntity<List<Contact>> getArticles(){
		 return new ResponseEntity<List<Contact>>(contactRep.findAll(),HttpStatus.OK);

	 }
	 @DeleteMapping("/delete/{ida}")

	 public void deleteArticle(@PathVariable("ida") long ida) {
		 Contact l=contactRep.findByIdC(ida);
		 
		 
		 contactRep.delete(l);
		 
		 
		 
	 }
}
