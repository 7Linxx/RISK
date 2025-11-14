package co.edu.unbosque.service;

import co.edu.unbosque.model.TerritorioDTO;
import co.edu.unbosque.model.persistence.DataMapper;
import co.edu.unbosque.model.persistence.TerritorioDAO;

public class TerritorioService {

	private TerritorioDAO territorioDAO;

	public TerritorioService() {
		territorioDAO = new TerritorioDAO();
		territorioDAO.leerArchivoSerializado();
	}

	public boolean crearTerritorio(TerritorioDTO dto) {
		return territorioDAO.crear(dto);
	}

	public boolean eliminarTerritorio(String nombre) {
		return territorioDAO.eliminarPorNombre(nombre);
	}

	public String mostrarTerritorios() {
		return territorioDAO.mostrarTodo();
	}

	public TerritorioDTO buscarTerritorio(String nombre) {
		var t = territorioDAO.findByNombre(nombre);
		return t == null ? null : DataMapper.territorioToTerritorioDTO(t);
	}

	public void guardar() {
		territorioDAO.escribirArchivo();
		territorioDAO.escribirArchivoSerializado();
	}

	public TerritorioDAO getDAO() {
		return territorioDAO;
	}
}
