package com.ecn.urbapp.fragments;

import android.app.Fragment;
import android.content.Intent;

import android.graphics.Bitmap;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.ecn.urbapp.R;

import com.ecn.urbapp.activities.LoadLocalProjectsActivity;
import com.ecn.urbapp.activities.Test;
import com.ecn.urbapp.activities.TestPhoto;

import com.ecn.urbapp.utils.ImageDownloader;
import com.ecn.urbapp.utils.UploadImage;


/**
 * This is the fragment used to make the user choose between the differents type of project.
 * 
 * @author	COHENDET Sébastien
 * 			DAVID Nicolas
 * 			GUILBART Gabriel
 * 			PALOMINOS Sylvain
 * 			PARTY Jules
 * 			RAMBEAU Merwan
 * 			
 */

public class HomeFragment extends Fragment implements OnClickListener{
	
	/**
	 * creating the interface
	 */
	private Button loadLocal = null ;
	private Button test = null ;
	private Button testPhoto = null ;
	private Button downloadImage;
	private ImageDownloader imageDownloader = new ImageDownloader();
	private ImageView image;
	private Button uploadImageButton;
	private String imageStoredUrl;

	private String[] URLs={
			"http://static.tumblr.com/604c1f8526cf8f5511c6d7a5e32f9abd/u00yntv/2wEmlbf4d/tumblr_static_baby_otter.jpg",
			"http://axemdo.files.wordpress.com/2010/07/loutre1.jpg",
			"http://www.spaycificzoo.com/wp-content/uploads/2011/11/loutre_naine1-300x232.jpg"
	};
	



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View v = inflater.inflate(R.layout.layout_home, null);

		loadLocal=(Button)v.findViewById(R.id.home_loadLocalProject);
		loadLocal.setOnClickListener(this);
		test=(Button)v.findViewById(R.id.home_test);
		test.setOnClickListener(this);
		
		testPhoto=(Button)v.findViewById(R.id.home_test_photo);
		testPhoto.setOnClickListener(this);
		
		Intent i = getActivity().getIntent();
		
		downloadImage = (Button) v.findViewById(R.id.home_loadDistantlProject);
		downloadImage.setOnClickListener(getImage);
		uploadImageButton = (Button) v.findViewById(R.id.home_savePicture);
		uploadImageButton.setOnClickListener(uploadImage);
		image = (ImageView) v.findViewById(R.id.home_image_loadDistantProject);

		return v;
		
	}
	
	@Override
	public void onClick(View v) {
		Intent i = null;
		switch (v.getId()) {
		case R.id.home_loadLocalProject:
			i = new Intent(this.getActivity(), LoadLocalProjectsActivity.class);
			startActivity(i);			
			break;
			
		case R.id.home_test:
			i = new Intent(this.getActivity(), Test.class);
			startActivity(i);			
			break;
			
		case R.id.home_test_photo:
			i = new Intent(this.getActivity(), TestPhoto.class);
			startActivity(i);			
			break;

		default:
			break;
		}
	}

    private OnClickListener getImage = new OnClickListener() {
        
        @Override
        public void onClick(View v) {
        	imageStoredUrl = imageDownloader.download(URLs[(int) (Math.random()*3)], image, "img"+((int)(Math.random()*3+1))+".png");
        }
    };

    private OnClickListener uploadImage = new OnClickListener() {
        
        @Override
        public void onClick(View v) {
        	image.buildDrawingCache();
        	
        	Bitmap bmap = image.getDrawingCache();
        	Intent i = new Intent(getActivity(), UploadImage.class);
			i.putExtra("imageUrl", imageStoredUrl);
			
			startActivity(i);
        }
    };
}