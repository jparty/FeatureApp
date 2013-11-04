package com.ecn.urbapp.activities;

import java.io.File;
import java.util.Vector;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.ecn.urbapp.R;
import com.ecn.urbapp.fragments.CharacteristicsFragment;
import com.ecn.urbapp.fragments.HomeFragment;
import com.ecn.urbapp.fragments.InformationFragment;
import com.ecn.urbapp.fragments.SaveFragment;
import com.ecn.urbapp.fragments.ZoneFragment;
import com.ecn.urbapp.listener.MyTabListener;
import com.ecn.urbapp.zones.BitmapLoader;
import com.ecn.urbapp.zones.DrawZoneView;
import com.ecn.urbapp.zones.Zone;

/**
 * @author	COHENDET S�bastien
 * 			DAVID Nicolas
 * 			GUILBART Gabriel
 * 			PALOMINOS Sylvain
 * 			PARTY Jules
 * 			RAMBEAU Merwan
 * 
 * MainActivity class
 * 
 * This is the main activity of the application.
 * It contains an action bar filled with the different fragments
 * 			
 */

public class MainActivity extends Activity {

	/**
	 * bar represent the action bar of the application
	 */
	ActionBar bar;
	public ImageView myImage; public File photo;
	public Vector<Zone> zones; public Zone zoneToDelete ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Setting the Activity bar
		bar = getActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		bar.setDisplayHomeAsUpEnabled(true);

		//Setting of the different tab of the bar
		
		//Home tab
		Tab tabHome =  bar.newTab();
		tabHome.setText(R.string.homeFragment);
		HomeFragment home = new HomeFragment();
		tabHome.setTabListener(new MyTabListener(home));
		bar.addTab(tabHome);
		
		//Information tab
		Tab tabInformation =  bar.newTab();
		tabInformation.setText(R.string.informationFragment);
		InformationFragment information = new InformationFragment();
		tabInformation.setTabListener(new MyTabListener(information));
		bar.addTab(tabInformation);
		
		//Zone tab
		Tab tabZone =  bar.newTab();
		tabZone.setText(R.string.zoneFragment);
		ZoneFragment zone = new ZoneFragment();
		tabZone.setTabListener(new MyTabListener(zone));
		bar.addTab(tabZone);
		
		//Definition tab
		Tab tabDefinition =  bar.newTab();
		tabDefinition.setText(R.string.definitionFragment);
		CharacteristicsFragment definition = new CharacteristicsFragment();
		tabDefinition.setTabListener(new MyTabListener(definition));
		bar.addTab(tabDefinition);
		
		//Save tab
		Tab tabSave =  bar.newTab();
		tabSave.setText(R.string.saveFragment);
		SaveFragment save = new SaveFragment();
		tabSave.setTabListener(new MyTabListener(save));
		bar.addTab(tabSave);
		
		//create zones' list for new image
		zones = new Vector<Zone>();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu, this adds items to the action bar if it is present.
		MenuInflater inflater =	getMenuInflater();
		inflater.inflate(R.menu.menu_main, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	
	//TODO dessin de zones directement dans la vue de gestion de zones ! 
	
	/****zone creation main****/
	//buttons' actions mostly at the bottom of this file
	public void zone_button_create (View view){//called by matching xml onclick
		
		Log.d("UrbApp","Créer zone");
		final Zone zone = new Zone();
		setContentView(R.layout.layout_zone_create);
		
		//TODO mettre par défaut dans le layout ?
		findViewById(R.id.zone_create_button_validate).setEnabled(false);
		findViewById(R.id.zone_create_button_back).setEnabled(false);
		
		//Image drawing plus its zones
		myImage = (ImageView) findViewById(R.id.image_zone_create);

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
		myImage.setImageDrawable(new LayerDrawable(drawables));
		
		//Button back action
		//TODO implémenter la même chose avec le back générique Android
		findViewById(R.id.zone_create_button_back).setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				zone.getPoints().remove(zone.getPoints().lastElement());
				refreshCreate(zone);
			}
		});
		
		//Image touch action
		myImage.setOnTouchListener(new ImageView.OnTouchListener() {
			protected Matrix matrix;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					Log.d("UrbApp","Avant x:"+event.getX()+";y:"+event.getY());
					float[] coord = {event.getX(),event.getY()};//get touched point coord
					if (matrix == null) {//create matrix that transforms point coord into image base 
						matrix = new Matrix();
						((ImageView) findViewById(R.id.image_zone_create)).getImageMatrix()
								.invert(matrix);
						Log.d("Matrix",matrix.toString());
					}
					matrix.mapPoints(coord);//apply matrix transformation on points coord
					Log.d("UrbApp","Après x:"+coord[0]+";y:"+coord[1]);
					zone.addPoint(new PointF(coord[0],coord[1]));
					refreshCreate(zone);//display new point, refresh buttons' availabilities					
				}
				return true;
			}
		});
		
		//Save new zone
		findViewById(R.id.zone_create_button_validate).setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				zones.add(zone);
				setContentView(R.layout.layout_zone);
			}
		});
	}
	
	/***** zone edition main *****/
	public void zone_button_edit (View view){//called by matching xml onclick
		Log.d("UrbApp","Créer zone");
		final Zone zone = new Zone();
		final PointF selected = new PointF();
		setContentView(R.layout.layout_zone_edit);
		
		//TODO inclure par défaut dans le layout ?
		findViewById(R.id.zone_edit_button_delete_point).setEnabled(false);
		findViewById(R.id.zone_edit_button_release_point).setEnabled(false);
		
		//display image plus its zones
		//TODO synergie affichages !
		myImage = (ImageView) findViewById(R.id.image_zone_edit);

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
			myImage.setOnTouchListener(new ImageView.OnTouchListener() {
				protected Matrix matrix;
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						Log.d("UrbApp","Avant x:"+event.getX()+";y:"+event.getY());
						float[] coord = {event.getX(),event.getY()};//get touched point coord
						//TODO synergie création matrice, récupération de coordonnées !
						if (matrix == null) {
							matrix = new Matrix();
							((ImageView) findViewById(R.id.image_zone_edit)).getImageMatrix()
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
								findViewById(R.id.zone_edit_button_delete_point).setEnabled(false);
								findViewById(R.id.zone_edit_button_release_point).setEnabled(false);
							}
							//If a zone is selected, but none of its points, is the touch point a normal or middle point ?
							else{
								for(PointF p : zone.getPoints()){//is the touched point a normal point ?
									float dx=Math.abs(p.x-touch.x);
									float dy=Math.abs(p.y-touch.y);
									if((dx*dx+dy*dy)<10*10){//10 radius tolerance
										selected.set(p);
										//enable point deleting or releasing
										findViewById(R.id.zone_edit_button_delete_point).setEnabled(true);
										findViewById(R.id.zone_edit_button_release_point).setEnabled(true);
									}
								}
								if(selected.x == 0 && selected.y == 0){//is the touched point a middle point ?
									for(PointF p : zone.getMiddles()){
										float dx=Math.abs(p.x-touch.x);
										float dy=Math.abs(p.y-touch.y);
										if((dx*dx+dy*dy)<10*10){
											selected.set(p);
											//impossible to delete a middle point, but enable releasing
											findViewById(R.id.zone_edit_button_release_point).setEnabled(true);
										}
									}
								}
							}							
						}
						myImage.invalidate();//refresh points' displaying
						
					}
					return true;
				}
			});
			
			//save edited zone
			findViewById(R.id.zone_edit_button_validate).setOnClickListener(new View.OnClickListener() {			
				@Override
				public void onClick(View v) {
					zones.remove(zoneToDelete);//delete original  
					zones.add(zone);//save edited
					setContentView(R.layout.layout_zone);
				}
			});
			
			//cancel zone edition
			findViewById(R.id.zone_edit_button_cancel).setOnClickListener(new View.OnClickListener() {			
				@Override
				public void onClick(View v) {
					setContentView(R.layout.layout_zone);
				}
			});
			
			//delete selected point
			findViewById(R.id.zone_edit_button_delete_point).setOnClickListener(new View.OnClickListener() {			
				@Override
				public void onClick(View v) {
					zone.deletePoint(selected);
					selected.set(new PointF(0,0));//no selected point anymore
					findViewById(R.id.zone_edit_button_delete_point).setEnabled(false);
					findViewById(R.id.zone_edit_button_release_point).setEnabled(false);
					myImage.invalidate();//refresh image
				}
			});
			
			//release selected point
			findViewById(R.id.zone_edit_button_release_point).setOnClickListener(new View.OnClickListener() {			
				@Override
				public void onClick(View v) {
					selected.set(new PointF(0,0));
					findViewById(R.id.zone_edit_button_delete_point).setEnabled(false);
					findViewById(R.id.zone_edit_button_release_point).setEnabled(false);
					myImage.invalidate();
				}
			});
	}
	
	/***** delete zone main *****/
	public void zone_button_delete (View view){//called by matching xml onclick
		Log.d("UrbApp","Créer zone");
		final Zone zone = new Zone();
		final PointF selected = new PointF();
		setContentView(R.layout.layout_zone_delete);
		
		myImage = (ImageView) findViewById(R.id.image_zone_delete);

		String youFilePath = Environment.getExternalStorageDirectory().toString()+"/Download/Images.jpeg";
		photo=new File(youFilePath);
	
		Drawable[] drawables = {
			new BitmapDrawable(
				getResources(),
				BitmapLoader.decodeSampledBitmapFromFile(
					photo.getAbsolutePath(), 1000, 1000)), 
				new DrawZoneView(zones, zone, selected) };
		myImage.setImageDrawable(new LayerDrawable(drawables));
			
			myImage.setOnTouchListener(new ImageView.OnTouchListener() {
				protected Matrix matrix;
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						Log.d("UrbApp","Avant x:"+event.getX()+";y:"+event.getY());
						float[] coord = {event.getX(),event.getY()};
						if (matrix == null) {
							matrix = new Matrix();
							((ImageView) findViewById(R.id.image_zone_delete)).getImageMatrix()
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
								setContentView(R.layout.layout_zone);
							}
						}
					}
					return true;
				}
			});
			
			findViewById(R.id.zone_delete_button_cancel).setOnClickListener(new View.OnClickListener() {			
				@Override
				public void onClick(View v) {
					setContentView(R.layout.layout_zone);
				}
			});
	}
	
	/*create actions*/
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
	}
	
	//display new point, refresh buttons' availabilities, only for zone creation
	public void refreshCreate(Zone zone){
		Vector<PointF> points = zone.getPoints();
		if(! points.isEmpty()){
			findViewById(R.id.zone_create_button_back).setEnabled(false);
			if(points.size()>1){							
				findViewById(R.id.zone_create_button_validate).setEnabled(false);
				findViewById(R.id.zone_create_button_back).setEnabled(true);
				if(points.size()>2){
					findViewById(R.id.zone_create_button_validate).setEnabled(true);
				}
			}
		}
		myImage.invalidate();
	}
	
	public boolean intersect(PointF a, PointF b, PointF c, PointF d){
		//TODO check polygon intersections
		//voir JTS
		return false;
	}


}
