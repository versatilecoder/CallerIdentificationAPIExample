package com.app.ibcaller.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.app.ibcaller.dom.SearchResults;
import com.app.ibcaller.service.UserDetailsServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SearchControllerTest extends AbstractTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserDetailsServiceImpl userDetailService;

	@Mock
	UserDetails userDetails;

	@Test
	public void searchByContact() throws Exception {

		List<SearchResults> list = new ArrayList<SearchResults>();
		SearchResults search = new SearchResults();
		search.setContact("99");
		search.setSpamcount("2");
		search.setUsername("pp");
		search.setEmail("aa@gm");
		list.add(search);

		List<SearchResults> list1 = new ArrayList<SearchResults>();
		SearchResults search1 = new SearchResults();
		search1.setContact("99");
		search1.setSpamcount("2");
		search1.setUsername("pranav");
		search1.setEmail("pranav@gm");
		list1.add(search1);
		Mockito.when(userDetailService.existsByContact(Mockito.anyString())).thenReturn(true);
		Mockito.when(userDetailService.searchByContactGlobal(Mockito.anyString())).thenReturn(list1);
		Mockito.when(userDetailService.searchByContactRegistered(Mockito.anyString())).thenReturn(list);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/searchByContact?contact=99")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String content = result.getResponse().getContentAsString();
		SearchResults[] searchlist = super.mapFromJson(content, SearchResults[].class);
		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(searchlist[0].getUsername(), "pp");
		assertTrue(searchlist.length > 0);

	}

	@Test
	public void searchByContactGlobal() throws Exception {

		List<SearchResults> list = new ArrayList<SearchResults>();
		SearchResults search = new SearchResults();
		search.setContact("99");
		search.setSpamcount("2");
		search.setUsername("pp");
		search.setEmail("aa@gm");
		list.add(search);

		List<SearchResults> list1 = new ArrayList<SearchResults>();
		SearchResults search1 = new SearchResults();
		search1.setContact("99");
		search1.setSpamcount("2");
		search1.setUsername("pranav");
		search1.setEmail("pranav@gm");
		list1.add(search1);
		Mockito.when(userDetailService.existsByContact(Mockito.anyString())).thenReturn(false);
		Mockito.when(userDetailService.searchByContactGlobal(Mockito.anyString())).thenReturn(list1);
		Mockito.when(userDetailService.searchByContactRegistered(Mockito.anyString())).thenReturn(list);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/searchByContact?contact=99")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String content = result.getResponse().getContentAsString();
		SearchResults[] searchlist = super.mapFromJson(content, SearchResults[].class);
		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(searchlist[0].getUsername(), "pranav");
		assertTrue(searchlist.length > 0);

	}

	@Test
	public void searchByNameRegistered() throws Exception {

		List<SearchResults> list = new ArrayList<SearchResults>();

		List<SearchResults> list1 = new ArrayList<SearchResults>();
		SearchResults search1 = new SearchResults();
		search1.setContact("99");
		search1.setSpamcount("2");
		search1.setUsername("pranav");
		search1.setEmail("pranav@gm");
		list1.add(search1);
		Mockito.when(userDetailService.searchByName(Mockito.anyString())).thenReturn(list);

		Mockito.when(userDetailService.searchByNameRegistered(Mockito.anyString())).thenReturn(list1);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/searchByName?name=pp")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String content = result.getResponse().getContentAsString();
		SearchResults[] searchlist = super.mapFromJson(content, SearchResults[].class);
		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(searchlist[0].getUsername(), "pranav");
		assertTrue(searchlist.length > 0);

	}

	@Test
	public void searchByNameGlobal() throws Exception {

		List<SearchResults> list = new ArrayList<SearchResults>();
		SearchResults search = new SearchResults();
		search.setContact("99");
		search.setSpamcount("2");
		search.setUsername("pp");
		search.setEmail("aa@gm");
		list.add(search);

		List<SearchResults> list1 = new ArrayList<SearchResults>();
		SearchResults search1 = new SearchResults();
		search1.setContact("99");
		search1.setSpamcount("2");
		search1.setUsername("pranav");
		search1.setEmail("pranav@gm");
		list1.add(search1);
		Mockito.when(userDetailService.searchByName(Mockito.anyString())).thenReturn(list);

		Mockito.when(userDetailService.searchByName(Mockito.anyString())).thenReturn(list);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/searchByName?name=pp")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String content = result.getResponse().getContentAsString();
		SearchResults[] searchlist = super.mapFromJson(content, SearchResults[].class);
		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(searchlist[0].getUsername(), "pp");
		assertTrue(searchlist.length > 0);

	}

	@Test
	public void getUserDetails() throws Exception {

		List<SearchResults> list = new ArrayList<SearchResults>();
		SearchResults search = new SearchResults();
		search.setContact("99");
		search.setSpamcount("2");
		search.setUsername("pp");
		search.setEmail("aa@gm");
		list.add(search);

		List<SearchResults> list1 = new ArrayList<SearchResults>();
		SearchResults search1 = new SearchResults();
		search1.setContact("99");
		search1.setSpamcount("2");
		search1.setUsername("pranav");
		search1.setEmail("pranav@gm");
		list1.add(search1);
		Mockito.when(userDetailService.existsByContact(Mockito.anyString())).thenReturn(true);
		Mockito.when(userDetailService.checkInPersonContact(Mockito.anyString())).thenReturn(1);

		Mockito.when(userDetailService.getContactDetails(Mockito.anyString(), Mockito.eq(true))).thenReturn(list);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/details/99")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String content = result.getResponse().getContentAsString();
		SearchResults[] searchlist = super.mapFromJson(content, SearchResults[].class);
		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(searchlist[0].getEmail(), "aa@gm");
		assertTrue(searchlist.length > 0);

	}

	@Test
	public void getUserDetails1() throws Exception {

		List<SearchResults> list = new ArrayList<SearchResults>();
		SearchResults search = new SearchResults();
		search.setContact("99");
		search.setSpamcount("2");
		search.setUsername("pp");
		search.setEmail("aa@gm");
		list.add(search);

		List<SearchResults> list1 = new ArrayList<SearchResults>();
		SearchResults search1 = new SearchResults();
		search1.setContact("99");
		search1.setSpamcount("2");
		search1.setUsername("pranav");
		search1.setEmail("pranav@gm");
		list1.add(search1);
		Mockito.when(userDetailService.existsByContact(Mockito.anyString())).thenReturn(true);
		Mockito.when(userDetailService.checkInPersonContact(Mockito.anyString())).thenReturn(0);
		Mockito.when(userDetailService.getContactDetails(Mockito.anyString(), Mockito.eq(false))).thenReturn(list);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/details/99")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String content = result.getResponse().getContentAsString();
		SearchResults[] searchlist = super.mapFromJson(content, SearchResults[].class);
		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(searchlist[0].getEmail(), "aa@gm");
		assertTrue(searchlist.length > 0);

	}

	@Test
	public void getUserDetailsGlobal() throws Exception {

		List<SearchResults> list = new ArrayList<SearchResults>();
		SearchResults search = new SearchResults();
		search.setContact("99");
		search.setSpamcount("2");
		search.setUsername("pp");
		search.setEmail("aa@gm");
		list.add(search);

		List<SearchResults> list1 = new ArrayList<SearchResults>();
		SearchResults search1 = new SearchResults();
		search1.setContact("99");
		search1.setSpamcount("2");
		search1.setUsername("pranav");
		search1.setEmail("pranav@gm");
		list1.add(search1);
		Mockito.when(userDetailService.existsByContact(Mockito.anyString())).thenReturn(false);
		Mockito.when(userDetailService.checkInPersonContact(Mockito.anyString())).thenReturn(0);
		Mockito.when(userDetailService.searchByContactGlobal(Mockito.anyString())).thenReturn(list1);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/v1/details/99")
				.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		String content = result.getResponse().getContentAsString();
		SearchResults[] searchlist = super.mapFromJson(content, SearchResults[].class);
		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(searchlist[0].getEmail(), "pranav@gm");
		assertTrue(searchlist.length > 0);

	}

}
