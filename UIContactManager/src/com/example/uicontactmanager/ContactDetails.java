package com.example.uicontactmanager;


import java.util.TreeMap;

import com.example.fileIO.FileOperations;
import com.example.service.AContact;
import com.example.service.Constants;
import com.example.service.StringPadding;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

/*
 * Code Written By  : Rakesh Manoharan
 * Net ID 			: rxm143130
 * Course 			: CS6301
 * Class Name		: ContactDetails.java
 * Description		:
 * 				Contact Details activity is used to allow the user for entering contact
 * Details for adding or editing the contact on this app.
 * 
 * This class enters into the edit or add mode with the data that is passed from the previous activity
 * through the variable "mode".
 */


public class ContactDetails extends Activity implements OnClickListener {
	
	private String mode, keyFromHome;
public ContactDetails viewVariable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_details);
        
        View AddButton=findViewById(R.id.singleclickedit);
        AddButton.setOnClickListener(this);
        
        View CancelButton=findViewById(R.id.singleclickdelete);
        CancelButton.setOnClickListener(this);
        
		Bundle extras = getIntent().getExtras(); 
	    mode = extras.getString("mode");

	    if(mode.equalsIgnoreCase("edit"))
	    {
	    	String firstName=extras.getString("fName");
	    	String lastName=extras.getString("lName").trim();
	    
	    
	        
	    	EditText fName=(EditText)findViewById(R.id.editTextFirstName);
	    	EditText lName=(EditText)findViewById(R.id.editTextLastName);
	    	EditText pNo=(EditText)findViewById(R.id.editTextPhone);
	    	EditText eMail=(EditText)findViewById(R.id.editTextEmail);
	    	fName.setText(firstName);
	    	lName.setText(lastName);
	    
	    	FileOperations fileDetails=new FileOperations();
	    	TreeMap<String,String> userData=fileDetails.read();
	    
	    	StringPadding pad=new StringPadding();
	    	firstName=pad.StringPaddingorTrucating(firstName, Constants.fNameLength);
	    	lastName=pad.StringPaddingorTrucating(lastName, Constants.lNameLength);
	    	keyFromHome=firstName.concat(lastName);
	    
	    	String value=userData.get(keyFromHome);
	    	int startPosition=Constants.fNameLength+ Constants.lNameLength;
	    	String phNoVal=value.substring(startPosition, startPosition+Constants.pNoLength);
	    	String eMailVal=value.substring(startPosition+Constants.pNoLength, startPosition+Constants.pNoLength+Constants.eMailLength);
	    
	    	pNo.setText(phNoVal.trim());
	    	eMail.setText(eMailVal.trim());
	    }
    }
    
    
	   @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.actions_back, menu);
	        return true;
	    }

	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        // Handle action bar item clicks here. The action bar will
	        // automatically handle clicks on the Home/Up button, so long
	        // as you specify a parent activity in AndroidManifest.xml.
	        int id = item.getItemId();
	        if(id==R.id.actionBarBackIcon)
	        {
	    		Intent i=new Intent(this,ContactListActivity.class);
	    		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
	    		startActivity(i);
	    		this.finish();
	           return true;
	        	
	        }
	        return super.onOptionsItemSelected(item);
	    }


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){

		case R.id.singleclickedit:
			EditText fName=(EditText)findViewById(R.id.editTextFirstName);
			EditText lName=(EditText)findViewById(R.id.editTextLastName);
			EditText pNo=(EditText)findViewById(R.id.editTextPhone);
			EditText eMail=(EditText)findViewById(R.id.editTextEmail);
			String fstName=fName.getText().toString();
			String fNameWOspaces=fstName.replaceAll(" ", "");
			String lstName=lName.getText().toString();
			String lNameWOspaces=lstName.replaceAll(" ", "");
			AContact contact=new AContact();
			StringPadding pad=new StringPadding();
			contact.setFirstName(pad.StringPaddingorTrucating(fNameWOspaces,Constants.fNameLength));
			contact.setLastName(pad.StringPaddingorTrucating(lNameWOspaces,Constants.lNameLength));
			contact.setpNo(pad.StringPaddingorTrucating(pNo.getText().toString(),Constants.pNoLength));
			contact.seteMail(pad.StringPaddingorTrucating(eMail.getText().toString(),Constants.eMailLength));
			
			
			if(contact.getFirstName().trim().length()<1)
			{
				final Context context = this;
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		 
					// set title
					//alertDialogBuilder.setTitle("Your Title");
		 
					// set dialog message
					alertDialogBuilder
						.setMessage("Please, Enter all the required fields")
						.setCancelable(false)
						.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								// if this button is clicked, close the dialog box
								
							}
						  });
		 
						// create alert dialog
						AlertDialog alertDialog = alertDialogBuilder.create();
		 
						// show it
						alertDialog.show();
						break;
			}
			else if(!mode.equalsIgnoreCase("edit"))
			{
				FileOperations fileWrite=new FileOperations();
				TreeMap<String,String> userData=fileWrite.read();
				String key=contact.getFirstName().concat(contact.getLastName());
				String value=key.concat(contact.getpNo()).concat(contact.geteMail());
				userData.put(key,value);
				fileWrite.write(userData);
				viewVariable=this;
		    	final Context context = this;
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		 
					// set title
					//alertDialogBuilder.setTitle("Your Title");
		 
					// set dialog message
					alertDialogBuilder
						.setMessage("Contact is saved!")
						.setCancelable(false)
						.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								// if this button is clicked, close the dialog box
								Intent i=new Intent(viewVariable,ContactListActivity.class);
								i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
								i.putExtra("saveMsg", "success");
								startActivity(i);
								viewVariable.finish();
								
							}
						  });
						// create alert dialog
						AlertDialog alertDialog = alertDialogBuilder.create();
		 
						// show it
						alertDialog.show();
		
				break;	
			}
			else
			{
				FileOperations fileWrite=new FileOperations();
				TreeMap<String,String> userData=fileWrite.read();
				String key=contact.getFirstName().concat(contact.getLastName());
				String value=key.concat(contact.getpNo()).concat(contact.geteMail());
				userData.remove(keyFromHome);
				userData.put(key,value);
				fileWrite.write(userData);
				viewVariable=this;
		    	final Context context = this;
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		 
					// set title
					//alertDialogBuilder.setTitle("Your Title");
		 
					// set dialog message
					alertDialogBuilder
						.setMessage("Contact is updated!")
						.setCancelable(false)
						.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								// if this button is clicked, close the dialog box
								Intent i=new Intent(viewVariable,ContactListActivity.class);
								i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
								i.putExtra("saveMsg", "success");
								startActivity(i);
								viewVariable.finish();
								
							}
						  });
						// create alert dialog
						AlertDialog alertDialog = alertDialogBuilder.create();
		 
						// show it
						alertDialog.show();
		
				break;	
			}
		case R.id.singleclickdelete:
		Intent cancel=new Intent(this,ContactListActivity.class);
		cancel.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
		cancel.putExtra("saveMsg", "fail");
		startActivity(cancel);
		this.finish();
		break;	

		}
		
	}
}
