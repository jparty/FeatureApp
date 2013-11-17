package com.ecn.urbapp.db;


public class Project {
	private long project_id;
	private String project_name;
	private long gpsGeom_id;
	private String Ext_GpsGeomCoord;
	
	public String getExt_GpsGeomCoord() {
		return Ext_GpsGeomCoord;
	}

	public void setExt_GpsGeomCoord(String ext_GpsGeomCoord) {
		Ext_GpsGeomCoord = ext_GpsGeomCoord;
	}

	public long getGpsGeom_id() {
		return gpsGeom_id;
	}

	public void setGpsGeom_id(long gpsGeom_id) {
		this.gpsGeom_id = gpsGeom_id;
	}

	public long getProjectId(){
		return project_id;
	}

	public String getProjectName() {
		return project_name;
	}

	public void setProjectName(String str) {
		this.project_name = str;
	}

	public void setProjectId(long id) {
		this.project_id = id;
	}
	
	//will be used by the ArayAdapter in the ListView
	@Override
	public String toString() {
		return "project_id =" + this.project_id + "&" + " name =" + this.project_name  + "&" + "  position =" + this.gpsGeom_id + "&" + "  position =" + this.Ext_GpsGeomCoord;
		//print the uuid, need a query to get a position
	}
	
	
		
}
