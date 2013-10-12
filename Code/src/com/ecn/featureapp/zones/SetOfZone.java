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

package com.ecn.featureapp.zones;

import java.util.Vector;

import com.ecn.featureapp.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.thoughtworks.xstream.XStream;

/**
 * This class regroup all the zones linked to a photo
 * 
 * @author patrick
 * 
 */
public class SetOfZone {

	/** List of all the zones */
	protected Vector<Zone> frontages;

	/** True if the location has been determined; false otherwise */
	public boolean gpsinfos;

	/** Number of floors of the building in the photo */
	public Integer numberOfFloors;

	/** Attribute containing the percentage of glazing */
	private Float percentageOfGlazing;

	/** Vector containing vector of zone that were regrouped */
	public Vector<Vector<Zone>> linkedZones;

	/** Vector containing all the balconies */
	public Vector<Balcony> balconies;

	/** SetOfZone containing the SetOfZone from an XML file */
	public SetOfZone set;

	/**
	 * Last type chosen by the user for a zone (used to remember the state of
	 * the material choice dialog during a change of screen orientation)
	 */
	public int type;

	/**
	 * Constructor of a new empty SetOfFrontage
	 */
	public SetOfZone() {
		frontages = new Vector<Zone>();
		gpsinfos = false;
		linkedZones = new Vector<Vector<Zone>>();
		balconies = new Vector<Balcony>();
	}

	/**
	 * Return the number of floors in text form (with written not defined if it
	 * is null)
	 * 
	 * @param res
	 *            the resource
	 * @return the number of floors in text form
	 */
	public String getNumberOfFloorsToText(Resources res) {
		if (this.numberOfFloors == null) {
			return res.getString(R.string.not_defined);
		} else {
			return this.numberOfFloors.toString() + " "
					+ res.getString(R.string.floors);
		}
	}

	/**
	 * This method get the percentage of glazing in text form (with %)
	 * 
	 * @param res
	 *            the resource
	 * @return the percentage of glazing in text form
	 */
	public String getPercentageOfGlazingToText(Resources res) {
		if (this.percentageOfGlazing == null) {
			return res.getString(R.string.not_defined);
		} else {
			return this.percentageOfGlazing.toString() + " %";
		}
	}

	/**
	 * Set the type of a zone and all its linked zones with the type in
	 * parameter
	 * 
	 * @param zone
	 *            the zone
	 * @param type
	 *            the type to set
	 */
	public void setType(Zone zone, String type) {
		zone.type = type;

		for (int i = 0; i < linkedZones.size(); i++) {
			if (linkedZones.get(i).contains(zone)) {
				for (int j = 0; j < linkedZones.get(i).size(); j++) {
					linkedZones.get(i).get(j).type = type;
				}
			}
		}
	}

	/**
	 * Set the material of a zone and all its linked zones with the material in
	 * parameter
	 * 
	 * @param zone
	 *            the zone
	 * @param material
	 *            the material to set
	 */
	public void setMaterial(Zone zone, String material) {
		zone.material = material;

		for (int i = 0; i < linkedZones.size(); i++) {
			if (linkedZones.get(i).contains(zone)) {
				for (int j = 0; j < linkedZones.get(i).size(); j++) {
					linkedZones.get(i).get(j).material = material;
				}
			}
		}
	}

	/**
	 * Set the color of a zone and all its linked zones with the color in
	 * parameter
	 * 
	 * @param zone
	 *            the zone
	 * @param color
	 *            the color to set
	 */
	public void setColor(Zone zone, int color) {
		zone.color = color;

		for (int i = 0; i < linkedZones.size(); i++) {
			if (linkedZones.get(i).contains(zone)) {
				for (int j = 0; j < linkedZones.get(i).size(); j++) {
					linkedZones.get(i).get(j).color = color;
				}
			}
		}
	}

	/**
	 * This method add the point to the last unfinished zone
	 * 
	 * @param point
	 *            the point to add
	 * @param accuracy
	 *            the maximum distance (in pixel) from the first point of the
	 *            zone that the user need to touch to finish to zone
	 */
	public void addPoint(Point point, float accuracy) {
		// Get the last frontage (there is at least an empty one)
		Zone lastFrontage = frontages.get(frontages.size() - 1);
		// Finish the frontage if the touch point is near the first point of the
		// frontage (check if there is at least one point before)
		if ((lastFrontage.frontage.size() != 0)
				&& ((Math.abs(lastFrontage.frontage.get(0).x - point.x) < accuracy) && (Math
						.abs(lastFrontage.frontage.get(0).y - point.y) < accuracy))) {
			// Add the first point to complete the polygon
			lastFrontage.addPoint(lastFrontage.frontage.get(0));
			lastFrontage.finished = true;
		} else {
			// Add the point if the frontage is not finished
			lastFrontage.addPoint(point);
		}
	}

	/**
	 * Getter for frontages
	 * 
	 * @return frontages
	 */
	public Vector<Zone> getFrontages() {
		return this.frontages;
	}

	/**
	 * Add an empty zone to the list of zone (called when the user touch the add
	 * zone button)
	 */
	public void addEmpty() {
		this.frontages.add(new Zone());
	}

	/**
	 * This method return the position (in the list) of the first zone that
	 * contains the point in parameter and -1 if no zone is appropriate
	 * 
	 * @param point
	 * @return the number of the smallest zone that contains the point and -1
	 *         otherwise
	 */
	public int isInsideFrontage(Point point) {
		int result = -1;
		for (int i = 0; i < frontages.size(); i++) {
			if (frontages.get(i).containPoint(point)) {
				if (result == -1) {
					result = i;
				} else if (frontages.get(i).area() < frontages.get(result)
						.area()) {
					result = i;
				}
			}
		}
		return result;
	}

	/**
	 * Delete the zone in parameter from the SetOfZone
	 * 
	 * @param zone
	 *            the zone to delete
	 */
	public void delete(Zone zone) {
		frontages.remove(zone);
		this.deleteBalcony(zone);

		// Delete all the linked zones
		for (int i = 0; i < linkedZones.size(); i++) {
			if (linkedZones.get(i).contains(zone)) {
				for (int j = 0; j < linkedZones.get(i).size(); j++) {
					frontages.remove(linkedZones.get(i).get(j));
					this.deleteBalcony(zone);
				}
			}
		}
	}

	/**
	 * Unselect all the zones
	 */
	public void unselectAll() {
		for (int i = 0; i < frontages.size(); i++) {
			frontages.get(i).selected = false;
		}
	}

	/**
	 * Select the zone whose number is in parameter (if the number is positive)
	 * and all the linked zones
	 * 
	 * @param zoneNumber
	 *            the number of the zone to select
	 * @return true if a zone was selected and false otherwise (if the number in
	 *         parameter was negative)
	 */
	public boolean select(int zoneNumber) {
		if (zoneNumber >= 0) {
			frontages.get(zoneNumber).selected = true;

			// Select all the linked zones
			for (int i = 0; i < linkedZones.size(); i++) {
				if (linkedZones.get(i).contains(frontages.get(zoneNumber))) {
					for (int j = 0; j < linkedZones.get(i).size(); j++) {
						linkedZones.get(i).get(j).selected = true;
					}
				}
			}
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Return the (first) selected zone
	 * 
	 * @return the first selected zone
	 */
	public Zone getSelectedFrontage() {
		for (int i = 0; i < frontages.size(); i++) {
			if (frontages.get(i).selected) {
				return frontages.get(i);
			}
		}
		return null;
	}

	/**
	 * Return a vector with all the selected zones
	 * 
	 * @return vector with all the selected zones
	 */
	public Vector<Zone> getAllSelectedFrontages() {
		Vector<Zone> selectedZonesNumbers = new Vector<Zone>();
		for (int i = 0; i < frontages.size(); i++) {
			if (frontages.get(i).selected) {
				selectedZonesNumbers.add(frontages.get(i));
			}
		}
		return selectedZonesNumbers;
	}

	/**
	 * This method update the value of percentageOfGlazing ; It consider that
	 * the frontages in glass are inside another frontage (generally a windows
	 * inside a wall.
	 * 
	 * @param res
	 *            the resource
	 */
	public void updatePercentageOfGlazing(Resources res) {
		float frontageArea = 0; // Sum of the area of the frontages (without
								// those in glass)
		float glassArea = 0; // Sum of the area of the frontages in glass
		for (int i = 0; i < frontages.size(); i++) {
			// If the zone is a frontage
			if (frontages.get(i).type == res.getString(R.string.frontage)) {
				frontageArea = frontageArea + frontages.get(i).area();
				// If the frontage is in glass
				if (frontages.get(i).material == res.getString(R.string.glass)) {
					glassArea = glassArea + frontages.get(i).area();
					frontageArea = frontageArea - frontages.get(i).area();
				}
			}
		}

		// Update the value of percentageOfGlazing (if it is define)
		if (frontageArea > 0) {
			percentageOfGlazing = glassArea / frontageArea * 100;
		}
	}

	/**
	 * This method regroup several zones (add them to the linkedZones vector
	 * 
	 * @param linkedZone
	 *            the vector of zones to regroup
	 */
	public void regroup(Vector<Zone> linkedZone) {
		this.linkedZones.add(linkedZone);
	}

	/**
	 * This method divide the selected zones (it remove the link between the
	 * zones)
	 * 
	 * @param zone
	 *            one of the zone of the group of link zone to divide
	 */
	public void divide(Zone zone) {
		int i = 0;
		while (i < linkedZones.size()) {
			if (linkedZones.get(i).contains(zone)) {
				linkedZones.remove(i);
			} else {
				// Increment only in that case (otherwise when we deleted the
				// element at position i, the element in position i+1 changed
				// its position to i
				i++;
			}
		}
	}

	/**
	 * Delete the balcony corresponding to the zone in parameter
	 * 
	 * @param zone
	 *            the zone corresponding to the balcony to delete
	 * @return true if the balcony was found (and deleted); false otherwise
	 */
	public boolean deleteBalcony(Zone zone) {
		for (int i = 0; i < balconies.size(); i++) {
			if (balconies.get(i).zone == zone) {
				balconies.remove(i);
				return true;
			}
		}
		return false;
	}

	/**
	 * This method add a balcony if it does not already exists and updates it if
	 * it does; if several zone are in parameter, show an error message;
	 * 
	 * @param zones
	 *            the zone corresponding to the balcony
	 * @param estimatedHeight
	 *            the height of the balcony
	 * @param context
	 *            the context
	 */
	public void addBalcony(Vector<Zone> zones, int height, Context context) {
		// Check if the user try to had multiples balconies
		if (zones.size() != 1) {
			Toast toast = Toast.makeText(context,
					R.string.multiple_balconies_error, Toast.LENGTH_LONG);
			toast.show();
		} else {
			// Check if the balcony already exists
			boolean alreadyExisting = false;
			for (int i = 0; i < balconies.size(); i++) {
				if (balconies.get(i).zone == zones.get(0)) {
					// Modify the height of the existing balcony
					balconies.get(i).estimatedHeight = height;
					alreadyExisting = true;
				}
			}

			// Add the balcony (if not already existing)
			if (!alreadyExisting) {
				balconies.add(new Balcony(zones.get(0), height));
			}
		}
	}

	/**
	 * Get the balcony corresponding to the zone in parameter
	 * 
	 * @param zone
	 *            the zone
	 * @return the balcony corresponding to the zone
	 */
	public Balcony getBalcony(Zone zone) {
		for (int i = 0; i < balconies.size(); i++) {
			if (balconies.get(i).zone == zone) {
				return balconies.get(i);
			}
		}
		return null;
	}

	/**
	 * This method saves the work made on the picture by creating and filling an
	 * XML file in the same folder as the picture, with exactly the same name
	 * followed by ".xml"
	 * 
	 * @param absoluteFilePath
	 *            String
	 * @param context
	 *            the context
	 */
	public void save(String absoluteFilePath, Context context) {
		try {
			// Initialize XStream
			XStream xstream = new XStream();

			// Instantiation of the file
			File file = new File(absoluteFilePath + ".xml");

			// Instantiation of a file output stream
			FileOutputStream fos = new FileOutputStream(file);

			try {
				// Serialization of the SetOfZone
				xstream.toXML(this, fos);

				// Show a confirmation message
				Toast toast = Toast.makeText(context,R.string.file_saved,
						Toast.LENGTH_LONG);
				toast.show();
			} finally {
				// Closing the file output stream
				fos.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

	}

	/**
	 * This method reconstructs the SetOfZone from an XML file from a previously
	 * saved work
	 * 
	 * @param SetOfZones
	 *            zones
	 * @param absoluteFilePath
	 *            String
	 */
	public SetOfZone open(SetOfZone zones, String absoluteFilePath) {
		try {
			// Initialize XStream
			XStream xstream = new XStream();

			// Creating an File Input Stream in order to read the XML file
			FileInputStream fis = new FileInputStream(new File(absoluteFilePath
					+ ".xml"));

			try {
				// Deserialize the File Input Stream to a new SetOfFrontage
				// object named 'set'
				zones = (SetOfZone) xstream.fromXML(fis);

			} finally {
				// Closing the file input stream
				fis.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return zones;
	}

}
