package com.ecn.urbapp.db;

import android.content.ContentValues;

public class Composed extends DataObject{

	//Attributes
	/**
	 * attribute that refers to the project_id that is relatated to the photo
	 */
	private long project_id;

	/**
	 * attribute that refers to the photo_id that is relatated to the project_id
	 */
	private long photo_id;

	
	
	//Getters
	/**
	 * getter of the project_id
	 * @return long id of the project
	 */
	//TODO delete and replace by getId from DataObject
	public long getProject_id() {
		return project_id;
	}
	
	/**
	 * getter of the photo_id
	 * @return long id of the photo
	 */
	//TODO Adddescription for javadoc
	public long getPhoto_id() {
		return photo_id;
	}
	
	//Getters
	//TODO Adddescription for javadoc
	public void setProject_id(long project_id) {
		this.project_id = project_id;
	}

	//TODO Adddescription for javadoc
	public void setPhoto_id(long photo_id) {
		this.photo_id = photo_id;
	}

	
	
	//Override Methods
	@Override
	public String toString() {
		return "Composed [project_id=" + project_id + ", photo_id=" + photo_id
				+ "]";
	}

	//TODO shit happens
	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	//TODO shit happens
	@Override
	public long setId() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * 
	 */
	@Override
	public void saveToLocal(LocalDataSource datasource) {
		//TODO trigger
		ContentValues values = new ContentValues(); 
		values.put(MySQLiteHelper.COLUMN_PROJECTID, this.project_id);
		values.put(MySQLiteHelper.COLUMN_PHOTOID, this.photo_id);
		
		if(this.registredInLocal){
			//TODO delete, no need to change it hen it's registred
			/*
			String[] s=new String[1];
			s[0]= ""+this.project_id;
			s[1]= ""+this.photo_id;
			datasource.getDatabase().update(MySQLiteHelper.TABLE_ELEMENT, values, MySQLiteHelper.COLUMN_ELEMENTID,s );
			*/
		}
		else{
			datasource.getDatabase().insert(MySQLiteHelper.TABLE_COMPOSED, null, values);
		}
	}
}
