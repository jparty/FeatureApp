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

import java.util.Vector;

import android.content.res.Resources;
import android.graphics.Point;
import com.ecn.urbapp.*;

/**
 * This class describe a zone (position, characteristics...)
 * 
 * @author patrick
 * 
 */
public class Zone {
	/**
	 * List of the points composing the polygon; the last point is listed twice
	 */
	public Vector<Point> frontage;

	/** The state of the zone (finished or unfinished) */
	protected boolean finished;

	/** Material of this zone */
	protected String material;

	/** If the zone is selected or not */
	protected boolean selected;

	/** Type of this zone */
	protected String type;

	/** Color of this zone */
	protected int color;

	/**
	 * Constructor of a new frontage (unfinished by default)
	 */
	public Zone() {
		frontage = new Vector<Point>();
		finished = false;
		selected = false;
	}

	/**
	 * Getter for the color
	 * 
	 * @return the color
	 */
	public int getColor() {
		return this.color;
	}

	/**
	 * Setter for the color
	 * 
	 * @param color
	 *            the new color
	 */
	public void setColor(int color) {
		this.color = color;
	}

	/**
	 * Set selected to true
	 */
	public void select() {
		this.selected = true;
	}

	/**
	 * Getter of selected
	 * 
	 * @return true if the zone is selected
	 */
	public boolean isSelected() {
		return this.selected;
	}
	
	/**
	 * Getter of finished
	 * @return true if the zone is finished
	 */
	public boolean isFinished(){
		return this.finished;
	}

	/**
	 * Return the type in text form (with written not defined if it is null)
	 * 
	 * @param res
	 *            the resource
	 * @return the type in text form
	 */
	public String getTypeToText(Resources res) {
		if (this.type == null) {
			return res.getString(R.string.not_defined);
		} else {
			return this.type;
		}
	}

	/**
	 * Return the material in text form (with written not defined if it is null)
	 * 
	 * @param res
	 *            the resource
	 * @return the material in text form
	 */
	public String getMaterialToText(Resources res) {
		if (this.material == null) {
			return res.getString(R.string.not_defined);
		} else {
			return this.material;
		}
	}

	/**
	 * Add the point to the zone
	 * 
	 * @param point
	 *            point that will be added
	 */
	public void addPoint(Point point) {
		frontage.add(point);
	}

	/**
	 * This method return true if the point in parameter is inside the polygon
	 * of the zone
	 * 
	 * @param point
	 *            point that need to be tested
	 * @return true if the point is inside the zone and false otherwise
	 */
	public boolean containPoint(Point point) {
		int j = frontage.size() - 1;
		boolean contain = false;

		for (int i = 0; i < frontage.size(); i++) {
			if (((frontage.get(i).y > point.y) != (frontage.get(j).y > point.y))
					&& (point.x < (frontage.get(j).x - frontage.get(i).x)
							* (point.y - frontage.get(i).y)
							/ (frontage.get(j).y - frontage.get(i).y)
							+ frontage.get(i).x)) {
				contain = (!contain);
			}
			j = i;
		}

		return contain;
	}

	/**
	 * The method return the area of the zone in pixels*pixels
	 * 
	 * @return the area
	 */
	public float area() {
		float result = 0;
		for (int i = 0; i < frontage.size() - 1; i++) {
			result = result + frontage.get(i).x * frontage.get(i + 1).y
					- frontage.get(i + 1).x * frontage.get(i).y;
		}

		result = (float) (0.5 * Math.abs(result));

		return result;
	}
}
