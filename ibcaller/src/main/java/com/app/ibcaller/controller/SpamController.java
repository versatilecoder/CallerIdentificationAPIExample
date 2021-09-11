package com.app.ibcaller.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
public class SpamController {

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	/**
	 * User can add a numer with name to spam list
	 * 
	 * @param contact
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "User can add a numer with name to spam list")
	@RequestMapping(value = "/markSpam/{contact}", method = RequestMethod.POST)
	public ResponseEntity<?> addToSpam(@PathVariable String contact, @RequestParam String name)
			throws RecordNotFoundException {

		if (userDetailsService.spamByContact(contact) > 0) {
			return ResponseEntity.ok(userDetailsService.markSpam(contact));
		} else {
			userDetailsService.addSpam(contact, name);
			return new ResponseEntity<>("Spam Added!!", HttpStatus.CREATED);
		}

	}

}
