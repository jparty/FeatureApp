package com.ecn.urbapp.db;

public class ElementType {

	private long elementType_id;
	private String elementType_name;
	
	
	public long getElementType_id() {
		return elementType_id;
	}


	public void setElementType_id(long elementType_id) {
		this.elementType_id = elementType_id;
	}


	public String getElementType_name() {
		return elementType_name;
	}


	public void setElementType_name(String elementType_name) {
		this.elementType_name = elementType_name;
	}


	@Override
	public String toString() {
		return "ElementType [elementType_id=" + elementType_id
				+ ", elementType_name=" + elementType_name + "]";
	}
	
	
}
