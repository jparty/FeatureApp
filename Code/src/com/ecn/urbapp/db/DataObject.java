package com.ecn.urbapp.db;

import android.content.ContentValues;

import com.ecn.urbapp.activities.MainActivity;

public abstract class DataObject {
	
	//Attributes 
	/**
	 * Boolean attribute to know if the object is already created in the local database or not
	 */
	protected Boolean registredInLocal ;
	
	
	
	
	//Getters
	/**
	 * getter for the attribute registredInLocal
	 * @return Boolean
	 */
	public Boolean getRegistredInLocal() {
		return registredInLocal;
	}
	
	/**
	 * method to get the Id of a DataObject
	 * will be specified in each class that inherits DataObjects
	 */
	public abstract long getId();

	
	
	
	//Setters
	/**
	 * setter for the attribute registredInLocal
	 * @param the value to insert
	 */
	public void setRegistredInLocal(Boolean registredInLocal) {
		this.registredInLocal = registredInLocal;
	}
	
	/**
	 * method to set the Id of a DataObject
	 * will be specified in each class that inherits DataObjects
	 */
	public abstract long setId();

	
	
	
	
	//Methods
	/**
	 * method to save the object in the Database
	 * will be specified in each class that inherits DataObjects
	 * @param datasource is the Localdatasource of which DataObject is a table
	 */
	public abstract void saveToLocal(LocalDataSource datasource);
	
	/**
	 * method to use the toString of a DataObject
	 * will be specified in each class that inherits DataObjects
	 */
	public abstract String toString();
	

}
