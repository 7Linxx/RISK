package co.edu.unbosque.model.persistence;

import co.edu.unbosque.model.UsuarioDTO;
import co.edu.unbosque.util.MyDoubleLinkedList;

public class UsuarioDAO implements CRUDOperation<Long, UsuarioDTO> {

	private MyDoubleLinkedList<UsuarioDTO> usuarios;
}
