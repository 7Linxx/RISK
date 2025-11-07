package co.edu.unbosque.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Player implements Serializable {
    private String id;
    private String name;
    private String color;
    private int availableReinforcements;
    private List<Territory> territories = new ArrayList<>();

    public Player() {}
    public Player(String id, String name, String color) {
        this.id = id;
        this.name = name;
        this.color = color;
    }

    // getters/setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public int getAvailableReinforcements() { return availableReinforcements; }
    public void setAvailableReinforcements(int availableReinforcements) { this.availableReinforcements = availableReinforcements; }

    public List<Territory> getTerritories() { return territories; }
    public void setTerritories(List<Territory> territories) { this.territories = territories; }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
			return true;
		}
        if (o == null || getClass() != o.getClass()) {
			return false;
		}

        Player player = (Player) o;
        return Objects.equals(id, player.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}