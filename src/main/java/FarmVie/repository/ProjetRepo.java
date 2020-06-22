package FarmVie.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import FarmVie.model.Articles;
import FarmVie.model.Projet;

public interface   ProjetRepo extends JpaRepository<Projet,Long> {
	Collection<Projet> findByidUser(long id);



}
