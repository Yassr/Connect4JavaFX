package application;

/**
 * 
 * @author Yassr Shaar - 14328571
 * Player class is used to create a player and store their name and colour.
 *
 */
public class Player {
	
	private String name = "";
	private String colour = "";
	
	/**
	 * Constructor for Player
	 * @param name of the player
	 * @param colour of the player
	 */
	public Player(String name, String colour) {
		this.name = name;
		this.colour = colour;
	}
	
	/**
	 * @return name of player
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name set player name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return player colour
	 */
	public String getColour() {
		return colour; 
	}
	
	/**
	 * @param colour set player colour
	 */
	public void setColour(String colour) {
		this.colour = colour;
	}

	
	 

}
