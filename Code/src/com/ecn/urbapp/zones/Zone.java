package com.ecn.urbapp.zones;

import java.util.Arrays;
import java.util.Vector;

import android.graphics.Point;
import android.util.Log;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Polygon;

//TODO Check if it's possible to supress the commented code
public class Zone {
	/**
	 * List of the points composing the polygon; the last point is listed twice
	 */
	public Vector<Point> points;

	/** If the zone is selected or not */
	protected boolean selected;
	
	/** List of small points displayed between normal points in zone edition mode **/
	protected Vector<Point> middles;//useful for updateMiddles only, otherwise it's built everytime its getter is used

	/** JTS Polygon representation of the zone. */
	private Polygon poly;

	/** The geometry factory used to create geometries. */
	GeometryFactory gf = new GeometryFactory();

	/**
	 * Constructor of a new points (unfinished by default)
	 */
	public Zone() {
		points = new Vector<Point>();
		selected = false;
		middles = new Vector<Point>();
	}

	/**
	 * Set a zone coordinates from an other ones
	 * 
	 * @param zone
	 */
	public void setZone(Zone zone) {
		this.points = new Vector<Point>();
		for (Point p : zone.getPoints()) {
			points.add(new Point(p));
		}
		this.poly = zone.getPolygon();
	}

	/**
	 * Constructor of a zone by copying an other
	 *
	 * @param zone
	 */
	public Zone(Zone zone){
		this();
		Vector<Point> vectPoints = zone.getPoints();
		int nbrPoints = vectPoints.size();
		for (Point p : vectPoints) {
			points.add(new Point(p));
		}
		if (!vectPoints.get(0).equals(vectPoints.get(nbrPoints - 1))) {
			points.add(vectPoints.get(0));
		}
		actualizePolygon();
	}

	public void closePolygon() {
		if (!points.get(0).equals(points.get(points.size() - 1))) {
			points.add(points.get(0));
		}
	}

	/**
	 * Actualize the polygon representation of the zone from its list of points. 
	 */
	public void actualizePolygon() {
		int nbrPoints = points.size();
		Coordinate[] coordinates;
		if (points.get(0).equals(points.get(nbrPoints - 1))) {
			coordinates = new Coordinate[nbrPoints];
		} else {
			coordinates = new Coordinate[nbrPoints + 1];
		}
		LinearRing shell = null;
		LinearRing[] holes = null;
		int index;
		int startIndex = 0;
		int holeSize = - 1;
		for (index = 0; index < nbrPoints; index ++) {
			coordinates[index] = new Coordinate(points.get(index).x, points.get(index).y);
			if (index != startIndex && coordinates[index].equals2D(coordinates[startIndex])) {
				if (holeSize == -1) {
					shell = gf.createLinearRing(Arrays.copyOf(coordinates, index + 1));
					holeSize++;
				} else {
					if (holeSize == 0) {
						holes = new LinearRing[1];
					} else {
						holes = Arrays.copyOf(holes, holeSize);
					}
					holes[holeSize] = gf.createLinearRing(Arrays.copyOfRange(coordinates, startIndex, index + 1));
					holeSize++;
				}
				startIndex = index + 1;
			}
		}
		
		poly = gf.createPolygon(shell, holes);
		
	}

	//TODO Add description for javadoc
	public Vector<Point> getPoints(){
		return points;
	}

	//TODO Add description for javadoc
	 public Vector<Point> getMiddles(){
		buildMiddles();
		return middles;
	}

	 /**
	  * Return the jts-polygon representing the zone.
	  */
	 public Polygon getPolygon() {
		 return poly;
	 }
	
	/**
	 * Move a point to its new coordinates
	 * @param oldPoint
	 * @param newPoint
	 * @return
	 */
	public boolean updatePoint(Point oldPoint, Point newPoint){
		if(points.get(0).equals(oldPoint) || points.lastElement().equals(oldPoint)){
			try{
				points.setElementAt(newPoint,0);
				points.setElementAt(newPoint,points.size()-1);
				return true;
			}catch(Exception e){
				return false;
			}
		}
		else{
			try{
				points.setElementAt(newPoint,points.indexOf(oldPoint));
				return true;
			}catch(Exception e){
				return false;
			}
		}
	}
	
	/**
	 * Delete a point, rebuild list
	 * @param point
	 */
	public void deletePoint(Point point){
		points.remove(point);
	}
	
	/**
	 * Insert a point where a middle was
	 * @param oldMiddle
	 * @param newPoint
	 */
	public void updateMiddle(Point oldMiddle, Point newPoint){
		points.insertElementAt(newPoint, middles.indexOf(oldMiddle)+1);
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
	 * Add the point to the zone
	 * 
	 * @param point
	 *            point that will be added
	 */
	public void addPoint(Point point) {
		points.add(point);
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
		int j = points.size() - 1;
		boolean contain = false;

		for (int i = 0; i < points.size(); i++) {
			if (((points.get(i).y > point.y) != (points.get(j).y > point.y))
					&& (point.x < (points.get(j).x - points.get(i).x)
							* (point.y - points.get(i).y)
							/ (points.get(j).y - points.get(i).y)
							+ points.get(i).x)) {
				contain = (!contain);
			}
			j = i;
		}

		return contain;
	}

	/**
	 * Create edition points between normal 
	 */
	public void buildMiddles(){
		middles=new Vector<Point>();//points.size());
		if(points.size()>2){
			for(int i=0;i<points.size()-1; i++){
				middles.add(
					new Point(
						(points.get(i).x + points.get(i+1).x)/2,
						(points.get(i).y + points.get(i+1).y)/2)
				);
			}
		}
		if(points.size()>1){
			middles.add(
			new Point(
				(points.lastElement().x + points.get(0).x)/2,
				(points.lastElement().y + points.get(0).y)/2)
			);
		}
	}
	/**
	 * Check if zone's polygon is self intersecting, segment by segment.
	 * @return List of points involved in intersections, 4 points (2 segments) per intersection
	 */
	public Vector<Point> isSelfIntersecting(){
		Vector<Point> result = new Vector<Point>();
		points.add(points.get(0));//temporarily copying the first point at the end to avoid bounds' problems
		for(int i=0; i<points.size()-2 ; i++){
			for(int j=i+2; j<points.size()-1 ; j++){
				Log.d("Intersection : test","("+i+","+(i+1)+");("+j+","+(j+1)+")");
					if(intersect(points.get(i),points.get(i+1),points.get(j),points.get(j+1))){
						result.add(points.get(i));
						result.add(points.get(i+1));
						result.add(points.get(j));
						result.add(points.get(j+1));
						Log.d("Intersection","oui");
					}
				}
		}
		points.remove(points.size()-1);
		return result;//possibly null
	}
	
	/**
	 * Check if two segments, from 4 points, are intersecting.
	 * @param start1
	 * @param end1
	 * @param start2
	 * @param end2
	 * @return
	 */
	// méthode d'un forum Google
	private boolean intersect(Point start1,
			Point end1, Point start2, Point end2) {

			// First find Ax+By=C values for the two lines
			double A1 = end1.y - start1.y;
			double B1 = start1.x - end1.x;
			double C1 = A1 * start1.x + B1 * start1.y;

			double A2 = end2.y - start2.y;
			double B2 = start2.x - end2.x;
			double C2 = A2 * start2.x + B2 * start2.y;

			double det = (A1 * B2) - (A2 * B1);

			if (Math.abs(det) < 5) {
				// Lines are either parallel, are collinear (the exact same
				// segment), or are overlapping partially, but not fully
				// To see what the case is, check if the endpoints of one line
				// correctly satisfy the equation of the other (meaning the two
				// lines have the same y-intercept).
				// If no endpoints on 2nd line can be found on 1st, they are
				// parallel.
				// If any can be found, they are either the same segment,
				// overlapping, or two segments of the same line, separated by some
				// distance.
				// Remember that we know they share a slope, so there are no other
				// possibilities
	
				// Check if the segments lie on the same line
				// (No need to check both points)
				if ((A1 * start2.x) + (B1 * start2.y) == C1) {
					// They are on the same line, check if they are in the same
					// space
					// We only need to check one axis - the other will follow
					if ((Math.min(start1.x, end1.x) < start2.x)
							&& (Math.max(start1.x, end1.x) > start2.x)){
						return true;
					}
		
					// One end point is ok, now check the other
					if ((Math.min(start1.x, end1.x) < end2.x)
							&& (Math.max(start1.x, end1.x) > end2.x)){
						return true;
					}
		
					// They are on the same line, but there is distance between them
					return false;
				}
	
				// They are simply parallel
				return false;
			} else {
				// Lines DO intersect somewhere, but do the line segments intersect?
				double x = (B2 * C1 - B1 * C2) / det;
				double y = (A1 * C2 - A2 * C1) / det;
	
				// Make sure that the intersection is within the bounding box of
				// both segments
				if ((x > Math.min(start1.x, end1.x) && x < Math.max(start1.x,end1.x))
						&& (y > Math.min(start1.y, end1.y) && y < Math.max(	start1.y, end1.y))) {
					// We are within the bounding box of the first line segment,
					// so now check second line segment
					if ((x > Math.min(start2.x, end2.x) && x < Math.max(start2.x,end2.x))
							&& (y > Math.min(start2.y, end2.y) && y < Math.max(start2.y, end2.y))) {
						// The line segments do intersect
						return true;
					}
				}
	
				// The lines do intersect, but the line segments do not
				return false;
			}
		}
}
