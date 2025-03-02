package com.santiparra.cars.dao;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import com.santiparra.cars.dao.util.HibernateSessionFactory;
import com.santiparra.cars.entities.Policy;
import com.santiparra.cars.exceptions.DatabaseException;

public class PolicyDAO {

	/* Singleton DAO Instance */
	private static PolicyDAO instance = new PolicyDAO();

	private PolicyDAO() {
	}

	public static PolicyDAO getInstances() {
		return instance;
	}

	/**
	 * Agrega una póliza a la base de datos. Si la póliza tiene un coche asociado,
	 * también se persiste el coche.
	 *
	 * @param policy El objeto Policy que se desea agregar a la base de datos.
	 * @return El objeto Policy que ha sido persistido.
	 * @throws DatabaseException Si ocurre un error durante la interacción con la
	 *                           base de datos.
	 */
	public Policy addPolicy(Policy policy) throws DatabaseException {
		if (policy.getPolicyId() == null) {
			throw new IllegalArgumentException("El POLICY_ID no puede ser nulo.");
		}
		Session session = HibernateSessionFactory.getSessionSingleton();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			if (policy.getCar() != null) {
				session.persist(policy.getCar()); // Persistir el coche asociado si existe
			}
			session.persist(policy);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			throw new DatabaseException(e);
		}
		return policy;
	}

	/**
	 * Obtiene una lista de todas las pólizas registradas en la base de datos.
	 * 
	 * @return Lista de pólizas almacenadas.
	 * @throws DatabaseException Si ocurre un error durante la interacción con la
	 *                           base de datos.
	 */
	public List<Policy> listAllPolicies() throws DatabaseException {
		try {
			Session session = HibernateSessionFactory.getSessionSingleton();
			Query<Policy> queryPolicy = session.createQuery("FROM Policy");
			return queryPolicy.list();
		} catch (Exception e) {
			throw new DatabaseException(e);
		}
	}

	/**
	 * Elimina una póliza de la base de datos según su identificador.
	 * 
	 * @param policyId Identificador de la póliza a eliminar.
	 * @throws DatabaseException Si ocurre un error durante la interacción con la
	 *                           base de datos.
	 */
	public void deletePolicy(String policyId) throws DatabaseException {
		Session session = HibernateSessionFactory.getSessionSingleton();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Query<Policy> deletePolicy = session.createQuery("DELETE FROM Policy p WHERE p.policyId = :policyId",
					Policy.class);
			deletePolicy.setParameter("policyId", policyId);
			deletePolicy.executeUpdate();
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			throw new DatabaseException(e);
		}
	}
}
