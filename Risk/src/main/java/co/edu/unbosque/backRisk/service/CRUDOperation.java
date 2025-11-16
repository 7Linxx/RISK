package co.edu.unbosque.backRisk.service;

import java.util.List;

import co.edu.unbosque.backRisk.util.MyDoubleLinkedList;
import co.edu.unbosque.backRisk.util.MyLinkedList;

/**
 * Interfaz genérica que define operaciones CRUD básicas para la capa de
 * servicio.
 * <p>
 * Las implementaciones de esta interfaz deben definir la lógica concreta de
 * creación, lectura, actualización y eliminación de entidades/DTOs del tipo T.
 * </p>
 * 
 * @param <T> Tipo de dato que serán manejados por las operaciones CRUD (por
 *            ejemplo DTOs o entidades).
 * 
 * @author Mariana Pineda
 * @version 2.0
 */
public interface CRUDOperation<T> {

	/**
	 * Crea un nuevo registro/entidad a partir del objeto proporcionado.
	 * <p>
	 * El significado del valor de retorno queda a cargo de la implementación (por
	 * ejemplo: 0 éxito, 1 ya existe, etc.).
	 * </p>
	 * 
	 * @param data Objeto con la información a crear.
	 * @return código de estado definido por la implementación.
	 */
	public int create(T data);

	/**
	 * Obtiene todos los registros/objetos del tipo T.
	 * 
	 * @return lista personalizada (MyDoubleLinkedList) con todos los elementos.
	 */
	public MyDoubleLinkedList<T> getAll();

	/**
	 * Elimina el registro identificado por el identificador proporcionado.
	 * <p>
	 * El significado del valor de retorno queda a cargo de la implementación (por
	 * ejemplo: 0 eliminado correctamente, 1 no encontrado, etc.).
	 * </p>
	 * 
	 * @param id Identificador del registro a eliminar.
	 * @return código de estado definido por la implementación.
	 */
	public int deleteById(Long id);

	/**
	 * Actualiza el registro identificado por id con la nueva información
	 * proporcionada en newData.
	 * <p>
	 * El significado del valor de retorno queda a cargo de la implementación (por
	 * ejemplo: 0 actualizado, 1 conflicto de datos, 2 no encontrado, etc.).
	 * </p>
	 * 
	 * @param id      Identificador del registro a actualizar.
	 * @param newData Nueva información para actualizar el registro.
	 * @return código de estado definido por la implementación.
	 */
	public int updateById(Long id, T newData);

	/**
	 * Retorna el conteo total de registros manejados por la implementación.
	 * 
	 * @return número de registros.
	 */
	public long count();

	/**
	 * Indica si existe un registro con el identificador proporcionado.
	 * 
	 * @param id Identificador a verificar.
	 * @return true si existe, false en caso contrario.
	 */
	public boolean exist(Long id);
}