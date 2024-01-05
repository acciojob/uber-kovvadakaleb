package com.driver.services.impl;

import com.driver.exception.CustomerNotFound;
import com.driver.exception.DriverNotAvailable;
import com.driver.exception.TripNotFound;
import com.driver.model.TripBooking;
import com.driver.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.driver.model.Customer;
import com.driver.model.Driver;
import com.driver.repository.CustomerRepository;
import com.driver.repository.DriverRepository;
import com.driver.repository.TripBookingRepository;
import com.driver.model.TripStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository2;

	@Autowired
	DriverRepository driverRepository2;

	@Autowired
	TripBookingRepository tripBookingRepository2;

	@Override
	public void register(Customer customer) {
		//Save the customer in database
		customerRepository2.save(customer);
	}

	@Override
	public void deleteCustomer(Integer customerId) {
		// Delete customer without using deleteById function
		Optional<Customer> optionalCustomer = customerRepository2.findById(customerId);
		if(!optionalCustomer.isPresent()){
			throw new CustomerNotFound("Customer Doesn't Exist");
		}
		Customer customer = optionalCustomer.get();
		customerRepository2.delete(customer);
	}

	@Override
	public TripBooking bookTrip(int customerId, String fromLocation, String toLocation, int distanceInKm) throws Exception{
		//Book the driver with lowest driverId who is free (cab available variable is Boolean.TRUE). If no driver is available, throw "No cab available!" exception
		//Avoid using SQL query
        Optional<Customer> optionalCustomer = customerRepository2.findById(customerId);
        if(!optionalCustomer.isPresent()){
            throw new CustomerNotFound("Customer Doesn't Exist");
        }
		Customer customer = optionalCustomer.get();
       List<Driver> drivers = driverRepository2.findAll();
	   Driver assignDriver = null;
	   boolean available = false;
	   for(Driver driver : drivers){
		   if(driver.getCab().isAvailable()==true){
			   assignDriver = driver;
			   available = true;
			   break;
		   }
	   }

	   if(available==false) throw new DriverNotAvailable("No Cab available");

	   TripBooking tripBooking = new TripBooking();
	   tripBooking.setFromLocation(fromLocation);
	   tripBooking.setToLocation(toLocation);
	   tripBooking.setDistanceInKm(distanceInKm);
	   tripBooking.setBill(distanceInKm*assignDriver.getCab().getPerKmRate());
	   tripBooking.setTripStatus(TripStatus.CONFIRMED);
	   tripBooking.setCustomer(customer);
       tripBooking.setDriver(assignDriver);

	   TripBooking savedTrip = tripBookingRepository2.save(tripBooking);

	   customer.getTripBookingList().add(savedTrip);
	   assignDriver.getTripBookingList().add(savedTrip);

	   return savedTrip;
	}

	@Override
	public void cancelTrip(Integer tripId){
		//Cancel the trip having given trip Id and update TripBooking attributes accordingly
		Optional<TripBooking> tripBooking = tripBookingRepository2.findById(tripId);
		if(!tripBooking.isPresent()){
			throw new TripNotFound("No Trip Found ");
		}
		TripBooking tripBooking1 = tripBooking.get();
		tripBooking1.setTripStatus(TripStatus.CANCELED);
		tripBookingRepository2.save(tripBooking1);
	}

	@Override
	public void completeTrip(Integer tripId){
		//Complete the trip having given trip Id and update TripBooking attributes accordingly
		Optional<TripBooking> tripBooking = tripBookingRepository2.findById(tripId);
		if(!tripBooking.isPresent()){
			throw new TripNotFound("No Trip Found ");
		}
		TripBooking tripBooking1 = tripBooking.get();
		tripBooking1.setTripStatus(TripStatus.COMPLETED);
		tripBookingRepository2.save(tripBooking1);
	}
}
