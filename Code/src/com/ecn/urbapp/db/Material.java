package com.ecn.urbapp.db;

public class Material {

	//TODO Adddescription for javadoc
	private long material_id;
	//TODO Adddescription for javadoc
	private String material_name;
	

	//TODO Adddescription for javadoc
	public long getMaterial_id() {
		return material_id;
	}

	//TODO Adddescription for javadoc
	public void setMaterial_id(long material_id) {
		this.material_id = material_id;
	}

	//TODO Adddescription for javadoc
	public String getMaterial_name() {
		return material_name;
	}

	//TODO Adddescription for javadoc
	public void setMaterial_name(String material_name) {
		this.material_name = material_name;
	}

	//TODO Adddescription for javadoc
	@Override
	public String toString() {
		return "Material [material_id=" + material_id + ", material_name="
				+ material_name + "]";
	}

}
