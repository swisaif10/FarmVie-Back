 
package com.vape.sec.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Contact")
@EntityListeners(AuditingEntityListener.class)

public class Contact    {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long idC;
	private String name;
	private String email;
 	private String subject;
 	private String description;
	 
	public long getIdC() {
		return idC;
	}
	public void setIdC(long idC) {
		this.idC = idC;
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
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Contact() {
		super();
	}
 	
 	

}
