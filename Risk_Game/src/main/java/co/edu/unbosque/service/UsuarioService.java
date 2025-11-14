package co.edu.unbosque.service;

import co.edu.unbosque.model.UsuarioDTO;
import co.edu.unbosque.model.persistence.UsuarioDAO;

public class UsuarioService {

    private UsuarioDAO usuarioDAO;

    public UsuarioService() {
        usuarioDAO = new UsuarioDAO();
        usuarioDAO.leerArchivoSerializado();
    }

    public boolean crearUsuario(UsuarioDTO dto) {
        return usuarioDAO.crear(dto);
    }

    public boolean eliminarUsuario(String username) {
        return usuarioDAO.eliminarPorNombre(username);
    }

    public UsuarioDTO buscarUsuario(String username) {
        var u = usuarioDAO.findByNombre(username);
        return u == null ? null : usuarioDAO.buscarPorId(u.getId().intValue());
    }

    public String mostrarUsuarios() {
        return usuarioDAO.mostrarTodo();
    }

    public void guardar() {
        usuarioDAO.escribirArchivo();
        usuarioDAO.escribirArchivoSerializado();
    }

    public UsuarioDAO getDAO() {
        return usuarioDAO;
    }
}
