package co.edu.unbosque.bean;

import co.edu.unbosque.model.*;
import co.edu.unbosque.service.*;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.*;

@Named("gameBean")
@SessionScoped
public class GameBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private GameState gameState;
    private List<String> log;
    private String selectedTerritory;          // value bound from hidden input when map clicked
    private String lastSelectedTerritory;      // for two-step actions (attack: select from, then to)
    private Phase phase = Phase.REINFORCE;

    public enum Phase { REINFORCE, ATTACK, FORTIFY }

    @PostConstruct
    public void init() {
        log = new ArrayList<>();
        gameState = new GameState();

        // Create simple demo players
        Player p1 = new Player("p1", "Jugador 1", "#c0392b");
        Player p2 = new Player("p2", "Jugador 2", "#2980b9");

        gameState.getPlayers().add(p1);
        gameState.getPlayers().add(p2);

        // Create demo territories and adjacency
        Territory t1 = new Territory("arg", "Argentina");
        Territory t2 = new Territory("bra", "Brazil");
        Territory t3 = new Territory("chi", "Chile");
        Territory t4 = new Territory("per", "Peru");

        // simple neighbors
        t1.getNeighbors().addAll(Arrays.asList("bra", "chi"));
        t2.getNeighbors().addAll(Arrays.asList("arg", "per"));
        t3.getNeighbors().addAll(Arrays.asList("arg", "per"));
        t4.getNeighbors().addAll(Arrays.asList("bra", "chi"));

        // assign initial owners and armies (simple distribution)
        t1.setOwner(p1); t1.setArmies(3); p1.getTerritories().add(t1);
        t2.setOwner(p2); t2.setArmies(3); p2.getTerritories().add(t2);
        t3.setOwner(p1); t3.setArmies(2); p1.getTerritories().add(t3);
        t4.setOwner(p2); t4.setArmies(2); p2.getTerritories().add(t4);

        gameState.getTerritories().put(t1.getId(), t1);
        gameState.getTerritories().put(t2.getId(), t2);
        gameState.getTerritories().put(t3.getId(), t3);
        gameState.getTerritories().put(t4.getId(), t4);

        // initial reinforcements
        gameState.getCurrentPlayer().setAvailableReinforcements(gameState.calculateReinforcements(gameState.getCurrentPlayer()));

        addLog("Juego iniciado. Turno: " + gameState.getCurrentPlayer().getName());
    }

    public void reinforcePhase() {
        phase = Phase.REINFORCE;
        addLog("Fase: Reforzar. Tropas disponibles: " + gameState.getCurrentPlayer().getAvailableReinforcements());
    }

    public void attackPhase() {
        phase = Phase.ATTACK;
        addLog("Fase: Atacar. Selecciona territorio origen y luego destino (vecino).");
    }

    public void endTurn() {
        // reset selection
        lastSelectedTerritory = null;
        selectedTerritory = null;

        gameState.nextPlayer();
        // recalc reinforcements for new player
        gameState.getCurrentPlayer().setAvailableReinforcements(gameState.calculateReinforcements(gameState.getCurrentPlayer()));
        phase = Phase.REINFORCE;
        addLog("Turno finalizado. Turno ahora: " + gameState.getCurrentPlayer().getName());
    }

    public String onTerritorySelected() {
        if (selectedTerritory == null || selectedTerritory.isEmpty()) return null;
        String id = selectedTerritory;
        addLog("Click en: " + id);
        Player current = gameState.getCurrentPlayer();
        Territory t = gameState.getTerritories().get(id);
        if (t == null) {
            addLog("Territorio desconocido: " + id);
            selectedTerritory = null;
            return null;
        }

        switch (phase) {
            case REINFORCE:
                // if the territory belongs to current player and has reinforcements available
                if (current.equals(t.getOwner())) {
                    if (current.getAvailableReinforcements() > 0) {
                        t.setArmies(t.getArmies() + 1);
                        current.setAvailableReinforcements(current.getAvailableReinforcements() - 1);
                        addLog("Reforzaste " + t.getName() + ". Tropas ahora: " + t.getArmies());
                    } else {
                        addLog("No tienes refuerzos disponibles.");
                    }
                } else {
                    addLog("No puedes reforzar un territorio enemigo.");
                }
                break;

            case ATTACK:
                // two-step: first click select origin (must be owned and have >1 army), second click selects target neighbor not owned
                if (lastSelectedTerritory == null) {
                    // select origin
                    if (!current.equals(t.getOwner())) {
                        addLog("Selecciona un territorio propio con >1 tropas para atacar.");
                    } else if (t.getArmies() < 2) {
                        addLog("Tropas insuficientes en " + t.getName() + " (debe quedar >=2).");
                    } else {
                        lastSelectedTerritory = id;
                        addLog("Origen seleccionado: " + t.getName() + ". Elige objetivo.");
                    }
                } else {
                    // select destination
                    Territory from = gameState.getTerritories().get(lastSelectedTerritory);
                    Territory to = t;
                    // validate adjacency and ownership
                    if (from.getNeighbors().contains(to.getId()) && !current.equals(to.getOwner())) {
                        addLog("Atacando desde " + from.getName() + " a " + to.getName() + " ...");
                        // dice: attacker up to 3 (max armies-1), defender up to 2
                        int attDice = Math.min(3, from.getArmies() - 1);
                        int defDice = Math.min(2, to.getArmies());
                        CombatService.Result res = CombatService.resolveAttack(attDice, defDice);
                        // apply losses
                        from.setArmies(from.getArmies() - res.attackerLosses);
                        to.setArmies(to.getArmies() - res.defenderLosses);
                        addLog("Resultado: atacante pierde " + res.attackerLosses + ", defensor pierde " + res.defenderLosses);
                        // capture?
                        if (to.getArmies() <= 0) {
                            addLog(current.getName() + " conquistó " + to.getName());
                            // transfer ownership
                            if (to.getOwner() != null) {
                                to.getOwner().getTerritories().remove(to);
                            }
                            to.setOwner(current);
                            current.getTerritories().add(to);
                            // move at least one army from from to to (simplified move 1)
                            int move = Math.max(1, attDice - res.attackerLosses);
                            move = Math.min(move, from.getArmies() - 1);
                            if (move < 1) move = 1;
                            from.setArmies(from.getArmies() - move);
                            to.setArmies(move);
                            addLog("Moviste " + move + " tropas a " + to.getName());
                        }
                    } else {
                        addLog("Objetivo inválido (no es vecino o es propio).");
                    }
                    lastSelectedTerritory = null;
                }
                break;

            case FORTIFY:
                addLog("Fase de fortificar no implementada en esta versión.");
                break;
        }

        // clear selectedTerritory to avoid double processing
        selectedTerritory = null;
        return null;
    }

    public String saveGame() {
        // Placeholder: persist to DB or file
        addLog("Guardado (simulado).");
        return null;
    }

    private void addLog(String line) {
        log.add(0, "[" + new Date() + "] " + line);
        if (log.size() > 200) log = log.subList(0, 200);
    }

    // getters/setters

    public GameState getGameState() {
        return gameState;
    }

    public List<String> getLog() {
        return log;
    }

    public String getSelectedTerritory() {
        return selectedTerritory;
    }

    public void setSelectedTerritory(String selectedTerritory) {
        this.selectedTerritory = selectedTerritory;
    }

    public String getLastSelectedTerritory() {
        return lastSelectedTerritory;
    }

    public Phase getPhase() {
        return phase;
    }
}