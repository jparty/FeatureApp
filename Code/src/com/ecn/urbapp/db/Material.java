package com.ecn.urbapp.db;

public class Material {
	
	private long material_id;
	private String material_name;
	
	
	public long getMaterial_id() {
		return material_id;
	}
	public void setMaterial_id(long material_id) {
		this.material_id = material_id;
	}
	public String getMaterial_name() {
		return material_name;
	}
	public void setMaterial_name(String material_name) {
		this.material_name = material_name;
	}
	
	@Override
	public String toString() {
		return "Material [material_id=" + material_id + ", material_name="
				+ material_name + "]";
	}

}
