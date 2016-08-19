package com.madit.spring.customer.register.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.madit.common.utils.Utils;
import com.madit.spring.customer.register.model.CustomerRegistration;

@Repository
@Transactional
public class CustomerRegistrationDAOImpl implements CustomerRegistrationDAO {
	private static final Logger log = LoggerFactory
			.getLogger(CustomerRegistrationDAOImpl.class);
	@PersistenceContext
	private EntityManager manager;

	@Override
	public void addCustomer(CustomerRegistration customer) {
		log.debug("Enter into theCustomerRegistrationDAOImpl-> addCustomer() ...");
		log.debug("customer PhoneNumber - > " + customer.getPhoneNumber());
		log.debug("customer Name - > " + customer.getName());
		log.debug("customer Email - > " + customer.getEmail());

		// Use null checks and handle them

		customer.setCreatedDate(new Date());
		// customer.setUpdatedDate();

		manager.persist(customer);

	}

	@Override
	public void updateCustomer(CustomerRegistration customer) {

		log.debug("Enter into the CustomerRegistrationDAOImpl-> updateCustomer() ...");
		log.debug("customer PhoneNumber - > " + customer.getPhoneNumber());
		log.debug("customer Name - > " + customer.getName());
		log.debug("customer Email - > " + customer.getEmail());

		// Use null checks and handle them

		// CustomerRegistration
		// customerRegistration=manager.find(CustomerRegistration.class,
		// customer.getId());

		customer.setUpdatedDate(new Date());

		manager.merge(customer);

	}

	@Override
	public void deleteCustomer(Long phno) {

		// Find managed Entity reference
		CustomerRegistration cust = findCustomerByPhNum(phno);
		if (cust != null) {
			CustomerRegistration customerRegistration = manager.find(
					CustomerRegistration.class, cust.getId());
			// Call remove method to remove the entity
			if (customerRegistration != null) {
				manager.remove(customerRegistration);
			}

		}
	}

	@Override
	public CustomerRegistration getCustomer(String username, String password) {
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<CustomerRegistration> c = cb
				.createQuery(CustomerRegistration.class);
		Root<CustomerRegistration> root = c.from(CustomerRegistration.class);
		c.select(root);
		List<Predicate> criteria = new ArrayList<Predicate>();

		if (Utils.isNotEmpty(username) && Utils.isNotEmpty(password)) {

			Predicate condition = cb.equal(root.get("name"), username);
			Predicate condition2 = cb.equal(root.get("password"), password);

			criteria.add(condition);
			criteria.add(condition2);

			c.where(cb.and(criteria.toArray(new Predicate[0])));
		}

		TypedQuery<CustomerRegistration> q = manager.createQuery(c);

		List<CustomerRegistration> resultList = q.getResultList();
		log.debug("Result List -- > " + resultList.size());
		CustomerRegistration cust = null;
		if (Utils.isNotEmpty(resultList)) {
			cust = resultList.get(0);
		}
		return cust;

	}

	@Override
	public List<CustomerRegistration> getAllCustomers() {

		// select all records

		CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
		CriteriaQuery<CustomerRegistration> criteriaQuery = criteriaBuilder
				.createQuery(CustomerRegistration.class);
		Root<CustomerRegistration> from = criteriaQuery
				.from(CustomerRegistration.class);
		CriteriaQuery<CustomerRegistration> select = criteriaQuery.select(from);
		TypedQuery<CustomerRegistration> typedQuery = manager
				.createQuery(select);
		List<CustomerRegistration> resultList = typedQuery.getResultList();

		return resultList;
	}

	@Override
	public CustomerRegistration findCustomerByMailId(String mailId) {

		log.debug(" 'Enter into the findCustomerByMailId()' " + mailId);

		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<CustomerRegistration> c = cb
				.createQuery(CustomerRegistration.class);
		Root<CustomerRegistration> root = c.from(CustomerRegistration.class);
		c.select(root);
		// c.distinct(emp);
		List<Predicate> criteria = new ArrayList<Predicate>();

		if (mailId != null) {
			ParameterExpression<String> p = cb.parameter(String.class, "email");
			criteria.add(cb.equal(root.get("email"), p));
		}

		if (criteria.size() == 0) {
			throw new RuntimeException("no criteria");
		} else if (criteria.size() == 1) {
			c.where(criteria.get(0));
		}

		TypedQuery<CustomerRegistration> q = manager.createQuery(c);

		if (mailId != null) {
			q.setParameter("email", mailId);
		}

		List<CustomerRegistration> resultList = q.getResultList();
		log.debug("Result List -- > " + resultList.size());
		return resultList.get(0);
	}

	@Override
	public CustomerRegistration findCustomerByPhNum(Long phno) {
		log.debug(" 'Enter into the findCustomerByPhNum()' " + phno);

		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<CustomerRegistration> c = cb
				.createQuery(CustomerRegistration.class);
		Root<CustomerRegistration> root = c.from(CustomerRegistration.class);
		c.select(root);
		List<Predicate> criteria = new ArrayList<Predicate>();

		if (phno != 0) {
			ParameterExpression<Long> p = cb.parameter(Long.class,
					"phoneNumber");
			criteria.add(cb.equal(root.get("phoneNumber"), p));
		}

		if (criteria.size() == 0) {
			throw new RuntimeException("no criteria");
		} else if (criteria.size() == 1) {
			c.where(criteria.get(0));
		}

		TypedQuery<CustomerRegistration> q = manager.createQuery(c);

		if (phno != 0) {
			q.setParameter("phoneNumber", phno);
		}
		/*
		 * CustomerRegistration customerRegistration = q.getSingleResult();
		 * log.debug("Phn No - " + customerRegistration.getPhoneNumber());
		 */

		List<CustomerRegistration> resultList = q.getResultList();
		log.debug("Result List -- > " + resultList.size());
		CustomerRegistration cust = null;
		if (Utils.isNotEmpty(resultList)) {
			cust = resultList.get(0);
		}
		return cust;
	}

}