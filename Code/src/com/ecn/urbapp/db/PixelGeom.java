package com.ecn.urbapp.db;

import android.content.ContentValues;
import android.database.Cursor;

import com.ecn.urbapp.activities.MainActivity;

public class PixelGeom extends DataObject  {

	
	//Attributes
	//TODO Adddescription for javadoc
	private long pixelGeom_id;
	private String pixelGeom_the_geom;

	
	//TODO rendre private et mettre accesseur
	public boolean selected;

	
	




	//Getters
	//TODO Adddescription for javadoc
	public long getPixelGeomId(){
		return pixelGeom_id;
	}
	
	public boolean isSelected() {
		return selected;
	}
	
	//TODO Adddescription for javadoc
	public String getPixelGeom_the_geom() {
		return pixelGeom_the_geom;
	}
	
	
	
	
	
	
	//Setters
	//TODO Adddescription for javadoc
	public void setPixelGeomId(long id) {
		this.pixelGeom_id = id;
	}
	
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	//TODO Adddescription for javadoc
	public void setPixelGeom_the_geom(String pixelGeom_the_geom) {
		this.pixelGeom_the_geom = pixelGeom_the_geom;
	}

	
	
	//Override methods
	//TODO Adddescription for javadoc
	//will be used by the ArayAdapter in the ListView
	@Override
	public String toString() {
		return "pixelGeom_id =" + this.pixelGeom_id + "&" + "\ncoord =" + this.pixelGeom_the_geom ;
		
	}

	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long setId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void saveToLocal(LocalDataSource datasource) {
		if(!this.getRegistredInLocal()){
			Cursor cursor = datasource.getDatabase().rawQuery(GETMAXPIXELGEOMID, null);
			cursor.moveToFirst();
			if(!cursor.isAfterLast()){
				this.setPixelGeomId(this.getPixelGeomId()+cursor.getLong(0));
			}
		}
		ContentValues values = new ContentValues(); 
		values.put(MySQLiteHelper.COLUMN_PIXELGEOMID, this.pixelGeom_id);
		values.put(MySQLiteHelper.COLUMN_PIXELGEOMCOORD, this.pixelGeom_the_geom);
		datasource.getDatabase().insert(MySQLiteHelper.TABLE_PIXELGEOM, null, values);		
		
	}
	/**
	 * query to get the biggest photo_id from local db
	 * 
	 */
	private static final String
		GETMAXPIXELGEOMID = 
			"SELECT "+MySQLiteHelper.TABLE_PIXELGEOM+"."+MySQLiteHelper.COLUMN_PIXELGEOMID+" FROM "
			+ MySQLiteHelper.TABLE_PIXELGEOM 
			+" ORDER BY "+MySQLiteHelper.TABLE_PIXELGEOM+"."+MySQLiteHelper.COLUMN_PIXELGEOMID
			+" DESC LIMIT 1 ;"
		;


	
}
