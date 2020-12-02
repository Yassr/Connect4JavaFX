package application;

public class Player {
	
	
	
	public Player(String name, String colour) {
		this.name = name;
		this.colour = colour;
	}
	
	public Player(String score) {
		this.score = score;
	}
	
	
	private String name = "";
	private String colour = "";
	private String score = "";
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getColour() {
		return colour; 
	}
	public void setColour(String colour) {
		this.colour = colour;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	
	 

}
