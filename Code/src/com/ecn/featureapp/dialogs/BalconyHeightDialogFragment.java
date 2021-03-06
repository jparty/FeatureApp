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

package com.ecn.featureapp.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.NumberPicker;
import com.ecn.featureapp.*;
import com.ecn.featureapp.activities.*;

/**
 * This class creates the dialog that ask the indicate the estimated height of
 * the balcony
 * 
 * @author patrick
 * 
 */
public class BalconyHeightDialogFragment extends DialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Create a dialog and set the title
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(R.string.balcony_height_dialog_title);

		// Create a number picker and set the min and max values
		final NumberPicker numberPicker = new NumberPicker(getActivity());
		numberPicker.setMinValue(0);
		numberPicker.setMaxValue(100000);

		// Hide the keyboard
		numberPicker
				.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
		
		// Prevent a circular choice of number
		numberPicker.setWrapSelectorWheel(false);
		
		// Set the initial value
		numberPicker.setValue(200);

		// Add the number picker to the dialog
		builder.setView(numberPicker);

		// Add the OK button
		builder.setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int whichButton) {
						// Set the height of the balcony with the value chosen
						// by the user
						((MainActivity) getActivity()).zones.addBalcony(
								((MainActivity) getActivity()).zones
										.getAllSelectedFrontages(),
								numberPicker.getValue(), getActivity()
										.getApplicationContext());
						
						// Unselect all the zones and draw the image again
						((MainActivity) getActivity()).zones.unselectAll();
						((MainActivity) getActivity()).myImage.invalidate();
					}
				});

		// Add the cancel button
		builder.setNegativeButton(R.string.cancel,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// Unselect all the zones and draw the image again
						((MainActivity) getActivity()).zones.unselectAll();
						((MainActivity) getActivity()).myImage.invalidate();
					}
				});

		return builder.create();

	}

	/**
	 * Action realized when the user cancel the dialog (touch outside the dialog
	 * or press the back button)
	 */
	@Override
	public void onCancel(DialogInterface dialog) {
		// Unselect all the zones and draw the image again
		((MainActivity) getActivity()).zones.unselectAll();
		((MainActivity) getActivity()).myImage.invalidate();
	}
}