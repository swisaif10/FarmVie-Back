package com.vape.sec.model;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
@Entity
@Table(name = "Investe")
@EntityListeners(AuditingEntityListener.class)

public class Investe {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idI;
	private long idP;
	private long idU;
	private float montant;
 	
	 
	public long getIdI() {
		return idI;
	}
	public void setIdI(long idI) {
		this.idI = idI;
	}
	public long getIdP() {
		return idP;
	}
	public void setIdP(long idP) {
		this.idP = idP;
	}
	public long getIdU() {
		return idU;
	}
	public void setIdU(long idU) {
		this.idU = idU;
	}
	public float getMontant() {
		return montant;
	}
	public void setMontant(float montant) {
		this.montant = montant;
	}
	public Investe(long idI, long idP, long idU, float montant) {
		super();
		this.idI = idI;
		this.idP = idP;
		this.idU = idU;
		this.montant = montant;
	}
	public Investe() {
		super();
	}


}
