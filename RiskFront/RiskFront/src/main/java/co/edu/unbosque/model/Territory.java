package co.edu.unbosque.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Territory implements Serializable {
    private String id;
    private String name;
    private Player owner;
    private int armies = 0;
    private List<String> neighbors = new ArrayList<>();

    public Territory() {}
    public Territory(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // getters/setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Player getOwner() { return owner; }
    public void setOwner(Player owner) { this.owner = owner; }

    public int getArmies() { return armies; }
    public void setArmies(int armies) { this.armies = armies; }

    public List<String> getNeighbors() { return neighbors; }
    public void setNeighbors(List<String> neighbors) { this.neighbors = neighbors; }
}