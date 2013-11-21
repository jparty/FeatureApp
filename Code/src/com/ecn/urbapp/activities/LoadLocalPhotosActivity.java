package com.ecn.urbapp.activities;

import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.ecn.urbapp.R;
import com.ecn.urbapp.db.LocalDataSource;
import com.ecn.urbapp.db.Photo;
import com.ecn.urbapp.utils.ImageDownloader;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class LoadLocalPhotosActivity extends Activity{

	/**
	 * creating datasource
	 */
	private LocalDataSource datasource;
	/**
	 * Contains all the projects attributes
	 */
	private List<Photo> refreshedValues;
	
	/**
	 * Hashmap between unique id of markers and the relative project_id
	 */
	private HashMap<String, Integer> photosMarkers = new HashMap<String, Integer>();
	/**
	 * displayable photos list
	 */
	private ListView listePhotos = null;	
    /**
     * The google map object
     */
    private GoogleMap map = null;
    /**
     * The instance of GeoActivity for map activity
     */
    GeoActivity displayedMap;
    /**
     * The button for switching to satellite view
     */
    private Button satellite = null;
    
    /**
     * The button for switching to plan view
     */
    private Button plan = null;
    
    /**
     * The button for switching to hybrid view
     */
    private Button hybrid = null;
                                                                                                                                                                                                                     
    long project_id;
                                                                                                                      
    ImageView Photo;

    private String[] URLs={
			"http://static.tumblr.com/604c1f8526cf8f5511c6d7a5e32f9abd/u00yntv/2wEmlbf4d/tumblr_static_baby_otter.jpg",
			"http://axemdo.files.wordpress.com/2010/07/loutre1.jpg",                
			"http://www.spaycificzoo.com/wp-content/uploads/2011/11/loutre_naine1-300x232.jpg"}
    	;
     
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_loadlocalphotos);
        datasource=MainActivity.datasource;
        datasource.open();
        
        /**
         * extras that countains the project_id
         */
        
        project_id = getIntent().getExtras().getLong("SELECTED_PROJECT_ID");
        
        map = ((MapFragment) getFragmentManager()
                .findFragmentById(R.id.map)).getMap();
        
        displayedMap = new GeoActivity(true, GeoActivity.defaultPos, map);
        
        /**
         * Define the listeners for switch satellite/plan/hybrid
         */
        satellite = (Button)findViewById(R.id.satellite);
        plan = (Button)findViewById(R.id.plan);
        hybrid = (Button)findViewById(R.id.hybrid);
        
        satellite.setOnClickListener(displayedMap.toSatellite);
        plan.setOnClickListener(displayedMap.toPlan);
        hybrid.setOnClickListener(displayedMap.toHybrid);
        
       Photo = (ImageView) findViewById(R.id.loadLocalImage);
        
        listePhotos = (ListView) findViewById(R.id.listViewPhotos);
        refreshListPhoto();
        
        
        listePhotos.setOnItemClickListener(selectedPhoto);
        map.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
               Toast.makeText(MainActivity.baseContext, refreshedValues.get(photosMarkers.get(marker.getId())).toString(), Toast.LENGTH_LONG).show();
   				Intent i = new Intent(getApplicationContext(), Test.class);
   				startActivity(i);

            }
        });
    }
    
    protected void onClose() {      
        datasource.close();
    }
    
    
    /**
     * loading the different projects of the local db
     * @return
     */
   public List<com.ecn.urbapp.db.Photo> recupPhoto() {
        
        //List<com.ecn.urbapp.db.Photo> values = this.datasource.getAllPhotos();
	   
	   //TODO CATCH EXCEPTION
        
            
            List<com.ecn.urbapp.db.Photo> values = this.datasource.getAllPhotolinkedtoProject(project_id);
            return values;

         
    }
    
    /**
     * creating a list of project and loads in the view
     */

    
    public void refreshListPhoto(){      
    	
    	refreshedValues = recupPhoto();
    	
        ArrayAdapter<com.ecn.urbapp.db.Photo> adapterPhoto = new ArrayAdapter<com.ecn.urbapp.db.Photo>(this, android.R.layout.simple_expandable_list_item_1,refreshedValues);
        listePhotos.setAdapter(adapterPhoto);  
        
        /**
         * Load Pictures
         */ 
        ImageDownloader imageDownloader = new ImageDownloader();
    	imageDownloader.download(URLs[(int) (Math.random()*3)], Photo, "img"+((int)(Math.random()*3+1))+".png");
        
        /**
         * Put markers on the map
         */
       Integer i = new Integer(0);
        for (Photo enCours:refreshedValues){
        	String[] coord = enCours.getExt_GpsGeomCoord().split("//");
			LatLng coordPhoto = new LatLng(Double.parseDouble(coord[0]), Double.parseDouble(coord[1]));
        	Marker marker = map.addMarker(new MarkerOptions()
            .position(coordPhoto)
            .title("Cliquez ici pour valider cette photo"));
            
        	photosMarkers.put(marker.getId(), i);
        	i++;
        }
    }    
   
    /**
     *  get the project selected in listview and show its position on the map 
     */
    
    public OnItemClickListener selectedPhoto = new OnItemClickListener()
    {
		@Override
		public void onItemClick(AdapterView<?> arg0, View v, int position,
				long id) {
			
			String[] coord = refreshedValues.get(position).getExt_GpsGeomCoord().split("//");
			LatLng coordPhoto = new LatLng(Double.parseDouble(coord[0]), Double.parseDouble(coord[1]));
			displayedMap = new GeoActivity(false, coordPhoto, map);
    		Toast.makeText(getApplicationContext(), coordPhoto.toString(), Toast.LENGTH_LONG).show();                  
		}
    };
}
