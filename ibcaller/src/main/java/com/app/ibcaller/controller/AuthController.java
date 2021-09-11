package com.app.ibcaller.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.ibcaller.models.JwtRequest;
import com.app.ibcaller.models.JwtResponse;
import com.app.ibcaller.models.User;
import com.app.ibcaller.security.JwtUtils;
import com.app.ibcaller.service.UserDetailsServiceImpl;

import io.swagger.annotations.ApiOperation;

/**
 * @author Pranav.Pandey
 *
 */
@RequestMapping("/api/v1")
@RestController
@CrossOrigin
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtils jwtTokenUtil;

	@Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Autowired
	private HttpSession httpSession;

	/**
	 * @param authenticationRequest
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "User should login using username and password")
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		httpSession.setAttribute("UserName", authenticationRequest.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponse(token));
	}

	/**
	 * User registration in portal
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "User registration in portal")
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> saveUser(@RequestBody User user) throws Exception {

		if (userDetailsService.existsByContact(user.getContact())) {
			return new ResponseEntity<>("Contact Already Registered!!", HttpStatus.BAD_REQUEST);
		}
		System.out.println(user);
		return ResponseEntity.ok(userDetailsService.save(user));
	}

	/**
	 * @param username
	 * @param password
	 * @throws Exception
	 */
	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}