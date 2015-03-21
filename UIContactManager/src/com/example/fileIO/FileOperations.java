package com.example.fileIO;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.TreeMap;
import com.example.service.Constants;
import android.os.Environment;
import android.util.Log;


public class FileOperations {
   public FileOperations() {
      }

   /*
    * Code Written By  : Rakesh Manoharan
    * Net ID 			: rxm143130
    * Course 			: CS6301
    * Function Name		: public Boolean write(TreeMap<String,String> userData)
    * Description 		:
    * 						FileOperations is the class used to do all the functions related
    * to the file. All the user information are stored on the text file. Both read and write
    * function are written on this class.
    * 
    * Write function is used to write the data onto the text file. Input to this function is
    * TreeMap with key, value pairs. Key contains the concatenated value of firstname and 
    * lastname of the user. One advantage of using the TreeMap is it disallows the duplicate key 
    * and also it orders the value is ascending order.
    */

   
   public Boolean write(TreeMap<String,String> userData){
      try {
    	  
        String fpath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath()+"/MyContactManagerRakesh.txt";
        
        // If file does not exists, then create it

        File file = new File(fpath);
        
        if (!file.exists()) {
        	file.createNewFile();
		}
        FileWriter fileWriter = null;
		fileWriter = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        for(Map.Entry<String, String> Iterator:userData.entrySet())
        {
        	if(Iterator.getKey()!=null)
        	{
        		bufferedWriter.write(Iterator.getValue());
           	}
		
        }
		bufferedWriter.close();
	return true;

        
        
      } catch (IOException e) {
        e.printStackTrace();
        return false;
      }
   }

   
   /*
    * Code Written By   : Rakesh Manoharan
    * Net ID 			: rxm143130
    * Course 			: CS6301
    * Function Name		: public TreeMap<String,String> read()
    * Description 		:
    *				Read function is used to read all the contact details stored on the 
    * text file. It reads the data from the text file and stores it on the TreeMap.
    * Input to this function is nothing, whereas this function has to return the treemap
    * that is constructed from reading the text file.    
    */

   
   
   public TreeMap<String,String> read(){
	     BufferedReader br = null;
	     try {
	        String fpath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath()+"/MyContactManagerRakesh.txt";
	        
	        File file = new File(fpath);
	        if (!file.exists()) {
	    		file.createNewFile();
	    	}    
	      FileInputStream in = new FileInputStream(file.getAbsoluteFile());
	      br = new BufferedReader(new InputStreamReader(in));
	      String strLine;
	      Map<String,String> allUserData=new TreeMap<String,String>();
/*	      while((strLine = br.readLine())!= null)
	      {
	    	  
	       allUserData.put(strLine.substring(0, 41), strLine);
	      }*/
	      strLine=br.readLine();
	      if(strLine!=null)
	      {
	    	  int startpos=0;
	    	  int contactLength=Constants.fNameLength+Constants.lNameLength+Constants.pNoLength+Constants.eMailLength;
	    	  int newPos=contactLength;
	    	  int nonPrimaryKeyLength=Constants.pNoLength+Constants.eMailLength;
	    	  while(newPos<=strLine.length())
	    	  {
	    		  Log.d("key", strLine.substring(startpos, newPos-nonPrimaryKeyLength));
	    		  Log.d("value", strLine.substring(startpos, newPos));
	    		String len=Integer.toString(strLine.length());
	    		  Log.d("String Length",len);
	    		  allUserData.put(strLine.substring(startpos, newPos-nonPrimaryKeyLength), strLine.substring(startpos, newPos));
	    		  startpos=newPos;
	    		  newPos=newPos+contactLength;
	    		  
	    	  }
	      }
	      br.close();
	    return (TreeMap<String, String>) allUserData;
	     
	        
	      } catch (IOException e) {
	        e.printStackTrace();
	        return null;
	      }
	      
	   }
}