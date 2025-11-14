package co.edu.unbosque.beans;

import java.io.Serializable;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import co.edu.unbosque.model.JugadorDTO;
import co.edu.unbosque.model.TerritorioDTO;
import co.edu.unbosque.model.UsuarioDTO;
import co.edu.unbosque.service.JugadorService;
import co.edu.unbosque.service.TerritorioService;
import co.edu.unbosque.service.GameService;
import co.edu.unbosque.util.MyDoubleLinkedList;

@Named("gameBean")
@ViewScoped
public class GameBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private JugadorService jugadorService;
	private TerritorioService territorioService;
	private GameService gameService;

	@Inject
	private AuthBean authBean; // <<====== Importante

	public GameBean() {
		jugadorService = new JugadorService();
		territorioService = new TerritorioService();
		gameService = new GameService(jugadorService, territorioService);
	}

	// ===========================================================
	// CREAR PARTIDA
	// ===========================================================
	public String init() {

		int numPlayers = authBean.getNumPlayers();
		UsuarioDTO[] usuarios = authBean.getUsuariosPartida();
		String[] colors = authBean.getColors();

		// ===========================================================
		// 1. Crear Jugadores a partir de AuthBean
		// ===========================================================
		for (int i = 0; i < numPlayers; i++) {

			UsuarioDTO user = usuarios[i];
			JugadorDTO jugador = new JugadorDTO(user.getUsername(), colors[i], user, // Usuario asociado
					30, // Tropas iniciales
					new MyDoubleLinkedList<>() // Territorios
			);

			jugadorService.crearJugador(jugador);
		}

		jugadorService.guardar();

		// ===========================================================
		// 2. Crear territorios base si no existen
		// ===========================================================
		if (territorioService.getDAO().getTerritorios().getSize() == 0) {

			String[] territories = { "Alaska", "Irkutsk", "Brasil", "Argentina", "Perú", "Islandia", "Egipto",
					"Ucrania" };

			for (String t : territories) {

				TerritorioDTO dto = new TerritorioDTO(t, 1, // Cantidad de tropas
						null, // Sin dueño
						new MyDoubleLinkedList<>() // Adyacentes vacíos
				);

				territorioService.crearTerritorio(dto);
			}

			territorioService.guardar();
		}

		// ===========================================================
		// 3. Asignar territorios aleatoriamente
		// ===========================================================
		var allTerr = territorioService.getDAO().getTerritorios();
		var allPlayers = jugadorService.getDAO().getJugadores();

		for (int i = 0; i < allTerr.getSize(); i++) {

			int p = (int) (Math.random() * allPlayers.getSize());

			gameService.asignarTerritorioAJugador(allTerr.get(i).getNombre(), allPlayers.get(p).getName());
		}

		gameService.guardarTodo();

		return "game.xhtml?faces-redirect=true";
	}
}
