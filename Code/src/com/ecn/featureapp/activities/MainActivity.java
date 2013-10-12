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

package com.ecn.featureapp.activities;

import java.io.File;

import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;

import com.ecn.featureapp.*;
import com.ecn.featureapp.zones.*;
import com.ecn.featureapp.fragments.*;
import com.ecn.featureapp.dialogs.*;
import com.ecn.featureapp.utils.*;

/**
 * This is the main activity of the application where the user add zones,
 * characteristics... It contains several fragments (tabs) for the different
 * possibilities.
 * 
 * @author patrick
 * 
 */
public class MainActivity extends Activity {

	/** List of all the zones */
	public SetOfZone zones;

	/**
	 * Integer used to memorize the state (last action asked by the user) 0 : no
	 * action asked; 1 : add zone action; 2 : characterize a zone; 3 : delete a
	 * zone; 4 : show a summary of the information of a zone; 5 : regroup
	 * different zones; 6 : choose color of a fragment; 7 : divide the zone; 8 :
	 * add a balcony;
	 */
	public int state;

	/** Image containing the photo and to drawing of the zones */
	public ImageView myImage;

	/** File containing the photo used by the application */
	public File photo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		state = 0;

		// Initialize the list of zones with its state before a screen rotation
		// (or with an empty list if this creation does not correspond to a
		// screen rotation)
		zones = (SetOfZone) getLastNonConfigurationInstance();
		Intent i = getIntent();
		if (zones == null) {
			if (i.getBooleanExtra("taken_photo", false)) { // Picture taken by
															// internal camera
				zones = new SetOfZone();
			} else { // Picture loaded from storage
				zones = new SetOfZone();
				zones = (SetOfZone) zones.open(zones,
						i.getStringExtra("filepath"));
			}
		}

		// Create the layout and the image
		setContentView(R.layout.mainactivity_layout);
		myImage = (ImageView) findViewById(R.id.image);

		// Display the picture (resized to limit the size in the memory)

		photo = new File(i.getStringExtra("filepath"));
		DrawImageView view = new DrawImageView(zones);
		Drawable[] drawables = {
				new BitmapDrawable(getResources(),
						BitmapLoader.decodeSampledBitmapFromFile(
								photo.getAbsolutePath(), 1000, 1000)), view };
		myImage.setImageDrawable(new LayerDrawable(drawables));

		// Add tabs to the action bar
		ActionBar bar = getActionBar();
		bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		ActionBar.Tab frontage_tab = bar.newTab().setText(
				getString(R.string.select_frontage_mode));
		ActionBar.Tab caracteristics_tab = bar.newTab().setText(
				getString(R.string.features_mode));
		ActionBar.Tab summary_tab = bar.newTab().setText(
				getString(R.string.summary));

		// Create the different fragments and add a listener
		Fragment fragmentA = new ZoneFragment();
		Fragment fragmentB = new CharacteristicsFragment();
		Fragment fragmentC = new SummaryFragment();

		frontage_tab.setTabListener(new MyTabsListener(fragmentA, 0));
		caracteristics_tab.setTabListener(new MyTabsListener(fragmentB, 0));
		summary_tab.setTabListener(new MyTabsListener(fragmentC, 4));

		bar.addTab(frontage_tab);
		bar.addTab(caracteristics_tab);
		bar.addTab(summary_tab);

		// If the photo was taken with the camera, add the GPS location to the
		// photo EXIF
		if (i.getBooleanExtra("taken_photo", false)) {
			if (!zones.gpsinfos) {
				// Show a message for the user
				Toast toast = Toast.makeText(getApplicationContext(),
						R.string.savingposition, Toast.LENGTH_LONG);
				toast.show();

				// Request location update (with the class LocationController)
				LocationManager locationManager = (LocationManager) this
						.getSystemService(Context.LOCATION_SERVICE);
				LocationController locationListener = new LocationController(
						getApplicationContext(), locationManager, photo, zones);
				locationManager.requestLocationUpdates(
						LocationManager.GPS_PROVIDER, 0, 0, locationListener);
			}
		}

		// Add a touch listener for when the user touch the image (the action
		// are different depending on the value of state)
		myImage.setOnTouchListener(new View.OnTouchListener() {
			/**
			 * This matrix is used to convert from application coordinates to
			 * pixels from the image
			 */
			protected Matrix matrix;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// Only consider when the user touch the screen
				if (event.getAction() == MotionEvent.ACTION_DOWN) {

					// Realize different actions depending on the value of state
					switch (state) {
					// The action asked is add a zone
					case 1:
						// If the last frontage is not finished (also check if
						// at
						// least one zone exist)
						if ((zones.getFrontages().size() != 0)
								&& (!zones.getFrontages()
										.get(zones.getFrontages().size() - 1)
										.isFinished())) {

							// Get the user preferences
							SharedPreferences preferences = PreferenceManager
									.getDefaultSharedPreferences(MainActivity.this);
							// Convert to point in image pixel and add it to
							// the
							// frontage
							zones.addPoint(
									this.convertTouchPoint(event.getX(),
											event.getY()),
									preferences.getInt("selectionAccuracy", 10));
							// Ask to draw again
							myImage.invalidate();
						} else {
							// If the zone is finished change the state to the
							// default value
							state = 0;
						}
						break;

					// The action asked is characterize a zone
					case 2:
						// If the user touch inside a zone, select the zone
						if (zones.select(zones.isInsideFrontage(this
								.convertTouchPoint(event.getX(), event.getY())))) {

							// Ask to draw again
							myImage.invalidate();

							// Show the dialog to choose the characteristics
							TypeDialogFragment typedialog = new TypeDialogFragment();
							typedialog.show(getFragmentManager(),
									"TypeFragment");

							// Change the state to the default value
							state = 0;
						}
						break;

					// The action asked is deleting a zone
					case 3:
						// If the user touch inside a zone, select the zone
						if (zones.select(zones.isInsideFrontage(this
								.convertTouchPoint(event.getX(), event.getY())))) {

							// Ask to draw again
							myImage.invalidate();

							// Show the alert dialog to ask confirmation for
							// this action
							DeleteFrontageAlertDialog deletealertdialog = new DeleteFrontageAlertDialog();
							deletealertdialog.show(getFragmentManager(),
									"DeleteAlertDialog");
						}

						// Change the state to the default value
						state = 0;
						break;

					// The action asked is show summary of the informations from
					// a zone
					case 4:
						// If the user touch inside a zone, select the zone
						if (zones.isInsideFrontage(this.convertTouchPoint(
								event.getX(), event.getY())) >= 0) {
							zones.getFrontages()
									.get(zones.isInsideFrontage(this
											.convertTouchPoint(event.getX(),
													event.getY()))).select();

							// Ask to draw again
							myImage.invalidate();

							// Show the choose characteristics dialog
							SummaryDialogFragment summarydialog = new SummaryDialogFragment();
							summarydialog.show(getFragmentManager(),
									"CharacteristicsFragments");
						}
						break;

					// The action asked is to regroup several zones
					case 5:
						// If the user touch inside a zone, select the zone
						if (zones.select(zones.isInsideFrontage(this
								.convertTouchPoint(event.getX(), event.getY())))) {
							// Ask to draw again
							myImage.invalidate();
						}
						break;

					// The action asked is to choose the color of a zone
					case 6:
						// If the user touch inside a zone, select the zone
						if (zones.select(zones.isInsideFrontage(this
								.convertTouchPoint(event.getX(), event.getY())))) {

							// Ask to draw again
							myImage.invalidate();

							// Show the dialog to choose to color of the zone
							ColorDialogFragment colordialog = new ColorDialogFragment();
							colordialog.show(getFragmentManager(),
									"ColorFragments");

							// Change the state to the default value
							state = 0;
						}
						break;
					case 7:
						// If the user touch inside a zone, select the zone
						if (zones.select(zones.isInsideFrontage(this
								.convertTouchPoint(event.getX(), event.getY())))) {
							zones.divide(zones.getSelectedFrontage());

							// Show a confirmation message
							Toast toast = Toast.makeText(
									getApplicationContext(),
									R.string.zones_divided, Toast.LENGTH_SHORT);
							toast.show();

							// Change the state to the default value and
							// unselect all
							state = 0;
							zones.unselectAll();
						}
						break;
					case 8:
						// If the user touch inside a zone, select the zone
						if (zones.select(zones.isInsideFrontage(this
								.convertTouchPoint(event.getX(), event.getY())))) {

							// Ask to draw again
							myImage.invalidate();

							// Show the dialog to choose to color of the zone
							BalconyHeightDialogFragment balconyheightdialog = new BalconyHeightDialogFragment();
							balconyheightdialog.show(getFragmentManager(),
									"BalconyHeightFragments");

							// Change the state to the default value
							state = 0;
						}
						break;
					}
				}
				return true;
			}

			/**
			 * Function used to convert from touch coordinates to image pixels
			 * 
			 * @param x
			 *            abscissa of the touched point
			 * @param y
			 *            ordinate of the touched point
			 * @return coordinate of the touched point in pixels
			 */
			public Point convertTouchPoint(float x, float y) {
				// Get the image matrix (if needed)
				if (this.matrix == null) {
					this.matrix = new Matrix();
					((ImageView) findViewById(R.id.image)).getImageMatrix()
							.invert(this.matrix);
				}

				// Get the touch point coordinates
				float[] point = { x, y };

				// Converting the point in image coordinate system
				this.matrix.mapPoints(point);
				return new Point(((int) point[0]), ((int) point[1]));
			}
		});
	}

	/**
	 * This class is used to manage the click on the tabs and open the
	 * corresponding fragments
	 * 
	 * @author patrick
	 * 
	 */
	protected class MyTabsListener implements ActionBar.TabListener {

		/** The corresponding fragment */
		private Fragment fragment;

		/** The default state of the application with this tab */
		private int defaultState;

		/**
		 * Constructor for a new MyTabsListener
		 * 
		 * @param fragment
		 *            the corresponding fragment
		 * @param defaultState
		 *            the default state of the application when the state is
		 *            chosen
		 */
		public MyTabsListener(Fragment fragment, int defaultState) {
			this.fragment = fragment;
			this.defaultState = defaultState;
		}

		public void onTabReselected(Tab tab, FragmentTransaction ft) {
		}

		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			ft.add(R.id.fragment_container, fragment, null);
			// Change the state to the default value when the user open a new
			// tab
			state = defaultState;
		}

		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			ft.remove(fragment);
		}
	}

	/**
	 * This method is used to defined the action corresponding to the menu
	 * buttons
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// The button pressed is parameter

		case R.id.menu_settings:
			// Open the parameter activity
			Intent i = new Intent(this, PreferencesActivity.class);
			this.startActivity(i);
			return true;

		case R.id.save:
			zones.save(photo.getAbsolutePath(), getApplicationContext());
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * This method is used during the rotation of the screen; It saves the value
	 * of zone to have it when the activity reopens (during a screen rotation
	 * the activity is automatically killed and restart).
	 */
	@Override
	public SetOfZone onRetainNonConfigurationInstance() {
		return zones;
	}

	/**
	 * This method modifies the action of the back button to show a confirmation
	 * dialog before quitting this activity
	 */
	@Override
	public void onBackPressed() {
		// Show a confirmation dialog
		QuitActivityAlertDialog quitalertdialog = new QuitActivityAlertDialog();
		quitalertdialog.show(getFragmentManager(), "QuitAlertDialog");
	}

}
