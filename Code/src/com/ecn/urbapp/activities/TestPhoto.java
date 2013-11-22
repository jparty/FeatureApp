package com.ecn.urbapp.activities;

import java.util.List;
import java.util.Random;

import android.app.ListActivity;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.ecn.urbapp.R;
import com.ecn.urbapp.db.*;

public class TestPhoto extends ListActivity {

	//creating datasource
	private LocalDataSource datasource;
	
	//creating buttons for the TEST

	Button addPhoto = null;
	Button deletePhoto = null;
	Button createLink = null;
	
	/**
	 * attributs EditText that countain the project id and the photo id that will appear in the Object Composed
	 */
	EditText projet_to_link_id = null;
	EditText photo_to_link_id = null;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_testphoto);
        
        datasource=MainActivity.datasource;
        datasource.open();
        refreshListPhoto();
        
        //TODO delete test methods

        addPhoto = (Button)findViewById(R.id.addPhoto);
        deletePhoto = (Button)findViewById(R.id.deletePhoto);
        createLink = (Button)findViewById(R.id.test_photo_create_link);
        
        projet_to_link_id = (EditText)findViewById(R.id.test_photo_project_idtolink);
        photo_to_link_id = (EditText)findViewById(R.id.test_photo_photo_idtolink);
        
        /**
         * define listeners
         */
        //OnClick listeners for Buttons
        addPhoto.setOnClickListener(clickListenerBoutonsAddPhoto);
        deletePhoto.setOnClickListener( clickListenerBoutonsDeletePhoto);
        createLink.setOnClickListener(clickListenerCreateLink);


        
        

    }
    
    protected void onClose() {      
        datasource.close();
    }
    
    
    /**
     * loading the different projects of the local db
     * @return
     */

    
    public List<com.ecn.urbapp.db.Photo> recupPhoto() {
        
        List<com.ecn.urbapp.db.Photo> values = this.datasource.getAllPhotos();
        
        return values;
         
    }
    
    /**
     * creating a list of project and loads in the view
     */

    
    public void refreshListPhoto(){      
    	
    	List<com.ecn.urbapp.db.Photo> values = recupPhoto();
    	
        ArrayAdapter<com.ecn.urbapp.db.Photo> adapterPhoto = new ArrayAdapter<com.ecn.urbapp.db.Photo>(this, android.R.layout.simple_expandable_list_item_1,values);
        setListAdapter(adapterPhoto);  
   }
    
    
    
   //TEST METHODS TO CREATE CONTENT THAT WILL BE DISPLAYED

    
   /**
    * warning Class photo already exists in android, use the explicit package path : com.ecn.urbapp.db.Photo for cast
    */
    private OnClickListener clickListenerBoutonsAddPhoto = new OnClickListener(){
    	public void onClick(View view){
    		ArrayAdapter<com.ecn.urbapp.db.Photo> adapterPhoto = (ArrayAdapter<com.ecn.urbapp.db.Photo>) getListAdapter();
    		
    		com.ecn.urbapp.db.Photo photo1 = null;
    			String[] Descr = new String[] {"jolie photo", "batiment trs grand", "so beautiful"};
    			String[] Authors = new String[]{"Mme Dromadaire","M. Dindon","M. Loutre"};
    			String[] url = new String[] {"img1.png", "img2.png", "img3.png"};
    			int nextInt = new Random().nextInt(3);
    			
    		GpsGeom gps1=null;
    			String[] coord = { new String("47.249069//-1.54820"),new String("48.853//2.35"),new String("47.322047//5.041479999999979")} ;
    		
    			
    			//save the new Project to database
    			photo1 = datasource.createPhoto(Descr[nextInt], Authors[nextInt], url[nextInt]);
    			//save the gpsgeom to database & project
    				//TODO CREATE A TRANSACTION THE SQL
    			
    			gps1 = datasource.createGPSGeomToPhoto(coord[(int) (Math.random()*3)], photo1.getPhoto_id());
    			//updating p1 attributes
    			photo1.setGpsGeom_id(gps1.getGpsGeomsId());
    			
    			adapterPhoto.add(photo1);
    			adapterPhoto.notifyDataSetChanged();
    	};
    };
    
    
    
    private OnClickListener clickListenerBoutonsDeletePhoto = new OnClickListener(){
    	public void onClick(View view){
    		ArrayAdapter<com.ecn.urbapp.db.Photo> adapter = (ArrayAdapter<com.ecn.urbapp.db.Photo>) getListAdapter();
    		com.ecn.urbapp.db.Photo photo1 = null;
    		if (getListAdapter().getCount()>0){
				photo1 = (com.ecn.urbapp.db.Photo) getListAdapter().getItem(0);
				datasource.deletePhoto(photo1);
				adapter.remove(photo1);
			}
    		
    			adapter.notifyDataSetChanged();
    	};
    };    
    
    private OnClickListener clickListenerCreateLink = new OnClickListener(){
    	public void onClick(View view){
    		ArrayAdapter<Composed> adapterPhoto = (ArrayAdapter<Composed>) getListAdapter();
    		Composed link1 = null;
    		link1 = datasource.createLink(Integer.parseInt(projet_to_link_id.getText().toString()), Integer.parseInt(photo_to_link_id.getText().toString()));
			adapterPhoto.add(link1);
			adapterPhoto.notifyDataSetChanged();
			}	
    	};
      
    
}
