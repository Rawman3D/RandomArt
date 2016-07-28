package com.example.kabinkale.randomart;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;

import java.util.Random;

/**
 * Created by Kabin Kale on 7/27/2016.
 */
public class DrawArtService extends IntentService {
    Random random= new Random();
    public static final String IN_HEIGHT="inHeight";
    public static final String IN_WIDTH="inWidth";
    public static final String OUT_BITMAP = "outBitmap";
    public static final String ACTION_RESP = MainActivity.class.getSimpleName() + "MESSAGE_PROCESSED";


    public DrawArtService() {
        super(DrawArtService.class.getName());
    }

    public DrawArtService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        int height = intent.getIntExtra(IN_HEIGHT,0);
        int width = intent.getIntExtra(IN_WIDTH,0);
        int i,j;
        Bitmap Art = Bitmap.createBitmap(height,width, Bitmap.Config.ARGB_8888);

        for(i=0;i<Art.getWidth();i++){
            for (j=0;j<Art.getHeight();j++){

                Art.setPixel(i,j, Color.argb(255,getRand(),getRand(),getRand()));

            }
        }

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(ACTION_RESP);
        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
        broadcastIntent.putExtra(OUT_BITMAP,Art);
        sendBroadcast(broadcastIntent);
    }

    /**
     * generate random numbers
     *
     * @return random number %255
     */
    private int getRand(){
        int rand=random.nextInt(255);
        if(rand<100){
            rand+=random.nextInt(100);
        }
        return rand;
    }
}
