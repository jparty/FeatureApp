package com.ecn.urbapp.activities;

import java.io.File;
import java.util.Vector;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.ecn.urbapp.R;
import com.ecn.urbapp.db.LocalDataSource;
import com.ecn.urbapp.fragments.CharacteristicsFragment;
import com.ecn.urbapp.fragments.HomeFragment;
import com.ecn.urbapp.fragments.InformationFragment;
import com.ecn.urbapp.fragments.SaveFragment;
import com.ecn.urbapp.fragments.ZoneFragment;
import com.ecn.urbapp.listener.MyTabListener;
import com.ecn.urbapp.utils.ConnexionCheck;
import com.ecn.urbapp.zones.BitmapLoader;
import com.ecn.urbapp.zones.DrawZoneView;
import com.ecn.urbapp.zones.Zone;

/**
 * @author	COHENDET Sébastien
 * 			DAVID Nicolas
 * 			GUILBART Gabriel
 * 			PALOMINOS Sylvain
 * 			PARTY Jules
 * 			RAMBEAU Merwan
 * 
 * MainActivity class
 * 
 * This is the main activity of the application.
 * It contains an action bar filled with the different fragments
 * 			
 */

public class MainActivity extends Activity {

	/**
	 * bar represent the action bar of the application
	 */
	ActionBar bar;
	public File photo;
	public Zone zoneToDelete ;
	
	/**
	 * attributs for the local database
	 */
	public static LocalDataSource datasource;

	
	/**

	 * baseContext to get the static context of app anywhere (for file)
	 */
    public static Context baseContext;

	private static Builder alertDialog;
    
    public static final String CONNECTIVITY_URL="http://clients3.google.com/generate_204";
    
    /**
	 * Attributs for the project information
	 */

	public static String author = "";
	public static String device = "";
	public static String project = "";
	public static String address = "";

	public static Vector<Zone> zones=null;
	public static ImageView myImage=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Setting the Context of app
		baseContext = getBaseContext();
		
		alertDialog = new AlertDialog.Builder(MainActivity.this);
		isInternetOn();
		
		//Setting the Activity bar
		bar = getActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		bar.setDisplayHomeAsUpEnabled(true);
		
		//Setting the Context of app
		baseContext = getBaseContext();
		
		//initialization of the local database
		datasource = new LocalDataSource(this);
		
		//Setting of the different tab of the bar
		
		//Home tab
		Tab tabHome =  bar.newTab();
		tabHome.setText(R.string.homeFragment);
		HomeFragment home = new HomeFragment();
		tabHome.setTabListener(new MyTabListener(home));
		bar.addTab(tabHome);
		
		//Information tab
		Tab tabInformation =  bar.newTab();
		tabInformation.setText(R.string.informationFragment);
		InformationFragment information = new InformationFragment();
		tabInformation.setTabListener((new MyTabListener(information)));
		bar.addTab(tabInformation);
		
		//Zone tab
		Tab tabZone =  bar.newTab();
		tabZone.setText(R.string.zoneFragment);
		ZoneFragment zone = new ZoneFragment();
		tabZone.setTabListener(new MyTabListener(zone));
		bar.addTab(tabZone);
		
		//Definition tab
		Tab tabDefinition =  bar.newTab();
		tabDefinition.setText(R.string.definitionFragment);
		CharacteristicsFragment definition = new CharacteristicsFragment();
		tabDefinition.setTabListener(new MyTabListener(definition));
		bar.addTab(tabDefinition);
		
		//Save tab
		Tab tabSave =  bar.newTab();
		tabSave.setText(R.string.saveFragment);
		SaveFragment save = new SaveFragment();
		tabSave.setTabListener(new MyTabListener(save));
		bar.addTab(tabSave);
		
		//create zones' list for new image
		zones = new Vector<Zone>();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu, this adds items to the action bar if it is present.
		MenuInflater inflater =	getMenuInflater();
		inflater.inflate(R.menu.menu_main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	/**
	 * Method to check if internet is available (and no portal !)
	 */
	public final void isInternetOn() {

		ConnectivityManager con=(ConnectivityManager)getSystemService(Activity.CONNECTIVITY_SERVICE);
        boolean wifi=con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        boolean mobile = false;
        
        try {
         mobile=con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        }
        catch (NullPointerException e){
        	mobile=false;
        }
        boolean internet=wifi|mobile;
        if (internet)
        	 new ConnexionCheck().Connectivity();
	}

	/**
	 * Method if no internet connectivity to print a Dialog.
	 */
	public static void errorConnect() {
		alertDialog.setTitle("Pas de connexion internet de disponible. Relancer l'application, une fois internet fonctionnel");
		alertDialog.show();		
	}
	
}
