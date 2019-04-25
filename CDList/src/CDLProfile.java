package com.example.clientdatalist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CDLProfile extends Activity{
	
	public static String UPDATED_INFO = "updated_info";
	TextView cName;
	EditText date, birthday, wPhone, hPhone, address, cPhone, 
	email, occEmp, preferred, allergies, comProds, comRet, referred, remarks;
	Button save;
	String vInfo[] = {"","","","","","","","","","","","","","",
			"","","","","","","","","","","","","",""};
	String info;
	
	private static final String TAG = "CDLPROFILE";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "===CREATE===");
		setContentView(R.layout.activity_cdl_profile);
		
		//set result cancelled incase the user backs out
		setResult(Activity.RESULT_CANCELED);
		
		setup();
		
		Intent i = getIntent();
		cName.setText(i.getStringExtra("name"));
		info = i.getStringExtra("info");
		if(!info.contentEquals("none")){
			addInfo();
		}
	}

	private void addInfo() {
		
		String[] temp = info.split(":");
		for(int i = 0; i<= temp.length-1; i++){
			vInfo[i] = temp[i].trim();
		}
		
		if(vInfo[1] != null) date.setText(vInfo[1]);
		if(vInfo[3] != null) birthday.setText(vInfo[3]);
		if(vInfo[5] != null) wPhone.setText(vInfo[5]);
		if(vInfo[7] != null) hPhone.setText(vInfo[7]);
		if(vInfo[9] != null) address.setText(vInfo[9]);
		if(vInfo[11]!= null) cPhone.setText(vInfo[11]);
		if(vInfo[13]!= null) email.setText(vInfo[13]);
		if(vInfo[15]!= null) occEmp.setText(vInfo[15]);
		if(vInfo[17]!= null) preferred.setText(vInfo[17]);
		if(vInfo[19]!= null) allergies.setText(vInfo[19]);
		if(vInfo[21]!= null) comProds.setText(vInfo[21]);
		if(vInfo[23]!= null) comRet.setText(vInfo[23]);
		if(vInfo[25]!= null) referred.setText(vInfo[25]);
		if(vInfo[27]!= null) remarks.setText(vInfo[27]);
	}

	private void setup() {
		Log.d(TAG, "===SETUP===");
		
		cName = (TextView) findViewById(R.id.tvName);
		date = (EditText) findViewById(R.id.etDate);
		birthday = (EditText) findViewById(R.id.etBday);
		wPhone = (EditText) findViewById(R.id.etWPhone);
		hPhone = (EditText) findViewById(R.id.etHPhone);
		address = (EditText) findViewById(R.id.etAddress);
		cPhone = (EditText) findViewById(R.id.etCPhone);
		email = (EditText) findViewById(R.id.etEmail);
		occEmp = (EditText) findViewById(R.id.etOccEmp);
		preferred = (EditText) findViewById(R.id.etPreferred);
		allergies = (EditText) findViewById(R.id.etAllergies);
		comProds = (EditText) findViewById(R.id.etComProds);
		comRet = (EditText) findViewById(R.id.etComRet);
		referred = (EditText) findViewById(R.id.etReferred);
		remarks = (EditText) findViewById(R.id.etRemarks);
		save = (Button) findViewById(R.id.bSave);
		save.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Log.d(TAG, "===click===");
				
				String uInfo = 
						"Date: " + date.getText().toString() + ":\n" 
						+ "Birthday: " + birthday.getText().toString() + ":\n"
						+ "Work Phone: " + wPhone.getText().toString() + ":\n"
						+ "Home Phone: " + hPhone.getText().toString() + ":\n"
						+ "Address: " + address.getText().toString() + ":\n"
						+ "Cell Phone: " + cPhone.getText().toString() + ":\n"
						+ "Email: " + email.getText().toString() + ":\n"
						+ "Occupation/Employeer: " + occEmp.getText() + ":\n"
						+ "Preferred appt day: " + preferred.getText().toString() + ":\n"
						+ "Allergies: " + allergies.getText().toString() + ":\n"
						+ "Common hair care products used: " + comProds.getText().toString() + ":\n"
						+ "Common retailed Products purchased: " + comRet.getText().toString() + ":\n"
						+ "Referred by: " + referred.getText().toString() + ":\n"
						+ "Client's remarks: " + remarks.getText().toString();
				
				Intent i = new Intent();
				i.putExtra(UPDATED_INFO, uInfo);
				
				setResult(Activity.RESULT_OK, i);
				finish();
			}
		});
		
	}

}
