package ru.stoliarenko.gb.lesson1.team;

import java.util.ArrayList;
import java.util.List;

public class Course {

	public static void main(String[] args) {
		// Preparing a team
		Team theTeam = new Team();
		System.out.println(theTeam);
		
		// Put them on the test
		Course.doIT(theTeam);
		theTeam.showResults();
	}
	
	public static void doIT(Team team) {
		StringBuffer teamResults = new StringBuffer("Lets see how they overcome life difficulties!\n");
		
		List<String> dids = new ArrayList<>();
		dids.add(wakeUpEarly(team));
		dids.add(doTheExcersizes(team));
		dids.add(eatBreakfast(team));
		dids.add(meetPeople(team));
		dids.add(doNotCry(team));
		
		for (String string : dids) {
			teamResults.append(string);
			teamResults.append("\n");
			if (string.startsWith("-")) break; 
		}
		
		team.setResults(teamResults.toString());
	}
	
	private static String wakeUpEarly(Team team) {
		if (team == null) return "-404 team not found";
		final int DIFFICULTY = 20;
		int passed = team.overcomeOneByOne(DIFFICULTY);
		final boolean IS_EVERYONE_PASSED = passed == Team.getTeamCapacity();
		final boolean IS_NOONE_PASSED = passed == 0;
		final boolean IS_TEAM_FAILED = team.overcomeAsATeam(DIFFICULTY) == false;
		if (IS_EVERYONE_PASSED) return "+Great success! Every team member awaken in time.";
		else if (IS_NOONE_PASSED || IS_TEAM_FAILED) return "-Failure! Team cant even wake up in time!";
		else return String.format("+Teamwork saved the day! %d of %d team members couldn't wake up.", 
												Team.getTeamCapacity()-passed, Team.getTeamCapacity());
	}
	private static String doTheExcersizes(Team team) {
		if (team == null) return "-404 team not found";
		final int DIFFICULTY = 35;
		int passed = team.overcomeOneByOne(DIFFICULTY);
		final boolean IS_EVERYONE_PASSED = passed == Team.getTeamCapacity();
		final boolean IS_NOONE_PASSED = passed == 0;
		final boolean IS_TEAM_FAILED = team.overcomeAsATeam(DIFFICULTY) == false;
		if (IS_EVERYONE_PASSED) return "+Great success! Every team member did his excersizes.";
		else if (IS_NOONE_PASSED || IS_TEAM_FAILED) return "-Failure! Team cant even warm up!";
		else return String.format("+Teamwork saved the day! %d of %d team members couldn't warm up.", 
												Team.getTeamCapacity()-passed, Team.getTeamCapacity());
	}
	private static String eatBreakfast(Team team) {
		if (team == null) return "-404 team not found";
		final int DIFFICULTY = 50;
		int passed = team.overcomeOneByOne(DIFFICULTY);
		final boolean IS_EVERYONE_PASSED = passed == Team.getTeamCapacity();
		final boolean IS_NOONE_PASSED = passed == 0;
		final boolean IS_TEAM_FAILED = team.overcomeAsATeam(DIFFICULTY) == false;
		if (IS_EVERYONE_PASSED) return "+Great success! Every team member ate his breakfast.";
		else if (IS_NOONE_PASSED || IS_TEAM_FAILED) return "-Failure! Team cant even eat!";
		else return String.format("+Teamwork saved the day! %d of %d team members couldn't eat a breakfast.", 
												Team.getTeamCapacity()-passed, Team.getTeamCapacity());
	}
	private static String meetPeople(Team team) {
		if (team == null) return "-404 team not found";
		final int DIFFICULTY = 80;
		int passed = team.overcomeOneByOne(DIFFICULTY);
		final boolean IS_EVERYONE_PASSED = passed == Team.getTeamCapacity();
		final boolean IS_NOONE_PASSED = passed == 0;
		final boolean IS_TEAM_FAILED = team.overcomeAsATeam(DIFFICULTY) == false;
		if (IS_EVERYONE_PASSED) return "+Great success! Every team member has met some people.";
		else if (IS_NOONE_PASSED || IS_TEAM_FAILED) return "-Failure! Team cant even talk to people!";
		else return String.format("+Teamwork saved the day! %d of %d team members couldn't talk.", 
												Team.getTeamCapacity()-passed, Team.getTeamCapacity());
	}
	private static String doNotCry(Team team) {
		if (team == null) return "-404 team not found";
		final int DIFFICULTY = 105;
		int passed = team.overcomeOneByOne(DIFFICULTY);
		final boolean IS_EVERYONE_PASSED = passed == Team.getTeamCapacity();
		final boolean IS_NOONE_PASSED = passed == 0;
		final boolean IS_TEAM_FAILED = team.overcomeAsATeam(DIFFICULTY) == false;
		if (IS_EVERYONE_PASSED) return "?!Cheater!!!";
		else if (IS_NOONE_PASSED || IS_TEAM_FAILED) return "-Failure! This is a team of crybabies!";
		else return String.format("+Teamwork saved the day! %d of %d team members couldn't stop crying.", 
												Team.getTeamCapacity()-passed, Team.getTeamCapacity());
	}

}
