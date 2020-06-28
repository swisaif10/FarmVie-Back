package com.vape.sec.repo;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vape.sec.model.Contact;
import com.vape.sec.model.Projet;

@Repository
public interface ContactRep extends JpaRepository<Contact,Long> {

	Contact findByIdC(long id);

}
