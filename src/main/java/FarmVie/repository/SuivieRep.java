package FarmVie.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import FarmVie.model.Suivie;

public interface SuivieRep extends JpaRepository<Suivie,Long> {
	Collection<Suivie> findByIdArticel(long id);
	Collection<Suivie> findByUserid(long id);
	Collection<Suivie> findByIdSuivie(long id);
	Collection <Suivie> findByIdArticelAndUserid(long idA,long IdU);
	Collection <Suivie> findByIdProjet(long id);


}
