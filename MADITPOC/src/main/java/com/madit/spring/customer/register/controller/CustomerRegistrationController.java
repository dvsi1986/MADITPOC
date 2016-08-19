package com.madit.spring.customer.register.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.SessionStatus;

import com.madit.spring.customer.register.model.CustomerRegistration;
import com.madit.spring.customer.register.service.CustomerRegistrationManager;

/**
 * Handles requests for the CustomerRegistration Service.
 */
@RestController
@RequestMapping(value = "/rest/CustomerRegistration")
public class CustomerRegistrationController {

	private static final Logger logger = LoggerFactory
			.getLogger(CustomerRegistrationController.class);

	// auto wiring the service class
	@Autowired
	CustomerRegistrationManager manager;

	@RequestMapping(value = "/addCustomer", method = RequestMethod.POST, headers = "Accept=application/json")
	public CustomerRegistration addCustomer(
			@RequestBody CustomerRegistration cust, SessionStatus status) {

		logger.info("Insdide the addCustomer() Method .. .");
		logger.info("Customer Name = " + cust.getName());
		logger.info("Customer PassWord = " + cust.getPassword());
		logger.info("Customer Email = " + cust.getEmail());
		logger.info("Customer Phone Number = " + cust.getPhoneNumber());

		// Store the Customer information in database
		cust.setCreatedDate(new Date());

		manager.addCustomer(cust);

		// Mark Session Complete
		status.setComplete();
		return cust;
	}

	@RequestMapping(value = "/updateCustomer", method = RequestMethod.PUT, headers = "Accept=application/json")
	public CustomerRegistration updateCustomer(
			@RequestBody CustomerRegistration cust, SessionStatus status) {

		logger.info("Insdide the updateCustomer() Method .. .");
		logger.info("Customer Name = " + cust.getName());
		logger.info("Customer PassWord = " + cust.getPassword());
		logger.info("Customer Email = " + cust.getEmail());
		logger.info("Customer Phone Number = " + cust.getPhoneNumber());

		// Store the Customer information in database
		manager.updateCustomer(cust);

		// Mark Session Complete
		status.setComplete();
		return cust;
	}

	@RequestMapping(value = "/getCustomer/{username}/{password}", method = RequestMethod.GET, headers = "Accept=application/json")
	public CustomerRegistration getCustomer(@PathVariable("username") String username ,@PathVariable("password") String password, SessionStatus status) {

		logger.info("Insdide the addCustomer() Method .. .");
		logger.info("Customer Name = " + username);
		logger.info("Customer PassWord = " + password);

		CustomerRegistration cust=manager.getCustomer(username,password);

		// Mark Session Complete
		status.setComplete();
		return cust;
	}

	@RequestMapping(value = "/getCustomers", method = RequestMethod.GET, headers = "Accept=application/json")
	public List<CustomerRegistration> getCustomers(SessionStatus status) {

		logger.info("Insdide the getCustomers() Method .. .");

		// Fetch the Customer information in database

		List<CustomerRegistration> custList = manager.getAllCustomers();

		// Mark Session Complete
		status.setComplete();
		return custList;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/deleteCustomer/{phno}", method = RequestMethod.DELETE, headers = "Accept=application/json")
	public ResponseEntity deleteCustomer(@PathVariable("phno") long phno,	SessionStatus status) {
		logger.debug("phno - > " + phno);

		logger.info("Insdide the deleteCustomer() Method .. .");

		manager.deleteCustomer(phno);

		status.setComplete();
		return new ResponseEntity(phno, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/findCustomerByMailId/{mailid:.+}", method = RequestMethod.GET, headers = "Accept=application/json")
	public CustomerRegistration findCustomerByMailId(
			@RequestBody @PathVariable("mailid") String mailId,	SessionStatus status) {

		logger.info("Insdide the findCustomerByMailId() Method .. .");

		logger.info("Mail iD " + mailId);

		CustomerRegistration cust = manager.findCustomerByMailId(mailId);
		logger.debug("Phone number - > " + cust.getPhoneNumber());
		// Mark Session Complete
		status.setComplete();
		return cust;
	}

	@RequestMapping(value = "/findCustomerByPhoneNum/{phno}", method = RequestMethod.GET, headers = "Accept=application/json")
	public CustomerRegistration findCustomerByPhno(@PathVariable("phno") long phno,
			SessionStatus status) {
		logger.debug("mailid - > " + phno);

		CustomerRegistration cust = manager.findCustomerByPhNum(phno);
		logger.debug("Phone number - > " + cust.getPhoneNumber());
		// Mark Session Complete
		status.setComplete();
		return cust;
	}

}
