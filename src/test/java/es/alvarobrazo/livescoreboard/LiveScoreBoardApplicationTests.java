package es.alvarobrazo.livescoreboard;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import es.alvarobrazo.livescoreboard.entity.Match;
import es.alvarobrazo.livescoreboard.service.MatchService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class LiveScoreBoardApplicationTests {

	
	
	@Autowired
	private TestRestTemplate testRestTemplate;
	
	@Autowired
	private MatchService matchService;
	
	@LocalServerPort
	private int port;
	
	
	@BeforeEach
	public void beforeEach() {
		Match m1 = new Match("Mexico","Canada");
		matchService.newMatch(m1);
		Match m2 = new Match("Spain","Brazil");
		matchService.newMatch(m2);
		Match m3 = new Match("Germany","France");
		matchService.newMatch(m3);
		Match m4 = new Match("Uruguay","Italy");
		matchService.newMatch(m4);
		Match m5 = new Match("Argentina","Australia");
		matchService.newMatch(m5);
		
		//a. Mexico - Canada: 0 - 5 
		m1.setHomeScore(0);
		m1.setAwayScore(5);
		matchService.updateMatch(m1);
		
		//b. Spain - Brazil: 10 – 2
		m2.setHomeScore(10);
		m2.setAwayScore(2);
		matchService.updateMatch(m2);
		
		//c. Germany - France: 2 – 2
		m3.setHomeScore(2);
		m3.setAwayScore(2);
		matchService.updateMatch(m3);
		

		//d. Uruguay - Italy: 6 – 6
		m4.setHomeScore(6);
		m4.setAwayScore(6);
		matchService.updateMatch(m4);
		
		//e. Argentina - Australia: 3 - 1 
		m5.setHomeScore(3);
		m5.setAwayScore(1);
		matchService.updateMatch(m5);
	}
	
	
	
	@Test
	@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
	public void getLivescoresOK() throws Exception {
		ResponseEntity<List<Match>> response = testRestTemplate.exchange(
				"http://localhost:" + this.port + "/api/livescore/", 
	            HttpMethod.GET, 
	            null,
	            new ParameterizedTypeReference<List<Match>>() {}
	            );
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(5, response.getBody().size());
	}
	
	@Test
	@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
	public void getLivesOrderTotalScoreByDate() throws Exception {
		ResponseEntity<List<Match>> response = testRestTemplate.exchange(
				"http://localhost:" + this.port + "/api/livescore/", 
	            HttpMethod.GET, 
	            null,
	            new ParameterizedTypeReference<List<Match>>() {}
	            );
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Uruguay", response.getBody().get(0).getHomeTeam());
		assertEquals("Spain", response.getBody().get(1).getHomeTeam());
		assertEquals("Mexico", response.getBody().get(2).getHomeTeam());
		assertEquals("Argentina", response.getBody().get(3).getHomeTeam());
		assertEquals("Germany", response.getBody().get(4).getHomeTeam());
	}
	
	@Test
	@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
	public void newLivescore() throws Exception {
		String url = "http://localhost:" + this.port + "/api/match/";
		Match m = new Match("Mexico","Canada");
		RequestEntity<Match> request = RequestEntity
	            .post(url)
	            .accept(MediaType.APPLICATION_JSON)
	            .contentType(MediaType.APPLICATION_JSON)
	            .body(m);
		ResponseEntity<Match> response = testRestTemplate.exchange(
				"http://localhost:" + this.port + "/api/match/", 
	            HttpMethod.POST, 
	            request,
	            Match.class
	            );
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Mexico", response.getBody().getHomeTeam());
		assertEquals("Canada", response.getBody().getAwayTeam());
		assertEquals(0, response.getBody().getAwayScore());
		assertEquals(0, response.getBody().getHomeScore());
	}
	
	@Test
	@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
	public void deleteMatch() throws Exception {
		ResponseEntity<String> response = testRestTemplate.exchange(
				"http://localhost:" + this.port + "/api/match/1", 
	            HttpMethod.DELETE, 
	            null,
	            String.class
	            );
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
	public void deleteMatchNotFound() throws Exception {
		ResponseEntity<String> response = testRestTemplate.exchange(
				"http://localhost:" + this.port + "/api/match/11", 
	            HttpMethod.DELETE, 
	            null,
	            String.class
	            );
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
	public void putMatch() throws Exception {
		String url = "http://localhost:" + this.port + "/api/match/";
		Match m = new Match(1,2,3);
		RequestEntity<Match> request = RequestEntity
	            .post(url)
	            .accept(MediaType.APPLICATION_JSON)
	            .contentType(MediaType.APPLICATION_JSON)
	            .body(m);
		ResponseEntity<Match> response = testRestTemplate.exchange(
				"http://localhost:" + this.port + "/api/match/", 
	            HttpMethod.PUT, 
	            request,
	            Match.class
	            );
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("Mexico", response.getBody().getHomeTeam());
		assertEquals("Canada", response.getBody().getAwayTeam());
		assertEquals(2,response.getBody().getHomeScore());
		assertEquals(3,response.getBody().getAwayScore());
	}
	
	@Test
	@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
	public void putMatchNotFound() throws Exception {
		String url = "http://localhost:" + this.port + "/api/match/";
		Match m = new Match(6,2,3);
		RequestEntity<Match> request = RequestEntity
	            .post(url)
	            .accept(MediaType.APPLICATION_JSON)
	            .contentType(MediaType.APPLICATION_JSON)
	            .body(m);
		ResponseEntity<Match> response = testRestTemplate.exchange(
				"http://localhost:" + this.port + "/api/match/", 
	            HttpMethod.PUT, 
	            request,
	            Match.class
	            );
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}
	
	

}
