package com.example.clientdatalist;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class CDLStylist extends Activity{
	private static final String TAG = "CDLStylist";
	public static final String UPDATED_INFO = null;
	TextView name;
	EditText hairCond, hcOne, hairScalp, hcTwo, prefCut,
	relaxer, eFormula;
	Button save;
	String info;
	List<CheckBox> allCheckBoxes = new ArrayList<CheckBox>();
	String vInfo[] = new String[27];
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "===ONCREATE===");
		
		setContentView(R.layout.activity_cdl_stylist);
		
		setup();
		
		setResult(Activity.RESULT_CANCELED);
		
		Intent i = getIntent();
		name.setText(i.getStringExtra("name"));
		info = i.getStringExtra("info");
		if(!info.contentEquals("none")){
			addInfo();
		}
	}

	private void addInfo() {
		Log.d(TAG, "===ADDINFO===");
		
		String[] tmp;
		tmp = info.split(":");
		for(int i = 0; i<=tmp.length-1; i++){
			vInfo[i] = tmp[i].trim();
		}
		hairCond.setText(vInfo[0]);
		hcOne.setText(vInfo[1]);
		hairScalp.setText(vInfo[2]);
		hcTwo.setText(vInfo[3]);
		prefCut.setText(vInfo[4]);
		relaxer.setText(vInfo[5]);
		eFormula.setText(vInfo[26]);
		for(int i = 0; i <= allCheckBoxes.size()-1; i++){
			if(vInfo[i+6].contentEquals("yes")){
				allCheckBoxes.get(i).setChecked(true);
			}
		}
	}

	private void setup() {
		Log.d(TAG, "===SETUP===");
		
		name = (TextView) findViewById(R.id.tvStName);
		hairCond = (EditText) findViewById(R.id.etSThair);
		hcOne = (EditText) findViewById(R.id.etSThairC);
		hairScalp = (EditText) findViewById(R.id.etSTscalp);
		hcTwo = (EditText) findViewById(R.id.etSTscalpC);
		prefCut = (EditText) findViewById(R.id.etSTCStyle);
		relaxer = (EditText) findViewById(R.id.etSTRelax);
		eFormula = (EditText) findViewById(R.id.etSTFormula);
		allCheckBoxes.add((CheckBox) findViewById(R.id.cbEPor));//0
		allCheckBoxes.add((CheckBox) findViewById(R.id.cbPor));
		allCheckBoxes.add((CheckBox) findViewById(R.id.cbNPor));
		allCheckBoxes.add((CheckBox) findViewById(R.id.cbCoarse));
		allCheckBoxes.add((CheckBox) findViewById(R.id.cbMed));
		allCheckBoxes.add((CheckBox) findViewById(R.id.cbFine));
		allCheckBoxes.add((CheckBox) findViewById(R.id.cbPerm));
		allCheckBoxes.add((CheckBox) findViewById(R.id.cbSPerm));
		allCheckBoxes.add((CheckBox) findViewById(R.id.cbToned));
		allCheckBoxes.add((CheckBox) findViewById(R.id.cbHigh));
		allCheckBoxes.add((CheckBox) findViewById(R.id.cbLLight));
		allCheckBoxes.add((CheckBox) findViewById(R.id.cbBleached));
		allCheckBoxes.add((CheckBox) findViewById(R.id.cbHbase));
		allCheckBoxes.add((CheckBox) findViewById(R.id.cbOther));
		allCheckBoxes.add((CheckBox) findViewById(R.id.cbExo));
		allCheckBoxes.add((CheckBox) findViewById(R.id.cbAcid));
		allCheckBoxes.add((CheckBox) findViewById(R.id.cbAlk));
		allCheckBoxes.add((CheckBox) findViewById(R.id.cbMild));
		allCheckBoxes.add((CheckBox) findViewById(R.id.cbReg));
		allCheckBoxes.add((CheckBox) findViewById(R.id.cbSuper));//19
		for(int i = 0; i <= 26; i++){
			vInfo[i] = "";
		}
		save = (Button) findViewById(R.id.bSTSave);
		save.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Log.d(TAG, "===click===");
				//0-5
				String uInfo = hairCond.getText().toString() + ":"
						+ hcOne.getText().toString() + ":"
						+ hairScalp.getText().toString() + ":"
						+ hcTwo.getText().toString() + ":"
						+ prefCut.getText().toString() + ":"
						+ relaxer.getText().toString() + ":";
				//6-25
				for(int i = 0 ; i <= allCheckBoxes.size()-1; i++){
					if(allCheckBoxes.get(i).isChecked()){
						uInfo = uInfo + "yes:";
					}else{
						uInfo = uInfo + "no:";
					}
				}
				//26
				uInfo = uInfo + eFormula.getText().toString();
				Intent i = new Intent();
				i.putExtra(UPDATED_INFO, uInfo);
				setResult(Activity.RESULT_OK, i);
				finish();
			}
		});
	}

}
