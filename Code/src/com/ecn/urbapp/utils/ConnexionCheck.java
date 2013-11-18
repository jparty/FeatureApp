package com.ecn.urbapp.utils;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import com.ecn.urbapp.activities.MainActivity;
import com.ecn.urbapp.utils.ImageDownloader.BitmapDownloaderTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

public class ConnexionCheck {

	private static String url=MainActivity.CONNECTIVITY_URL;

	public void Connectivity(){
		ConnectivityManager con=(ConnectivityManager)MainActivity.baseContext.getSystemService(Activity.CONNECTIVITY_SERVICE);
		boolean wifi=con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
		boolean mobile = false;

		try {
			mobile=con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
		}
		catch (NullPointerException e){
			mobile=false;
		}
		boolean internet=wifi|mobile;
		if (internet){
			ConnectivityRequestTask task = new ConnectivityRequestTask(url);
			task.execute(url);
		}

	}
}
class ConnectivityRequestTask extends AsyncTask<String, Void, Boolean> {

	private String url;

	public ConnectivityRequestTask(String url){
		this.url = url;
	}

	@Override
	// Actual download method, run in the task thread
	protected Boolean doInBackground(String... params) {
		HttpURLConnection urlConnection = null;
		try{
			URL url = new URL(MainActivity.CONNECTIVITY_URL); // "http://clients3.google.com/generate_204"
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setInstanceFollowRedirects(false);
			urlConnection.setConnectTimeout(3000);
			urlConnection.setReadTimeout(5000);
			urlConnection.setUseCaches(false);
			urlConnection.getInputStream();
			// We got a valid response, but not from the real google
			Boolean dindon = urlConnection.getResponseCode() == 204;
			return urlConnection.getResponseCode() == 204;
		} catch (IOException e) {

				Log.e("Connect","Walled garden check - probably not a portal: exception "
						+ e);
			return false;
		} finally {
			if (urlConnection != null) {
				urlConnection.disconnect();
			}
		}
	}


	protected void onPostExecute(Boolean isConnect) {
		if(!isConnect){
			MainActivity.errorConnect();
		}
	}
}
