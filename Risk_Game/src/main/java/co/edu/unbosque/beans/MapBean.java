package co.edu.unbosque.beans;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.itextpdf.text.log.SysoCounter;

import co.edu.unbosque.controller.HttpClientSynchronous;
import co.edu.unbosque.model.PlayerDTO;
import co.edu.unbosque.model.TerritoryDTO;
import co.edu.unbosque.model.persistence.FileHandler;
import co.edu.unbosque.model.persistence.PlayerDAO;
import co.edu.unbosque.model.persistence.TerritoryDAO;
import co.edu.unbosque.util.MailSender;
import co.edu.unbosque.util.MyDoubleLinkedList;
import co.edu.unbosque.util.MyGraph;
import co.edu.unbosque.util.MyMap;
import co.edu.unbosque.util.QueueImp;
import co.edu.unbosque.util.algorithm.BreadthFirstSearch;
import jakarta.annotation.ManagedBean;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

@ManagedBean
@RequestScoped
public class MapBean {

	private TerritoryDAO tDao;
	private PlayerDAO pDao;
	private String[] territories;
	private TerritoryDTO selectedTerritory, targetTerritory;
	private int selectedPlayer;
	private String phase, textWinner, textLosser;
	private boolean error1, error2, reforzed;
	private int index = 0, assignTroops, troopsObtained, troopsCommand, numDice, maxDice, numEDice, winner;
	private int selectedDices, selectedEDices, maxTroops, minTroops;
	private MyGraph<String> map;
	private MyDoubleLinkedList<String> enemiesTarget, alliesTarget;
	private String enemyPlayer, currentPlayer;
	private String colors[];
	private String names[];
	private String emails[];
	private String passwords[];
	private int[] disesA, disesE;
	private int numPlayers;
	private String hashCode;
	private String[] hashCodes;
	private int sizeHashes;
	private StreamedContent file;

	public MapBean() {
		tDao = new TerritoryDAO();
		pDao = new PlayerDAO();
		map = new MyGraph<>();
		disesA = new int[3];
		disesE = new int[2];
		colors = new String[4];
		names = new String[4];
		emails = new String[4];
		passwords = new String[4];
		reforzed = false;
		territories = new String[] { "Alaska", "Northwest_Territory", "Alberta", "Ontario", "Western_United_States",
				"Quebec", "Eastern_United_States", "Central_America", "Venezuela", "Peru", "Brazil", "Argentina",
				"Greenland", "Iceland", "Great_Britain", "Northern_Europe", "Scandinavia", "Western_Europe",
				"Southern_Europe", "Ukraine", "Ural", "Middle_East", "Afghanistan", "China", "India", "Siam", "Siberia",
				"Mongolia", "Irkutsk", "Yakursk", "Kamchatka", "Japan", "Indonesia", "New_Guinea", "Eastern_Australia",
				"Western_Australia", "North_Africa", "Congo", "Egypt", "East_Africa", "South_Africa", "Madagascar" };
		for (String aux : territories) {
			map.addNode(aux);
		}
		initGraph();
	}

	public TerritoryDAO gettDao() {
		return tDao;
	}

	public void settDao(TerritoryDAO tDao) {
		this.tDao = tDao;
	}

	public PlayerDAO getpDao() {
		return pDao;
	}

	public void setpDao(PlayerDAO pDao) {
		this.pDao = pDao;
	}

	public String[] getTerritories() {
		return territories;
	}

	public void setTerritories(String[] territories) {
		this.territories = territories;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public TerritoryDTO getSelectedTerritory() {
		return selectedTerritory;
	}

	public void setSelectedTerritory(TerritoryDTO selectedTerritory) {
		this.selectedTerritory = selectedTerritory;
	}

	public int getSelectedPlayer() {
		return selectedPlayer;
	}

	public void setSelectedPlayer(int selectedPlayer) {
		this.selectedPlayer = selectedPlayer;
	}

	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

	public boolean isError1() {
		return error1;
	}

	public void setError1(boolean error1) {
		this.error1 = error1;
	}

	public int getTroopsObtained() {
		return troopsObtained;
	}

	public void setTroopsObtained(int troopsObtained) {
		this.troopsObtained = troopsObtained;
	}

	public String[] getPasswords() {
		return passwords;
	}

	public void setPasswords(String[] passwords) {
		this.passwords = passwords;
	}

	public int getAssignTroops() {
		assignTroops = tDao.getTerrritories().getValue(territories[index]).getNumberTroops();
		index++;
		if (index == territories.length) {
			index = 0;
		}
		return assignTroops;
	}

	public void setNumberTroops(int numberTroops) {
		this.assignTroops = numberTroops;
	}

	public int getTroopsCommand() {
		return troopsCommand;
	}

	public void setTroopsCommand(int troopsCommand) {
		this.troopsCommand = troopsCommand;
	}

	public TerritoryDTO getTargetTerritory() {
		return targetTerritory;
	}

	public void setTargetTerritory(TerritoryDTO targetTerritory) {
		this.targetTerritory = targetTerritory;
	}

	public MyGraph<String> getMap() {
		return map;
	}

	public void setMap(MyGraph<String> map) {
		this.map = map;
	}

	public MyDoubleLinkedList<String> getEnemiesTarget() {
		return enemiesTarget;
	}

	public void setEnemiesTarget(MyDoubleLinkedList<String> enemiesTarget) {
		this.enemiesTarget = enemiesTarget;
	}

	public void setAssignTroops(int assignTroops) {
		this.assignTroops = assignTroops;
	}

	public boolean isError2() {
		return error2;
	}

	public void setError2(boolean error2) {
		this.error2 = error2;
	}

	public int getNumDice() {
		return numDice;
	}

	public void setNumDice(int numDice) {
		this.numDice = numDice;
	}

	public int getMaxDice() {
		return maxDice;
	}

	public void setMaxDice(int maxDice) {
		this.maxDice = maxDice;
	}

	public int getNumEDice() {
		return numEDice;
	}

	public void setNumEDice(int numEDice) {
		this.numEDice = numEDice;
	}

	public int[] getDisesA() {
		return disesA;
	}

	public void setDisesA(int[] disesA) {
		this.disesA = disesA;
	}

	public int[] getDisesE() {
		return disesE;
	}

	public void setDisesE(int[] disesE) {
		this.disesE = disesE;
	}

	public String getTextWinner() {
		return textWinner;
	}

	public void setTextWinner(String textWinner) {
		this.textWinner = textWinner;
	}

	public int getWinner() {
		return winner;
	}

	public void setWinner(int winner) {
		this.winner = winner;
	}

	public String getEnemyPlayer() {
		return enemyPlayer;
	}

	public void setEnemyPlayer(String enemyPlayer) {
		this.enemyPlayer = enemyPlayer;
	}

	public String getTextLosser() {
		return textLosser;
	}

	public void setTextLosser(String textLosser) {
		this.textLosser = textLosser;
	}

	public int getSelectedDices() {
		return selectedDices;
	}

	public void setSelectedDices(int selectedDices) {
		this.selectedDices = selectedDices;
	}

	public int getSelectedEDices() {
		return selectedEDices;
	}

	public void setSelectedEDices(int selectedEDices) {
		this.selectedEDices = selectedEDices;
	}

	public int getMaxTroops() {
		return maxTroops;
	}

	public void setMaxTroops(int maxTroops) {
		this.maxTroops = maxTroops;
	}

	public int getMinTroops() {
		return minTroops;
	}

	public void setMinTroops(int minTroops) {
		this.minTroops = minTroops;
	}

	public boolean isReforzed() {
		return reforzed;
	}

	public void setReforzed(boolean reforzed) {
		this.reforzed = reforzed;
	}

	public MyDoubleLinkedList<String> getAlliesTarget() {
		return alliesTarget;
	}

	public void setAlliesTarget(MyDoubleLinkedList<String> alliesTarget) {
		this.alliesTarget = alliesTarget;
	}

	public String getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(String currentPlayer) {
		this.currentPlayer = currentPlayer;
	}

	public int getNumPlayers() {
		return numPlayers;
	}

	public void setNumPlayers(int numPlayers) {
		this.numPlayers = numPlayers;
	}

	public String[] getColors() {
		return colors;
	}

	public void setColors(String[] colors) {
		this.colors = colors;
	}

	public String[] getNames() {
		return names;
	}

	public void setNames(String[] names) {
		this.names = names;
	}

	public String[] getEmails() {
		return emails;
	}

	public void setEmails(String[] emails) {
		this.emails = emails;
	}

	public String getHashCode() {
		return hashCode;
	}

	public void setHashCode(String hashCode) {
		this.hashCode = hashCode;
	}

	public String[] getHashCodes() {
		return hashCodes;
	}

	public void setHashCodes(String[] hashCodes) {
		this.hashCodes = hashCodes;
	}

	public int getSizeHashes() {
		return sizeHashes;
	}

	public void setSizeHashes(int sizeHashes) {
		this.sizeHashes = sizeHashes;
	}

	public StreamedContent getFile() {
		file = DefaultStreamedContent
				.builder().name("GameDetails.zip").contentType("application/zip").stream(() -> FacesContext
						.getCurrentInstance().getExternalContext().getResourceAsStream("/files/GameDetails.zip"))
				.build();
		return file;
	}

	public void setFile(StreamedContent file) {
		this.file = file;
	}

	public String init() {
		if (!initPlayers()) {
			return null;
		}
		Random r = new Random();
		for (String territory : territories) {
			int selectedPlayer = r.nextInt(pDao.getPlayers().getSize());
			tDao.create(
					new TerritoryDTO(territory, pDao.getPlayers().get(selectedPlayer).getColor(), r.nextInt(3) + 1));
			pDao.getPlayers().get(selectedPlayer).getTerritories().add(territory);
		}
		hashCode = generarHash(Objects.hash(firstDigitHashCode(), tDao, pDao));
		initDetails();
		selectedPlayer = 0;
		phase = "Phase 1: Reinforce";
		receiveTroops();
		return "game.xhtml?faces-redirect=true";
	}

	public void initDetails() {
		troopsCommand = 0;
		textWinner = "";
		enemyPlayer = "";
		currentPlayer = pDao.getPlayers().get(selectedPlayer).getName();
		error1 = error2 = false;
		enemiesTarget = new MyDoubleLinkedList<>();
		alliesTarget = new MyDoubleLinkedList<>();
		selectedTerritory = null;
		targetTerritory = null;
	}

	public void initGraph() {
		map.addEdge("Alaska", "Northwest_Territory");
		map.addEdge("Alaska", "Alberta");
		map.addEdge("Northwest_Territory", "Alberta");
		map.addEdge("Northwest_Territory", "Ontario");
		map.addEdge("Northwest_Territory", "Greenland");
		map.addEdge("Alberta", "Ontario");
		map.addEdge("Alberta", "Western_United_States");
		map.addEdge("Ontario", "Western_United_States");
		map.addEdge("Ontario", "Eastern_United_States");
		map.addEdge("Ontario", "Quebec");
		map.addEdge("Ontario", "Greenland");
		map.addEdge("Quebec", "Greenland");
		map.addEdge("Quebec", "Eastern_United_States");
		map.addEdge("Western_United_States", "Eastern_United_States");
		map.addEdge("Western_United_States", "Central_America");
		map.addEdge("Eastern_United_States", "Central_America");
		map.addEdge("Central_America", "Venezuela");
		map.addEdge("Venezuela", "Peru");
		map.addEdge("Venezuela", "Brazil");
		map.addEdge("Peru", "Argentina");
		map.addEdge("Peru", "Brazil");
		map.addEdge("Brazil", "Argentina");
		map.addEdge("Brazil", "North_Africa");
		map.addEdge("Greenland", "Iceland");
		map.addEdge("Iceland", "Great_Britain");
		map.addEdge("Iceland", "Scandinavia");
		map.addEdge("Great_Britain", "Scandinavia");
		map.addEdge("Great_Britain", "Western_Europe");
		map.addEdge("Great_Britain", "Northern_Europe");
		map.addEdge("Western_Europe", "Northern_Europe");
		map.addEdge("Western_Europe", "Southern_Europe");
		map.addEdge("Western_Europe", "North_Africa");
		map.addEdge("Northern_Europe", "Southern_Europe");
		map.addEdge("Northern_Europe", "Ukraine");
		map.addEdge("Northern_Europe", "Scandinavia");
		map.addEdge("Southern_Europe", "North_Africa");
		map.addEdge("Southern_Europe", "Middle_East");
		map.addEdge("Southern_Europe", "Ukraine");
		map.addEdge("Scandinavia", "Ukraine");
		map.addEdge("Middle_East", "Egypt");
		map.addEdge("Middle_East", "Ukraine");
		map.addEdge("Middle_East", "Afghanistan");
		map.addEdge("Middle_East", "India");
		map.addEdge("Ukraine", "Afghanistan");
		map.addEdge("Ukraine", "Ural");
		map.addEdge("India", "Afghanistan");
		map.addEdge("India", "China");
		map.addEdge("India", "Siam");
		map.addEdge("Afghanistan", "China");
		map.addEdge("Afghanistan", "Ural");
		map.addEdge("Ural", "China");
		map.addEdge("Ural", "Siberia");
		map.addEdge("Siam", "China");
		map.addEdge("Siam", "Indonesia");
		map.addEdge("China", "Siberia");
		map.addEdge("China", "Mongolia");
		map.addEdge("Siberia", "Mongolia");
		map.addEdge("Siberia", "Irkutsk");
		map.addEdge("Siberia", "Yakursk");
		map.addEdge("Mongolia", "Japan");
		map.addEdge("Mongolia", "Kamchatka");
		map.addEdge("Mongolia", "Irkutsk");
		map.addEdge("Irkutsk", "Kamchatka");
		map.addEdge("Irkutsk", "Yakursk");
		map.addEdge("Yakursk", "Kamchatka");
		map.addEdge("Kamchatka", "Japan");
		map.addEdge("Indonesia", "Western_Australia");
		map.addEdge("Indonesia", "New_Guinea");
		map.addEdge("New_Guinea", "Western_Australia");
		map.addEdge("New_Guinea", "Eastern_Australia");
		map.addEdge("Western_Australia", "Eastern_Australia");
		map.addEdge("North_Africa", "Egypt");
		map.addEdge("North_Africa", "Congo");
		map.addEdge("North_Africa", "East_Africa");
		map.addEdge("Egypt", "East_Africa");
		map.addEdge("Congo", "East_Africa");
		map.addEdge("Congo", "South_Africa");
		map.addEdge("East_Africa", "Madagascar");
		map.addEdge("East_Africa", "South_Africa");
		map.addEdge("South_Africa", "Madagascar");
	}

	public String obtainColor(String territory) {
		if (tDao.getTerrritories().containsKey(territory)) {
			return tDao.getTerrritories().getValue(territory).getColor();
		}
		return "#e6e6e6";
	}

	public void changeTerritory(String territory) {
		if (phase.equals("Phase 1: Reinforce")) {
			if (selectedTerritory != null) {
				tDao.getTerrritories().getValue(selectedTerritory.getName())
						.setColor(playerTerritory(selectedTerritory.getName()).getColor());
			}
			if (troopsObtained == 0)
				return;
			if (pDao.getPlayers().get(selectedPlayer).containsTerritory(territory)) {
				selectedTerritory = tDao.getTerrritories().getValue(territory);
				tDao.getTerrritories().getValue(territory).setColor("#75FA61");
				error1 = false;
			} else {
				error1 = true;
			}
		} else if (phase.equals("Phase 2: Attack")) {
			if (pDao.getPlayers().get(selectedPlayer).containsTerritory(territory)
					&& tDao.getTerrritories().getValue(territory).getNumberTroops() <= 1) {
				error1 = true;
				offTargetEnemies();
				selectedTerritory = null;
				targetTerritory = null;
			} else if (pDao.getPlayers().get(selectedPlayer).containsTerritory(territory)
					&& tDao.getTerrritories().getValue(territory).getNumberTroops() > 1) {
				offTargetEnemies();
				selectedTerritory = tDao.getTerrritories().getValue(territory);
				tDao.getTerrritories().getValue(territory).setColor("#75FA61");
				targetEnemies(territory);
				error1 = false;
				if (enemiesTarget.getSize() == 0) {
					error2 = true;
				} else
					error2 = false;
				targetTerritory = null;
			} else if (enemiesTarget.getSize() > 0 && isTargetEnemy(territory)) {
				targetTerritory = tDao.getTerrritories().getValue(territory);
				enemyPlayer = playerTerritory(territory).getName();
				offTargetEnemies(territory);
				calcNumDices();
			}
		} else if (phase.equals("Phase 3: Strengthen")) {
			if (reforzed) {
				return;
			}
			if (selectedTerritory == null && pDao.getPlayers().get(selectedPlayer).containsTerritory(territory)
					&& tDao.getTerrritories().getValue(territory).getNumberTroops() <= 1) {
				error2 = true;
				error1 = false;
				targetTerritory = null;
			} else if (selectedTerritory == null && pDao.getPlayers().get(selectedPlayer).containsTerritory(territory)
					&& tDao.getTerrritories().getValue(territory).getNumberTroops() > 1) {
				selectedTerritory = tDao.getTerrritories().getValue(territory);
				tDao.getTerrritories().getValue(territory).setColor("#75FA61");
				targetTerritory = null;
				targetAllies(territory);
				error1 = false;
				error2 = false;

			} else if (selectedTerritory != null && targetTerritory != null
					&& pDao.getPlayers().get(selectedPlayer).containsTerritory(territory)
					&& tDao.getTerrritories().getValue(territory).getNumberTroops() > 1) {
				tDao.getTerrritories().getValue(selectedTerritory.getName())
						.setColor(pDao.getPlayers().get(selectedPlayer).getColor());
				selectedTerritory = tDao.getTerrritories().getValue(territory);
				maxTroops = selectedTerritory.getNumberTroops() - 1;
				targetTerritory = null;
				offTargetAllies();
				tDao.getTerrritories().getValue(territory).setColor("#75FA61");
				targetAllies(territory);
				error1 = false;
				error2 = false;

			} else if (selectedTerritory != null && isTargetAllie(territory)) {
				targetTerritory = tDao.getTerrritories().getValue(territory);
				maxTroops = selectedTerritory.getNumberTroops() - 1;
				offTargetAllies(territory);
				error1 = false;
				error2 = false;
			} else if (selectedTerritory != null && pDao.getPlayers().get(selectedPlayer).containsTerritory(territory)
					&& !isTargetAllie(territory) && tDao.getTerrritories().getValue(territory).getNumberTroops() > 1) {
				tDao.getTerrritories().getValue(selectedTerritory.getName())
						.setColor(pDao.getPlayers().get(selectedPlayer).getColor());
				selectedTerritory = tDao.getTerrritories().getValue(territory);
				maxTroops = selectedTerritory.getNumberTroops() - 1;
				targetTerritory = null;
				offTargetAllies();
				tDao.getTerrritories().getValue(territory).setColor("#75FA61");
				targetAllies(territory);
				error1 = false;
				error2 = false;

			} else if (selectedTerritory != null && pDao.getPlayers().get(selectedPlayer).containsTerritory(territory)
					&& !isTargetAllie(territory) && tDao.getTerrritories().getValue(territory).getNumberTroops() <= 1) {
				tDao.getTerrritories().getValue(selectedTerritory.getName())
						.setColor(pDao.getPlayers().get(selectedPlayer).getColor());
				selectedTerritory = null;
				targetTerritory = null;
				offTargetAllies();
				error2 = true;
				error1 = false;
			} else {
				offTargetAllies();
				selectedTerritory = null;
				targetTerritory = null;
				error1 = true;
				error2 = false;
			}
		}
	}

	public void reinforceTerritory() {
		int number = tDao.getTerrritories().getValue(selectedTerritory.getName()).getNumberTroops() + troopsCommand;
		tDao.getTerrritories().getValue(selectedTerritory.getName()).setNumberTroops(number);
		troopsObtained -= troopsCommand;
		tDao.getTerrritories().getValue(selectedTerritory.getName())
				.setColor(playerTerritory(selectedTerritory.getName()).getColor());
		selectedTerritory = null;
		troopsCommand = 0;
	}

	public PlayerDTO playerTerritory(String territory) {
		for (int i = 0; i < pDao.getPlayers().getSize(); i++) {
			if (pDao.getPlayers().get(i).containsTerritory(territory)) {
				return pDao.getPlayers().get(i);
			}
		}
		return null;
	}

	public void changePhase() {
		if (phase.equals("Phase 1: Reinforce")) {
			error1 = false;
			error2 = false;
			phase = "Phase 2: Attack";
			selectedTerritory = null;
		} else if (phase.equals("Phase 2: Attack")) {
			error1 = false;
			error2 = false;
			reforzed = false;
			offTargetEnemies();
			selectedTerritory = null;
			targetTerritory = null;
			phase = "Phase 3: Strengthen";
		} else if (phase.equals("Phase 3: Strengthen")) {
			error1 = false;
			error2 = false;
			offTargetAllies();
			passTurn();
			receiveTroops();
			currentPlayer = pDao.getPlayers().get(selectedPlayer).getName();
			selectedTerritory = null;
			targetTerritory = null;
			textWinner = "";
			winner = 0;
			selectedDices = 0;
			selectedEDices = 0;
			phase = "Phase 1: Reinforce";
		}
	}

	public void phaseDices() {
		if (phase.equals("Phase 2: Attack")) {
			phase = "Phase 2: Dices";
		} else if (phase.equals("Phase 2: Dices") && winner != 1) {
			phase = "Phase 2: Attack";
			offTargetEnemies();
			selectedTerritory = null;
			targetTerritory = null;
		} else {
			addAfterConquist(targetTerritory.getName());
			if (pDao.getPlayers().get(selectedPlayer).getTerritories().getSize() == territories.length) {
				phase = "Winner";
				generateFileResults();
				return;
			}
			maxTroops = selectedTerritory.getNumberTroops() - 1;
			phase = "Phase 2: Conquest";
		}
	}

	public void targetEnemies(String territory) {
		enemiesTarget = new MyDoubleLinkedList<>();
		BreadthFirstSearch<String> bfs = new BreadthFirstSearch<String>(map, territory);
		bfs.runSearch();
		for (String aux : territories) {
			if (!pDao.getPlayers().get(selectedPlayer).containsTerritory(aux)) {
				if (bfs.obtainDistance(aux) == 1) {
					tDao.getTerrritories().getValue(aux).setColor("#F79A9A");
					enemiesTarget.add(aux);
				}
			}
		}
	}

	public void offTargetEnemies() {
		if (selectedTerritory != null) {
			tDao.getTerrritories().getValue(selectedTerritory.getName())
					.setColor(playerTerritory(selectedTerritory.getName()).getColor());
		}
		for (String aux : enemiesTarget) {
			tDao.getTerrritories().getValue(aux).setColor(playerTerritory(aux).getColor());
		}
		enemiesTarget = new MyDoubleLinkedList<>();
	}

	public void offTargetEnemies(String territory) {
		for (String aux : enemiesTarget) {
			if (aux.equals(territory)) {
				tDao.getTerrritories().getValue(aux).setColor("#F79A9A");
				continue;
			}
			tDao.getTerrritories().getValue(aux).setColor(playerTerritory(aux).getColor());
		}
	}

	public boolean isTargetEnemy(String territory) {
		for (String aux : enemiesTarget) {
			if (aux.equals(territory))
				return true;
		}
		return false;
	}

	public void calcNumDices() {
		if (selectedTerritory.getNumberTroops() - 1 >= 3)
			numDice = 3;
		else if (selectedTerritory.getNumberTroops() - 1 == 2)
			numDice = 2;
		else
			numDice = 1;

		if (targetTerritory.getNumberTroops() >= 2)
			numEDice = 2;
		else
			numEDice = 1;
		selectedDices = 0;
		selectedEDices = 0;
	}

	public void rollDice() {
		for (int i = 0; i < 3; i++) {
			if (i < selectedDices)
				disesA[i] = random();
			else
				disesA[i] = 0;
		}
		for (int i = 0; i < 2; i++) {
			if (i < selectedEDices)
				disesE[i] = random();
			else
				disesE[i] = 0;
		}
		textWinner = "";
		winner = 0;
	}

	public int random() {
		SecureRandom r = new SecureRandom();
		int val = r.nextInt(6) + 1;
		return val;
	}

	public void battleRound() {
		int[] restTroops = resTroops(disesA, disesE);
		int remainingATroops = selectedTerritory.getNumberTroops() - restTroops[0];
		int remainingBTroops = targetTerritory.getNumberTroops() - restTroops[1];
		tDao.getTerrritories().getValue(selectedTerritory.getName()).setNumberTroops(remainingATroops);
		tDao.getTerrritories().getValue(targetTerritory.getName()).setNumberTroops(remainingBTroops);
		selectedTerritory = tDao.getTerrritories().getValue(selectedTerritory.getName());
		targetTerritory = tDao.getTerrritories().getValue(targetTerritory.getName());
		if (targetTerritory.getNumberTroops() == 0) {
			textWinner = pDao.getPlayers().get(selectedPlayer).getName() + " lost " + restTroops[0] + " troops | "
					+ playerTerritory(targetTerritory.getName()).getName() + " lost " + restTroops[1] + " troops.";
			textLosser = pDao.getPlayers().get(selectedPlayer).getName() + " has conquered "
					+ targetTerritory.getName();
			;
			winner = 1;
			minTroops = selectedDices;
		} else if (selectedTerritory.getNumberTroops() == 1) {
			textWinner = "You can no longer attack from this territory, there is only one troop left.";
			textLosser = pDao.getPlayers().get(selectedPlayer).getName() + " lost " + restTroops[0] + " troops | "
					+ playerTerritory(targetTerritory.getName()).getName() + " lost " + restTroops[1] + " troops";
			winner = 2;
		} else {
			textWinner = pDao.getPlayers().get(selectedPlayer).getName() + " lost " + restTroops[0] + " troops.";
			textLosser = playerTerritory(targetTerritory.getName()).getName() + " lost " + restTroops[1] + " troops.";
		}

	}

	public int[] resTroops(int[] arr, int[] arr2) {
		int resA = 0;
		int resB = 0;
		Arrays.sort(arr);
		Arrays.sort(arr2);
		for (int i = arr.length - 1, j = arr2.length - 1; i >= 0 && j >= 0; i--, j--) {
			if (arr[i] == 0 || arr2[j] == 0)
				continue;
			if (arr2[j] >= arr[i])
				resA++;
			else if (arr2[j] < arr[i])
				resB++;
		}
		return new int[] { resA, resB };
	}

	public void resetBattleRound() {
		textWinner = "";
		calcNumDices();
	}

	public void addAfterConquist(String territory) {
		playerTerritory(territory).removeTerritory(territory);
		pDao.getPlayers().get(selectedPlayer).getTerritories().add(territory);
	}

	public void addTroopsAfterConquist() {
		int resTroops = selectedTerritory.getNumberTroops() - troopsCommand;
		tDao.getTerrritories().getValue(selectedTerritory.getName()).setNumberTroops(resTroops);
		tDao.getTerrritories().getValue(targetTerritory.getName()).setNumberTroops(troopsCommand);
		phase = "Phase 2: Attack";
		offTargetEnemies();
		selectedTerritory = null;
		targetTerritory = null;
	}

	public void targetAllies(String territory) {
		alliesTarget = new MyDoubleLinkedList<>();
		BreadthFirstSearch<String> bfs = new BreadthFirstSearch<String>(map, territory) {
			@Override
			public void runSearch() {
				init();
				int first = getGraph().nodeToNumber(getSource());
				QueueImp<Integer> queue = new QueueImp<>();
				getDis()[first] = 0;
				queue.enqueue(first);
				while (!queue.isEmpty()) {
					int u = queue.dequeue();
					for (int v : getGraph().getAdj().get(u)) {
						if (getDis()[v] == -1 && pDao.getPlayers().get(selectedPlayer)
								.containsTerritory(getGraph().numberToNode(v))) {
							getDis()[v] = getDis()[u] + 1;
							queue.enqueue(v);
						}
					}
				}
			}
		};
		bfs.runSearch();
		for (String aux : territories) {
			if (pDao.getPlayers().get(selectedPlayer).containsTerritory(aux)) {
				if (bfs.obtainDistance(aux) != -1 && !aux.equals(territory)) {
					tDao.getTerrritories().getValue(aux).setColor("#C0C0C0");
					alliesTarget.add(aux);
				}
			}
		}
	}

	public void offTargetAllies() {
		if (selectedTerritory != null) {
			tDao.getTerrritories().getValue(selectedTerritory.getName())
					.setColor(playerTerritory(selectedTerritory.getName()).getColor());
		}
		for (String aux : alliesTarget) {
			tDao.getTerrritories().getValue(aux).setColor(playerTerritory(aux).getColor());
		}
		alliesTarget = new MyDoubleLinkedList<>();
	}

	public void offTargetAllies(String territory) {
		for (String aux : alliesTarget) {
			if (aux.equals(territory)) {
				tDao.getTerrritories().getValue(aux).setColor("#C0C0C0");
				continue;
			}
			tDao.getTerrritories().getValue(aux).setColor(playerTerritory(aux).getColor());
		}
	}

	public boolean isTargetAllie(String territory) {
		for (String aux : alliesTarget) {
			if (aux.equals(territory))
				return true;
		}
		return false;
	}

	public void strengthenTerritory() {
		int restTroops = selectedTerritory.getNumberTroops() - troopsCommand;
		tDao.getTerrritories().getValue(selectedTerritory.getName()).setNumberTroops(restTroops);
		int receivedTroops = targetTerritory.getNumberTroops() + troopsCommand;
		tDao.getTerrritories().getValue(targetTerritory.getName()).setNumberTroops(receivedTroops);
		selectedTerritory = tDao.getTerrritories().getValue(selectedTerritory.getName());
		targetTerritory = tDao.getTerrritories().getValue(targetTerritory.getName());
		offTargetAllies();
		reforzed = true;
	}

	public void passTurn() {
		selectedPlayer++;
		if (selectedPlayer == pDao.getPlayers().getSize()) {
			selectedPlayer = 0;
		}
		while (pDao.getPlayers().get(selectedPlayer).getTerritories().getSize() == 0) {
			selectedPlayer++;
			if (selectedPlayer == pDao.getPlayers().getSize()) {
				selectedPlayer = 0;
			}
		}
	}

	public void receiveTroops() {
		troopsObtained = pDao.getPlayers().get(selectedPlayer).getTerritories().getSize() / 3;
		if (troopsObtained < 3) {
			troopsObtained = 3;
		}
	}

	public boolean initPlayers() {
		pDao = new PlayerDAO();
		boolean required = true;
		boolean auxName = true;
		MyMap<String, Boolean> vColor = new MyMap<>();
		MyMap<String, Boolean> vName = new MyMap<>();
		for (int i = 0; i < numPlayers; i++) {
			auxName = true;
			if (names[i].length() > 8) {
				addMessage(FacesMessage.SEVERITY_ERROR, "Invalid name player " + (i + 1),
						"The name should only have a maximum of 8 characters.");
				required = false;
				auxName = false;
			} else if (names[i].isEmpty()) {
				addMessage(FacesMessage.SEVERITY_ERROR, "Invalid name player " + (i + 1), "Please enter a name.");
				required = false;
				auxName = false;
			} else if (names[i].matches(".*[,;].*")) {
				addMessage(FacesMessage.SEVERITY_ERROR, "Invalid name player " + (i + 1),
						"Characters \",\" and \";\" are not allowed.");
				required = false;
			}
			if (vName.containsKey(names[i]) && auxName) {
				addMessage(FacesMessage.SEVERITY_ERROR, "Name already taked",
						"Please select other name player " + (i + 1) + ".");
				required = false;
			} else {
				vName.put(names[i], true);
			}
			if (!emails[i].matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")) {
				addMessage(FacesMessage.SEVERITY_ERROR, "Invalid email player " + (i + 1),
						"The entered email is not valid.");
				required = false;
			}
			if (colors[i].isEmpty()) {
				addMessage(FacesMessage.SEVERITY_ERROR, "Invalid color player " + (i + 1), "Please select a color.");
				required = false;
			}
			if (vColor.containsKey(colors[i]) && !colors[i].isEmpty()) {
				addMessage(FacesMessage.SEVERITY_ERROR, "Color already taked",
						"Please select other color player " + (i + 1) + ".");
				required = false;
			} else {
				vColor.put(colors[i], true);
			}
			pDao.getPlayers().add(new PlayerDTO(names[i], passwords[i], colors[i], emails[i]));
		}
		return required;
	}

	public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
	}

	public String firstDigitHashCode() {
		String val = "0";
		try {
			val = HttpClientSynchronous.doGet("http://localhost:8081/gamedetails/getlastid");
			Integer.parseInt(val);
		} catch (Exception e) {
			SecureRandom r = new SecureRandom();
			val = Integer.toString((r.nextInt(50) + 1));
		}
		return val;
	}

	public int playerIndex(String territory) {
		for (int i = 0; i < pDao.getPlayers().getSize(); i++) {
			if (pDao.getPlayers().get(i).containsTerritory(territory)) {
				return i;
			}
		}
		return 0;
	}

	public void saveGame() {
		if (phase.equals("Phase 2: Dices")) {
			addMessage(FacesMessage.SEVERITY_WARN, "Warning",
					"It is not possible to save while rolling dice. Select the back button to stop rolling dice and save.");
			return;
		}
		if (phase.equals("Phase 2: Conquest")) {
			addMessage(FacesMessage.SEVERITY_WARN, "Warning",
					"You cannot save while sending troops after you have conquered a territory. Please send troops to the conquered territory to save.");
			return;
		}
		if (phase.equals("Winner")) {
			addMessage(FacesMessage.SEVERITY_INFO, "The game is over.",
					"The game cannot be saved at the end of the game.");
			return;
		}
		if (phase.equals("Phase 1: Reinforce") && selectedTerritory != null) {
			tDao.getTerrritories().getValue(selectedTerritory.getName())
					.setColor(playerTerritory(selectedTerritory.getName()).getColor());
		} else if (phase.equals("Phase 3: Strengthen")) {
			offTargetAllies();
		} else if (phase.equals("Phase 2: Attack")) {
			offTargetEnemies();
		}
		String exist = HttpClientSynchronous.doGet("http://localhost:8081/gamedetails/existhash/" + hashCode);
		if (!exist.equals("No")) {
			HttpClientSynchronous.doDelete("http://localhost:8081/gamedetails/deletebyhash/" + hashCode);
			HttpClientSynchronous.doDelete("http://localhost:8081/player/deletebyhash/" + hashCode);
			HttpClientSynchronous.doDelete("http://localhost:8081/territory/deletebyhash/" + hashCode);
		}
		for (TerritoryDTO current : tDao.getTerrritories().values()) {
			HttpClientSynchronous.doPost("http://localhost:8081/territory/create",
					"{\"hashCode\": \"" + hashCode + "\",\"name\":\"" + current.getName() + "\",\"color\":\""
							+ current.getColor() + "\",\"numberTroops\": " + current.getNumberTroops()
							+ ",\"playerIndex\": " + playerIndex(current.getName()) + "}");
		}
		int i = 0;
		for (PlayerDTO current : pDao.getPlayers()) {
			HttpClientSynchronous.doPost("http://localhost:8081/player/create",
					"{\"hashCode\": \"" + hashCode + "\",\"name\": \"" + current.getName() + "\",\"color\": \""
							+ current.getColor() + "\",\"email\": \"" + current.getEmail() + "\",\"indexPlayer\": " + i
							+ "}");
			i++;
		}
		String gameInfo = "empty";
		if (phase.equals("Phase 1: Reinforce")) {
			gameInfo = Integer.toString(troopsObtained);
		} else if (phase.equals("Phase 3: Strengthen")) {
			gameInfo = reforzed ? "Yes" : "No";
		}
		HttpClientSynchronous.doPost("http://localhost:8081/gamedetails/create",
				"{\"hashCode\": \"" + hashCode + "\",\"phase\": \"" + phase + "\",\"playerTurn\": " + selectedPlayer
						+ ",\"gameInfo\": \"" + gameInfo + "\"}");

		addMessage(FacesMessage.SEVERITY_INFO, "Game saved.", "Game successfully saved.");
	}

	public void loadHashCodes() {
		hashCode = "";
		String jsHash = HttpClientSynchronous.doGet("http://localhost:8081/gamedetails/getallhashcode");
		if (jsHash.isEmpty()) {
			sizeHashes = 0;
			hashCodes = new String[0];
			return;
		}
		String[] aux = jsHash.split(",");
		hashCodes = new String[aux.length];
		sizeHashes = aux.length;
		for (int i = 0; i < hashCodes.length; i++) {
			hashCodes[i] = aux[i];
		}
	}

	public String loadGame() {
		String exist = HttpClientSynchronous.doGet("http://localhost:8081/gamedetails/existhash/" + hashCode);
		if (exist.equals("No")) {
			addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Please select game hashcode.");
			return null;
		}
		tDao = new TerritoryDAO();
		pDao = new PlayerDAO();
		MyMap<Integer, MyDoubleLinkedList<String>> pTerritory = new MyMap<>();
		String[] jsTerritories = HttpClientSynchronous.doGet("http://localhost:8081/territory/getbyhash/" + hashCode)
				.split(";");
		for (String object : jsTerritories) {
			String[] attributes = object.split(",");
			TerritoryDTO territory = new TerritoryDTO(attributes[1], attributes[2], Integer.parseInt(attributes[3]));
			int index = Integer.parseInt(attributes[4]);
			if (!pTerritory.containsKey(index)) {
				pTerritory.put(index, new MyDoubleLinkedList<>());
			}
			pTerritory.getValue(index).add(territory.getName());
			tDao.create(territory);
		}
		MyMap<Integer, PlayerDTO> pPlayers = new MyMap<>();
		String[] jsPlayers = HttpClientSynchronous.doGet("http://localhost:8081/player/getbyhash/" + hashCode)
				.split(";");
		for (String object : jsPlayers) {
			String[] attributes = object.split(",");
			PlayerDTO player = new PlayerDTO(attributes[1], attributes[2], attributes[3], attributes[4]);
			int index = Integer.parseInt(attributes[4]);
			if (pTerritory.containsKey(index)) {
				player.setTerritories(pTerritory.getValue(index));
			} else {
				player.setTerritories(new MyDoubleLinkedList<String>());
			}
			pPlayers.put(index, player);
		}
		for (int i = 0; i < jsPlayers.length; i++) {
			pDao.create(pPlayers.getValue(i));
		}
		String[] jsGameDetails = HttpClientSynchronous.doGet("http://localhost:8081/gamedetails/getbyhash/" + hashCode)
				.split(",");
		phase = jsGameDetails[1];
		selectedPlayer = Integer.parseInt(jsGameDetails[2]);
		if (phase.equals("Phase 1: Reinforce")) {
			troopsObtained = Integer.parseInt(jsGameDetails[3]);
		} else if (phase.equals("Phase 3: Strengthen")) {
			reforzed = jsGameDetails[3].equals("Yes");
		}
		initDetails();
		return "game.xhtml?faces-redirect=true";
	}

	public void generateFileResults() {
		String pathPdf = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/files/GameDetails.pdf");
		String pathZip = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/files/GameDetails.zip");
		String pathImg = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/images/risklogo.png");
		String header = "Game results";
		int totalTroops = 0;
		for (String aux : territories)
			totalTroops += tDao.getTerrritories().getValue(aux).getNumberTroops();
		String content = "Thank you very much for playing the game. Congratulations "
				+ pDao.getPlayers().get(selectedPlayer).getName() + " you won the game with a total of " + totalTroops
				+ " troops. Thanks also to the other players you played very well.";
		String[] lplayers = new String[pDao.getPlayers().getSize() + 1];
		lplayers[0] = "Players List:";
		for (int i = 0; i < pDao.getPlayers().getSize(); i++) {
			lplayers[i + 1] = pDao.getPlayers().get(i).getName() + ". " + (i == selectedPlayer ? "Winner." : "Loser");
		}
		String content2 = "Game hashcode: " + hashCode;
		FileHandler.generateZIP(pathPdf, pathZip, pathImg, header, content, content2, lplayers);
	}

	public String exitGame() {
		return "index.xhtml?faces-redirect=true";
	}

	public String endGame() {
		for (int i = 0; i < pDao.getPlayers().getSize(); i++) {
			PlayerDTO current = pDao.getPlayers().get(i);
			sendEmailToPlayers(current.getName(), current.getEmail(), i == selectedPlayer);
		}
		String exist = HttpClientSynchronous.doGet("http://localhost:8081/gamedetails/existhash/" + hashCode);
		if (!exist.equals("No")) {
			HttpClientSynchronous.doDelete("http://localhost:8081/gamedetails/deletebyhash/" + hashCode);
			HttpClientSynchronous.doDelete("http://localhost:8081/player/deletebyhash/" + hashCode);
			HttpClientSynchronous.doDelete("http://localhost:8081/territory/deletebyhash/" + hashCode);
		}
		return "index.xhtml?faces-redirect=true";
	}

	public void sendEmailToPlayers(String name, String email, boolean isWinner) {
		String content = "Thank you for playing the game.\n\n";
		if (isWinner) {
			int totalTroops = 0;
			for (String aux : territories)
				totalTroops += tDao.getTerrritories().getValue(aux).getNumberTroops();
			content += "Congratulations " + name
					+ " on your victory in the game! Your strategy and tactical skills took you to the top of the board."
					+ " You are a true master of conquest and territorial control.You have conquered all the terrain with a total of "
					+ totalTroops + " troops." + "\n\nGeneral results:\n\n";
		} else {
			content += "Although this time you did not achieve victory " + name
					+ ", I want to highlight your bravery and strategic approach."
					+ " Your participation showed your dedication and tactical skills, and it is evident that you faced challenges with determination."
					+ "\n\nGeneral results:\n\n";
		}
		for (int i = 0; i < pDao.getPlayers().getSize(); i++) {
			content += pDao.getPlayers().get(i).getName() + ". " + (i == selectedPlayer ? "Winner.\n" : "Loser\n");
		}
		try {
			MailSender.sendEamil(email, content);
		} catch (MessagingException e) {
			System.err.println("Error sending mail");
		}
	}

	public String generarHash(int password) {
		return HttpClientSynchronous.doGet("http://localhost:8081/gamedetails/generatehash/" + password);
	}

	private String username;
	private String password;
	private String email;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String register() {
		if (username == null || username.isEmpty() || password == null || password.isEmpty() || email == null
				|| email.isEmpty()) {
			addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Todos los campos son obligatorios.");
			return null;
		}
		if (!email.matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")) {
			addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Email no válido.");
			return null;
		}
		for (PlayerDTO p : pDao.getPlayers()) {
			if (p.getName().equals(username)) {
				addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Usuario ya existe.");
				return null;
			}
		}
		String hashedPassword = hashPassword(password);
		PlayerDTO nuevo = new PlayerDTO(username, hashedPassword, email, "white");
		pDao.create(nuevo);
		sendRegisterEmail(email, username);
		addMessage(FacesMessage.SEVERITY_INFO, "Registro exitoso", "Bienvenido, " + username);
		username = "";
		password = "";
		email = "";
		return "Login.xhtml?faces-redirect=true";
	}

	public String login() {
		if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
			addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Usuario y contraseña requeridos.");
			return null;
		}
		String hashed = hashPassword(password);
		for (PlayerDTO p : pDao.getPlayers()) {
			if (p.getName().equals(username) && p.getPassword().equals(hashed)) {
				addMessage(FacesMessage.SEVERITY_INFO, "Bienvenido", "Acceso correcto");
				return "game.xhtml?faces-redirect=true";
			}
		}
		addMessage(FacesMessage.SEVERITY_ERROR, "Acceso denegado", "Credenciales incorrectas.");
		return null;
	}

	private String hashPassword(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
			StringBuilder hex = new StringBuilder();
			for (byte b : hash)
				hex.append(String.format("%02x", b));
			return hex.toString();
		} catch (Exception e) {
			return password;
		}
	}

	private void sendRegisterEmail(String email, String username) {
		String msg = "¡Bienvenido a Risk!\n\nTu usuario (" + username
				+ ") ha sido registrado exitosamente.\n¡Disfruta el juego!";
		try {
			MailSender.sendEamil(email, msg);
		} catch (MessagingException e) {
			System.err.println("Error enviando correo de registro");
		}
	}

}