package com.app.ibcaller.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.ibcaller.models.Contact;
import com.app.ibcaller.models.User;

/**
 * @author Pranav.Pandey
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String userName);

	@Query(value = "SELECT * from contacts c where c.name like %:name%", nativeQuery = true)
	List<Contact> searchByUsername(@Param("name") String name);

	Optional<User> findByContact(String contact);

	Boolean existsByUsername(String userName);

	Boolean existsByContact(String contact);

	@Transactional
	@Modifying
	@Query("UPDATE Spam t set t.spamcount = t.spamcount + 1 WHERE t.contact = :contact")
	int updateSpamCount(@Param("contact") String contact);

	@Transactional
	@Modifying
	@Query("SELECT count(s.contact) FROM Spam s WHERE s.contact = :contact")
	int spamByContact(@Param("contact") String contact);

}
