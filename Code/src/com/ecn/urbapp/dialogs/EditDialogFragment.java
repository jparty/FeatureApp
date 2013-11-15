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

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;

import com.ecn.urbapp.R;
import com.ecn.urbapp.fragments.CharacteristicsFragment;

/**
 * This class creates the dialog that allow the user to enter a personalize the
 * name of the material of the selected zone
 * 
 * @author patrick, Jules Party
 * 
 */
public class EditDialogFragment extends DialogFragment {

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Create a dialog and set the title
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(R.string.edit_dialog_title);

		// Add the edit text zone
		final EditText input = new EditText(getActivity());
		builder.setView(input);

		// Add the OK button
		builder.setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int whichButton) {
						// Set the material for the selected zone as the string
						// entered by the user
						CharacteristicsFragment.getZones()
								.setMaterialForSelectedZones(
										input.getText().toString());

						// Show the dialog to choose to color of the zone
						ColorDialogFragment colordialog = new ColorDialogFragment();
						colordialog.show(getFragmentManager(), "ColorFragments");
					}
				});

		// Add the cancel button
		builder.setNegativeButton(R.string.cancel,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// Show the dialog the choose the material of the zone
						MaterialDialogFragment characteristicdialog = new MaterialDialogFragment();
						characteristicdialog.show(getFragmentManager(),
								"CharacteristicsFragments");
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
		
	}
}