package com.ecn.urbapp.db;

public class ElementType {

	//TODO Adddescription for javadoc
	private long elementType_id;
	//TODO Adddescription for javadoc
	private String elementType_name;
	

	//TODO Adddescription for javadoc
	public long getElementType_id() {
		return elementType_id;
	}


	//TODO Adddescription for javadoc
	public void setElementType_id(long elementType_id) {
		this.elementType_id = elementType_id;
	}


	//TODO Adddescription for javadoc
	public String getElementType_name() {
		return elementType_name;
	}


	//TODO Adddescription for javadoc
	public void setElementType_name(String elementType_name) {
		this.elementType_name = elementType_name;
	}


	//TODO Adddescription for javadoc
	@Override
	public String toString() {
		return "ElementType [elementType_id=" + elementType_id
				+ ", elementType_name=" + elementType_name + "]";
	}
	
	
}
