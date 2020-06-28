package com.vape.sec.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vape.sec.model.Investe;
import com.vape.sec.model.Projet;
import com.vape.sec.model.User;

import payloads.Userinvest;
 @Repository

public interface InvesteRep  extends JpaRepository<Investe,Long>  {
	 @Query("select p from Projet p,Investe i where  p.idProjet= i.idP and  i.idU=?1 and p.idUser!=?1 ")
	 List<Projet> getprojet( long id);
	 @Query("select i.montant,u.name,u.email from User u,Investe i where  u.id= i.idU and  i.idP=?1  ")
	 List<Object> getprojet2( long id);
	 @Query("select u from User u,Investe i where  u.id= i.idU and  i.idP=?1  ")
	 List<User> getprojet3( long id);
	 Investe findByIdPAndIdU(long idp,long idu);

}
