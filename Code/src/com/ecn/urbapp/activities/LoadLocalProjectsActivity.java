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
import com.ecn.urbapp.db.LocalDataSource;
import com.ecn.urbapp.db.MySQLiteHelper;
import com.ecn.urbapp.db.Project;

public class LoadLocalProjectsActivity extends ListActivity {

	//creating datasource
	private LocalDataSource datasource;
	
	//creating buttons for the TEST
	Button add = null;
	Button delete = null;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_loadlocaldb);
        
        datasource=MainActivity.datasource;
        datasource.open();
        refreshList();
        
        
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
    
}
