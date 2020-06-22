package FarmVie.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import FarmVie.model.Articles;

@Repository
public interface ArticleRep extends JpaRepository<Articles,Long> {
	Collection<Articles> findByContactContains(String contact);
 	Collection<Articles> findByNomArticleContains(String contact);
	Collection<Articles> findByDescriptionContains(String contact);
	Collection<Articles> findByPrixContains(String contact);
	Collection<Articles> findByidArtcile(long id);
	Collection<Articles> findByidUser(long id);

   


}
