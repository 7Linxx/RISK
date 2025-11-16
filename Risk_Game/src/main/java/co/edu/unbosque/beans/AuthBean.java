package co.edu.unbosque.beans;

import java.io.Serializable;
import java.util.Arrays;
import java.util.UUID;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.annotation.ManagedBean;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

import co.edu.unbosque.model.UsuarioDTO;
import co.edu.unbosque.service.UsuarioService;

@Named("authBean")
@ViewScoped
public class AuthBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private UsuarioService usuarioService;

	// LOGIN
	private String username;
	private String password;
	private String email;
	private UsuarioDTO usuarioLogueado;

	// PARTIDA
	private int numPlayers = 2; // Por defecto 2
	private String[] names = new String[6];
	private String[] colors = new String[6];

	public AuthBean() {
		usuarioService = new UsuarioService();
		// Inicializar arrays
		for (int i = 0; i < 6; i++) {
			names[i] = "";
			colors[i] = "";
		}
	}

	// LOGIN
	public String login() {
		UsuarioDTO u = usuarioService.buscarUsuario(username);
		if (u == null || !u.getContrasenia().equals(password)) {
			addMessage(FacesMessage.SEVERITY_ERROR, "Error", "Credenciales incorrectas");
			return null;
		}
		usuarioLogueado = u;
		
		// Establecer el nombre del usuario logueado en names[0]
		names[0] = username;
		
		return "jugar.xhtml?faces-redirect=true";
	}

	// REGISTER
	public String register() {
		long id = Math.abs(UUID.randomUUID().getMostSignificantBits());
		UsuarioDTO nuevo = new UsuarioDTO();
		nuevo.setId(id);
		nuevo.setCorreo(email);
		nuevo.setUsername(username);
		nuevo.setContrasenia(password);

		boolean creado = usuarioService.crearUsuario(nuevo);
		if (!creado) {
			addMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo crear el usuario");
			return null;
		}

		usuarioService.guardar();
		addMessage(FacesMessage.SEVERITY_INFO, "Éxito", "Usuario registrado correctamente");
		return "index.xhtml?faces-redirect=true";
	}

	private void addMessage(FacesMessage.Severity severity, String summary, String detail) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
	}

	// GETTERS Y SETTERS
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

	public String[] getNames() {
		return names;
	}

	public void setNames(String[] names) {
		this.names = names;
	}

	public String[] getColors() {
		return colors;
	}

	public void setColors(String[] colors) {
		this.colors = colors;
	}
}