package com.driver.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.driver.exception.AdminNotFound;
import com.driver.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.stereotype.Service;

import com.driver.model.Admin;
import com.driver.model.Customer;
import com.driver.model.Driver;
import com.driver.repository.AdminRepository;
import com.driver.repository.CustomerRepository;
import com.driver.repository.DriverRepository;

import javax.transaction.Transactional;

@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	AdminRepository adminRepository1;

	@Autowired
	DriverRepository driverRepository1;

	@Autowired
	CustomerRepository customerRepository1;

	@Override
	public void adminRegister(Admin admin) {
		//Save the admin in the database
		adminRepository1.save(admin);
	}

	@Override
	public Admin updatePassword(Integer adminId, String password) {
		//Update the password of admin with given id
		Optional<Admin> adminOptional = Optional.ofNullable(adminRepository1.findByAdminId(adminId));
		if(!adminOptional.isPresent()){
			throw new AdminNotFound("Invalid AdminId");
		}
		Admin admin = adminOptional.get();
		admin.setPassword(password);
		Admin updateAdmin = adminRepository1.save(admin);
		return updateAdmin;
	}

	@Override
	@Transactional
	public void deleteAdmin(int adminId){
		// Delete admin without using deleteById function
		Optional<Admin> adminOptional = Optional.ofNullable(adminRepository1.findByAdminId(adminId));
		if(!adminOptional.isPresent()){
			throw new AdminNotFound("Invalid AdminId");
		}
		adminRepository1.deleteByAdminId(adminId);
	}

	@Override
	public List<Driver> getListOfDrivers() {
		//Find the list of all drivers
      List<Driver> driverList = driverRepository1.findAll();
	  return driverList;
	}

	@Override
	public List<Customer> getListOfCustomers() {
		//Find the list of all customers
		List<Customer> customerList = customerRepository1.findAll();
		return customerList;
	}

}
