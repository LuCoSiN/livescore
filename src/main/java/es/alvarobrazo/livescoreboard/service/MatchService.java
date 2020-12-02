package es.alvarobrazo.livescoreboard.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import es.alvarobrazo.livescoreboard.entity.Match;
import es.alvarobrazo.livescoreboard.exceptions.MyResourceNotFoundException;
import es.alvarobrazo.livescoreboard.repository.MatchRepository;

@Component
public class MatchService {
	
	@Autowired
	MatchRepository matchRepository;
	
	public List<Match> getAllMatchs(){
		return matchRepository.findAll();
	}
	
	public Match newMatch(Match match) {
		match.setHomeScore(0);
		match.setAwayScore(0);
		match.setUpdateDate(new Date());
		match.setRegisterDate(new Date());
		return matchRepository.save(match);
	}
	
	
	public Match updateMatch(Match match) throws MyResourceNotFoundException {
		//bucamos el partido con el mismo id y seteamos valores
		Optional<Match> m= matchRepository.findById(match.getId());
		if(m.isPresent()) {
			m.get().setAwayScore(match.getAwayScore());
			m.get().setHomeScore(match.getHomeScore());;
			m.get().setUpdateDate(new Date());
			return matchRepository.save(m.get());
		}
		throw new MyResourceNotFoundException("Match not found");
	}
	
	public void deleteMatch(long id) throws MyResourceNotFoundException {
		//bucamos el partido con el mismo id y seteamos valores
		Optional<Match> m= matchRepository.findById(id);
		if(m.isPresent()) {
			matchRepository.delete(m.get());
		}else {
			throw new MyResourceNotFoundException("Match not found");
		}
		
	}

}
