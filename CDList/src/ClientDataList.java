package com.example.clientdatalist;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class ClientDataList extends Activity implements OnItemClickListener, OnClickListener{
	private static String TAG = "Main";
	String clientNames[];
	ArrayAdapter<String> display;
	List<String> list;
	Button add;
	ListView clientList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_client_data_list);
		
		setup();
		clientList.setOnItemClickListener(this);
		add.setOnClickListener(this);
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		refresh();
		registerForContextMenu(clientList);
		
	}



	private void setup() {
		Log.d(TAG, "===SETUP===");
		
		clientNames = this.getApplicationContext().fileList();
		list = new ArrayList<String>();
		for(int i = 0; i<clientNames.length; i++){
			if(!clientNames[i].contains("rList"))
                list.add(clientNames[i]);
		}
		java.util.Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
		display = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, list);
		add = (Button) findViewById(R.id.bAdd);
		clientList = (ListView) findViewById(R.id.lvClients);
		clientList.setAdapter(display);
		registerForContextMenu(clientList);
	}

	private void refresh() {
		Log.d(TAG, "===REFRESH===");
		display.clear();
		
		clientNames = getApplicationContext().fileList();
		list = new ArrayList<String>();
		for(int i = 0; i<clientNames.length; i++){
            if(!clientNames[i].contains("rList"))
                list.add(clientNames[i]);
		}
		java.util.Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
		display = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, list);
		clientList.setAdapter(display);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Log.d(TAG, "===onItemClick===");
		
		Intent i = new Intent(getApplicationContext(), com.example.clientdatalist.CDLForm.class);
		i.putExtra("cName", list.get(arg2));
		startActivity(i);
	}

	@Override
	public void onClick(View arg0) {
		Log.d(TAG, "===Click===");
		
		Intent i = new Intent(getApplicationContext(), com.example.clientdatalist.CDLForm.class);
		i.putExtra("cName", "new");
		startActivity(i);
		
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	                                ContextMenuInfo menuInfo) {
		Log.d(TAG, "===Menu===");
		
	    super.onCreateContextMenu(menu, v, menuInfo);
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.context_menu, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		Log.d(TAG, "===MENUSELECT===");
		
	    AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	    String rMove = display.getItem(info.position);
	    display.remove(rMove);
	    deleteFile(rMove);
	    
	    return super.onContextItemSelected(item);
	}

	@Override
	protected void onStop() {
		unregisterForContextMenu(clientList);
		super.onStop();
	}
	
	
}
