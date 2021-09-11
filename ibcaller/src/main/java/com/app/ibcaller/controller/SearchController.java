package com.app.ibcaller.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.ibcaller.exception.RecordNotFoundException;
import com.app.ibcaller.service.UserDetailsServiceImpl;

import io.swagger.annotations.ApiOperation;

/**
 * @author Pranav.Pandey
 *
 */
@RequestMapping("/api/v1")
@RestController
@CrossOrigin
public class SearchController {

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	/**
	 * Search person by contact number
	 * 
	 * @param contact
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "Search  person by contact number")
	@RequestMapping(value = "/searchByContact", method = RequestMethod.GET)
	public ResponseEntity<?> searchByContact(@RequestParam String contact) throws RecordNotFoundException {
		if (userDetailsService.existsByContact(contact)) {
			return ResponseEntity.ok(userDetailsService.searchByContactRegistered(contact));
		} else {
			return ResponseEntity.ok(userDetailsService.searchByContactGlobal(contact));
		}
	}

	/**
	 * Search person by Name
	 * 
	 * @param name
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "Search person by Name")
	@RequestMapping(value = "/searchByName", method = RequestMethod.GET)
	public ResponseEntity<?> searchByName(@RequestParam String name) throws RecordNotFoundException {
		if (userDetailsService.searchByName(name).isEmpty()) {
			return ResponseEntity.ok(userDetailsService.searchByNameRegistered(name));
		} else {
			return ResponseEntity.ok(userDetailsService.searchByName(name));
		}
	}
	/**
	 * See full details of person using his contact number
	 * 
	 * @param contact
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "See full details of person using his contact number")
	@RequestMapping(value = "/details/{contact}", method = RequestMethod.GET)
	public ResponseEntity<?> getDetailsByContact(@PathVariable String contact) throws RecordNotFoundException {
		boolean emailDisplay = false;
		if (userDetailsService.existsByContact(contact)) {

			if (userDetailsService.checkInPersonContact(contact) > 0) {
				emailDisplay = true;
			}
			return ResponseEntity.ok(userDetailsService.getContactDetails(contact, emailDisplay));
		} else {
			return ResponseEntity.ok(userDetailsService.searchByContactGlobal(contact));
		}

	}

}
