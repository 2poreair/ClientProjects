package com.allhustlenoluck.developer.snax2poreair.spiritualsoldierringtones;

import android.app.ListActivity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

/**
 * Created by 2poreAIR on 3/22/2016.
 */
public class MySSRDownloads extends ListActivity {
    ArrayList<String> list;
    private static final String TAG = "MYLIBRARY";
     MediaPlayer mplayer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "===ONCREATE===");

        String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_RINGTONES).toString();
        Log.d(TAG,path);

        list = new ArrayList<>();

        File f = new File(path);
        File file[] = f.listFiles();
        Log.d(TAG, "Size: "+ file.length);

        for(int i = 0; i < file.length; i++){
            Log.d(TAG, "FileName:" + file[i].getName());
            list.add(file[i].getName());
        }

        java.util.Collections.sort(list, String.CASE_INSENSITIVE_ORDER);

        setListAdapter(new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, list));

        getListView().setBackgroundColor(Color.DKGRAY);

    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        if(mplayer != null){
            mplayer.stop();
            mplayer.release();
            mplayer = null;
        }
//192.168.43.7
        FileDescriptor fileD = null;

        try {
            File baseDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_RINGTONES);
            String audioP = baseDir.getAbsolutePath() + "/" + list.get(position);
            FileInputStream fis = new FileInputStream(audioP);
            fileD = fis.getFD();

            if(fileD != null){
                mplayer = new MediaPlayer();
                mplayer.setDataSource(fileD);
                mplayer.prepare();
                mplayer.start();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "===STOP===");

        if(mplayer != null){
            mplayer.stop();
            mplayer.release();
        }
        mplayer = null;
    }
}
