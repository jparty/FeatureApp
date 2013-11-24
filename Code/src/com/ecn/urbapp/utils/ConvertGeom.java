package com.ecn.urbapp.utils;

import java.util.ArrayList;
import java.util.Arrays;

import android.graphics.Point;

import com.ecn.urbapp.db.GpsGeom;
import com.ecn.urbapp.db.PixelGeom;
import com.ecn.urbapp.zones.Zone;
import com.google.android.gms.maps.model.LatLng;

public class ConvertGeom{
	//TODO test every function
	public static Zone pixelGeomToZone(PixelGeom the_geom){
		ArrayList<Point> list = new ArrayList<Point>();

		String s = the_geom.getPixelGeom_the_geom().replace("POLYGON((", "");
		s = s.replace("))", "");
		ArrayList<String> tab = new ArrayList<String>(Arrays.asList(s.split(", ")));
		for(String str : tab){
			list.add(new Point(Integer.parseInt(str.split(" ")[0]), Integer.parseInt(str.split(" ")[1])));
		}
		
		Zone z = new Zone();
		for(Point p : list){
			z.addPoint(p);
		}
		return z;
	}
	
	public static String ZoneToPixelGeom(Zone zone){
		String ret="POLYGON((";
		String s="";
		
		s="";
		for(Point p : zone.getPoints()){
			s+=p.x+" "+p.y;
			if(zone.getPoints().lastElement()!=p){
				s+=", ";
			}
		}
		ret+=s;
		
		ret+="))";
		return ret;
	}
	
	public static ArrayList<LatLng> gpsGeomToLatLng(GpsGeom the_geom){
		ArrayList<LatLng> list = new ArrayList<LatLng>();

		String s = the_geom.getGpsGeomCord().replace("POLYGON((", "");
		s = s.replace("))", "");
		ArrayList<String> tab = new ArrayList<String>(Arrays.asList(s.split(", ")));
		for(String str : tab){
			list.add(new LatLng(Double.parseDouble(str.split(" ")[0]), Double.parseDouble(str.split(" ")[1])));
		}
		return list;
	}
	
	public static String latLngToGpsGeom(ArrayList<LatLng> list){
		String ret="POLYGON((";
		
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