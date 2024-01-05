package com.driver.controllers;

import com.driver.exception.AdminNotFound;
import com.driver.model.Admin;
import com.driver.model.Customer;
import com.driver.model.Driver;
import com.driver.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	AdminService adminService;



	@PostMapping("/register")
	public ResponseEntity registerAdmin(@RequestBody Admin admin){
		adminService.adminRegister(admin);
		return new ResponseEntity<>("Admin Registered Successfully",HttpStatus.OK);
	}

	@PutMapping("/update")
	public ResponseEntity updateAdminPassword(@RequestParam("id") Integer adminId, @RequestParam("password") String password){
		try {
			Admin updatedAdmin = adminService.updatePassword(adminId, password);
			return new ResponseEntity<>(updatedAdmin, HttpStatus.OK);
		}
		catch(AdminNotFound e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
		}
	}

	@DeleteMapping("/delete")
	public ResponseEntity deleteAdmin(@RequestParam("id") Integer adminId){
		try {
			adminService.deleteAdmin(adminId);
			return new ResponseEntity("Admin Deleted Successfully",HttpStatus.ACCEPTED);
		}
		catch(AdminNotFound e) {
			return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/listOfCustomers")
	public List<Customer> listOfCustomers() {
		List<Customer> listOfCustomers = adminService.getListOfCustomers();
		return listOfCustomers;
	}

	@GetMapping("/listOfDrivers")
	public List<Driver> listOfDrivers() {
		List<Driver> listOfDrivers = adminService.getListOfDrivers();
		return listOfDrivers;
	}
}
