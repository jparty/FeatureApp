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
	Fragment f;
	
	Activity a;
	
	/**
	 * Main constructor
	 * @param f is the fragment concerned by the listener
	 */
	public MyTabListener(Fragment f, Activity a){
		this.f=f;
		this.a=a;
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {

        
        if(f.getClass()==HomeFragment.class && MainActivity.pathImage!=null){
        	MainActivity.pathTampon=MainActivity.pathImage;
        	MainActivity.pathImage=null;
        	MainActivity.start=true;
        }
        else if(MainActivity.pathImage==null){
        	MainActivity.pathImage=MainActivity.pathTampon;
        }
        
        
		if (MainActivity.pathImage != null || MainActivity.start /*|| f.getClass()==HomeFragment.class*/){
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

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
	}
	
}