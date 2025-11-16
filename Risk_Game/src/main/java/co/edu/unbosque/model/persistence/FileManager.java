package co.edu.unbosque.model.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Utilidad para la gestión de archivos de la aplicación.
 *
 * <p>
 * Proporciona métodos estáticos para:
 * <ul>
 * <li>Crear la carpeta de almacenamiento si no existe.</li>
 * <li>Escribir y leer archivos de texto.</li>
 * <li>Escribir y leer objetos serializados (.dat).</li>
 * <li>Registrar inicios de sesión tanto en CSV como en un archivo
 * serializado.</li>
 * <li>Obtener la fecha y hora actual en un formato estandarizado.</li>
 * </ul>
 * </p>
 *
 * Nota: todos los métodos y atributos son estáticos y la clase no mantiene
 * estado compartido más allá de referencias a streams/archivos temporales.
 *
 * @author Mariana Pineda
 * @since 1.0
 */
public class FileManager {

	/**
	 * Archivo o carpeta general usado internamente para operaciones.
	 */
	private static File archivo;

	/**
	 * Scanner para lectura de archivos de texto.
	 */
	private static Scanner lectorArchivoTexto;

	/**
	 * PrintWriter para escritura en archivos de texto.
	 */
	private static PrintWriter escritorArchivoTexto;

	/**
	 * Ruta de la carpeta donde se almacenan los archivos de la aplicación.
	 */
	private static final String RUTA_CARPETA = "src/archivos";

	/**
	 * FileOutputStream para escritura de archivos serializados.
	 */
	private static FileOutputStream fos;

	/**
	 * ObjectOutputStream para escritura de objetos serializados.
	 */
	private static ObjectOutputStream oos;

	/**
	 * FileInputStream para lectura de archivos serializados.
	 */
	private static FileInputStream fis;

	/**
	 * ObjectInputStream para lectura de objetos serializados.
	 */
	private static ObjectInputStream ois;

	/**
	 * Crea la carpeta de almacenamiento (RUTA_CARPETA) si no existe.
	 */
	private static void crearCarpeta() {
		archivo = new File(RUTA_CARPETA);
		if (!archivo.exists() || !archivo.isDirectory()) {
			archivo.mkdir();
		}
	}

	/**
	 * Escribe el contenido proporcionado en un archivo de texto dentro de la
	 * carpeta de la aplicación. Si el archivo no existe se crea.
	 *
	 * @param nombreArchivo nombre del archivo (por ejemplo "miarchivo.txt")
	 * @param contenido     texto que se escribirá en el archivo
	 */
	public static void escribirEnArchivoTexto(String nombreArchivo, String contenido) {
		try {
			archivo = new File(RUTA_CARPETA + "/" + nombreArchivo);
			if (!archivo.exists()) {
				archivo.createNewFile();
			}
			escritorArchivoTexto = new PrintWriter(archivo);
			escritorArchivoTexto.println(contenido);
			escritorArchivoTexto.close();
		} catch (IOException e) {
			System.out.println("Error al crear el archivo (escritura)");
			e.printStackTrace();
		}
	}

	/**
	 * Serializa un objeto y lo escribe en un archivo dentro de la carpeta de la
	 * aplicación. Si el archivo no existe se crea.
	 *
	 * @param nombreArchivo nombre del archivo serializado (por ejemplo "datos.dat")
	 * @param contenido     objeto a serializar y guardar
	 */
	public static void escribirArchivoSerializado(String nombreArchivo, Object contenido) {
		try {
			archivo = new File(RUTA_CARPETA + "/" + nombreArchivo);
			if (!archivo.exists()) {
				archivo.createNewFile();
			}
			fos = new FileOutputStream(archivo);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(contenido);
			oos.close();
			fos.close();
		} catch (Exception e) {
			System.out.println("Error en la escritura del archivo serializado");
			e.printStackTrace();
		}
	}

	/**
	 * Lee el contenido completo de un archivo de texto dentro de la carpeta de la
	 * aplicación. Si el archivo no existe se crea y se devuelve una cadena vacía (o
	 * null si ocurre un error).
	 *
	 * @param nombreArchivo nombre del archivo de texto a leer
	 * @return contenido completo del archivo (con saltos de línea) o null si ocurre
	 *         un error
	 */
	public static String leerArchivoTexto(String nombreArchivo) {
		try {
			archivo = new File(RUTA_CARPETA + "/" + nombreArchivo);
			if (!archivo.exists()) {
				archivo.createNewFile();
			}
			lectorArchivoTexto = new Scanner(archivo);
			String contenido = "";
			while (lectorArchivoTexto.hasNext()) {
				contenido += lectorArchivoTexto.nextLine() + "\n";
			}
			lectorArchivoTexto.close();
			return contenido;
		} catch (IOException e) {
			System.out.println("Error al crear el archivo (lectura)");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Registra un intento de inicio de sesión en formato CSV (archivo
	 * "registro_logins.csv") con la cabecera "Usuario,Contraseña,FechaHora".
	 *
	 * @param usuario    nombre de usuario
	 * @param contraseña contraseña asociada (tener precaución por seguridad)
	 */
	public static void registrarInicioSesion(String usuario, String contraseña) {
		File archivo = new File(RUTA_CARPETA + "/" + "registro_logins.csv");
		PrintWriter escritorArchivoTexto = null;

		try {
			boolean archivoNuevo = !archivo.exists();

			if (archivoNuevo) {
				archivo.createNewFile();
			}

			escritorArchivoTexto = new PrintWriter(new FileOutputStream(archivo, true));

			if (archivoNuevo) {
				escritorArchivoTexto.println("Usuario,Contraseña,FechaHora");
			}

			if (usuario != null && contraseña != null) {
				escritorArchivoTexto.println(usuario + "," + contraseña + "," + obtenerFechaHora());
			}

		} catch (IOException e) {
			System.out.println("Error al escribir en el archivo de registros.");
			e.printStackTrace();
		} finally {
			if (escritorArchivoTexto != null) {
				escritorArchivoTexto.close();
			}
		}
	}

	/**
	 * Registra un intento de inicio de sesión en un archivo serializado (.dat). Se
	 * almacena una lista de strings donde cada elemento tiene la forma:
	 * "usuario,contraseña,fechaHora".
	 *
	 * @param usuario    nombre de usuario
	 * @param contraseña contraseña asociada
	 */
	@SuppressWarnings("unchecked")
	public static void registrarInicioSesionDat(String usuario, String contraseña) {
		try {
			archivo = new File(RUTA_CARPETA + "/" + "registro_logins.dat");

			if (!archivo.exists()) {
				archivo.createNewFile();
			}

			List<String> registros = new ArrayList<>();
			if (archivo.length() > 0) {
				fis = new FileInputStream(archivo);
				ois = new ObjectInputStream(fis);
				registros = (List<String>) ois.readObject();
				ois.close();
				fis.close();
			}

			if (usuario != null && contraseña != null) {
				registros.add(usuario + "," + contraseña + "," + obtenerFechaHora());
			}

			fos = new FileOutputStream(archivo);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(registros);
			oos.close();
			fos.close();

		} catch (IOException | ClassNotFoundException e) {
			System.out.println("Error al manejar el archivo de registros .dat.");
			e.printStackTrace();
		}
	}

	/**
	 * Obtiene la fecha y hora local actual formateada como "yyyy-MM-dd HH:mm:ss".
	 *
	 * @return fecha y hora actual formateada
	 */
	public static String obtenerFechaHora() {
		return java.time.LocalDateTime.now()
				.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}

	/**
	 * Lee un archivo serializado dentro de la carpeta de la aplicación y devuelve
	 * el objeto leído (por ejemplo una lista o estructura serializada).
	 *
	 * @param nombreArchivo nombre del archivo serializado (por ejemplo "datos.dat")
	 * @return objeto leído del archivo, o null si el archivo no existe, está vacío
	 *         o hay error
	 */
	public static Object leerArchivoSerializado(String nombreArchivo) {
		Object contenido = null;
		try {
			archivo = new File(RUTA_CARPETA + "/" + nombreArchivo);
			if (!archivo.exists()) {
				archivo.createNewFile();
				return null;
			}
			if (archivo.length() == 0) {
				return null;
			}

			fis = new FileInputStream(archivo);
			ois = new ObjectInputStream(fis);
			contenido = ois.readObject();
			fis.close();
			ois.close();
		} catch (IOException e) {
			System.out.println("Error al leer archivo serializado");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.out.println("Error al contener el archivo del archivo");
			e.printStackTrace();
		}

		return contenido;
	}

}