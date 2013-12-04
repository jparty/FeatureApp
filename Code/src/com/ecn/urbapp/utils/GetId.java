package com.ecn.urbapp.utils;

import java.util.ArrayList;

import com.ecn.urbapp.activities.MainActivity;
import com.ecn.urbapp.db.Element;
import com.ecn.urbapp.db.GpsGeom;
import com.ecn.urbapp.db.PixelGeom;
import com.ecn.urbapp.db.Project;
/**
 * 
 * We suppose that the elementplace in the list is equal to their ID
 *
 */
public class GetId{
	
	public static long Element(){
		/*long ret = 1;
		for(Element el : MainActivity.element){
			if(el.getElement_id()==ret){
				ret++;
			}
		}
		return ret;*/
		ArrayList<Long> ids = new ArrayList<Long>();
		for (int i = 1; i <= MainActivity.element.size() + 1; i++) {
			ids.add((long) i);
		}
		for (int i = 0; i < MainActivity.element.size(); i++) {
			ids.remove(MainActivity.element.get(i).getElement_id());
		}
		return ids.get(0);
	}
	
	public static long GpsGeom(){
		long ret = 1;
		for(GpsGeom gg : MainActivity.gpsGeom){
			if(gg.getGpsGeomsId()==ret){
				ret++;
			}
		}
		return ret;
	}
	
	public static long PixelGeom(){
		/*long ret = 1;
		for(PixelGeom pg : MainActivity.pixelGeom){
			if(pg.getPixelGeomId()==ret){
				ret++;
			}
		}
		return ret;*/
		ArrayList<Long> ids = new ArrayList<Long>();
		for (int i = 1; i <= MainActivity.pixelGeom.size() + 1; i++) {
			ids.add((long) i);
		}
		for (int i = 0; i < MainActivity.pixelGeom.size(); i++) {
			ids.remove(MainActivity.pixelGeom.get(i).getPixelGeomId());
		}
		return ids.get(0);
	}
	
	public static long Project(){
		long ret = 1;
		for(Project p : MainActivity.project){
			if(p.getProjectId()==ret){
				ret++;
			}
		}
		return ret;
	}
}