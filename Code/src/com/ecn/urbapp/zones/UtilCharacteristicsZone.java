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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import com.ecn.urbapp.R;
import com.ecn.urbapp.activities.MainActivity;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryCollection;
import com.vividsolutions.jts.geom.GeometryFactory;

import android.content.res.Resources;
import android.graphics.Point;

/**
 * This class regroup all the zones linked to a photo
 * 
 * @author patrick, Jules Party
 * 
 */
public final class UtilCharacteristicsZone {

	private static GeometryFactory gf = new GeometryFactory();

	/**
	 * Set the type of all the selected zones
	 * 
	 * @param zone
	 *            the zone
	 * @param type
	 *            the type to set
	 */
	public static void setTypeForSelectedZones(String type) {
		for (Zone zone : getAllSelectedZones()) {
			zone.setType(type);
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
		for (Zone zone : getAllSelectedZones()) {
			zone.setMaterial(material);
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
		for (Zone zone : getAllSelectedZones()) {
			zone.setColor(color);
		}
	}

	/**
	 * Return the color of all the selected zones as an int (or 0 when the zones
	 * have not the same color)
	 * 
	 * @return the color as an int
	 */
	public static Integer getColorForSelectedZones() {
		Vector<Zone> zones = getAllSelectedZones();
		if (zones != null && !zones.isEmpty()) {
			int color = zones.get(0).getColor();
			for (Zone zone : zones) {
				if (zone.getColor() != color) {
					color = 0;
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
		for (int i = 0; i < MainActivity.zones.size(); i++) {
			if (MainActivity.zones.get(i).getPolygon().contains(gf.createPoint(new Coordinate(point.x, point.y)))) {
				if (result == -1) {
					result = i;
				} else if (MainActivity.zones.get(i).area() < MainActivity.zones.get(result)
						.area()) {
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
		for (int i = 0; i < MainActivity.zones.size(); i++) {
			MainActivity.zones.get(i).selected = false;
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
			MainActivity.zones.get(zoneNumber).selected = !MainActivity.zones.get(zoneNumber).selected;
		} else {
			unselectAll();
		}
	}

	/**
	 * Return a vector with all the selected zones
	 * 
	 * @return vector with all the selected zones
	 */
	public static Vector<Zone> getAllSelectedZones() {
		Vector<Zone> selectedZonesNumbers = new Vector<Zone>();
		for (int i = 0; i < MainActivity.zones.size(); i++) {
			if (MainActivity.zones.get(i).selected) {
				selectedZonesNumbers.add(MainActivity.zones.get(i));
			}
		}
		return selectedZonesNumbers;
	}

	/**
	 * This method return a HashMap with two keys :  types and materials,
	 * and whose values are HashMaps using the kind of materials (or types) as keys and the
	 * percentage of presence of these materials (or types) along the selected zones as values.
	 * 
	 * @param res
	 */
	public static Map<String, HashMap<String, Float>> getStatsForSelectedZones(
			Resources res) {
		Vector<Zone> selectedZones = getAllSelectedZones();
		if (selectedZones.isEmpty()) {
			selectedZones = MainActivity.zones;
		}
		float totalArea = 0f;
		HashMap<String, Float> types = new HashMap<String, Float>();
		HashMap<String, Float> materials = new HashMap<String, Float>();
		for (Zone zone : selectedZones) {
			String type = zone.getTypeToText(res);
			Float currentArea = types.get(type);
			if (currentArea != null) {
				types.put(type, currentArea + zone.area());
			} else {
				types.put(type, zone.area());
			}
			String material = zone.getMaterialToText(res);
			currentArea = materials.get(material);
			if (currentArea != null) {
				materials.put(material, currentArea + zone.area());
			} else {
				materials.put(material, zone.area());
			}
			totalArea += zone.area();
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

	public static void addInMainActivityZones(Zone zone) {
		List<Zone> zonesToRemove =  new ArrayList<Zone>();
		List<Zone> zonesToAdd =  new ArrayList<Zone>();
		if (zone.getPolygon() == null) {
			zone = new Zone(zone);
		}
		for (Zone oldZone : MainActivity.zones) {
			if (zone.getPolygon().covers(oldZone.getPolygon())) {
				zone.createHole(oldZone.getPolygon());
				zonesToAdd.add(zone);
				break;
			} else if (zone.getPolygon().coveredBy(oldZone.getPolygon())) {
				oldZone.createHole(zone.getPolygon());
				zonesToAdd.add(zone);
				break;
			} else if (zone.getPolygon().intersects(oldZone.getPolygon())
					&& !zone.getPolygon().touches(oldZone.getPolygon())) {
				zonesToRemove.add(oldZone);
				Geometry geom = zone.getPolygon().intersection(oldZone.getPolygon());
				zonesToAdd.addAll(getZonesFromGeom(geom));
				geom = zone.getPolygon().difference(oldZone.getPolygon());
				zonesToAdd.addAll(getZonesFromGeom(geom));
				geom = oldZone.getPolygon().difference(zone.getPolygon());
				zonesToAdd.addAll(getZonesFromGeom(geom));
				break;
			}
		}
		if (zonesToAdd.isEmpty()) {
			MainActivity.zones.add(zone);
		} else {
			for (Zone z : zonesToRemove) {
				MainActivity.zones.remove(z);
			}
			for (Zone z : zonesToAdd) {
				addInMainActivityZones(z);
			}
		}
	}

	private static List<Zone> getZonesFromGeom(Geometry geom) {
		List<Zone> result = new ArrayList<Zone>();
		if (geom instanceof GeometryCollection) {
			GeometryCollection geomColl = (GeometryCollection) geom;
			for (int i = 0; i < geomColl.getNumGeometries(); i++) {
				result.addAll(getZonesFromGeom(geomColl.getGeometryN(i)));
			}
		} else {
			Coordinate[] coords = geom.getCoordinates();
			Zone zone = new Zone();
			for (int i = 0; i < coords.length; i++) {
				zone.addPoint(new Point((int) coords[i].x, (int) coords[i].y));
			}
			result.add(zone);
		}
		return result;
	}
}
