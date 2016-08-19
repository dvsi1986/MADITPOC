package com.madit.spring.customer.register.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.madit.spring.customer.register.dao.CustomerRegistrationDAO;
import com.madit.spring.customer.register.model.CustomerRegistration;

@Service
public class CustomerRegistrationManagerImpl implements
		CustomerRegistrationManager {
	private static final Logger logger = LoggerFactory
			.getLogger(CustomerRegistrationManagerImpl.class);
	@Autowired
	CustomerRegistrationDAO dao;

	@Override
	public void addCustomer(CustomerRegistration customer) {

		logger.info(" 'Enter into the CustomerRegistrationManagerImpl - > addCustomer ()' ");
		logger.info(" 'Customer - > Name' " + customer.getName());
		logger.info(" 'Customer - > ID' " + customer.getEmail());
		logger.info(" 'Phone Number - > ID' " + customer.getPhoneNumber());

		dao.addCustomer(customer);

	}

	@Override
	public void updateCustomer(CustomerRegistration customer) {
		dao.updateCustomer(customer);
	}

	@Override
	public void deleteCustomer(Long phno) {
		dao.deleteCustomer(phno);
	}

	@Override
	public CustomerRegistration getCustomer(String username, String password) {
		CustomerRegistration cust = dao.getCustomer(username,password);
		return cust;
	}

	@Override
	public List<CustomerRegistration> getAllCustomers() {
		List<CustomerRegistration> custList = dao.getAllCustomers();
		return custList;
	}

	@Override
	public CustomerRegistration findCustomerByMailId(String mailId) {
		CustomerRegistration cust = dao.findCustomerByMailId(mailId);
		return cust;
	}

	@Override
	public CustomerRegistration findCustomerByPhNum(Long phno) {
		CustomerRegistration cust = dao.findCustomerByPhNum(phno);
		return cust;
	}
}
