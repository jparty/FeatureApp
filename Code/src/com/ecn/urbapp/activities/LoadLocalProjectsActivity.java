package com.ecn.urbapp.activities;

import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.ecn.urbapp.R;
import com.ecn.urbapp.db.LocalDataSource;
import com.ecn.urbapp.db.Project;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

public class LoadLocalProjectsActivity extends Activity {

	/**
	 * creating datasource
	 */
	private LocalDataSource datasource;
	/**
	 * Contains all the projects attributes
	 */
	private List<Project> refreshedValues;
	/**
	 * displayable project list
	 */
	private ListView listeProjects = null;	
    /**
     * The google map object
     */
    private GoogleMap map = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_loadlocaldb);
        datasource=MainActivity.datasource;
        datasource.open();
        
        map = ((MapFragment) getFragmentManager()
                .findFragmentById(R.id.map)).getMap();
        
        listeProjects = (ListView) findViewById(R.id.listView);
        refreshList();
        listeProjects.setOnItemClickListener(selectedProject);
    }
    
    protected void onClose() {      
        datasource.close();
    }
    
    
    /**
     * loading the different projects of the local db
     * @return
     */
    public List<Project> recupProject() {
         
         List<Project> values = this.datasource.getAllProjects();
         
         return values;
          
     }
    
    /**
     * creating a list of project and loads in the view
     */
    public void refreshList(){      
    	
    	refreshedValues = recupProject();
    	
        ArrayAdapter<Project> adapter = new ArrayAdapter<Project>(this, android.R.layout.simple_list_item_1, refreshedValues);
        listeProjects.setAdapter(adapter);
        
   }
    /**
     *  get the project selected in listview and show its position on the map 
     */
    
    public OnItemClickListener selectedProject = new OnItemClickListener()
    {
		@Override
		public void onItemClick(AdapterView<?> arg0, View v, int position,
				long id) {
			String[] coord = refreshedValues.get(position).getExt_GpsGeomCoord().split("//");
			LatLng coordProjet = new LatLng(Double.parseDouble(coord[0]), Double.parseDouble(coord[1]));
			GeoActivity displayedMap = new GeoActivity(false, coordProjet, map);
    		Toast.makeText(getApplicationContext(), coordProjet.toString(), Toast.LENGTH_LONG).show();                  
			
		}
    };
}
