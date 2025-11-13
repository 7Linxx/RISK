package co.edu.unbosque.model.persistence;

import co.edu.unbosque.model.Jugador;
import co.edu.unbosque.model.JugadorDTO;
import co.edu.unbosque.model.Territorio;
import co.edu.unbosque.model.TerritorioDTO;
import co.edu.unbosque.model.Usuario;
import co.edu.unbosque.model.UsuarioDTO;
import co.edu.unbosque.util.MyDoubleLinkedList;

public class DataMapper {

	public static Jugador jugadorDTOtoJugador(JugadorDTO dto) {
		MyDoubleLinkedList<Territorio> territorios = new MyDoubleLinkedList<>();
		if (dto.getTerritoriosPertenecientes() != null) {
			co.edu.unbosque.util.DNode<TerritorioDTO> nodo = dto.getTerritoriosPertenecientes().getHead();
			while (nodo != null) {
				territorios.insert(territorioDTOtoTerritorio(nodo.getInfo()));
				nodo = nodo.getNext();
			}
		}
		return new Jugador(dto.getName(), dto.getColor(), dto.getUser(), dto.getTropasDisponibles(), territorios);
	}

	public static JugadorDTO jugadorToJugadorDTO(Jugador entity) {
		MyDoubleLinkedList<TerritorioDTO> territoriosDTO = new MyDoubleLinkedList<>();
		if (entity.getTerritoriosPertenecientes() != null) {
			co.edu.unbosque.util.DNode<Territorio> nodo = entity.getTerritoriosPertenecientes().getHead();
			while (nodo != null) {
				territoriosDTO.insert(territorioToTerritorioDTO(nodo.getInfo()));
				nodo = nodo.getNext();
			}
		}
		return new JugadorDTO(entity.getName(), entity.getColor(), entity.getUser(), entity.getTropasDisponibles(),
				territoriosDTO);
	}

	public static Territorio territorioDTOtoTerritorio(TerritorioDTO dto) {
		MyDoubleLinkedList<Territorio> territorios = new MyDoubleLinkedList<>();
		if (dto.getTerritoriosAdyacentes() != null) {
			co.edu.unbosque.util.DNode<TerritorioDTO> nodo = dto.getTerritoriosAdyacentes().getHead();
			while (nodo != null) {
				territorios.insert(territorioDTOtoTerritorio(nodo.getInfo()));
				nodo = nodo.getNext();
			}
		}
		return new Territorio(dto.getNombre(), dto.getCantidadTropas(), dto.getDuenio(), territorios);
	}

	public static TerritorioDTO territorioToTerritorioDTO(Territorio entity) {
		MyDoubleLinkedList<TerritorioDTO> territoriosDTO = new MyDoubleLinkedList<>();
		if (entity.getTerritoriosAdyacentes() != null) {
			co.edu.unbosque.util.DNode<Territorio> nodo = entity.getTerritoriosAdyacentes().getHead();
			while (nodo != null) {
				territoriosDTO.insert(territorioToTerritorioDTO(nodo.getInfo()));
				nodo = nodo.getNext();
			}
		}
		return new TerritorioDTO(entity.getNombre(), entity.getCantidadTropas(), entity.getDuenio(), territoriosDTO);
	}

	public static Usuario usuarioDTOtoUsuario(UsuarioDTO dto) {
		return new Usuario(dto.getId(), dto.getUsername(), dto.getCorreo(), dto.getContrasenia(),
				dto.getFechaNacimiento());
	}

	public static UsuarioDTO usuarioToUsuarioDTO(Usuario entity) {
		return new UsuarioDTO(entity.getId(), entity.getUsername(), entity.getCorreo(), entity.getContrasenia(),
				entity.getFechaNacimiento());
	}

}
