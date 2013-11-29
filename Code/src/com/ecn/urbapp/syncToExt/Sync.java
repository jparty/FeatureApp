package com.ecn.urbapp.syncToExt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ecn.urbapp.db.Element;
import com.ecn.urbapp.db.ElementType;
import com.ecn.urbapp.db.GpsGeom;
import com.ecn.urbapp.db.LocalDataSource;
import com.ecn.urbapp.db.MainActivity;
import com.ecn.urbapp.db.Material;
import com.ecn.urbapp.db.MySQLiteHelper;
import com.ecn.urbapp.db.Photo;
import com.ecn.urbapp.db.PixelGeom;
import com.ecn.urbapp.db.Project;
import com.google.gson.Gson;

public class Sync
{

	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	public Photo SyncablePhoto;
	public Project SyncableProject;
	public GpsGeom SyncableGpsGeom;
	public List<Element> SyncableElements;
	public List<PixelGeom> SyncablePixelGeoms;
	public List<Material> SyncableMaterials;
	public List<ElementType> SyncableElementTypes;
	public Activity context;

	public Boolean doSync(long photoID)
	{
		Boolean success = false;
		boolean successFromLocal = this.getAllFromLocal(photoID);
		if (successFromLocal)
		{
			try
			{
				this.syncInfoPhoto(this.SyncablePhoto);
				this.syncInfoGpsGeom(this.SyncableGpsGeom);
				this.syncInfoProject(this.SyncableProject);
				this.syncInfoElement(this.SyncableElements);
				this.syncInfoPixelGeom(this.SyncablePixelGeoms);
				this.syncInfoMaterial(this.SyncableMaterials);
				this.syncInfoElementType(this.SyncableElementTypes);
				success = true;
			}
			catch (Exception e)
			{
			}
		}
		return success;
	}

	public long getProjectID(long photoID)
	{
		Cursor cursor = database.query(MySQLiteHelper.TABLE_COMPOSED, new String[] {MySQLiteHelper.COLUMN_PROJECTID}, 
				MySQLiteHelper.COLUMN_PHOTOID + " = \"" + photoID + "\"", null, null, null, null);
		long projectID = cursor.getLong(0);
		return projectID;
	}
	public long getGpsGeomID(long photoID)
	{
		Cursor cursor = database.query(MySQLiteHelper.TABLE_PHOTO, new String[] {MySQLiteHelper.COLUMN_GPSGEOMID},
				MySQLiteHelper.COLUMN_PHOTOID + " = \"" + photoID + "\"", null, null, null, null);
		long gpsGeomID = cursor.getLong(0);
		return gpsGeomID;
	}
	public List<Long> getElementIDs(long photoID)
	{
		Cursor cursor = database.query(MySQLiteHelper.TABLE_ELEMENT, new String[] {MySQLiteHelper.COLUMN_ELEMENTID},
				MySQLiteHelper.COLUMN_PHOTOID + " = \"" + photoID + "\"", null, null, null, null);
		List<Long> ElementIDs = new ArrayList<Long>();
		cursor.moveToFirst();
		while (!cursor.isAfterLast())
		{
			ElementIDs.add(cursor.getLong(0));
			cursor.moveToNext();
		}
		return ElementIDs;
	}
	public List<Long> getPixelGeomIDs(long photoID)
	{
		Cursor cursor = database.query(MySQLiteHelper.TABLE_PIXELGEOM, new String [] {MySQLiteHelper.COLUMN_PIXELGEOMID},
				MySQLiteHelper.COLUMN_PHOTOID + " = \"" + photoID + "\"", null, null, null, null);
		List<Long> PixelGeomIDs = new ArrayList<Long>();
		cursor.moveToFirst();
		while (!cursor.isAfterLast())
		{
			PixelGeomIDs.add(cursor.getLong(0));
			cursor.moveToNext();
		}
		return PixelGeomIDs;
	}
	public List<Long> getMaterialIDs(long photoID)
	{
		Cursor cursor = database.query(MySQLiteHelper.TABLE_ELEMENT, new String[] {MySQLiteHelper.COLUMN_MATERIALID},
				MySQLiteHelper.COLUMN_PHOTOID + " = \"" + photoID + "\"", null, null, null, null);
		List<Long> MaterialIDs = new ArrayList<Long>();
		cursor.moveToFirst();
		while (!cursor.isAfterLast())
		{
			MaterialIDs.add(cursor.getLong(0));
			cursor.moveToNext();
		}
		return MaterialIDs;
	}
	public List<Long> getElementTypeIDs(long photoID)
	{
		Cursor cursor = database.query(MySQLiteHelper.TABLE_ELEMENT, new String[] {MySQLiteHelper.COLUMN_ELEMENTTYPEID},
				MySQLiteHelper.COLUMN_PHOTOID + " = \"" + photoID + "\"", null, null, null, null);
		List<Long> ElementTypeIDs = new ArrayList<Long>();
		cursor.moveToFirst();
		while (!cursor.isAfterLast())
		{
			ElementTypeIDs.add(cursor.getLong(0));
			cursor.moveToNext();
		}
		return ElementTypeIDs;
	}

	public boolean getAllFromLocal(long photoID)
	{
		boolean success = false;
		try
		{
			LocalDataSource temp = ((MainActivity)this.context).getDatasource();
			this.SyncablePhoto = temp.getPhotoWithID(photoID);
			this.SyncableProject = temp.getProjectWithId(getProjectID(photoID));
			this.SyncableGpsGeom = temp.getGpsGeomWithID(getGpsGeomID(photoID));
			for (long elementID : getElementIDs(photoID))
			{
				this.SyncableElements.add(temp.getElementWithID(elementID));
			}
			for (long pixelGeomID : getPixelGeomIDs(photoID))
			{
				this.SyncablePixelGeoms.add(temp.getPixelGeomWithID(pixelGeomID));
			}
			for (long materialID : getMaterialIDs(photoID))
			{
				this.SyncableMaterials.add(temp.getMaterialWithID(materialID));
			}
			for (long elementTypeID : getElementTypeIDs(photoID))
			{
				this.SyncableElementTypes.add(temp.getElementTypeWithID(elementTypeID));
			}
			success = true;
		}
		catch (Exception e)
		{
		}
		return success;
	}

	public boolean syncInfoGpsGeom(GpsGeom concernedGpsGeom)
	{
		Boolean success = false;
		try
		{
			Gson gson = new Gson();
			String jSonGpsGeom = gson.toJson(concernedGpsGeom);
			postData(jSonGpsGeom);
			success = true;
		}
		catch (Exception e)
		{
		}
		return success;
	}
	public boolean syncInfoPhoto(Photo concernedPhoto)
	{
		Boolean success = false;
		try
		{
			Gson gson = new Gson();
			String jSonPhoto = gson.toJson(concernedPhoto);
			postData(jSonPhoto);
			success = true;
		}
		catch (Exception e)
		{
		}
		return success;
	}
	public boolean syncInfoProject(Project concernedProject)
	{
		Boolean success = false;
		try
		{
			Gson gson = new Gson();
			String jSonProject = gson.toJson(concernedProject);
			postData(jSonProject);
			success = true;
		}
		catch (Exception e)
		{
		}
		return success;
	}
	public boolean syncInfoElement(List<Element> concernedElements)
	{
		Boolean success = false;
		try
		{
			Gson gson = new Gson();
			String jSonElement;
			for (Element concernedElement : concernedElements)
			{
				jSonElement= gson.toJson(concernedElement);
				postData(jSonElement);
			}
			success = true;
		}
		catch (Exception e)
		{
		}
		return success;
	}
	public boolean syncInfoPixelGeom(List<PixelGeom> concernedPixelGeoms)
	{
		Boolean success = false;
		try
		{
			Gson gson = new Gson();
			String jSonPixelGeom;
			for (PixelGeom concernedPixelGeom : concernedPixelGeoms)
			{
				jSonPixelGeom = gson.toJson(concernedPixelGeom);
				postData(jSonPixelGeom);
			}
			success = true;
		}
		catch (Exception e)
		{
		}
		return success;
	}
	public boolean syncInfoMaterial(List<Material> concernedMaterials)
	{
		Boolean success = false;
		try
		{
			Gson gson = new Gson();
			String jSonMaterial;
			for (Material concernedMaterial : concernedMaterials)
			{
				jSonMaterial = gson.toJson(concernedMaterial);
				postData(jSonMaterial);
			}
			success = true;
		}
		catch (Exception e)
		{
		}
		return success;
	}
	public boolean syncInfoElementType(List<ElementType> concernedElementTypes)
	{
		Boolean success = false;
		try
		{
			Gson gson = new Gson();
			String jSonElementType;
			for (ElementType concernedElementType : concernedElementTypes)
			{
				jSonElementType = gson.toJson(concernedElementType);
				postData(jSonElementType);
			}
			success = true;
		}
		catch (Exception e)
		{
		}
		return success;
	}
	public void postData(String param) {
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost("http://192.168.34.1/SQLite/");
		try {
			// create a list to store HTTP variables and their values
			List<BasicNameValuePair> nameValuePairs = new ArrayList<BasicNameValuePair>();
			// add an HTTP variable and value pair
			nameValuePairs.add(new BasicNameValuePair("myHttpData", param));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			// send the variable and value, in other words post, to the URL
			httpclient.execute(httppost);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} ;
	}
}
