package com.santiparra.cars.dao;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import com.santiparra.cars.dao.util.HibernateSessionFactory;
import com.santiparra.cars.entities.Accident;
import com.santiparra.cars.entities.Car;
import com.santiparra.cars.exceptions.DatabaseException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;

public class CarDAO {

	/* Singleton DAO Instance */
	private static CarDAO instance = new CarDAO();

	private CarDAO() {
	}

	public static CarDAO getInstances() {
		return instance;
	}

	/**
	 * Agrega un coche a la base de datos. Si el coche tiene una póliza asociada,
	 * también se persiste la póliza.
	 *
	 * @param car El objeto Car que se desea agregar a la base de datos.
	 * @return El objeto Car que ha sido persistido.
	 * @throws DatabaseException Si ocurre un error durante la interacción con la
	 *                           base de datos.
	 */
	public Car addCar(Car car) throws DatabaseException {
		if (car.getLicenseId() == null) {
			throw new IllegalArgumentException("El LICENSE_ID no puede ser nulo.");
		}
		Session session = HibernateSessionFactory.getSessionSingleton();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			if (car.getPolicy() != null) {
				session.persist(car.getPolicy()); // Persistir la póliza asociada si existe
			}
			session.persist(car);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			throw new DatabaseException(e);
		}
		return car;
	}

	/**
	 * Obtiene una lista de todos los coches registrados en la base de datos.
	 * 
	 * @return Lista de coches almacenados.
	 * @throws DatabaseException Si ocurre un error durante la interacción con la
	 *                           base de datos.
	 */
	public List<Car> listAllCar() throws DatabaseException {
		try {
			Session session = HibernateSessionFactory.getSessionSingleton();
			Query<Car> queryCar = session.createQuery("SELECT c FROM Car c LEFT JOIN FETCH c.policy", Car.class);
			return queryCar.list();
		} catch (Exception e) {
			throw new DatabaseException(e);
		}
	}

	/**
	 * Elimina un coche de la base de datos según su número de matrícula.
	 * 
	 * @param licenseId Número de matrícula del coche a eliminar.
	 * @throws DatabaseException Si ocurre un error durante la interacción con la
	 *                           base de datos.
	 */
	public void deleteCar(String licenseId) throws DatabaseException {
		Session session = HibernateSessionFactory.getSessionSingleton();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			// Verificar si el coche tiene una póliza
			Query<Long> policyCheck = session
					.createQuery("SELECT COUNT(p) FROM Policy p WHERE p.car.licenseId = :licenseId", Long.class);
			policyCheck.setParameter("licenseId", licenseId);
			Long policyCount = policyCheck.uniqueResult();

			if (policyCount > 0) {
				System.out.println("No se puede eliminar el coche con LICENSE_ID: " + licenseId
						+ " porque tiene una póliza asociada.");
				return;
			}

			// Eliminar el coche
			Query<Car> deleteCar = session.createQuery("DELETE FROM Car c WHERE c.licenseId = :licenseId");
			deleteCar.setParameter("licenseId", licenseId);
			deleteCar.executeUpdate();

			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			throw new DatabaseException(e);
		}
	}

	/**
	 * Obtiene una lista de coches que no tienen póliza.
	 * 
	 * @return Lista de coches sin póliza.
	 * @throws DatabaseException Si ocurre un error durante la interacción con la
	 *                           base de datos.
	 */
	public List<Car> carsWithoutPolicy() throws DatabaseException {
		try {
			Session session = HibernateSessionFactory.getSessionSingleton();
			Query<Car> query = session.createQuery("FROM Car a WHERE a.policy IS NULL", Car.class);
			return query.list();
		} catch (HibernateException e) {
			throw new DatabaseException(e);
		}
	}

	/**
	 * Obtiene una lista de coches involucrados en accidentes en una ubicación
	 * específica (HQL).
	 * 
	 * @param location Ubicación del accidente.
	 * @return Lista de coches involucrados en accidentes en la ubicación dada.
	 * @throws DatabaseException Si ocurre un error durante la interacción con la
	 *                           base de datos.
	 */
	public List<Car> carsByAccidentLocationHQL(String location) throws DatabaseException {
		try {
			Session session = HibernateSessionFactory.getSessionSingleton();
			Query<Car> query = session.createQuery(
					"SELECT DISTINCT c FROM Car c JOIN c.accidents a WHERE a.location = :location", Car.class);
			query.setParameter("location", location);
			return query.list();
		} catch (HibernateException e) {
			throw new DatabaseException(e);
		}
	}

	/**
	 * Obtiene una lista de coches que no tienen póliza utilizando Criteria API.
	 * 
	 * @return Lista de coches sin póliza.
	 * @throws DatabaseException Si ocurre un error durante la interacción con la
	 *                           base de datos.
	 */
	public List<Car> carsWithoutPolicyCriteria() throws DatabaseException {
		try {
			Session session = HibernateSessionFactory.getSessionSingleton();
			HibernateCriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<Car> cq = cb.createQuery(Car.class);
			Root<Car> carRoot = cq.from(Car.class);
			cq.select(carRoot).where(cb.isNull(carRoot.get("policy")));
			return session.createQuery(cq).getResultList();
		} catch (HibernateException e) {
			throw new DatabaseException(e);
		}
	}

	/**
	 * Obtiene una lista de coches involucrados en accidentes en una ubicación
	 * específica utilizando Criteria API.
	 * 
	 * @param location Ubicación del accidente.
	 * @return Lista de coches involucrados en accidentes en la ubicación dada.
	 * @throws DatabaseException Si ocurre un error durante la interacción con la
	 *                           base de datos.
	 */
	public List<Car> carsByAccidentLocationCriteria(String location) throws DatabaseException {
		try {
			Session session = HibernateSessionFactory.getSessionSingleton();
			HibernateCriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<Car> cq = cb.createQuery(Car.class);
			Root<Car> carRoot = cq.from(Car.class);
			Join<Car, Accident> accidentJoin = carRoot.join("accidents");
			cq.select(carRoot).distinct(true).where(cb.equal(accidentJoin.get("location"), location));
			return session.createQuery(cq).getResultList();
		} catch (HibernateException e) {
			throw new DatabaseException(e);
		}
	}
}
