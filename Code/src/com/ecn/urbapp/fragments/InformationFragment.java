package com.ecn.urbapp.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.ecn.urbapp.R;
import com.ecn.urbapp.activities.GeoActivity;
import com.ecn.urbapp.activities.MainActivity;

/**
 * @author	COHENDET Sébastien
 * 			DAVID Nicolas
 * 			GUILBART Gabriel
 * 			PALOMINOS Sylvain
 * 			PARTY Jules
 * 			RAMBEAU Merwan
 * 
 * InformationFragment class
 * 
 * This is the fragment used to define the informations about the project.
 * 			
 */

public class InformationFragment extends Fragment implements OnClickListener{

	private Button geo = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.layout_information, null);

		geo = (Button) v.findViewById(R.id.info_button_geo);
	    geo.setOnClickListener(this);
		
	    /*Intent i = getActivity().getIntent();
	    EditText txt = (EditText) v.findViewById(R.id.info_edit_adress);
	    txt.setText(i.getStringExtra("addr"));*/
		return v;
	}
		@Override
		public void onClick(View v) {
			Intent i = new Intent(this.getActivity(), GeoActivity.class);
			startActivity(i);
		}

		@Override
		public void onStop(){
			super.onStop();
		    EditText txt = (EditText) getView().findViewById(R.id.info_edit_author);
		    MainActivity.author = txt.getText().toString();
		    txt = (EditText) getView().findViewById(R.id.info_edit_deviceName);
		    MainActivity.device = txt.getText().toString();
		    txt = (EditText) getView().findViewById(R.id.info_edit_project);
		    MainActivity.project = txt.getText().toString();
		    txt = (EditText) getView().findViewById(R.id.info_edit_adress);
		    MainActivity.address = txt.getText().toString();
		    
		}

		@Override
		public void onStart(){
			super.onStop();
		    EditText txt = (EditText) getView().findViewById(R.id.info_edit_author);
		    txt.setText(MainActivity.author);
		    txt = (EditText) getView().findViewById(R.id.info_edit_deviceName);
		    txt.setText(MainActivity.device);
		    txt = (EditText) getView().findViewById(R.id.info_edit_project);
		    txt.setText(MainActivity.project);
		    txt = (EditText) getView().findViewById(R.id.info_edit_adress);
		    txt.setText(MainActivity.address);
		    
		}
}