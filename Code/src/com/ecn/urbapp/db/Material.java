package com.ecn.urbapp.db;

import android.content.ContentValues;

import com.ecn.urbapp.activities.MainActivity;

public class Material extends DataObject  {

	
	//Attributes
	//TODO Adddescription for javadoc
	private long material_id;
	//TODO Adddescription for javadoc
	private String material_name;
	

	
	
	
	//Getters
	//TODO Adddescription for javadoc
	public long getMaterial_id() {
		return material_id;
	}
	
	//TODO Adddescription for javadoc
	public String getMaterial_name() {
		return material_name;
	}

	
	
	
	
	//Setters
	//TODO Adddescription for javadoc
	public void setMaterial_id(long material_id) {
		this.material_id = material_id;
	}

	//TODO Adddescription for javadoc
	public void setMaterial_name(String material_name) {
		this.material_name = material_name;
	}

	
	
	
	
	//Override Methods
	//TODO Adddescription for javadoc
	@Override
	public String toString() {
		return "Material [material_id=" + material_id + ", material_name="
				+ material_name + "]";
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
		values.put(MySQLiteHelper.COLUMN_MATERIALID, this.material_id);
		values.put(MySQLiteHelper.COLUMN_MATERIALNAME, this.material_name);
		datasource.getDatabase().insert(MySQLiteHelper.TABLE_MATERIAL, null, values);	
		
	}

}
