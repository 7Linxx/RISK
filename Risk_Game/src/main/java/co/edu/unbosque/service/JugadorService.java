package co.edu.unbosque.service;

import co.edu.unbosque.model.JugadorDTO;
import co.edu.unbosque.model.persistence.DataMapper;
import co.edu.unbosque.model.persistence.JugadorDAO;

public class JugadorService {

	private JugadorDAO jugadorDAO;

	public JugadorService() {
		jugadorDAO = new JugadorDAO();
		jugadorDAO.leerArchivoSerializado();
	}

	public boolean crearJugador(JugadorDTO dto) {
		return jugadorDAO.crear(dto);
	}

	public boolean eliminarJugador(String nombre) {
		return jugadorDAO.eliminarPorNombre(nombre);
	}

	public String mostrarJugadores() {
		return jugadorDAO.mostrarTodo();
	}

	public JugadorDTO buscarJugador(String nombre) {
		var j = jugadorDAO.findByNombre(nombre);
		return j == null ? null : DataMapper.jugadorToJugadorDTO(j);
	}

	public void guardar() {
		jugadorDAO.escribirArchivo();
		jugadorDAO.escribirArchivoSerializado();
	}

	public JugadorDAO getDAO() {
		return jugadorDAO;
	}
}
