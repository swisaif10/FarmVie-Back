package FarmVie.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import FarmVie.model.Projet;
import FarmVie.model.User;
import FarmVie.modelDTO.ProjetDTO;
import FarmVie.repository.ProjetRepo;
import FarmVie.security.UserPrincipal;

@Service
public class ProjectService {

	@Autowired
	public ProjetRepo projetReo;

	public Projet add(ProjetDTO projet) throws IOException {
		long id = -1;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserPrincipal) {
			id = ((UserPrincipal) principal).getId();

		}
		Projet ProjetNew = new Projet();
		ProjetNew.setIdUser(id);
		if (projet.gevernorat != null)
			ProjetNew.setGevernorat(projet.gevernorat);
		if (projet.delegation != null)
			ProjetNew.setDelegation(projet.delegation);
		if (projet.superficieE != null)
			ProjetNew.setSuperficieE(projet.superficieE);
		if (projet.superficieT != null)
			ProjetNew.setSuperficieT(projet.superficieT);
		if (projet.biologique != null)
			ProjetNew.setBiologique(projet.biologique);
		if (projet.irrigation != null)
			ProjetNew.setIrrigation(projet.irrigation);
		if (projet.siege != null)
			ProjetNew.setSiege(projet.siege);
		if (projet.sol != null) {
			byte[] bytes = projet.sol.getBytes();
			Path path = FileSystems.getDefault().getPath("sol/" + projet.sol.getOriginalFilename());
			Files.write(path, bytes);
			ProjetNew.setSol("sol/" + projet.sol.getOriginalFilename());
		}
		if (projet.nomProjet != null)
			ProjetNew.setNomProjet(projet.nomProjet);
		if (projet.photoProjet != null) {
			byte[] bytes2 = projet.photoProjet.getBytes();
			Path path2 = FileSystems.getDefault().getPath("photos_profil/" + projet.photoProjet.getOriginalFilename());
			Files.write(path2, bytes2);
			ProjetNew.setPhotoProjet("photos_profil/" + projet.photoProjet.getOriginalFilename());
		}
		if (projet.type != null)
			ProjetNew.setType(projet.type);
		if (projet.description != null)
			ProjetNew.setDescription(projet.description);
		if (projet.montantMin != null)
			ProjetNew.setMontantMin(projet.montantMin);
		if (projet.isfavorite)
			ProjetNew.setIsfavorite(projet.isfavorite);
		if (projet.typeFinance != null)
			ProjetNew.setTypeFinance(projet.typeFinance);
		return projetReo.save(ProjetNew);
	}

	public List<Projet> getAll() throws IOException {
		List<Projet> projets = projetReo.findAll();
		for (Projet projet : projets) {
			if(projet.getPhotoProjet() != null) {
				File f = new File(projet.getPhotoProjet());
				String encodeBase64 = null;
				String extense = FilenameUtils.getExtension(f.getName());
				FileInputStream fileInputStream = new FileInputStream(f);
				byte[] bytes = new byte[(int) f.length()];
				fileInputStream.read(bytes);
				encodeBase64 = Base64.getEncoder().encodeToString(bytes);
				projet.setPhotoProjet("data:image/" + extense + ";base64," + encodeBase64);
				fileInputStream.close();
			}	
			if(projet.getSol() != null ) {
				File f_ = new File(projet.getSol());
				String encodeBase64_ = null;
				String extense_ = FilenameUtils.getExtension(f_.getName());
				FileInputStream fileInputStream_ = new FileInputStream(f_);
				byte[] bytes_ = new byte[(int) f_.length()];
				fileInputStream_.read(bytes_);
				encodeBase64_ = Base64.getEncoder().encodeToString(bytes_);
				projet.setSol("data:image/" + extense_ + ";base64," + encodeBase64_);
				fileInputStream_.close();
			}			
		}
		return projets;
	}
}
