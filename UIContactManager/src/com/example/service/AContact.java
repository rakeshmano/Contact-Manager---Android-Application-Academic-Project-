package com.example.service;
/*
 * Code Written By  : Rakesh Manoharan
 * Net ID 			: rxm143130
 * Course 			: CS6301
 * Description 		:
 * Class AContact is used to hold all the parameter values of a single contact at any point of time
 * This is a virtual object which is used to carry data between operations or classes.
 * This class has only getters and setters methods for all the parameters of a contact record.
 */
public class AContact {

	String firstName, lastName, pNo, eMail;

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the pNo
	 */
	public String getpNo() {
		return pNo;
	}

	/**
	 * @param pNo the pNo to set
	 */
	public void setpNo(String pNo) {
		this.pNo = pNo;
	}

	/**
	 * @return the eMail
	 */
	public String geteMail() {
		return eMail;
	}

	/**
	 * @param eMail the eMail to set
	 */
	public void seteMail(String eMail) {
		this.eMail = eMail;
	}
	
	
}
