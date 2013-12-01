package com.ecn.urbapp.syncToExt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.ecn.urbapp.activities.MainActivity;
import com.google.gson.Gson;

public class Sync
{
	public Activity context;
	public String JSON="";
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
				Gson gson = new Gson();
				String dataJson = gson.toJson(MainActivity.gpsGeom);
				String jSonComplete = "{\"gpsGeom\":"+dataJson+"}";
				
				dataJson = gson.toJson(MainActivity.pixelGeom);
				jSonComplete += "{\"pixelGeom\":"+dataJson+"}";
				
				dataJson = gson.toJson(MainActivity.photo);
				jSonComplete += "{\"photo\":"+dataJson+"}";

				dataJson = gson.toJson(MainActivity.project);
				jSonComplete += "{\"project\":"+dataJson+"}";
				
				dataJson = gson.toJson(MainActivity.composed);
				jSonComplete += "{\"composed\":"+dataJson+"}";
				
				dataJson = gson.toJson(MainActivity.element);
				jSonComplete += "{\"element\":"+dataJson+"}";

				Toast.makeText(MainActivity.baseContext, jSonComplete, Toast.LENGTH_LONG).show();
				
				postData(jSonComplete);
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
	/*
	public String saveProjectListToExt(ArrayList<Project> l1){
		String data="";
		for (Project p : l1){
			data += p.saveToExt();
		}
		return data;
	}
	
	public String saveElementListToExt(ArrayList<Element> l1){
		String data="";
		for (Element p : l1){
			data += p.saveToExt();
		}
		return data;
	}
	
	public String saveGpsGeomListToExt(ArrayList<GpsGeom> l1){
		String data="";
		for (GpsGeom p : l1){
			data += p.saveToExt();
		}
		return data;
	}
	
	public String savePixelGeomListToExt(ArrayList<PixelGeom> l1){
		String data="";
		for (PixelGeom p : l1){
			data += p.saveToExt();
		}
		return data;
	}
	
	public String saveElementTypeListToExt(ArrayList<ElementType> l1){
		String data="";
		for (ElementType p : l1){
			data += p.saveToExt();
		}
		return data;
	}
	
	public String saveMaterialListToExt(ArrayList<Material> l1){
		String data="";
		for (Material p : l1){
			data += p.saveToExt();
		}
		return data;
	}
	
	public String saveComposedListToExt(ArrayList<Composed> l1){
		String data="";
		for (Composed p : l1){
			data += p.saveToExt();
		}
		return data;
	}
	*/
	public void postData(String param) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("http://192.168.177.1/registerDB.php");
		try {
			// create a list to store HTTP variables and their values
			List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
			// add an HTTP variable and value pair
			nameValuePairs.add(new BasicNameValuePair("myHttpData", param));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			// send the variable and value, in other words post, to the URL
			httpclient.execute(httppost);
		} catch (ClientProtocolException e) {
			Log.e("DFHUPLOAD", "gne");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			Log.e("DFHUPLOAD", "boum");
		} ;
	}
}
