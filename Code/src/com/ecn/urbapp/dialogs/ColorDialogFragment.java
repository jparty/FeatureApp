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

package com.ecn.urbapp.dialogs;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.ecn.urbapp.R;
import com.ecn.urbapp.fragments.CharacteristicsFragment;
import com.ecn.urbapp.utils.colorpicker.AmbilWarnaDialog;
import com.ecn.urbapp.utils.colorpicker.AmbilWarnaDialog.OnAmbilWarnaListener;

/**
 * This class creates the dialog that ask the user to choose the color of the
 * zone
 * 
 * @author patrick, Jules Party
 * 
 */
public class ColorDialogFragment extends DialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		// Create the color picker dialog (with the actual color of the selected
		// zone)
		AmbilWarnaDialog dialog = new AmbilWarnaDialog(getActivity(),
				CharacteristicsFragment.getZones().getColorForSelectedZones(),
				new OnAmbilWarnaListener() {
					@Override
					public void onOk(AmbilWarnaDialog dialog, int color) {
						// Modify the color of the zone
						CharacteristicsFragment.getZones()
								.setColorForSelectedZones(color);

						// Unselect all the zones and draw the image again
						CharacteristicsFragment.getZones().unselectAll();
						CharacteristicsFragment.getMyImage().invalidate();
					}

					@Override
					public void onCancel(AmbilWarnaDialog dialog) {
						// Unselect all the zones and draw the image again
						CharacteristicsFragment.getZones().unselectAll();
						CharacteristicsFragment.getMyImage().invalidate();
					}
				});

		// Add a title to the dialog
		dialog.getDialog().setTitle(R.string.choose_color_message);

		return dialog.getDialog();

	}

	/**
	 * Action realized when the user cancel the dialog (touch outside the dialog
	 * or press the back button)
	 */
	@Override
	public void onCancel(DialogInterface dialog) {
		
	}

}