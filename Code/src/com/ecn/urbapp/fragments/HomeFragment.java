package com.ecn.urbapp.fragments;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.ecn.urbapp.R;
import com.ecn.urbapp.activities.GeoActivity;
import com.ecn.urbapp.activities.LoadLocalProjectsActivity;
import com.ecn.urbapp.activities.Test;

/**
 * This is the fragment used to make the user choose between the differents type of project.
 * 
 * @author	COHENDET Sébastien
 * 			DAVID Nicolas
 * 			GUILBART Gabriel
 * 			PALOMINOS Sylvain
 * 			PARTY Jules
 * 			RAMBEAU Merwan
 * 			
 */

public class HomeFragment extends Fragment implements OnClickListener{
	
	/**
	 * creating the interface
	 */
	private Button loadLocal = null ;
	private Button test = null ;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.layout_home, null);
		loadLocal=(Button)v.findViewById(R.id.home_loadLocalProject);
		loadLocal.setOnClickListener(this);
		loadLocal=(Button)v.findViewById(R.id.home_test);
		loadLocal.setOnClickListener(this);
		
		return v;
		
	}
	
	@Override
	public void onClick(View v) {
		Intent i = null;
		switch (v.getId()) {
		case R.id.home_loadLocalProject:
			i = new Intent(this.getActivity(), LoadLocalProjectsActivity.class);
			startActivity(i);			
			break;
			
		case R.id.home_test:
			i = new Intent(this.getActivity(), Test.class);
			startActivity(i);			
			break;

		default:
			break;
		}
	}

}