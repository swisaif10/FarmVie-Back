 
package com.vape.sec.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "Projet")
@EntityListeners(AuditingEntityListener.class)

public class Projet    {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long idProjet;
	private long idUser;
	private String gevernorat;
	private String emailUser;
 
	private String delegation;
	private String superficieE;
	private String superficieT;
private String biologique;
	private String irrigation;
	private String siege;
	private String sol;
	private String  nomProjet;
	private String photoProjet;
 	private String type;
 	private String  description;
 	private String montantMin;
 	private float montantTotal;
 	private float montantRecu;
 	private String etat;
 private LocalDate datadeployment;
 	private boolean isfavorite;
 	private String typeFinance;
 	
 	
  	private float pourcentage;
 	private String ip;
 	
 	
	 
	public String getEmailUser() {
		return emailUser;
	}
	public void setEmailUser(String emailUser) {
		this.emailUser = emailUser;
	}
	 
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public float getPourcentage() {
		return pourcentage;
	}
	public void setPourcentage(float pourcentage) {
		this.pourcentage = pourcentage;
	}
	public float getMontantRecu() {
		return montantRecu;
	}
	public void setMontantRecu(float montantRecu) {
		this.montantRecu = montantRecu;
	}
	public float getMontantTotal() {
		return montantTotal;
	}
	public void setMontantTotal(float montantTotal) {
		this.montantTotal = montantTotal;
	}
	public LocalDate getDatadeployment() {
		return datadeployment;
	}
	public void setDatadeployment(LocalDate datadeployment) {
		this.datadeployment = datadeployment;
	}
	public String getEtat() {
		return etat;
	}
	public void setEtat(String etat) {
		this.etat = etat;
	}
	public long getIdProjet() {
		return idProjet;
	}
	public void setIdProjet(long idProjet) {
		this.idProjet = idProjet;
	}
	public long getIdUser() {
		return idUser;
	}
	public void setIdUser(long idUser) {
		this.idUser = idUser;
	}
	public String getGevernorat() {
		return gevernorat;
	}
	public void setGevernorat(String gevernorat) {
		this.gevernorat = gevernorat;
	}
	public String getDelegation() {
		return delegation;
	}
	public void setDelegation(String delegation) {
		this.delegation = delegation;
	}
	public String getSuperficieE() {
		return superficieE;
	}
	public void setSuperficieE(String superficieE) {
		this.superficieE = superficieE;
	}
	public String getSuperficieT() {
		return superficieT;
	}
	public void setSuperficieT(String superficieT) {
		this.superficieT = superficieT;
	}
	public String getBiologique() {
		return biologique;
	}
	public void setBiologique(String biologique) {
		this.biologique = biologique;
	}
	public String getIrrigation() {
		return irrigation;
	}
	public void setIrrigation(String irrigation) {
		this.irrigation = irrigation;
	}
	public String getSiege() {
		return siege;
	}
	public void setSiege(String siege) {
		this.siege = siege;
	}
	public String getSol() {
		return sol;
	}
	public void setSol(String sol) {
		this.sol = sol;
	}
	public String getNomProjet() {
		return nomProjet;
	}
	public void setNomProjet(String nomProjet) {
		this.nomProjet = nomProjet;
	}
	public String getPhotoProjet() {
		return photoProjet;
	}
	public void setPhotoProjet(String photoProjet) {
		this.photoProjet = photoProjet;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getMontantMin() {
		return montantMin;
	}
	public void setMontantMin(String montantMin) {
		this.montantMin = montantMin;
	}
	public boolean isIsfavorite() {
		return isfavorite;
	}
	public void setIsfavorite(boolean isfavorite) {
		this.isfavorite = isfavorite;
	}
	public String getTypeFinance() {
		return typeFinance;
	}
	public void setTypeFinance(String typeFinance) {
		this.typeFinance = typeFinance;
	}
	 
 
	 
	 
 
	public Projet() {
		super();
	}
 	
	 
	
 	
 	
	
	
	 
	 
	
	
	

}
