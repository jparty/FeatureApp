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
import android.text.Html;
import com.ecn.featureapp.*;
import com.ecn.featureapp.activities.*;

/**
 * This class creates the dialog that give global information about the photo,
 * specifically about the building on the photo (number of floors, percentage of
 * glazing...)
 * 
 * @author patrick
 * 
 */
public class GlobalInformationDialogFragment extends DialogFragment {
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {

		// Create a dialog and set the title
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(R.string.information_about_building);

		// Add the content of the dialog (string with html tags)
		String contenu = "<b><u>"
				+ getString(R.string.number_of_floors)
				+ " :</b></u> "
				+ ((MainActivity) getActivity()).zones
						.getNumberOfFloorsToText(getResources())
				+ "<br><b><u>"
				+ getString(R.string.percentage_of_glazing)
				+ " :</b></u> "
				+ ((MainActivity) getActivity()).zones
						.getPercentageOfGlazingToText(getResources())
				+ "<br><b><u>"
				+ getString(R.string.number_of_balcony)
				+ " :</b></u> "
				+ ((MainActivity) getActivity()).zones.balconies.size()+"<br>";
		builder.setMessage(Html.fromHtml(contenu));

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