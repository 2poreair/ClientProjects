package com.allhustlenoluck.developer.snax2poreair.spiritualsoldierringtones;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

/*
*created by: 2poreair on 03/10/16
*/

public class SpiritualSoldierRingtone extends AppCompatActivity {

    ListView container;
    ArrayAdapter<String> filler;
    ArrayList<String> list;
    WebView web;
    Button download, myLib;
    BackGThread bgThread;
    String songID = "", ringToneUrl = "";
    ProgressBar dprogress;
    private static final String TAG = "SSRMain";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "===ONCREATE===");

        setContentView(R.layout.activity_spiritual_soldier_ringtone);

        //create variables
        init();

        //set the click listener for the button
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ringToneUrl.matches("")) {
                    Toast.makeText(getApplicationContext(), "Please Choose a Tone!", Toast.LENGTH_LONG).show();
                    return;
                }
                bgThread = new BackGThread();
                bgThread.execute("download");
                Toast.makeText(getApplicationContext(),"Download in progress!", Toast.LENGTH_LONG).show();
            }
        });
        container.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "===OItemC===");
                songID = list.get(position);
                bgThread = new BackGThread();
                bgThread.execute(songID);
            }
        });

        myLib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("com.allhustlenoluck.developer.snax2poreair.spiritualsoldierringtones.MySSRDownloads");
                startActivity(intent);
            }
        });
    }

    public void init(){
        Log.d(TAG,"===init===");

        container = (ListView) findViewById(R.id.lvssr);
        list = new ArrayList<>();
        filler = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, list);
        container.setAdapter(filler);
        web = (WebView) findViewById(R.id.wvpreview);
        web.setWebViewClient(new WebViewClient());
        web.setInitialScale(1);
        web.getSettings().setLoadWithOverviewMode(true);
        web.getSettings().setUseWideViewPort(true);
        web.loadUrl("http://192.168.6.129/choose.html");
        download = (Button) findViewById(R.id.bdown);
        myLib = (Button) findViewById(R.id.bLib);
        dprogress = (ProgressBar) findViewById(R.id.pbdownload);

        //get the ringtone list from server
        bgThread = new BackGThread();
        bgThread.execute("getList");

    }
    public class BackGThread extends AsyncTask<String, Integer, String> {

        String action;
        private int conLen = -1;
        private int counter = 0;
        private int cProg = 0;

        @Override
        protected void onPreExecute() {
            Log.d(TAG, "===ONPRE===");

            if(!ringToneUrl.matches("")){
                dprogress.setVisibility(View.VISIBLE);
            }
        }

        @Override
        protected String doInBackground(String[] params) {
            Log.d(TAG, "===DIBG===");

            action = params[0];

           if(action.matches("download")){
               Log.d(TAG,"===Then1===");

               try {

                   URL url = new URL(ringToneUrl);
                   HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                   connection.setRequestMethod("GET");
                   connection.setDoOutput(true);
                   connection.connect();

                   conLen = connection.getContentLength();

                   File path = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_RINGTONES).getAbsolutePath());

                   if(!path.exists()){
                       path.mkdir();
                   }
                   File saved = new File(path, songID);
                   FileOutputStream fos = new FileOutputStream(saved);
                   InputStream input = connection.getInputStream();

                   byte[] buffer = new byte[1024];
                   int read = -1;
                   while ((read = input.read(buffer)) != -1) {

                       fos.write(buffer, 0, read);
                       counter = counter + read;
                       publishProgress(counter);

                   }
                   fos.close();
                   input.close();

                   ContentValues content = new ContentValues();
                   content.put(MediaStore.MediaColumns.DATA,saved.getAbsolutePath());
                   content.put(MediaStore.MediaColumns.TITLE, songID);
                   content.put(MediaStore.MediaColumns.SIZE, 215454);
                   content.put(MediaStore.MediaColumns.MIME_TYPE, "audio/*");
                   content.put(MediaStore.Audio.Media.ARTIST, "n/a");
                   content.put(MediaStore.Audio.Media.DURATION, 230);
                   content.put(MediaStore.Audio.Media.IS_RINGTONE, true);
                   content.put(MediaStore.Audio.Media.IS_NOTIFICATION, true);
                   content.put(MediaStore.Audio.Media.IS_ALARM, true);
                   content.put(MediaStore.Audio.Media.IS_MUSIC, false);

                   Uri uri = MediaStore.Audio.Media.getContentUriForPath(
                           saved.getAbsolutePath());
                   getApplicationContext().getContentResolver().insert(uri, content);

               } catch (MalformedURLException e) {
                   e.printStackTrace();
               } catch (IOException e) {
                   e.printStackTrace();
               }

           }else{

               Log.d(TAG, "===ELSE1===");
               try {

                   URL url = new URL("http://192.168.6.129/ssrserverside.php");
                   HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                   connection.setRequestMethod("POST");
                   connection.setDoOutput(true);
                   connection.setDoInput(true);

                   OutputStream outp = connection.getOutputStream();
                   BufferedWriter bwritter = new BufferedWriter(new OutputStreamWriter(outp,"UTF-8"));

                   String data = null;

                   if(action.matches("getList")){

                       data = URLEncoder.encode("action", "UTF-8") + "=" + URLEncoder.encode("get list", "UTF-8");

                   }else{

                       data = URLEncoder.encode("action", "UTF-8") + "=" + URLEncoder.encode("chose", "UTF-8") + "&" +
                               URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(action, "UTF-8");

                   }

                   bwritter.write(data);
                   bwritter.flush();
                   bwritter.close();
                   outp.close();

                   Log.d(TAG,"===SENT===");

                   //response
                   InputStream inp = connection.getInputStream();
                   BufferedReader breader = new BufferedReader(new InputStreamReader(inp, "iso-8859-1"));
                   String line, response = null;
                   while((line = breader.readLine()) != null){
                       if(action.matches("getList")) {
                           String l[] = line.split(";");
                           for(int i = 0; i < l.length; i++){
                               list.add(l[i]);
                           }
                       }else{

                           response = line;

                       }
                   }

                   breader.close();
                   inp.close();

                   connection.disconnect();

                   return response;

               } catch (MalformedURLException e) {
                   e.printStackTrace();
               } catch (IOException e) {
                   e.printStackTrace();
               }
           }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.d(TAG, "===ONPROG===");

            cProg = (int)(((double)values[0]/conLen)*100);
            dprogress.setProgress(cProg);

        }

        @Override
        protected void onPostExecute(String value) {
            Log.d(TAG,"===ONPOST===");

            if(action.matches("getList")){
                filler.notifyDataSetChanged();
            }else if(action.matches("download")){
                dprogress.setVisibility(View.GONE);
                ringToneUrl = "";
                web.loadUrl("http://192.168.6.129/choose.html");

            }else{
                ringToneUrl = "http://192.168.6.129/sounds/" + value;
                Log.d(TAG, ringToneUrl);
                web.loadUrl(ringToneUrl);
            }

        }
    }
}
