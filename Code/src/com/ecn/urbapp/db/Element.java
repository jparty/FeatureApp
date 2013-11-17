package com.ecn.urbapp.db;

public class Element {
	
	private long element_id;
	private long photo_id;
	private long material_id;
	private long elementType_id;
	private long pixelGeom_id;
	private long gpsGeom_id;
	
	private String element_color;
	
	
	public long getElement_id() {
		return element_id;
	}

	public void setElement_id(long element_id) {
		this.element_id = element_id;
	}

	public long getPhoto_id() {
		return photo_id;
	}

	public void setPhoto_id(long photo_id) {
		this.photo_id = photo_id;
	}

	public long getMaterial_id() {
		return material_id;
	}

	public void setMaterial_id(long material_id) {
		this.material_id = material_id;
	}

	public long getElementType_id() {
		return elementType_id;
	}

	public void setElementType_id(long elementType_id) {
		this.elementType_id = elementType_id;
	}

	public long getPixelGeom_id() {
		return pixelGeom_id;
	}

	public void setPixelGeom_id(long pixelGeom_id) {
		this.pixelGeom_id = pixelGeom_id;
	}

	public long getGpsGeom_id() {
		return gpsGeom_id;
	}

	public void setGpsGeom_id(long gpsGeom_id) {
		this.gpsGeom_id = gpsGeom_id;
	}

	public String getElement_color() {
		return element_color;
	}

	public void setElement_color(String element_color) {
		this.element_color = element_color;
	}

	@Override
	public String toString() {
		return "Element [element_id=" + element_id + ", photo_id=" + photo_id
				+ ", material_id=" + material_id + ", elementType_id="
				+ elementType_id + ", pixelGeom_id=" + pixelGeom_id
				+ ", gpsGeom_id=" + gpsGeom_id + ", element_color="
				+ element_color + "]";
	}
	
	


}
