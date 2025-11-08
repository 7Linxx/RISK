package co.edu.unbosque.model;

import java.util.Objects;

import co.edu.unbosque.util.MyDoubleLinkedList;

public class PlayerDTO {
	
	private String name;
	private String color;
	private String email;
	private String password;
	private MyDoubleLinkedList<String> territories;
	
	
	public PlayerDTO() {}

	public PlayerDTO(String name, String password, String color,String email) {
		this.name = name;
		this.color = color;
		this.password = password;
		this.email=email;
		this.territories = new MyDoubleLinkedList<>();
	}

	
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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


	public MyDoubleLinkedList<String> getTerritories() {
		return territories;
	}


	public void setTerritories(MyDoubleLinkedList<String> territories) {
		this.territories = territories;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean containsTerritory(String territory) {
		for(String aux: territories) {
			if(aux.equals(territory))return true;
		}
		return false;
	}
	
	public void removeTerritory(String terrytory) {
		for (int i = 0; i < territories.getSize(); i++) {
			if(terrytory.equals(territories.get(i))) {
				territories.remove(i);
				return;
			}
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(color, email, name, territories);
	}
	
}
