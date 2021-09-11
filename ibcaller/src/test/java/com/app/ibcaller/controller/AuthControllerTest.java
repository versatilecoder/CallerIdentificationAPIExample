package com.app.ibcaller.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.app.ibcaller.models.JwtRequest;
import com.app.ibcaller.models.User;
import com.app.ibcaller.service.UserDetailsServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest extends AbstractTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserDetailsServiceImpl userDetailService;

	@Mock
	UserDetails userDetails;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Test
	public void registerUser() throws Exception {
		User user = new User();
		user.setId(1L);
		user.setContact("99");
		user.setEmail("aa@gm");
		user.setPassword("pp");
		user.setUsername("pranav");

		String inputJson = super.mapToJson(user);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/register")
				.accept(MediaType.APPLICATION_JSON).content(inputJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}

	@Test
	public void loginUser() throws Exception {
		JwtRequest user = new JwtRequest();

		user.setPassword(bcryptEncoder.encode("pp"));
		user.setUsername("pranav");
		userDetails = new org.springframework.security.core.userdetails.User("pranav", "pp", new ArrayList<>());

		Mockito.when(userDetailService.loadUserByUsername("pranav")).thenReturn(userDetails);

		String inputJson = super.mapToJson(user);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/authenticate")
				.accept(MediaType.APPLICATION_JSON).content(inputJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatus());

	}

}
