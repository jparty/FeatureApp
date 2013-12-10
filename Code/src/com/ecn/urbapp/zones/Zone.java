package com.ecn.urbapp.zones;

import java.util.Vector;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.util.Log;

import com.ecn.urbapp.R;

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

	/** Material of this zone */
	protected String material;

	/** If the zone is selected or not */
	protected boolean selected;

	/** Type of this zone */
	protected String type;

	/** Color of this zone */
	protected int color;
	
	/** List of small points displayed between normal points in zone edition mode **/
	protected Vector<Point> middles;//useful for updateMiddles only, otherwise it's built everytime its getter is used

	/** JTS Polygon representation of the zone. */
	private Polygon poly;

	/** The geometry factory used to create geometries. */
	GeometryFactory gf = new GeometryFactory();

	/** Zone list of last actions of creation or edition**/
	private Vector<Action> actions;

	/**
	 * Constructor of a new points (unfinished by default)
	 */
	public Zone() {
		points = new Vector<Point>();
		selected = false;
		middles = new Vector<Point>();
		color = Color.RED;
		actions = new Vector<Action>();
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
		Coordinate[] coordinates;
		if (vectPoints.get(0).equals(vectPoints.get(nbrPoints - 1))) {
			coordinates = new Coordinate[nbrPoints];
		} else {
			coordinates = new Coordinate[nbrPoints + 1];
		}
		int i = 0;
		for (Point p : vectPoints) {
			points.add(new Point(p));
			coordinates[i] = new Coordinate(p.x, p.y);
			i++;
		}
		if (!vectPoints.get(0).equals(vectPoints.get(nbrPoints - 1))) {
			coordinates[nbrPoints] = coordinates[0];
			points.add(vectPoints.get(0));
		}
		LinearRing lr = gf.createLinearRing(coordinates);
		if ((coordinates[0].x - coordinates[1].x) * (coordinates[2].y - coordinates[0].y)
				-  (coordinates[0].y - coordinates[1].y) * (coordinates[2].x - coordinates[0].x) > 0) {
			lr = gf.createLinearRing(lr.reverse().getCoordinates());
		}
		poly = gf.createPolygon(lr, null);
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
	 * 
	 */
	
	public void startMove(Point oldPoint){
		actions.add(new Action().startMove(oldPoint));
	}
	
	/**
	 * Declare moving action
	 * @param oldPoint 
	 * @param newPoint
	 */
	public void endMove(Point newPoint){
		actions.lastElement().endMove(newPoint);
	}
	
	/** 
	 * Insert a point at a chosen location
	 * @param point
	 * @param location
	 */
	public void insertPoint(int location, Point point){
		points.add(location, point);
	}
	
	/**
	 * Delete a point, rebuild list
	 * @param point
	 */
	public boolean deletePoint(Point point){
		Exception exc = new Exception();
		String className = ((exc.getStackTrace())[1]).getClassName();
		Class calling = null;
		try {
			calling = Class.forName(className);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(calling != Action.class){
			actions.add(new Action().delete(point));
		}
		if(points.get(0).equals(point) || points.lastElement().equals(point)){
			points.remove(0);
			points.remove(points.lastElement());
			if(points.size()>0){
				points.add(points.get(0));
			}
			return true;
		}
		else{
			return points.remove(point);
		}
	}

	/**
	 * Delete a point, rebuild list
	 * @param point
	 */
	public boolean deletePoint(int index){
		return deletePoint(points.get(index));
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
	 * Setter for the type
	 * 
	 * @param type
	 *            the new type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Setter for the material
	 * 
	 * @param material
	 *            the new material
	 */
	public void setMaterial(String material) {
		this.material = material;
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
		points.add(point);
	}
	
	public void addPoint2(Point point) {
		Exception exc = new Exception();
		String className = ((exc.getStackTrace())[1]).getClassName();
		Class calling = null;
		try {
			calling = Class.forName(className);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(calling != Action.class){
			actions.add(new Action().create(point));
		}
	if(points.size()==0){
		points.add(point);
		points.add(point);
	}else{
		points.add(points.size()-1, point);
	}
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
		/*
		if(points.size()>1){
			middles.add(
			new Point(
				(points.lastElement().x + points.get(0).x)/2,
				(points.lastElement().y + points.get(0).y)/2)
			);
		}
		*/
	}
	/**
	 * Check if zone's polygon is self intersecting, segment by segment.
	 * @return List of points involved in intersections, 4 points (2 segments) per intersection
	 */
	public Vector<Point> isSelfIntersecting(){
		Vector<Point> result = new Vector<Point>();
		//points.add(points.get(0));//temporarily copying the first point at the end to avoid bounds' problems
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
		//points.remove(points.size()-1);
		return result;//possibly null
	}
	
	/**
	 * Check if two segments, from 4 points, are intersecting.
	 * @param start1
	 * @param end1
	 * @param start2
	 * @param end2
	 * @return intersection flag
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
	
	/***INNER CLASS ACTION***/
	/** An action corresponds to a point's creation, edition or deletion **/
	private class Action{
		private final int CREATE = 1;
		private final int DELETE = 2;
		private final int MOVE = 3;
		
		/** Action type : creation, deletion or move **/
		private int action;
		
		/** Old point - previous location of a point, in case of deletion or move. If null in move means that action is an insertion **/ 
		private Point oldPoint;
		
		/** New Point - new location of a point, in case of creation or move **/
		private Point newPoint;
		
		/** Old Location - where was the point in the list in case of deletion **/
		private int oldLocation;
		
		/** Constructor of an action. Does nothing, because of the differences between actions types. TODO Code class extensions instead of types ?**/
		public Action(){
		}
		
		/** 
		 * ACTION INNER CLASS : Create action, called for each point creation 
		 * @param newPoint (the point created)
		 * @return Action (in fact this method is like a constructor)
		 */
		public Action create(Point newPoint){
			this.action = CREATE;
			this.newPoint = new Point(newPoint);
			return this;
		}
		
		/**
		 * ACTION INNER CLASS : Delete action, called for each point deletion
		 * @param oldPoint (the point deleted)
		 * @return Action (in fact this method is like a constructor)
		 */
		public Action delete(Point oldPoint){
			this.action = DELETE;
			this.oldPoint = new Point(oldPoint);
			this.oldLocation = points.indexOf(oldPoint);
			return this;
		}
		
		/**
		 * ACTION INNER CLASS : Move action, begin call. Save the point moved, new location entered in end call.
		 * !! It's possible that the end call never occurs !! See cancel() method.
		 * !! It's possible that the moved point is null in middle point case.
		 * @param oldPoint
		 * @return Action (in fact this method is like a constructor)
		 */
		public Action startMove(Point oldPoint){
			this.action = MOVE;
			if(oldPoint != null){
				this.oldPoint = new Point(oldPoint);
			}else{
				this.oldPoint = null;
			}
			this.newPoint = null;
			return this;
		}
		
		/**
		 * ACTION INNER CLASS : Move action, end call. Save the point moved new location, old one entered in begin call.
		 * !! It's possible that begin call occurs and not end
		 * @param newPoint
		 */
		public void endMove(Point newPoint){
			this.newPoint = new Point(newPoint);
		}
		
		/** 
		 * ACTION INNER CLASS : Canceling action method
		 */
		public void cancel(){
			switch(action){
			case CREATE:
				//create case : just delete the point
				deletePoint(newPoint);
				break;
			case DELETE:
				//delete case : insert the ancient point at its ancient location in points' list
				insertPoint(oldLocation,oldPoint);
				break;
			case MOVE:
				// move case : depends on the point original type (middle or not) and if end call occured
				if(oldPoint == null){
					//if there was no old point (middle point case) the point is just deleted
					deletePoint(newPoint);
					break;
				}
				if(newPoint != null){
					//if there was an end call, replace the new point by the old one
					updatePoint(newPoint, oldPoint);
				}
				//if there was no end call (select point case) : do nothing and wait for action deletion TODO better dealing with point selection
				break;
				}		
		}
	}
	/*** END OF INNER CLASS ACTION ***/
	
	/**
	 * Zone back method. Cancel, then remove last action from actions list, if exists.
	 * @return execution flag
	 */
	public boolean back(){
		if(actions.size()>0){
			actions.lastElement().cancel();
			actions.remove(actions.lastElement());
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Actions list clearing method
	 */
	public void clearBacks(){
		actions = new Vector<Action>();
	}
}
