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

public class AccidentDAO {

	/* Singleton DAO Instance */
	private static AccidentDAO instance = new AccidentDAO();

	private AccidentDAO() {
	}

	public static AccidentDAO getInstances() {
		return instance;
	}

	/**
	 * Agrega un nuevo accidente a la base de datos.
	 * 
	 * @param accident El objeto Accident a ser agregado.
	 * @return El objeto Accident que ha sido persistido.
	 * @throws DatabaseException Si ocurre un error durante la interacción con la
	 *                           base de datos.
	 */
	public Accident addAccident(Accident accident) throws DatabaseException {
		if (accident.getReportNumber() == null) {
			throw new IllegalArgumentException("El REPORT_NUMBER no puede ser nulo.");
		}
		Session session = HibernateSessionFactory.getSessionSingleton();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.persist(accident);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			throw new DatabaseException(e);
		}
		return accident;
	}

	/**
	 * Obtiene una lista de todos los accidentes registrados en la base de datos.
	 * 
	 * @return Lista de accidentes almacenados.
	 * @throws DatabaseException Si ocurre un error durante la interacción con la
	 *                           base de datos.
	 */
	public List<Accident> listAllAccident() throws DatabaseException {
		try {
			Session session = HibernateSessionFactory.getSessionSingleton();
			Query<Accident> queryAccident = session.createQuery("FROM Accident", Accident.class);
			return queryAccident.list();
		} catch (Exception e) {
			throw new DatabaseException(e);
		}
	}

	/**
	 * Elimina un accidente de la base de datos según su número de informe.
	 * 
	 * @param reportNumber Número de informe del accidente a eliminar.
	 * @throws DatabaseException Si ocurre un error durante la interacción con la
	 *                           base de datos.
	 */
	public void deleteAccident(String reportNumber) throws DatabaseException {
		Session session = HibernateSessionFactory.getSessionSingleton();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Query<Accident> deleteAccident = session
					.createQuery("DELETE FROM Accident a WHERE a.reportNumber = :reportNumber");
			deleteAccident.setParameter("reportNumber", reportNumber);
			deleteAccident.executeUpdate();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			throw new DatabaseException(e);
		}
	}

	/**
	 * Obtiene una lista de accidentes ocurridos en un año específico usando HQL.
	 * 
	 * @param year Año de los accidentes a buscar.
	 * @return Lista de accidentes registrados en el año indicado.
	 * @throws DatabaseException Si ocurre un error durante la interacción con la
	 *                           base de datos.
	 */
	public List<Accident> accidentsByYearHQL(int year) throws DatabaseException {
		try {
			Session session = HibernateSessionFactory.getSessionSingleton();
			Query<Accident> query = session.createQuery(
					"SELECT a, c FROM Accident a JOIN a.carsInvoled c WHERE a.year = :year ORDER BY a.location",
					Accident.class);
			query.setParameter("year", year);
			return query.list();
		} catch (HibernateException e) {
			throw new DatabaseException(e);
		}
	}

	/**
	 * Obtiene una lista de accidentes ocurridos en un año específico usando
	 * Criteria API.
	 * 
	 * @param year Año de los accidentes a buscar.
	 * @return Lista de accidentes registrados en el año indicado.
	 * @throws DatabaseException Si ocurre un error durante la interacción con la
	 *                           base de datos.
	 */
	public List<Accident> accidentsByYearCriteria(int year) throws DatabaseException {
		try {
			Session session = HibernateSessionFactory.getSessionSingleton();
			HibernateCriteriaBuilder cb = session.getCriteriaBuilder();
			CriteriaQuery<Accident> cq = cb.createQuery(Accident.class);
			Root<Accident> accidentRoot = cq.from(Accident.class);
			Join<Accident, Car> carJoin = accidentRoot.join("carsInvoled");
			cq.multiselect(accidentRoot, carJoin).where(cb.equal(accidentRoot.get("year"), year))
					.orderBy(cb.asc(accidentRoot.get("location")));
			return session.createQuery(cq).getResultList();
		} catch (HibernateException e) {
			throw new DatabaseException(e);
		}
	}
}
