package FarmVie.model;

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
@Table(name = "Vote")
@EntityListeners(AuditingEntityListener.class)
public class Vote {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long idVote;
	private long idArticel;
	private long userid;
 
	private int note;
	public long getIdVote() {
		return idVote;
	}
	public void setIdVote(long idVote) {
		this.idVote = idVote;
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
	 
	public int getNote() {
		return note;
	}
	public void setNote(int note) {
		this.note = note;
	}
	public Vote(long idVote, long idArticel, long userid,int note) {
		super();
		this.note=note;
		this.idVote = idVote;
		this.idArticel = idArticel;
		this.userid = userid;
 	}
	public Vote(long idArticel, long userid, int note) {
		super();
		this.idArticel = idArticel;
		this.userid = userid;
		this.note = note;
	}
	public Vote() {
		super();
	}
	

}
