package com.santiparra.filemanagment;

import java.io.*;
import java.util.*;

import com.santiparra.cars.dao.util.Config;
import com.santiparra.cars.entities.Person;

/**
 * Clase que maneja la escritura y lectura de archivos CSV para la entidad
 * Person.
 */
public class CSVHandler {

	/**
	/**
     * Crea la carpeta de salida si no existe.
     */
    private static void ensureOutputFolderExists() {
        File directory = new File("target/output");
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (!created) {
                System.out.println("No se pudo crear la carpeta 'target/output'. Verifica los permisos.");
            }
        }
    }

	/**
	 * Escribe una lista de personas en un archivo CSV.
	 * 
	 * @param persons Lista de personas a escribir en el archivo.
	 * @throws IOException Si ocurre un error al escribir el archivo.
	 */
	public static void writeToCSV(List<Person> persons) throws IOException {
		ensureOutputFolderExists();
		File file = new File(Config.FILE_CSV);
		boolean isNewFile = !file.exists();

		if (!isNewFile) {
			file.delete();
		}

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			writer.write("driverId,address,name,age");
			writer.newLine();
			for (Person p : persons) {
				writer.write(p.getDriverId() + "," + p.getAddress() + "," + p.getName() + "," + p.getAge());
				writer.newLine();
			}
		}

		if (isNewFile) {
			System.out.println("Archivo CSV creado correctamente: " + Config.FILE_CSV);
		} else {
			System.out.println("Archivo CSV actualizado: " + Config.FILE_CSV);
		}
	}

	/**
	 * Lee una lista de personas desde un archivo CSV.
	 * 
	 * @return Lista de objetos Person.
	 * @throws IOException Si ocurre un error al leer el archivo.
	 */
	public static List<Person> readFromCSV() throws IOException {
		List<Person> persons = new ArrayList<>();
		File file = new File(Config.FILE_CSV);

		if (!file.exists()) {
			System.out.println("El archivo CSV no existe: " + Config.FILE_CSV);
			return persons;
		}

		try (BufferedReader reader = new BufferedReader(new FileReader(Config.FILE_CSV))) {
			String line = reader.readLine(); // Saltar encabezado
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				persons.add(new Person(parts[0], parts[1], parts[2], Integer.parseInt(parts[3])));
			}
		}

		System.out.println("Archivo CSV leído correctamente: " + Config.FILE_CSV);
		return persons;
	}
}
