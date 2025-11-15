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

import co.edu.unbosque.model.Jugador;
import co.edu.unbosque.model.JugadorDTO;
import co.edu.unbosque.model.Territorio;
import co.edu.unbosque.model.TerritorioDTO;
import co.edu.unbosque.model.persistence.DataMapper;
import co.edu.unbosque.model.persistence.JugadorDAO;
import co.edu.unbosque.model.persistence.TerritorioDAO;
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

	private TerritorioDAO territorioDao;
	private JugadorDAO jugadorDao;
	private String[] territories;
	private TerritorioDTO selectedTerritory, targetTerritory;
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
	private static final int MAX_PLAYERS = 6;

	public MapBean() {
		territorioDao = new TerritorioDAO();
		jugadorDao = new JugadorDAO();
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

	public TerritorioDAO getTerritorioDao() {
		return territorioDao;
	}

	public void setTerritorioDao(TerritorioDAO territorioDao) {
		this.territorioDao = territorioDao;
	}

	public JugadorDAO getJugadorDao() {
		return jugadorDao;
	}

	public void setJugadorDao(JugadorDAO jugadorDao) {
		this.jugadorDao = jugadorDao;
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

	public TerritorioDTO getSelectedTerritory() {
		return selectedTerritory;
	}

	public void setSelectedTerritory(TerritorioDTO selectedTerritory) {
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
		assignTroops = territorioDao.getTerritorios().getHead().getInfo().getCantidadTropas();
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

	public TerritorioDTO getTargetTerritory() {
		return targetTerritory;
	}

	public void setTargetTerritory(TerritorioDTO targetTerritory) {
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

			int selectedPlayerIndex = r.nextInt(jugadorDao.getJugadores().getSize());
			Jugador selectedPlayer = jugadorDao.getJugadores().get(selectedPlayerIndex);

			// Generar tropas iniciales: entre 1 y 3
			int tropasIniciales = r.nextInt(3) + 1;

			// Crear lista de adyacentes vacía por ahora
			MyDoubleLinkedList<TerritorioDTO> adyacentesVacia = new MyDoubleLinkedList<>();

			// Crear TerritorioDTO usando el NUEVO constructor
			TerritorioDTO nuevo = new TerritorioDTO(territory, tropasIniciales, selectedPlayer, adyacentesVacia);

			// Guardarlo en el DAO
			territorioDao.crear(nuevo);

			// Agregar el territorio al jugador
			selectedPlayer.getTerritoriosPertenecientes().add(DataMapper.territorioDTOtoTerritorio(nuevo));
		}

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
		currentPlayer = jugadorDao.getJugadores().get(selectedPlayer).getName();
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
		if (territorioDao.getTerritorios().getHead().getInfo().getDuenio().getColor() != null) {
			return territorioDao.getTerritorios().getHead().getInfo().getDuenio().getColor();
		}
		return "#e6e6e6";
	}

	public void changeTerritory(String territory) {

		// Buscar el territorio por nombre
		TerritorioDTO clicked = DataMapper.territorioToTerritorioDTO(territorioDao.findByNombre(territory));

		if (clicked == null) {
			error1 = true;
			return;
		}

		switch (phase) {

		/*
		 * ============================================================ PHASE 1:
		 * REINFORCE ============================================================
		 */
		case "Phase 1: Reinforce":

			if (selectedTerritory != null) {
				selectedTerritory.getDuenio().setColor(selectedTerritory.getDuenio().getColor());

			}

			if (troopsObtained == 0)
				return;

			if (jugadorDao.getJugadores().get(selectedPlayer).containsTerritory(territory)) {

				selectedTerritory = clicked;
				clicked.getDuenio().setColor("#7da028");
				error1 = false;

			} else {
				error1 = true;
			}
			break;

		/*
		 * ============================================================ PHASE 2: ATTACK
		 * ============================================================
		 */
		case "Phase 2: Attack":

			boolean isMine = jugadorDao.getJugadores().get(selectedPlayer).containsTerritory(territory);
			int troops = clicked.getCantidadTropas();

			// Own territory with 1 troop -> cannot attack
			if (isMine && troops <= 1) {
				error1 = true;
				offTargetEnemies();
				selectedTerritory = null;
				targetTerritory = null;
				break;
			}

			// Select origin to attack from
			if (isMine && troops > 1) {
				offTargetEnemies();
				selectedTerritory = clicked;
				clicked.getDuenio().setColor("#7da028");

				targetEnemies(territory);

				error1 = false;
				error2 = enemiesTarget.getSize() == 0;

				targetTerritory = null;
				break;
			}

			// Enemy target clicked
			if (enemiesTarget.getSize() > 0 && isTargetEnemy(territory)) {

				targetTerritory = clicked;
				enemyPlayer = clicked.getDuenio().getName();
				offTargetEnemies(territory);
				calcNumDices();
			}

			break;

		/*
		 * ============================================================ PHASE 3:
		 * STRENGTHEN ============================================================
		 */
		case "Phase 3: Strengthen":

			if (reforzed)
				return;

			boolean iOwn = jugadorDao.getJugadores().get(selectedPlayer).containsTerritory(territory);
			int t = clicked.getCantidadTropas();

			// Nothing selected + territory ≤ 1
			if (selectedTerritory == null && iOwn && t <= 1) {
				error2 = true;
				error1 = false;
				targetTerritory = null;
				break;
			}

			// First selected territory
			if (selectedTerritory == null && iOwn && t > 1) {

				selectedTerritory = clicked;
				clicked.getDuenio().setColor("#7da028");
				targetAllies(territory);

				error1 = false;
				error2 = false;
				break;
			}

			// Changing origin territory
			if (selectedTerritory != null && targetTerritory != null && iOwn && t > 1) {

				selectedTerritory.getDuenio().setColor(selectedTerritory.getDuenio().getColor());
				selectedTerritory = clicked;

				maxTroops = selectedTerritory.getCantidadTropas() - 1;

				offTargetAllies();
				selectedTerritory.getDuenio().setColor("#7da028");

				targetAllies(territory);

				error1 = false;
				error2 = false;
				break;
			}

			// Selecting ally target
			if (selectedTerritory != null && isTargetAllie(territory)) {

				targetTerritory = clicked;
				maxTroops = selectedTerritory.getCantidadTropas() - 1;

				offTargetAllies(territory);

				error1 = false;
				error2 = false;
				break;
			}

			// Invalid ally selection
			if (selectedTerritory != null && iOwn && !isTargetAllie(territory) && t <= 1) {

				selectedTerritory.getDuenio().setColor(selectedTerritory.getDuenio().getColor());
				selectedTerritory = null;
				targetTerritory = null;

				offTargetAllies();

				error2 = true;
				error1 = false;
				break;
			}

			// Default invalid
			offTargetAllies();
			selectedTerritory = null;
			targetTerritory = null;

			error1 = true;
			error2 = false;

			break;
		}
	}

	public void reinforceTerritory() {

		int number = territorioDao.getTerritorios().getCurrent().getInfo().getDuenio().getTropasDisponibles()
				+ troopsCommand;
		territorioDao.getTerritorios().getCurrent().getInfo().getDuenio().setTropasDisponibles(number);
		troopsObtained -= troopsCommand;
		territorioDao.getTerritorios().getCurrent().getInfo().getDuenio()
				.setColor(playerTerritory(selectedTerritory.getDuenio().getColor()).getColor());

		selectedTerritory = null;
		troopsCommand = 0;
	}

	public JugadorDTO playerTerritory(String territory) {
		for (int i = 0; i < jugadorDao.getJugadores().getSize(); i++) {
			if (jugadorDao.getJugadores().get(i).containsTerritory(territory)) {
				return DataMapper.jugadorToJugadorDTO(jugadorDao.getJugadores().get(i));
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
			currentPlayer = jugadorDao.getJugadores().get(selectedPlayer).getName();
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
			addAfterConquist(targetTerritory.getNombre());
			if (jugadorDao.getJugadores().get(selectedPlayer).getTerritoriosPertenecientes()
					.getSize() == territories.length) {
				phase = "Winner";
				return;
			}
			maxTroops = selectedTerritory.getCantidadTropas() - 1;
			phase = "Phase 2: Conquest";
		}
	}

	public void targetEnemies(String territory) {
		enemiesTarget = new MyDoubleLinkedList<>();
		BreadthFirstSearch<String> bfs = new BreadthFirstSearch<String>(map, territory);
		bfs.runSearch();
		for (String aux : territories) {
			if (!jugadorDao.getJugadores().get(selectedPlayer).containsTerritory(aux)) {
				if (bfs.obtainDistance(aux) == 1) {
					territorioDao.getTerritorios().getCurrent().getInfo().getDuenio().setColor("#7da028");
					enemiesTarget.add(aux);
				}
			}
		}
	}

	public void offTargetEnemies() {
		if (selectedTerritory != null) {
			territorioDao.getTerritorios().getCurrent().getInfo().getDuenio()
					.setColor(playerTerritory(selectedTerritory.getDuenio().getColor()).getColor());
		}
		for (String aux : enemiesTarget) {
			territorioDao.getTerritorios().getCurrent().getInfo().getDuenio().setColor(playerTerritory(aux).getColor());
		}
		enemiesTarget = new MyDoubleLinkedList<>();
	}

	public void offTargetEnemies(String territory) {
		for (String aux : enemiesTarget) {
			if (aux.equals(territory)) {
				territorioDao.getTerritorios().getCurrent().getInfo().getDuenio().setColor("#7da028");
				continue;
			}
			territorioDao.getTerritorios().getCurrent().getInfo().getDuenio().setColor(playerTerritory(aux).getColor());
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
		if (selectedTerritory.getCantidadTropas() - 1 >= 3)
			numDice = 3;
		else if (selectedTerritory.getCantidadTropas() - 1 == 2)
			numDice = 2;
		else
			numDice = 1;

		if (targetTerritory.getCantidadTropas() >= 2)
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
		int remainingATroops = selectedTerritory.getCantidadTropas() - restTroops[0];
		int remainingBTroops = targetTerritory.getCantidadTropas() - restTroops[1];
		territorioDao.findByNombre(targetTerritory.getNombre()).setCantidadTropas(remainingBTroops);
		selectedTerritory = DataMapper
				.territorioToTerritorioDTO(territorioDao.findByNombre(selectedTerritory.getNombre()));
		targetTerritory = DataMapper.territorioToTerritorioDTO(territorioDao.findByNombre(targetTerritory.getNombre()));
		if (targetTerritory.getCantidadTropas() == 0) {
			textWinner = jugadorDao.getJugadores().get(selectedPlayer).getName() + " lost " + restTroops[0]
					+ " troops | " + playerTerritory(targetTerritory.getNombre()).getName() + " lost " + restTroops[1]
					+ " troops.";
			textLosser = jugadorDao.getJugadores().get(selectedPlayer).getName() + " has conquered "
					+ targetTerritory.getNombre();
			;
			winner = 1;
			minTroops = selectedDices;
		} else if (selectedTerritory.getCantidadTropas() == 1) {
			textWinner = "You can no longer attack from this territory, there is only one troop left.";
			textLosser = jugadorDao.getJugadores().get(selectedPlayer).getName() + " lost " + restTroops[0]
					+ " troops | " + playerTerritory(targetTerritory.getNombre()).getName() + " lost " + restTroops[1]
					+ " troops";
			winner = 2;
		} else {
			textWinner = jugadorDao.getJugadores().get(selectedPlayer).getName() + " lost " + restTroops[0]
					+ " troops.";
			textLosser = playerTerritory(targetTerritory.getNombre()).getName() + " lost " + restTroops[1] + " troops.";
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
		jugadorDao.getJugadores().get(selectedPlayer).getTerritoriosPertenecientes()
				.add(territorioDao.findByNombre(territory));
	}

	public void addTroopsAfterConquist() {
		int resTroops = selectedTerritory.getCantidadTropas() - troopsCommand;
		territorioDao.findByNombre(selectedTerritory.getNombre()).setCantidadTropas(resTroops);
		territorioDao.findByNombre(targetTerritory.getNombre()).setCantidadTropas(troopsCommand);
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
						if (getDis()[v] == -1 && jugadorDao.getJugadores().get(selectedPlayer)
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
			if (jugadorDao.getJugadores().get(selectedPlayer).containsTerritory(aux)) {
				if (bfs.obtainDistance(aux) != -1 && !aux.equals(territory)) {
					territorioDao.findByNombre(aux).getDuenio().setColor("#ccd9c1");
					alliesTarget.add(aux);
				}
			}
		}
	}

	public void offTargetAllies() {
		if (selectedTerritory != null) {
			territorioDao.findByNombre(selectedTerritory.getNombre()).getDuenio()
					.setColor(playerTerritory(selectedTerritory.getDuenio().getColor()).getColor());
		}
		for (String aux : alliesTarget) {
			territorioDao.findByNombre(aux).getDuenio().setColor(playerTerritory(aux).getColor());
		}
		alliesTarget = new MyDoubleLinkedList<>();
	}

	public void offTargetAllies(String territory) {
		for (String aux : alliesTarget) {
			if (aux.equals(territory)) {
				territorioDao.findByNombre(aux).getDuenio().setColor("#ccd9c1");
				continue;
			}
			territorioDao.findByNombre(aux).getDuenio().setColor(playerTerritory(aux).getColor());

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
		int restTroops = selectedTerritory.getCantidadTropas() - troopsCommand;
		territorioDao.findByNombre(selectedTerritory.getNombre()).setCantidadTropas(restTroops);
		int receivedTroops = targetTerritory.getCantidadTropas() + troopsCommand;
		territorioDao.findByNombre(targetTerritory.getNombre()).setCantidadTropas(receivedTroops);
		selectedTerritory = territorioDao.findByNombre(selectedTerritory.getNombre()).getNombre();
		targetTerritory = territorioDao.findByNombre(targetTerritory.getNombre());
		getTerrritories().getValue(targetTerritory.getName());
		offTargetAllies();
		reforzed = true;
	}

	public void passTurn() {
		selectedPlayer++;
		if (selectedPlayer == jugadorDao.getJugadores().getSize()) {
			selectedPlayer = 0;
		}
		while (jugadorDao.getJugadores().get(selectedPlayer).getTerritoriosPertenecientes().getSize() == 0) {
			selectedPlayer++;
			if (selectedPlayer == jugadorDao.getJugadores().getSize()) {
				selectedPlayer = 0;
			}
		}
	}

	public void receiveTroops() {
		troopsObtained = jugadorDao.getJugadores().get(selectedPlayer).getTerritoriosPertenecientes().getSize() / 3;
		if (troopsObtained < 3) {
			troopsObtained = 3;
		}
	}

	public boolean initPlayers() {
		jugadorDao = new JugadorDAO();
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
			jugadorDao.getJugadores()
					.add(new JugadorDTO(names[i], colors[i], jugadorDao.getJugadores().get(i).getUser(),
							jugadorDao.getJugadores().get(i).getTropasDisponibles(),
							jugadorDao.getJugadores().get(i).getTerritoriosPertenecientes()));
		}
		return required;
	}

	public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
	}

	public int playerIndex(String territory) {
		for (int i = 0; i < jugadorDao.getJugadores().getSize(); i++) {
			if (jugadorDao.getJugadores().get(i).containsTerritory(territory)) {
				return i;
			}
		}
		return 0;
	}

	public String exitGame() {
		return "index.xhtml?faces-redirect=true";
	}

	public String endGame() {
		for (int i = 0; i < jugadorDao.getJugadores().getSize(); i++) {
			JugadorDTO current = jugadorDao.getJugadores().get(i).getClass();
		}
		return "index.xhtml?faces-redirect=true";
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

	public String login() {
		if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
			addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Usuario y contraseña requeridos.");
			return null;
		}
		String hashed = hashPassword(password);
		for (JugadorDTO p : jugadorDao.getAll()) {
			if (p.getName().equals(username) && p.getUser().getContrasenia().equals(hashed)) {
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

	private String selectedMode;

	public String getSelectedMode() {
		return selectedMode;
	}

	public void setSelectedMode(String selectedMode) {
		this.selectedMode = selectedMode;
	}

}