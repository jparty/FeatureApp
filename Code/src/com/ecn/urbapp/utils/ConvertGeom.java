package com.ecn.urbapp.utils;

import java.util.ArrayList;
import java.util.Arrays;

import android.graphics.Point;

import com.ecn.urbapp.db.GpsGeom;
import com.ecn.urbapp.db.PixelGeom;
import com.ecn.urbapp.zones.Zone;
import com.google.android.gms.maps.model.LatLng;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;

public class ConvertGeom{

	private static WKTReader wktr = new WKTReader();
	
	//TODO test every function
	public static Zone pixelGeomToZone(PixelGeom the_geom){
			Zone temp = new Zone();
			Coordinate[] coords;
			try {
				coords = ((Polygon) wktr.read(the_geom.getPixelGeom_the_geom())).getCoordinates();
				for (Coordinate coord : coords) {
					temp.addPoint(new Point((int) coord.x, (int) coord.y));
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
			temp.actualizePolygon();
			return temp;
	}
	
	public static String ZoneToPixelGeom(Zone zone){
		zone.actualizePolygon();
		return zone.getPolygon().toText();
	}
	
	public static ArrayList<LatLng> gpsGeomToLatLng(GpsGeom the_geom){
		ArrayList<LatLng> list = new ArrayList<LatLng>();

		String s = the_geom.getGpsGeomCord().replace("POLYGON ((", "");
		s = s.replace("))", "");
		ArrayList<String> tab = new ArrayList<String>(Arrays.asList(s.split(", ")));
		for(String str : tab){
			//TODO debug
			list.add(new LatLng(Double.parseDouble(str.split(" ")[0]), Double.parseDouble(str.split(" ")[1])));
		}
		return list;
	}
	
	public static String latLngToGpsGeom(ArrayList<LatLng> list){
		String ret="POLYGON ((";
		
		String s="";
		for(LatLng ll : list){
			s+=ll.latitude+" "+ll.longitude;
			if(list.get(list.size()-1)!=ll){
				s+=", ";
			}
		}
		ret+=s;
		
		ret+="))";
		return ret;
	}
}