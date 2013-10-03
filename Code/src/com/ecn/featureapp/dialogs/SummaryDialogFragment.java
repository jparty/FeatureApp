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
import android.view.LayoutInflater;
import android.view.View;
import com.ecn.featureapp.*;
import com.ecn.featureapp.activities.*;
import com.ecn.featureapp.zones.*;

/**
 * This class creates the dialog that give information about a zone
 * 
 * @author patrick
 * 
 */
public class SummaryDialogFragment extends DialogFragment {
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Get the selected zone (the one about which we want the informations)
		Zone selectedFrontage = ((MainActivity) getActivity()).zones
				.getSelectedFrontage();

		// Create a dialog and set the title
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(R.string.information_about_frontage);

		// Add the content of the dialog (string with html tags)
		String contenu = "<b><u>" + getString(R.string.type) + " :</b></u> "
				+ selectedFrontage.getTypeToText(getResources()) + "<br><b><u>"
				+ getString(R.string.materials) + " :</b></u> "
				+ selectedFrontage.getMaterialToText(getResources());

		Balcony balcony = ((MainActivity) getActivity()).zones
				.getBalcony(selectedFrontage);
		if (balcony != null) {
			contenu = contenu + "<br>" + getString(R.string.is_balcony) + " "
					+ balcony.getEstimatedHeight() + " " + getString(R.string.meter_height);
		}

		contenu = contenu + "<br><b><u>" + getString(R.string.color)
				+ " :</b></u>";
		builder.setMessage(Html.fromHtml(contenu));

		// Add a rectangle of the color of the zone to the dialog
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View dialoglayout = inflater.inflate(R.layout.summary_dialog, null);
		View colorView = dialoglayout.findViewById(R.id.color);
		colorView.setBackgroundColor(selectedFrontage.getColor());
		builder.setView(dialoglayout);

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