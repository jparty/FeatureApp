package com.ecn.urbapp.syncToExt;


import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ecn.urbapp.db.LocalDataSource;
import com.ecn.urbapp.db.MainActivity;
import com.ecn.urbapp.db.MySQLiteHelper;
import com.ecn.urbapp.db.Project;
import com.ecn.urbapp.db.R;
import com.google.gson.GsonBuilder;
import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;


public class BackTaskExportAllDB extends AsyncTask<Void, Integer, Integer> {

	private static final String TAG_ProjectS = "Projects";
	private static final String TAG_ID = "Projects_id";
	private static final String TAG_DESCRIPTION = "Projects_description";
	
	protected Activity mContext;
	private ProgressBar mProgressBar;
	
	
	
	public BackTaskExportAllDB(Activity context){
		super();
		this.mContext = context;
		
	}
	
	
	protected void onPreExecute(){
		this.mProgressBar = (ProgressBar) mContext.findViewById(R.id.pBAsyncDBToExt);
		this.mProgressBar.setVisibility(View.VISIBLE);
		super.onPreExecute();
		Toast.makeText(mContext,  "D�but de l'exportation", Toast.LENGTH_SHORT).show();
	}
		
	protected Integer doInBackground(Void... params) { 
		LocalDataSource tampon = ((MainActivity)this.mContext).getDatasource();
		List<Project> allProjects = new ArrayList<Project>(tampon.getAllProjects());
		String name = MySQLiteHelper.getTableProject();
		Gson gson = new Gson();
		String dataJson = gson.toJson(allProjects);
		//String jSonComplete = "{\""+name+"\":"+dataJson+"}";
		String jSonComplete = dataJson;
		postData(jSonComplete);
				return null;
				
    }
 
	
    public void postData(String param) {
	    HttpClient httpclient = new DefaultHttpClient();
	    // specify the URL you want to post to
	    HttpPost httppost = new HttpPost("http://192.168.34.1/SQLite/");
	    try {
		    // create a list to store HTTP variables and their values
		    List nameValuePairs = new ArrayList();
		    // add an HTTP variable and value pair
		    nameValuePairs.add(new BasicNameValuePair("myHttpData", param));
		    httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		    // send the variable and value, in other words post, to the URL
		    HttpResponse response = httpclient.execute(httppost);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
          } catch (IOException e) {
            e.printStackTrace();
          } ;
    }
 
     //mise en place d'une progress bar : cf tuto : http://www.tutos-android.com/asynctask-android-traitement-asynchrone-background
    @Override
    protected void onProgressUpdate(Integer... values){
		this.mProgressBar.setProgress(values[0]);
		super.onProgressUpdate(values);
    }
     
    @Override
    protected void onPostExecute(Integer result) {
    	//this.mProgressBar.setVisibility(View.GONE);
    	
        Toast.makeText(mContext, "Le traitement asynchrone est termin�", Toast.LENGTH_SHORT).show();
        super.onPostExecute(result);
    }
    

}
