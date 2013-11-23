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
import com.ecn.urbapp.activities.MainActivity;
import com.ecn.urbapp.fragments.CharacteristicsFragment;
import com.ecn.urbapp.utils.colorpicker.AmbilWarnaDialog;
import com.ecn.urbapp.utils.colorpicker.AmbilWarnaDialog.OnAmbilWarnaListener;

/**
 * This class creates the dialog that ask the user to choose the characteristics of the
 * zone
 * 
 * @author Jules Party
 * 
 */
public class ConfirmPhotoDialogFragment extends DialogFragment {

	private Dialog box;
	private Button bpok;
	private Button quit;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		box = new Dialog(getActivity());
		box.setContentView(R.layout.layout_confirm_dialog);
		box.setTitle(R.string.confirm_photo);
		box.setCanceledOnTouchOutside(true);
		bpok = (Button)box.findViewById(R.id.confirm_ok);
		bpok.setOnClickListener(listener_ok);
		quit = (Button)box.findViewById(R.id.confirm_quit);
		quit.setOnClickListener(listener_quit);
		return box;
	}
	
	private OnClickListener listener_ok = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			MainActivity.zones=null;
			box.dismiss();
		}
	};
	
	private OnClickListener listener_quit = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			MainActivity.pathImage=null;
			box.dismiss();
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