package payloads;

import org.springframework.web.multipart.MultipartFile;

public class ProjetRequest {
	private String gevernorat;
	private String delegation;

	private String superficieE;
	private String superficieT;

	private String irrigation;
	private String siege;
	private MultipartFile sol;
	private String  nomProjet;
	private MultipartFile photoProjet;
	private String biologique;
 	private String type;
 	private String  description;
 	private String montantMin;
 
  	private String typeFinance;

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

	public MultipartFile getSol() {
		return sol;
	}

	public void setSol(MultipartFile sol) {
		this.sol = sol;
	}

	public String getNomProjet() {
		return nomProjet;
	}

	public void setNomProjet(String nomProjet) {
		this.nomProjet = nomProjet;
	}

	public MultipartFile getPhotoProjet() {
		return photoProjet;
	}

	public void setPhotoProjet(MultipartFile photoProjet) {
		this.photoProjet = photoProjet;
	}

	public String getBiologique() {
		return biologique;
	}

	public void setBiologique(String biologique) {
		this.biologique = biologique;
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

	public String getTypeFinance() {
		return typeFinance;
	}

	public void setTypeFinance(String typeFinance) {
		this.typeFinance = typeFinance;
	}
 	
	 
	
}
