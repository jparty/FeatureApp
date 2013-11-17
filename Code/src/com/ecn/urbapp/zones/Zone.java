package com.ecn.urbapp.zones;

import java.util.Vector;

import android.graphics.Point;
import android.util.Log;

public class Zone {
	protected Vector<Point> points;
	protected Vector<Point> middles;//useful for updateMiddles
	
	public Zone(){
		points = new Vector<Point>();
		middles = new Vector<Point>();
	}
	
	public Zone(Zone zone){
		this.setZone(zone);
	}
	
	public Vector<Point> getPoints(){
		return points;
	}
	
	 public Vector<Point> getMiddles(){
		buildMiddles();
		return middles;
	}
	
	public void addPoint(Point point){
		points.add(point);
	}
	
	public boolean updatePoint(Point oldPoint, Point newPoint){
		try{
			points.setElementAt(newPoint,points.indexOf(oldPoint));
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	public void deletePoint(Point point){
		points.remove(point);
	}
	
	public void updateMiddle(Point oldMiddle, Point newPoint){
		points.insertElementAt(newPoint, middles.indexOf(oldMiddle)+1);
	}
	
	public void setZone(Zone zone){
		this.points = (Vector<Point>) zone.points.clone();
	}
	
	public boolean containPoint(Point point) {
		//de FeatureApp
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
	
	/*public Vector<Point> isSelfIntersecting(){
		Vector<Point> result = new Vector<Point>();
		points.add(points.get(0));//temporarily copying the first point at the end to avoid bounds' problems
		for(int i=0; i<points.size()-1 ; i++){
			for(int j=0; j<points.size()-1 ; j++){
				if((j<i-1 || j>i+1) && (i!=points.size()-2 || j!=0) && !(i!=0 || j!=points.size()-2)){
					if(intersect(points.get(i),points.get(i+1),points.get(j),points.get(j+1))){
						result.add(points.get(i));
						result.add(points.get(i+1));
						result.add(points.get(j));
						result.add(points.get(j+1));
						Log.d("Intersection","("+i+","+(i+1)+");("+j+","+(j+1)+")");
					}
				}
			}
		}
		points.remove(points.size()-1);
		return result;//possibly null
	}*/
	
	public Vector<Point> isSelfIntersecting(){
		Vector<Point> result = new Vector<Point>();
		for(int i=0; i<points.size()-3 ; i++){
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
			Log.d("Intersection","("+i+","+(i+1)+");("+(points.size()-1)+","+0+")");
			if(intersect(points.get(i+1),points.get(i+2),points.get(points.size()-1),points.get(0))){
				result.add(points.get(i));
				result.add(points.get(i+1));
				result.add(points.get(points.size()-1));
				result.add(points.get(0));
				Log.d("Intersection","oui");
			}
		}
		return result;//possibly null
	}
	
	 //méthode de Jules
	 /* public boolean intersect(Point p11, Point p12, Point p21, Point p22){
		double denominateur = (p22.x - p21.x) * (p12.y - p11.y) - (p12.x - p11.x) * (p22.y - p21.y);
		Log.d("Intersect","den:"+denominateur);
		if (denominateur != 0.0) {
			Log.d("Intersect","droites non //");
			double x;
			double y;
			if (p22.x != p21.x) {
				// Cas où les droites sont sécantes (pas forcément les segments !
				x = (-(p12.x - p11.x) * (p22.x - p21.x) * (p21.y - p11.y)
					+ p11.x * (p22.x - p21.x) * (p12.y - p11.y)
					- p21.x * (p12.x - p11.x) * (p22.y - p21.y))
					/ denominateur;
				y = p21.y - (p22.y - p21.y) * (x - p21.x)
					/ (p22.x - p21.x);
			} else {
				x = p21.x;
				y = p11.y - (p12.y - p11.y) * (x - p11.x) / (p12.x - p11.x);
			}
			return isInSegment(x, y, p11, p12)
			&& isInSegment(x, y, p21, p22);
		} else {
			Log.d("intersectest","droites oui //");
			//TODO Cas où les droites sont parallèles
			return isInSegment(p11.x, p11.y, p21, p22)
			|| isInSegment(p12.x, p12.y, p21, p22)
			|| isInSegment(p21.x, p21.y, p11, p12)
			|| isInSegment(p22.x, p22.y, p11, p12);
		}
	}*/
	
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

			if (Math.abs(det) < 5) {//epsilon=5, but ideally ==0
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


	/*
	public static boolean isInSegment(double x, double y, Point p1, Point p2) {
		return ((p2.x - p1.x) * (y - p1.y) + (p2.y - p1.y) * (x - p1.x) == 0
		&& (Math.min(p1.x, p2.x) <= x) && (Math.max(p1.x, p2.x) >= x)
		&& (Math.min(p1.y, p2.y) <= y) && (Math.max(p1.y, p2.y) >= y));
}*/
	
}
