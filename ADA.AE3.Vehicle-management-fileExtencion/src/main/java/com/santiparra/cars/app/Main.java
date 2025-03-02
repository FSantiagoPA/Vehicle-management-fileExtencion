package com.santiparra.cars.app;

import java.util.List;
import java.util.Scanner;
import com.santiparra.cars.dao.AccidentDAO;
import com.santiparra.cars.dao.CarDAO;
import com.santiparra.cars.dao.PersonDAO;
import com.santiparra.cars.dao.PolicyDAO;
import com.santiparra.cars.dao.util.Config;
import com.santiparra.cars.entities.Accident;
import com.santiparra.cars.entities.Car;
import com.santiparra.cars.entities.Person;
import com.santiparra.cars.entities.Policy;
import com.santiparra.cars.exceptions.DatabaseException;
import com.santiparra.filemanagment.CSVHandler;
import com.santiparra.filemanagment.DOMHandler;
import com.santiparra.filemanagment.SAXHandler;
import com.santiparra.filemanagment.XSLTransformer;

public class Main {

	private static final Scanner scanner = new Scanner(System.in);
	private static int option;
	public static PersonDAO personDAO = PersonDAO.getInstances();
	public static CarDAO carDAO = CarDAO.getInstances();
	public static AccidentDAO accidentDAO = AccidentDAO.getInstances();
	public static PolicyDAO policyDAO = PolicyDAO.getInstances();

	public static void main(String[] args) throws DatabaseException {
		exampleData();
		mainMenu();
	}

	public static void mainMenu() throws DatabaseException {
		do {
			try {
				System.out.println("\n--- Menú Principal ---");
				System.out.println("1. Agregar elementos");
				System.out.println("2. Listar elementos");
				System.out.println("3. Borrar elementos");
				System.out.println("4. Filtros con HQL");
				System.out.println("5. Filtros con Criteria");
				System.out.println("6. Files");
				System.out.println("0. Salir");
				System.out.print("Seleccione una opción: ");

				option = Integer.parseInt(scanner.nextLine());

				switch (option) {
				case 1:
					submenuEntities();
					break;
				case 2:
					submenuListar();
					break;
				case 3:
					submenuEliminar();
					break;
				case 4:
					submenuHQL();
					break;
				case 5:
					submenuCriteria();
					break;
				case 6:
					submenuFiles();
					break;
				case 0:
					System.out.println("Saliendo...");
					break;
				default:
					System.out.println("Opción no válida, intente de nuevo.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Error: Ingrese un número válido.");
			}
		} while (option != 0);
	}

	public static void submenuEntities() throws DatabaseException {
		int subOption;
		do {
			try {
				System.out.println("/n--- Submenú ENTITIES ---");
				System.out.println("1. add Person");
				System.out.println("2. add Cars");
				System.out.println("3. add policy");
				System.out.println("4. add Accident");
				System.out.println("5. Volver al menú principal");

				subOption = Integer.parseInt(scanner.nextLine());

				switch (subOption) {
				case 1:
					System.out.println("Ejecuntado insercion de Person");
					addPerson();
					break;
				case 2:
					System.out.println("Ejecutnado insercion de Cars");
					addCar();
					break;
				case 3:
					System.out.println("Ejecutando insercion de Policy");
					addPolicy();
					break;
				case 4:
					System.out.println("Ejecutando insercion de Accident");
					addAccident();
					break;
				case 5:
					System.out.println("Volviendo al menú principal.");
					break;
				default:
					System.out.println("Opción no válida.");
				}

			} catch (NumberFormatException e) {
				System.out.println("Error: Ingrese un número válido.");
				subOption = -1;
			}
		} while (subOption != 5);
	}

	public static void submenuListar() throws DatabaseException {
		int subOption;
		do {
			try {
				System.out.println("\n--- Submenú Listar Elementos ---");
				System.out.println("1. Listar Personas");
				System.out.println("2. Listar Coches");
				System.out.println("3. Listar Pólizas");
				System.out.println("4. Listar Accidentes");
				System.out.println("5. Volver al menú principal");
				System.out.print("Seleccione una opción: ");

				subOption = Integer.parseInt(scanner.nextLine());

				switch (subOption) {
				case 1:
					listPersons();
					break;
				case 2:
					listCars();
					break;
				case 3:
					listPolicies();
					break;
				case 4:
					listAccidents();
					break;
				case 5:
					System.out.println("Volviendo al menú principal.");
					break;
				default:
					System.out.println("Opción no válida.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Error: Ingrese un número válido.");
				subOption = -1;
			}
		} while (subOption != 5);
	}

	public static void submenuEliminar() throws DatabaseException {
		int subOption;
		do {
			try {
				System.out.println("\n--- Submenú Eliminar Elementos ---");
				System.out.println("1. Eliminar Persona");
				System.out.println("2. Eliminar Coche");
				System.out.println("3. Eliminar Póliza");
				System.out.println("4. Eliminar Accidente");
				System.out.println("5. Volver al Menú Principal");
				System.out.print("Seleccione una opción: ");

				subOption = Integer.parseInt(scanner.nextLine());

				switch (subOption) {
				case 1:
					System.out.print("Ingrese el DRIVER_ID de la persona a eliminar: ");
					String driverId = scanner.nextLine().trim();
					personDAO.deletePerson(driverId);
					break;
				case 2:
					System.out.print("Ingrese el LICENSE_ID del coche a eliminar: ");
					String licenseId = scanner.nextLine().trim();
					carDAO.deleteCar(licenseId);
					break;
				case 3:
					System.out.print("Ingrese el POLICY_ID de la póliza a eliminar: ");
					String policyId = scanner.nextLine().trim();
					policyDAO.deletePolicy(policyId);
					break;
				case 4:
					System.out.print("Ingrese el REPORT_NUMBER del accidente a eliminar: ");
					String reportNumber = scanner.nextLine().trim();
					accidentDAO.deleteAccident(reportNumber);
					break;
				case 5:
					System.out.println("Volviendo al menú principal.");
					break;
				default:
					System.out.println("Opción no válida.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Error: Ingrese un número válido.");
				subOption = -1;
			}
		} while (subOption != 5);
	}

	public static void submenuHQL() throws DatabaseException {
		int subOption;
		do {
			try {
				System.out.println("\n--- Submenú HQL ---");
				System.out.println("1. Listar CAR sin POLICY");
				System.out.println("2. Listar CAR con ACCIDENT por LOCATION");
				System.out.println("3. PERSON con más CAR");
				System.out.println("4. Listado de ACCIDENT y CAR por YEAR");
				System.out.println("5. Volver al menú principal");
				System.out.print("Seleccione una opción: ");

				subOption = Integer.parseInt(scanner.nextLine());

				switch (subOption) {
				case 1:
					System.out.println("Ejecutando HQL para listar CAR sin POLICY.");
					displayCars(carDAO.carsWithoutPolicy());
					confirmacion();
					break;
				case 2:
					System.out.println("Ejecutando HQL para listar CAR con ACCIDENT por LOCATION.");
					System.out.print("Ingrese la ubicación: ");
					String location = scanner.nextLine();
					displayCars(carDAO.carsByAccidentLocationHQL(location));
					confirmacion();
					break;
				case 3:
					System.out.println("Ejecutando HQL para encontrar PERSON con más CAR.");
					System.out.println("Persona con más coches: " + personDAO.personWithMostCarsHQL());
					confirmacion();
					break;
				case 4:
					System.out.println("Ejecutando HQL para listar ACCIDENT y CAR por YEAR.");
					System.out.print("Ingrese el año: ");
					int year = Integer.parseInt(scanner.nextLine());
					displayAccidents(accidentDAO.accidentsByYearHQL(year));
					confirmacion();
					break;
				case 5:
					System.out.println("Volviendo al menú principal.");
					break;
				default:
					System.out.println("Opción no válida.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Error: Ingrese un número válido.");
				subOption = -1;
			}
		} while (subOption != 5);
	}

	public static void submenuFiles() {
		int subOption;
		do {
			try {
				System.out.println("\n--- Submenú CSV/XML ---");
				System.out.println("1. Exportar personas a CSV");
				System.out.println("2. Convertir CSV a XML");
				System.out.println("3. Agregar año de nacimiento al XML");
				System.out.println("4. Listar personas desde XML con SAX");
				System.out.println("5. Generar reportes HTML con XSL");
				System.out.println("6. Volver al menú principal");
				System.out.print("Seleccione una opción: ");
				subOption = Integer.parseInt(scanner.nextLine());

				switch (subOption) {
				case 1:
					exportToCSV();
					break;
				case 2:
					convertCSVtoXML();
					break;
				case 3:
					addBirthYearToXML();
					break;
				case 4:
					listPersonsFromXML();
					break;
				case 5:
					generateHTMLReports();
					break;
				case 6:
					System.out.println("Volviendo al menú principal.");
					break;
				default:
					System.out.println("Opción no válida.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Error: Ingrese un número válido.");
				subOption = -1;
			}
		} while (subOption != 6);
	}

	public static void exportToCSV() {
		try {
			List<Person> persons = personDAO.listAllPerson();
			CSVHandler.writeToCSV(persons);
			System.out.println("Personas exportadas a CSV correctamente.");
		} catch (Exception e) {
			System.out.println("Error al exportar a CSV: " + e.getMessage());
		}
	}

	public static void convertCSVtoXML() {
		try {
			List<Person> persons = CSVHandler.readFromCSV();
			if (persons.isEmpty()) {
				System.out.println("No hay datos en el CSV para convertir.");
				return;
			}
			DOMHandler.convertCSVToXML(persons);
			System.out.println("CSV convertido a XML correctamente.");
		} catch (Exception e) {
			System.out.println("Error al convertir CSV a XML: " + e.getMessage());
		}
	}

	public static void addBirthYearToXML() {
		try {
			DOMHandler.addYearOfBirthToXML();
			System.out.println("Año de nacimiento agregado al XML correctamente.");
		} catch (Exception e) {
			System.out.println("Error al agregar año de nacimiento: " + e.getMessage());
		}
	}

	public static void listPersonsFromXML() {
		try {
			SAXHandler.listPersonsFromXML();
		} catch (Exception e) {
			System.out.println("Error al listar personas desde XML: " + e.getMessage());
		}
	}
	
	 public static void generateHTMLReports() {
	        try {
	            XSLTransformer.generateAllPersonInHTML(Config.FILE_XML);
	            XSLTransformer.generateOlderPersonInHTML(Config.FILE_XML);
	            XSLTransformer.generateMinorPersonInHTML(Config.FILE_XML);
	            System.out.println("Reportes HTML generados correctamente.");
	        } catch (Exception e) {
	            System.out.println("Error al generar reportes HTML: " + e.getMessage());
	        }
	    }

	public static void submenuCriteria() throws DatabaseException {
		int subOption;
		do {
			try {
				System.out.println("\n--- Submenú Criteria ---");
				System.out.println("1. Listar CAR sin POLICY");
				System.out.println("2. Listar CAR con ACCIDENT por LOCATION");
				System.out.println("3. PERSON con más CAR");
				System.out.println("4. Listado de ACCIDENT y CAR por YEAR");
				System.out.println("5. Volver al menú principal");
				System.out.print("Seleccione una opción: ");

				subOption = Integer.parseInt(scanner.nextLine());

				switch (subOption) {
				case 1:
					System.out.println("Ejecutando Criteria API para listar CAR sin POLICY.");
					displayCars(carDAO.carsWithoutPolicyCriteria());
					confirmacion();
					break;
				case 2:
					System.out.println("Ejecutando Criteria API para listar CAR con ACCIDENT por LOCATION.");
					System.out.print("Ingrese la ubicación: ");
					String location = scanner.nextLine();
					displayCars(carDAO.carsByAccidentLocationCriteria(location));
					confirmacion();
					break;
				case 3:
					System.out.println("Ejecutando Criteria API para encontrar PERSON con más CAR.");
					System.out.println("Persona con más coches: " + personDAO.personWithMostCarsCriteria());
					confirmacion();
					break;
				case 4:
					System.out.println("Ejecutando Criteria API para listar ACCIDENT y CAR por YEAR.");
					System.out.print("Ingrese el año: ");
					Integer year = Integer.parseInt(scanner.nextLine());
					displayAccidents(accidentDAO.accidentsByYearCriteria(year));
					confirmacion();
					break;
				case 5:
					System.out.println("Volviendo al menú principal.");
					break;
				default:
					System.out.println("Opción no válida.");
				}
			} catch (NumberFormatException e) {
				System.out.println("Error: Ingrese un número válido.");
				subOption = -1;
			}
		} while (subOption != 5);
	}

	/**
	 * Agrega una nueva persona a la base de datos si el ID no está duplicado.
	 *
	 * @throws DatabaseException si ocurre un error en la operación con la base de
	 *                           datos.
	 */
	public static void addPerson() throws DatabaseException {
		String id, address, name;
		Integer age;
		try {
			do {
				System.out.println("\n=== Agregar nueva Person ===");
				System.out.println("Ingresar DRIVER_ID (Ingresa '0' para salir)");
				id = scanner.nextLine().trim();
				if (id == null || id.isEmpty()) {
					System.out.println("Error: El DRIVER_ID no puede ser null.");
				}
				if (id.equals("0"))
					return;
				if (isIdDuplicate(id, "Person")) {
					System.out.println("Error: El DRIVER_ID ya existe.");
					continue;
				}

				System.out.println("Ingrese la Address:");
				address = scanner.nextLine();
				System.out.println("Ingrese el Name:");
				name = scanner.nextLine();
				System.out.println("Ingrese la edad");
				age = scanner.nextInt();

				Person person = new Person(id, address, name, age);
				if (personDAO.addPerson(person) != null) {
					System.out.println("Persona añadida correctamente.");
					System.out.println(person.toString());
				} else {
					System.out.println("Error al añadir la persona.");
				}
				confirmacion();
				break;
			} while (true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Agrega un nuevo coche a la base de datos si el ID no está duplicado.
	 *
	 * @throws DatabaseException si ocurre un error en la operación con la base de
	 *                           datos.
	 */
	public static void addCar() throws DatabaseException {
		String licenseId, model, driverId;
		int year;
		try {
			do {
				System.out.println("\n=== Agregar nuevo Car ===");
				System.out.println("Ingresar LICENSE_ID (Ingresa '0' para salir)");
				licenseId = scanner.nextLine().trim();
				if (licenseId == null || licenseId.isEmpty()) {
					System.out.println("Error: El LICENSE_ID es obligatorio.");
					continue;
				}
				if (licenseId.equals("0"))
					return;
				if (isIdDuplicate(licenseId, "Car")) {
					System.out.println("Error: El LICENSE_ID ya existe.");
					continue;
				}

				System.out.println("Ingrese el modelo:");
				model = scanner.nextLine().trim();
				System.out.println("Ingrese el año:");
				year = Integer.parseInt(scanner.nextLine().trim());

				System.out.println("Ingrese el DRIVER_ID del propietario:");
				driverId = scanner.nextLine().trim();

				// Buscar propietario
				Person owner = null;
				for (Person p : personDAO.listAllPerson()) {
					if (p.getDriverId().equals(driverId)) {
						owner = p;
						break;
					}
				}
				if (owner == null) {
					System.out.println("Error: No existe una persona con ese DRIVER_ID.");
					continue;
				}

				Car car = new Car(licenseId, model, year);
				car.setOwner(owner);
				owner.getCars().add(car);

				if (carDAO.addCar(car) != null) {
					System.out.println("Coche añadido correctamente.");
					System.out.println(car.toString());
				} else {
					System.out.println("Error al añadir el coche.");
				}
				confirmacion();
				break;
			} while (true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Agrega una nueva póliza a la base de datos si el ID no está duplicado.
	 *
	 * @throws DatabaseException si ocurre un error en la operación con la base de
	 *                           datos.
	 */
	public static void addPolicy() throws DatabaseException {
		String policyId, licenseId;
		try {
			do {
				System.out.println("\n=== Agregar nueva Policy ===");
				System.out.println("Ingresar POLICY_ID (Ingresa '0' para salir)");
				policyId = scanner.nextLine().trim();
				if (policyId == null || policyId.isEmpty()) {
					System.out.println("Error: El POLICY_ID es obligatorio.");
					continue;
				}
				if (policyId.equals("0"))
					return;
				if (isIdDuplicate(policyId, "Policy")) {
					System.out.println("Error: El POLICY_ID ya existe.");
					continue;
				}

				System.out.println("Ingrese el LICENSE_ID del coche relacionado:");
				licenseId = scanner.nextLine().trim();

				Car car = null;
				for (Car c : carDAO.listAllCar()) {
					if (c.getLicenseId().equals(licenseId)) {
						car = c;
						break;
					}
				}
				if (car == null) {
					System.out.println("Error: No existe un coche con ese LICENSE_ID.");
					continue;
				}

				Policy policy = new Policy(policyId);
				car.setPolicy(policy);
				policy.setCar(car);

				if (policyDAO.addPolicy(policy) != null) {
					System.out.println("Póliza añadida correctamente.");
					System.out.println(policy.toString());
				} else {
					System.out.println("Error al añadir la póliza.");
				}
				confirmacion();
				break;
			} while (true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Agrega un nuevo accidente a la base de datos si el ID no está duplicado.
	 *
	 * @throws DatabaseException si ocurre un error en la operación con la base de
	 *                           datos.
	 */
	public static void addAccident() throws DatabaseException {
		String reportNumber, location, licenseId;
		try {
			do {
				System.out.println("\n=== Agregar nuevo Accident ===");
				System.out.println("Ingresar REPORT_NUMBER (Ingresa '0' para salir)");
				reportNumber = scanner.nextLine().trim();
				if (reportNumber == null || reportNumber.isEmpty()) {
					System.out.println("Error: EL REPORT_NUMBER no puede ser null");
				}
				if (reportNumber.equals("0"))
					return;
				if (isIdDuplicate(reportNumber, "Accident")) {
					System.out.println("Error: El REPORT_NUMBER ya existe.");
					continue;
				}

				System.out.println("Ingrese la ubicación:");
				location = scanner.nextLine().trim();

				System.out.println("Ingrese el LICENSE_ID del coche involucrado:");
				licenseId = scanner.nextLine().trim();

				Car car = null;
				for (Car c : carDAO.listAllCar()) {
					if (c.getLicenseId().equals(licenseId)) {
						car = c;
						break;
					}
				}
				if (car == null) {
					System.out.println("Error: No existe un coche con ese LICENSE_ID.");
					continue;
				}

				Accident accident = new Accident(reportNumber, location);

				// Sincronizar la relación muchos a muchos
				accident.getCarsInvoled().add(car);
				car.getAccidents().add(accident);

				// Persistir el accidente (y la relación)
				if (accidentDAO.addAccident(accident) != null) {
					System.out.println("Accidente añadido correctamente.");
					System.out.println(accident.toString());
				} else {
					System.out.println("Error al añadir el accidente.");
				}
				confirmacion();
				break;
			} while (true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Lista todas las personas en la base de datos junto con los coches
	 * relacionados.
	 *
	 * @throws DatabaseException si ocurre un error al acceder a la base de datos.
	 */
	public static void listPersons() throws DatabaseException {
		System.out.println("\n=== Listado de Personas ===");
		for (Person person : personDAO.listAllPerson()) {
			// Construir lista de LICENSE_IDs de los coches del propietario
			StringBuilder cars = new StringBuilder();
			for (Car car : person.getCars()) {
				if (cars.length() > 0) {
					cars.append(", ");
				}
				cars.append(car.getLicenseId());
			}

			System.out.println("DRIVER_ID: " + person.getDriverId() + ", ADDRESS: " + person.getAddress() + ", NAME: "
					+ person.getName() + ", CARS: [" + cars + "]");
		}
		confirmacion();
	}

	/**
	 * Lista todos los coches en la base de datos junto con su propietario y póliza
	 * relacionados.
	 *
	 * @throws DatabaseException si ocurre un error al acceder a la base de datos.
	 */
	public static void listCars() throws DatabaseException {
		System.out.println("\n=== Listado de Coches ===");
		for (Car car : carDAO.listAllCar()) {
			StringBuilder output = new StringBuilder();
			output.append("LICENSE_ID: ").append(car.getLicenseId());
			output.append(", MODEL: ").append(car.getModel());
			output.append(", YEAR: ").append(car.getYear());

			if (car.getOwner() != null) {
				output.append(", DRIVER_ID: ").append(car.getOwner().getDriverId());
			}

			if (car.getPolicy() != null) {
				output.append(", POLICY_ID: ").append(car.getPolicy().getPolicyId());
			}

			System.out.println(output.toString());
		}
		confirmacion();
	}

	/**
	 * Lista todas las pólizas en la base de datos junto con los coches
	 * relacionados.
	 *
	 * @throws DatabaseException si ocurre un error al acceder a la base de datos.
	 */
	public static void listPolicies() throws DatabaseException {
		System.out.println("\n=== Listado de Pólizas ===");
		for (Policy policy : policyDAO.listAllPolicies()) {
			StringBuilder output = new StringBuilder();
			output.append("POLICY_ID: ").append(policy.getPolicyId());

			if (policy.getCar() != null) {
				output.append(", LICENSE_ID: ").append(policy.getCar().getLicenseId());
			}

			System.out.println(output.toString());
		}
		confirmacion();
	}

	/**
	 * Lista todos los accidentes en la base de datos junto con los coches
	 * involucrados.
	 *
	 * @throws DatabaseException si ocurre un error al acceder a la base de datos.
	 */
	public static void listAccidents() throws DatabaseException {
		System.out.println("\n=== Listado de Accidentes ===");
		for (Accident accident : accidentDAO.listAllAccident()) {
			StringBuilder carsInvolved = new StringBuilder();
			for (Car car : accident.getCarsInvoled()) {
				if (carsInvolved.length() > 0) {
					carsInvolved.append(", ");
				}
				carsInvolved.append(car.getLicenseId());
			}

			System.out.println("REPORT_NUMBER: " + accident.getReportNumber() + ", LOCATION: " + accident.getLocation()
					+ ", CARS INVOLVED: [" + carsInvolved + "]");
		}
		confirmacion();
	}

	private static void displayCars(List<Car> cars) {
		if (cars.isEmpty()) {
			System.out.println("No hay coches que mostrar.");
		} else {
			for (Car car : cars) {
				System.out.println(
						"ID: " + car.getLicenseId() + ", Modelo: " + car.getModel() + ", Año: " + car.getYear());
			}
		}
		confirmacion();
	}

	private static void displayAccidents(List<Accident> accidents) {
		if (accidents.isEmpty()) {
			System.out.println("No hay accidentes que mostrar.");
		} else {
			for (Accident accident : accidents) {
				System.out.println("Accidente: " + accident.getReportNumber() + " en " + accident.getLocation());
			}
		}
		confirmacion();
	}

	/**
	 * Solicita al usuario confirmar una acción con 'S' o 'N'. Lanza una excepción
	 * si el usuario no ingresa una opción válida.
	 *
	 * @return true si el usuario ingresa 'S' (sí), false si ingresa 'N' (no).
	 * @throws IllegalArgumentException si el usuario no ingresa 'S' o 'N'.
	 */
	public static boolean confirmacion() {
		try {
			System.out.print("¿Está seguro de continuar con esta opción? (S/N): ");
			String respuesta = scanner.nextLine().trim().toLowerCase();

			if (!respuesta.equals("s") && !respuesta.equals("n")) {
				throw new IllegalArgumentException("Debe ingresar 'S' para sí o 'N' para no.");
			}

			return respuesta.equals("s");
		} catch (IllegalArgumentException e) {
			System.out.println("Error: " + e.getMessage());
			return false; // Por defecto, se interpreta como "No".
		}
	}

	/* Methods to validate Attributes */

	/**
	 * Verifica si un ID ya existe en la base de datos para evitar duplicados.
	 *
	 * @param id         ID a verificar.
	 * @param entityType Tipo de entidad (Person, Car, Policy, Accident).
	 * @return true si el ID ya existe, false si está disponible.
	 * @throws DatabaseException en caso de error con la base de datos.
	 */
	public static boolean isIdDuplicate(String id, String entityType) throws DatabaseException {
		switch (entityType) {
		case "Person":
			for (Person p : personDAO.listAllPerson()) {
				if (p.getDriverId().equals(id)) {
					return true;
				}
			}
			break;
		case "Car":
			for (Car c : carDAO.listAllCar()) {
				if (c.getLicenseId().equals(id)) {
					return true;
				}
			}
			break;
		case "Policy":
			for (Policy p : policyDAO.listAllPolicies()) {
				if (p.getPolicyId().equals(id)) {
					return true;
				}
			}
			break;
		case "Accident":
			for (Accident a : accidentDAO.listAllAccident()) {
				if (a.getReportNumber().equals(id)) {
					return true;
				}
			}
			break;
		}
		return false;
	}

	public static void exampleData() {

		try {

			// Crear personas
			Person person1 = new Person("PersonA", "Sergio Alejo", "c/Pez 1ºA", 21);
			Person person2 = new Person("PersonB", "Elia Bastian", "c/Pez 1ºB", 19);
			Person person3 = new Person("PersonC", "Juan Clase", "c/Pez 1ºC", 20);

			// Insertar personas sin duplicados
			personDAO.addPerson(person1);
			personDAO.addPerson(person2);
			personDAO.addPerson(person3);

			// Crear coches y asignar propietarios
			Car car1 = new Car("carA", "Ford Focus", 2011);
			car1.setOwner(person1);
			person1.getCars().add(car1);

			Car car2 = new Car("carB", "Seat Ibiza", 2012);
			car2.setOwner(person1);
			person1.getCars().add(car2);

			Car car3 = new Car("carC", "Renault Laguna", 2013);
			car3.setOwner(person2);
			person2.getCars().add(car3);

			Car car4 = new Car("carD", "Fiat Tipo", 2014);
			car4.setOwner(person3);
			person3.getCars().add(car4);

			// Guardar coches en la base de datos
			carDAO.addCar(car1);
			carDAO.addCar(car2);
			carDAO.addCar(car3);
			carDAO.addCar(car4);

			// Crear pólizas y asociarlas a coches
			Policy policy1 = new Policy("polA");
			Policy policy2 = new Policy("polB");

			// Asociar pólizas con coches
			car1.setPolicy(policy1);
			policy1.setCar(car1);

			car2.setPolicy(policy2);
			policy2.setCar(car2);

			// Guardar coches y pólizas en la base de datos
			carDAO.addCar(car1);
			carDAO.addCar(car2);
			carDAO.addCar(car3);
			carDAO.addCar(car4);

			policyDAO.addPolicy(policy1);
			policyDAO.addPolicy(policy2);

			// Crear accidentes
			Accident accident1 = new Accident("accA", "Madrid");
			Accident accident2 = new Accident("accB", "Teruel");
			Accident accident3 = new Accident("accC", "Lugo");

			accidentDAO.addAccident(accident1); // Persiste accident1 en la base de datos
			accidentDAO.addAccident(accident2);
			accidentDAO.addAccident(accident3);

			// Configuración de accidentes para cada coche
			car1.getAccidents().add(accident1); // Añade accident1 a la lista de accidentes en los que está involucrado
												// car1
			car1.getAccidents().add(accident3);
			car2.getAccidents().add(accident1);
			car3.getAccidents().add(accident2);
			car4.getAccidents().add(accident2);
			car4.getAccidents().add(accident3);

			// Configuración de coches involucrados en cada accidente
			accident1.getCarsInvoled().add(car1); // Añade car1 a la lista de coches involucrados en accident1
			accident1.getCarsInvoled().add(car2);
			accident2.getCarsInvoled().add(car3);
			accident2.getCarsInvoled().add(car4);
			accident3.getCarsInvoled().add(car1);
			accident3.getCarsInvoled().add(car4);

			// Persistir accidentes (y relaciones asociadas)
			accidentDAO.addAccident(accident1); // Persiste accident1 con las relaciones establecidas
			accidentDAO.addAccident(accident2);
			accidentDAO.addAccident(accident3);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}