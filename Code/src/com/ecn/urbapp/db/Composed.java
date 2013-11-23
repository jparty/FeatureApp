package com.ecn.urbapp.db;

public class Composed {

	//TODO Adddescription for javadoc
	private long project_id;

	//TODO Adddescription for javadoc
	private long photo_id;

	//TODO Adddescription for javadoc
	public long getProject_id() {
		return project_id;
	}

	//TODO Adddescription for javadoc
	public void setProject_id(long project_id) {
		this.project_id = project_id;
	}

	//TODO Adddescription for javadoc
	public long getPhoto_id() {
		return photo_id;
	}

	//TODO Adddescription for javadoc
	public void setPhoto_id(long photo_id) {
		this.photo_id = photo_id;
	}

	@Override
	public String toString() {
		return "Composed [project_id=" + project_id + ", photo_id=" + photo_id
				+ "]";
	}
}
