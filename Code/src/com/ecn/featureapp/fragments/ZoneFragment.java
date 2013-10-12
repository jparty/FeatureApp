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

package com.ecn.featureapp.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ecn.featureapp.*;
import com.ecn.featureapp.activities.*;

/**
 * This class is the fragment for the zone management (add zone, group or
 * divide...)
 * 
 * @author patrick
 * 
 */
public class ZoneFragment extends Fragment {

	ActionMode.Callback mCallback;
	ActionMode mMode;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = new View(getActivity());
		setHasOptionsMenu(true);

		// Create a contextual menu (but does not show it yet)
		mCallback = new ActionMode.Callback() {

			/**
			 * Invoked whenever the action mode is shown. This is invoked
			 * immediately after onCreateActionMode
			 */
			@Override
			public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
				return false;
			}

			/** Called when user exits action mode */
			@Override
			public void onDestroyActionMode(ActionMode mode) {
				mMode = null;
				((MainActivity) getActivity()).zones.unselectAll();
				((MainActivity) getActivity()).state = 0;
				((MainActivity) getActivity()).myImage.invalidate();
			}

			/**
			 * This is called when the action mode is created. This is called by
			 * startActionMode()
			 */
			@Override
			public boolean onCreateActionMode(ActionMode mode, Menu menu) {
				mode.setTitle(R.string.choose_frontages_to_regroup);
				((MainActivity) getActivity()).getMenuInflater().inflate(
						R.menu.regroupfrontages_contextmenu, menu);
				return true;
			}

			/** This is called when an item in the context menu is selected */
			@Override
			public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
				switch (item.getItemId()) {
				case R.id.regroup:
					// Regroup the selected zones
					((MainActivity) getActivity()).zones
							.regroup(((MainActivity) getActivity()).zones
									.getAllSelectedFrontages());
					
					// Show a confirmation message
					Toast toast = Toast.makeText(getActivity().getApplicationContext(),
							R.string.zones_regrouped, Toast.LENGTH_SHORT);
					toast.show();
					
					// Close the contextual bar
					mode.finish();
					break;
				case R.id.cancel:
					// Close the contextual bar
					mode.finish();
					break;
				}
				return false;
			}
		};

		return view;
	}

	/**
	 * This method fills the menu with the values in the corresponding XML file
	 */
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// Delete the existing menu
		menu.clear();
		// Add the new menu
		inflater.inflate(R.menu.frontagefragment_menu, menu);
	}

	/**
	 * This method is used to defined the action corresponding to the menu
	 * buttons
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		// Handle the add zone button
		case R.id.add_frontage:
			// Add an empty zone
			((MainActivity) getActivity()).zones.addEmpty();

			// Modify the state of the application (the action will be executed
			// when the user touch the screen)
			((MainActivity) getActivity()).state = 1;
			return true;

			// Handle the delete zone button
		case R.id.delete_frontage:
			// Modify the state of the application (the action will be executed
			// when the user select a zone)
			((MainActivity) getActivity()).state = 3;
			return true;

			// Handle the regroup zone button
		case R.id.regroup_frontage:
			// Modify the state of the application (the action will be executed
			// when the user select a zone)
			((MainActivity) getActivity()).state = 5;

			// Show the contextual menu
			mMode = ((MainActivity) getActivity()).startActionMode(mCallback);
			return true;
		case R.id.divide_frontage:
			// Modify the state of the application (the action will be executed
			// when the user select a zone)
			((MainActivity) getActivity()).state = 7;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
