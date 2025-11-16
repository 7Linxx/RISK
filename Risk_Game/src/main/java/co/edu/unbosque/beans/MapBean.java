package co.edu.unbosque.beans;

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
import co.edu.unbosque.model.Jugador;
import co.edu.unbosque.model.Territorio;
import co.edu.unbosque.model.persistence.FileHandler;
import co.edu.unbosque.model.persistence.JugadorDAO;
import co.edu.unbosque.model.persistence.TerritorioDAO;
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
	private String[] territorios, hashCodes;
	private Territorio territorioSeleccionado, territorioFichado;
	private int jugadorSeleccionado, index = 0, tropasAsignadas, tropasObtenidas, comandoTropas, numDice, maxDice,
			numEDice, ganador, selectedDices, selectedEDices, maxTropas, minTropas, numeroJugadores, sizeHashes;
	private String fase, textGanador, textPerdedor, hashCode, jugadorEnemigo, jugadorActual;
	private boolean error1, error2, reforzado;
	private MyGraph<String> map;
	private MyDoubleLinkedList<String> enemiesTarget, alliesTarget;
	private String colores[];
	private String nombres[];
	private String emails[];
	private int[] disesA, disesE;
	private StreamedContent file;

	public MapBean() {
		territorioDao = new TerritorioDAO();
		jugadorDao = new JugadorDAO();
		map = new MyGraph<>();
		disesA = new int[3];
		disesE = new int[2];
		colores = new String[4];
		nombres = new String[4];
		emails = new String[4];
		reforzado = false;
		territorios = new String[] { "Alaska", "Northwest_Territory", "Alberta", "Ontario", "Western_United_States",
				"Quebec", "Eastern_United_States", "Central_America", "Venezuela", "Peru", "Brazil", "Argentina",
				"Greenland", "Iceland", "Great_Britain", "Northern_Europe", "Scandinavia", "Western_Europe",
				"Southern_Europe", "Ukraine", "Ural", "Middle_East", "Afghanistan", "China", "India", "Siam", "Siberia",
				"Mongolia", "Irkutsk", "Yakursk", "Kamchatka", "Japan", "Indonesia", "New_Guinea", "Eastern_Australia",
				"Western_Australia", "North_Africa", "Congo", "Egypt", "East_Africa", "South_Africa", "Madagascar" };
		for (String aux : territorios) {
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

	public String[] getTerritorios() {
		return territorios;
	}

	public void setTerritorios(String[] territorios) {
		this.territorios = territorios;
	}

	public String[] getHashcodes() {
		return hashCodes;
	}

	public void setHashcodes(String[] hashcodes) {
		this.hashCodes = hashcodes;
	}

	public Territorio getTerritorioSeleccionado() {
		return territorioSeleccionado;
	}

	public void setTerritorioSeleccionado(Territorio territorioSeleccionado) {
		this.territorioSeleccionado = territorioSeleccionado;
	}

	public Territorio getTerritorioFichado() {
		return territorioFichado;
	}

	public void setTerritorioFichado(Territorio territorioFichado) {
		this.territorioFichado = territorioFichado;
	}

	public int getJugadorSeleccionado() {
		return jugadorSeleccionado;
	}

	public void setJugadorSeleccionado(int jugadorSeleccionado) {
		this.jugadorSeleccionado = jugadorSeleccionado;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getTropasAsignadas() {
		tropasAsignadas = territorioDao.getTerritorios().getValue(territorios[index]).getNumeroTropas();
		index++;
		if (index == territorios.length) {
			index = 0;
		}
		return tropasAsignadas;
	}

	public void setTropasAsignadas(int tropasAsignadas) {
		this.tropasAsignadas = tropasAsignadas;
	}

	public int getTropasObtenidas() {
		return tropasObtenidas;
	}

	public void setTropasObtenidas(int tropasObtenidas) {
		this.tropasObtenidas = tropasObtenidas;
	}

	public int getComandoTropas() {
		return comandoTropas;
	}

	public void setComandoTropas(int comandoTropas) {
		this.comandoTropas = comandoTropas;
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

	public int getGanador() {
		return ganador;
	}

	public void setGanador(int ganador) {
		this.ganador = ganador;
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

	public int getMaxTropas() {
		return maxTropas;
	}

	public void setMaxTropas(int maxTropas) {
		this.maxTropas = maxTropas;
	}

	public int getMinTropas() {
		return minTropas;
	}

	public void setMinTropas(int minTropas) {
		this.minTropas = minTropas;
	}

	public int getNumeroJugadores() {
		return numeroJugadores;
	}

	public void setNumeroJugadores(int numeroJugadores) {
		this.numeroJugadores = numeroJugadores;
	}

	public int getSizeHashes() {
		return sizeHashes;
	}

	public void setSizeHashes(int sizeHashes) {
		this.sizeHashes = sizeHashes;
	}

	public String getFase() {
		return fase;
	}

	public void setFase(String fase) {
		this.fase = fase;
	}

	public String getTextGanador() {
		return textGanador;
	}

	public void setTextGanador(String textGanador) {
		this.textGanador = textGanador;
	}

	public String getTextPerdedor() {
		return textPerdedor;
	}

	public void setTextPerdedor(String textPerdedor) {
		this.textPerdedor = textPerdedor;
	}

	public String getHashCode() {
		return hashCode;
	}

	public void setHashCode(String hashCode) {
		this.hashCode = hashCode;
	}

	public String getJugadorEnemigo() {
		return jugadorEnemigo;
	}

	public void setJugadorEnemigo(String jugadorEnemigo) {
		this.jugadorEnemigo = jugadorEnemigo;
	}

	public String getJugadorActual() {
		return jugadorActual;
	}

	public void setJugadorActual(String jugadorActual) {
		this.jugadorActual = jugadorActual;
	}

	public boolean isError1() {
		return error1;
	}

	public void setError1(boolean error1) {
		this.error1 = error1;
	}

	public boolean isError2() {
		return error2;
	}

	public void setError2(boolean error2) {
		this.error2 = error2;
	}

	public boolean isReforzado() {
		return reforzado;
	}

	public void setReforzado(boolean reforzado) {
		this.reforzado = reforzado;
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

	public MyDoubleLinkedList<String> getAlliesTarget() {
		return alliesTarget;
	}

	public void setAlliesTarget(MyDoubleLinkedList<String> alliesTarget) {
		this.alliesTarget = alliesTarget;
	}

	public String[] getColores() {
		return colores;
	}

	public void setColores(String[] colores) {
		this.colores = colores;
	}

	public String[] getNombres() {
		return nombres;
	}

	public void setNombres(String[] nombres) {
		this.nombres = nombres;
	}

	public String[] getEmails() {
		return emails;
	}

	public void setEmails(String[] emails) {
		this.emails = emails;
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
		for (String territorio : territorios) {
			int selectedPlayer = r.nextInt(jugadorDao.getJugadores().getSize());
			territorioDao.create(new Territorio(territorio, jugadorDao.getJugadores().get(selectedPlayer).getColor(),
					r.nextInt(3) + 1));
			jugadorDao.getJugadores().get(selectedPlayer).getTerritorios().add(territorio);
		}
		hashCode = generarHash(Objects.hash(firstDigitHashCode(), territorioDao, jugadorDao));
		initDetails();
		jugadorSeleccionado = 0;
		fase = "Fase 1: Refuerzos";
		receiveTroops();
		return "game.xhtml?faces-redirect=true";
	}

	public void initDetails() {
		comandoTropas = 0;
		textGanador = "";
		jugadorEnemigo = "";
		jugadorActual = jugadorDao.getJugadores().get(jugadorSeleccionado).getNombre();
		error1 = error2 = false;
		enemiesTarget = new MyDoubleLinkedList<>();
		alliesTarget = new MyDoubleLinkedList<>();
		territorioSeleccionado = null;
		territorioFichado = null;
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

	public String obtainColor(String territorio) {
		if (territorioDao.getTerritorios().containsKey(territorio)) {
			return territorioDao.getTerritorios().getValue(territorio).getColor();
		}
		return "#e6e6e6";
	}

	public void changeTerritory(String territorio) {
		if (fase.equals("Fase 1: Refuerzos")) {
			if (territorioSeleccionado != null) {
				territorioDao.getTerritorios().getValue(territorioSeleccionado.getNombre())
						.setColor(playerTerritory(territorioSeleccionado.getNombre()).getColor());
			}
			if (tropasObtenidas == 0)
				return;
			if (jugadorDao.getJugadores().get(jugadorSeleccionado).containsTerritory(territorio)) {
				territorioSeleccionado = territorioDao.getTerritorios().getValue(territorio);
				territorioDao.getTerritorios().getValue(territorio).setColor("#75FA61");
				error1 = false;
			} else {
				error1 = true;
			}
		} else if (fase.equals("Fase 2: Ataque")) {
			if (jugadorDao.getJugadores().get(jugadorSeleccionado).containsTerritory(territorio)
					&& territorioDao.getTerritorios().getValue(territorio).getNumeroTropas() <= 1) {
				error1 = true;
				offTargetEnemies();
				territorioSeleccionado = null;
				territorioFichado = null;
			} else if (jugadorDao.getJugadores().get(jugadorSeleccionado).containsTerritory(territorio)
					&& territorioDao.getTerritorios().getValue(territorio).getNumeroTropas() > 1) {
				offTargetEnemies();
				territorioSeleccionado = territorioDao.getTerritorios().getValue(territorio);
				territorioDao.getTerritorios().getValue(territorio).setColor("#75FA61");
				targetEnemies(territorio);
				error1 = false;
				if (enemiesTarget.getSize() == 0) {
					error2 = true;
				} else
					error2 = false;
				territorioFichado = null;
			} else if (enemiesTarget.getSize() > 0 && isTargetEnemy(territorio)) {
				territorioFichado = territorioDao.getTerritorios().getValue(territorio);
				jugadorEnemigo = playerTerritory(territorio).getNombre();
				offTargetEnemies(territorio);
				calcNumDices();
			}
		} else if (fase.equals("Fase 3: Fortificacion")) {
			if (reforzado) {
				return;
			}
			if (territorioSeleccionado == null
					&& jugadorDao.getJugadores().get(jugadorSeleccionado).containsTerritory(territorio)
					&& territorioDao.getTerritorios().getValue(territorio).getNumeroTropas() <= 1) {
				error2 = true;
				error1 = false;
				territorioFichado = null;
			} else if (territorioSeleccionado == null
					&& jugadorDao.getJugadores().get(jugadorSeleccionado).containsTerritory(territorio)
					&& territorioDao.getTerritorios().getValue(territorio).getNumeroTropas() > 1) {
				territorioSeleccionado = territorioDao.getTerritorios().getValue(territorio);
				territorioDao.getTerritorios().getValue(territorio).setColor("#75FA61");
				territorioFichado = null;
				targetAllies(territorio);
				error1 = false;
				error2 = false;

			} else if (territorioSeleccionado != null && territorioFichado != null
					&& jugadorDao.getJugadores().get(jugadorSeleccionado).containsTerritory(territorio)
					&& territorioDao.getTerritorios().getValue(territorio).getNumeroTropas() > 1) {
				territorioDao.getTerritorios().getValue(territorioSeleccionado.getNombre())
						.setColor(jugadorDao.getJugadores().get(jugadorSeleccionado).getColor());
				territorioSeleccionado = territorioDao.getTerritorios().getValue(territorio);
				maxTropas = territorioSeleccionado.getNumeroTropas() - 1;
				territorioFichado = null;
				offTargetAllies();
				territorioDao.getTerritorios().getValue(territorio).setColor("#75FA61");
				targetAllies(territorio);
				error1 = false;
				error2 = false;

			} else if (territorioSeleccionado != null && isTargetAllie(territorio)) {
				territorioFichado = territorioDao.getTerritorios().getValue(territorio);
				maxTropas = territorioSeleccionado.getNumeroTropas() - 1;
				offTargetAllies(territorio);
				error1 = false;
				error2 = false;
			} else if (territorioSeleccionado != null
					&& jugadorDao.getJugadores().get(jugadorSeleccionado).containsTerritory(territorio)
					&& !isTargetAllie(territorio)
					&& territorioDao.getTerritorios().getValue(territorio).getNumeroTropas() > 1) {
				territorioDao.getTerritorios().getValue(territorioSeleccionado.getNombre())
						.setColor(jugadorDao.getJugadores().get(jugadorSeleccionado).getColor());
				territorioSeleccionado = territorioDao.getTerritorios().getValue(territorio);
				maxTropas = territorioSeleccionado.getNumeroTropas() - 1;
				territorioFichado = null;
				offTargetAllies();
				territorioDao.getTerritorios().getValue(territorio).setColor("#75FA61");
				targetAllies(territorio);
				error1 = false;
				error2 = false;

			} else if (territorioSeleccionado != null
					&& jugadorDao.getJugadores().get(jugadorSeleccionado).containsTerritory(territorio)
					&& !isTargetAllie(territorio)
					&& territorioDao.getTerritorios().getValue(territorio).getNumeroTropas() <= 1) {
				territorioDao.getTerritorios().getValue(territorioSeleccionado.getNombre())
						.setColor(jugadorDao.getJugadores().get(jugadorSeleccionado).getColor());
				territorioSeleccionado = null;
				territorioFichado = null;
				offTargetAllies();
				error2 = true;
				error1 = false;
			} else {
				offTargetAllies();
				territorioSeleccionado = null;
				territorioFichado = null;
				error1 = true;
				error2 = false;
			}
		}
	}

	public void reinforceTerritory() {
		int number = territorioDao.getTerritorios().getValue(territorioSeleccionado.getNombre()).getNumeroTropas()
				+ comandoTropas;
		territorioDao.getTerritorios().getValue(territorioSeleccionado.getNombre()).setNumeroTropas(number);
		tropasObtenidas -= comandoTropas;
		territorioDao.getTerritorios().getValue(territorioSeleccionado.getNombre())
				.setColor(playerTerritory(territorioSeleccionado.getNombre()).getColor());
		territorioSeleccionado = null;
		comandoTropas = 0;
	}

	public Jugador playerTerritory(String territory) {
		for (int i = 0; i < jugadorDao.getJugadores().getSize(); i++) {
			if (jugadorDao.getJugadores().get(i).containsTerritory(territory)) {
				return jugadorDao.getJugadores().get(i);
			}
		}
		return null;
	}

	public void changePhase() {
		if (fase.equals("Fase 1: Refuerzos")) {
			error1 = false;
			error2 = false;
			fase = "Fase 2: Ataque";
			territorioSeleccionado = null;
		} else if (fase.equals("Fase 2: Ataque")) {
			error1 = false;
			error2 = false;
			reforzado = false;
			offTargetEnemies();
			territorioSeleccionado = null;
			territorioFichado = null;
			fase = "Fase 3: Fortificacion";
		} else if (fase.equals("Fase 3: Fortificacion")) {
			error1 = false;
			error2 = false;
			offTargetAllies();
			passTurn();
			receiveTroops();
			jugadorActual = jugadorDao.getJugadores().get(jugadorSeleccionado).getNombre();
			territorioSeleccionado = null;
			territorioFichado = null;
			textGanador = "";
			ganador = 0;
			selectedDices = 0;
			selectedEDices = 0;
			fase = "Fase 1: Refuerzos";
		}
	}

	public void phaseDices() {
		if (fase.equals("Fase 2: Ataque")) {
			fase = "Fase 2: Dados";
		} else if (fase.equals("Fase 2: Dados") && ganador != 1) {
			fase = "Fase 2: Ataque";
			offTargetEnemies();
			territorioSeleccionado = null;
			territorioFichado = null;
		} else {
			addAfterConquist(territorioFichado.getNombre());
			if (jugadorDao.getJugadores().get(jugadorSeleccionado).getTerritorios().getSize() == territorios.length) {
				fase = "Ganador";
				generateFileResults();
				return;
			}
			maxTropas = territorioSeleccionado.getNumeroTropas() - 1;
			fase = "Phase 2: Conquista";
		}
	}

	public void targetEnemies(String territory) {
		enemiesTarget = new MyDoubleLinkedList<>();
		BreadthFirstSearch<String> bfs = new BreadthFirstSearch<String>(map, territory);
		bfs.runSearch();
		for (String aux : territorios) {
			if (!jugadorDao.getJugadores().get(jugadorSeleccionado).containsTerritory(aux)) {
				if (bfs.obtainDistance(aux) == 1) {
					territorioDao.getTerritorios().getValue(aux).setColor("#F79A9A");
					enemiesTarget.add(aux);
				}
			}
		}
	}

	public void offTargetEnemies() {
		if (territorioSeleccionado != null) {
			territorioDao.getTerritorios().getValue(territorioSeleccionado.getNombre())
					.setColor(playerTerritory(territorioSeleccionado.getNombre()).getColor());
		}
		for (String aux : enemiesTarget) {
			territorioDao.getTerritorios().getValue(aux).setColor(playerTerritory(aux).getColor());
		}
		enemiesTarget = new MyDoubleLinkedList<>();
	}

	public void offTargetEnemies(String territory) {
		for (String aux : enemiesTarget) {
			if (aux.equals(territory)) {
				territorioDao.getTerritorios().getValue(aux).setColor("#F79A9A");
				continue;
			}
			territorioDao.getTerritorios().getValue(aux).setColor(playerTerritory(aux).getColor());
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
		if (territorioSeleccionado.getNumeroTropas() - 1 >= 3)
			numDice = 3;
		else if (territorioSeleccionado.getNumeroTropas() - 1 == 2)
			numDice = 2;
		else
			numDice = 1;

		if (territorioFichado.getNumeroTropas() >= 2)
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
		textGanador = "";
		ganador = 0;
	}

	public int random() {
		SecureRandom r = new SecureRandom();
		int val = r.nextInt(6) + 1;
		return val;
	}

	public void battleRound() {
		int[] restTroops = resTroops(disesA, disesE);
		int remainingATroops = territorioSeleccionado.getNumeroTropas() - restTroops[0];
		int remainingBTroops = territorioFichado.getNumeroTropas() - restTroops[1];
		territorioDao.getTerritorios().getValue(territorioSeleccionado.getNombre()).setNumeroTropas(remainingATroops);
		territorioDao.getTerritorios().getValue(territorioFichado.getNombre()).setNumeroTropas(remainingBTroops);
		territorioSeleccionado = territorioDao.getTerritorios().getValue(territorioSeleccionado.getNombre());
		territorioFichado = territorioDao.getTerritorios().getValue(territorioFichado.getNombre());
		if (territorioFichado.getNumeroTropas() == 0) {
			textGanador = jugadorDao.getJugadores().get(jugadorSeleccionado).getNombre() + " perdio " + restTroops[0]
					+ " troops | " + playerTerritory(territorioFichado.getNombre()).getNombre() + " perdio "
					+ restTroops[1] + " troops.";
			textPerdedor = jugadorDao.getJugadores().get(jugadorSeleccionado).getNombre() + " has conquered "
					+ territorioFichado.getNombre();
			;
			ganador = 1;
			minTropas = selectedDices;
		} else if (territorioSeleccionado.getNumeroTropas() == 1) {
			textGanador = "No puedes atacar desde este territorio, solo queda una tropa.";
			textGanador = jugadorDao.getJugadores().get(jugadorSeleccionado).getNombre() + " perdio " + restTroops[0]
					+ " tropas | " + playerTerritory(territorioFichado.getNombre()).getNombre() + " perdio "
					+ restTroops[1] + " tropas";
			ganador = 2;
		} else {
			textGanador = jugadorDao.getJugadores().get(jugadorSeleccionado).getNombre() + " perdio " + restTroops[0]
					+ " tropas.";
			textPerdedor = playerTerritory(territorioFichado.getNombre()).getNombre() + " perdio " + restTroops[1]
					+ " tropas.";
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
		textGanador = "";
		calcNumDices();
	}

	public void addAfterConquist(String territory) {
		playerTerritory(territory).removeTerritory(territory);
		jugadorDao.getJugadores().get(jugadorSeleccionado).getTerritorios().add(territory);
	}

	public void addTroopsAfterConquist() {
		int resTroops = territorioSeleccionado.getNumeroTropas() - comandoTropas;
		territorioDao.getTerritorios().getValue(territorioSeleccionado.getNombre()).setNumeroTropas(resTroops);
		territorioDao.getTerritorios().getValue(territorioFichado.getNombre()).setNumeroTropas(comandoTropas);
		fase = "Phase 2: Attack";
		offTargetEnemies();
		territorioSeleccionado = null;
		territorioFichado = null;
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
						if (getDis()[v] == -1 && jugadorDao.getJugadores().get(jugadorSeleccionado)
								.containsTerritory(getGraph().numberToNode(v))) {
							getDis()[v] = getDis()[u] + 1;
							queue.enqueue(v);
						}
					}
				}
			}
		};
		bfs.runSearch();
		for (String aux : territorios) {
			if (jugadorDao.getJugadores().get(jugadorSeleccionado).containsTerritory(aux)) {
				if (bfs.obtainDistance(aux) != -1 && !aux.equals(territory)) {
					territorioDao.getTerritorios().getValue(aux).setColor("#C0C0C0");
					alliesTarget.add(aux);
				}
			}
		}
	}

	public void offTargetAllies() {
		if (territorioSeleccionado != null) {
			territorioDao.getTerritorios().getValue(territorioSeleccionado.getNombre())
					.setColor(playerTerritory(territorioSeleccionado.getNombre()).getColor());
		}
		for (String aux : alliesTarget) {
			territorioDao.getTerritorios().getValue(aux).setColor(playerTerritory(aux).getColor());
		}
		alliesTarget = new MyDoubleLinkedList<>();
	}

	public void offTargetAllies(String territory) {
		for (String aux : alliesTarget) {
			if (aux.equals(territory)) {
				territorioDao.getTerritorios().getValue(aux).setColor("#C0C0C0");
				continue;
			}
			territorioDao.getTerritorios().getValue(aux).setColor(playerTerritory(aux).getColor());
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
		int restTroops = territorioSeleccionado.getNumeroTropas() - comandoTropas;
		territorioDao.getTerritorios().getValue(territorioSeleccionado.getNombre()).setNumeroTropas(restTroops);
		int receivedTroops = territorioFichado.getNumeroTropas() + comandoTropas;
		territorioDao.getTerritorios().getValue(territorioFichado.getNombre()).setNumeroTropas(receivedTroops);
		territorioSeleccionado = territorioDao.getTerritorios().getValue(territorioSeleccionado.getNombre());
		territorioFichado = territorioDao.getTerritorios().getValue(territorioFichado.getNombre());
		offTargetAllies();
		reforzado = true;
	}

	public void passTurn() {
		jugadorSeleccionado++;
		if (jugadorSeleccionado == jugadorDao.getJugadores().getSize()) {
			jugadorSeleccionado = 0;
		}
		while (jugadorDao.getJugadores().get(jugadorSeleccionado).getTerritorios().getSize() == 0) {
			jugadorSeleccionado++;
			if (jugadorSeleccionado == jugadorDao.getJugadores().getSize()) {
				jugadorSeleccionado = 0;
			}
		}
	}

	public void receiveTroops() {
		tropasObtenidas = jugadorDao.getJugadores().get(jugadorSeleccionado).getTerritorios().getSize() / 3;
		if (tropasObtenidas < 3) {
			tropasObtenidas = 3;
		}
	}

	public boolean initPlayers() {
		jugadorDao = new JugadorDAO();
		boolean required = true;
		boolean auxName = true;
		MyMap<String, Boolean> vColor = new MyMap<>();
		MyMap<String, Boolean> vName = new MyMap<>();
		for (int i = 0; i < numeroJugadores; i++) {
			auxName = true;
			if (nombres[i].length() > 8) {
				addMessage(FacesMessage.SEVERITY_ERROR, "Invalid name player " + (i + 1),
						"The name should only have a maximum of 8 characters.");
				required = false;
				auxName = false;
			} else if (nombres[i].isEmpty()) {
				addMessage(FacesMessage.SEVERITY_ERROR, "Invalid name player " + (i + 1), "Please enter a name.");
				required = false;
				auxName = false;
			} else if (nombres[i].matches(".*[,;].*")) {
				addMessage(FacesMessage.SEVERITY_ERROR, "Invalid name player " + (i + 1),
						"Characters \",\" and \";\" are not allowed.");
				required = false;
			}
			if (vName.containsKey(nombres[i]) && auxName) {
				addMessage(FacesMessage.SEVERITY_ERROR, "Name already taked",
						"Please select other name player " + (i + 1) + ".");
				required = false;
			} else {
				vName.put(nombres[i], true);
			}
			if (!emails[i].matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")) {
				addMessage(FacesMessage.SEVERITY_ERROR, "Invalid email player " + (i + 1),
						"The entered email is not valid.");
				required = false;
			}
			if (colores[i].isEmpty()) {
				addMessage(FacesMessage.SEVERITY_ERROR, "Invalid color player " + (i + 1), "Please select a color.");
				required = false;
			}
			if (vColor.containsKey(colores[i]) && !colores[i].isEmpty()) {
				addMessage(FacesMessage.SEVERITY_ERROR, "Color already taked",
						"Please select other color player " + (i + 1) + ".");
				required = false;
			} else {
				vColor.put(colores[i], true);
			}
			jugadorDao.getJugadores().add(new PlayerDTO(nombres[i], colores[i], emails[i]));
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
		for (int i = 0; i < jugadorDao.getJugadores().getSize(); i++) {
			if (jugadorDao.getJugadores().get(i).containsTerritory(territory)) {
				return i;
			}
		}
		return 0;
	}

	public void saveGame() {
		if (fase.equals("Phase 2: Dices")) {
			addMessage(FacesMessage.SEVERITY_WARN, "Warning",
					"It is not possible to save while rolling dice. Select the back button to stop rolling dice and save.");
			return;
		}
		if (fase.equals("Phase 2: Conquest")) {
			addMessage(FacesMessage.SEVERITY_WARN, "Warning",
					"You cannot save while sending troops after you have conquered a territory. Please send troops to the conquered territory to save.");
			return;
		}
		if (fase.equals("Winner")) {
			addMessage(FacesMessage.SEVERITY_INFO, "The game is over.",
					"The game cannot be saved at the end of the game.");
			return;
		}
		if (fase.equals("Fase 1: Refuerzos") && territorioSeleccionado != null) {
			territorioDao.getTerritorios().getValue(territorioSeleccionado.getNombre())
					.setColor(playerTerritory(territorioSeleccionado.getNombre()).getColor());
		} else if (fase.equals("Fase 3: Fortificacion")) {
			offTargetAllies();
		} else if (fase.equals("Fase 2: Ataque")) {
			offTargetEnemies();
		}
		String exist = HttpClientSynchronous.doGet("http://localhost:8081/gamedetails/existhash/" + hashCode);
		if (!exist.equals("No")) {
			HttpClientSynchronous.doDelete("http://localhost:8081/gamedetails/deletebyhash/" + hashCode);
			HttpClientSynchronous.doDelete("http://localhost:8081/player/deletebyhash/" + hashCode);
			HttpClientSynchronous.doDelete("http://localhost:8081/territory/deletebyhash/" + hashCode);
		}
		for (Territorio current : territorioDao.getTerritorios().values()) {
			HttpClientSynchronous.doPost("http://localhost:8081/territory/create",
					"{\"hashCode\": \"" + hashCode + "\",\"name\":\"" + current.getNombre() + "\",\"color\":\""
							+ current.getColor() + "\",\"numberTroops\": " + current.getNumeroTropas()
							+ ",\"playerIndex\": " + playerIndex(current.getNombre()) + "}");
		}
		int i = 0;
		for (Jugador current : jugadorDao.getJugadores()) {
			HttpClientSynchronous.doPost("http://localhost:8081/player/create",
					"{\"hashCode\": \"" + hashCode + "\",\"name\": \"" + current.getNombre() + "\",\"color\": \""
							+ current.getColor() + "\",\"email\": \"" + current.getEmail() + "\",\"indexPlayer\": " + i
							+ "}");
			i++;
		}
		String gameInfo = "empty";
		if (fase.equals("Fase 1: Refuerzos")) {
			gameInfo = Integer.toString(tropasObtenidas);
		} else if (fase.equals("Fase 3: Fortificacion")) {
			gameInfo = reforzado ? "Yes" : "No";
		}
		HttpClientSynchronous.doPost("http://localhost:8081/gamedetails/create",
				"{\"hashCode\": \"" + hashCode + "\",\"phase\": \"" + fase + "\",\"playerTurn\": " + jugadorSeleccionado
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
		territorioDao = new TerritorioDAO();
		jugadorDao = new JugadorDAO();
		MyMap<Integer, MyDoubleLinkedList<String>> pTerritory = new MyMap<>();
		String[] jsTerritories = HttpClientSynchronous.doGet("http://localhost:8081/territory/getbyhash/" + hashCode)
				.split(";");
		for (String object : jsTerritories) {
			String[] attributes = object.split(",");
			Territorio territorio = new Territorio(attributes[1], attributes[2], Integer.parseInt(attributes[3]));
			int index = Integer.parseInt(attributes[4]);
			if (!pTerritory.containsKey(index)) {
				pTerritory.put(index, new MyDoubleLinkedList<>());
			}
			pTerritory.getValue(index).add(territorio.getNombre());
			territorioDao.create(territorio);
		}
		MyMap<Integer, Jugador> jJugadores = new MyMap<>();
		String[] jsJugadores = HttpClientSynchronous.doGet("http://localhost:8081/player/getbyhash/" + hashCode)
				.split(";");
		for (String object : jsJugadores) {
			String[] attributes = object.split(",");
			Jugador jugador = new Jugador(attributes[1], attributes[2], attributes[3]);
			int index = Integer.parseInt(attributes[4]);
			if (pTerritory.containsKey(index)) {
				jugador.setTerritorios(pTerritory.getValue(index));
			} else {
				jugador.setTerritorios(new MyDoubleLinkedList<String>());
			}
			jJugadores.put(index, jugador);
		}
		for (int i = 0; i < jsJugadores.length; i++) {
			jugadorDao.create(jJugadores.getValue(i));
		}
		String[] jsGameDetails = HttpClientSynchronous.doGet("http://localhost:8081/gamedetails/getbyhash/" + hashCode)
				.split(",");
		fase = jsGameDetails[1];
		jugadorSeleccionado = Integer.parseInt(jsGameDetails[2]);
		if (fase.equals("Fase 1: Refuerzos")) {
			tropasObtenidas = Integer.parseInt(jsGameDetails[3]);
		} else if (fase.equals("Fase 3: Fortificacion")) {
			reforzado = jsGameDetails[3].equals("Si");
		}
		initDetails();
		return "game.xhtml?faces-redirect=true";
	}

	public void generateFileResults() {
		String pathPdf = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/files/GameDetails.pdf");
		String pathZip = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/files/GameDetails.zip");
		String pathImg = FacesContext.getCurrentInstance().getExternalContext().getRealPath("/images/risklogo.png");
		String header = "Resultados del juego";
		int totalTroops = 0;
		for (String aux : territorios)
			totalTroops += territorioDao.getTerritorios().getValue(aux).getNumeroTropas();
		String content = "Muchas gracias por jugar el juego. Felicidades "
				+ jugadorDao.getJugadores().get(jugadorSeleccionado).getNombre() + " ganaste el juego con un total de "
				+ totalTroops + " tropas. Gracias a los otros jugadores, gran juego.";
		String[] lplayers = new String[jugadorDao.getJugadores().getSize() + 1];
		lplayers[0] = "lista jugadores:";
		for (int i = 0; i < jugadorDao.getJugadores().getSize(); i++) {
			lplayers[i + 1] = jugadorDao.getJugadores().get(i).getNombre() + ". "
					+ (i == jugadorSeleccionado ? "Ganador." : "Perdedor");
		}
		String content2 = "Codigo del juego: " + hashCode;
		FileHandler.generateZIP(pathPdf, pathZip, pathImg, header, content, content2, lplayers);
	}

	public String exitGame() {
		return "index.xhtml?faces-redirect=true";
	}

	public String endGame() {
		for (int i = 0; i < jugadorDao.getJugadores().getSize(); i++) {
			Jugador current = jugadorDao.getJugadores().get(i);
			sendEmailToPlayers(current.getNombre(), current.getEmail(), i == jugadorSeleccionado);
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
		String content = "Gracias por jugar el juego.\n\n";
		if (isWinner) {
			int totalTroops = 0;
			for (String aux : territorios)
				totalTroops += territorioDao.getTerritorios().getValue(aux).getNumeroTropas();
			content += "Felicidades " + name
					+ " por tu victoria en el juego! Tu estrategia y habilidades tacticas te llevaron a la cima del tablero."
					+ " Eres un verdadero maestro de la conquista. Haz conquistado todos los terrenos con un total de "
					+ totalTroops + " tropas." + "\n\nResultados generales:\n\n";
		} else {
			content += "Aunque esta vez no has conseguido la victoria" + name
					+ ", quiero destacar tu valentia y tu enfoque estrategico."
					+ " Tu participacion demuestra tu dedicacion y habilidades tacticas, y es evidente que enfrentaste los retos con determinacion."
					+ "\n\nResultados generales:\n\n";
		}
		for (int i = 0; i < jugadorDao.getJugadores().getSize(); i++) {
			content += jugadorDao.getJugadores().get(i).getNombre() + ". "
					+ (i == jugadorSeleccionado ? "Ganador.\n" : "Perdedor\n");
		}
		try {
			MailSender.sendEamil(email, content);
		} catch (MessagingException e) {
			System.err.println("Error enviando el mail");
		}
	}

	public String generarHash(int password) {
		return HttpClientSynchronous.doGet("http://localhost:8081/gamedetails/generatehash/" + password);
	}

}