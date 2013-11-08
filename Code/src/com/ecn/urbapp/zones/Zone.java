package com.ecn.urbapp.zones;

import java.util.Vector;

import android.graphics.Point;
import android.graphics.PointF;

public class Zone {
	protected Vector<PointF> points;
	protected Vector<PointF> middles;//useful for updateMiddles
	
	public Zone(){
		points = new Vector<PointF>();
		middles = new Vector<PointF>();
	}
	
	public Vector<PointF> getPoints(){
		return points;
	}
	
	 public Vector<PointF> getMiddles(){
		buildMiddles();
		return middles;
	}
	
	public void addPoint(PointF point){
		points.add(point);
	}
	
	public boolean updatePoint(PointF oldPoint, PointF newPoint){
		try{
			points.setElementAt(newPoint,points.indexOf(oldPoint));
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	public void deletePoint(PointF point){
		points.remove(point);
	}
	
	public void updateMiddle(PointF oldMiddle, PointF newPoint){
		points.insertElementAt(newPoint, middles.indexOf(oldMiddle)+1);
	}
	
	public void setZone(Zone zone){
		this.points = (Vector<PointF>) zone.points.clone();
	}
	
	public boolean intersect(Point p1, Point p2, Point q1, Point q2){
		//TODO verifier intersection
		return false;
	}
	
	public boolean containPointF(PointF point) {
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
		middles=new Vector<PointF>();//points.size());
		if(points.size()>2){
			for(int i=0;i<points.size()-1; i++){
				middles.add(
					new PointF(
						(points.get(i).x + points.get(i+1).x)/2,
						(points.get(i).y + points.get(i+1).y)/2)
				);
			}
		}
		if(points.size()>1){
			middles.add(
			new PointF(
				(points.lastElement().x + points.get(0).x)/2,
				(points.lastElement().y + points.get(0).y)/2)
			);
		}
	}
	
	
}
