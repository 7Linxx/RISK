package co.edu.unbosque.model.persistence;

import co.edu.unbosque.model.Jugador;
import co.edu.unbosque.model.JugadorDTO;
import co.edu.unbosque.model.Territorio;
import co.edu.unbosque.model.TerritorioDTO;
import co.edu.unbosque.model.Usuario;
import co.edu.unbosque.model.UsuarioDTO;
import co.edu.unbosque.util.MyDoubleLinkedList;

/**
 * Utilidad estática para mapear entre entidades de dominio y sus DTOs
 * correspondientes.
 *
 * <p>
 * Proporciona conversiones bidireccionales entre {@link Jugador} /
 * {@link JugadorDTO}, {@link Territorio} / {@link TerritorioDTO} y
 * {@link Usuario} / {@link UsuarioDTO}. Las conversiones preservan las
 * relaciones anidadas (por ejemplo, territorios adyacentes o territorios
 * pertenecientes) creando listas equivalentes de DTOs/entidades.
 * </p>
 * 
 * @author Mariana Pineda
 * @since 1.0
 */
public class DataMapper {

	/**
	 * Convierte un {@link JugadorDTO} en un {@link Jugador}, mapeando también sus
	 * territorios pertenecientes.
	 *
	 * @param dto DTO del jugador
	 * @return entidad Jugador correspondiente
	 */
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

	/**
	 * Convierte un {@link Jugador} en su {@link JugadorDTO}, mapeando también sus
	 * territorios pertenecientes a DTOs.
	 *
	 * @param entity entidad Jugador
	 * @return DTO correspondiente
	 */
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

	/**
	 * Convierte un {@link TerritorioDTO} en un {@link Territorio}, mapeando sus
	 * territorios adyacentes recursivamente.
	 *
	 * @param dto DTO del territorio
	 * @return entidad Territorio correspondiente
	 */
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

	/**
	 * Convierte un {@link Territorio} en su {@link TerritorioDTO}, mapeando sus
	 * territorios adyacentes recursivamente.
	 *
	 * @param entity entidad Territorio
	 * @return DTO correspondiente
	 */
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

	/**
	 * Convierte un {@link UsuarioDTO} en un {@link Usuario}.
	 *
	 * @param dto DTO del usuario
	 * @return entidad Usuario correspondiente
	 */
	public static Usuario usuarioDTOtoUsuario(UsuarioDTO dto) {
		return new Usuario(dto.getId(), dto.getUsername(), dto.getCorreo(), dto.getContrasenia(),
				dto.getFechaNacimiento());
	}

	/**
	 * Convierte un {@link Usuario} en su {@link UsuarioDTO}.
	 *
	 * @param entity entidad Usuario
	 * @return DTO correspondiente
	 */
	public static UsuarioDTO usuarioToUsuarioDTO(Usuario entity) {
		return new UsuarioDTO(entity.getId(), entity.getUsername(), entity.getCorreo(), entity.getContrasenia(),
				entity.getFechaNacimiento());
	}

}