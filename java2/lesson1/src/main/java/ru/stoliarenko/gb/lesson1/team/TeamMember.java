package ru.stoliarenko.gb.lesson1.team;

import java.util.Random;

public class TeamMember {
	private static final Random RANDOM = new Random();
	private final int SKILL_LEVEL = RANDOM.nextInt(70) + 30;
	private static int memberCounter = 0;
	private String name;
	
	// Constructors
	public TeamMember(String name) {
		super();
		this.name = name;
		memberCounter++;
	}
	public TeamMember() {
		super();
		this.name = "TeamMember#" + ++memberCounter;
	}
	
	// Getters&Setters
	public int getSkillLevel() {
		return SKILL_LEVEL;
	}
	public String getName() {
		return name;
	}
	
	// Methods
	public boolean overcome(int difficulty) {
		return SKILL_LEVEL >= difficulty;
	}
	
	@Override
	public String toString() {
		return "-TeamMember " + this.name + " has skill level of " + this.SKILL_LEVEL;
	}

}
