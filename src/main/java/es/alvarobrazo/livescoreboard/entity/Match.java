package es.alvarobrazo.livescoreboard.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "matchs")
public class Match implements Comparable<Match>{
	
	public Match() {
		
	}
	
	public Match(String homeTeam, String awayTeam) {
		super();
		this.homeTeam = homeTeam;
		this.awayTeam = awayTeam;
	}
	
	public Match(long id, Integer homeScore, Integer awayScore) {
		super();
		this.id = id;
		this.homeScore = homeScore;
		this.awayScore = awayScore;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    
    private String homeTeam;
    
    private Integer homeScore;
    
    private String awayTeam;
    
    private Integer awayScore;
    
    private Date registerDate;
    
    private Date updateDate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getHomeTeam() {
		return homeTeam;
	}

	public void setHomeTeam(String homeTeam) {
		this.homeTeam = homeTeam;
	}

	public Integer getHomeScore() {
		return homeScore;
	}

	public void setHomeScore(Integer homeScore) {
		this.homeScore = homeScore;
	}

	public String getAwayTeam() {
		return awayTeam;
	}

	public void setAwayTeam(String awayTeam) {
		this.awayTeam = awayTeam;
	}

	public Integer getAwayScore() {
		return awayScore;
	}

	public void setAwayScore(Integer awayScore) {
		this.awayScore = awayScore;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	@JsonIgnore
	public Integer getTotalScore() {
		return awayScore+homeScore;
	}

	@Override
	public int compareTo(Match o) {
		int res =o.getTotalScore().compareTo(this.getTotalScore());
		if(res==0) {
			res=o.getUpdateDate().compareTo(this.getUpdateDate());
		}
		return res;
	}

	
    
	

}
