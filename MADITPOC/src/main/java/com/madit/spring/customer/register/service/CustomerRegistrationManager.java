package com.madit.spring.customer.register.service;

import java.util.List;

import com.madit.spring.customer.register.model.CustomerRegistration;

public interface CustomerRegistrationManager 
{
	public void addCustomer(CustomerRegistration employee);
	public void updateCustomer(CustomerRegistration customer);
	public void deleteCustomer(Long phno);
	public List<CustomerRegistration> getAllCustomers();
	public CustomerRegistration findCustomerByMailId(String mailId);
	public CustomerRegistration findCustomerByPhNum(Long phno);
	public CustomerRegistration getCustomer(String username, String password);
}
