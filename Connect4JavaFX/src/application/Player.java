package application;

public class Player {
	
	
	
	public Player(String name, String colour) {
		this.name = name;
		this.colour = colour;
	}
	
	
	private String name = "";
	private String colour = "";
	private int score = 0;
	
	
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
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
	 

}
