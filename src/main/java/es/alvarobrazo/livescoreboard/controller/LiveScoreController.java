package es.alvarobrazo.livescoreboard.controller;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.alvarobrazo.livescoreboard.entity.Match;
import es.alvarobrazo.livescoreboard.service.MatchService;

@RestController
@RequestMapping("/livescore")
public class LiveScoreController {
	
	@Autowired
	private MatchService matchService;
	
	
	@GetMapping(path="/", produces = "application/json")
	public List<Match> getAll() {
		List<Match> res= matchService.getAllMatchs();
		Collections.sort(res);
		return res;
	}
	
	

}
