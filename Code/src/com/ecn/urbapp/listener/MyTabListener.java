package com.ecn.urbapp.listener;

import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.widget.Toast;

import com.ecn.urbapp.activities.MainActivity;
import com.ecn.urbapp.fragments.HomeFragment;

/**
 * @author	COHENDET S�bastien
 * 			DAVID Nicolas
 * 			GUILBART Gabriel
 * 			PALOMINOS Sylvain
 * 			PARTY Jules
 * 			RAMBEAU Merwan
 * 
 * MyTabListener class
 * 
 * This class is used to define the different actions for the tab of the ActionBar.
 * 			
 */

public class MyTabListener implements TabListener{

	/**
	 * It's the fragment concerned by the listener
	 */
	private Fragment f;

	//TODO Adddescription for javadoc
	private Activity a;
	
	/**
	 * Main constructor
	 * @param f is the fragment concerned by the listener
	 */
	public MyTabListener(Fragment f, Activity a){
		this.f=f;
		this.a=a;
	}

	//TODO Adddescription for javadoc
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
	}

	//TODO Adddescription for javadoc
	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {

        
        if(f.getClass()==HomeFragment.class && MainActivity.photo.getPhoto_url()!=null){
        	MainActivity.pathTampon=MainActivity.photo.getPhoto_url();
        	MainActivity.photo.setPhoto_url(null);
        	MainActivity.start=true;
        }
        else if(MainActivity.photo.getPhoto_url()==null){
        	MainActivity.photo.setPhoto_url(MainActivity.pathTampon);
        	MainActivity.photo.setGpsGeom_id(1); //TODO delete
        }
        
        
		if (MainActivity.photo.getPhoto_url() != null || MainActivity.start /*|| f.getClass()==HomeFragment.class*/){
			ft.replace(android.R.id.content, f);
			MainActivity.start=false;
		}
		else if(f.getClass()!=HomeFragment.class){
			Context context = a.getApplicationContext();
			CharSequence text = "Veuillez charger une image ou un projet avant de commencer à travailler";
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
			a.getActionBar().setSelectedNavigationItem(1);
		}
	}

	//TODO Adddescription for javadoc
	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
	}
	
}