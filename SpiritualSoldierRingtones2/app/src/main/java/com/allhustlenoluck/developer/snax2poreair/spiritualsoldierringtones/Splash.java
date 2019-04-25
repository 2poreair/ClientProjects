package com.allhustlenoluck.developer.snax2poreair.spiritualsoldierringtones;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by 2poreAIR on 3/22/2016.
 */
public class Splash extends Activity {
    Thread hold = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);

        hold = new Thread(){
            public void run(){
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally{
                    Intent nextPage = new Intent("com.allhustlenoluck.developer.snax2poreair.spiritualsoldierringtones.SpiritualSoldierRingtone");
                    startActivity(nextPage);
                }
            }
        };

        hold.start();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if(hold != null){
            try {
                hold.join();
                this.finish();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
