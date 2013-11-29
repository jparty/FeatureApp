package com.ecn.urbapp.dialogs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.ecn.urbapp.R;
import com.ecn.urbapp.fragments.CharacteristicsFragment;
import com.ecn.urbapp.utils.colorpicker.AmbilWarnaDialog;
import com.ecn.urbapp.utils.colorpicker.AmbilWarnaDialog.OnAmbilWarnaListener;
import com.ecn.urbapp.zones.UtilCharacteristicsZone;

/**
 * This class creates the dialog that ask the user to choose the characteristics of the
 * zone
 * 
 * @author Jules Party
 * 
 */
public class CharacteristicsDialogFragment extends DialogFragment {

	//TODO Adddescription for javadoc
	private Dialog box;
	//TODO Adddescription for javadoc
	private Spinner spinType;
	//TODO Adddescription for javadoc
	private Spinner spinMaterial;
	//TODO Adddescription for javadoc
	private TextView typeCustom;
	//TODO Adddescription for javadoc
	private TextView materialCustom;
	//TODO Adddescription for javadoc
	private View colorView;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		box = new Dialog(getActivity());
		box.setContentView(R.layout.layout_definition_dialog);
		box.setTitle(R.string.definition_dialog_title);
		box.setCanceledOnTouchOutside(true);
		spinType = (Spinner) box.findViewById(R.id.typeZone);
		typeCustom = (TextView) box.findViewById(R.id.typeZone_custom);
		spinMaterial = (Spinner) box.findViewById(R.id.materialZone);
		materialCustom = (TextView) box.findViewById(R.id.materialZone_custom);
		Button validate = (Button) box.findViewById(R.id.validation);
		spinType.setOnItemSelectedListener(itemSelectedListenerType);
		spinMaterial.setOnItemSelectedListener(itemSelectedListenerMaterial);
		validate.setOnClickListener(validation);
		colorView = box.findViewById(R.id.color);
		colorView.setBackgroundColor(UtilCharacteristicsZone.getColorForSelectedZones());
		colorView.setOnClickListener(chooseColor);
		Map<String, HashMap<String, Float>> summary = UtilCharacteristicsZone.getStatsForSelectedZones(getResources());
		HashMap<String, Float> types = summary.get(getString(R.string.type));
		HashMap<String, Float> materials = summary.get(getString(R.string.materials));
		List<String> list;
		int position = -1;
		if (types.keySet().size() == 1) {
			String type = (String) types.keySet().toArray()[0];
			if (type.equals(getResources().getString(R.string.not_defined))) {
				type = "";
			}
			list = getMaterialList(false, R.array.type);
			position = list.indexOf(type);
			if (position != -1) {
				spinType.setSelection(position);
			} else {
				spinType.setVisibility(View.GONE);
				typeCustom.setVisibility(View.VISIBLE);
				typeCustom.setText(type);
			}
		} else {
			list = getMaterialList(false, R.array.type);
			position = list.indexOf(getResources().getString(R.string.nullString));
			spinType.setSelection(position);
		}
		position = -1;
		if (materials.keySet().size() == 1) {
			String material = (String) materials.keySet().toArray()[0];
			if (material.equals(getResources().getString(R.string.not_defined))) {
				material = "";
			}
			if (getResources().getString(R.string.frontage).equals(spinType.getSelectedItem())) {
				list = getMaterialList(true, R.array.frontagematerial);
				position = list.indexOf(material);
			} else if (getResources().getString(R.string.ground).equals(spinType.getSelectedItem())) {
				list = getMaterialList(true, R.array.groundmaterial);
				position = list.indexOf(material);
			} else if (getResources().getString(R.string.roof).equals(spinType.getSelectedItem())) {
				list = getMaterialList(true, R.array.roofmaterial);
				position = list.indexOf(material);
			}
			if (position == -1) {
				list = getMaterialList(false, R.array.frontagematerial, R.array.groundmaterial, R.array.roofmaterial);
				position = list.indexOf(material);
			}
			if (position != -1) {
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list);
				spinMaterial.setAdapter(adapter);
				spinMaterial.setSelection(position);
			} else {
				spinMaterial.setVisibility(View.GONE);
				materialCustom.setVisibility(View.VISIBLE);
				materialCustom.setText(material);
			}
		} else {
			if (getResources().getString(R.string.frontage).equals(spinType.getSelectedItem())) {
				list = getMaterialList(true, R.array.frontagematerial);
				position = list.indexOf(getResources().getString(R.string.nullString));
			} else if (getResources().getString(R.string.ground).equals(spinType.getSelectedItem())) {
				list = getMaterialList(true, R.array.groundmaterial);
				position = list.indexOf(getResources().getString(R.string.nullString));
			} else if (getResources().getString(R.string.roof).equals(spinType.getSelectedItem())) {
				list = getMaterialList(true, R.array.roofmaterial);
				position = list.indexOf(getResources().getString(R.string.nullString));
			}
			if (position == -1) {
				list = getMaterialList(false, R.array.frontagematerial, R.array.groundmaterial, R.array.roofmaterial);
				position = list.indexOf(getResources().getString(R.string.nullString));
			}
			if (position != -1) {
				spinMaterial.setSelection(position);
			}
		}
		return box;

	}

	//TODO Adddescription for javadoc
	private OnItemSelectedListener itemSelectedListenerType = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position,
				long id) {
			if (spinType.getItemAtPosition(position).equals(getResources().getString(R.string.frontage))) {
				List<String> list = getMaterialList(true, R.array.frontagematerial);
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list);
				spinMaterial.setAdapter(adapter);
			} else if (spinType.getItemAtPosition(position).equals(getResources().getString(R.string.ground))) {
				List<String> list = getMaterialList(true, R.array.groundmaterial);
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list);
				spinMaterial.setAdapter(adapter);
			} else if (spinType.getItemAtPosition(position).equals(getResources().getString(R.string.roof))) {
				List<String> list = getMaterialList(true, R.array.roofmaterial);
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list);
				spinMaterial.setAdapter(adapter);
			} else if (spinType.getItemAtPosition(position).equals(getResources().getString(R.string.other))) {
				List<String> list = getMaterialList(false, R.array.frontagematerial, R.array.groundmaterial, R.array.roofmaterial);
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list);
				spinMaterial.setAdapter(adapter);
				spinType.setVisibility(View.GONE);
				typeCustom.setVisibility(View.VISIBLE);
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
		}
	};

	//TODO Adddescription for javadoc
	private List<String> getMaterialList(boolean addMore, int... arrayIds) {
		List<String> list = new ArrayList<String>();
		for (int arrayId : arrayIds) {
			String[] array = getResources().getStringArray(arrayId);
			List<String> subList = new ArrayList<String>(Arrays.asList(array));
			subList.remove(getResources().getString(R.string.nullString));
			subList.remove(getResources().getString(R.string.other));
			subList.remove(getResources().getString(R.string.more));
			java.util.Collections.sort(subList);
			list = fusionSortedList(list, subList);
		}
		list.add(0, getResources().getString(R.string.nullString));
		list.add(getResources().getString(R.string.other));
		if (addMore) {
			list.add(getResources().getString(R.string.more));
		}
		return list;
		//return new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list);
	}

	//TODO Adddescription for javadoc
	private OnItemSelectedListener itemSelectedListenerMaterial = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view, int position,
				long id) {
			if (spinMaterial.getItemAtPosition(position).equals(getResources().getString(R.string.other))) {
				spinMaterial.setVisibility(View.GONE);
				materialCustom.setVisibility(View.VISIBLE);
			} else if (spinMaterial.getItemAtPosition(position).equals(getResources().getString(R.string.more))) {
				List<String> list = getMaterialList(false, R.array.frontagematerial, R.array.groundmaterial, R.array.roofmaterial);
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list);
				spinMaterial.setAdapter(adapter);
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
		}
	};

	//TODO Adddescription for javadoc
	private OnClickListener validation = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			String selection;
			if (spinType.getVisibility() == View.VISIBLE) {
				selection = (String) spinType.getSelectedItem();
				if (!selection.equals("")) {
					UtilCharacteristicsZone.setTypeForSelectedZones(selection);
				}
			} else {
				UtilCharacteristicsZone.setTypeForSelectedZones(
						((SpannableStringBuilder) typeCustom.getText()).toString());
			}
			if (spinMaterial.getVisibility() == View.VISIBLE) {
				selection = (String) spinMaterial.getSelectedItem();
				if (!selection.equals("")) {
					UtilCharacteristicsZone.setMaterialForSelectedZones(selection);
				}
			} else {
				UtilCharacteristicsZone.setMaterialForSelectedZones(
						((SpannableStringBuilder) materialCustom.getText()).toString());
			}
			CharacteristicsFragment.getMyImage().invalidate();
			box.dismiss();
		}
	};

	//TODO Adddescription for javadoc
	private List<String> fusionSortedList(List<String> list1, List<String> list2) {
		List<String> result = new ArrayList<String>();
		while (!list1.isEmpty()) {
			String str1 = list1.remove(0);
			while (!list2.isEmpty()) {
				if (str1.compareToIgnoreCase(list2.get(0)) < 0) {
					break;
				} else {
					String str2 = list2.remove(0);
					result.add(str2);
				}
			}
			result.add(str1);
		}
		while (!list2.isEmpty()) {
			String str2 = list2.remove(0);
			result.add(str2);
		}
		return result;
	}

	//TODO Adddescription for javadoc
	private OnClickListener chooseColor = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// Create the color picker dialog (with the actual color of the selected zone)
			AmbilWarnaDialog dialog = new AmbilWarnaDialog(getActivity(),
					UtilCharacteristicsZone.getColorForSelectedZones(),
					new OnAmbilWarnaListener() {
						@Override
						public void onOk(AmbilWarnaDialog dialog, int color) {
							// Modify the color of the zone
							UtilCharacteristicsZone
									.setColorForSelectedZones(color);
							colorView.setBackgroundColor(UtilCharacteristicsZone
									.getColorForSelectedZones());
						}

						@Override
						public void onCancel(AmbilWarnaDialog dialog) {
						}
					});

			// Add a title to the dialog
			dialog.getDialog().setTitle(R.string.definition_dialog_color);

			dialog.getDialog().show();
		}
	};

	/**
	 * Action realized when the user cancel the dialog (touch outside the dialog
	 * or press the back button)
	 */
	@Override
	public void onCancel(DialogInterface dialog) {
	}
}