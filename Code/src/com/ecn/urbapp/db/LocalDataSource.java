package com.ecn.urbapp.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class LocalDataSource {
	
	//Database fields
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	
	public MySQLiteHelper getDbHelper() {
		return dbHelper;
	}


	private String[] allColumnsProject = {MySQLiteHelper.COLUMN_PROJECTID, MySQLiteHelper.COLUMN_PROJECTNAME, MySQLiteHelper.COLUMN_GPSGEOMID};
	private String[] allColumnsPhoto = {MySQLiteHelper.COLUMN_PHOTOID, MySQLiteHelper.COLUMN_PHOTODESCRIPTION, MySQLiteHelper.COLUMN_PHOTOAUTHOR, MySQLiteHelper.COLUMN_GPSGEOMID};
	private String[] allColumnsGpsGeom = {MySQLiteHelper.COLUMN_GPSGEOMID, MySQLiteHelper.COLUMN_GPSGEOMCOORD};
	private String[] allColumnsPixelGeom = {MySQLiteHelper.COLUMN_PIXELGEOMID, MySQLiteHelper.COLUMN_PIXELGEOMCOORD};
	private String[] allColumnsMaterial = {MySQLiteHelper.COLUMN_MATERIALID, MySQLiteHelper.COLUMN_MATERIALNAME};
	private String[] allColumnsElementType = {MySQLiteHelper.COLUMN_ELEMENTTYPEID, MySQLiteHelper.COLUMN_ELEMENTTYPENAME};
	private String[] allColumnsComposed = {MySQLiteHelper.COLUMN_PROJECTID, MySQLiteHelper.COLUMN_PHOTOID};
	private String[] allColumnsElement = {MySQLiteHelper.COLUMN_ELEMENTID, MySQLiteHelper.COLUMN_PHOTOID,MySQLiteHelper.COLUMN_MATERIALID, MySQLiteHelper.COLUMN_GPSGEOMID,MySQLiteHelper.COLUMN_PIXELGEOMID, MySQLiteHelper.COLUMN_ELEMENTTYPEID, MySQLiteHelper.COLUMN_ELEMENTCOLOR};
		
	//constructor
	public LocalDataSource(Context context){
		dbHelper = new MySQLiteHelper(context);
	}
		
	//Open and close database
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	
	public void close(){
		dbHelper.close();
	}
	
	/**
	 * creating a new project in the database
	 * @param str
	 * @return
	 */
	public Project createProject (String str){
		ContentValues values = new ContentValues(); 
		values.put(MySQLiteHelper.COLUMN_PROJECTNAME, str);
		long insertId = database.insert(MySQLiteHelper.TABLE_PROJECT, null, values);
		//TODO check the utily of autoincrement
		Cursor cursor = 
				database.query(
						MySQLiteHelper.TABLE_PROJECT,
						allColumnsProject,
						MySQLiteHelper.COLUMN_PROJECTID+" = "+insertId,
						null, null, null, null);
		cursor.moveToFirst();
		Project newProject = cursorToProject(cursor);//method at the end of the class
		cursor.close();
		return newProject;
	}
	
	//surcharge
	public Project createProject (long id, String str){
        Boolean exist = existProjectWithId(id);
        
        if(exist == true){
        	Project existProject = getProjectWithId(id);
        	Project updatedProject = updateProject(id, existProject, str);
            return updatedProject;
        }
        else {
            ContentValues values = new ContentValues();
            values.put(MySQLiteHelper.COLUMN_PROJECTID, id);
            values.put(MySQLiteHelper.COLUMN_PROJECTNAME, str);
            long insertId = database.insert(MySQLiteHelper.TABLE_PROJECT, null,
                    values);
            Cursor cursor = database.query(MySQLiteHelper.TABLE_PROJECT,
                    allColumnsProject, MySQLiteHelper.COLUMN_PROJECTID + " = " + insertId, null,
                    null, null, null);
            cursor.moveToFirst();
            Project p2 = cursorToProject(cursor);
            cursor.close();
            return p2;
        }
    }
	
	public Project updateProject(Long id, Project project, String descr){
        ContentValues values = new ContentValues();
 
        values.put(MySQLiteHelper.COLUMN_PROJECTNAME, descr);
 
        database.update(MySQLiteHelper.TABLE_PROJECT, values, MySQLiteHelper.COLUMN_PROJECTID + " = " +project.getProjectId(), null);
 
        return getProjectWithId(project.getProjectId());
    }
	
    public Project getProjectWithId(Long id){
        Cursor c = database.query(MySQLiteHelper.TABLE_PROJECT, allColumnsProject, MySQLiteHelper.COLUMN_PROJECTID + " = \"" + id +"\"", null, null, null, null);
        c.moveToFirst();
        Project p1 = cursorToProject(c);
        c.close();
        return p1;
    }
    
    public Boolean existProjectWithId(Long id){
        Cursor c = database.query(MySQLiteHelper.TABLE_PROJECT, allColumnsProject, MySQLiteHelper.COLUMN_PROJECTID + " = \"" + id +"\"", null, null, null, null);
        if(c.getCount()>0){
            c.close();
            return true;
        }
        else {
            c.close();
            return false;
        }
    }
	
	
	
	public void deleteProject(Project p1){
		long id = p1.getProjectId();
		System.out.println("Project deleted with id: "+ id);
		database.delete(MySQLiteHelper.TABLE_PROJECT, MySQLiteHelper.COLUMN_PROJECTID+" = "+ id, null);
	}
	
	public List<Project> getAllProjects(){
		List<Project> projectsList = new ArrayList<Project>();
		
		Cursor cursor = database.query(MySQLiteHelper.TABLE_PROJECT, allColumnsProject, null, null, null, null, null);
		
		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			Project p1 = cursorToProject(cursor);
			projectsList.add(p1);
			cursor.moveToNext();
		}
		cursor.close();
		return projectsList;
	}


  private Project cursorToProject(Cursor cursor) {
    Project p1 = new Project();
    p1.setProjectId(cursor.getLong(0));
    p1.setProjectName(cursor.getString(1));
    return p1;
	
  }
}