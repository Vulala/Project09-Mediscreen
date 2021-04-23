package com.abernathyclinic.mediscreen.model;

import java.util.UUID;

/**
 * Entity representing a patient. <br>
 */
public class Patient {

	private UUID uuid;
	private String lastName;
	private String firstName;
	private String dateOfBirth;
	private String gender;
	private String homeAddress;
	private String phoneNumber;

	public Patient() {
	}

	public Patient(String lastName, String firstName, String dateOfBirth, String gender, String homeAddress,
			String phoneNumber) {
		this.lastName = lastName;
		this.firstName = firstName;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.homeAddress = homeAddress;
		this.phoneNumber = phoneNumber;
	}

	public UUID getUuid() {
		return uuid;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return "Patient [UUID: " + uuid + ", Last Name: " + lastName + ", First Name: " + firstName
				+ ", Date of Birth: " + dateOfBirth + ", Gender: " + gender + ", Home Address: " + homeAddress
				+ ", Phone Number: " + phoneNumber + "]";
	}

}
