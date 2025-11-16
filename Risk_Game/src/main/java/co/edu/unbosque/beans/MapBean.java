package co.edu.unbosque.beans;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import co.edu.unbosque.model.Jugador;
import co.edu.unbosque.model.JugadorDTO;
import co.edu.unbosque.model.TerritorioDTO;
import co.edu.unbosque.model.persistence.DataMapper;
import co.edu.unbosque.model.persistence.JugadorDAO;
import co.edu.unbosque.model.persistence.TerritorioDAO;
import co.edu.unbosque.util.MailSender;
import co.edu.unbosque.util.MyDoubleLinkedList;
import co.edu.unbosque.util.MyGraph;
import co.edu.unbosque.util.MyMap;
import co.edu.unbosque.util.QueueImp;
import co.edu.unbosque.util.algorithm.BreadthFirstSearch;

import java.util.Arrays;
import java.util.Random;
import jakarta.annotation.ManagedBean;
import jakarta.enterprise.context.RequestScoped;
import javax.mail.MessagingException;

@ManagedBean
@RequestScoped
public class MapBean {
	private static final long serialVersionUID = 1L;

	@Inject
	private AuthBean authBean;
	
	private TerritorioDAO territorioDao;
	private JugadorDAO jugadorDao;
	private MyGraph<String> map;

	private String[] territories;
	private TerritorioDTO selectedTerritory, targetTerritory;
	private int selectedPlayer;
	private String phase, textWinner, textLosser;
	private boolean error1, error2, reforzed;
	private int troopsObtained, troopsCommand;
	private String enemyPlayer, currentPlayer;

	// Variables para dados
	private int selectedDices, selectedEDices;
	private int numDice, numEDice;
	private int[] disesA, disesE;
	private int winner;
	private int maxTroops, minTroops;

	private MyDoubleLinkedList<String> enemiesTarget, alliesTarget;

	public MapBean() {
		territorioDao = new TerritorioDAO();
		jugadorDao = new JugadorDAO();
		map = new MyGraph<>();
		disesA = new int[3];
		disesE = new int[2];
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

	public String init() {
		// Obtener datos del AuthBean
		int numPlayers = authBean.getNumPlayers();
		String[] names = authBean.getNames();
		String[] colors = authBean.getColors();

		// Validar datos
		if (!validatePlayers(numPlayers, names, colors)) {
			return null; // Se quedan en la misma página
		}

		// Crear jugadores
		if (!createPlayers(numPlayers, names, colors)) {
			return null;
		}

		// Asignar territorios aleatoriamente
		assignTerritories(numPlayers);

		// Inicializar variables del juego
		selectedPlayer = 0;
		phase = "Phase 1: Reinforce";
		receiveTroops();
		currentPlayer = jugadorDao.getJugadores().get(selectedPlayer).getName();

		initDetails();

		return "game.xhtml?faces-redirect=true";
	}

	private boolean validatePlayers(int numPlayers, String[] names, String[] colors) {
		MyMap<String, Boolean> vColor = new MyMap<>();
		MyMap<String, Boolean> vName = new MyMap<>();

		for (int i = 0; i < numPlayers; i++) {
			// Validar nombre
			if (names[i] == null || names[i].trim().isEmpty()) {
				addMessage(FacesMessage.SEVERITY_ERROR, "Error", "El jugador " + (i + 1) + " debe tener un nombre.");
				return false;
			}
			if (names[i].length() > 15) {
				addMessage(FacesMessage.SEVERITY_ERROR, "Error",
						"El nombre del jugador " + (i + 1) + " es muy largo (máximo 15 caracteres).");
				return false;
			}
			if (vName.containsKey(names[i])) {
				addMessage(FacesMessage.SEVERITY_ERROR, "Error", "El nombre '" + names[i] + "' ya está siendo usado.");
				return false;
			}
			vName.put(names[i], true);

			// Validar color
			if (colors[i] == null || colors[i].trim().isEmpty()) {
				addMessage(FacesMessage.SEVERITY_ERROR, "Error",
						"El jugador " + (i + 1) + " debe seleccionar un color.");
				return false;
			}
			if (vColor.containsKey(colors[i])) {
				addMessage(FacesMessage.SEVERITY_ERROR, "Error", "El color ya está siendo usado por otro jugador.");
				return false;
			}
			vColor.put(colors[i], true);
		}

		return true;
	}

	private boolean createPlayers(int numPlayers, String[] names, String[] colors) {
		jugadorDao = new JugadorDAO(); // Limpiar jugadores anteriores

		for (int i = 0; i < numPlayers; i++) {
			Jugador jugador = new Jugador();
			jugador.setName(names[i]);
			jugador.setColor(colors[i]);
			jugador.setTerritoriosPertenecientes(new MyDoubleLinkedList<>());

			jugadorDao.getJugadores().add(jugador);
		}

		return true;
	}

	private void assignTerritories(int numPlayers) {
		Random r = new Random();

		for (String territory : territories) {
			int playerIndex = r.nextInt(numPlayers);
			Jugador selectedPlayer = jugadorDao.getJugadores().get(playerIndex);

			int tropasIniciales = r.nextInt(3) + 1;
			MyDoubleLinkedList<TerritorioDTO> adyacentesVacia = new MyDoubleLinkedList<>();

			TerritorioDTO nuevo = new TerritorioDTO(territory, tropasIniciales, selectedPlayer, adyacentesVacia);

			territorioDao.crear(nuevo);
			selectedPlayer.getTerritoriosPertenecientes().add(DataMapper.territorioDTOtoTerritorio(nuevo));
		}
	}

	public void initDetails() {
		troopsCommand = 0;
		textWinner = "";
		enemyPlayer = "";
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
		TerritorioDTO terr = DataMapper.territorioToTerritorioDTO(territorioDao.findByNombre(territory));

		if (terr != null && terr.getDuenio() != null && terr.getDuenio().getColor() != null) {
			return terr.getDuenio().getColor();
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
		int territories = jugadorDao.getJugadores().get(selectedPlayer).getTerritoriosPertenecientes().getSize();
		troopsObtained = territories / 3;
		if (troopsObtained < 3) {
			troopsObtained = 3;
		}
	}

	private void addMessage(FacesMessage.Severity severity, String summary, String detail) {
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