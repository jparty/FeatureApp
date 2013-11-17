// thank's to 
// http://www.vogella.com/articles/AndroidSQLite/article.html#overview_sqlite
// for this usefull tuto

package com.ecn.urbapp.db;


import java.util.List;
import java.util.Random;

import android.app.ListActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.ecn.urbapp.R;
import com.ecn.urbapp.syncToExt.BackTaskExport;
import com.ecn.urbapp.syncToExt.BackTaskExportAllDB;
import com.ecn.urbapp.syncToLocal.BackTaskImport;

public class MainActivity extends ListActivity {
	
	private LocalDataSource datasource;
	
	public LocalDataSource getDatasource() {
		return datasource;
	}

	public void setDatasource(LocalDataSource datasource) {
		this.datasource = datasource;
	}

	Button add = null;
	Button delete = null;

	//creating the widget for AsyncTask
	//private ProgressBar mProgressBar;
	private Button importButton;
	private Button exportButton;
	EditText toFile = null;
	private Button exportDBButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout layout = null;
        layout = (LinearLayout) LinearLayout.inflate(this, R.layout.layout_loadlocaldb, null);
        setContentView(layout);
        
        //Link to id the Widget for updating database in local
        add = (Button)findViewById(R.id.add);
        delete = (Button)findViewById(R.id.delete);
        toFile = (EditText)findViewById(R.id.toExterneFile);
        
        //Link to id the Widget for the AsyncTask
	   //mProgressBar = (ProgressBar) findViewById(R.id.pBAsync);
	    importButton = (Button) findViewById(R.id.btnLaunchImport);
	    exportButton = (Button) findViewById(R.id.btnLaunchExport);
	    exportDBButton = (Button) findViewById(R.id.btnLaunchDBExport);
        
	    
        datasource = new LocalDataSource(this);
        datasource.open();

        List<Project> values = recupProject();
        
        //use the SimpleCursorAdapter to show the elements in a ListView
        ArrayAdapter<Project> adapter = new ArrayAdapter<Project>(this, android.R.layout.simple_expandable_list_item_1,values);
        setListAdapter(adapter);
        
        
        add.setOnClickListener(clickListenerBoutonsAdd);
        delete.setOnClickListener( clickListenerBoutonsDelete);
        importButton.setOnClickListener( clickListenerBackTaskImport);
        exportButton.setOnClickListener( clickListenerBackTaskExport);
        exportDBButton.setOnClickListener( clickListenerBackTaskExportAllDB);
        
        
        
    }

    @Override
    protected void onResume(){
    	datasource.open();
    	super.onResume();
    }

    @Override
    protected void onPause(){
    	datasource.close();
    	super.onPause();
    }
    
    
    public void refreshList(){      
    	List<Project> values = recupProject();
        ArrayAdapter<Project> adapter = new ArrayAdapter<Project>(this, android.R.layout.simple_expandable_list_item_1,values);
        setListAdapter(adapter);  
   }

    public List<Project> recupProject() {
       // datasource = new LocalDataSource(this);
       // datasource.open();
        List<Project> values = this.datasource.getAllProjects();
        return values;
         
    }
    
    
    
    //redefine the Onclick actions thanks to an attribute 
    private OnClickListener clickListenerBoutonsAdd = new OnClickListener(){
    	public void onClick(View view){
    		ArrayAdapter<Project> adapter = (ArrayAdapter<Project>) getListAdapter();
    		Project Project = null;
    			String[] Projects = new String[] {"Cool", "Very nice", "Hate it"};
    			int nextInt = new Random().nextInt(3);
    			//save the new Project to database
    			Project = datasource.createProject(Projects[nextInt]);
    			adapter.add(Project);
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
    
    private OnClickListener clickListenerBackTaskImport = new OnClickListener(){
    	public void onClick(View view){
				BackTaskImport calculImport = new BackTaskImport(MainActivity.this);
				calculImport.execute();
				

    	};
    };
    
    private OnClickListener clickListenerBackTaskExport = new OnClickListener(){
    	public void onClick(View view){
				BackTaskExport calculExport = new BackTaskExport(MainActivity.this);
				calculExport.execute();
				

    	};
    };
    
    private OnClickListener clickListenerBackTaskExportAllDB = new OnClickListener(){
    	public void onClick(View view){
				BackTaskExportAllDB calculExportDB = new BackTaskExportAllDB(MainActivity.this);
				calculExportDB.execute();
				

    	};
    };
    

}
