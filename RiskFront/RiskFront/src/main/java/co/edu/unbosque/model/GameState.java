package co.edu.unbosque.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GameState implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Player> players = new ArrayList<>();
    private Map<String, Territory> territories = new LinkedHashMap<>();
    private int currentPlayerIndex = 0;

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public void nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % Math.max(1, players.size());
    }

    // Very simple reinforcement calculation: max(3, territories/3)
    public int calculateReinforcements(Player p) {
        int base = Math.max(3, p.getTerritories().size() / 3);
        return base;
    }

    // getters/setters
    public List<Player> getPlayers() {
        return players;
    }

    public Map<String, Territory> getTerritories() {
        return territories;
    }

    public int getCurrentPlayerIndex() {
        return currentPlayerIndex;
    }

    public void setCurrentPlayerIndex(int currentPlayerIndex) {
        this.currentPlayerIndex = currentPlayerIndex;
    }
}