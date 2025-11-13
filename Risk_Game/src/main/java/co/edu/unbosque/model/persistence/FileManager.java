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
 * Clase que gestiona la lectura y escritura de archivos de texto y serializados.
 * Incluye métodos para crear carpetas, escribir y leer archivos,
 * así como registrar inicios de sesión en archivos CSV y DAT.
 */
public class FileManager {

    /**
     * Archivo de texto general para operaciones de lectura y escritura.
     */
    private static File archivo;

    /**
     * Scanner para leer archivos de texto.
     */
    private static Scanner lectorArchivoTexto;

    /**
     * PrintWriter para escribir archivos de texto.
     */
    private static PrintWriter escritorArchivoTexto;

    /**
     * Ruta de la carpeta donde se almacenan los archivos.
     */
    private static final String RUTA_CARPETA = "src/archivos";

    /**
     * FileOutputStream para escribir archivos serializados.
     */
    private static FileOutputStream fos;

    /**
     * ObjectOutputStream para escribir objetos serializados.
     */
    private static ObjectOutputStream oos;

    /**
     * FileInputStream para leer archivos serializados.
     */
    private static FileInputStream fis;

    /**
     * ObjectInputStream para leer objetos serializados.
     */
    private static ObjectInputStream ois;

    /**
     * Crea la carpeta donde se almacenan los archivos si no existe.
     */
    private static void crearCarpeta() {
        archivo = new File(RUTA_CARPETA);
        if (!archivo.exists() || !archivo.isDirectory()) {
            archivo.mkdir();
        }
    }

    /**
     * Escribe contenido en un archivo de texto.
     * 
     * @param nombreArchivo Nombre del archivo donde se escribirá.
     * @param contenido     Texto que se escribirá en el archivo.
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
     * Escribe un objeto serializado en un archivo.
     * 
     * @param nombreArchivo Nombre del archivo serializado.
     * @param contenido     Objeto que se va a serializar y guardar.
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
     * Lee el contenido de un archivo de texto y lo retorna como String.
     * 
     * @param nombreArchivo Nombre del archivo de texto a leer.
     * @return Contenido completo del archivo como String, o null si hay error.
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
     * Registra un inicio de sesión en un archivo CSV con formato "Usuario,Contraseña,FechaHora".
     * 
     * @param usuario    Nombre de usuario.
     * @param contraseña Contraseña del usuario.
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
     * Registra un inicio de sesión en un archivo serializado .dat como lista de strings.
     * 
     * @param usuario    Nombre de usuario.
     * @param contraseña Contraseña del usuario.
     */
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
     * Obtiene la fecha y hora actual en formato "yyyy-MM-dd HH:mm:ss".
     * 
     * @return Fecha y hora actual formateada.
     */
    public static String obtenerFechaHora() {
        return java.time.LocalDateTime.now()
                .format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /**
     * Lee un archivo serializado y devuelve su contenido como objeto.
     * 
     * @param nombreArchivo Nombre del archivo serializado a leer.
     * @return Objeto leído del archivo, o null si el archivo no existe o está vacío.
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
