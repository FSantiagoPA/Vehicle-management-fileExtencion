package com.santiparra.cars.dao;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import com.santiparra.cars.dao.util.HibernateSessionFactory;
import com.santiparra.cars.entities.Car;
import com.santiparra.cars.entities.Person;
import com.santiparra.cars.exceptions.DatabaseException;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public class PersonDAO {

	/* Singleton DAO Instance */
	private static PersonDAO instance = new PersonDAO();

	private PersonDAO() {
	}

	public static PersonDAO getInstances() {
		return instance;
	}

	/**
	 * Agrega una nueva persona a la base de datos.
	 * 
	 * @param person El objeto Person a ser agregado.
	 * @return El objeto Person que ha sido persistido.
	 * @throws DatabaseException Si ocurre un error durante la interacción con la
	 *                           base de datos.
	 */
	public Person addPerson(Person person) throws DatabaseException {
		if (person.getDriverId() == null) {
			throw new IllegalArgumentException("El DRIVER_ID no puede ser nulo.");
		}
		Session session = HibernateSessionFactory.getSessionSingleton();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.persist(person);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			throw new DatabaseException(e);
		}
		return person;
	}

	/**
	 * Obtiene una lista de todas las personas registradas en la base de datos.
	 * 
	 * @return Lista de personas almacenadas.
	 * @throws DatabaseException Si ocurre un error durante la interacción con la
	 *                           base de datos.
	 */
	public List<Person> listAllPerson() throws DatabaseException {
		try {
			Session session = HibernateSessionFactory.getSessionSingleton();
			Query<Person> queryPerson = session.createQuery("FROM Person", Person.class);
			return queryPerson.list();
		} catch (Exception e) {
			throw new DatabaseException(e);
		}
	}

	/**
	 * Elimina una persona de la base de datos según su ID de conductor.
	 * 
	 * @param driverId ID del conductor a eliminar.
	 * @throws DatabaseException Si ocurre un error durante la interacción con la
	 *                           base de datos.
	 */
	public void deletePerson(String driverId) throws DatabaseException {
		Session session = HibernateSessionFactory.getSessionSingleton();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			// Verificar si la persona tiene coches con póliza
			Query<Long> policyCheck = session.createQuery(
					"SELECT COUNT(p) FROM Policy p JOIN p.car c WHERE c.owner.driverId = :driverId", Long.class);
			policyCheck.setParameter("driverId", driverId);
			Long policyCount = policyCheck.uniqueResult();

			if (policyCount > 0) {
				System.out.println("No se puede eliminar la persona con DRIVER_ID: " + driverId
						+ " porque uno de sus coches tiene una póliza.");
				return; /* TODO: cambiar esto para que trasition se cierre */
			}

			// Eliminar los coches de la persona
			Query<Car> deleteCars = session.createQuery("DELETE FROM Car c WHERE c.owner.driverId = :driverId",
					Car.class);
			deleteCars.setParameter("driverId", driverId);
			deleteCars.executeUpdate();

			// Eliminar la persona
			Query<Object[]> deletePerson = session.createQuery("DELETE FROM Person p WHERE p.driverId = :driverId",
					Object[].class);
			deletePerson.setParameter("driverId", driverId);
			deletePerson.executeUpdate();

			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			throw new DatabaseException(e);
		}
	}

	/**
	 * Obtiene la persona que posee la mayor cantidad de coches usando HQL.
	 * 
	 * @return La persona con más coches registrados.
	 * @throws DatabaseException Si ocurre un error durante la interacción con la
	 *                           base de datos.
	 */
	public Person personWithMostCarsHQL() throws DatabaseException {
		try {
			Session session = HibernateSessionFactory.getSessionSingleton();
			Query<Person> query = session.createQuery(
					"SELECT p FROM Person p WHERE size(p.cars) = (SELECT MAX(size(p2.cars)) FROM Person p2)",
					Person.class);
			return query.uniqueResult();
		} catch (HibernateException e) {
			throw new DatabaseException(e);
		}
	}

	/**
	 * Obtiene la persona que posee la mayor cantidad de coches usando Criteria API.
	 * 
	 * @return La persona con más coches registrados.
	 * @throws DatabaseException Si ocurre un error durante la interacción con la
	 *                           base de datos.
	 */
	public Person personWithMostCarsCriteria() throws DatabaseException {
		try {
			Session session = HibernateSessionFactory.getSessionSingleton();
			HibernateCriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<Person> cq = cb.createQuery(Person.class);
			Root<Person> personRoot = cq.from(Person.class);
			cq.select(personRoot).orderBy(cb.desc(cb.size(personRoot.get("cars"))));
			return session.createQuery(cq).setMaxResults(1).uniqueResult();
		} catch (HibernateException e) {
			throw new DatabaseException(e);
		}
	}
}
