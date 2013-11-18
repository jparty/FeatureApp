package com.ecn.urbapp.activities;

import java.util.List;
import java.util.Random;

import android.app.ListActivity;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;

import com.ecn.urbapp.R;
import com.ecn.urbapp.db.GpsGeom;
import com.ecn.urbapp.db.LocalDataSource;
import com.ecn.urbapp.db.MySQLiteHelper;
import com.ecn.urbapp.db.Project;

public class Test extends ListActivity {

	//creating datasource
	private LocalDataSource datasource;
	
	//creating buttons for the TEST
	Button add = null;
	Button delete = null;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_test);
        
        datasource=MainActivity.datasource;
        datasource.open();
        refreshList();
        
        //TODO delete test methods
        add = (Button)findViewById(R.id.add);
        delete = (Button)findViewById(R.id.delete);
        
        add.setOnClickListener(clickListenerBoutonsAdd);
        delete.setOnClickListener( clickListenerBoutonsDelete);

    }
    
    protected void onClose() {      
        datasource.close();
    }
    
    
    /**
     * loading the different projects of the local db
     * @return
     */
    public List<Project> recupProject() {
         
         List<Project> values = this.datasource.getAllProjects();
         
         return values;
          
     }
    
    /**
     * creating a list of project and loads in the view
     */
    public void refreshList(){      
    	
    	List<Project> values = recupProject();
    	
        ArrayAdapter<Project> adapter = new ArrayAdapter<Project>(this, android.R.layout.simple_expandable_list_item_1,values);
        setListAdapter(adapter);  
   }
    
    
    
   //TEST METHODS TO CREATE CONTENT THAT WILL BE DISPLAYED
    private OnClickListener clickListenerBoutonsAdd = new OnClickListener(){
    	public void onClick(View view){
    		ArrayAdapter<Project> adapter = (ArrayAdapter<Project>) getListAdapter();
    		
    		Project p1 = null;
    			String[] Projects = new String[] {"Cool", "Very nice", "Hate it"};
    			int nextInt = new Random().nextInt(3);
    			
    		GpsGeom gps1=null;
    			String[] coord = { new String("47.249069//-1.54820"),new String("50.249069//-8.54820"),new String("20.249069//41.54820")} ;
  
    			//save the new Project to database
    			p1 = datasource.createProject(Projects[nextInt]);
    			//save the gpsgeom to database & project
    				//TODO CREATE A TRANSACTION THE SQL
    			
    			gps1 = datasource.createGPSGeom(coord[(int) (Math.random()*3)],p1.getProjectId());
    			//updating p1 attributes
    			p1.setGpsGeom_id(gps1.getGpsGeomsId());
    			
    			adapter.add(p1);
    			adapter.notifyDataSetChanged();
    	};
    };
    
    private OnClickListener clickListenerBoutonsDelete = new OnClickListener(){
    	public void onClick(View view){
    		ArrayAdapter<Project> adapter = (ArrayAdapter<Project>) getListAdapter();
    		Project Project = null;
    		if (getListAdapter().getCount()>0){
				Project = (Project) getListAdapter().getItem(0);
				datasource.deleteProject(Project);
				adapter.remove(Project);
			}
    		
    			adapter.notifyDataSetChanged();
    	};
    };
}
