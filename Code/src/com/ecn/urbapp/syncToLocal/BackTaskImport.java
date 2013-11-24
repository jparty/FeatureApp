package com.ecn.urbapp.syncToLocal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ecn.urbapp.R;
import com.ecn.urbapp.db.LocalDataSource;
import com.ecn.urbapp.db.MainActivity;



public class BackTaskImport extends AsyncTask<String, Integer, Integer>{

	//TODO Adddescription for javadoc
	private static final String TAG_COMMENTS = "comments";
	private static final String TAG_ID = "comments_id";
	private static final String TAG_DESCRIPTION = "comments_description";
	
	private Activity mContext;
	private ProgressBar mProgressBar;

	//TODO Adddescription for javadoc
	public BackTaskImport(Activity context){
		super();
		this.mContext = context;
	}

	//TODO Adddescription for javadoc
	@Override
	protected void onPreExecute(){
		this.mProgressBar = (ProgressBar) mContext.findViewById(R.id.pBAsync);
		this.mProgressBar.setVisibility(View.VISIBLE);
		super.onPreExecute();
		Toast.makeText(mContext,  "D�but du traitement asynchrone", Toast.LENGTH_SHORT).show();
	}

	//TODO Adddescription for javadoc
	protected Integer doInBackground(String... params) { 
		// Here we do useless work, you have to put here what you have to do
		/*		for (int i = 0; i< 100; i++){
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		 
					int progress = (i);
					Log.i("test", " progress : " + progress);
					// Cette fonction appelle la fonction onProgressUpdate()
					// This function call onProgressUpdate()
					publishProgress(progress);
				}*/
		
		String commentToParse = readCommentFeed();
		JSONObject jsonComment = parseComment(commentToParse);
		Log.w("Debug","parsing done");
		Boolean result  = recComment(jsonComment);
		if (result) {
			int progress = 100;
			publishProgress(progress);
			Log.w("Debug","parsing done");
		};
		return null;
    }
 

	//TODO Adddescription for javadoc
     //mise en place d'une progress bar : cf tuto : http://www.tutos-android.com/asynctask-android-traitement-asynchrone-background
    @Override
    protected void onProgressUpdate(Integer... values){
		this.mProgressBar.setProgress(values[0]);
		super.onProgressUpdate(values);
    }

	//TODO Adddescription for javadoc
    @Override
    protected void onPostExecute(Integer result) {
    	//this.mProgressBar.setVisibility(View.GONE);
    	((MainActivity) mContext).refreshList();
        Toast.makeText(mContext, "Le traitement asynchrone est termin�", Toast.LENGTH_SHORT).show();
        super.onPostExecute(result);
    }

	//TODO Adddescription for javadoc
    //connexion vers l'exterieur
    public String readCommentFeed() {
		StringBuilder builder = new StringBuilder();
		HttpClient client = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet("http://192.168.34.1/SQLite/");
		try {
			HttpResponse response = client.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode == 200) {
				HttpEntity entity = response.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(content));
				String line;
				while ((line = reader.readLine()) != null) {
					builder.append(line + "\n");
				}
			} else {
				Log.e(MainActivity.class.toString(), "Failed to download file");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return builder.toString();
    }
    

	//TODO Adddescription for javadoc
    public JSONObject parseComment(String commentToParse){
        try {
    	JSONObject jObj = new JSONObject(commentToParse); 
    	return jObj; //TO DO : is that OK ? or must we use an "if" condition at the end of the method ? 
    	
        } catch (JSONException e) {
           Log.e("JSON Parser", "Error parsing data " + e.toString());
           return (JSONObject) null;
        }  
	}

	//TODO Adddescription for javadoc
    public Boolean recComment(JSONObject jsonComment) {
        LocalDataSource datasource = new LocalDataSource(mContext);
        datasource.open();
         
        try {
            JSONArray projectsList = jsonComment.getJSONArray(TAG_COMMENTS);
              
            for(int i = 0; i < projectsList.length(); i++){
                JSONObject c = projectsList.getJSONObject(i);
                Long id = c.getLong(TAG_ID);
                String descr = c.getString(TAG_DESCRIPTION);
                datasource.createProject(id, descr);
            }
            datasource.close();
            return true;
             
        } catch (JSONException e) {
            e.printStackTrace();
            datasource.close();
            return false;
        }
	}


}
