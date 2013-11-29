/*--------------------------------------------------------------------

Copyright Jonathan Cozzo and Patrick Rannou (22/03/2013)

This software is an Android application whose purpose is to select 
and characterize zones on a photography (type, material, color...).

This software is governed by the CeCILL license under French law and
abiding by the rules of distribution of free software.  You can  use, 
modify and/ or redistribute the software under the terms of the CeCILL
license as circulated by CEA, CNRS and INRIA at the following URL
"http://www.cecill.info". 

As a counterpart to the access to the source code and  rights to copy,
modify and redistribute granted by the license, users are provided only
with a limited warranty  and the software's author,  the holder of the
economic rights,  and the successive licensors  have only  limited
liability. 

In this respect, the user's attention is drawn to the risks associated
with loading,  using,  modifying and/or developing or reproducing the
software by the user in light of its specific status of free software,
that may mean  that it is complicated to manipulate,  and  that  also
therefore means  that it is reserved for developers  and  experienced
professionals having in-depth computer knowledge. Users are therefore
encouraged to load and test the software's suitability as regards their
requirements in conditions enabling the security of their systems and/or 
data to be ensured and,  more generally, to use and operate it in the 
same conditions as regards security. 

The fact that you are presently reading this means that you have had
knowledge of the CeCILL license and that you accept its terms.

-----------------------------------------------------------------------*/

package com.ecn.urbapp.zones;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import android.content.res.Resources;
import android.graphics.Point;

import com.ecn.urbapp.R;
import com.ecn.urbapp.activities.MainActivity;
import com.ecn.urbapp.db.Element;
import com.ecn.urbapp.db.ElementType;
import com.ecn.urbapp.db.Material;
import com.ecn.urbapp.db.PixelGeom;
import com.ecn.urbapp.utils.ConvertGeom;

//TODO only dsplay databased material and not those put into ressources

/**
 * This class regroup all the zones linked to a photo
 * 
 * @author patrick, Jules Party
 * 
 */
public final class UtilCharacteristicsZone {

	/**
	 * Set the type of all the selected zones
	 * 
	 * @param zone
	 *            the zone
	 * @param type
	 *            the type to set
	 */
	public static void setTypeForSelectedZones(String type) {
		for (Element e : getAllSelectedZones()) {
			for(ElementType et : MainActivity.elementType){
				if(et.getElementType_name().equals(type)){
					e.setElementType_id(et.getElementType_id());
				}
			}
		}
	}

	/**
	 * Set the material of all the selected zones
	 * 
	 * @param zone
	 *            the zone
	 * @param material
	 *            the material to set
	 */
	public static void setMaterialForSelectedZones(String material) {

		for (Element e : getAllSelectedZones()) {
			for(Material m : MainActivity.material){
				if(m.getMaterial_name().equals(material)){
					e.setMaterial_id(m.getMaterial_id());
				}
			}
		}
	}

	/**
	 * Set the color of all the selected zones
	 * 
	 * @param zone
	 *            the zone
	 * @param color
	 *            the color to set
	 */
	public static void setColorForSelectedZones(int color) {
		for (Element e : getAllSelectedZones()) {
			e.setElement_color(""+color);
		}
	}

	/**
	 * Return the color of all the selected zones as an int (or 0 when the zones
	 * have not the same color)
	 * 
	 * @return the color as an int
	 */
	public static Integer getColorForSelectedZones() {
		Vector<Element> element = getAllSelectedZones();
		if (element != null && !element.isEmpty()) {
			int color=0;
			if(element.get(0).getElement_color()!=null){
				color = Integer.parseInt(element.get(0).getElement_color());
			}
			for (Element e : element) {
				if(element.get(0).getElement_color()!=null){
					if (Integer.parseInt(e.getElement_color()) != color) {
						color = 0;
					}
				}
			}
			return color;
		} else {
			return 0;
		}
	}

	/**
	 * This method return the position (in the list) of the first zone that
	 * contains the point in parameter and -1 if no zone is appropriate
	 * 
	 * @param point
	 * @return the number of the smallest zone that contains the point and -1
	 *         otherwise
	 */
	public static int isInsideZone(Point point) {
		int result = -1;
		for (int i = 0; i < MainActivity.pixelGeom.size(); i++) {
			if (ConvertGeom.pixelGeomToZone(MainActivity.pixelGeom.get(i)).containPoint(point)) {
				if (result == -1) {
					result = i;
				} else if (ConvertGeom.pixelGeomToZone(MainActivity.pixelGeom.get(i)).area()
						< ConvertGeom.pixelGeomToZone(MainActivity.pixelGeom.get(result)).area()) {
					result = i;
				}
			}
		}
		return result;
	}

	/**
	 * Unselect all the zones
	 */
	public static void unselectAll() {
		for (int i = 0; i < MainActivity.pixelGeom.size(); i++) {
			MainActivity.pixelGeom.get(i).selected = false;
		}
	}

	/**
	 * Select the zone whose number is in parameter (if the number is positive)
	 * and all the linked zones
	 * 
	 * @param zoneNumber
	 *            the number of the zone to select
	 */
	public static void select(int zoneNumber) {
		if (zoneNumber >= 0) {
			MainActivity.pixelGeom.get(zoneNumber).selected = !MainActivity.pixelGeom.get(zoneNumber).selected;
		} else {
			unselectAll();
		}
	}

	/**
	 * Return a vector with all the selected zones
	 * 
	 * @return vector with all the selected zones
	 */
	public static Vector<Element> getAllSelectedZones() {
		Vector<Element> selectedElements = new Vector<Element>();
		for(PixelGeom pg: MainActivity.pixelGeom){
			if(pg.selected){
				for(Element e : MainActivity.element){
					if(e.getPixelGeom_id()==pg.getPixelGeomId()){
						selectedElements.add(e);
					}
				}
			}
		}
		/*
		for (int i = 0; i < MainActivity.zones.size(); i++) {
			if (MainActivity.zones.get(i).selected) {
				selectedZonesNumbers.add(MainActivity.zones.get(i));
			}
		}*/
		return selectedElements;
	}

	/**
	 * This method return a HashMap with two keys :  types and materials,
	 * and whose values are HashMaps using the kind of materials (or types) as keys and the
	 * percentage of presence of these materials (or types) along the selected zones as values.
	 * 
	 * @param res
	 */
	public static Map<String, HashMap<String, Float>> getStatsForSelectedZones(Resources res) {
		Vector<Element> selectedZones = getAllSelectedZones();
		if (selectedZones.isEmpty()) {
			selectedZones = new Vector<Element>(MainActivity.element);
		}
		float totalArea = 0f;
		HashMap<String, Float> types = new HashMap<String, Float>();
		HashMap<String, Float> materials = new HashMap<String, Float>();
		for (Element e : selectedZones) {
			String type="";
			PixelGeom pg = new PixelGeom();;
			for(PixelGeom g : MainActivity.pixelGeom){
				if(g.getPixelGeomId()==e.getPixelGeom_id()){
					pg = g;
				}
			}
			for(ElementType et : MainActivity.elementType){
				if(et.getElementType_id()==e.getElementType_id()){
					type=et.getElementType_name();
				}
			}
			Float currentArea = types.get(type);
			//TODO finish to implement the database object
			if (currentArea != null) {
				types.put(type, currentArea + ConvertGeom.pixelGeomToZone(pg).area());
			} else {
				types.put(type, ConvertGeom.pixelGeomToZone(pg).area());
			}
			String material="";
			for(Material  m : MainActivity.material){
				if(m.getMaterial_id()==e.getMaterial_id()){
					material=m.getMaterial_name();
				}
			}
			currentArea = materials.get(material);
			if (currentArea != null) {
				materials.put(material, currentArea + ConvertGeom.pixelGeomToZone(pg).area());
			} else {
				materials.put(material, ConvertGeom.pixelGeomToZone(pg).area());
			}
			totalArea += ConvertGeom.pixelGeomToZone(pg).area();
			
		}
		for (String key : materials.keySet()) {
			materials.put(key, materials.get(key) / totalArea);
		}

		for (String key : types.keySet()) {
			types.put(key, types.get(key) / totalArea);
		}
		HashMap<String, HashMap<String, Float>> summary = new HashMap<String, HashMap<String, Float>>();
		summary.put(res.getString(R.string.type), types);
		summary.put(res.getString(R.string.materials), materials);
		return summary;
	}

}
