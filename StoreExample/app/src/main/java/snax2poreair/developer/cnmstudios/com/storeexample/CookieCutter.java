package snax2poreair.developer.cnmstudios.com.storeexample;

import android.graphics.Bitmap;
import android.util.Log;

/**
 * Created by 2poreAIR on 4/11/2016.
 */
public class CookieCutter {
    private static final String TAG = "COOKIE";
    Bitmap imgRes;
    private int width = 0, height = 0, resource, x = 0, y = 0;

    public CookieCutter(Bitmap bmp, int res){

        imgRes = bmp;
        resource = res;
        width = imgRes.getWidth()/4;
        height = imgRes.getHeight()/2;

        switch(resource){
            case 4:
                y = height;
                break;
            default:
                x = resource * width;
                break;
        }
    }

    public Bitmap getImage() {
        Log.d(TAG, "===GETIMAGE===");

        return Bitmap.createBitmap(imgRes, x, y, width, height);
    }
}