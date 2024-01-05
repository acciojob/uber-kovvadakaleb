package com.driver.controllers;

import com.driver.services.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/driver")
public class DriverController {

	@Autowired
	DriverService driverService;
	@PostMapping(value = "/register")
	public ResponseEntity registerDriver(@RequestParam String mobile, @RequestParam String password){
		driverService.register(mobile,password);
		return new ResponseEntity("Driver Register Successfully",HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/delete")
	public ResponseEntity deleteDriver(@RequestParam("id") Integer driverId){
		try {
			driverService.removeDriver(driverId);
			return new ResponseEntity("Driver Deleted Successfully",HttpStatus.ACCEPTED);
		}
		catch(Exception e){
			return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
		}

	}

	@PutMapping("/status")
	public ResponseEntity updateStatus(@RequestParam("id") Integer driverId){
		try {
			driverService.updateStatus(driverId);
			return new ResponseEntity("Driver Updated",HttpStatus.ACCEPTED);
		}
		catch (Exception e){
			return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}
}
