package co.edu.unbosque.beans;

import java.io.Serializable;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import co.edu.unbosque.model.JugadorDTO;
import co.edu.unbosque.model.TerritorioDTO;
import co.edu.unbosque.service.JugadorService;
import co.edu.unbosque.service.TerritorioService;
import co.edu.unbosque.service.GameService;
import co.edu.unbosque.util.MyDoubleLinkedList;

@Named("gameBean")
@SessionScoped
public class GameBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private JugadorService jugadorService;
	private TerritorioService territorioService;
	private GameService gameService;

	public GameBean() {
		jugadorService = new JugadorService();
		territorioService = new TerritorioService();
		gameService = new GameService(jugadorService, territorioService);
	}

	// ===========================================================
	// CAMPOS PARA EL XHTML
	// ===========================================================
	private int numPlayers = 2;

	private String[] emails = new String[4];
	private String[] names = new String[4];
	private String[] colors = new String[4];

	// ===========================================================
	// CREAR PARTIDA
	// ===========================================================
	public String init() {

		// ===========================================================
		// 1. Crear jugadores
		// ===========================================================
		for (int i = 0; i < numPlayers; i++) {

			JugadorDTO dto = new JugadorDTO(names[i], colors[i], null, // Usuario asociado (null por ahora)
					30, // tropas iniciales
					new MyDoubleLinkedList<>() // territorios vacíos
			);

			jugadorService.crearJugador(dto);
		}

		jugadorService.guardar();

		// ===========================================================
		// 2. Crear territorios base si no existen
		// ===========================================================
		if (territorioService.getDAO().getTerritorios().getSize() == 0) {

			String[] territories = { "Alaska", "Irkutsk", "Brasil", "Argentina", "Perú", "Islandia", "Egipto",
					"Ucrania" };

			for (String t : territories) {
				TerritorioDTO dto = new TerritorioDTO(t, 1, // tropas
						null, // sin dueño al inicio
						new MyDoubleLinkedList<>() // adyacentes vacíos
				);

				territorioService.crearTerritorio(dto);
			}

			territorioService.guardar();
		}

		// ===========================================================
		// 3. Asignar territorios aleatorios
		// ===========================================================
		var allTerr = territorioService.getDAO().getTerritorios();
		var allPlayers = jugadorService.getDAO().getJugadores();

		for (int i = 0; i < allTerr.getSize(); i++) {
			int p = (int) (Math.random() * allPlayers.getSize());

			gameService.asignarTerritorioAJugador(allTerr.get(i).getNombre(), allPlayers.get(p).getName());
		}

		// Guardar cambios en ambos DAO
		gameService.guardarTodo();

		return "game.xhtml?faces-redirect=true";
	}

	// ===========================================================
	// GETTERS / SETTERS
	// ===========================================================
	public int getNumPlayers() {
		return numPlayers;
	}

	public void setNumPlayers(int numPlayers) {
		this.numPlayers = numPlayers;
	}

	public String[] getEmails() {
		return emails;
	}

	public String[] getNames() {
		return names;
	}

	public String[] getColors() {
		return colors;
	}
}
