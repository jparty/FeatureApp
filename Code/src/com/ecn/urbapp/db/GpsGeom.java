package com.ecn.urbapp.db;

import android.content.ContentValues;
import android.database.Cursor;

import com.ecn.urbapp.activities.MainActivity;

public class GpsGeom extends DataObject{
	
	
	//Attributes
	//TODO Adddescription for javadoc
	private long gpsGeom_id;
	//TODO Adddescription for javadoc
	private String gpsGeom_the_geom;

	
	
	
	//Getters
	//TODO Adddescription for javadoc
	public long getGpsGeomsId(){
		return gpsGeom_id;
	}

	//TODO Adddescription for javadoc
	public String getGpsGeomCord() {
		return gpsGeom_the_geom;
	}

	
	
	
	
	//Setters
	//TODO Adddescription for javadoc
	public void setGpsGeomCoord(String str) {
		this.gpsGeom_the_geom = str;
	}

	//TODO Adddescription for javadoc
	public void setGpsGeomId(long id) {
		this.gpsGeom_id = id;
	}

	
	
	
	
	
	//Ovveride methods
	//TODO Adddescription for javadoc
	//will be used by the ArayAdapter in the ListView
	@Override
	public String toString() {
		return "gpsGeom_id =" + this.gpsGeom_id + "&" + "\n gpsGeom_the_geom =" + this.gpsGeom_the_geom  + "&" + "\n coord =" + this.gpsGeom_id;
		
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
			Cursor cursor = datasource.getDatabase().rawQuery(GETMAXGPSGEOMID, null);
			cursor.moveToFirst();
			if(!cursor.isAfterLast()){
				this.setGpsGeomId(this.getGpsGeomsId()+cursor.getLong(0));
			}
		}
		ContentValues values = new ContentValues(); 
		values.put(MySQLiteHelper.COLUMN_GPSGEOMID, this.gpsGeom_id);
		values.put(MySQLiteHelper.COLUMN_GPSGEOMCOORD, this.gpsGeom_the_geom);
		datasource.getDatabase().insert(MySQLiteHelper.TABLE_GPSGEOM, null, values);	
	}		
	/**
	 * query to get the biggest GpsGeom_id from local db
	 * 
	 */
	private static final String
		GETMAXGPSGEOMID = 
			"SELECT "+MySQLiteHelper.TABLE_GPSGEOM+"."+MySQLiteHelper.COLUMN_GPSGEOMID+" FROM "
			+ MySQLiteHelper.TABLE_GPSGEOM 
			+" ORDER BY "+ MySQLiteHelper.TABLE_GPSGEOM+"."+MySQLiteHelper.COLUMN_GPSGEOMID
			+" DESC LIMIT 1 ;"
		;
}
