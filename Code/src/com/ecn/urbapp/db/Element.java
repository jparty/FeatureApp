package com.ecn.urbapp.db;

public class Element {

	//TODO Adddescription for javadoc
	private long element_id;
	//TODO Adddescription for javadoc
	private long photo_id;
	//TODO Adddescription for javadoc
	private long material_id;
	//TODO Adddescription for javadoc
	private long elementType_id;
	//TODO Adddescription for javadoc
	private long pixelGeom_id;
	//TODO Adddescription for javadoc
	private long gpsGeom_id;

	//TODO Adddescription for javadoc
	private String element_color;
	

	//TODO Adddescription for javadoc
	public long getElement_id() {
		return element_id;
	}

	//TODO Adddescription for javadoc
	public void setElement_id(long element_id) {
		this.element_id = element_id;
	}

	//TODO Adddescription for javadoc
	public long getPhoto_id() {
		return photo_id;
	}

	//TODO Adddescription for javadoc
	public void setPhoto_id(long photo_id) {
		this.photo_id = photo_id;
	}

	//TODO Adddescription for javadoc
	public long getMaterial_id() {
		return material_id;
	}

	//TODO Adddescription for javadoc
	public void setMaterial_id(long material_id) {
		this.material_id = material_id;
	}

	//TODO Adddescription for javadoc
	public long getElementType_id() {
		return elementType_id;
	}

	//TODO Adddescription for javadoc
	public void setElementType_id(long elementType_id) {
		this.elementType_id = elementType_id;
	}

	//TODO Adddescription for javadoc
	public long getPixelGeom_id() {
		return pixelGeom_id;
	}

	//TODO Adddescription for javadoc
	public void setPixelGeom_id(long pixelGeom_id) {
		this.pixelGeom_id = pixelGeom_id;
	}

	//TODO Adddescription for javadoc
	public long getGpsGeom_id() {
		return gpsGeom_id;
	}

	public void setGpsGeom_id(long gpsGeom_id) {
		this.gpsGeom_id = gpsGeom_id;
	}

	//TODO Adddescription for javadoc
	public String getElement_color() {
		return element_color;
	}

	//TODO Adddescription for javadoc
	public void setElement_color(String element_color) {
		this.element_color = element_color;
	}

	//TODO Adddescription for javadoc
	@Override
	public String toString() {
		return "Element [element_id=" + element_id + ", photo_id=" + photo_id
				+ ", material_id=" + material_id + ", elementType_id="
				+ elementType_id + ", pixelGeom_id=" + pixelGeom_id
				+ ", gpsGeom_id=" + gpsGeom_id + ", element_color="
				+ element_color + "]";
	}
}
