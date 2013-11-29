package com.ecn.urbapp.db;

import android.content.ContentValues;

import com.ecn.urbapp.activities.MainActivity;

public class ElementType extends DataObject {

	
	//Attributes
	//TODO Adddescription for javadoc
	private long elementType_id;
	//TODO Adddescription for javadoc
	private String elementType_name;
	
	
	
	
	//Getters
	//TODO Adddescription for javadoc
	public long getElementType_id() {
		return elementType_id;
	}
	
	//TODO Adddescription for javadoc
	public String getElementType_name() {
		return elementType_name;
	}

	
	
	
	
	//Setters
	//TODO Adddescription for javadoc
	public void setElementType_id(long elementType_id) {
		this.elementType_id = elementType_id;
	}
	
	//TODO Adddescription for javadoc
	public void setElementType_name(String elementType_name) {
		this.elementType_name = elementType_name;
	}

	


	
	
	//Override Methods
	//TODO Adddescription for javadoc
	@Override
	public String toString() {
		return "ElementType [elementType_id=" + elementType_id
				+ ", elementType_name=" + elementType_name + "]";
	}

	@Override
	public long getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long setId() {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public void saveToLocal(LocalDataSource datasource) {
		ContentValues values = new ContentValues(); 
		values.put(MySQLiteHelper.COLUMN_ELEMENTTYPEID, this.elementType_id);
		values.put(MySQLiteHelper.COLUMN_ELEMENTTYPENAME, this.elementType_name);
		datasource.getDatabase().insert(MySQLiteHelper.TABLE_ELEMENTTYPE, null, values);		
	}
}
