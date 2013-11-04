package com.ecn.urbapp.fragments;

import android.app.Fragment;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.layout_definition, null);

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

		return v;
	}
}