package com.example.uicontactmanager;

import java.util.Map;
import java.util.TreeMap;

import com.example.fileIO.FileOperations;
import com.example.service.AContact;
import com.example.service.Constants;
import com.example.service.SplitValues;
import com.example.service.StringPadding;

import android.R.color;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
public class ContactListActivity extends Activity implements  OnLongClickListener, OnClickListener {

	private TextView name;
	private int rowIdentifier=0;
	public String key;
	public ContactListActivity viewDelete;
	public String menuTitle;
	
	/*
	 * Code Written By  : Rakesh Manoharan
	 * Net ID 			: rxm143130
	 * Course 			: CS6301
	 * Function Name	: protected void onCreate(Bundle savedInstanceState)
	 * Description		:
	 * 				onCreate function used to design all the components on the activity screen, 
	 * when the user starts a new activity. Here, in this function the contact list initiation is 
	 * also done inside the for loop.
	 */
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {

    	super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_list);
       
        LinearLayout layout=((LinearLayout)findViewById(R.id.linearlayout));

        FileOperations fileWrite=new FileOperations();
		TreeMap<String,String> userData=fileWrite.read();

		AContact contact=new AContact();
		
		SplitValues splitVal=new SplitValues();
		
		Map<String,String> orderedData=new TreeMap<String,String>(userData);
		int i=1;
		
		for(Map.Entry<String, String> Iterator:orderedData.entrySet())
        {
        	if(Iterator.getKey()!=null)
        	{
        		contact=splitVal.splitTheValues(Iterator.getValue());
        				
        		name=new TextView(this);
        		name.setBackgroundColor(color.holo_blue_light);
        		name.setTextSize(Constants.listNamesize);
        		name.setId(i);
        		name.setBottom(600);
        		name.setPadding(0, 8, 0, 0);
        		name.setBackgroundResource(R.color.background_color);
        		name.setText(contact.getFirstName().trim().concat(" ").concat(contact.getLastName().trim()));
        		name.setOnLongClickListener(this);
        		name.setOnClickListener(this);
        		TextView pNo=new TextView(this);
        		pNo.setText(contact.getpNo().trim());
        		pNo.setTextSize(Constants.listPnosize);
        		pNo.setPadding(0, 0, 0, 30);
        		pNo.setTextColor(getResources().getColor(R.color.phonenumbercolor));
        		layout.addView(name);
        		layout.addView(pNo);
        		i++;
        		
        	}
        }
    }
    
	/*
	 * Code Written By  : Rakesh Manoharan, Pavan Trinath
	 * Net ID 			: rxm143130, BXV131230
	 * Course 			: CS6301
	 * Function Name	:  public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo)
	 * Description		:
	 * 				This onCreateContextMenu is called when view registers itself for the
	 * Context Menu. On running this function, view brings the context menu to the front of
	 * the screen.
	 * 				Context menu is designed on the edit_delete.xml file present on the res/menu.
	 * There are two menu items present on this context menu, edit and delete. when the user long press
	 * the name text view, he/she will get this context menu to edit or delete the long pressed contact.
	 */
    
    public void onCreateContextMenu(ContextMenu menu, View v,
            ContextMenuInfo menuInfo) {
    	super.onCreateContextMenu(menu, v, menuInfo);
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.edit_delete, menu);
    	menu.setHeaderTitle(menuTitle); // To set the header to the context menu
     }
	/*
	 * Code Written By  : Rakesh Manoharan, Pavan Trinath
	 * Net ID 			: rxm143130, BXV131230
	 * Course 			: CS6301
	 * Function Name	: public boolean onContextItemSelected(MenuItem item)
	 * Description		:
	 * 			On selecting the menu item from the context menu, any of the one
	 * defined case gets executed. On selecting the edit menu item, the user will
	 * be taken to the edit screen with the pre-populated data of the contact user selects.
	 * 
	 * 			On selecting the delete menu item, the program asks for the user confirmation
	 * on delete action by popping up the dialog box. On selecting ok the contact gets deleted and the
	 * list refreshes. If user selects cancel, nothing happens.
	 */
    
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        //AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        
        switch (item.getItemId()) {
        
        case R.id.menuedit:
        		TextView valueName=(TextView)findViewById(rowIdentifier);            	
        		String fullName=valueName.getText().toString();
        		String[] flName = null;
        		
        		flName=fullName.split(" ");
          		Log.d("length",Integer.toString(flName.length));
          		Log.d("FName", flName[0]);
          		String first1Name,second1Name;
          		
          		if(flName.length==1)
          		{
          			first1Name=flName[0];
          			second1Name=" ";
          		}
          		else
          		{
          			first1Name=flName[0];
          			second1Name=flName[1];
          		}

        		Intent intent = new Intent(this, ContactListActivity.class);
        		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        		startActivity(intent);
        		
        		Intent i=new Intent(this,ContactDetails.class);
        		// Following lines of code is used to store the values on Bundle to access it on the next activity.
        		i.putExtra("fName", first1Name);
        		i.putExtra("lName", second1Name);
        		i.putExtra("mode", "edit");
        		i.putExtra("saveMsg", "fail");
        		startActivity(i);

        		//this.finish();
                return true;

        case R.id.menudelete:
          		TextView deleteName=(TextView)findViewById(rowIdentifier);            	
        		String fullNameDelete=deleteName.getText().toString();
          		String[] flNameDelete=fullNameDelete.split(" ");
          		String firstName,secondName;
          		
          		if(flNameDelete.length==1)
          		{
          			firstName=flNameDelete[0];
          			secondName=" ";
          		}
          		else
          		{
          			firstName=flNameDelete[0];
          			secondName=flNameDelete[1];
          		}
          		
                
          		StringPadding pad=new StringPadding();
          		firstName=pad.StringPaddingorTrucating(firstName, Constants.fNameLength);
          		secondName=pad.StringPaddingorTrucating(secondName, Constants.lNameLength);
        	    key=firstName.concat(secondName);
        	    viewDelete=this;
        	   
        	    //Following lines of code is used to trigger the dialog box and decide an action based on the user input
        	   
        	    final Context context = this;
				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		 
				alertDialogBuilder
						.setMessage("Are you sure want to delete a contact?")
						.setCancelable(false)
						.setPositiveButton("Ok",new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,int id) {
								FileOperations fileWrite=new FileOperations();
								TreeMap<String,String> userData=fileWrite.read();
								userData.remove(key);
								fileWrite.write(userData);
								viewDelete.recreate();

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
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

	/*
	 * Code Written By  : Pavan Trinath
	 * Net ID 			: BXV131230
	 * Course 			: CS6301
	 * Function Name	: public boolean onCreateOptionsMenu(Menu menu)
	 * 					  public boolean onOptionsItemSelected(MenuItem item)
	 * Description		:
	 * 		Add Icon on the action bar is added through this onCreateOptionsMenu.
	 * 
	 * When user clicks on the add icon on the action, application opens the Contact Details screen in add mode.
	 */
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_icons, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.actionBarAddIcon) {
    		Intent intent = new Intent(this, ContactListActivity.class);
    		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    		startActivity(intent);
        	Intent i=new Intent(this,ContactDetails.class);
    		i.putExtra("mode", "add");
    		startActivity(i);
    		//this.finish();
           return true;
        }
        return super.onOptionsItemSelected(item);
    }
    /*
	 * Code Written By  : Rakesh Manoharan, Pavan Trinath
	 * Net ID 			: rxm143130, BXV131230
	 * Course 			: CS6301
	 * Function Name	: public boolean onLongClick(View v)
	 * Description		:
	 * 				When the user long clicks name on the contact list, the context menu should
	 * open with the actions edit and delete. This function is used to register the view with context menu. 
	 */

	@Override
	public boolean onLongClick(View v) {
		// TODO Auto-generated method stub
		rowIdentifier=v.getId();
		TextView valueName=(TextView)findViewById(rowIdentifier);            	
		menuTitle=valueName.getText().toString();
		this.registerForContextMenu(v);
		return false;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		rowIdentifier=v.getId();
		TextView valueName=(TextView)findViewById(rowIdentifier);            	
		String fullName=valueName.getText().toString();
		valueName.setBackgroundResource(R.color.highlightcolor);
  		String[] flName=fullName.split(" ");
		Log.d("checkOnClick", fullName);
  		String first1Name,second1Name;
  		
  		if(flName.length==1)
  		{
  			first1Name=flName[0];
  			second1Name=" ";
  		}
  		else
  		{
  			first1Name=flName[0];
  			second1Name=flName[1];
  		}

		
		Intent intent = new Intent(this, ContactListActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
		Intent i=new Intent(this,EditView.class);
		
		i.putExtra("fName", first1Name);
		i.putExtra("lName", second1Name);
		i.putExtra("mode", "view");
		i.putExtra("saveMsg", "fail");
		startActivity(i);
		//this.finish();
		

	}


}
