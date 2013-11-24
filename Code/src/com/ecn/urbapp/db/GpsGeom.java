package com.ecn.urbapp.db;

public class GpsGeom{

	//TODO Adddescription for javadoc
	private long gpsGeom_id;
	//TODO Adddescription for javadoc
	private String gpsGeom_the_geom;

	//TODO Adddescription for javadoc
	public long getGpsGeomsId(){
		return gpsGeom_id;
	}

	//TODO Adddescription for javadoc
	public String getGpsGeomCord() {
		return gpsGeom_the_geom;
	}

	//TODO Adddescription for javadoc
	public void setGpsGeomCoord(String str) {
		this.gpsGeom_the_geom = str;
	}

	//TODO Adddescription for javadoc
	public void setGpsGeomId(long id) {
		this.gpsGeom_id = id;
	}

	//TODO Adddescription for javadoc
	//will be used by the ArayAdapter in the ListView
	@Override
	public String toString() {
		return "gpsGeom_id =" + this.gpsGeom_id + "&" + "\n gpsGeom_the_geom =" + this.gpsGeom_the_geom  + "&" + "\n coord =" + this.gpsGeom_id;
		
	}
	
	
		
}
