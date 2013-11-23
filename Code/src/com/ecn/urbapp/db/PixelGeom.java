package com.ecn.urbapp.db;

public class PixelGeom {

	//TODO Adddescription for javadoc
	private long pixelGeom_id;
	private String pixelGeom_the_geom;
	

	//TODO Adddescription for javadoc
	public long getPixelGeomId(){
		return pixelGeom_id;
	}

	//TODO Adddescription for javadoc
	public void setPixelGeomId(long id) {
		this.pixelGeom_id = id;
	}

	//TODO Adddescription for javadoc
	public String getPixelGeom_the_geom() {
		return pixelGeom_the_geom;
	}

	//TODO Adddescription for javadoc
	public void setPixelGeom_the_geom(String pixelGeom_the_geom) {
		this.pixelGeom_the_geom = pixelGeom_the_geom;
	}

	//TODO Adddescription for javadoc
	//will be used by the ArayAdapter in the ListView
	@Override
	public String toString() {
		return "pixelGeom_id =" + this.pixelGeom_id + "&" + "\ncoord =" + this.pixelGeom_the_geom ;
		
	}


	
}
