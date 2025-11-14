package co.edu.unbosque.beans;

import java.io.Serializable;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import co.edu.unbosque.model.JugadorDTO;
import co.edu.unbosque.model.PlayerDTO;
import co.edu.unbosque.model.TerritoryDTO;
import co.edu.unbosque.model.Usuario;
import co.edu.unbosque.model.persistence.DataMapper;
import co.edu.unbosque.model.persistence.JugadorDAO;
import co.edu.unbosque.model.persistence.TerritoryDAO;
import co.edu.unbosque.util.MyMap;
import co.edu.unbosque.util.MyDoubleLinkedList;
import java.security.SecureRandom;

@Named
@ViewScoped
public class MapBean implements Serializable {

	private JugadorDAO jugadorDao;
	private TerritoryDAO territoryDao;

	private int numPlayers;
	private String[] names;
	private String[] emails;
	private String[] passwords;
	private String[] colors;

	private String hashCode;
	private String[] hashCodes;
	private int sizeHashes = 1;

	public MapBean() {
		jugadorDao = new JugadorDAO();
		territoryDao = new TerritoryDAO();
	}

	// =============================
	// INIT PLAYERS
	// =============================
	public boolean initPlayers() {
		boolean required = true;
		MyMap<String, Boolean> vColor = new MyMap<>();
		MyMap<String, Boolean> vName = new MyMap<>();

		for (int i = 0; i < numPlayers; i++) {

			// === Nombre válido
			if (names[i].isEmpty() || names[i].length() > 8 || names[i].matches(".*[,;].*")) {
				addMessage(FacesMessage.SEVERITY_ERROR, "Invalid name player " + (i + 1),
						"The name must be 1–8 characters and cannot contain ',' or ';'.");
				required = false;
				continue;
			}

			// === Nombre repetido
			if (vName.containsKey(names[i])) {
				addMessage(FacesMessage.SEVERITY_ERROR, "Name already taken",
						"Player " + (i + 1) + " must choose another name.");
				required = false;
			} else {
				vName.put(names[i], true);
			}

			// === Email válido
			if (!emails[i].matches("^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$")) {
				addMessage(FacesMessage.SEVERITY_ERROR, "Invalid email player " + (i + 1),
						"Please enter a valid email.");
				required = false;
			}

			// === Color válido
			if (colors[i].isEmpty() || vColor.containsKey(colors[i])) {
				addMessage(FacesMessage.SEVERITY_ERROR, "Invalid color player " + (i + 1),
						"Color is empty or repeated.");
				required = false;
			} else {
				vColor.put(colors[i], true);
			}

			// === Crear jugador vacío y válido
			JugadorDTO newPlayer = new JugadorDTO(names[i], // nombre
					colors[i], // color
					new Usuario(), // usuario vacío por ahora
					0, // tropas disponibles al inicio
					new MyDoubleLinkedList<>() // lista vacía de territorios
			);

			jugadorDao.getJugadores().add(DataMapper.jugadorDTOtoJugador(newPlayer));
		}

		return required;
	}

	// =============================
	// NAVEGACIÓN AL JUEGO
	// =============================
	public String startGame() {
		if (!initPlayers())
			return null;
		return "game.xhtml?faces-redirect=true";
	}

	// =============================
	// UTILS
	// =============================
	public void addMessage(FacesMessage.Severity s, String sum, String det) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(s, sum, det));
	}

	// GETTERS Y SETTERS …

}
