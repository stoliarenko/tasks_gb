package ru.stoliarenko.gb.lesson1.team;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Team {
	private static final int TEAM_CAPACITY = 4;
	private static final float TEAMWORK_RATE = 1.2f;
	List<TeamMember> team = new ArrayList<>();
	private String results = "";
	
	// Constructors
	public Team() {
		super();
		while (this.team.size() < TEAM_CAPACITY) {
			team.add(new TeamMember());
		}
	}
	public Team(List<TeamMember> team) {
		super();
		int counter = 4;
		Iterator<TeamMember> iterator = team.iterator();
		while (counter > 0) {
			if (iterator.hasNext()) this.team.add(iterator.next());
			else this.team.add(new TeamMember());
			counter--;
		}
	}
	
	// Getters&Setters
	private int getAverageSkill() {
		int total = 0;
		for (TeamMember teamMember : team) {
			total += teamMember.getSkillLevel();
		}
		return (int)(total*TEAMWORK_RATE / TEAM_CAPACITY);
	}	
	public static int getTeamCapacity() {
		return TEAM_CAPACITY;
	}
	public void setResults(String results) {
		this.results = results;
	}
	
	// Methods
	public boolean overcomeAsATeam(int difficulty) {
		return difficulty < getAverageSkill();
	}
	public int overcomeOneByOne(int difficulty) {
		int counter = 0;
		for (TeamMember teamMember : team) {
			if(teamMember.overcome(difficulty)) counter++;
		}
		return counter;
	}
	public void showResults() {
		System.out.println(results);
	}
	
	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		result.append(String.format("Team has %d members:%n", team.size()));
		for (TeamMember teamMember : team) {
			result.append(teamMember.toString() + "\n");
		}
		return result.toString();
	}
	
}
