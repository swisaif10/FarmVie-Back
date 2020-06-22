package FarmVie.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import FarmVie.model.Vote;

public interface VoteRep extends JpaRepository<Vote,Long> {
	Collection<Vote> findByIdArticel(long id);
	Collection<Vote> findByUserid(long id);
	Collection<Vote> findByUseridAndIdArticel(long idu,long ida);

	@Query(
			  value = "SELECT avg(v.note) FROM vote v WHERE v.id_articel = ?1", 
			  nativeQuery = true)
			float findMoyNoteArticle(long id);
	@Query(
			  value = "SELECT avg(v.note) FROM vote v WHERE userid = ?1", 
			  nativeQuery = true)
			float findMoyNoteUser(long id);
	 
}
