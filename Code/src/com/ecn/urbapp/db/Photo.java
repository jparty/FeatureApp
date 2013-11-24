package com.ecn.urbapp.db;

public class Photo {
	

	//TODO Adddescription for javadoc
	private long photo_id;
	private String photo_description;
	private String photo_author;
	/**
	 * attributes that declare the name of the picture for instance : img1.png
	 */
	private String photo_url;
	private long gpsGeom_id;

	//TODO Adddescription for javadoc
	public long getGpsGeom_id() {
		return gpsGeom_id;
	}

	//TODO Adddescription for javadoc
	public void setGpsGeom_id(long gpsGeom_id) {
		this.gpsGeom_id = gpsGeom_id;
	}

	//TODO Adddescription for javadoc
	private String Ext_GpsGeomCoord;

	//TODO Adddescription for javadoc
	public String getExt_GpsGeomCoord() {
		return Ext_GpsGeomCoord;
	}

	//TODO Adddescription for javadoc
	public void setExt_GpsGeomCoord(String ext_GpsGeomCoord) {
		Ext_GpsGeomCoord = ext_GpsGeomCoord;
	}

	public String getPhoto_url() {
		return photo_url;
	}

	//TODO Adddescription for javadoc
	public void setPhoto_url(String photo_url) {
		this.photo_url = photo_url;
	}

	//TODO Adddescription for javadoc
	private long gps_Geom_id;
	

	//TODO Adddescription for javadoc
	public void setPhoto_id(long photo_id) {
		this.photo_id = photo_id;
	}

	//TODO Adddescription for javadoc
	public void setPhoto_description(String photo_description) {
		this.photo_description = photo_description;
	}

	//TODO Adddescription for javadoc
	public void setPhoto_author(String photo_author) {
		this.photo_author = photo_author;
	}

	//TODO Adddescription for javadoc
	public void setGps_Geom_id(long gps_Geom_id) {
		this.gps_Geom_id = gps_Geom_id;
	}

	//TODO Adddescription for javadoc
	public long getPhoto_id() {
		return photo_id;
	}

	//TODO Adddescription for javadoc
	public String getPhoto_description() {
		return photo_description;
	}

	//TODO Adddescription for javadoc
	public String getPhoto_author() {
		return photo_author;
	}

	//TODO Adddescription for javadoc
	public long getGps_Geom_id() {
		return gps_Geom_id;
	}

	//TODO Adddescription for javadoc
	@Override
	public String toString() {
		return "Photo [photo_id=" + photo_id + ", photo_description="
				+ photo_description + ", photo_author=" + photo_author
				+ ", photo_url=" + photo_url + ", gps_Geom_id=" + gps_Geom_id +"&" + "  position =" + this.Ext_GpsGeomCoord
				+ "]";
	}


}
