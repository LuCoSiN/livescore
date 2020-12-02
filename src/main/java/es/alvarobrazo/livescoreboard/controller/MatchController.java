package es.alvarobrazo.livescoreboard.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import es.alvarobrazo.livescoreboard.entity.Match;
import es.alvarobrazo.livescoreboard.exceptions.MyResourceNotFoundException;
import es.alvarobrazo.livescoreboard.service.MatchService;

@RestController
@RequestMapping("/match")
public class MatchController {
	
	
	@Autowired
	private MatchService matchService;
	
	@PostMapping( path="/",produces = "application/json")
	public Match newMatch(@RequestBody Match match) {
		return matchService.newMatch(match);
	}
	
	@PutMapping(path="/",produces = "application/json")
	public Match updateMatch(@RequestBody Match match,HttpServletResponse response) throws MyResourceNotFoundException {
		try {
			return matchService.updateMatch(match);
		}catch (MyResourceNotFoundException exc) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Match Not Found", exc);
		}
	}
	
	@DeleteMapping(path="/{id}")
	public void deleteMatch(@PathVariable long id) throws MyResourceNotFoundException {
		try {
			matchService.deleteMatch(id);
		}catch (MyResourceNotFoundException exc) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Match Not Found", exc);
		}
	}

}
