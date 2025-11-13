package co.edu.unbosque.model.persistence;

import java.util.Objects;

import co.edu.unbosque.model.UsuarioDTO;
import co.edu.unbosque.util.MyDoubleLinkedList;

public class UsuarioDAO implements CRUDOperation<Integer, UsuarioDTO> {

	private MyDoubleLinkedList<UsuarioDTO> usuarios;

	public UsuarioDAO() {
		usuarios = new MyDoubleLinkedList<>();
	}

	public UsuarioDAO(MyDoubleLinkedList<UsuarioDTO> usuarios) {
		super();
		this.usuarios = usuarios;
	}

	public MyDoubleLinkedList<UsuarioDTO> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(MyDoubleLinkedList<UsuarioDTO> usuarios) {
		this.usuarios = usuarios;
	}

	@Override
	public void create(UsuarioDTO data) {
		usuarios.add(data);
	}

	@Override
	public int update(Integer id, UsuarioDTO data) {
		if (id >= 0 && id < usuarios.getSize()) {
			usuarios.set(id, data);
			return 0;
		}
		return 1;
	}

	@Override
	public int delete(Integer id) {
		if (id >= 0 && id < usuarios.getSize()) {
			usuarios.remove(id);
			return 0;
		}
		return 1;
	}

	@Override
	public int hashCode() {
		return Objects.hash(usuarios);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioDAO other = (UsuarioDAO) obj;
		return Objects.equals(usuarios, other.usuarios);
	}

}
