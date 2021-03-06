/**
 * 
 */
package com.vape.sec.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
 

import org.hibernate.annotations.NaturalId;

/**
 * @author Vaibhav.Singh
 *
 */

@Entity
@Table(name = "users")
public class User {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
     private String name;

    private String prenom;

private String photo;


 private String numtel; 
 private String ddn;
 private float argent;
    private String email;
    private String RS;
    private String adresseP;
    private String governorat;
    private String delegation;
    private String codeP;
    private String ep;

    @NotBlank
     private String password;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();
    
    public User() {

    }
    
    

   



	public String getEp() {
		return ep;
	}







	public void setEp(String ep) {
		this.ep = ep;
	}







	public float getArgent() {
		return argent;
	}



	public void setArgent(float argent) {
		this.argent = argent;
	}



	public String getPrenom() {
		return prenom;
	}


	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}


	public String getNumtel() {
		return numtel;
	}


	public void setNumtel(String numtel) {
		this.numtel = numtel;
	}


	public String getDdn() {
		return ddn;
	}


	public void setDdn(String ddn) {
		this.ddn = ddn;
	}


	public String getRS() {
		return RS;
	}


	public void setRS(String rS) {
		RS = rS;
	}


	public String getAdresseP() {
		return adresseP;
	}


	public void setAdresseP(String adresseP) {
		this.adresseP = adresseP;
	}


	public String getGovernorat() {
		return governorat;
	}


	public void setGovernorat(String governorat) {
		this.governorat = governorat;
	}


	public String getDelegation() {
		return delegation;
	}


	public void setDelegation(String delegation) {
		this.delegation = delegation;
	}


	public String getCodeP() {
		return codeP;
	}


	public void setCodeP(String codeP) {
		this.codeP = codeP;
	}


	public User(String name, String email, String password) {
        this.name = name;
         this.email = email;
        this.password = password;
    }
    

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	 
 

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

 

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public User(Long id,   String name, String photo,   
			 String email, @NotBlank  String password, Set<Role> roles) {
		super();
		this.id = id;
		this.name = name;
		this.photo = photo;
 		this.email = email;
		this.password = password;
		this.roles = roles;
	}







	public User(Long id, @NotBlank String name, String prenom, String photo, String numtel, String ddn, float argent,
			String email, String rS, String adresseP, String governorat, String delegation, String codeP, String ep,
			@NotBlank String password, Set<Role> roles) {
		super();
		this.id = id;
		this.name = name;
		this.prenom = prenom;
		this.photo = photo;
		this.numtel = numtel;
		this.ddn = ddn;
		this.argent = argent;
		this.email = email;
		RS = rS;
		this.adresseP = adresseP;
		this.governorat = governorat;
		this.delegation = delegation;
		this.codeP = codeP;
		this.ep = ep;
		this.password = password;
		this.roles = roles;
	}



	 



 

	 
	
    
    

}
