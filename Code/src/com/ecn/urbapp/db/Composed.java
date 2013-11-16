package com.ecn.urbapp.db;

public class Composed {
	
	private long project_id;
	private long photo_id;
	
	public long getProject_id() {
		return project_id;
	}

	public void setProject_id(long project_id) {
		this.project_id = project_id;
	}

	public long getPhoto_id() {
		return photo_id;
	}

	public void setPhoto_id(long photo_id) {
		this.photo_id = photo_id;
	}

	@Override
	public String toString() {
		return "Composed [project_id=" + project_id + ", photo_id=" + photo_id
				+ "]";
	}
}
