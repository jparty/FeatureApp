package com.ecn.urbapp.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

import android.graphics.Point;

import com.ecn.urbapp.zones.Zone;

public class ConvertGeom{

	//TODO adapter pixelGeom to pixelGeom
	public static Vector<Zone> geometryToVectorZone(String the_geom){
		return null;
	}
	
	public static String vectorZoneToGeometry(Vector<Zone> zones){
		return null;
	}
	
	public static Vector<Zone> pixelToVectorZone(String pixel){
		/*String s = pixel.replace("GEOMETRYCOLLECTION(", "");
		s = s.replace(")))", "");
		ArrayList<String> tab = new ArrayList<String>(Arrays.asList(s.split(")),")));
		for(int i=0; i<)*/
		return null;
	}
	
	public static String vectorZoneToPixel(Vector<Zone> zones){
		String ret="GEOMETRYCOLLECTION(";
		String s="";
		
		for(Zone z : zones){
			s="POLYGON((";
			for(Point p : z.getPoints()){
				s+=p.x+" "+p.y;
				if(z.getPoints().lastElement()!=p){
					s+=", ";
				}
			}
			s+="))";
			if(zones.lastElement()!=z){
				s+=", ";
			}
		}
		ret+=s;
		ret+=")";
		return ret;
	}
}