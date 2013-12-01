package com.ecn.urbapp.fragments;

import java.util.ArrayList;

import com.ecn.urbapp.R;
import com.ecn.urbapp.db.*;
import com.ecn.urbapp.activities.MainActivity;

import android.app.Fragment;
import android.content.ContentValues;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;




/**
 * @author	COHENDET S�bastien
 * 			DAVID Nicolas
 * 			GUILBART Gabriel
 * 			PALOMINOS Sylvain
 * 			PARTY Jules
 * 			RAMBEAU Merwan
 * 
 * SaveFragment class
 * 
 * This is the fragment used to save the project.
 * 			
 */


public class SaveFragment extends Fragment{
	

	private Button saveToLocal = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.layout_save, null);
		
		saveToLocal=(Button)v.findViewById(R.id.save_button_ync);
		saveToLocal.setOnClickListener(OnClickSaveToLocal);
		
		return v;
	}
	
	
    private OnClickListener OnClickSaveToLocal = new OnClickListener(){
    	public void onClick(View view){
    		
    		MainActivity.datasource.open();
    		saveGpsGeomListToLocal(MainActivity.gpsGeom);
    		savePixelGeomListToLocal(MainActivity.pixelGeom);
    		saveToLocal(MainActivity.photo);
    		saveProjectListToLocal(MainActivity.project);
    		saveComposedListToLocal(MainActivity.composed);
    		saveElementListToLocal(MainActivity.element); 		 		
    		MainActivity.datasource.close();
    		
    		
    	};
    };
	
	//TODO pour la photo il faut appeler direct la liste car on n'a pas de list de photo vu qu'ion bosse avecu ne seule.
	
	public void saveProjectListToLocal(ArrayList<Project> l1){
		for (Project p : l1){
			saveToLocal(p);
		}
	}
	
	public void saveElementListToLocal(ArrayList<Element> l1){
		for (Element p : l1){
			saveToLocal(p);
		}
	}
	
	public void saveGpsGeomListToLocal(ArrayList<GpsGeom> l1){
		for (GpsGeom p : l1){
			saveToLocal(p);
		}
	}
	
	public void savePixelGeomListToLocal(ArrayList<PixelGeom> l1){
		for (PixelGeom p : l1){
			saveToLocal(p);
		}
	}
	
	public void saveElementTypeListToLocal(ArrayList<ElementType> l1){
		for (ElementType p : l1){
			saveToLocal(p);
		}
	}
	
	public void saveMaterialListToLocal(ArrayList<Material> l1){
		for (Material p : l1){
			saveToLocal(p);
		}
	}
	
	public void saveComposedListToLocal(ArrayList<Composed> l1){
		for (Composed p : l1){
			saveToLocal(p);
		}
	}
	
	
	/**
	 * Method that save the project information to the local db
	 * @param p1 is a project from public static ArrayList<Project> that is instancied and has to be registred in local database
	 */
	public void saveToLocal(Project p1){
		
		ContentValues values = new ContentValues(); 
		values.put(MySQLiteHelper.COLUMN_PROJECTID, p1.getProjectId());
		values.put(MySQLiteHelper.COLUMN_PROJECTNAME, p1.getProjectName());
		values.put(MySQLiteHelper.COLUMN_GPSGEOMID, p1.getGpsGeom_id());
		MainActivity.datasource.getDatabase().insert(MySQLiteHelper.TABLE_PROJECT, null, values);
	}
	
	
	/**
	 * Method that save the photo information to the local db
	 * @param p1 is a photo from public static Photo that is instancied and has to be registred in local database
	 */
	public void saveToLocal(Photo p1){
		ContentValues values = new ContentValues(); 
		values.put(MySQLiteHelper.COLUMN_PHOTOID, p1.getPhoto_id());
		values.put(MySQLiteHelper.COLUMN_PHOTOURL, p1.getPhoto_url());
		values.put(MySQLiteHelper.COLUMN_PHOTODESCRIPTION, p1.getPhoto_description());
		values.put(MySQLiteHelper.COLUMN_PHOTOAUTHOR, p1.getPhoto_author());
		values.put(MySQLiteHelper.COLUMN_GPSGEOMID, p1.getGpsGeom_id());
		MainActivity.datasource.getDatabase().insert(MySQLiteHelper.TABLE_PHOTO, null, values);			
	}
	
	/**
	 * Method that save the composed information to the local db
	 * @param link is a Composed from public static ArrayList<Composed> that is instancied and has to be registred in local database
	 */
	public void saveToLocal(Composed link){
		ContentValues values = new ContentValues(); 
		values.put(MySQLiteHelper.COLUMN_PROJECTID, link.getProject_id());
		values.put(MySQLiteHelper.COLUMN_PHOTOID, link.getPhoto_id());
		MainActivity.datasource.getDatabase().insert(MySQLiteHelper.TABLE_COMPOSED, null, values);		
	}
	
	
	/**
	 * Method that save the composed information to the local db
	 * @param gps_thegeom is a GpsGeom from public static ArrayList<GpsGeom> that is instancied and has to be registred in local database
	 */
	public void saveToLocal(GpsGeom gps_thegeom){
		ContentValues values = new ContentValues(); 
		values.put(MySQLiteHelper.COLUMN_GPSGEOMID, gps_thegeom.getGpsGeomsId());
		values.put(MySQLiteHelper.COLUMN_GPSGEOMCOORD, gps_thegeom.getGpsGeomCord());
		MainActivity.datasource.getDatabase().insert(MySQLiteHelper.TABLE_GPSGEOM, null, values);		
	}
	
	
	/**
	 * Method that save the composed information to the local db
	 * @param pixel_thegeom is a GpsGeom from public static ArrayList<GpsGeom> that is instancied and has to be registred in local database
	 */
	public void saveToLocal(PixelGeom pixel_thegeom){
		ContentValues values = new ContentValues(); 
		values.put(MySQLiteHelper.COLUMN_PIXELGEOMID, pixel_thegeom.getPixelGeomId());
		values.put(MySQLiteHelper.COLUMN_PIXELGEOMCOORD, pixel_thegeom.getPixelGeom_the_geom());
		MainActivity.datasource.getDatabase().insert(MySQLiteHelper.TABLE_PIXELGEOM, null, values);		
	}
	
	
	/**
	 * Method that save the composed information to the local db
	 * @param elt is an Element from public static ArrayList<Element> that is instancied and has to be registred in local database
	 */
	public void saveToLocal(Element elt){
		ContentValues values = new ContentValues(); 
		values.put(MySQLiteHelper.COLUMN_ELEMENTID, elt.getElement_id());
		values.put(MySQLiteHelper.COLUMN_PHOTOID, elt.getPhoto_id());
		values.put(MySQLiteHelper.COLUMN_MATERIALID, elt.getMaterial_id());
		values.put(MySQLiteHelper.COLUMN_ELEMENTTYPEID, elt.getElementType_id());
		values.put(MySQLiteHelper.COLUMN_PIXELGEOMID, elt.getPixelGeom_id());
		values.put(MySQLiteHelper.COLUMN_GPSGEOMID, elt.getGpsGeom_id());
		values.put(MySQLiteHelper.COLUMN_ELEMENTCOLOR, elt.getElement_color());
		MainActivity.datasource.getDatabase().insert(MySQLiteHelper.TABLE_ELEMENT, null, values);		
	}
	
	
	/**
	 * Method that save the composed information to the local db
	 * @param eltType is an ElementType from public static ArrayList<ElementType> that is instancied and has to be registred in local database
	 */
	public void saveToLocal(ElementType eltType){
		ContentValues values = new ContentValues(); 
		values.put(MySQLiteHelper.COLUMN_ELEMENTTYPEID, eltType.getElementType_id());
		values.put(MySQLiteHelper.COLUMN_ELEMENTTYPENAME, eltType.getElementType_name());
		MainActivity.datasource.getDatabase().insert(MySQLiteHelper.TABLE_ELEMENTTYPE, null, values);		
	}
	
	/**
	 * Method that save the composed information to the local db
	 * @param mat is a Material from public static ArrayList<Material> that is instancied and has to be registred in local database
	 */
	public void saveToLocal(Material mat){
		ContentValues values = new ContentValues(); 
		values.put(MySQLiteHelper.COLUMN_MATERIALID, mat.getMaterial_id());
		values.put(MySQLiteHelper.COLUMN_MATERIALNAME, mat.getMaterial_name());
		MainActivity.datasource.getDatabase().insert(MySQLiteHelper.TABLE_MATERIAL, null, values);		
	}
	
}