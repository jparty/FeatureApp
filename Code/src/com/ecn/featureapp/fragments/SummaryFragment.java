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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.ecn.featureapp.*;
import com.ecn.featureapp.activities.MainActivity;
import com.ecn.featureapp.dialogs.*;

/**
 * This class is the fragment for the summary view management
 * 
 * @author patrick
 * 
 */
public class SummaryFragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = new View(getActivity());
		setHasOptionsMenu(true);
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
		inflater.inflate(R.menu.summaryfragment_menu, menu);
	}
	
	/**
	 * This method is used to defined the action corresponding to the menu
	 * buttons
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.show_global_infos:
			// Update the value of percentageOfGlazing
			((MainActivity) getActivity()).zones.updatePercentageOfGlazing(getResources());
			
			// Show the global information dialog
			GlobalInformationDialogFragment globalinformationdialog = new GlobalInformationDialogFragment();
			globalinformationdialog.show(getFragmentManager(),
					"GlobalInformationFragment");
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
