package co.edu.unbosque.beans;

import java.io.Serializable;
import java.util.Random;
import java.util.UUID;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

import co.edu.unbosque.model.UsuarioDTO;
import co.edu.unbosque.service.UsuarioService;

@Named("authBean")
@ViewScoped
public class AuthBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private UsuarioService usuarioService;

	// ===========================================================
	// CAMPOS PARA LOGIN NORMAL
	// ===========================================================
	private String username;
	private String password;
	private String email;

	private UsuarioDTO usuarioLogueado;

	// ===========================================================
	// CAMPOS PARA CREAR PARTIDA (N jugadores)
	// ===========================================================
	private int numPlayers = 2;

	private String[] emails = new String[4];
	private String[] names = new String[4];
	private String[] colors = new String[4];

	// Aquí se guardan los usuarios generados con ID aleatorio
	private UsuarioDTO[] usuariosPartida = new UsuarioDTO[4];

	public AuthBean() {
		usuarioService = new UsuarioService();
	}

	// ===========================================================
	// LOGIN
	// ===========================================================
	public String login() {
		UsuarioDTO u = usuarioService.buscarUsuario(username);

		if (u == null || !u.getContrasenia().equals(password)) {
			return null;
		}

		usuarioLogueado = u;

		return "menu.xhtml?faces-redirect=true";
	}

	// ===========================================================
	// REGISTER
	// ===========================================================
	public String register() {

		// Generar ID aleatorio
		long id = Math.abs(UUID.randomUUID().getMostSignificantBits());

		UsuarioDTO nuevo = new UsuarioDTO();
		nuevo.setId(id);
		nuevo.setCorreo(email);
		nuevo.setUsername(username);
		nuevo.setContrasenia(password);

		boolean creado = usuarioService.crearUsuario(nuevo);

		if (!creado) {
			return null;
		}

		usuarioService.guardar();

		return "index.xhtml?faces-redirect=true";
	}

	// ===========================================================
	// CREAR USUARIOS PARA LA PARTIDA
	// ===========================================================
	public String registrarUsuariosParaPartida() {

		Random r = new Random();

		for (int i = 0; i < numPlayers; i++) {

			long id = Math.abs(r.nextLong()); // ID aleatorio

			UsuarioDTO u = new UsuarioDTO();
			u.setId(id);
			u.setUsername(names[i]);
			u.setCorreo(emails[i]);
			u.setContrasenia("default"); // No importa para partida

			usuariosPartida[i] = u;
		}

		return "newgame.xhtml?faces-redirect=true";
	}

	// ===========================================================
	// GETTERS / SETTERS
	// ===========================================================
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

	public UsuarioDTO getUsuarioLogueado() {
		return usuarioLogueado;
	}

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

	public UsuarioDTO[] getUsuariosPartida() {
		return usuariosPartida;
	}
}
