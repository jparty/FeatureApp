package com.ecn.urbapp.db;


public class Project {
	private long project_id;
	private String project_name;
	private long gpsGeom_id;
	
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
		return "project_id =" + this.project_id + "&" + " name =" + this.project_name  + "&" + "  position =" + this.gpsGeom_id ;
		//print the uuid, need a query to get a position
	}
	
	
		
}
