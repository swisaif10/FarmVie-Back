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
@Table(name = "Suivie")
@EntityListeners(AuditingEntityListener.class)
public class Suivie {
	

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long idSuivie;
	private long idArticel;
	private long userid;
	
	public long getIdSuivie() {
		return idSuivie;
	}

	public void setIdSuivie(long idSuivie) {
		this.idSuivie = idSuivie;
	}

	public long getIdArticel() {
		return idArticel;
	}

	public void setIdArticel(long idArticel) {
		this.idArticel = idArticel;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public Suivie(long idSuivie, long idArticel, long userid) {
		super();
		this.idSuivie = idSuivie;
		this.idArticel = idArticel;
		this.userid = userid;
	}
	
	public Suivie() {
		super();
	}

	public Suivie(long idArticel, long userid) {
		super();
		this.idArticel = idArticel;
		this.userid = userid;
	}
	
}
