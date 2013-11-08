package com.ecn.urbapp.fragments;

import java.io.File;
import java.util.Vector;

import android.app.Fragment;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.ecn.urbapp.R;
import com.ecn.urbapp.zones.BitmapLoader;
import com.ecn.urbapp.zones.DrawZoneView;
import com.ecn.urbapp.zones.Zone;

/**
 * @author	COHENDET Sébastien
 * 			DAVID Nicolas
 * 			GUILBART Gabriel
 * 			PALOMINOS Sylvain
 * 			PARTY Jules
 * 			RAMBEAU Merwan
 * 
 * ZoneFragment class
 * 
 * This is the fragment used to define the different zones.
 * 			
 */

public class ZoneFragment extends Fragment{
	
	private Button create;
	private Button edit;
	private Button delete;

	private Button create_back;
	private Button create_help;
	private Button create_cancel;
	private Button create_validate;
	

	private Button delete_cancel;
	private Button delete_help;

	private Button edit_cancel;
	private Button edit_validate;
	private Button edit_deletePoint;
	private Button edit_releasePoint;
	private Button edit_help;
	

	public ImageView myImage; public File photo;
	public Vector<Zone> zones; public Zone zoneToDelete ;
	private Zone zone;
	private PointF selected;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.layout_zone, null);

		create = (Button) v.findViewById(R.id.zone_button_create);
		create.setOnClickListener(createListener);
		edit = (Button) v.findViewById(R.id.zone_button_edit);
		edit.setOnClickListener(editListener);
		delete = (Button) v.findViewById(R.id.zone_button_delete);
		delete.setOnClickListener(deleteListener);

		create_back = (Button) v.findViewById(R.id.zone_create_button_back);
		create_help = (Button) v.findViewById(R.id.zone_create_button_help);
		create_cancel = (Button) v.findViewById(R.id.zone_create_button_cancel);
		create_validate = (Button) v.findViewById(R.id.zone_create_button_validate);
		
		create_back.setVisibility(View.GONE);
		create_help.setVisibility(View.GONE);
		create_cancel.setVisibility(View.GONE);
		create_validate.setVisibility(View.GONE);

		create_back.setOnClickListener(createBackListener);
		create_cancel.setOnClickListener(createCancelListener);
		create_validate.setOnClickListener(createValidateListener);

		delete_cancel = (Button) v.findViewById(R.id.zone_delete_button_cancel);
		delete_help = (Button) v.findViewById(R.id.zone_delete_button_help);

		delete_cancel.setVisibility(View.GONE);
		delete_help.setVisibility(View.GONE);
		
		delete_cancel.setOnClickListener(deleteCancelListener);

		edit_cancel = (Button) v.findViewById(R.id.zone_edit_button_cancel);
		edit_validate = (Button) v.findViewById(R.id.zone_edit_button_validate);
		edit_deletePoint = (Button) v.findViewById(R.id.zone_edit_button_delete_point);
		edit_releasePoint = (Button) v.findViewById(R.id.zone_edit_button_release_point);
		edit_help = (Button) v.findViewById(R.id.zone_edit_button_help);

		edit_cancel.setVisibility(View.GONE);
		edit_validate.setVisibility(View.GONE);
		edit_deletePoint.setVisibility(View.GONE);
		edit_releasePoint.setVisibility(View.GONE);
		edit_help.setVisibility(View.GONE);

		edit_cancel.setOnClickListener(editCancelListener);
		edit_validate.setOnClickListener(editValidateListener);
		edit_deletePoint.setOnClickListener(editDeletePointListener);
		edit_releasePoint.setOnClickListener(editReleasePointListener);

		zones = new Vector<Zone>();
		
		return v;
	}
	
	/** Declarations for create zone **/
	private OnClickListener createListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            create.setVisibility(View.GONE);
            edit.setVisibility(View.GONE);
            delete.setVisibility(View.GONE);

    		create_back.setVisibility(View.VISIBLE);
    		create_help.setVisibility(View.VISIBLE);
    		create_cancel.setVisibility(View.VISIBLE);
    		create_validate.setVisibility(View.VISIBLE);
    		
    		//Log.d("UrbApp","Créer zone");
    		zone = new Zone();
    		//setContentView(R.layout.layout_zone_create);
    		
    		//TODO mettre par défaut dans le layout ?
    		getView().findViewById(R.id.zone_create_button_validate).setEnabled(false);
    		getView().findViewById(R.id.zone_create_button_back).setEnabled(false);
    		
    		//Image drawing plus its zones
    		myImage = (ImageView) getView().findViewById(R.id.image_zone);

    		/** ATTENTION ATTENTION !!!
    		 *  PLACER UN FICHIER Images.jpeg dans le Download de l'appareil
    		 */
    		String youFilePath = Environment.getExternalStorageDirectory().toString()+"/Download/Images.jpeg";
    		photo=new File(youFilePath);
    	
    		Drawable[] drawables = {
    			new BitmapDrawable(
    				getResources(),
    				BitmapLoader.decodeSampledBitmapFromFile(
    					photo.getAbsolutePath(), 1000, 1000)), 
    				new DrawZoneView(zones, zone) };
    		//TODO Problem for the displaying of the image
    		myImage.setImageDrawable(new LayerDrawable(drawables));
    		
    		//Button back action
    		//TODO implémenter la même chose avec le back générique Android
    		myImage.setOnTouchListener(imageTouchListener);
        }
	};
    private OnClickListener createBackListener = new View.OnClickListener() {
    	@Override
		public void onClick(View v) {
			zone.getPoints().remove(zone.getPoints().lastElement());
			refreshCreate(zone);
		}
    };
    private OnClickListener createValidateListener = new View.OnClickListener() {			
		@Override
		public void onClick(View v) {
			zones.add(zone);
            create.setVisibility(View.VISIBLE);
            edit.setVisibility(View.VISIBLE);
            delete.setVisibility(View.VISIBLE);

    		create_back.setVisibility(View.GONE);
    		create_help.setVisibility(View.GONE);
    		create_cancel.setVisibility(View.GONE);
    		create_validate.setVisibility(View.GONE);
		}
	};
	private OnClickListener createCancelListener = new View.OnClickListener() {			
		@Override
		public void onClick(View v) {
            create.setVisibility(View.VISIBLE);
            edit.setVisibility(View.VISIBLE);
            delete.setVisibility(View.VISIBLE);

    		create_back.setVisibility(View.GONE);
    		create_help.setVisibility(View.GONE);
    		create_cancel.setVisibility(View.GONE);
    		create_validate.setVisibility(View.GONE);
		}
	};
	private OnTouchListener imageTouchListener = new ImageView.OnTouchListener() {
		protected Matrix matrix;
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				Log.d("UrbApp","Avant x:"+event.getX()+";y:"+event.getY());
				float[] coord = {event.getX(),event.getY()};//get touched point coord
				if (matrix == null) {//create matrix that transforms point coord into image base 
					matrix = new Matrix();
					((ImageView) getView().findViewById(R.id.image_zone)).getImageMatrix().invert(matrix);
					Log.d("Matrix",matrix.toString());
				}
				matrix.mapPoints(coord);//apply matrix transformation on points coord
				Log.d("UrbApp","Après x:"+coord[0]+";y:"+coord[1]);
				zone.addPoint(new PointF(coord[0],coord[1]));
				refreshCreate(zone);//display new point, refresh buttons' availabilities					
			}
			return true;
		}
	};
    public void refreshCreate(Zone zone){
		Vector<PointF> points = zone.getPoints();
		if(! points.isEmpty()){
			getView().findViewById(R.id.zone_create_button_back).setEnabled(false);
			if(points.size()>1){							
				getView().findViewById(R.id.zone_create_button_validate).setEnabled(false);
				getView().findViewById(R.id.zone_create_button_back).setEnabled(true);
				if(points.size()>2){
					getView().findViewById(R.id.zone_create_button_validate).setEnabled(true);
				}
			}
		}
		myImage.invalidate();
	}
    
    
    /***** zone edition main *****/
    private OnClickListener editListener = new View.OnClickListener(){
    	@Override
        public void onClick(View v) {
    		
            create.setVisibility(View.GONE);
            edit.setVisibility(View.GONE);
            delete.setVisibility(View.GONE);

    		edit_cancel.setVisibility(View.VISIBLE);
    		edit_validate.setVisibility(View.VISIBLE);
    		edit_deletePoint.setVisibility(View.VISIBLE);
    		edit_releasePoint.setVisibility(View.VISIBLE);
    		edit_help.setVisibility(View.VISIBLE);
            
    		Log.d("UrbApp","Créer zone");
    		zone = new Zone();
    		selected = new PointF();
    		//setContentView(R.layout.layout_zone_edit);
    		
    		//TODO inclure par défaut dans le layout ?
    		getView().findViewById(R.id.zone_edit_button_delete_point).setEnabled(false);
    		getView().findViewById(R.id.zone_edit_button_release_point).setEnabled(false);
    		
    		//display image plus its zones
    		//TODO synergie affichages !
    		myImage = (ImageView) getView().findViewById(R.id.image_zone);

    		String youFilePath = Environment.getExternalStorageDirectory().toString()+"/Download/Images.jpeg";
    		photo=new File(youFilePath);
    	
    		Drawable[] drawables = {
    			new BitmapDrawable(
    				getResources(),
    				BitmapLoader.decodeSampledBitmapFromFile(
    					photo.getAbsolutePath(), 1000, 1000)), 
    				new DrawZoneView(zones, zone, selected) };
    		myImage.setImageDrawable(new LayerDrawable(drawables));
    			
    		//image touched
    		myImage.setOnTouchListener(imageEditTouchListener); 
    	}
    };
    
    private ImageView.OnTouchListener imageEditTouchListener = new ImageView.OnTouchListener() {
		protected Matrix matrix;
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				Log.d("UrbApp","Avant x:"+event.getX()+";y:"+event.getY());
				float[] coord = {event.getX(),event.getY()};//get touched point coord
				//TODO synergie création matrice, récupération de coordonnées !
				if (matrix == null) {
					matrix = new Matrix();
					((ImageView) getView().findViewById(R.id.image_zone)).getImageMatrix()
							.invert(matrix);
					Log.d("Matrix",matrix.toString());
				}
				matrix.mapPoints(coord);
				Log.d("UrbApp","Après x:"+coord[0]+";y:"+coord[1]);
				PointF touch = new PointF(coord[0],coord[1]);
				
				//If no zone has been selected, try to select one
				if(zone.getPoints().isEmpty()){
					for(Zone test : zones){
						if(test.containPointF(touch)){
							zone.setZone(test);
							zoneToDelete = test;
						}
					}
				}
				else{
					//If a zone is selected, and one of its points was, it's a point move
					if(selected.x != 0 && selected.y != 0){
						if (! zone.updatePoint(selected, touch)){//Is it a normal point ?
							zone.updateMiddle(selected, touch);	//If not it's a "middle" point, and it's upgraded to normal
						}
						selected.set(0, 0);//No selected point anymore
						//Disable point delete or release actions 
						getView().findViewById(R.id.zone_edit_button_delete_point).setEnabled(false);
						getView().findViewById(R.id.zone_edit_button_release_point).setEnabled(false);
					}
					//If a zone is selected, but none of its points, is the touch point a normal or middle point ?
					else{
						for(PointF p : zone.getPoints()){//is the touched point a normal point ?
							float dx=Math.abs(p.x-touch.x);
							float dy=Math.abs(p.y-touch.y);
							if((dx*dx+dy*dy)<10*10){//10 radius tolerance
								selected.set(p);
								//enable point deleting or releasing
								getView().findViewById(R.id.zone_edit_button_delete_point).setEnabled(true);
								getView().findViewById(R.id.zone_edit_button_release_point).setEnabled(true);
							}
						}
						if(selected.x == 0 && selected.y == 0){//is the touched point a middle point ?
							for(PointF p : zone.getMiddles()){
								float dx=Math.abs(p.x-touch.x);
								float dy=Math.abs(p.y-touch.y);
								if((dx*dx+dy*dy)<10*10){
									selected.set(p);
									//impossible to delete a middle point, but enable releasing
									getView().findViewById(R.id.zone_edit_button_release_point).setEnabled(true);
								}
							}
						}
					}							
				}
				myImage.invalidate();//refresh points' displaying
				
			}
			return true;
		}
	};
	private OnClickListener editValidateListener = new View.OnClickListener() {			
		@Override
		public void onClick(View v) {
			zones.remove(zoneToDelete);//delete original  
			zones.add(zone);//save edited
			
            create.setVisibility(View.VISIBLE);
            edit.setVisibility(View.VISIBLE);
            delete.setVisibility(View.VISIBLE);
			
			edit_cancel.setVisibility(View.GONE);
			edit_validate.setVisibility(View.GONE);
			edit_deletePoint.setVisibility(View.GONE);
			edit_releasePoint.setVisibility(View.GONE);
			edit_help.setVisibility(View.GONE);
		}
	};
	private OnClickListener editCancelListener = new View.OnClickListener() {			
		@Override
		public void onClick(View v) {

            create.setVisibility(View.VISIBLE);
            edit.setVisibility(View.VISIBLE);
            delete.setVisibility(View.VISIBLE);
			
			edit_cancel.setVisibility(View.GONE);
			edit_validate.setVisibility(View.GONE);
			edit_deletePoint.setVisibility(View.GONE);
			edit_releasePoint.setVisibility(View.GONE);
			edit_help.setVisibility(View.GONE);
		}
	};
	private OnClickListener editDeletePointListener = new View.OnClickListener() {			
		@Override
		public void onClick(View v) {
			zone.deletePoint(selected);
			selected.set(new PointF(0,0));//no selected point anymore
			getView().findViewById(R.id.zone_edit_button_delete_point).setEnabled(false);
			getView().findViewById(R.id.zone_edit_button_release_point).setEnabled(false);
			myImage.invalidate();//refresh image
		}
	};
	private OnClickListener editReleasePointListener = new View.OnClickListener() {			
		@Override
		public void onClick(View v) {
			selected.set(new PointF(0,0));
			getView().findViewById(R.id.zone_edit_button_delete_point).setEnabled(false);
			getView().findViewById(R.id.zone_edit_button_release_point).setEnabled(false);
			myImage.invalidate();
		}
	};
	
	
	private OnClickListener deleteListener = new View.OnClickListener() {			
		@Override
		public void onClick(View v) {
			Log.d("UrbApp","Créer zone");
			final Zone zone = new Zone();
			final PointF selected = new PointF();
			
            create.setVisibility(View.GONE);
            edit.setVisibility(View.GONE);
            delete.setVisibility(View.GONE);

    		delete_cancel.setVisibility(View.VISIBLE);
    		delete_help.setVisibility(View.VISIBLE);
			
			myImage = (ImageView) getView().findViewById(R.id.image_zone);

			String youFilePath = Environment.getExternalStorageDirectory().toString()+"/Download/Images.jpeg";
			photo=new File(youFilePath);
		
			Drawable[] drawables = {
				new BitmapDrawable(
					getResources(),
					BitmapLoader.decodeSampledBitmapFromFile(
						photo.getAbsolutePath(), 1000, 1000)), 
					new DrawZoneView(zones, zone, selected) };
			myImage.setImageDrawable(new LayerDrawable(drawables));
			myImage.setOnTouchListener(deleteImageTouchListener);
		}
	};
	
	private OnTouchListener deleteImageTouchListener = new ImageView.OnTouchListener() {
		protected Matrix matrix;
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				Log.d("UrbApp","Avant x:"+event.getX()+";y:"+event.getY());
				float[] coord = {event.getX(),event.getY()};
				if (matrix == null) {
					matrix = new Matrix();
					((ImageView) getView().findViewById(R.id.image_zone)).getImageMatrix()
							.invert(matrix);
					Log.d("Matrix",matrix.toString());
				}
				matrix.mapPoints(coord);
				Log.d("UrbApp","Après x:"+coord[0]+";y:"+coord[1]);
				PointF touch = new PointF(coord[0],coord[1]);
				if(zone.getPoints().isEmpty()){
					for(Zone test : zones){
						if(test.containPointF(touch)){
							zoneToDelete = test;
						}
					}
					if(zoneToDelete != null){
						zones.remove(zoneToDelete);
						
			            create.setVisibility(View.VISIBLE);
			            edit.setVisibility(View.VISIBLE);
			            delete.setVisibility(View.VISIBLE);

			    		delete_cancel.setVisibility(View.GONE);
			    		delete_help.setVisibility(View.GONE);
					}
				}
			}
			return true;
		}
	};
	private OnClickListener deleteCancelListener = new View.OnClickListener() {			
		@Override
		public void onClick(View v) {
            create.setVisibility(View.VISIBLE);
            edit.setVisibility(View.VISIBLE);
            delete.setVisibility(View.VISIBLE);

    		delete_cancel.setVisibility(View.GONE);
    		delete_help.setVisibility(View.GONE);
		}
	};
	
	/*create actions*//*
	public void zone_create_button_back (View view){//appelée par le xml onclick
		Log.d("UrbApp","Annuler action");
	}
	public void zone_create_button_cancel (View view){//appelée par le xml onclick
		Log.d("UrbApp","Annuler création");
		setContentView(R.layout.layout_zone);
	}
	public void zone_create_button_validate (View view){//appelée par le xml onclick
		Log.d("UrbApp","Valider");
	}
	public void zone_create_button_help (View view){//appelée par le xml onclick
		Log.d("UrbApp","Help !");
	}*/
	
	public boolean intersect(PointF a, PointF b, PointF c, PointF d){
		//TODO check polygon intersections
		//voir JTS
		return false;
	}
}