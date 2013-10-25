package com.ecn.urbapp.listener;

import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Fragment;
import android.app.FragmentTransaction;

/**
 * @author	COHENDET Sébastien
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
	
	/**
	 * Main constructor
	 * @param f is the fragment concerned by the listener
	 */
	public MyTabListener(Fragment f){
		this.f=f;
	}
	
	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
	    ft.replace(android.R.id.content, f);
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
	}
	
}