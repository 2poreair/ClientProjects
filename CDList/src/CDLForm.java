package com.example.clientdatalist;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CDLForm extends Activity implements OnClickListener{

	private static final String TAG = "CDLFORM";
	private static final int PROFILE = 0;
	private static final int STYLIST = 1;
	private static final int FORMULA = 2;
	
	EditText name, cNumber, aComment;
	Button profile, stylist, formula, comments;
	String cName;
	TextView tFormula;
	String[] fcontents = {"","","","","",""};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.d(TAG, "===CREATE===");

		setContentView(R.layout.activity_client_data_list_form);

		setup();
		// get the intent that started this activity
		Intent i = getIntent();
		cName = i.getStringExtra("cName");

		if (!cName.contentEquals("new")){
			load();
		}		
		//delete a file with = myContext.deleteFile(fileName);
	}

	private void addToTxtViews() {
		Log.d(TAG, "===ADDTOTXTVIEW===");
		
		name.setText(fcontents[0]);
		cName = fcontents[0];
		cNumber.setText(fcontents[1]);
		tFormula.setText(fcontents[4]);
		aComment.setText(fcontents[5]);
	}

	private void load() {
		Log.d(TAG, "===LOAD===");

		FileInputStream fis = null;
		String content = " ";
		try {
			fis = openFileInput(cName);
			byte[] dataArray = new byte[fis.available()];
			while (fis.read(dataArray) != -1) {
				content = new String(dataArray);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				fis.close();
				String split = "'mSplit'";
				String [] temp;
				temp = content.split(split);
				for(int i = 0; i <= temp.length-1; i++){
					fcontents[i] = temp[i].trim();
				}
				addToTxtViews();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void setup() {
		Log.d(TAG, "===SETUP===");

		name = (EditText) findViewById(R.id.etCName);
		name.setHint("Client's Name");
		cNumber = (EditText) findViewById(R.id.etCNum);
		profile = (Button) findViewById(R.id.bProfile);
		profile.setOnClickListener(this);
		stylist = (Button) findViewById(R.id.bStylist);
		stylist.setOnClickListener(this);
		formula = (Button) findViewById(R.id.bFormula);
		formula.setOnClickListener(this);
		comments = (Button) findViewById(R.id.bComments);
		comments.setOnClickListener(this);
		aComment = (EditText) findViewById(R.id.etStAComments);
		tFormula = (TextView) findViewById(R.id.tvFormula);
	}
	@Override
	public void onClick(View arg0) {
		Log.d(TAG, "===CLICK===");
		if(name.getText().toString().contentEquals("")){
			Toast.makeText(getApplicationContext(), "Please Enter a Name", Toast.LENGTH_SHORT).show();
			return;
		}
		
		Intent i;
		
		switch(arg0.getId()){
		
		case R.id.bProfile:
			i = new Intent(getApplicationContext(), com.example.clientdatalist.CDLProfile.class);
			i.putExtra("name", name.getText().toString());
			if(fcontents[2].contentEquals("")){
				i.putExtra("info", "none");
			}else{
				i.putExtra("info", fcontents[2]);
			}
			startActivityForResult(i, PROFILE);
			break;
		
		case R.id.bStylist:
			i = new Intent(getApplicationContext(), com.example.clientdatalist.CDLStylist.class);
			i.putExtra("name", name.getText().toString());
			if(!fcontents[3].contentEquals("")){
				i.putExtra("info", fcontents[3]);
			}else{
				i.putExtra("info", "none");
			}
			startActivityForResult(i, STYLIST);
			break;
		case R.id.bFormula:
			
			i = new Intent(getApplicationContext(), com.example.clientdatalist.CDLFormula.class);
			if(fcontents[4].contentEquals("")){
				i.putExtra("info", "none");
			}else{
				i.putExtra("info", fcontents[4]);
			}
			startActivityForResult(i, FORMULA);
			break;
		case R.id.bComments:
			
			fcontents[5] = aComment.getText().toString();
			
			try {
				deleteFile();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				update();
			}
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.d(TAG, "===OARESULT===");
		
		if(resultCode == Activity.RESULT_OK){
			String info;
			switch(requestCode){
			case PROFILE:
				
					info = data.getExtras().getString(CDLProfile.UPDATED_INFO);
					fcontents[2] = info;
					
				break;
			case STYLIST:
				
					info = data.getExtras().getString(CDLStylist.UPDATED_INFO);
					fcontents[3] = info;
				
				break;
			case FORMULA:
				
				info = data.getExtras().getString(CDLFormula.UPDATED_INFO);
				fcontents[4] = info;
				
				break;
			}
			try {
				deleteFile();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				update();
			}
		}
	}

	private void deleteFile() throws FileNotFoundException {
		Log.d(TAG, "===UPDATE===");
		
		this.deleteFile(cName);
	}
		
	private void update(){
		fcontents[0] = name.getText().toString();
		fcontents[1] = cNumber.getText().toString();
		
		Toast.makeText(getApplicationContext(), "Updating one moment please", Toast.LENGTH_LONG).show();
		
		FileOutputStream fos = null;
		String wFile = fcontents[0] + "'mSplit'" +fcontents[1] + "'mSplit'" + fcontents[2] + "'mSplit'"
				+ fcontents[3] + "'mSplit'" + fcontents[4] + "'mSplit'" + fcontents[5];
		try {
			fos = openFileOutput(name.getText().toString(), Context.MODE_PRIVATE);
			fos.write(wFile.getBytes());
			fos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			addToTxtViews();
		}
	}	
}
