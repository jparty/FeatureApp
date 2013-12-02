package com.ecn.urbapp.syncToExt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ecn.urbapp.R;
import com.ecn.urbapp.activities.MainActivity;
import com.ecn.urbapp.db.LocalDataSource;
import com.ecn.urbapp.db.Project;
import com.google.gson.Gson;

public class Sync
{
	public static HashMap<String, Integer> maxId;

	/**
	 * Launch the sync to external DB
	 * @return Boolean if success of not
	 */
	public Boolean doSync()
	{
		Boolean success = false;
		
			try
			{
				new BackTaskExportToExt().execute();
				success = true;
			}
			catch (Exception e)
			{
			}
		
		return success;
	}
	
	
	/**
	 * Get the max id of each critical tables in external DB
	 * @return Hashmap of all max id
	 */
	public HashMap<String, Integer> getMaxId() {
		//TODO get all the id of each table in external DB
		return maxId;
	}

	
	
	/**
	 * The additional threat to upload data to the server
	 * @author Sebastien
	 *
	 */
	public class BackTaskExportToExt extends AsyncTask<Void, String, String> {
		
		private Context mContext;
		
		/**
		 * Constructor
		 * @param json
		 */
		public BackTaskExportToExt(){
			super();			
			this.mContext = MainActivity.baseContext;
		}

		/**
		 * Pre Execution orders
		 */
		protected void onPreExecute(){
			
			super.onPreExecute();
			Toast.makeText(MainActivity.baseContext,  "Début de l'exportation", Toast.LENGTH_SHORT).show();
		}

		/**
		 * The transformation in json and the sending to server
		 */
		protected String doInBackground(Void... params) { 
			Gson gson = new Gson();
			String dataJson = gson.toJson(MainActivity.gpsGeom);
			String jSonComplete = "[{\"gpsGeom\":"+dataJson+"},";

			dataJson = gson.toJson(MainActivity.pixelGeom);
			jSonComplete += "{\"pixelGeom\":"+dataJson+"},";

			dataJson = gson.toJson(MainActivity.photo);
			jSonComplete += "{\"photo\":"+dataJson+"},";

			dataJson = gson.toJson(MainActivity.project);
			jSonComplete += "{\"project\":"+dataJson+"},";

			dataJson = gson.toJson(MainActivity.composed);
			jSonComplete += "{\"composed\":"+dataJson+"},";

			dataJson = gson.toJson(MainActivity.element);
			jSonComplete += "{\"element\":"+dataJson+"}]";

			return postData(jSonComplete);
		}
	 

		/**
		 * The posting method to server
		 * @param param the json data to send
		 * @return the string of the server response
		 */
	    public String postData(String param) {
		    HttpClient httpclient = new DefaultHttpClient();
		    // specify the URL you want to post to
		    HttpPost httppost = new HttpPost(MainActivity.serverURL+"registerDB.php");
		    try {
			    // create a list to store HTTP variables and their values
			    List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
			    // add an HTTP variable and value pair
			    nameValuePairs.add(new BasicNameValuePair("myHttpData", param));
			    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			    // send the variable and value, in other words post, to the URL
			    HttpResponse response = httpclient.execute(httppost);
			    
			    StringBuilder sb = new StringBuilder();
			    try {
			    	BufferedReader reader = 
			    			new BufferedReader(new InputStreamReader(response.getEntity().getContent()), 65728);
			    	String line = null;

			    	while ((line = reader.readLine()) != null) {
			    		sb.append(line);
			    	}
			    }
			    catch (IOException e) { e.printStackTrace(); }
			    catch (Exception e) { e.printStackTrace(); }
			    
			    return sb.toString();
				
	        } catch (ClientProtocolException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        } ;
	        return null;
	    }

		
		/**
		 * The things to execute after the backTask 
		 */
	    protected void onPostExecute(String result) {	
	    	if (result.equals("OK")){
	    		Toast.makeText(mContext, "Synchronisation avec la base : SUCCES", Toast.LENGTH_SHORT).show();
	    	}
	    	else {
		        Toast.makeText(mContext, result, Toast.LENGTH_SHORT).show();
	    	}
	    }
	    

	}

}