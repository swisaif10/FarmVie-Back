package com.vape.sec.repo;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

 import com.vape.sec.model.Projet;

public interface   ProjetRepo extends JpaRepository<Projet,Long> {
	Collection<Projet> findByidUser(long id);
	
	List<Projet> findByEtat(String e);
	Projet findByIdProjet(long id);
	List<Projet> findByType(String e);

}
