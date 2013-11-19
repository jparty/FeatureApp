package com.ecn.urbapp.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.ecn.urbapp.R;
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
	
	private Button downloadImage;
	private ImageView image;
	
	private String[] URLs={
			"http://static.tumblr.com/604c1f8526cf8f5511c6d7a5e32f9abd/u00yntv/2wEmlbf4d/tumblr_static_baby_otter.jpg",
			"http://axemdo.files.wordpress.com/2010/07/loutre1.jpg",
			"http://www.spaycificzoo.com/wp-content/uploads/2011/11/loutre_naine1-300x232.jpg"
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.layout_home, null);

		loadLocal=(Button)v.findViewById(R.id.home_loadLocalProject);
		loadLocal.setOnClickListener(this);
		test=(Button)v.findViewById(R.id.home_test);
		test.setOnClickListener(this);
		
		return v;
		
	}
	
	@Override
	public void onClick(View v) {
		Intent i = null;
		int id = v.getId();
		if (id == R.id.home_loadLocalProject) {
			i = new Intent(this.getActivity(), LoadLocalProjectsActivity.class);
			startActivity(i);
		} else if (id == R.id.home_test) {
			i = new Intent(this.getActivity(), Test.class);
			startActivity(i);
		} else {
		}
	}
}