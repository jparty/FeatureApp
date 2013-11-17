package com.ecn.urbapp.activities;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import com.ecn.urbapp.R;
import com.ecn.urbapp.fragments.CharacteristicsFragment;
import com.ecn.urbapp.fragments.HomeFragment;
import com.ecn.urbapp.fragments.InformationFragment;
import com.ecn.urbapp.fragments.SaveFragment;
import com.ecn.urbapp.fragments.ZoneFragment;
import com.ecn.urbapp.listener.MyTabListener;

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
 * It contains an action bar filled with the differents fragments
 * 			
 */

public class MainActivity extends Activity {

	/**
	 * bar represent the action bar of the application
	 */
	ActionBar bar;
	
	/**
	 * baseContext to get the static context of app anywhere (for file)
	 */
    public static Context baseContext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Setting the Context of app
		baseContext = getBaseContext();
		
		//Setting the Activity bar
		bar = getActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		bar.setDisplayHomeAsUpEnabled(true);

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
		
		Intent i = getIntent();
		switch(i.getIntExtra("fragment", -1)){
		case 2:
			bar.selectTab(tabInformation);
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu, this adds items to the action bar if it is present.
		MenuInflater inflater =	getMenuInflater();
		inflater.inflate(R.menu.menu_main, menu);
		return super.onCreateOptionsMenu(menu);
	}

}
