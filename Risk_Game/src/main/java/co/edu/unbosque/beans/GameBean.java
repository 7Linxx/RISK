package co.edu.unbosque.beans;

import java.io.Serializable;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

import co.edu.unbosque.model.PlayerDTO;
import co.edu.unbosque.model.TerritoryDTO;
import co.edu.unbosque.model.persistence.JugadorDAO;
import co.edu.unbosque.model.persistence.TerritoryDAO;
import co.edu.unbosque.util.MyMap;
import co.edu.unbosque.util.MyDoubleLinkedList;

@Named
@ViewScoped
public class GameBean implements Serializable {

	private JugadorDAO jugadorrDao;
	private TerritoryDAO territoryDao;

	private String phase;
	private int selectedPlayer;
	private String hashCode;

	private TerritoryDTO selectedTerritory;
	private boolean reforzed;
	private int troopsObtained;

	public GameBean() {
		jugadorrDao = new JugadorDAO();
		territoryDao = new TerritoryDAO();
		phase = "Phase 1: Reinforce";
	}

	// =============================
	// SAVE GAME
	// =============================
	public void saveGame() {
		if (phase.equals("Phase 2: Dices")) {
			warn("It is not possible to save while rolling dice.");
			return;
		}
		if (phase.equals("Winner")) {
			info("Game over", "You cannot save a finished game.");
			return;
		}
	}

	public void warn(String msg) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning", msg));
	}

	public void info(String sum, String msg) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, sum, msg));
	}

	public JugadorDAO getJugadorrDao() {
		return jugadorrDao;
	}

	public void setJugadorrDao(JugadorDAO jugadorrDao) {
		this.jugadorrDao = jugadorrDao;
	}

	public TerritoryDAO getTerritoryDao() {
		return territoryDao;
	}

	public void setTerritoryDao(TerritoryDAO territoryDao) {
		this.territoryDao = territoryDao;
	}

	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

	public int getSelectedPlayer() {
		return selectedPlayer;
	}

	public void setSelectedPlayer(int selectedPlayer) {
		this.selectedPlayer = selectedPlayer;
	}

	public String getHashCode() {
		return hashCode;
	}

	public void setHashCode(String hashCode) {
		this.hashCode = hashCode;
	}

	public TerritoryDTO getSelectedTerritory() {
		return selectedTerritory;
	}

	public void setSelectedTerritory(TerritoryDTO selectedTerritory) {
		this.selectedTerritory = selectedTerritory;
	}

	public boolean isReforzed() {
		return reforzed;
	}

	public void setReforzed(boolean reforzed) {
		this.reforzed = reforzed;
	}

	public int getTroopsObtained() {
		return troopsObtained;
	}

	public void setTroopsObtained(int troopsObtained) {
		this.troopsObtained = troopsObtained;
	}

	// GETTERS Y SETTERS …

}
