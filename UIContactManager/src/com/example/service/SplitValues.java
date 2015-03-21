package com.example.service;

public class SplitValues {

	public SplitValues()
	{
		
	}
	   /*
	    * Code Written By   : Pavan Trinath
	    * Net ID 			: BXV131230
	    * Course 			: CS6301
	    * Function Name		: public AContact splitTheValues(String contactWhole)
	    * Description 		:
	    * 	SplitTheValues is the function used to split the values of first name, last name,
	    * phone number and the e-Mail values from the value retrieved through the key on TreeMap.
	    * Here the length of each parameter is configurable on the Constants class. If you update
	    * the length of the characters on any parameter then you should probably update the
	    * android:maxlength attribute value on the corresponding XML file.
	    * 	This method returns the whole contact stored on the object of the AContact class
	    * which is just a virtual class used to store temporary information.
		*/
	public AContact splitTheValues(String contactWhole)
	{
		int startPos=0;
		int newPos=0;
		
		newPos=Constants.fNameLength;
		AContact contactDetail=new AContact();
		contactDetail.setFirstName(contactWhole.substring(startPos,newPos));
	
		startPos=newPos;
		newPos=startPos+Constants.lNameLength;
		contactDetail.setLastName(contactWhole.substring(startPos, newPos));

		startPos=newPos;
		newPos=startPos+Constants.pNoLength;
		contactDetail.setpNo(contactWhole.substring(startPos, newPos));

		startPos=newPos;
		newPos=startPos+Constants.eMailLength-1;
		contactDetail.seteMail(contactWhole.substring(startPos, newPos));

		return contactDetail;
		
	}
}
