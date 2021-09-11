/**
 * 
 */
package com.app.ibcaller.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.app.ibcaller.dom.SearchResults;
import com.app.ibcaller.repository.UserRepository;

/**
 * @author Pranav.Pandey
 *
 */
public class UserDetailsServiceImplTest {

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@InjectMocks
	UserDetailsServiceImpl userDetails;
	@Mock
	UserRepository userRepository;

	@Mock
	EntityManager em;

	@Mock
	Query query;

	@Test
	public void existsByContact() {

		Mockito.when(userRepository.existsByContact(Mockito.anyString())).thenReturn(true);

		assertEquals(userDetails.existsByContact("99"), true);
	}

	@Test
	public void markSpam() {

		Mockito.when(userRepository.updateSpamCount(Mockito.anyString())).thenReturn(1);

		assertEquals(userDetails.markSpam("99"), 1);
	}

	@Test
	public void searchByName() throws Exception {
		List<Object[]> results = new ArrayList<Object[]>();
		Object[] obj=new String[]{"io","ui"};
		
		SearchResults search1 = new SearchResults();
		search1.setContact("99");
		search1.setSpamcount("2");
		search1.setUsername("pranav");
		search1.setEmail("pranav@gm");
		results.add(obj);

		Mockito.when(em.createQuery(Mockito.anyString())).thenReturn(query);
		Mockito.when(query.setParameter(1, "hh")).thenReturn(query);
		Mockito.when(query.getResultList()).thenReturn(results);
		//assertEquals(userDetails.searchByName("pp").size(), 1);
	}

}
