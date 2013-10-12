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
import java.text.SimpleDateFormat;
import java.util.Date;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.MediaColumns;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.ecn.featureapp.*;

/**
 * This is the first activity where the user has to choose between selecting an
 * existing photo or taking a new one
 * 
 * @author patrick
 * 
 */
public class WelcomeActivity extends Activity {

	/** Value used for when the user chose an existing photo */
	private static final int CHOOSE_PHOTO_RESULT = 1;

	/** Value used when the user chose to take a new photo */
	private static final int PHOTO_RESULT = 2;

	/** File the taken photo is */
	private File photo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// Creating the main layout and instancing the buttons
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcomeactivity_layout);
		Button load_image = (Button) findViewById(R.id.load_image);
		Button take_picture = (Button) findViewById(R.id.take_picture);

		// Create a click listener on the load_image button
		load_image.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// Creating an intent to get an image from the gallery
				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
				startActivityForResult(i, CHOOSE_PHOTO_RESULT);

			}
		});

		// Create a click listener on the take_picture button
		take_picture.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// Create a featureapp folder on the tablet if needed
				File folder = new File(Environment
						.getExternalStorageDirectory(), "featureapp/");
				folder.mkdirs();

				// Get the time to create a unique name for the photo
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
				String currentDateandTime = sdf.format(new Date());
				
				// The place where to photo will be saved
				photo = new File(Environment.getExternalStorageDirectory(),
						"featureapp/Photo_"+currentDateandTime+".jpg");

				// Creating an intent to take a photo and store it in photo
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
				startActivityForResult(intent, PHOTO_RESULT);
			}
		});

	}

	/**
	 * This method is called at the return the intent (to get or take a photo)
	 * and open the main activity (if there is no error)
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// If the intent worked normally and a photo is available
		if (resultCode == RESULT_OK) {

			// Get the absolute path of the photo depending on the origin of the
			// photo (gallery or camera) and specify in a boolean if the photo
			// came from the camera
			String absoluteFilePath = "";
			Boolean taken_photo = false;
			Boolean onlineImage = false;

			switch (requestCode) {
			case PHOTO_RESULT:
				absoluteFilePath = photo.getAbsolutePath();
				taken_photo = true;
				break;
			case CHOOSE_PHOTO_RESULT:
				final String[] filePathColumn = { MediaColumns.DATA,
						MediaColumns.DISPLAY_NAME };
				Cursor cursor = getContentResolver().query(data.getData(),
						filePathColumn, null, null, null);

				// Check if it is on online photo (from picasa)
				if (data.getData().toString()
						.startsWith("content://com.android.gallery3d.provider")) {
					onlineImage = true;
				} else {
					cursor.moveToFirst();
					int idx = cursor
							.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
					absoluteFilePath = cursor.getString(idx);
				}
				break;
			}

			if (onlineImage) {
				// Show an error message if it is an online photo
				Toast toast = Toast.makeText(getApplicationContext(),
						R.string.online_image_error, Toast.LENGTH_LONG);
				toast.show();
			} else {
				// Call the second activity and give it the path of the photo
				// and
				// the boolean specifying if the photo was taken with the camera
				Intent i = new Intent(this, MainActivity.class);
				i.putExtra("filepath", absoluteFilePath);
				i.putExtra("taken_photo", taken_photo);
				this.startActivity(i);
			}

		} else {
			// Show a message if the selection of the photo did not work and
			// stay in the same activity
			Toast toast = Toast.makeText(getApplicationContext(),
					R.string.error_selecting_image, Toast.LENGTH_SHORT);
			toast.show();
		}

	}

	/**
	 * This method fills the menu
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.welcomeactivity_menu, menu);
		return true;
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
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
