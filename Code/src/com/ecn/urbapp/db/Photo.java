package com.ecn.urbapp.db;

public class Photo {
	
	
	private long photo_id;
	private String photo_description;
	private String photo_author;
	private String photo_url;
	private long gpsGeom_id;
	
	public long getGpsGeom_id() {
		return gpsGeom_id;
	}

	public void setGpsGeom_id(long gpsGeom_id) {
		this.gpsGeom_id = gpsGeom_id;
	}

	private String Ext_GpsGeomCoord;
	
	public String getExt_GpsGeomCoord() {
		return Ext_GpsGeomCoord;
	}

	public void setExt_GpsGeomCoord(String ext_GpsGeomCoord) {
		Ext_GpsGeomCoord = ext_GpsGeomCoord;
	}

	public String getPhoto_url() {
		return photo_url;
	}

	public void setPhoto_url(String photo_url) {
		this.photo_url = photo_url;
	}

	private long gps_Geom_id;
	
	
	public void setPhoto_id(long photo_id) {
		this.photo_id = photo_id;
	}

	public void setPhoto_description(String photo_description) {
		this.photo_description = photo_description;
	}

	public void setPhoto_author(String photo_author) {
		this.photo_author = photo_author;
	}

	public void setGps_Geom_id(long gps_Geom_id) {
		this.gps_Geom_id = gps_Geom_id;
	}
	
	public long getPhoto_id() {
		return photo_id;
	}

	public String getPhoto_description() {
		return photo_description;
	}

	public String getPhoto_author() {
		return photo_author;
	}

	public long getGps_Geom_id() {
		return gps_Geom_id;
	}

	@Override
	public String toString() {
		return "Photo [photo_id=" + photo_id + ", photo_description="
				+ photo_description + ", photo_author=" + photo_author
				+ ", photo_url=" + photo_url + ", gps_Geom_id=" + gps_Geom_id +"&" + "  position =" + this.Ext_GpsGeomCoord
				+ "]";
	}


}
