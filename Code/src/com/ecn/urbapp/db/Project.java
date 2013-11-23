package com.ecn.urbapp.db;


public class Project {

	//TODO Adddescription for javadoc
	private long project_id;
	private String project_name;
	private long gpsGeom_id;
	private String Ext_GpsGeomCoord;

	//TODO Adddescription for javadoc
	public String getExt_GpsGeomCoord() {
		return Ext_GpsGeomCoord;
	}

	//TODO Adddescription for javadoc
	public void setExt_GpsGeomCoord(String ext_GpsGeomCoord) {
		Ext_GpsGeomCoord = ext_GpsGeomCoord;
	}

	//TODO Adddescription for javadoc
	public long getGpsGeom_id() {
		return gpsGeom_id;
	}

	//TODO Adddescription for javadoc
	public void setGpsGeom_id(long gpsGeom_id) {
		this.gpsGeom_id = gpsGeom_id;
	}

	//TODO Adddescription for javadoc
	public long getProjectId(){
		return project_id;
	}

	//TODO Adddescription for javadoc
	public String getProjectName() {
		return project_name;
	}

	//TODO Adddescription for javadoc
	public void setProjectName(String str) {
		this.project_name = str;
	}

	//TODO Adddescription for javadoc
	public void setProjectId(long id) {
		this.project_id = id;
	}

	//TODO Adddescription for javadoc
	//will be used by the ArayAdapter in the ListView
	@Override
	public String toString() {
		return "project_id =" + this.project_id + "&" + " name =" + this.project_name  + "&" + "  position =" + this.gpsGeom_id + "&" + "  position =" + this.Ext_GpsGeomCoord;
		//print the uuid, need a query to get a position
	}
	
	
		
}
