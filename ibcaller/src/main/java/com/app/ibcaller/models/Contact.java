package com.app.ibcaller.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Pranav.Pandey
 *
 */
@Entity
@Table(name = "contacts")
public class Contact {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long contact_id;

	@Column(name = "contact_user_id", nullable = true, length = 20)
	private String contactuserId;

	@Column(name = "name", nullable = false, length = 50)
	private String username;

	@Column(name = "contact", nullable = false, length = 20)
	private String contact;

	public Long getContact_id() {
		return contact_id;
	}

	public void setContact_id(Long contact_id) {
		this.contact_id = contact_id;
	}

	public String getContactuserId() {
		return contactuserId;
	}

	public void setContactuserId(String contactuserId) {
		this.contactuserId = contactuserId;
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

}
