package com.example.clientdatalist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class CDLFormula extends Activity{
	
	private static final String TAG = "CDLFormulas";
	protected static final String UPDATED_INFO = null;
	EditText product, oz, color, liftTime;
	Button save, add;
	ListView listFormulas;
	ArrayAdapter<String> cFormulas;
	String info;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "===CREATE===");
		
		setContentView(R.layout.activity_cdl_formula);
						
		setResult(Activity.RESULT_CANCELED);
		
		setup();
		
		Intent i = getIntent();
		if(!i.getStringExtra("info").contentEquals("none")){
			info = i.getStringExtra("info");
			update();
		}
	}

	private void update() {
		Log.d(TAG, "===UPDATE===");	
		
		String [] tmp;
		tmp = info.split(";");
		for(int i = 0; i <= tmp.length-1; i++){
			cFormulas.add(tmp[i].trim());
		}
	}

	private void setup() {
		Log.d(TAG, "===SETUP===");
		
		product = (EditText) findViewById(R.id.etFProd);
		oz = (EditText) findViewById(R.id.etFounces);
		color = (EditText) findViewById(R.id.etFcolor);
		liftTime = (EditText) findViewById(R.id.etFlTime);
		save = (Button) findViewById(R.id.bFormSave);
		save.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				Log.d(TAG, "===SAVE===");
				if(cFormulas.getCount() !=0) {
                    String  uInfo = "";
                    for(int i = 0; i <= cFormulas.getCount()-2; i++){
                        uInfo = uInfo + cFormulas.getItem(i) + ";\n";
                    }
                    uInfo = uInfo + cFormulas.getItem(cFormulas.getCount()-1);
                    Intent i = new Intent();
                    i.putExtra(UPDATED_INFO, uInfo);

                    setResult(Activity.RESULT_OK, i);
                    finish();
                }else{
                    setResult(Activity.RESULT_CANCELED);
                    finish();
                }

			}
			
		});
		add = (Button) findViewById(R.id.bFadd);
		add.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Log.d(TAG, "===ADD===");
				
				if(!product.getText().toString().contentEquals("")){
					cFormulas.add("Product: " + product.getText().toString() + ": "
							+ "Oz: " + oz.getText().toString() + ": "
							+ "Color: " + color.getText().toString() + ": "
							+ "Lift Time: " + liftTime.getText().toString());
					product.setText("");
					oz.setText("");
					color.setText("");
					liftTime.setText("");
				}else{
					Toast.makeText(getApplicationContext(), "Please Enter a Prod. Name", Toast.LENGTH_SHORT).show();
				}				
			}
			
		});
		cFormulas = new ArrayAdapter<String>(this.getApplicationContext(), R.layout.added_formulas);
		listFormulas = (ListView) findViewById(R.id.lvFormulas);
		listFormulas.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Log.d(TAG, "===SETONITEMCLICK===");
				
				String edit = cFormulas.getItem(arg2);
				String [] tmp = edit.split(":");
				product.setText(tmp[1]);
				oz.setText(tmp[3]);
				color.setText(tmp[5]);
				liftTime.setText(tmp[7]);
				cFormulas.remove(edit);
			}
			
		});
		listFormulas.setAdapter(cFormulas);
		registerForContextMenu(listFormulas);
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
	    String rMove = cFormulas.getItem(info.position);
	    cFormulas.remove(rMove);
	    
	    return super.onContextItemSelected(item);
	}

	@Override
	protected void onStop() {
		Log.d(TAG, "===STOP===");
		unregisterForContextMenu(listFormulas);
		super.onStop();
	}
}
