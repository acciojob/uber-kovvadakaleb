package com.driver.controllers;

import com.driver.model.Customer;
import com.driver.model.TripBooking;
import com.driver.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	CustomerService customerService;
	@PostMapping("/register")
	public ResponseEntity registerCustomer(@RequestBody Customer customer){
		customerService.register(customer);
		return new ResponseEntity("Customer Registered Successfully",HttpStatus.CREATED);
	}

	@DeleteMapping("/delete")
	public ResponseEntity deleteCustomer(@RequestParam("id") Integer customerId){
		try{
			customerService.deleteCustomer(customerId);
			return new ResponseEntity("Customer Deleted Successfully",HttpStatus.ACCEPTED);
		}
		catch(Exception e){
			return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/bookTrip")
	public ResponseEntity bookTrip(@RequestParam("id") Integer customerId, @RequestParam("from") String fromLocation, @RequestParam("to") String toLocation, @RequestParam("d") Integer distanceInKm) throws Exception {
		try {
			TripBooking bookedTrip = customerService.bookTrip(customerId, fromLocation, toLocation, distanceInKm);
			return new ResponseEntity(bookedTrip.getTripBookingId(),HttpStatus.CREATED);
		}
		catch (Exception e){
			return new ResponseEntity(e.getMessage(),HttpStatus.NOT_FOUND);
		}

	}

	@DeleteMapping("/complete")
	public ResponseEntity completeTrip(@RequestParam Integer tripId){
		try {
			customerService.completeTrip(tripId);
			return new ResponseEntity("Your Trip was Completed",HttpStatus.OK);
		}
		catch(Exception e){
			return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/cancelTrip")
	public ResponseEntity cancelTrip(@RequestParam Integer tripId){
		try {
			customerService.cancelTrip(tripId);
			return new ResponseEntity("Your Trip Cancelled!!!",HttpStatus.OK);
		}
		catch (Exception e){
			return new ResponseEntity(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
}
