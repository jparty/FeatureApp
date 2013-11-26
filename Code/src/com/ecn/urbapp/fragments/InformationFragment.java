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
import com.ecn.urbapp.db.Composed;
import com.ecn.urbapp.db.Project;

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

		/**
		 * Function called when the fragment stop (i.e. when an other fragment is selected).
		 */
		@Override
		public void onStop(){
			super.onStop();
			//TODO supress the following code
			/*
		    EditText txt = (EditText) getView().findViewById(R.id.info_edit_author);
		    MainActivity.author = txt.getText().toString();
		    txt = (EditText) getView().findViewById(R.id.info_edit_deviceName);
		    MainActivity.device = txt.getText().toString();
		    txt = (EditText) getView().findViewById(R.id.info_edit_project);
		    MainActivity.sproject = txt.getText().toString();
		    txt = (EditText) getView().findViewById(R.id.info_edit_adress);
		    MainActivity.address = txt.getText().toString();*/
		    
		    //TODO verificate the case of multi project (cf maybe get the project selected in the list)
		    //need to verificate if the project is already defined or not
		    if(MainActivity.projectSet){
		    	//Obtaining teh last define project
		    	if(MainActivity.project.size()>0){
			    	Project pro = MainActivity.project.get(MainActivity.project.size()-1);
			    	EditText txt = (EditText) getView().findViewById(R.id.info_edit_project);
				    pro.setProjectName(txt.getText().toString());
				    txt = (EditText) getView().findViewById(R.id.info_edit_description);
				    MainActivity.photo.setPhoto_description(txt.getText().toString());
		    	}
		    }
		    else{
			    Project pro = new Project();
			    Composed comp= new Composed();
			    EditText txt = (EditText) getView().findViewById(R.id.info_edit_project);
			    pro.setProjectName(txt.getText().toString());
			    pro.setProjectId(MainActivity.project.size()+1);
			    pro.setGpsGeom_id(1);//TODO DELETE
			    txt = (EditText) getView().findViewById(R.id.info_edit_description);
			    MainActivity.photo.setPhoto_description(txt.getText().toString());
			    MainActivity.project.add(pro);
			    MainActivity.projectSet=true;
			    comp.setPhoto_id(MainActivity.photo.getPhoto_id());
			    comp.setProject_id(pro.getProjectId());
			    MainActivity.composed.add(comp);
			    
			    txt = (EditText) getView().findViewById(R.id.info_edit_author);
			    MainActivity.photo.setPhoto_author(txt.getText().toString());
		    }
		}

		/**
		 * Function called when the fragment start.
		 */
		@Override
		public void onStart(){
			super.onStart();
			//TODO supress the following code
			/*
		    EditText txt = (EditText) getView().findViewById(R.id.info_edit_author);
		    txt.setText(MainActivity.author);
		    txt = (EditText) getView().findViewById(R.id.info_edit_deviceName);
		    txt.setText(MainActivity.device);
		    txt = (EditText) getView().findViewById(R.id.info_edit_project);
		    txt.setText(MainActivity.sproject);
		    txt = (EditText) getView().findViewById(R.id.info_edit_adress);
		    txt.setText(MainActivity.address);*/
		    
		    //if the project is already set
		    if(MainActivity.projectSet){
		    	EditText txt = (EditText) getView().findViewById(R.id.info_edit_author);
			    txt.setText(MainActivity.photo.getPhoto_author());
			    txt = (EditText) getView().findViewById(R.id.info_edit_project);
			    txt.setText(MainActivity.project.get(MainActivity.project.size()-1).getProjectName());
			    txt = (EditText) getView().findViewById(R.id.info_edit_description);
			    txt.setText(MainActivity.photo.getPhoto_description());
			    txt = (EditText) getView().findViewById(R.id.info_edit_adress);
			    txt.setText("");
		    }
		    else{
		    	EditText txt = (EditText) getView().findViewById(R.id.info_edit_author);
			    txt.setText("");
			    txt = (EditText) getView().findViewById(R.id.info_edit_project);
			    txt.setText("");
			    txt = (EditText) getView().findViewById(R.id.info_edit_description);
			    txt.setText("");
			    txt = (EditText) getView().findViewById(R.id.info_edit_adress);
			    txt.setText("");
		    }
		}
}