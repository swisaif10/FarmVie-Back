package com.vape.sec.repo;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vape.sec.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);
  
	List<User> findByIdIn(List<Long> userIds);
 
 
	 @Query("select u from User u,Projet p where  u.id= p.idUser and  p.idProjet=?1  ")
	 User getuser( long id);
	Boolean existsByEmail(String email);
}
