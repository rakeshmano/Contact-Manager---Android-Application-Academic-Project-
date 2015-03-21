package com.example.service;

/*
 * Code Written By  : Rakesh Manoharan
 * Net ID 			: rxm143130
 * Course 			: CS6301
 * Function Name	: public String StringPaddingorTrucating(String value, int count)
 * Description		:
 * 					StringPadding class is used to achieve the fixed level length required for writing the each record 
 * on file relating to one contact.
 * Function StringPaddingorTrucating is used to append the spaces to reach the required characters or 
 * get the substring if the length exceeded.
 */
public class StringPadding {

	public String StringPaddingorTrucating(String value, int count)
	{

		if(value.length()==count)
		{
			return value;
		}
		else if(value.length()<count)
		{
			int i=value.length();
			int repeat=count-i;
			for(int j=0;j<repeat;j++)
			{
				value=value.concat(" ");
			}
			return value;
		}
		else
		{
			value=value.substring(0, count);
		}
		

		return value;
	}
}
