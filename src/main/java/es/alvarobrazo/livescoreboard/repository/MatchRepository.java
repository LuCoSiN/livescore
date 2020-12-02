package es.alvarobrazo.livescoreboard.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import es.alvarobrazo.livescoreboard.entity.Match;

@Repository
public interface  MatchRepository extends CrudRepository<Match, Long>  {
	
	@Override
    List<Match> findAll();

}
