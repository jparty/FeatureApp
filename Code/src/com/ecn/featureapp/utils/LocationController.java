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

package com.ecn.featureapp.utils;

import java.io.File;
import java.io.IOException;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.ExifInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
import com.ecn.featureapp.*;
import com.ecn.featureapp.zones.*;

/**
 * This class is used to get location information with the GPS sensor and writes
 * them as EXIF data on the photo
 * 
 * @author patrick
 * 
 */
public class LocationController implements LocationListener {

	/** The application context */
	private Context appContext;

	/** The location manager linked to this location controller */
	private LocationManager locationManager;

	/** The file containing the photo */
	private File photo;

	/** The zones corresponding to this photo */
	private SetOfZone frontages;

	/**
	 * Constructor of the LocationController class
	 * 
	 * @param context
	 *            the context
	 * @param locationManager
	 *            the locationManager
	 * @param photo
	 *            the photo to which the GPS data will be added
	 */
	public LocationController(Context context, LocationManager locationManager,
			File photo, SetOfZone frontages) {
		this.appContext = context;
		this.locationManager = locationManager;
		this.photo = photo;
		this.frontages = frontages;

		// Stop looking for location after 20s (if no updates were found)
		Handler serviceHandler = new Handler();
		serviceHandler.postDelayed(new stopSearchingGPS(), 20000);
	}

	/**
	 * Method called when the GPS position is found
	 */
	public void onLocationChanged(Location location) {
		// Write GPS position is the EXIF
		this.writeEXIF(location);

		// Show a confirmation message to the user
		Toast toast = Toast.makeText(appContext, R.string.positionsaved,
				Toast.LENGTH_LONG);
		toast.show();

		// Stop looking for updates to the position of the user
		locationManager.removeUpdates(this);
		frontages.gpsinfos = true;
	}

	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	public void onProviderEnabled(String provider) {
	}

	public void onProviderDisabled(String provider) {
		// Show a message telling the GPS is not active
		Toast toast = Toast.makeText(appContext, R.string.noGPSsensor,
				Toast.LENGTH_LONG);
		toast.show();
	}

	/**
	 * This class will make the application stop looking for updates after 20
	 * secondes
	 * 
	 * @author patrick
	 * 
	 */
	class stopSearchingGPS implements Runnable {
		public void run() {
			// If not GPS position was found yet
			if (!frontages.gpsinfos) {
				// Stop looking for updates to the position of the user
				locationManager.removeUpdates(LocationController.this);
				frontages.gpsinfos = true;

				// Show an error message
				Toast toast = Toast.makeText(appContext,
						R.string.GPSpositionnotfound, Toast.LENGTH_LONG);
				toast.show();
			}
		}
	}

	/**
	 * Method used to write the GPS position in the EXIF of the photo. It needs
	 * to convert the GPS position from decimal degrees to DMS coordinates.
	 * 
	 * @param location
	 *            the GPS location
	 */
	public void writeEXIF(Location location) {
		ExifInterface exif;
		double latitude = location.getLatitude();
		double longitude = location.getLongitude();

		try {
			exif = new ExifInterface(photo.getAbsolutePath());

			// Convert decimal degree to DMS coordinates
			int num1Lat = (int) Math.floor(Math.abs(latitude));
			int num2Lat = (int) Math.floor((Math.abs(latitude) - num1Lat) * 60);
			double num3Lat = ((Math.abs(latitude) - num1Lat) * 60 - num2Lat) * 60000;

			int num1Lon = (int) Math.floor(Math.abs(longitude));
			int num2Lon = (int) Math
					.floor((Math.abs(longitude) - num1Lon) * 60);
			double num3Lon = ((Math.abs(longitude) - num1Lon) * 60 - num2Lon) * 60000;

			// Set the coordinate to the EXIF and the reference (North,
			// South...)
			exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE, num1Lat + "/1,"
					+ num2Lat + "/1," + num3Lat + "/1000");
			exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE, num1Lon + "/1,"
					+ num2Lon + "/1," + num3Lon + "/1000");

			if (latitude > 0) {
				exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE_REF, "N");
			} else {
				exif.setAttribute(ExifInterface.TAG_GPS_LATITUDE_REF, "S");
			}

			if (longitude > 0) {
				exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF, "E");
			} else {
				exif.setAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF, "W");
			}

			// Save the EXIF
			exif.saveAttributes();

		} catch (IOException e) {
			Log.e("PictureActivity", e.getLocalizedMessage());
		}
	}

}
