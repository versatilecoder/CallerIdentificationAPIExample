package com.app.ibcaller.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.ibcaller.dom.SearchResults;
import com.app.ibcaller.exception.RecordNotFoundException;
import com.app.ibcaller.models.User;
import com.app.ibcaller.repository.UserRepository;

/**
 * @author Pranav.Pandey
 *
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	UserRepository userRepository;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Autowired
	private EntityManager entityManager;

	@Autowired
	private HttpSession httpSession;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				new ArrayList<>());
	}

	/**
	 * Saving person details in repository
	 * 
	 * @param user
	 * @return User Object
	 */
	public User save(User user) {
		User newUser = new User();
		newUser.setUsername(user.getUsername());
		newUser.setContact(user.getContact());
		newUser.setEmail(user.getEmail());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		return userRepository.save(newUser);
	}

	/**
	 * Check if registered user exist by contact number
	 * 
	 * @param contact
	 * @return true or false
	 */
	public boolean existsByContact(String contact) {
		return userRepository.existsByContact(contact);
	}

	/**
	 * Fetching person details with contact number
	 * 
	 * @param contact
	 * @return UserDetails object
	 * @throws UsernameNotFoundException
	 */
	public UserDetails findUserByContact(String contact) throws UsernameNotFoundException {
		User user = userRepository.findByContact(contact)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with contact: " + contact));
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				new ArrayList<>());
	}

	/**
	 * Updating spam count of contact
	 * 
	 * @param contact
	 * @return update status 0 or 1
	 */
	public int markSpam(String contact) {
		return userRepository.updateSpamCount(contact);
	}

	/**
	 * Inserting new contact in spam list
	 * 
	 * @param contact
	 * @param name
	 * @throws RecordNotFoundException
	 */
	@Transactional
	public void addSpam(String contact, String name) throws RecordNotFoundException {
		entityManager.createNativeQuery("INSERT INTO Spam (contact, spamcount) VALUES (?1,?2)").setParameter(1, contact)
				.setParameter(2, 1).executeUpdate();

		entityManager.createNativeQuery("INSERT INTO Contacts (contact, name) VALUES (?1,?2)").setParameter(1, contact)
				.setParameter(2, name).executeUpdate();

	}

	/**
	 * Check if contact already exist in spam list
	 * 
	 * @param contact
	 * @return integer value
	 * @throws RecordNotFoundException
	 */
	@Transactional
	public int spamByContact(String contact) throws RecordNotFoundException {
		List<String> s = entityManager.createQuery("SELECT s.contact FROM Spam s where s.contact=?1", String.class)
				.setParameter(1, contact).getResultList();
		return s.size();

	}

	/**
	 * Searching person with name in global database
	 * 
	 * @param contact
	 * @return List of Search results
	 * @throws RecordNotFoundException
	 */
	@Transactional
	public List<SearchResults> searchByName(String contact) throws RecordNotFoundException {
		List<Object[]> results = entityManager.createQuery(
				"SELECT c.username,c.contact,s.spamcount from Contact c left join  Spam s on c.contact=s.contact where c.username like CONCAT('%',?1,'%') ",
				Object[].class).setParameter(1, contact).getResultList();
		List<SearchResults> list = new ArrayList<>();

		for (Object[] row : results) {
			SearchResults search = new SearchResults();
			search.setUsername((String) row[0]);
			search.setContact((String) row[1]);
			if (null != row[2])
				search.setSpamcount(row[2].toString());
			else
				search.setSpamcount("0");
			list.add(search);
		}
		return list;

	}

	/**
	 * Searching person by name in registered user
	 * 
	 * @param contact
	 * @return
	 * @throws RecordNotFoundException
	 */
	@Transactional
	public List<SearchResults> searchByNameRegistered(String contact) throws RecordNotFoundException {
		List<Object[]> results = entityManager.createQuery(
				"SELECT u.username,u.contact,u.email,s.spamcount from User u left join  Spam s on u.contact=s.contact where u.username like CONCAT('%',?1,'%')",
				Object[].class).setParameter(1, contact).getResultList();

		if (results.isEmpty()) {
			throw new RecordNotFoundException("No User with Name: " + contact + " not found");
		}
		List<SearchResults> list = new ArrayList<>();

		for (Object[] row : results) {
			SearchResults search = new SearchResults();
			search.setUsername((String) row[0]);
			search.setContact((String) row[1]);
			search.setSpamcount((String) row[3]);

			list.add(search);
		}
		return list;

	}

	/**
	 * Searching a person by contact number from registered Users
	 * 
	 * @param contact
	 * @return List of all search results
	 * @throws RecordNotFoundException
	 */
	@Transactional
	public List<SearchResults> searchByContactRegistered(String contact) throws RecordNotFoundException {
		List<Object[]> results = entityManager.createQuery(
				"SELECT c.username,c.contact,s.spamcount from User c left join  Spam s on c.contact=s.contact where c.contact like CONCAT('%',?1,'%') ",
				Object[].class).setParameter(1, contact).getResultList();
		if (results.isEmpty()) {
			throw new RecordNotFoundException("Contact Number: " + contact + " not found");
		}
		List<SearchResults> list = new ArrayList<>();

		for (Object[] row : results) {
			SearchResults search = new SearchResults();
			search.setUsername((String) row[0]);
			search.setContact((String) row[1]);
			if(null!=row[2])
			search.setSpamcount(row[2].toString());
			else
			search.setSpamcount("0");	
			list.add(search);
		}
		return list;

	}

	/**
	 * Finding person by contact in global database
	 * 
	 * @param contact
	 * @return List of search results
	 * @throws RecordNotFoundException
	 */
	@Transactional
	public List<SearchResults> searchByContactGlobal(String contact) throws RecordNotFoundException {
		List<Object[]> results = entityManager.createQuery(
				"SELECT c.username,c.contact,s.spamcount from Contact c left join  Spam s on c.contact=s.contact where c.contact like CONCAT('%',?1,'%') ",
				Object[].class).setParameter(1, contact).getResultList();

		if (results.isEmpty()) {
			throw new RecordNotFoundException("Contact Number: " + contact + " not found");
		}
		List<SearchResults> list = new ArrayList<>();

		for (Object[] row : results) {
			SearchResults search = new SearchResults();
			search.setUsername((String) row[0]);
			search.setContact((String) row[1]);
			if (null != row[2])
				search.setSpamcount(row[2].toString());
			else
				search.setSpamcount("0");
			list.add(search);
		}
		return list;

	}

	/**
	 * Get full details of person if a searching person is in contact list
	 * 
	 * @param contact
	 * @param emailDisplay
	 * @return List of search result
	 * @throws RecordNotFoundException
	 */
	@Transactional
	public List<SearchResults> getContactDetails(String contact, boolean emailDisplay) throws RecordNotFoundException {

		List<Object[]> results = entityManager.createQuery(
				"SELECT c.username,c.contact,s.spamcount,c.email from User c left join  Spam s on c.contact=s.contact where c.contact like CONCAT('%',?1,'%') ",
				Object[].class).setParameter(1, contact).getResultList();

		List<SearchResults> list = new ArrayList<>();
		if (results.isEmpty()) {
			throw new RecordNotFoundException("Contact Number: " + contact + " not found");
		}
		for (Object[] row : results) {
			SearchResults search = new SearchResults();
			search.setUsername((String) row[0]);
			search.setContact((String) row[1]);
			if(null!=row[2])
			search.setSpamcount((String) row[2]);
			else
				search.setSpamcount("0");
			if (emailDisplay) {
				search.setEmail((String) row[3]);
			}

			list.add(search);
		}
		return list;

	}

	/**
	 * Check if searching person is in contact list of searched person
	 * 
	 * @param contact
	 * @return int value
	 * @throws RecordNotFoundException
	 */
	@Transactional
	public int checkInPersonContact(String contact) throws RecordNotFoundException {

		String username = (String) httpSession.getAttribute("UserName");
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
		List<Object[]> results = entityManager.createQuery(
				"SELECT u.username FROM Contact c,User u WHERE c.contactuserId=u.id AND c.contact=?1 AND u.contact=?2",
				Object[].class).setParameter(1, user.getContact()).setParameter(2, contact).getResultList();

		return results.size();

	}

}
