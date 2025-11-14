package co.edu.unbosque.beans;

import java.io.Serializable;
import java.util.UUID;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import co.edu.unbosque.model.UsuarioDTO;
import co.edu.unbosque.service.UsuarioService;

@Named("authBean")
@SessionScoped
public class AuthBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private UsuarioService usuarioService;

	private String username;
	private String password;
	private String email;

	private UsuarioDTO usuarioLogueado;

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

		// Generar ID aquí mismo
		long id = Math.abs(UUID.randomUUID().getMostSignificantBits());

		UsuarioDTO nuevo = new UsuarioDTO();
		nuevo.setId(id);
		nuevo.setCorreo(email);
		;
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
}
