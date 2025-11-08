package co.edu.unbosque.model;

import java.util.Objects;

public class TerritoryDTO {
	
	private String name;
	private String color;
	private int numberTroops;
	
	public TerritoryDTO() {}

	public TerritoryDTO(String name, String color, int numberTroops) {
		this.name = name;
		this.color = color;
		this.numberTroops = numberTroops;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getNumberTroops() {
		return numberTroops;
	}

	public void setNumberTroops(int numberTroops) {
		this.numberTroops = numberTroops;
	}

	@Override
	public int hashCode() {
		return Objects.hash(color, name, numberTroops);
	}
	

}
