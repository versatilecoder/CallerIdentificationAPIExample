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
/**
 * @author Pranav.Pandey
 *
 */
@Entity
@Table(name = "spam")
public class Spam {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "contact", nullable = false, length = 20)
	private String contact;

	@Column(name = "spamcount", nullable = false, length = 10)
	private int spamcount;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public int getSpamcount() {
		return spamcount;
	}

	public void setSpamcount(int spamcount) {
		this.spamcount = spamcount;
	}

}
