package com.example.googlemaps;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

/**
 * Defines a mix of differents objects, needed for the Async method
 * Contains 
 * 			a marker
 * 			the position
 * 			the address (facultative first)
 * @author Sebastien
 *
 */
public class MarkerPos {
	
	private Marker marker;
	private LatLng position;
	private String adresse="Adresse inconnue";
	
	public MarkerPos(Marker marker, LatLng position) {
		super();
		this.marker = marker;
		this.position = position;
	}
	
	public MarkerPos(MarkerPos markpos) {
		super();
		this.marker = markpos.getMarker();
		this.position = markpos.getPosition();
		this.adresse = markpos.getAdresse();
	}
	
	public Marker getMarker() {
		return marker;
	}
	public void setMarker(Marker marker) {
		this.marker = marker;
	}
	public LatLng getPosition() {
		return position;
	}
	public void setPosition(LatLng position) {
		this.position = position;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	
	

}
