package com.ecn.urbapp.fragments;

import android.app.Fragment;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ecn.urbapp.R;
import com.ecn.urbapp.utils.DrawImageView;
import com.ecn.urbapp.zones.SetOfZone;

/**
 * @author	COHENDET Sébastien
 * 			DAVID Nicolas
 * 			GUILBART Gabriel
 * 			PALOMINOS Sylvain
 * 			PARTY Jules
 * 			RAMBEAU Merwan
 * 
 * CharacteristicsFragment class
 * 
 * This is the fragment used to define the differents characteristics of the zone.
 * 			
 */

public class CharacteristicsFragment extends Fragment {

	/** List of all the zones */
	private static SetOfZone zones;

	/** Image containing the photo and to drawing of the zones */
	private static ImageView myImage = null;

	/** Button to enable the selection of the zones */
	private Button select = null;

	/** Button to fill the characteristic the selected zones */
	private Button define = null;

	/** Button to delete all the characteristics of the selected zones */
	private Button delete = null;

	/** Button to show a summary of the characteristics of the selected zones */
	private Button recap = null;

	/** Button to confirm the selection of the zone */
	private Button selectConfirm = null;

	/** Text to explain how to select the zone */
	private TextView text = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.layout_definition, null);

		select = (Button) v.findViewById(R.id.definition_button_select);
		define = (Button) v.findViewById(R.id.definition_button_define);
		delete = (Button) v.findViewById(R.id.definition_button_delete);
		recap = (Button) v.findViewById(R.id.definition_button_recap);
		selectConfirm = (Button) v.findViewById(R.id.definition_button_select_confirm);
		text = (TextView) v.findViewById(R.id.definition_textview_select);
		myImage = (ImageView) v.findViewById(R.id.image);

		// Initialize the list of zones with its state before a screen rotation
		// (or with an empty list if this creation does not correspond to a
		// screen rotation)
		zones = (SetOfZone) getActivity().getLastNonConfigurationInstance();
		if (zones == null) {
			zones = new SetOfZone();
		}

		zones.addEmpty();
		zones.addPoint(new Point(200, 200), 10);
		zones.addPoint(new Point(400, 200), 10);
		zones.addPoint(new Point(400, 400), 10);
		zones.addPoint(new Point(200, 400), 10);
		zones.addPoint(new Point(200, 200), 10);
		zones.addEmpty();
		zones.addPoint(new Point(600, 200), 10);
		zones.addPoint(new Point(900, 200), 10);
		zones.addPoint(new Point(900, 400), 10);
		zones.addPoint(new Point(600, 400), 10);
		zones.addPoint(new Point(600, 200), 10);

		DrawImageView view = new DrawImageView(zones);
		Drawable[] drawables = {view};
		myImage.setImageDrawable(new LayerDrawable(drawables));

	    select.setOnClickListener(clickListenerSelect);
	    selectConfirm.setOnClickListener(clickListenerSelectConfirm);

		return v;
	}

	/**
	 * Listener that activates the Listener for myImage allowing the selection of the zones
	 * and that change the visible button to prevent the user to do any other action during the selection.
	 */
	private OnClickListener clickListenerSelect = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			myImage.setOnTouchListener(touchListenerSelectImage);
			select.setVisibility(View.GONE);
			define.setVisibility(View.GONE);
			delete.setVisibility(View.GONE);
			recap.setVisibility(View.GONE);
			selectConfirm.setVisibility(View.VISIBLE);
			text.setVisibility(View.VISIBLE);
		}
	};

	/**
	 * Listener that deactivates the Listener for myImage allowing the selection of the zones
	 * and that change the visible button to allow the modification of zones' characteristics.
	 */
	private OnClickListener clickListenerSelectConfirm = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			myImage.setOnTouchListener(null);
			select.setVisibility(View.VISIBLE);
			define.setVisibility(View.VISIBLE);
			delete.setVisibility(View.VISIBLE);
			recap.setVisibility(View.VISIBLE);
			selectConfirm.setVisibility(View.GONE);
			text.setVisibility(View.GONE);
		}
	};

	/**
	 * Listener that allows the selection of the zones by clicking on them.
	 * The convertTouchPoint method comes from the FeatureApp project.
	 */
	private OnTouchListener touchListenerSelectImage = new View.OnTouchListener() {

		/**
		 * This matrix is used to convert from application coordinates to
		 * pixels from the image
		 */
		protected Matrix matrix;

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// If the user touch inside a zone, select the zone
			zones.select(zones.isInsideFrontage(this
					.convertTouchPoint(event.getX(), event.getY())));

			// Ask to draw again
			myImage.invalidate();
			return false;
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
				((ImageView) getView().findViewById(R.id.image)).getImageMatrix()
						.invert(this.matrix);
			}

			// Get the touch point coordinates
			float[] point = { x, y };

			// Converting the point in image coordinate system
			this.matrix.mapPoints(point);
			return new Point(((int) point[0]), ((int) point[1]));
		}
	};
}