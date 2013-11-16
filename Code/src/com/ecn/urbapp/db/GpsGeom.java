package com.ecn.urbapp.db;

public class GpsGeom{
	private long gpsGeom_id;
	private String gpsGeom_the_geom;
	
	public long getGpsGeomsId(){
		return gpsGeom_id;
	}

	public String getGpsGeomCord() {
		return gpsGeom_the_geom;
	}

	public void setGpsGeomCoord(String str) {
		this.gpsGeom_the_geom = str;
	}

	public void setGpsGeomsId(long id) {
		this.gpsGeom_id = id;
	}
	
	//will be used by the ArayAdapter in the ListView
	@Override
	public String toString() {
		return "gpsGeom_id =" + this.gpsGeom_id + "&" + "\n gpsGeom_the_geom =" + this.gpsGeom_the_geom  + "&" + "\n position =" + this.gpsGeom_id;
		
	}
	
	
		
}
