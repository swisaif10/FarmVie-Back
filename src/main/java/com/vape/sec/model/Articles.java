 
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
@Table(name = "Articles")
@EntityListeners(AuditingEntityListener.class)

public class Articles    {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long idArtcile;
	private long idUser;
	private String nomArticle;
	private String contact;
	private String prix;
	private String description;
	private String lieu;
	private String  date;
	private String email;
 	private String image;
 	private float  articlevote;
 	private float uservote;
 	private boolean isfavorite;
 	
 	
	
	
	 
	public boolean isIsfavorite() {
		return isfavorite;
	}
	public void setIsfavorite(boolean isfavorite) {
		this.isfavorite = isfavorite;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String urlimage) {
		this.image = urlimage;
	}
	public long getIdArtcile() {
		return idArtcile;
	}
	public void setIdArtcile(long idArtcile) {
		this.idArtcile = idArtcile;
	}
	public long getIdUser() {
		return idUser;
	}
	public void setIdUser(long idUser) {
		this.idUser = idUser;
	}
	public String getNomArticle() {
		return nomArticle;
	}
	public void setNomArticle(String nomArticle) {
		this.nomArticle = nomArticle;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getPrix() {
		return prix;
	}
	public void setPrix(String prix) {
		this.prix = prix;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLieu() {
		return lieu;
	}
	public void setLieu(String lieu) {
		this.lieu = lieu;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	 
	public float getArticlevote() {
		return articlevote;
	}
	public void setArticlevote(float articlevote) {
		this.articlevote = articlevote;
	}
	public float getUservote() {
		return uservote;
	}
	public void setUservote(float uservote) {
		this.uservote = uservote;
	}
	 
	
	public Articles(long idArtcile, long idUser, String nomArticle, String contact, String prix, String description,
			String lieu, String date, String email, String image, float articlevote, float uservote) {
		super();
		this.idArtcile = idArtcile;
		this.idUser = idUser;
		this.nomArticle = nomArticle;
		this.contact = contact;
		this.prix = prix;
		this.description = description;
		this.lieu = lieu;
		this.date = date;
		this.email = email;
		this.image = image;
		this.articlevote = articlevote;
		this.uservote = uservote;
	}
	public Articles() {
		super();
	}
	
	
	

}
