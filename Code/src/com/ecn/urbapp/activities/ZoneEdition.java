package com.ecn.urbapp.activities;

import android.R;
import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.ecn.urbapp.zones.Zone;

public class ZoneEdition extends Activity{
	protected Zone zone;
	protected ImageView myImage;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.layout_zone, null);
		//image = (ImageView) findViewById(R.id.image);
		
	}
	
	public static void createZone(){
		//myImage.setOnTouchListener(
		new View.OnTouchListener(){
			public boolean onTouch(View v, MotionEvent event){
				//if(event.getAction() == MotionEvent.ACTION_DOWN){	
					//Point point = new Point((int)event.getX(),(int)event.getY());
					// TODO vérifier la validité (pas de polygone croisé) -> intersect
					//zone.addPoint(point);
					// TODO vérifier si le tour est fait
					//myImage.invalidate(); //ask to draw TODO draw function !
					Log.d("UrbApp","touché !");
					//return true;
				//}
				return false;
			}
		};
	}
	
	//TODO delete
	//TODO edit
	
	public boolean intersect(Point p1, Point p2, Point q1, Point q2){
		//méthode : le parcours A->B->C->D doit être plus court que A->C->B->D
		float ap = p2.y/(p2.x-p1.x); float bp = p1.y;
		float aq = q2.y/(q2.x-q1.x); float bq = q1.y;
		float x = (bq - bp)/(ap - aq);
		if(x>p1.x && p2.x>x){
			return true;
		}
		else {
			return false;
		}
	}

}


