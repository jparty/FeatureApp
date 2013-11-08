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
		
	    Intent i = getActivity().getIntent();
	    EditText txt = (EditText) v.findViewById(R.id.info_edit_adress);
	    txt.setText(i.getStringExtra("addr"));
		return v;
	}
		@Override
		public void onClick(View v) {
			Intent i = new Intent(this.getActivity(), GeoActivity.class);
			startActivity(i);
		}
}