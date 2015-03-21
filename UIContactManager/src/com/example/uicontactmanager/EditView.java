package com.example.uicontactmanager;

import java.util.TreeMap;

import com.example.fileIO.FileOperations;
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
import android.widget.TextView;


/*
 * Code Written By  : Pavan Trinath
 * Net ID 			: BXV131230
 * Course 			: CS6301
 * Class Name		: EditView
 * Description		:
 * 			When the user clicks name on the contact list, this activity is started on the view mode
 * with all the user contact details.
 * From this activity user can edit or delete the contact.
 * 
 * If user change mind to add a new contact instead of editing or deleting, application allows the user
 * to do that by clicking on the add icon on the action bar.
 * 
 */

public class EditView extends Activity  {
	public String key;
	public EditView viewDelete;
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_single_click);
        
        Bundle extras = getIntent().getExtras(); 
        String firstName=extras.getString("fName");
	    String lastName=extras.getString("lName");
	    
	    
	        
	    TextView fName=(TextView)findViewById(R.id.editfirstname);
		TextView lName=(TextView)findViewById(R.id.editlastname);
		TextView pNo=(TextView)findViewById(R.id.editphnumber);
		TextView eMail=(TextView)findViewById(R.id.editemail);
		fName.setText(firstName);
		lName.setText(lastName);
	    
		FileOperations fileDetails=new FileOperations();
	    TreeMap<String,String> userData=fileDetails.read();
	    
	    StringPadding pad=new StringPadding();
	    firstName=pad.StringPaddingorTrucating(firstName, Constants.fNameLength);
	    lastName=pad.StringPaddingorTrucating(lastName, Constants.lNameLength);
	    String key=firstName.concat(lastName);
	    
	    String value=userData.get(key);
	    int startPosition=Constants.fNameLength+ Constants.lNameLength;
	    String phNoVal=value.substring(startPosition, startPosition+Constants.pNoLength);
	    String eMailVal=value.substring(startPosition+Constants.pNoLength, startPosition+Constants.pNoLength+Constants.eMailLength);
	    
	    pNo.setText(phNoVal.trim());
	    eMail.setText(eMailVal.trim());
	    
	    /*ImageButton editButton=(ImageButton)findViewById(R.id.singleclickedit);
	    editButton.setOnClickListener(this);
	    ImageButton deleteButton=(ImageButton)findViewById(R.id.singleclickdelete);
	    deleteButton.setOnClickListener(this);*/
	}
	
	   @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	        getMenuInflater().inflate(R.menu.actions_back_add, menu);
	        return true;
	    }

	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        // Handle action bar item clicks here. The action bar will
	        // automatically handle clicks on the Home/Up button, so long
	        // as you specify a parent activity in AndroidManifest.xml.
			TextView firstName=(TextView)findViewById(R.id.editfirstname);
			TextView lastName=(TextView)findViewById(R.id.editlastname);

	        int id = item.getItemId();
	        if (id == R.id.actionBarAddIcon) {
	    		Intent i=new Intent(this,ContactDetails.class);
	    		i.putExtra("mode", "add");
	    		startActivity(i);
	    		this.finish();
	           return true;
	        }
	        else if(id==R.id.actionBarBackIcon)
	        {
	    		Intent i=new Intent(this,ContactListActivity.class);
	    		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
	    		startActivity(i);
	    		this.finish();
	           return true;
	        	
	        }
	        else if(id==R.id.actionBarEditIcon)
	        {
				String fName=firstName.getText().toString();
				String lName=lastName.getText().toString();
				Intent i=new Intent(this,ContactDetails.class);
	    		
	    		i.putExtra("fName", fName);
	    		i.putExtra("lName", lName);
	    		i.putExtra("mode", "edit");
	    		i.putExtra("saveMsg", "fail");
	    		startActivity(i);
	    		this.finish();
				return true;	        	
	        }
	        else if(id==R.id.actionBarDeleteIcon)
	        {
	        	String Name1=firstName.getText().toString();
				String Name2=lastName.getText().toString();

				StringPadding pad=new StringPadding();
				Name1=pad.StringPaddingorTrucating(Name1, Constants.fNameLength);
				Name2=pad.StringPaddingorTrucating(Name2, Constants.lNameLength);
	    	    key=Name1.concat(Name2);
	    	    viewDelete=this;
	    	    final Context context = this;
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		 
					// set title
					//alertDialogBuilder.setTitle("Your Title");
		 
					// set dialog message
					alertDialogBuilder
						.setMessage("Are you sure want to delete a contact?")
						.setCancelable(false)
						.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								// if this button is clicked, close the dialog box
								FileOperations fileWrite=new FileOperations();
								TreeMap<String,String> userData=fileWrite.read();
								
								userData.remove(key);
								fileWrite.write(userData);
								
								Intent i=new Intent(viewDelete,ContactListActivity.class);
								i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
								startActivity(i);
							viewDelete.finish();

															}
						  })
						.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								// if this button is clicked, close the dialog box
								
							}
						  });
						// create alert dialog
						AlertDialog alertDialog = alertDialogBuilder.create();
		 
						// show it
						alertDialog.show();
	        	
	        }
	        return super.onOptionsItemSelected(item);
	    }

/*	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id=v.getId();
		TextView firstName=(TextView)findViewById(R.id.editfirstname);
		TextView lastName=(TextView)findViewById(R.id.editlastname);
		switch(id){
		case R.id.singleclickedit:

			String fName=firstName.getText().toString();
			String lName=lastName.getText().toString();
			Intent i=new Intent(this,ContactDetails.class);
    		
    		i.putExtra("fName", fName);
    		i.putExtra("lName", lName);
    		i.putExtra("mode", "edit");
    		i.putExtra("saveMsg", "fail");
    		startActivity(i);
    		this.finish();
			break;
			
		case R.id.singleclickdelete:

			String Name1=firstName.getText().toString();
			String Name2=lastName.getText().toString();

			StringPadding pad=new StringPadding();
			Name1=pad.StringPaddingorTrucating(Name1, Constants.fNameLength);
			Name2=pad.StringPaddingorTrucating(Name2, Constants.lNameLength);
    	    key=Name1.concat(Name2);
    	    viewDelete=this;
    	    final Context context = this;
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
	 
				// set title
				//alertDialogBuilder.setTitle("Your Title");
	 
				// set dialog message
				alertDialogBuilder
					.setMessage("Are you sure want to delete a contact?")
					.setCancelable(false)
					.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							// if this button is clicked, close the dialog box
							FileOperations fileWrite=new FileOperations();
							TreeMap<String,String> userData=fileWrite.read();
							
							userData.remove(key);
							fileWrite.write(userData);
							
							Intent i=new Intent(viewDelete,ContactListActivity.class);
							startActivity(i);
						viewDelete.finish();

														}
					  })
					.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
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
	}*/
}
